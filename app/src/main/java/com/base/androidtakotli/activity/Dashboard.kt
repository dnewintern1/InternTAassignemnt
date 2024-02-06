package com.base.androidtakotli.activity

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.base.androidtakotli.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Dashboard : Fragment() {

    private val uiScope = CoroutineScope(Dispatchers.IO)
    private var name: ArrayList<String> = ArrayList()
    private var date: ArrayList<String> = ArrayList()
    private var btn: ArrayList<Boolean> = ArrayList()
    private var size = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        // Find the Toolbar by its ID
        val toolbar = view.findViewById<Toolbar>(R.id.dash_toolbar)

        // Set the Toolbar as the support ActionBar
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)

        // Set the title if needed
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Dashboard"

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.items, menu) // Inflate the menu resource file
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            R.id.home -> {
                val intent = Intent(requireContext(), MainActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.login -> {
                // Navigate to the Login fragment
                requireActivity().supportFragmentManager.commit {
                    replace<Login>(R.id.fragment_login) // Replace fragment_container with the ID of your fragment container
                    addToBackStack(null) // Add to back stack if needed
                }
                true
            }
            R.id.signup -> {
                // Navigate to the Signup fragment
                requireActivity().supportFragmentManager.commit {
                    replace<Signup>(R.id.fragment_signup) // Replace fragment_container with the ID of your fragment container
                    addToBackStack(null) // Add to back stack if needed
                }
                true
            }
            R.id.workshop -> {
                // Navigate to the Available Workshops fragment
                requireActivity().supportFragmentManager.commit {
                    replace<AvailableWorkshops>(R.id.fragment_available_workshops) // Replace fragment_container with the ID of your fragment container
                    addToBackStack(null) // Add to back stack if needed
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        val recyclerView = itemView.findViewById<RecyclerView>(R.id.recycler_view2)
        recyclerView.apply {
            // set a LinearLayoutManager to handle Android
            // RecyclerView behavior
            layoutManager = LinearLayoutManager(activity)
            fetchData()
            // set the custom adapter to the RecyclerView
            adapter = RVAdapter(name,date,btn)

            adapter?.notifyDataSetChanged()
        }
    }
    private fun fetchData() {
        uiScope.launch {
            val list: List<Workshop> =
                WorkshopRepository.getInstance(requireContext())!!.getAppDatabase().workshopDao()
                    .getAll()
            for (workshop in list) {
                if (workshop.button) {
                    name.add(workshop.name)
                    date.add(workshop.date)
                    btn.add(workshop.button)
                    size++
                }
            }
        }
//        Toast.makeText(requireContext(),"stored "+(size+1)+" new workshop", Toast.LENGTH_SHORT).show()
    }
}


