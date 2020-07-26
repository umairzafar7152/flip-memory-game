package com.chumairzafar.flipmemory

import android.app.ActionBar
import android.app.Dialog
import android.content.*
import android.media.MediaPlayer
import android.os.Bundle
import android.os.IBinder
import android.os.PowerManager
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import com.chumairzafar.flipmemory.MusicService.ServiceBinder
import com.github.zagum.switchicon.SwitchIconView
import kotlinx.android.synthetic.main.activity_single_player.*

class SinglePlayerActivity : FragmentActivity(), SinglePlayerDataPasser {
    private lateinit var mPreferences: SharedPreferences
    private var pLevel = 1
    private var playerCurrentScore = 0
    private lateinit var typeOfImages: String
    private var mIsBound = false
    private var mService: MusicService? = null
    private var turnBtnSoundOn: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_player)

        //  get type from parent activity
        val myIntent = intent
        typeOfImages = myIntent.getStringExtra("type")!!

        //  send bundle to child fragment
        val bundle = Bundle()
        bundle.putString("type", typeOfImages)

        mPreferences = applicationContext.getSharedPreferences(sharedPrefsFile, MODE_PRIVATE)
        if(mPreferences.getBoolean(MUSIC_KEY, true)) {
            doBindService()
            startService(Intent(this, MusicService::class.java))
        }
        turnBtnSoundOn = mPreferences.getBoolean(SOUND_KEY, true)

        replay_button.setOnClickListener {
            if(turnBtnSoundOn!!) {
                playButtonClickSound()
            }
            showQuitDialog(true)
        }

        pause_button.setOnClickListener {
            if(turnBtnSoundOn!!) {
                playButtonClickSound()
            }
            showPauseDialog(mPreferences.getBoolean(SOUND_KEY, true), mPreferences.getBoolean(MUSIC_KEY, true))
        }

        //  inflate with first fragment
        val transaction = supportFragmentManager.beginTransaction()
        transaction.addToBackStack(null)
        val fragObject = SinglePlayerFragment22()
        //  passing bundle to fragment
        fragObject.arguments = bundle
        transaction.add(R.id.singlePlayerFrag, fragObject)
        transaction.commit()
    }

    override fun isGameOver(flag: Boolean) {
        val previousScore = mPreferences.getInt(HIGH_SCORE_KEY, 0)
        if(previousScore < playerCurrentScore) {
            val prefEditor = mPreferences.edit()
            prefEditor.putInt(HIGH_SCORE_KEY, playerCurrentScore)
            prefEditor.apply()
            showGameOverDialog(true)
        } else {
            showGameOverDialog(false)
        }

    }

    override fun onDataPass(
        pFragment: String,
        pAttempts: Int,
        pLevelCompleted: Boolean
    ) {
        //  bundle to pass in fragments
        val bundle = Bundle()
        bundle.putString("type", typeOfImages)

        attempts.text = pAttempts.toString()
        if (pLevelCompleted) {
            playerCurrentScore = Integer.parseInt(pScore.text.toString()) + pAttempts + pLevel
            pScore.text = playerCurrentScore.toString()
            val fragManager = supportFragmentManager
            val fragmentTransaction = fragManager.beginTransaction()
            fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right)
            fragmentTransaction.addToBackStack(null)
            if (pFragment == "frag22") {
                val flag = (0..1).random()
                if(flag == 0) {
                    val fragObject = SinglePlayerFragment23()
                    fragObject.arguments = bundle
                    fragmentTransaction.replace(R.id.singlePlayerFrag,fragObject)
                } else {
                    val fragObject = SinglePlayerFragment32()
                    fragObject.arguments = bundle
                    fragmentTransaction.replace(R.id.singlePlayerFrag,fragObject)
                }
            } else if(pFragment == "frag23") {
                val flag = (0..1).random()
                if(flag == 0) {
                    val fragObject = SinglePlayerFragment32()
                    fragObject.arguments = bundle
                    fragmentTransaction.replace(R.id.singlePlayerFrag,fragObject)
                } else {
                    val fragObject = SinglePlayerFragment24()
                    fragObject.arguments = bundle
                    fragmentTransaction.replace(R.id.singlePlayerFrag,fragObject)
                }
            } else if(pFragment == "frag24") {
                val flag = (0..1).random()
                if(flag == 0) {
                    val fragObject = SinglePlayerFragment42()
                    fragObject.arguments = bundle
                    fragmentTransaction.replace(R.id.singlePlayerFrag,fragObject)
                } else {
                    val fragObject = SinglePlayerFragment43()
                    fragObject.arguments = bundle
                    fragmentTransaction.replace(R.id.singlePlayerFrag,fragObject)
                }
            } else if(pFragment == "frag32") {
                val flag = (0..1).random()
                if(flag == 0) {
                    val fragObject = SinglePlayerFragment23()
                    fragObject.arguments = bundle
                    fragmentTransaction.replace(R.id.singlePlayerFrag,fragObject)
                } else {
                    val fragObject = SinglePlayerFragment24()
                    fragObject.arguments = bundle
                    fragmentTransaction.replace(R.id.singlePlayerFrag,fragObject)
                }
            } else if(pFragment == "frag34") {
                val flag = (0..1).random()
                if(flag == 0) {
                    val fragObject = SinglePlayerFragment34()
                    fragObject.arguments = bundle
                    fragmentTransaction.replace(R.id.singlePlayerFrag,fragObject)
                } else {
                    val fragObject = SinglePlayerFragment44()
                    fragObject.arguments = bundle
                    fragmentTransaction.replace(R.id.singlePlayerFrag,fragObject)
                }
            } else if(pFragment == "frag42") {
                when ((0..2).random()) {
                    0 -> {
                        val fragObject = SinglePlayerFragment44()
                        fragObject.arguments = bundle
                        fragmentTransaction.replace(R.id.singlePlayerFrag,fragObject)
                    }
                    1 -> {
                        val fragObject = SinglePlayerFragment45()
                        fragObject.arguments = bundle
                        fragmentTransaction.replace(R.id.singlePlayerFrag,fragObject)
                    }
                    else -> {
                        val fragObject = SinglePlayerFragment54()
                        fragObject.arguments = bundle
                        fragmentTransaction.replace(R.id.singlePlayerFrag,fragObject)
                    }
                }
            } else if(pFragment == "frag43") {
                when ((0..2).random()) {
                    0 -> {
                        val fragObject = SinglePlayerFragment44()
                        fragObject.arguments = bundle
                        fragmentTransaction.replace(R.id.singlePlayerFrag,fragObject)
                    }
                    1 -> {
                        val fragObject = SinglePlayerFragment45()
                        fragObject.arguments = bundle
                        fragmentTransaction.replace(R.id.singlePlayerFrag,fragObject)
                    }
                    else -> {
                        val fragObject = SinglePlayerFragment54()
                        fragObject.arguments = bundle
                        fragmentTransaction.replace(R.id.singlePlayerFrag,fragObject)
                    }
                }
            } else if(pFragment == "frag44") {
                when ((0..1).random()) {
                    0 -> {
                        val fragObject = SinglePlayerFragment45()
                        fragObject.arguments = bundle
                        fragmentTransaction.replace(R.id.singlePlayerFrag,fragObject)
                    }
                    1 -> {
                        val fragObject = SinglePlayerFragment54()
                        fragObject.arguments = bundle
                        fragmentTransaction.replace(R.id.singlePlayerFrag,fragObject)
                    }
                }
            } else if(pFragment == "frag45") {
                when ((0..1).random()) {
                    0 -> {
                        val fragObject = SinglePlayerFragment54()
                        fragObject.arguments = bundle
                        fragmentTransaction.replace(R.id.singlePlayerFrag,fragObject)
                    }
                    1 -> {
                        val fragObject = SinglePlayerFragment64()
                        fragObject.arguments = bundle
                        fragmentTransaction.replace(R.id.singlePlayerFrag,fragObject)
                    }
                }
            } else if(pFragment == "frag54") {
                when ((0..1).random()) {
                    0 -> {
                        val fragObject = SinglePlayerFragment54()
                        fragObject.arguments = bundle
                        fragmentTransaction.replace(R.id.singlePlayerFrag,fragObject)
                    }
                    1 -> {
                        val fragObject = SinglePlayerFragment64()
                        fragObject.arguments = bundle
                        fragmentTransaction.replace(R.id.singlePlayerFrag,fragObject)
                    }
                }
            } else if(pFragment == "frag64") {
                when ((0..1).random()) {
                    0 -> {
                        val fragObject = SinglePlayerFragment66()
                        fragObject.arguments = bundle
                        fragmentTransaction.replace(R.id.singlePlayerFrag,fragObject)
                    }
                    1 -> {
                        val fragObject = SinglePlayerFragment74()
                        fragObject.arguments = bundle
                        fragmentTransaction.replace(R.id.singlePlayerFrag,fragObject)
                    }
                }
            } else if(pFragment == "frag66") {
                when ((0..1).random()) {
                    0 -> {
                        val fragObject = SinglePlayerFragment74()
                        fragObject.arguments = bundle
                        fragmentTransaction.replace(R.id.singlePlayerFrag,fragObject)
                    }
                    1 -> {
                        val fragObject = SinglePlayerFragment76()
                        fragObject.arguments = bundle
                        fragmentTransaction.replace(R.id.singlePlayerFrag,fragObject)
                    }
                }
            } else if(pFragment == "frag76") {
                when ((0..2).random()) {
                    0 -> {
                        val fragObject = SinglePlayerFragment22()
                        fragObject.arguments = bundle
                        fragmentTransaction.replace(R.id.singlePlayerFrag,fragObject)
                    }
                    1 -> {
                        val fragObject = SinglePlayerFragment23()
                        fragObject.arguments = bundle
                        fragmentTransaction.replace(R.id.singlePlayerFrag,fragObject)
                    }
                    2 -> {
                        val fragObject = SinglePlayerFragment32()
                        fragObject.arguments = bundle
                        fragmentTransaction.replace(R.id.singlePlayerFrag,fragObject)
                    }
                }
            }
            fragmentTransaction.commit()
            pLevel++
        }
    }

    override fun onPause() {
        super.onPause()
        mPreferences = getSharedPreferences(sharedPrefsFile, MODE_PRIVATE)
        if(mPreferences.getBoolean(MUSIC_KEY, true)) {
            doUnbindService()
            stopService(Intent(this, MusicService::class.java))
        }
        val pm = getSystemService(POWER_SERVICE) as PowerManager
        val isScreenOn = pm.isInteractive
        if (!isScreenOn) {
            if (mPreferences.getBoolean(MUSIC_KEY, true)) {
                doUnbindService()
                stopService(Intent(this, MusicService::class.java))
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mPreferences = getSharedPreferences(sharedPrefsFile, MODE_PRIVATE)
        if(mPreferences.getBoolean(MUSIC_KEY, true)) {
            doBindService()
            startService(Intent(this, MusicService::class.java))
        }
        if(mPreferences.getBoolean(SOUND_KEY, true)) {
            turnBtnSoundOn = true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        doUnbindService()
        stopService(Intent(this, MusicService::class.java))
    }

    override fun onBackPressed() {
        showQuitDialog(false)
    }

    private fun showPauseDialog(isSoundEnabled: Boolean, isMusicEnabled: Boolean) {
        var soundFlag = isSoundEnabled
        var musicFlag = isMusicEnabled
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.pause_dialog)
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        val window = dialog.window
        window!!.setGravity(Gravity.CENTER)
        window.attributes.windowAnimations = R.style.winnerAnimation

        val btnContinue = dialog.findViewById<View>(R.id.buttonContinue)
        btnContinue.setOnClickListener {
            dialog.dismiss()
        }
        val btnQuit = dialog.findViewById<View>(R.id.buttonQuit)
        btnQuit.setOnClickListener {
            dialog.dismiss()
            val myIntent = Intent(this, MainActivity::class.java)
            myIntent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
            myIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            startActivity(myIntent)
        }
        val soundButton = dialog.findViewById<SwitchIconView>(R.id.soundSwitch)
        soundButton.setIconEnabled(soundFlag)
        soundButton.setOnClickListener {
            soundButton.switchState(animate = true)
            soundFlag = !soundFlag
            turnBtnSoundOn = soundFlag
            val prefEditor = mPreferences.edit()
            prefEditor.putBoolean(SOUND_KEY, soundFlag).apply()
        }
        val musicButton = dialog.findViewById<SwitchIconView>(R.id.musicSwitch)
        musicButton.setIconEnabled(musicFlag)
        musicButton.setOnClickListener {
            musicButton.switchState(animate = true)
            musicFlag = !musicFlag
            val prefEditor = mPreferences.edit()
            if(musicFlag) {
                doBindService()
                startService(Intent(this, MusicService::class.java))
                prefEditor.putBoolean(MUSIC_KEY, musicFlag).apply()
            } else {
                doUnbindService()
                stopService(Intent(this, MusicService::class.java))
                prefEditor.putBoolean(MUSIC_KEY, musicFlag).apply()
            }
        }

        dialog.setOnCancelListener {
            dialog.dismiss()
        }
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(true)
        window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT)
        dialog.show()
    }

    private fun showQuitDialog(replay: Boolean) {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.back_press_dialog)
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        val window = dialog.window
        window!!.setGravity(Gravity.CENTER)
        window.attributes.windowAnimations = R.style.winnerAnimation
        val askText = dialog.findViewById<TextView>(R.id.textAsk)
        if(replay) {
            askText.text = resources.getString(R.string.do_you_want_to_replay_txt)
        } else {
            askText.text = resources.getString(R.string.do_you_want_to_quit_txt)
        }
        val btnYes = dialog.findViewById<View>(R.id.buttonYes)
        btnYes.setOnClickListener {
            dialog.dismiss()
            if(replay) {
                val replayIntent = Intent(this, SinglePlayerSelectionActivity::class.java)
                replayIntent.putExtra("type", typeOfImages)
                replayIntent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
                replayIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                startActivity(replayIntent)
                finish()
            } else {
                val myIntent = Intent(this, MainActivity::class.java)
                myIntent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
                myIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                startActivity(myIntent)
                finish()
            }
        }

        val btnNo = dialog.findViewById<View>(R.id.buttonNo)
        btnNo.setOnClickListener {
            dialog.dismiss()
        }

        dialog.setOnCancelListener {
            if(replay) {
                val replayIntent = Intent(this, SinglePlayerSelectionActivity::class.java)
                replayIntent.putExtra("type", typeOfImages)
                replayIntent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
                replayIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                startActivity(replayIntent)
                finish()
            } else {
                val myIntent = Intent(this, MainActivity::class.java)
                myIntent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
                myIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                startActivity(myIntent)
                finish()
            }
        }
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(true)
        window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT)
        dialog.show()
    }

    private val serviceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, binder: IBinder) {
            mService = (binder as ServiceBinder).service
        }

        override fun onServiceDisconnected(name: ComponentName) {
            mService = null
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
    private fun showNewHighScoreDialog() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.high_score_dialog)
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        val window = dialog.window
        window!!.setGravity(Gravity.CENTER)
        window.attributes.windowAnimations = R.style.winnerAnimation

        val buttonCancel = dialog.findViewById<View>(R.id.btnCancel)
        buttonCancel.setOnClickListener {
            val myIntent = Intent(this, MainActivity::class.java)
            myIntent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
            myIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            startActivity(myIntent)
            finish()
        }
        val buttonPlayAgain = dialog.findViewById<View>(R.id.btnPlayAgain)
        buttonPlayAgain.setOnClickListener {
            dialog.dismiss()
            val myIntent = Intent(this, SinglePlayerSelectionActivity::class.java)
            myIntent.putExtra("key", typeOfImages)
            startActivity(myIntent)
        }
        dialog.setOnCancelListener {
            val myIntent = Intent(this, MainActivity::class.java)
            myIntent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
            myIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            startActivity(myIntent)
            finish()
        }
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(true)
        window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT)
        dialog.show()
    }

    private fun showGameOverDialog(isNewHighScore: Boolean) {
        val dialog = Dialog(this@SinglePlayerActivity)
        dialog.setContentView(R.layout.sp_game_over_dialog)
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        val window = dialog.window
        window!!.setGravity(Gravity.CENTER)
        window.attributes.windowAnimations = R.style.winnerAnimation

        val cancel = dialog.findViewById<View>(R.id.buttonCan)
        cancel.setOnClickListener {
            dialog.dismiss()
            if(isNewHighScore) {
                showNewHighScoreDialog()
            } else {
                val myIntent = Intent(this.applicationContext, MainActivity::class.java)
                myIntent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
                myIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                startActivity(myIntent)
                finish()
            }
        }

        val playAgain = dialog.findViewById<View>(R.id.buttonPlay)
        playAgain.setOnClickListener {
            dialog.dismiss()
            if(isNewHighScore) {
                showNewHighScoreDialog()
            } else {
                val myIntent = Intent(this.applicationContext, SinglePlayerSelectionActivity::class.java)
                myIntent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
                myIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                startActivity(myIntent)
                finish()
            }
        }

        dialog.setOnCancelListener {
            dialog.dismiss()
            val myIntent = Intent(applicationContext, MainActivity::class.java)
            myIntent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
            myIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            startActivity(myIntent)
            finish()
        }
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(true)
        window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT)
        dialog.show()
    }


    companion object {
        private const val sharedPrefsFile = "com.chumairzafar.flipmemory.mysharedprefs"
        private const val SOUND_KEY = "sound_key"
        private const val MUSIC_KEY = "music_key"
        private const val HIGH_SCORE_KEY = "score_key"
    }
}
