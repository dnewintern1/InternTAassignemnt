package com.base.androidtakotli.activity

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.base.androidtakotli.R
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    private val uiScope = CoroutineScope(Dispatchers.IO)

    private val name = arrayOf(
        "Android Dev W1", "Android Dev W2",
        "Web Dev W1", "Learn Kotlin B1", "Learn DSA"
    )

    private val date = arrayOf(
        "20:00 18th May, 2021", "04:00 18th May, 2021",
        "07:00 19th May, 2021", "04:00 20th May, 2021", "10:00 30th May, 2021"
    )

    private val btn = arrayOf(false, false, false, false, false, false)

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var itemName: TextView = itemView.findViewById(R.id.workshopName)
        var itemDate: TextView = itemView.findViewById(R.id.workshopDate)
        var button: Button = itemView.findViewById(R.id.applyBtn)

        val prefs = itemView.context.getSharedPreferences("prefs", Context.MODE_PRIVATE)!!
        val preferences = itemView.context.getSharedPreferences("userFile", Context.MODE_PRIVATE)!!

        fun hideButton() {
            button.text = "Applied"
            button.isClickable = false
            button.setBackgroundColor(Color.parseColor("#FF3700E9"))
        }

        fun storeData(i: Int) {
            uiScope.launch {
                val workshop = Workshop()
                workshop.name = name[i]
                workshop.date = date[i]
                workshop.button = prefs.getBoolean("registered$i", false)
                WorkshopRepository.getInstance(itemView.context)!!.getAppDatabase().workshopDao()
                    .insert(workshop)
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.frame_textview, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.itemName.text = name[i]
        viewHolder.itemDate.text = date[i]

        // ** SHOW OR HIDE THIS INSTANCE'S BUTTON - AND ONLY THIS INSTANCE'S BUTTON **
        btn[i] = viewHolder.prefs.getBoolean("registered$i", false)

        viewHolder.storeData(i)

        if (btn[i]) {
            viewHolder.hideButton()
        }

        viewHolder.button.setOnClickListener {
            viewHolder.prefs.edit().putBoolean("registered$i", true).apply()
            if (!btn[i]) {
                viewHolder.storeData(i)
            }
            btn[i] = true
            viewHolder.hideButton()

            val context = it.context
            if(context is AppCompatActivity)
            {
                val activity = it.context as AppCompatActivity

                val username = viewHolder.preferences.getString("userName", null)
                val password = viewHolder.preferences.getString("password", null)

                if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
                    activity.supportFragmentManager.commit {
                        replace<Login>(R.id.fragment_login)
                        setReorderingAllowed(true)
                        addToBackStack("login") // name can be null
                    }
                } else {

                    activity.supportFragmentManager.commit {
                        replace<Dashboard>(R.id.fragment_dashboard)
                        setReorderingAllowed(true)
                        addToBackStack("dashboard") // name can be null
                    }
                }
            }else
            {
                Toast.makeText(context, "Unexpected context type", Toast.LENGTH_SHORT).show()

            }

        }

    }

    override fun getItemCount(): Int {
        return name.size
    }
}