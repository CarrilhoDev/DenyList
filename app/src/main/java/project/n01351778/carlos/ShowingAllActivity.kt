package project.n01351778.carlos

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.room.util.TableInfo
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class ShowingAllActivity : AppCompatActivity() {

    private lateinit var mDb: EvaluationDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_showing_all)
        setSupportActionBar(findViewById(R.id.toolbar))

        val showAll = findViewById<TextView>(R.id.showallTv)
        val showAll2 = findViewById<TextView>(R.id.showAllTv2)
        val returnBtn = findViewById<Button>(R.id.returnBtn)
        val deleteAllBtn = findViewById<Button>(R.id.deleteAllBtn)
        deleteAllBtn.isVisible = false

        mDb = EvaluationDatabase.getInstance(applicationContext)


        doAsync {
            val evaluationsList = mDb.evaluationDao().getAll() //get all the entries from database

            //and present them in a text view.
            uiThread {

                showToast("${evaluationsList.size} evaluations found.")
                showAll.text = ""
                for (evaluation in evaluationsList) {
                        if(evaluationsList.indexOf(evaluation) < 5) {
                            showAll.append(
                                "Name: ${evaluation.placeName}\n" + "Rate: ${evaluation.placeRate}\nComment: ${evaluation.placeComment}\n\n"
                            )
                        } else{
                            showAll2.append(
                                "Name: ${evaluation.placeName}\n" + "Rate: ${evaluation.placeRate}\nComment: ${evaluation.placeComment}\n\n"
                            )
                        }

                }
                if(showAll.text.isNotEmpty()){
                    deleteAllBtn.isVisible = true
                }
            }
        }

        //delete all entries from database
        deleteAllBtn.setOnClickListener {
            doAsync {
                // do things in the background  // (1)

                mDb.evaluationDao().deleteAll()  // Get the student list from database

                uiThread {
                    // make changes to the UI     // (2)
                    showAll.text = "" // Display the students in text view with grade
                    showAll2.text = ""
                    showToast("All entries deleted")
                    deleteAllBtn.isVisible = false

                }
            }
           // and also delete all the internal storage files.
                filesDir.deleteRecursively()
        }

        //returns to the adding activity
        returnBtn.setOnClickListener {
            val newIntent = Intent(this, MainActivity::class.java)
            startActivity(newIntent)
        }


    }
    fun Context.showToast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, text, duration).show()
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