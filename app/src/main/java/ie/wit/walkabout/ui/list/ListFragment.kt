package ie.wit.walkabout.ui.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SwitchCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ie.wit.walkabout.R
import ie.wit.walkabout.databinding.FragmentListBinding
import ie.wit.walkabout.adapters.WalkAdapter
import ie.wit.walkabout.adapters.WalkClickListener
import ie.wit.walkabout.main.WalkaboutApp
import ie.wit.walkabout.models.WalkaboutModel
import ie.wit.walkabout.ui.auth.LoggedInViewModel
import ie.wit.walkabout.ui.list.ListFragmentDirections.Companion.actionListFragmentToWalkDetailFragment
import ie.wit.walkabout.utils.SwipeToDeleteCallback
import ie.wit.walkabout.utils.SwipeToEditCallback
import ie.wit.walkabout.utils.createLoader
import ie.wit.walkabout.utils.hideLoader
import ie.wit.walkabout.utils.showLoader

class ListFragment : Fragment(), WalkClickListener  {

    private var _fragBinding: FragmentListBinding? = null
    private val fragBinding get() = _fragBinding!!
    lateinit var loader : AlertDialog
    private val listViewModel: ListViewModel by activityViewModels()
    private val loggedInViewModel : LoggedInViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragBinding = FragmentListBinding.inflate(inflater, container, false)
        val root = fragBinding.root

        setupMenu()
        loader = createLoader(requireActivity())

        fragBinding.recyclerView.layoutManager = LinearLayoutManager(activity)
        fragBinding.fab.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToWalkFragment()
            findNavController().navigate(action)
        }
        showLoader(loader,"Downloading Walks")
        listViewModel.observableWalksList.observe(viewLifecycleOwner, Observer {
                walks ->
            walks?.let {
                render(walks as ArrayList<WalkaboutModel>)
                hideLoader(loader)
                checkSwipeRefresh()
            }
        })

        setSwipeRefresh()

        val swipeDeleteHandler = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                showLoader(loader,"Deleting Walk")
                val adapter = fragBinding.recyclerView.adapter as WalkAdapter
                adapter.removeAt(viewHolder.adapterPosition)
                listViewModel.delete(listViewModel.liveFirebaseUser.value?.uid!!,
                    (viewHolder.itemView.tag as WalkaboutModel).uid!!)

                hideLoader(loader)
            }
        }
        val itemTouchDeleteHelper = ItemTouchHelper(swipeDeleteHandler)
        itemTouchDeleteHelper.attachToRecyclerView(fragBinding.recyclerView)

        val swipeEditHandler = object : SwipeToEditCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                onWalkClick(viewHolder.itemView.tag as WalkaboutModel)
            }
        }
        val itemTouchEditHelper = ItemTouchHelper(swipeEditHandler)
        itemTouchEditHelper.attachToRecyclerView(fragBinding.recyclerView)

        return root
    }
    private fun setupMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onPrepareMenu(menu: Menu) {
                // Handle for example visibility of menu items
            }

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_list, menu)

                val item = menu.findItem(R.id.toggleWalks) as MenuItem
                item.setActionView(R.layout.togglebutton_layout)
                val toggleWalks: SwitchCompat = item.actionView!!.findViewById(R.id.toggleButton)
                toggleWalks.isChecked = false

                toggleWalks.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) listViewModel.loadAll()
                    else listViewModel.load()
                }
            }
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Validate and handle the selected menu item
                return NavigationUI.onNavDestinationSelected(menuItem,
                    requireView().findNavController())
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun render(walksList: ArrayList<WalkaboutModel>) {
        fragBinding.recyclerView.adapter = WalkAdapter(walksList, this,
        listViewModel.readOnly.value!!)
        if (walksList.isEmpty()) {
            fragBinding.recyclerView.visibility = View.GONE
            fragBinding.walksNotFound.visibility = View.VISIBLE
        } else {
            fragBinding.recyclerView.visibility = View.VISIBLE
            fragBinding.walksNotFound.visibility = View.GONE
        }
    }

    override fun onWalkClick(walk: WalkaboutModel) {
        val action = actionListFragmentToWalkDetailFragment(walk.uid!!)
        if(!listViewModel.readOnly.value!!) {
            findNavController().navigate(action)
        }
    }

    private fun setSwipeRefresh() {
        fragBinding.swiperefresh.setOnRefreshListener {
            fragBinding.swiperefresh.isRefreshing = true
            showLoader(loader,"Downloading Walks")
            if(listViewModel.readOnly.value!!)
                listViewModel.loadAll()
            else
                listViewModel.load()
        }
    }

    private fun checkSwipeRefresh() {
        if (fragBinding.swiperefresh.isRefreshing)
            fragBinding.swiperefresh.isRefreshing = false
    }

    override fun onResume() {
        super.onResume()
        showLoader(loader,"Downloading Donations")
        loggedInViewModel.liveFirebaseUser.observe(viewLifecycleOwner, Observer { firebaseUser ->
            if (firebaseUser != null) {
                listViewModel.liveFirebaseUser.value = firebaseUser
                listViewModel.load()
            }
        })
        //hideLoader(loader)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }
}