package project.n01351778.carlos

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible

class AboutActivity : AppCompatActivity() {
    val TAG = "Myapp"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        setSupportActionBar(findViewById(R.id.toolbar))

        val aboutReturn = findViewById<Button>(R.id.aboutReturnBtn)
        val aboutTv = findViewById<TextView>(R.id.aboutTv)

//another one “About” should include a brief information about project developer (including submission project date, student name and Student ID).



        aboutReturn.setOnClickListener {
            val newIntent = Intent(this, MainActivity::class.java)
            startActivity(newIntent)
        }

        }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {

        R.id.action_searchAll -> {
            val newIntent = Intent(this, ShowingAllActivity::class.java)
            startActivity(newIntent)
            true
        }
        R.id.action_help -> {
            val newIntent = Intent(this, HelpActivity::class.java)
            startActivity(newIntent)
            true
        }
        R.id.action_about -> {
            val newIntent = Intent(this, AboutActivity::class.java)
            startActivity(newIntent)
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

}