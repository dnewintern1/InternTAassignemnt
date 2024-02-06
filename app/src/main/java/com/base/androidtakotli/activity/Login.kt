package com.base.androidtakotli.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
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
 * Use the [Login.newInstance] factory method to
 * create an instance of this fragment.
 */
class Login : Fragment() {

    private lateinit var buttonLogin: Button
    private lateinit var buttonSignUp: Button
    private lateinit var lUsername: EditText
    private lateinit var lPassword: EditText
    private lateinit var buttonSkip: TextView
    private lateinit var preferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

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
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        val imm =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        lUsername = view.findViewById(R.id.lUsername)
        lPassword = view.findViewById(R.id.lPass)
        buttonLogin = view.findViewById(R.id.lBtn)
        buttonSignUp = view.findViewById(R.id.rBtn)
        buttonSkip = view.findViewById(R.id.lskip)


        buttonLogin.setOnClickListener {
            val activity = it.context as AppCompatActivity
            val lUserName = lUsername.text.toString()
            val lPassWord = lPassword.text.toString()
            val username = preferences.getString("userName", null)
            val password = preferences.getString("password", null)
            val msg: String

            if (username.equals(lUserName) && password.equals(lPassWord)) {
                msg = "Login Successfully"
                activity.supportFragmentManager.commit {
                    replace<Dashboard>(R.id.fragment_dashboard)
                    setReorderingAllowed(true)
                    addToBackStack("dashboard") // name can be null
                }
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            } else {
                msg = "Login Failed"
            }

            Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
        }

        buttonSignUp.setOnClickListener {
            val activity = it.context as AppCompatActivity

            activity.supportFragmentManager.commit {
                replace<Signup>(R.id.fragment_signup)
                setReorderingAllowed(true)
                addToBackStack("signup") // name can be null
            }
        }

        buttonSkip.setOnClickListener {
            val intent = Intent(it.context, MainActivity::class.java)
            startActivity(intent)
        }



        return view
    }

}