package com.sam.whatsup

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class WUViewModel @Inject constructor(
    private val auth: FirebaseAuth
) : ViewModel() {

    init {

    }

    fun signUp(name: String, phone: String, email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if(it.isSuccessful){
                Log.d("TAG", "Sign-Up Successful")
            }
        }

    }

}