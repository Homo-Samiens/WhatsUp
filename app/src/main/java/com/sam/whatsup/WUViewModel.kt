package com.sam.whatsup

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.sam.whatsup.data.USER_NODE
import com.sam.whatsup.data.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WUViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore

) : ViewModel() {

    init {

    }

    var inProgress = mutableStateOf(false)
    private val eventMutableState = mutableStateOf<com.sam.whatsup.data.Event<String>?>(null)
    private var signIn = mutableStateOf(false)
    private val userData = mutableStateOf<UserData?>(null)

    fun signUp(name: String, phone: String, email: String, password: String) {
        inProgress.value = true
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {

            if (it.isSuccessful) {

                inProgress.value = false

                Log.e("WhatsUp", "signUp: Successful")

                signIn.value = true

                createOrUpdateProfile(name, phone)

            } else {
                handleException(it.exception, customMessage = "Sign-Up Failed")
            }
        }

    }

    private fun handleException(exception: Exception? = null, customMessage: String = "") {
        Log.e("WhatsUp", "SignUp-Crashed", exception)
        exception?.printStackTrace()
        val errorMsg = exception?.localizedMessage ?: ""
        val message = customMessage.ifEmpty { errorMsg }

        eventMutableState.value = com.sam.whatsup.data.Event(message)

        inProgress.value = false
    }

    private fun createOrUpdateProfile(
        name: String? = null,
        phone: String? = null,
        imageurl: String? = null
    ) {

        val uid = auth.currentUser?.uid
        val userData = UserData(
            userId = uid,
            name = name ?: userData.value?.name,
            phone = phone ?: userData.value?.phone,
            imageUrl = imageurl ?: userData.value?.imageUrl
        )

        uid?.let {
            inProgress.value = true
            db.collection(USER_NODE).document(uid).get().addOnSuccessListener {

                if (it.exists()) {
                    //update
                } else {

                    db.collection(USER_NODE).document(uid).set(userData)
                    inProgress.value = false

                }

            }
                .addOnFailureListener {
                    handleException(it, "Cannot Retrieve User")
                }
        }

    }

}