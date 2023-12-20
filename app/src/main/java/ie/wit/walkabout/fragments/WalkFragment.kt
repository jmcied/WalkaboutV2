package ie.wit.walkabout.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.snackbar.Snackbar
import ie.wit.walkabout.R
import ie.wit.walkabout.databinding.FragmentWalkBinding
import ie.wit.walkabout.main.WalkaboutApp
import ie.wit.walkabout.models.WalkaboutModel
import timber.log.Timber.Forest.i

class WalkFragment : Fragment() {

    lateinit var app: WalkaboutApp
    private var _fragBinding: FragmentWalkBinding? = null
    private val fragBinding get() = _fragBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        app = activity?.application as WalkaboutApp
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragBinding = FragmentWalkBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        activity?.title = getString(R.string.action_walk)

        setButtonListener(fragBinding)
        return root;
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            WalkFragment().apply {
                arguments = Bundle().apply {}
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_walk, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item,
            requireView().findNavController()) || super.onOptionsItemSelected(item)
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
            app.walksStore.create(
                WalkaboutModel(
                    title = title,
                    difficulty = difficulty,
                    terrain = terrain
                )           )

            i("Added: $title, $difficulty, $terrain")
            }
            else {
            Snackbar
                .make(it, "Please Enter a title", Snackbar.LENGTH_LONG)
                .show()
        }

        }
    }
}