package com.base.androidtakotli.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.base.androidtakotli.R

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


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