package ie.wit.walkabout.ui.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import ie.wit.walkabout.R

class WalkDetailFragment : Fragment() {

    companion object {
        fun newInstance() = WalkDetailFragment()
    }

    private lateinit var viewModel: WalkDetailViewModel
    private val args by navArgs<WalkDetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_walk_detail, container, false)

        Toast.makeText(context,"Donation ID Selected : ${args.walkid}", Toast.LENGTH_LONG).show()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(WalkDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}