package com.sam.whatsup

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.events.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WUViewModel @Inject constructor(
    private val auth: FirebaseAuth
) : ViewModel() {

    init {

    }

    var inProcess = mutableStateOf(false)
    val eventMutableState = mutableStateOf<Event<String>?>(null)
    var signIn = mutableStateOf(false)

    fun signUp(name: String, phone: String, email: String, password: String) {
        inProcess.value = true
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {

            if (it.isSuccessful) {

                signIn.value = true
                createOrUpdateProfile(name, phone)

            } else {
                handleException(it.exception, customMessage = "Sign-Up Failed")
            }
        }

    }

    fun createOrUpdateProfile(
        name: String? = null,
        phone: String? = null,
        imageurl: String? = null
    ) {

        var uid = auth.currentUser?.uid
//        val userData =

    }

    fun handleException(exception: Exception? = null, customMessage: String = "") {
        Log.e("WhatsUp", "WhatsUp", exception)
        exception?.printStackTrace()
        val errorMsg = exception?.localizedMessage ?: ""
        val message = if (customMessage.isNullOrEmpty()) errorMsg else customMessage

//        eventMutableState.value =

        inProcess.value = false
    }

}