package com.example.finalproject.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.finalproject.databinding.ActivityLoginBinding
import com.example.finalproject.repositories.UserRepository
import com.example.finalproject.viewModels.LoginViewModel
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var viewModel: LoginViewModel
    private var binding: ActivityLoginBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding!!.getRoot())
        onNotSingedIn()
        onLogin()
        binding!!.loginProgressBar.visibility = View.GONE
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        if (viewModel.isConnected()) {
            changeActivity(MainActivity::class.java)
        }
    }


    private fun onNotSingedIn() {
        binding!!.signinTv.setOnClickListener {
            changeActivity(
                SigninActivity::class.java
            )
        }
    }

    private fun onLogin() {
        binding!!.loginBtn.setOnClickListener {
            binding!!.loginProgressBar.visibility = android.view.View.VISIBLE
            val mail: String = binding!!.mailTp.text.toString()
            val password: String = binding!!.passwordTp.text.toString()
            if (TextUtils.isEmpty(mail)) {
                Toast.makeText(this@LoginActivity, "enter mail", Toast.LENGTH_SHORT)
                    .show()
            } else if (TextUtils.isEmpty(password)) {
                Toast.makeText(this@LoginActivity, "enter password", Toast.LENGTH_SHORT)
                    .show()
            } else {
                if (viewModel.login(mail, password)) {
                    changeActivity(MainActivity::class.java)
                } else {
                    Toast.makeText(
                        this@LoginActivity, "can not log you in.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun changeActivity(activityClass: Class<*>) {
        val intent = Intent(this@LoginActivity, activityClass)
        startActivity(intent)
    }
}