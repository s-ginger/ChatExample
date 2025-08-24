package com.example.chatexample.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log

class MyService : Service() {

    private val  binder = LocalBinder()
    private var threadStarted= false

    inner class LocalBinder : Binder() {
        fun getService(): MyService = this@MyService
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Тут логика фоновой работы (например, музыка, таймер, загрузка и т.д.)
        Log.d("MyService", "Сервис запущен")

        if (threadStarted == false) {
            Thread {
                threadStarted = true
                var counter = 0
                while (true) {
                    counter++
                    Thread.sleep(7000)
                    Log.d("MyService", "$counter")
                }
            }.start()
        }

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