package com.chumairzafar.flipmemory

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.widget.Toast


class MusicService : Service(), MediaPlayer.OnErrorListener {
    private val mBinder: IBinder = ServiceBinder()
    var mPlayer: MediaPlayer? = null

    inner class ServiceBinder : Binder() {
        val service: MusicService
            get() = this@MusicService
    }

    override fun onBind(arg0: Intent?): IBinder {
        return mBinder
    }

    override fun onCreate() {
        super.onCreate()
        mPlayer = MediaPlayer.create(this, R.raw.game_bg_music)
        mPlayer!!.setOnErrorListener(this)
        if (mPlayer != null) {
            mPlayer!!.isLooping = true
            mPlayer!!.setVolume(50f, 50f)
        }
        mPlayer!!.setOnErrorListener(object : MediaPlayer.OnErrorListener {
            override fun onError(mp: MediaPlayer, what: Int, extra: Int): Boolean {
                onError(mPlayer!!, what, extra)
                return true
            }
        })
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (mPlayer != null) {
            mPlayer!!.start()
        }
        return START_NOT_STICKY
    }

    private fun stopMusic() {
        if (mPlayer != null) {
            mPlayer!!.stop()
            mPlayer!!.release()
            mPlayer = null
        }
    }

    override fun onDestroy() {
        stopMusic()
        super.onDestroy()
    }

    override fun onError(mp: MediaPlayer, what: Int, extra: Int): Boolean {
        Toast.makeText(this, "Music player failed", Toast.LENGTH_SHORT).show()
        if (mPlayer != null) {
            try {
                mPlayer!!.stop()
                mPlayer!!.release()
            } finally {
                mPlayer = null
            }
        }
        return false
    }
}