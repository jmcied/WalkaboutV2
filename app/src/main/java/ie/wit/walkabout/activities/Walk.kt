package ie.wit.walkabout.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_walk, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_list -> {
                startActivity(Intent(this, List::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}