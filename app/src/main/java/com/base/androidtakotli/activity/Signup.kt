package com.base.androidtakotli.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.base.androidtakotli.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Signup.newInstance] factory method to
 * create an instance of this fragment.
 */
class Signup : Fragment() {

    private lateinit var rEmail: EditText
    private lateinit var rConfirmPassword: EditText
    private lateinit var buttonRegister: Button
    private lateinit var rUserName: EditText
    private lateinit var rPassword: EditText
    private lateinit var rbuttonSkip: TextView
    private lateinit var preferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private var username = ""
    private var password = ""
    private var email = ""
    private var cPassword = ""

    override fun onAttach(context: Context) {
        preferences = context.getSharedPreferences("userFile", Context.MODE_PRIVATE)
        editor = preferences.edit()
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_signup, container, false)

        rUserName = view.findViewById(R.id.rUsername)
        rPassword = view.findViewById(R.id.rPass)
        rEmail = view.findViewById(R.id.rEmail)
        rConfirmPassword = view.findViewById(R.id.rConPass)
        buttonRegister = view.findViewById(R.id.register)
        rbuttonSkip = view.findViewById(R.id.rskip)

        buttonRegister.setOnClickListener {
            val activity = it.context as AppCompatActivity
            val msg: String

            username = rUserName.text.toString()
            password = rPassword.text.toString()
            cPassword = rConfirmPassword.text.toString()
            email = rEmail.text.toString()

            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || cPassword.isEmpty()) {
                msg = "All fields are required"
            } else if (password != cPassword) {
                msg = "Password does not match"
            } else if (password.length < 7) {
                msg = "Password too short"
            } else {

                editor.putString("userName", username)
                editor.putString("password", password)
                editor.apply()

                msg = "Registered"

                activity.supportFragmentManager.popBackStack()
            }
            Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
        }

        rbuttonSkip.setOnClickListener {
            val intent = Intent(it.context, MainActivity::class.java)
            startActivity(intent)
        }

        return view
    }
}