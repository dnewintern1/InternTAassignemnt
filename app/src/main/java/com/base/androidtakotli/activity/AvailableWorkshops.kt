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
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.base.androidtakotli.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AvailableWorkshops : Fragment() {

    private val uiScope = CoroutineScope(Dispatchers.IO)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_available_workshops, container, false)
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
        val recyclerView = itemView.findViewById<RecyclerView>(R.id.recycler_view1)
        uiScope.launch {
            WorkshopRepository.getInstance(requireContext())!!.getAppDatabase().workshopDao()
                .deleteAll()
        }
        recyclerView.apply {
            // set a LinearLayoutManager to handle Android
            // RecyclerView behavior
            layoutManager = LinearLayoutManager(activity)
            // set the custom adapter to the RecyclerView
            adapter = RecyclerViewAdapter()
        }
    }
}