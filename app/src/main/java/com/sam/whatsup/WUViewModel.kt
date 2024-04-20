package com.sam.whatsup

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects
import com.google.firebase.storage.FirebaseStorage
import com.sam.whatsup.data.CHATS
import com.sam.whatsup.data.ChatData
import com.sam.whatsup.data.ChatUser
import com.sam.whatsup.data.Event
import com.sam.whatsup.data.MESSAGES
import com.sam.whatsup.data.Message
import com.sam.whatsup.data.USER_NODE
import com.sam.whatsup.data.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Calendar
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
    val eventMutableState = mutableStateOf<Event<String>?>(null)
    var signIn = mutableStateOf(false)
    val userData = mutableStateOf<UserData?>(null)
    val chats = mutableStateOf<List<ChatData>>(listOf())
    val chatMessages = mutableStateOf<List<Message>>(listOf())
    val inProgressChatMessage = mutableStateOf(false)
    var currentChatMessageListener: ListenerRegistration? = null

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

    fun createOrUpdateProfile(
        name: String? = null,
        number: String? = null,
        imageUrl: String? = null
    ) {

        val uid = auth.currentUser?.uid
        val userData = UserData(
            userId = uid,
            name = name ?: userData.value?.name,
            number = number ?: userData.value?.number,
            imageUrl = imageUrl ?: userData.value?.imageUrl
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
                populateChats()
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
            createOrUpdateProfile(imageUrl = it.toString())
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

        }.addOnFailureListener {
            handleException(it)
        }

    }

    fun logout() {
        auth.signOut()
        signIn.value = false
        userData.value = null
        depopulateMessages()
        currentChatMessageListener = null
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

    }

    private fun populateChats() {

        inProgressChats.value = true
        db.collection(CHATS).where(
            Filter.or(
                Filter.equalTo("user1.userId", userData.value?.userId),
                Filter.equalTo("user2.userId", userData.value?.userId)
            )
        ).addSnapshotListener { value, error ->
            if (error != null) {

                handleException(error)

            }
            if (value != null) {
                chats.value = value.documents.mapNotNull {
                    it.toObject<ChatData>()
                }
                inProgressChats.value = false
            }
        }

    }

    fun onSendReply(chatID: String, message: String) {

        val time = Calendar.getInstance().time.toString()
        val msg = Message(userData.value?.userId)
        db.collection(CHATS).document(chatID).collection(MESSAGES).document().set(message)

    }

    fun populateMessages(chatID: String) {
        inProgressChatMessage.value = true
        currentChatMessageListener = db.collection(CHATS).document().collection(MESSAGES)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    handleException(error)
                }
                if (value != null) {
                    chatMessages.value = value.documents.mapNotNull {
                        it.toObject<Message>()
                    }.sortedBy { it.timestamp }
                    inProgressChatMessage.value = false
                }
            }
    }

    fun depopulateMessages() {
        chatMessages.value = listOf()
        currentChatMessageListener = null
    }

}