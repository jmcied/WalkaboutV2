package ie.wit.walkabout.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import ie.wit.walkabout.R
import ie.wit.walkabout.adapters.WalkAdapter
import ie.wit.walkabout.databinding.ActivityListBinding
import ie.wit.walkabout.databinding.ActivityWalkBinding
import ie.wit.walkabout.main.WalkaboutApp
import ie.wit.walkabout.main.walksStore

class List : AppCompatActivity() {

    private lateinit var listLayout : ActivityListBinding
    lateinit var app: WalkaboutApp
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listLayout = ActivityListBinding.inflate(layoutInflater)
        setContentView(listLayout.root)

        app = this.application as WalkaboutApp
        listLayout.recyclerView.layoutManager = LinearLayoutManager(this)
        listLayout.recyclerView.adapter = WalkAdapter(walksStore.findAll())
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_walk -> {
                startActivity(Intent(this, Walk::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}