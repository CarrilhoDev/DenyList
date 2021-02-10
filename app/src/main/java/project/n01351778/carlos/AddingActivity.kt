package project.n01351778.carlos

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Contacts
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.lang.Exception

class AddingActivity : AppCompatActivity() {

    private lateinit var mDb: EvaluationDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adding)
        setSupportActionBar(findViewById(R.id.toolbar))

        //buttons to be used
        val insertBtn = findViewById<Button>(R.id.insertBtn)
        val returnBtn = findViewById<Button>(R.id.cancelBtn)
        val clearBtn = findViewById<Button>(R.id.clearBtn)


        //text fields and radio buttons to be used
        var addName = findViewById<TextView>(R.id.addNameEt)
        var addComment = findViewById<TextView>(R.id.addCommentEt)
        var addRate = findViewById<RadioGroup>(R.id.choiceRadGroup)
        var rate = ""
//        var experienceTv = findViewById<TextView>(R.id.experienceTv)

        mDb = EvaluationDatabase.getInstance(applicationContext)

        //get the name that a user put on the the search field in the main activity, so that they do not need to fill the name field again
        val intent = getIntent()
        addName.text = intent.getStringExtra("Name")

        //getting the rate option from the radios
        addRate.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.excelentRad -> {
                    rate = "Excellent"
                }
                R.id.regularRad -> {
                    rate = "Regular"
                }
                R.id.neverMoreRad -> {
                    rate = "Never More"
                }
            }
        }

        //giving up of adding a place
       returnBtn.setOnClickListener {
            finish()
        }

        //add the place, rate, and comments (if it exists) to a file
        insertBtn.setOnClickListener {
            var placeName = addName.text.toString()
            var placeRate = rate
            var placeComment = addComment.text.toString()



            if (placeName.isNotEmpty() && (placeRate.isNotBlank())) {

                //for Room Database
                val evaluation = EvaluationEntity(0, placeName, placeRate, placeComment)
                if (mDb.evaluationDao().findByResult(placeName).size == 0) {
                    doAsync {
                        // do things in the background  // (1)
                        mDb.evaluationDao().insert(evaluation) // Put the evaluation in database
                        uiThread {
                            //make changes to the Contacts.Intents.UI   // (2)
                            showToast("record inserted")
                        }
                    }
                } else{
                    doAsync {
                        // do things in the background  // (1)
                        mDb.evaluationDao().updateEvaluation(mDb.evaluationDao().getId(evaluation.placeName), evaluation.placeName, evaluation.placeRate, evaluation.placeComment) // Put the evaluation in database
                        uiThread {
                            //make changes to the Contacts.Intents.UI   // (2)
                            showToast("This place was already evaluated and it as updated!")
                        }
                    }
                }

                    //for internal storage
                    val file = addName.text.toString()
                    val data =
                        "You have evaluated the " + file + " as a(n) " + rate.toUpperCase() + " place" + " and commented what follows: \n" + placeComment

                    val fileOutputStream: FileOutputStream

                    try {
                        fileOutputStream = openFileOutput(file, Context.MODE_PRIVATE)
                        fileOutputStream.write(data.toByteArray())
                    } catch (e: FileNotFoundException) {
                        e.printStackTrace()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    //
                val newIntent = Intent(this@AddingActivity, ConfirmationActivity::class.java)
                newIntent.putExtra("Name", addName.text.toString())
                newIntent.putExtra("Rate", rate)
                newIntent.putExtra("Comment", addComment.text.toString())
                startActivity(newIntent)
                } else {
                    showToast("You need to add a valid Name of a place and evaluate it")
                }


            }
            clearBtn.setOnClickListener {
                addComment.text = ""
                addName.text = ""
                addRate.clearCheck()
            }
        }

        fun Context.showToast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
            Toast.makeText(this, text, duration).show()
        }

//    fun isOnlyLetters(word: String): Boolean {
////        val regex = "^[A-Za-z]*$".toRegex()
////        return regex.matches(word)
////    }
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