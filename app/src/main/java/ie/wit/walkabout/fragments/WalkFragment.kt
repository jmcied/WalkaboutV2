package ie.wit.walkabout.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ie.wit.walkabout.R
import ie.wit.walkabout.databinding.FragmentWalkBinding
import ie.wit.walkabout.main.WalkaboutApp
import ie.wit.walkabout.main.walksStore
import ie.wit.walkabout.models.WalkaboutModel

class WalkFragment : Fragment() {

    lateinit var app: WalkaboutApp
    private var _fragBinding: FragmentWalkBinding? = null
    private val fragBinding get() = _fragBinding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        app = activity?.application as WalkaboutApp
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_walk, container, false)
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

    fun setButtonListener(layout: FragmentWalkBinding) {
        layout.walkButton.setOnClickListener {
    val title = fragBinding.walkTitle.text.toString()
    val selectedDifficulty = fragBinding.radioGroupDifficulty.checkedRadioButtonId
    val difficulty = when (selectedDifficulty) {
        R.id.radioEasy -> "Easy"
        R.id.radioMedium -> "Medium"
        R.id.radioHard -> "Hard"
        else -> {
            "Easy"
        }
    }
    val selectedTerrain = fragBinding.radioGroupTerrain.checkedRadioButtonId
    val terrain = when (selectedTerrain) {
        R.id.radioBeach -> "Beach"
        R.id.radioForest -> "Forest"
        R.id.radioHill -> "Hill"
        else -> {""}
    }
    walksStore.create(
        WalkaboutModel(
            title = title,
            difficulty = difficulty,
            terrain = terrain
        )
    )
}
}
}