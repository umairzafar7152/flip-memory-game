package com.chumairzafar.flipmemory

import android.app.ActionBar
import android.app.Dialog
import android.content.*
import android.media.MediaPlayer
import android.os.Bundle
import android.os.IBinder
import android.os.PowerManager
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.chumairzafar.flipmemory.MusicService.ServiceBinder
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.github.zagum.switchicon.SwitchIconView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var mPreferences: SharedPreferences
    private var mIsBound = false
    private var mService: MusicService? = null
    private var turnBtnSoundOn: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        YoYo.with(Techniques.Flash)
            .duration(2000)
            .repeat(-1)
            .playOn(findViewById(R.id.flipMemoryText))

        mPreferences = applicationContext.getSharedPreferences(sharedPrefsFile, MODE_PRIVATE)
        Log.d("MainActivity", "oncreate prefMusic ${mPreferences.getBoolean(MUSIC_KEY, true)}")
        if (mPreferences.getBoolean(MUSIC_KEY, true)) {
            doBindService()
            startService(Intent(this, MusicService::class.java))
        }
        turnBtnSoundOn = mPreferences.getBoolean(SOUND_KEY, true)
        Log.d("MainActivity", "oncreate prefSound $turnBtnSoundOn")
        //  text animation
        val anim = AnimationUtils.loadAnimation(this, R.anim.expand_in)
        anim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(p0: Animation?) {
            }

            override fun onAnimationEnd(p0: Animation?) {
                anim.start()
            }

            override fun onAnimationStart(p0: Animation?) {
                flipMemoryText.visibility = View.VISIBLE
            }
        })
        flipMemoryText.startAnimation(anim)

        settingBtn.setOnClickListener {
            if (turnBtnSoundOn!!) {
                playButtonClickSound()
            }
            showSettingsDialog(
                mPreferences.getBoolean(SOUND_KEY, true),
                mPreferences.getBoolean(MUSIC_KEY, true)
            )
        }

        singlePlayerCard.setOnClickListener {
            if (turnBtnSoundOn!!) {
                playButtonClickSound()
            }
            startActivity(Intent(this, SinglePlayerSelectionActivity::class.java))
        }

        twoPlayerCard.setOnClickListener {
            if (turnBtnSoundOn!!) {
                playButtonClickSound()
            }
            startActivity(Intent(this, TwoPlayerSelectionActivity::class.java))
        }

        shareBtn.setOnClickListener {
            if (turnBtnSoundOn!!) {
                playButtonClickSound()
            }
            val sharingIntent = Intent(Intent.ACTION_SEND)
            sharingIntent.type = "text/plain"
            val shareBody =
                "Application Link : https://play.google.com/store/apps/details?id=${this.packageName}"
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "App link")
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
            startActivity(Intent.createChooser(sharingIntent, "Share App Link Via :"))
        }
    }

    private fun showSettingsDialog(isSoundEnabled: Boolean, isMusicEnabled: Boolean) {
        var soundFlag = isSoundEnabled
        var musicFlag = isMusicEnabled

        Log.d("MainActivity", "ShowDialog prefSound $soundFlag")
        Log.d("MainActivity", "ShowDialog prefMusic $musicFlag")
        val dialog = Dialog(this@MainActivity)
        dialog.setContentView(R.layout.settings_preferences)
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        val window = dialog.window
        window!!.setGravity(Gravity.CENTER)
        window.attributes.windowAnimations = R.style.winnerAnimation
        val soundButton = dialog.findViewById<SwitchIconView>(R.id.soundSwitch)
        soundButton.setIconEnabled(soundFlag)
        soundButton.setOnClickListener {
            soundButton.switchState(animate = true)
            soundFlag = !soundFlag
            Log.d("MainActivity", "changed prefSound in dialog $soundFlag")
            turnBtnSoundOn = soundFlag
            val prefEditor = mPreferences.edit()
            prefEditor.putBoolean(SOUND_KEY, soundFlag).apply()
        }
        val musicButton = dialog.findViewById<SwitchIconView>(R.id.musicSwitch)
        musicButton.setIconEnabled(musicFlag)
        musicButton.setOnClickListener {
            musicButton.switchState(animate = true)
            musicFlag = !musicFlag
            Log.d("MainActivity", "changed prefMusic in dialog $musicFlag")
            val prefEditor = mPreferences.edit()
            if (musicFlag) {
                doBindService()
                startService(Intent(this@MainActivity, MusicService::class.java))
                prefEditor.putBoolean(MUSIC_KEY, musicFlag).apply()
            } else {
                doUnbindService()
                stopService(Intent(this@MainActivity, MusicService::class.java))
                prefEditor.putBoolean(MUSIC_KEY, musicFlag).apply()
            }
        }

        val cancel = dialog.findViewById<View>(R.id.btnCancel)
        cancel.setOnClickListener {
            dialog.dismiss()
        }
        dialog.setOnCancelListener {
            dialog.dismiss()
        }
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(true)
        window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT)
        dialog.show()
    }

    override fun onBackPressed() {
        finish()
    }

    override fun onPause() {
        mPreferences = getSharedPreferences(sharedPrefsFile, MODE_PRIVATE)
        if (mPreferences.getBoolean(MUSIC_KEY, true)) {
            doUnbindService()
            stopService(Intent(this@MainActivity, MusicService::class.java))
        }

        val pm = getSystemService(Context.POWER_SERVICE) as PowerManager
        val isScreenOn = pm.isInteractive
        if (!isScreenOn) {
            if (mPreferences.getBoolean(MUSIC_KEY, true)) {
                doUnbindService()
                stopService(Intent(this@MainActivity, MusicService::class.java))
            }
        }
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        mPreferences = applicationContext.getSharedPreferences(sharedPrefsFile, MODE_PRIVATE)
        if (mPreferences.getBoolean(MUSIC_KEY, true)) {
            doBindService()
            startService(Intent(this@MainActivity, MusicService::class.java))
        }
        if (mPreferences.getBoolean(SOUND_KEY, true)) {
            turnBtnSoundOn = true
        }
    }

    override fun onDestroy() {
        doUnbindService()
        stopService(Intent(this, MusicService::class.java))
        super.onDestroy()
    }

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(p0: ComponentName?) {
            mService = null
        }

        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            mService = (p1 as ServiceBinder).service
        }
    }

    private fun doBindService() {
        if (!mIsBound) {
            bindService(
                Intent(this, MusicService::class.java),
                serviceConnection, Context.BIND_AUTO_CREATE
            )
            mIsBound = true
        }
    }

    private fun doUnbindService() {
        if (mIsBound) {
            unbindService(serviceConnection)
            mIsBound = false
        }
    }

    private fun playButtonClickSound() {
        val mp = MediaPlayer.create(this, R.raw.game_bg_sound)
        mp.start()
        mp.setOnCompletionListener {
            mp.release()
        }
    }

    companion object {
        private const val sharedPrefsFile = "com.chumairzafar.flipmemory.mysharedprefs"
        private const val SOUND_KEY = "sound_key"
        private const val MUSIC_KEY = "music_key"
    }
}
