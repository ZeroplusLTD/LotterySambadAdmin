package com.lottery.sambad.admin.ui

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.ktx.Firebase
import com.lottery.sambad.admin.database.MyApi


class MyApplication: Application() {

    lateinit var firebaseAnalytics: FirebaseAnalytics
    val myApi by lazy { MyApi.invoke() }



    override fun onCreate() {
        super.onCreate()


        FirebaseApp.initializeApp(this)
        firebaseAnalytics = Firebase.analytics
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)




    }






}