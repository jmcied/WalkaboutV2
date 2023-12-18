package ie.wit.walkabout.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ie.wit.walkabout.R
import ie.wit.walkabout.databinding.ActivityWalkBinding
import ie.wit.walkabout.main.WalkaboutApp
import ie.wit.walkabout.main.walksStore
import ie.wit.walkabout.models.WalkaboutModel
import timber.log.Timber

class Walk : AppCompatActivity() {

    private lateinit var walkLayout: ActivityWalkBinding
    lateinit var app: WalkaboutApp
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        walkLayout = ActivityWalkBinding.inflate(layoutInflater)
        setContentView(walkLayout.root)

        app = this.application as WalkaboutApp

        walkLayout.walkButton.setOnClickListener {
            val selectedDifficulty = walkLayout.radioGroupDifficulty.checkedRadioButtonId
            val difficulty = when (selectedDifficulty) {
                R.id.radioEasy -> "Easy"
                R.id.radioMedium -> "Medium"
                R.id.radioHard -> "Hard"
                else -> {
                    "Easy"
                }
            }
            val selectedTerrain = walkLayout.radioGroupTerrain.checkedRadioButtonId
            val terrain = when (selectedTerrain) {
                R.id.radioBeach -> "Beach"
                R.id.radioForest -> "Forest"
                R.id.radioHill -> "Hill"
                else -> {""}
            }
            walksStore.create(WalkaboutModel(difficulty = difficulty, terrain = terrain))
            Timber.i("Button Pressed: $difficulty $terrain")
        }
    }
}