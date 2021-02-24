package com.example.homework

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class AddReminderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_reminder)

        val title: EditText = findViewById(R.id.etMessage)
        val time: EditText = findViewById(R.id.etTime)
        val button: Button = findViewById(R.id.btnReminder)

        val posIntent: Intent = intent

        val position: Int = posIntent.getIntExtra("position", 0)

        button.setOnClickListener{

            val message: String = title.text.toString()
            val clock: String = time.text.toString()

            val intent: Intent = Intent()
            intent.putExtra("keymessage", message)
            intent.putExtra("keytime", clock)
            intent.putExtra("position", position)

            setResult(Activity.RESULT_OK, intent)
            finish()

        }


    }
}