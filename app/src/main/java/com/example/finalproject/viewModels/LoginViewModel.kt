package com.example.finalproject.viewModels

import androidx.lifecycle.ViewModel
import com.example.finalproject.repositories.UserRepository
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel : ViewModel() {
    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    fun login(mail: String, password: String): Boolean {
        var shouldLogin = false
        mAuth.signInWithEmailAndPassword(mail, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    UserRepository.instance.setUserId(task.result.user!!.uid)
                    shouldLogin = true
                }
            }

        return shouldLogin
    }

    fun isConnected(): Boolean {
        val currentUser = mAuth!!.currentUser
        if (currentUser != null) {
            UserRepository.instance.setUserId(currentUser.uid)
            return true
        }

        return false
    }
}
