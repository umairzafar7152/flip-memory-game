package com.chumairzafar.flipmemory

import android.content.*
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.os.PowerManager
import com.chumairzafar.flipmemory.R.drawable.*
import com.ramotion.cardslider.CardSliderLayoutManager
import com.ramotion.cardslider.CardSnapHelper
import kotlinx.android.synthetic.main.activity_two_player_selection.*

class TwoPlayerSelectionActivity : AppCompatActivity() {
    private lateinit var mPreferences: SharedPreferences
    private var listTitles = arrayOf("fruits", "flags", "animals", "shapes", "tools")
    private var listImages = arrayOf(fruits, flags, animals, ic_paint, ic_tool)
    private lateinit var myAdapter: CustomRecyclerAdapter
    private var mIsBound = false
    private var mService: MusicService? = null
    private var turnBtnSoundOn: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_two_player_selection)

        mPreferences = applicationContext.getSharedPreferences(sharedPrefsFile, MODE_PRIVATE)
        if(mPreferences.getBoolean(MUSIC_KEY, true)) {
            doBindService()
            startService(Intent(this, MusicService::class.java))
        }
        turnBtnSoundOn = mPreferences.getBoolean(SOUND_KEY, true)

        val layoutManager = CardSliderLayoutManager(this)
        itemListRecyclerView.layoutManager = layoutManager
        CardSnapHelper().attachToRecyclerView(itemListRecyclerView)
        myAdapter = CustomRecyclerAdapter(listTitles, listImages)
        itemListRecyclerView.adapter = myAdapter

        myAdapter.onItemClick = {
            if(turnBtnSoundOn!!) {
                playButtonClickSound()
            }
            val myIntent = Intent(this, TwoPlayerActivity::class.java)
            val itemSelected = listTitles[it]
            myIntent.putExtra("type", itemSelected)
            startActivity(myIntent)

        }
    }

    override fun onPause() {
        mPreferences = getSharedPreferences(sharedPrefsFile, MODE_PRIVATE)
        if(mPreferences.getBoolean(MUSIC_KEY, true)) {
            doUnbindService()
            stopService(Intent(this, MusicService::class.java))
        }
        val pm = getSystemService(Context.POWER_SERVICE) as PowerManager
        val isScreenOn = pm.isInteractive
        if (!isScreenOn) {
            if (mPreferences.getBoolean(MUSIC_KEY, true)) {
                doUnbindService()
                stopService(Intent(this, MusicService::class.java))
            }
        }
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        mPreferences = getSharedPreferences(sharedPrefsFile, MODE_PRIVATE)
        if(mPreferences.getBoolean(MUSIC_KEY, true)) {
            doBindService()
            startService(Intent(this, MusicService::class.java))
        }
        if(mPreferences.getBoolean(SOUND_KEY, true))
            turnBtnSoundOn = true
    }

    override fun onDestroy() {
        doUnbindService()
        stopService(Intent(this, MusicService::class.java))
        super.onDestroy()
    }

    override fun onBackPressed() {
        val myIntent = Intent(this, MainActivity::class.java)
        myIntent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
        myIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        startActivity(myIntent)
        finish()
    }

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(p0: ComponentName?) {
            mService = null
        }

        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            mService = (p1 as MusicService.ServiceBinder).service
        }
    }

    private fun doBindService() {
        if(!mIsBound) {
            bindService(
                Intent(this, MusicService::class.java),
                serviceConnection, Context.BIND_AUTO_CREATE
            )
        }
        mIsBound = true
    }
    private fun doUnbindService() {
        if(mIsBound) {
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
