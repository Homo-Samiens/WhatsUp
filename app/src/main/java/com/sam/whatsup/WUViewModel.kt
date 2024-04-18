package com.sam.whatsup

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
<<<<<<< HEAD
import com.google.firebase.firestore.toObjects
import com.google.firebase.storage.FirebaseStorage
import com.sam.whatsup.data.CHATS
import com.sam.whatsup.data.ChatData
import com.sam.whatsup.data.ChatUser
import com.sam.whatsup.data.Event
=======
import com.google.firebase.storage.FirebaseStorage
>>>>>>> eb7365fa177cdbf41fdb606ad830e87d23d670ba
import com.sam.whatsup.data.USER_NODE
import com.sam.whatsup.data.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class WUViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore,
    private val storage: FirebaseStorage

) : ViewModel() {

    var inProgress = mutableStateOf(false)
    var inProgressChats = mutableStateOf(false)
    private val eventMutableState = mutableStateOf<Event<String>?>(null)
    var signIn = mutableStateOf(false)
    val userData = mutableStateOf<UserData?>(null)
<<<<<<< HEAD
    val chats = mutableStateOf<List<ChatData>>(listOf())
=======
>>>>>>> eb7365fa177cdbf41fdb606ad830e87d23d670ba

    init {

        val currentUser = auth.currentUser
        signIn.value = currentUser != null
        currentUser?.uid?.let {
            getUserData(it)
        }

    }

    fun signUp(name: String, phone: String, email: String, password: String) {
        inProgress.value = true

        if (name.isEmpty() or phone.isEmpty() or email.isEmpty() or password.isEmpty()) {
            handleException(customMessage = "Please Fill All Fields")
            return
        }

        inProgress.value = true
        db.collection(USER_NODE).whereEqualTo("phone", phone).get().addOnSuccessListener {

            if (it.isEmpty) {
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
            } else {
                handleException(customMessage = "Number Already Exists")
                inProgress.value = false
            }

        }

    }

    private fun handleException(exception: Exception? = null, customMessage: String = "") {
        Log.e("WhatsUp", "SignUp-Crashed", exception)
        exception?.printStackTrace()
        val errorMsg = exception?.localizedMessage ?: ""
        val message = customMessage.ifEmpty { errorMsg }

        eventMutableState.value = Event(message)

        inProgress.value = false
    }

<<<<<<< HEAD
    fun createOrUpdateProfile(
        name: String? = null, number: String? = null, imageurl: String? = null
=======
    private fun createOrUpdateProfile(
        name: String? = null, phone: String? = null, imageurl: String? = null
>>>>>>> eb7365fa177cdbf41fdb606ad830e87d23d670ba
    ) {

        val uid = auth.currentUser?.uid
        val userData = UserData(
            userId = uid,
            name = name ?: userData.value?.name,
            number = number ?: userData.value?.number,
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
                    getUserData(uid)


                }

            }.addOnFailureListener {
                handleException(it, "Cannot Retrieve User")
            }
        }

    }

    private fun getUserData(uid: String) {
        inProgress.value = true
        db.collection(USER_NODE).document(uid).addSnapshotListener { value, error ->
            if (error != null) {
                handleException(error, "Cannot Retrieve User")
            }
            if (value != null) {
                val user = value.toObject<UserData>()
                userData.value = user
                inProgress.value = false
            }
        }
    }

    fun logIn(email: String, password: String) {

        if (email.isEmpty() or password.isEmpty()) {
            handleException(customMessage = "Please Fill All Fields")
            return
        } else {
            inProgress.value = true
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {

                    signIn.value = true
                    inProgress.value = false
                    auth.currentUser?.uid?.let {
                        getUserData(it)
                    }

                } else {
                    handleException(it.exception, "Log-In Failed")
                }
            }
        }

    }

    fun uploadDP(uri: Uri) {

        uploadImage(uri) {
            createOrUpdateProfile(imageurl = it.toString())
        }

    }

    private fun uploadImage(uri: Uri, onSuccess: (Uri) -> Unit) {

        inProgress.value = true
        val storageRef = storage.reference
        val uuid = UUID.randomUUID()
        val imageRef = storageRef.child("images/$uuid")
        val uploadTask = imageRef.putFile(uri)
        uploadTask.addOnSuccessListener {

            val result = it.metadata?.reference?.downloadUrl

            result?.addOnSuccessListener(onSuccess)
            inProgress.value = false

<<<<<<< HEAD
        }.addOnFailureListener {
            handleException(it)
        }

    }

    fun logout() {
        auth.signOut()
        signIn.value = false
        userData.value = null
        eventMutableState.value = Event("Logged Out")
    }

    fun onAddChat(number: String) {

        if (number.isEmpty() or !number.isDigitsOnly()) {
            handleException(customMessage = "Number must be Digits Only!")
        } else {
            db.collection(CHATS).where(
                Filter.or(
                    Filter.and(
                        Filter.equalTo("user!.number", number),
                        Filter.equalTo("user2.number", userData.value?.number)
                    ), Filter.and(
                        Filter.equalTo("user1.number", userData.value?.number),
                        Filter.equalTo("user2.number", number)
                    )
                )
            ).get().addOnSuccessListener {
                if (it.isEmpty) {
                    db.collection(USER_NODE).whereEqualTo("number", number).get()
                        .addOnSuccessListener {

                            if (it.isEmpty) {
                                handleException(customMessage = "Number not Found!")
                            } else {

                                val chatPartner = it.toObjects<UserData>()[0]
                                val id = db.collection(CHATS).document().id
                                val chat = ChatData(
                                    chatId = id, ChatUser(
                                        userData.value?.userId,
                                        userData.value?.name,
                                        userData.value?.imageUrl,
                                        userData.value?.number
                                    ), ChatUser(
                                        chatPartner.userId,
                                        chatPartner.name,
                                        chatPartner.imageUrl,
                                        chatPartner.number
                                    )

                                )

                                db.collection(CHATS).document(id).set(chat)

                            }

                        }
                        .addOnFailureListener {
                            handleException(it)
                        }

                } else {
                    handleException(customMessage = "Chat Already Exists")
                }
            }
        }
=======
        }
            .addOnFailureListener {
                handleException(it)
            }
>>>>>>> eb7365fa177cdbf41fdb606ad830e87d23d670ba

    }

}