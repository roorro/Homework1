package com.example.homework

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    companion object {
        lateinit var dbHandler: ReminderDBHandler
    }

    //private val list = generateList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHandler = ReminderDBHandler(this)

        buildRV()

        val add: Button = findViewById(R.id.btnAdd)
        val profile: Button = findViewById(R.id.btnProfile)

        add.setOnClickListener{ startActivityForResult(Intent(applicationContext, AddReminderActivity::class.java), 1) }

        profile.setOnClickListener { startActivity(Intent(applicationContext, ProfileActivity::class.java)) }
    }

    private fun buildRV() {

        val remindersList: ArrayList<Reminder> = dbHandler.getReminders(this)
        val adapter = NotificationsAdapter(this, remindersList)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }

    override fun onResume() {
        buildRV()
        super.onResume()
    }

    /*private fun generateList(): ArrayList<Reminder> {

        val list = ArrayList<Reminder>()

        val tbc: String = "not implemented yet"
        val item = Reminder("Random reminder", tbc, tbc, tbc, tbc, 0, false)

        list += item

        return list

    }*/

    private fun addReminder(message: String, time: String) {
        val tbc: String = "not implemented yet"

        val newItem = Reminder(message, tbc, tbc, time, tbc, 0, false)
        //list.add(0, newItem)
        dbHandler.newReminder(this, newItem)
        //adapter.notifyItemInserted(0)
    }

    /*private fun deleteReminder(position: Int) {
        //list.removeAt(position)
        val remindersList: ArrayList<Reminder> = dbHandler.getReminders(this)
        val adapter = NotificationsAdapter(this, remindersList, this)
        if (dbHandler.deleteReminder(position)) {
            remindersList.removeAt(position)
            adapter.notifyItemRemoved(position)
        }
        //adapter.notifyItemRemoved(position)
    }*/

    /*private fun editReminder(position: Int, message: String, time: String) {
        val clickedItem = list[position]
        clickedItem.message = message
        clickedItem.reminder_time = time
        //adapter.notifyItemChanged(position)
    }*/

    /*override fun onItemClick(position: Int) {
        val intent: Intent = Intent(applicationContext, AddReminderActivity::class.java)
        intent.putExtra("position", position)
        startActivityForResult(intent, 2)
    }*/

    /*override fun onDeleteClick(position: Int) {
        deleteReminder(position)
    }*/

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                val message: String = data?.getStringExtra("keymessage").toString()
                val time: String = data?.getStringExtra("keytime").toString()

                addReminder(message, time)

                Toast.makeText(applicationContext, "Reminder added succesfully", Toast.LENGTH_SHORT).show()
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(applicationContext, "Reminder creation canceled!", Toast.LENGTH_SHORT).show()
            }
        }
        /*if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {
                val message: String = data?.getStringExtra("keymessage").toString()
                val time: String = data?.getStringExtra("keytime").toString()
                val position: Int = data!!.getIntExtra("position", 0)

                editReminder(position, message, time)

                Toast.makeText(applicationContext, "Reminder edited succesfully", Toast.LENGTH_SHORT).show()
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(applicationContext, "Reminder editing canceled!", Toast.LENGTH_SHORT).show()
            }
        }*/
    }

}
