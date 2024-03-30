package com.sam.whatsup.data

data class UserData(

    var userId: String? = "",
    var name: String? = "",
    var phone: String? = "",
    var imageUrl: String? = "",

    ) {

    fun toMap() = mapOf(
        "userId" to userId,
        "name" to name,
        "phone" to phone,
        "imageUrl" to imageUrl
    )

}