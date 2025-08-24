package com.example.chatexample.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.chatexample.R

class MyService : Service() {

    private val  binder = LocalBinder()


    inner class LocalBinder : Binder() {
        fun getService(): MyService = this@MyService
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    override fun onCreate() {
        super.onCreate()
        startForegroundService()
    }

    private fun startForegroundService() {
        val notificationChannelId = "MY_CHANNEL_ID"
        val channelName = "My Foreground Service"

        val chan = NotificationChannel(
            notificationChannelId,
            channelName,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(chan)

        val notification: Notification = NotificationCompat.Builder(this, notificationChannelId)
            .setContentTitle("Сервис запущен")
            .setContentText("Работает в фоне")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()

        startForeground(1, notification)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Тут логика фоновой работы (например, музыка, таймер, загрузка и т.д.)
        Log.d("MyService", "Сервис запущен")
        return START_STICKY // если система убьёт сервис, он перезапустится
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MyService", "onDestroy()")
    }


    fun doSomething(): String {
        return "Hello from Service"
    }


}