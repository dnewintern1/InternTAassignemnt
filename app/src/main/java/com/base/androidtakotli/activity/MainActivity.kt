package com.base.androidtakotli.activity

import android.os.Bundle
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import com.base.androidtakotli.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = "My Toolbar"
        supportActionBar?.subtitle = "Subtitle"

        val fragmentA = AvailableWorkshops()
        val fragmentB = Dashboard()
        val fragmentC = Signup()

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_login, fragmentB)
            .commit()

//        if (savedInstanceState == null) {
//            supportFragmentManager.commit {
//                setReorderingAllowed(true)
//                add<AvailableWorkshops>(R.id.fragment_available_workshops)
//            }
//        }

    }
}

