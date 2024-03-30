package com.sam.whatsup

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
class FBModule {

    @Provides
    fun providesAuth(): FirebaseAuth = Firebase.auth

    @Provides
    fun provideFireStore(): FirebaseFirestore = Firebase.firestore

}