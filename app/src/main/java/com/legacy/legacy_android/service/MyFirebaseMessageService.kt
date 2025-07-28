package com.legacy.legacy_android.service

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessageService: FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        //새로운 token이 생성될때마다 호출되는 callback
        super.onNewToken(token)
        Log.d("MyFirebaseMessageService", "Refreshed token: $token")

        // Foreground에서 Push Service를 받기 위해 Notification 설정
//        override fun onMessageReceived(remoteMessage: RemoteMessage) {
//        }
    }
}