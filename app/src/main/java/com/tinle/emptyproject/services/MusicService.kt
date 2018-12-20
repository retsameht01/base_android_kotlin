package com.tinle.emptyproject.services

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import com.tinle.emptyproject.R

class MusicService:Service() {
    private lateinit var mediaPlayer:MediaPlayer
    override fun onBind(p0: Intent?): IBinder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun onStartCommand(intent:Intent, flags:Int, startId:Int):Int {
        //Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
        mediaPlayer.start();
        return super.onStartCommand(intent, flags, startId);
    }

    override fun onCreate() {
        //Toast.makeText(this, "Service Created", Toast.LENGTH_LONG).show();
        mediaPlayer = MediaPlayer.create(this, R.layout.activity_customer_rewards)
        mediaPlayer.isLooping = false

    }


    override fun onDestroy() {
        //Toast.makeText(this, "Service Stopped", Toast.LENGTH_LONG).show();
        mediaPlayer.stop();
    }

}