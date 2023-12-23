package ie.wit.walkabout.ui.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ie.wit.walkabout.databinding.FragmentWalkDetailBinding
import ie.wit.walkabout.ui.auth.LoggedInViewModel
import ie.wit.walkabout.ui.list.ListViewModel
import timber.log.Timber

class WalkDetailFragment : Fragment() {

    private lateinit var detailViewModel: WalkDetailViewModel
    private val args by navArgs<WalkDetailFragmentArgs>()
    private var _fragBinding: FragmentWalkDetailBinding? = null
    private val fragBinding get() = _fragBinding!!
    private val loggedInViewModel : LoggedInViewModel by activityViewModels()
    private val reportViewModel : ListViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        _fragBinding = FragmentWalkDetailBinding.inflate(inflater, container, false)
        val root = fragBinding.root

        detailViewModel = ViewModelProvider(this).get(WalkDetailViewModel::class.java)
        detailViewModel.observableWalk.observe(viewLifecycleOwner, Observer { render() })

        fragBinding.editWalkButton.setOnClickListener {
            detailViewModel.updateWalk(loggedInViewModel.liveFirebaseUser.value?.uid!!,
                args.walkid, fragBinding.walkvm?.observableWalk!!.value!!)
            findNavController().navigateUp()
        }

        fragBinding.deleteWalkButton.setOnClickListener {
            reportViewModel.delete(loggedInViewModel.liveFirebaseUser.value?.email!!,
                detailViewModel.observableWalk.value?.uid!!)
            findNavController().navigateUp()
        }

        return root
    }

    private fun render() {
        fragBinding.editTitle.setText("")
        fragBinding.editTerrain.setText("")
        fragBinding.editDifficulty.setText("")
        fragBinding.walkvm = detailViewModel
        Timber.i("Retrofit fragBinding.walkvm == $fragBinding.walkvm")
    }

    override fun onResume() {
        super.onResume()
        detailViewModel.getWalk(loggedInViewModel.liveFirebaseUser.value?.uid!!,
            args.walkid)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }
}