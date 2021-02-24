package com.example.homework

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class NotificationsAdapter(context: Context, private val list: ArrayList<Reminder>) : RecyclerView.Adapter<NotificationsAdapter.MyViewHolder>() {

    val context = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val reminder: Reminder = list[position]
        holder.setData(reminder, position)

    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private val titles: TextView = itemView.findViewById(R.id.txvTitle)
        private val delete: ImageView = itemView.findViewById(R.id.imgDelete)
        private val time: TextView = itemView.findViewById(R.id.txvTime)


        init {
            delete.setOnClickListener(this)
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            val reminder: Reminder = list[position]
            if ((position != RecyclerView.NO_POSITION) && (v == itemView)) {
                //listener.onItemClick(position)
                val inflater: LayoutInflater = LayoutInflater.from(context)
                val view: View = inflater.inflate(R.layout.update_reminder, null)

                val updMsg: TextView = view.findViewById(R.id.etMessage)
                val updTime: TextView = view.findViewById(R.id.etTime)

                updMsg.text = reminder.message
                updTime.text = reminder.reminder_time

                val builder: AlertDialog.Builder = AlertDialog.Builder(context)
                        .setTitle("Update reminder")
                        .setView(view)
                        .setPositiveButton("update", DialogInterface.OnClickListener { dialog, which ->
                            val isUpdate : Boolean = MainActivity.dbHandler.updateReminder(
                                    reminder.creator_id.toString(),
                                    updMsg.text.toString(),
                                    updTime.text.toString())
                            if (isUpdate) {
                                list[position].message = updMsg.text.toString()
                                list[position].reminder_time = updTime.text.toString()
                                notifyDataSetChanged()
                                Toast.makeText(context, "Updated succesfully", Toast.LENGTH_SHORT).show()
                            }
                            else {
                                Toast.makeText(context, "Error updating", Toast.LENGTH_SHORT).show()
                            }
                        }).setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->

                        })
                val alert = builder.create()
                alert.show()
                notifyDataSetChanged()
            }
            if ((position != RecyclerView.NO_POSITION) && (v == delete)) {
                if (MainActivity.dbHandler.deleteReminder(reminder.creator_id)) {
                    val message: String = reminder.message
                    list.removeAt(position)
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position, list.size)
                    Toast.makeText(context, "Item $message deleted", Toast.LENGTH_SHORT).show()
                }
            }
        }

        fun setData(reminder: Reminder?, pos: Int) {
            titles.text = reminder!!.message
            time.text = reminder.reminder_time
        }
    }

    /*interface OnItemClickListener {
        fun onItemClick(position: Int)
    }*/

}