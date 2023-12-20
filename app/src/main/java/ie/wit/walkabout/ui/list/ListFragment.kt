package ie.wit.walkabout.ui.list

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ie.wit.walkabout.R
import ie.wit.walkabout.databinding.FragmentListBinding
import ie.wit.walkabout.adapters.WalkAdapter
import ie.wit.walkabout.main.WalkaboutApp
import ie.wit.walkabout.models.WalkaboutModel

class ListFragment : Fragment() {

    lateinit var app: WalkaboutApp
    private var _fragBinding: FragmentListBinding? = null
    private val fragBinding get() = _fragBinding!!
    private lateinit var listViewModel: ListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //app = activity?.application as WalkaboutApp
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragBinding = FragmentListBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        //activity?.title = getString(R.string.action_list)
        setupMenu()
        fragBinding.recyclerView.layoutManager = LinearLayoutManager(activity)

        listViewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        listViewModel.observableDonationsList.observe(viewLifecycleOwner, Observer {
                walks ->
            walks?.let { render(walks) }
        })

        val fab: FloatingActionButton = fragBinding.fab
        fab.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToWalkFragment()
            findNavController().navigate(action)
        }
        return root
    }
    private fun setupMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onPrepareMenu(menu: Menu) {
                // Handle for example visibility of menu items
            }

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_list, menu)
            }
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Validate and handle the selected menu item
                return NavigationUI.onNavDestinationSelected(menuItem,
                    requireView().findNavController())
            }     }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun render(walksList: List<WalkaboutModel>) {
        fragBinding.recyclerView.adapter = WalkAdapter(walksList)
        if (walksList.isEmpty()) {
            fragBinding.recyclerView.visibility = View.GONE
            fragBinding.donationsNotFound.visibility = View.VISIBLE
        } else {
            fragBinding.recyclerView.visibility = View.VISIBLE
            fragBinding.donationsNotFound.visibility = View.GONE
        }
    }

    override fun onWalkClick(walk: WalkaboutModel) {
        val action = ListFragmentDirections.actionListFragmentToWalkDetailFragment(walk.id)
        findNavController().navigate(action)
    }

    override fun onResume() {
        super.onResume()
        listViewModel.load()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }
}