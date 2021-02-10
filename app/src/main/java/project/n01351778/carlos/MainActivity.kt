package project.n01351778.carlos

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import java.io.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


class MainActivity : AppCompatActivity() {
    val TAG = "Myapp"
    private lateinit var mDb: EvaluationDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate")
        setContentView(R.layout.activity_main)

        //buttons used in the code
        val searchBtn = findViewById<Button>(R.id.searchBtn)
        val addBtn = findViewById<Button>(R.id.addBtn)
        val clearBtn = findViewById<Button>(R.id.clearBtn)
        val delBtn = findViewById<Button>(R.id.deleteBtn)
        val selectAllBtn = findViewById<Button>(R.id.selectAllBtn)

        //Edit Text Fields used in the code
        var searchName = findViewById<TextView>(R.id.nameEt)
        var result = findViewById<TextView>(R.id.commentTv)

        mDb = EvaluationDatabase.getInstance(applicationContext)

        //open the options to fill in with data in another activity
        addBtn.setOnClickListener {
            val newIntent = Intent(this, AddingActivity::class.java)
            if(searchName.text.isNotEmpty()) {
                newIntent.putExtra("Name", searchName.text.toString())
            }
            result.isVisible = false
            clearBtn.isVisible = false
                startActivity(newIntent)
        }

        //lead us to the showingAll activity
        selectAllBtn.setOnClickListener {
            val newIntent = Intent(this, ShowingAllActivity::class.java)
            startActivity(newIntent)
        }


        //Search for a place previously added and display the results (name, rate, comment)
        searchBtn.setOnClickListener {
            //searching in the internal storage
//            val filename = searchName.text.toString()
//            if (filename != null && filename.trim() != "") {
//                var fileInputStream: FileInputStream? = null
//                try {
//                    fileInputStream = openFileInput(filename)
//                    var inputStreamReader: InputStreamReader = InputStreamReader(fileInputStream)
//                    val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)
//
//                    val stringBuilder: StringBuilder = StringBuilder()
//                    var text: String? = null
//
//                    while ({ text = bufferedReader.readLine(); text }() != null) {
//                        stringBuilder.append(text)
//                    }
//                    result.setText(stringBuilder.toString()).toString()
//                    result.isVisible = true
//                    clearBtn.isVisible = true
//                } catch (e: FileNotFoundException) {
//                    e.printStackTrace()
//                    val error = "There is no such file"
//                    showToast(error)
//                }
//            } else {
//                showToast("Name of the place can't be blank")
//            }

            //SEARCHING DATA BY NAME USING DATABASE
            if (searchName.text.isNotEmpty()) {
                doAsync {
                    val evaluationsList =
                        mDb.evaluationDao().findByResult(searchName.text.toString())
                    uiThread {
                        showToast("${evaluationsList.size} evaluations found.")
                        result.text = ""
                        for (evaluation in evaluationsList) {
                            result.append(
                                "Name: ${evaluation.placeName} \n" + "Rate: ${evaluation.placeRate}\nComment: ${evaluation.placeComment}\n\n"
                            )
                            result.isVisible = true
                            clearBtn.isVisible = true
                        }
                    }
                }
            } else {
                showToast("Name of the place can't be blank")
            }
        }


        //DELETING DATA
        delBtn.setOnClickListener {
            //DELETE FROM DATABASE
            val name = searchName.text
            if (searchName.text.isNotEmpty()) {
                doAsync {
                    // do things in the background  // (1)
                    val deletedData = mDb.evaluationDao().delete(searchName.text.toString())
                    uiThread {
                        showToast("$deletedData data deleted. Name: $name")
                        result.isVisible = false
                        searchName.text = ""
                        clearBtn.isVisible = false
                    }
                }
            } else {
                showToast("Name cannot be blank")
            }
            //DELETE FROM INTERNAL STORAGE
            val filename = searchName.text.toString()
            if (filename != null && filename.trim() != "") {
                var fileInputStream: FileInputStream? = null
                try {
                    fileInputStream = openFileInput(filename)
                    var inputStreamReader: InputStreamReader = InputStreamReader(fileInputStream)
                    val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)

                    this.deleteFile(filename)
                    result.isVisible = false
                    searchName.text = ""
                    clearBtn.isVisible = false
                    showToast("$filename deleted from Internal Storage and from database")

                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                    val error = "There is no such file"
                    showToast(error)
                }
            } else if (filename.isNullOrEmpty()) {
                val error = "You need to choose a file to delete"
                showToast(error)
            }

        }
        //Clearing fields
        clearBtn.setOnClickListener {
            result.isVisible = false
            searchName.text = ""
            result.text = ""
            clearBtn.isVisible = false

        }
    }

    fun Context.showToast(text: CharSequence, duration: Int = Toast.LENGTH_LONG) {
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


    override fun onStart() {
        super.onStart()
        Log.i(TAG, "onStart")
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "onStop")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i(TAG, "onRestart")
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy")
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.i(TAG, "onSaveInstanceState")
        val message = findViewById<TextView>(R.id.commentTv).text
        outState?.putCharSequence("savedText1", message)


    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.i(TAG, "onRestoreInstanceState")
        val message = savedInstanceState?.getCharSequence("savedText1")
        if (message.isNullOrEmpty()) {
            findViewById<TextView>(R.id.commentTv).isVisible = false
            findViewById<Button>(R.id.clearBtn).isVisible = false

        } else {
            findViewById<TextView>(R.id.commentTv).text = message
            findViewById<TextView>(R.id.commentTv).isVisible = true
            findViewById<Button>(R.id.clearBtn).isVisible = true
        }


    }


}

