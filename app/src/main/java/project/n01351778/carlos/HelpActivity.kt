package project.n01351778.carlos

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity

class HelpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)
        setSupportActionBar(findViewById(R.id.toolbar))

        val helpReturn = findViewById<Button>(R.id.helpReturnBtn)

        helpReturn.setOnClickListener {
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