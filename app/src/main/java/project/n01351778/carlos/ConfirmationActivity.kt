package project.n01351778.carlos

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity

class ConfirmationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmation)



        val okBtn = findViewById<Button>(R.id.insertBtn)

        val intent = getIntent()
        val name = intent.getStringExtra("Name")
        val rate = intent.getStringExtra("Rate")
        val comment = intent.getStringExtra("Comment")
        var result = findViewById<TextView>(R.id.commentTv)

        result.text = "You have made a new evaluation.\nThe details follow below: \nName of the place: " + name.toString() + "\nRating: " + rate + "\nComment: " + comment.toString()
        okBtn.setOnClickListener {
            val newIntent = Intent(this, MainActivity::class.java)
            startActivity(newIntent)
        }
    }


}