package ie.wit.walkabout.ui.walk

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.snackbar.Snackbar
import ie.wit.walkabout.R
import ie.wit.walkabout.databinding.FragmentWalkBinding
import ie.wit.walkabout.models.WalkaboutModel
import timber.log.Timber.Forest.i

class WalkFragment : Fragment() {

    //lateinit var app: WalkaboutApp
    private var _fragBinding: FragmentWalkBinding? = null
    private val fragBinding get() = _fragBinding!!
    private lateinit var walkViewModel: WalkViewModel

/*    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //app = activity?.application as WalkaboutApp
        setHasOptionsMenu(true)
    }*/

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        _fragBinding = FragmentWalkBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        activity?.title = getString(R.string.action_walk)
        setupMenu()
        walkViewModel = ViewModelProvider(this).get(WalkViewModel::class.java)
        walkViewModel.observableStatus.observe(viewLifecycleOwner, Observer {
            status -> status?.let { render(status) }
        })

        setButtonListener(fragBinding)
        return root;
    }

    private fun setupMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onPrepareMenu(menu: Menu) {
                // Handle for example visibility of menu items
            }

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_walk, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Validate and handle the selected menu item
                return NavigationUI.onNavDestinationSelected(
                    menuItem,
                    requireView().findNavController()
                )
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun render(status: Boolean) {
        when (status) {
            true -> {
                view?.let {
                    //Uncomment this if you want to immediately return to Report
                    //findNavController().popBackStack()
                }
            }

            false -> Toast.makeText(context, getString(R.string.walkError), Toast.LENGTH_LONG)
                .show()
        }
    }

    fun setButtonListener(layout: FragmentWalkBinding) {
        layout.walkButton.setOnClickListener {
            val title = layout.walkTitle.text.toString()
            if (title.isNotEmpty()) {
                i("add Button Pressed: $title")
                Snackbar
                    .make(it, "Walk $title added", Snackbar.LENGTH_LONG)
                    .show()

                val difficulty = when (layout.radioGroupDifficulty.checkedRadioButtonId) {
                    R.id.radioEasy -> "Easy"
                    R.id.radioMedium -> "Medium"
                    R.id.radioHard -> "Hard"
                    else -> {
                        "Easy"
                    }
                }
                val selectedTerrain = layout.radioGroupTerrain.checkedRadioButtonId
                val terrain = when (selectedTerrain) {
                    R.id.radioBeach -> "Beach"
                    R.id.radioForest -> "Forest"
                    R.id.radioHill -> "Hill"
                    else -> {
                        ""
                    }
                }
                walkViewModel.addWalk(WalkaboutModel(
                        title = title,
                        difficulty = difficulty,
                        terrain = terrain))

                i("Added: $title, $difficulty, $terrain")
            } else {
                Snackbar
                    .make(it, "Please Enter a title", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }
}

