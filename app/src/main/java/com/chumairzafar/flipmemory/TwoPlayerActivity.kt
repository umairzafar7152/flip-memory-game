package com.chumairzafar.flipmemory

import android.app.ActionBar
import android.app.Dialog
import android.content.*
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.PowerManager
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.chumairzafar.flipmemory.R.drawable.*
import com.github.zagum.switchicon.SwitchIconView
import kotlinx.android.synthetic.main.activity_two_player.*


class TwoPlayerActivity : AppCompatActivity() {
    private lateinit var mPreferences: SharedPreferences
    private var mIsBound = false
    private var mService: MusicService? = null
    private var scorePlayer1 = 0
    private var scorePlayer2 = 0
    private var player1Turn = true
    private var lastCard = -1
    private var winner = 0
    private lateinit var imagesList: MutableList<Pair<Int, Int>>
    private lateinit var images: MutableList<Int>
    private lateinit var imageViews: Array<ImageView>
    private lateinit var typeOfImages: String
    private var turnBtnSoundOn: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_two_player)

        mPreferences = applicationContext.getSharedPreferences(sharedPrefsFile, MODE_PRIVATE)
        if (mPreferences.getBoolean(MUSIC_KEY, true)) {
            doBindService()
            startService(Intent(this, MusicService::class.java))
        }
        turnBtnSoundOn = mPreferences.getBoolean(SOUND_KEY, true)

        isPlayer1Turn(player1Turn)

        val imagesList1: MutableList<Pair<Int, Int>> = mutableListOf(
            Pair(apple, apple),
            Pair(avocado, avocado),
            Pair(avocado1, avocado1),
            Pair(bananas, bananas),
            Pair(coconut, coconut),
            Pair(coconut1, coconut1),
            Pair(figfruit1, figfruit1),
            Pair(grapes, grapes),
            Pair(grapes1, grapes1),
            Pair(greenberry1, greenberry1),
            Pair(guava, guava),
            Pair(lemon, lemon),
            Pair(mangoes, mangoes),
            Pair(muskmelon1, muskmelon1),
            Pair(passionfruit1, passionfruit1),
            Pair(peach, peach),
            Pair(pomegranate1, pomegranate1),
            Pair(romanov, romanov),
            Pair(tropicalfruit1, tropicalfruit1),
            Pair(watermelon, watermelon),
            Pair(zitrone1, zitrone1),
            Pair(apricot, apricot),
            Pair(grapefruit1, grapefruit1),
            Pair(kiwi, kiwi),
            Pair(pear1, pear1),
            Pair(pineapple, pineapple)
        )

        val imagesList2: MutableList<Pair<Int, Int>> = mutableListOf(
            Pair(bd, bd), Pair(bi, bi), Pair(bl, bl),
            Pair(bn, bn), Pair(bo, bo), Pair(br, br), Pair(bs, bs), Pair(bv, bv), Pair(bw, bw),
            Pair(by, by), Pair(cf, cf), Pair(cg, cg), Pair(ch, ch), Pair(ci, ci), Pair(cl, cl),
            Pair(cm, cm), Pair(cn, cn), Pair(co, co), Pair(cu, cu), Pair(cw, cw), Pair(cz, cz),
            Pair(eg, eg), Pair(es, es), Pair(et, et), Pair(fi, fi), Pair(ga, ga), Pair(gb, gb),
            Pair(gd, gd), Pair(ge, ge), Pair(gh, gh), Pair(gl, gl), Pair(gm, gm), Pair(gn, gn),
            Pair(gq, gq), Pair(gs, gs), Pair(ind, ind), Pair(jp, jp), Pair(pk, pk), Pair(pl, pl)
        )

        val imageList3: MutableList<Pair<Int, Int>> = mutableListOf(
            Pair(adjustable_wrench, adjustable_wrench),
            Pair(alicate, alicate),
            Pair(arched_spade, arched_spade),
            Pair(automatic_mason, automatic_mason),
            Pair(barber_tools, barber_tools),
            Pair(blockmaker_engine, blockmaker_engine),
            Pair(blockmaker_plates, blockmaker_plates),
            Pair(bolt_cutter, bolt_cutter),
            Pair(cleaver, cleaver),
            Pair(clinic_thermometer, clinic_thermometer),
            Pair(colourhammer, colourhammer),
            Pair(construction_tool, construction_tool),
            Pair(fantasy_hammer, fantasy_hammer),
            Pair(gatentang, gatentang),
            Pair(ladder, ladder),
            Pair(machovka_plier, machovka_plier),
            Pair(motosega, motosega),
            Pair(paint_brush, paint_brush),
            Pair(papapishu, papapishu),
            Pair(pen_ball, pen_ball),
            Pair(pipe_wrench, pipe_wrench),
            Pair(saw, saw),
            Pair(scissors_forbici, scissors_forbici),
            Pair(scraper, scraper),
            Pair(tinkertool, tinkertool),
            Pair(watering_can, watering_can)
        )

        val imageList4: MutableList<Pair<Int, Int>> = mutableListOf(
            Pair(bull, bull),
            Pair(cat, cat),
            Pair(cow, cow),
            Pair(crocodile, crocodile),
            Pair(crow, crow),
            Pair(dog, dog),
            Pair(donkey, donkey),
            Pair(elephant, elephant),
            Pair(fish1, fish1),
            Pair(frog, frog),
            Pair(giraffe, giraffe),
            Pair(goat, goat),
            Pair(horse, horse),
            Pair(lion, lion),
            Pair(monkey, monkey),
            Pair(mouse, mouse),
            Pair(owl, owl),
            Pair(panda, panda),
            Pair(penguin, penguin),
            Pair(rabbit, rabbit),
            Pair(rhino, rhino),
            Pair(rudolf, rudolf),
            Pair(squirrel, squirrel),
            Pair(tiger, tiger),
            Pair(toucan, toucan),
            Pair(whale_fish, whale_fish),
            Pair(zebra, zebra)
        )

        val imageList5: MutableList<Pair<Int, Int>> = mutableListOf(
            Pair(icon_1, icon_1),
            Pair(icon_2, icon_2),
            Pair(icon_3, icon_3),
            Pair(icon_4, icon_4),
            Pair(icon_5, icon_5),
            Pair(icon_6, icon_6),
            Pair(icon_7, icon_7),
            Pair(icon_8, icon_8),
            Pair(icon_9, icon_9),
            Pair(icon_10, icon_10),
            Pair(icon_11, icon_11),
            Pair(icon_12, icon_12),
            Pair(icon_13, icon_13),
            Pair(icon_14, icon_14),
            Pair(icon_15, icon_15),
            Pair(icon_16, icon_16),
            Pair(icon_17, icon_17),
            Pair(icon_18, icon_18),
            Pair(icon_19, icon_19),
            Pair(icon_20, icon_20),
            Pair(icon_21, icon_21),
            Pair(icon_22, icon_22),
            Pair(icon_23, icon_23),
            Pair(icon_24, icon_24),
            Pair(icon_25, icon_25),
            Pair(icon_26, icon_26)
        )

        val intent = intent
        typeOfImages = intent.getStringExtra("type")!!
        when (typeOfImages) {
            "fruits" -> {
                imagesList = imagesList1
            }
            "flags" -> {
                imagesList = imagesList2
            }
            "tools" -> {
                imagesList = imageList3
            }
            "animals" -> {
                imagesList = imageList4
            }
            "shapes" -> {
                imagesList = imageList5
            }
        }
        imagesList.shuffle()
        images = ArrayList()
        for (i in 0..20) {
            images.add(imagesList[i].first)
            images.add(imagesList[i].second)
        }
        images.shuffle()
        imageViews = arrayOf(
            img1, img2, img3, img4, img5, img6, img7, img8, img9, img10,
            img11, img12, img13, img14, img15, img16, img17, img18, img19, img20,
            img21, img22, img23, img24, img25, img26, img27, img28, img29, img30,
            img31, img32, img33, img34, img35, img36, img37, img38, img39, img40, img41, img42
        )
        val cardBack = thumbnail
        for (i in 0..41) {
            imageViews[i].contentDescription = "cardBack"
            imageViews[i].setOnClickListener {
                if (imageViews[i].contentDescription == "cardBack") {
                    if (lastCard < 0) {
                        if (turnBtnSoundOn!!) {
                            playButtonClickSound()
                        }
                        displayImage(i, images[i])
                        imageViews[i].contentDescription = imageViews[i].toString()
                        lastCard = i
                    } else {    //  there is some card previously opened
                        if (turnBtnSoundOn!!) {
                            playButtonClickSound()
                        }
                        displayImage(i, images[i])
                        imageViews[i].contentDescription = imageViews[i].toString()
                        disableTouch(imagesLayout)
                        Handler().postDelayed({
                            if (images[i] == images[lastCard]) {
                                //  both cards are same
                                displayImage(i, android.R.color.transparent)
                                imageViews[i].contentDescription = imageViews[i].toString()
                                displayImage(lastCard, android.R.color.transparent)
                                imageViews[lastCard].contentDescription =
                                    imageViews[lastCard].toString()
                                if (player1Turn) {
                                    scorePlayer1++
                                    player1Score.text = scorePlayer1.toString()
                                } else {
                                    scorePlayer2++
                                    player2Score.text = scorePlayer2.toString()
                                }
                                //  check game over
                                if ((scorePlayer1 + scorePlayer2) == 21) {
                                    winner = if (scorePlayer1 > scorePlayer2) {
                                        1
                                    } else {
                                        2
                                    }
                                    Handler().postDelayed({
                                        for (j in 0..41) {
                                            imageViews[j].setImageResource(images[j])
                                        }
                                        showWinnerDialog(winner)
                                    }, 500)
                                }
                            } else {
                                // second card is different
                                displayImage(i, cardBack)
                                imageViews[i].contentDescription = "cardBack"
                                displayImage(lastCard, cardBack)
                                imageViews[lastCard].contentDescription = "cardBack"
                                //  change turn
                                player1Turn = !player1Turn
                                isPlayer1Turn(player1Turn)
                            }
                            lastCard = -1
                            enableTouch(imagesLayout)
                        }, 600)
                    }
                }
            }
        }

        replay_button.setOnClickListener {
            if (turnBtnSoundOn!!) {
                playButtonClickSound()
            }
            showQuitDialog(true)
        }

        pause_button.setOnClickListener {
            if (turnBtnSoundOn!!) {
                playButtonClickSound()
            }
            showPauseDialog(
                mPreferences.getBoolean(SOUND_KEY, true),
                mPreferences.getBoolean(MUSIC_KEY, true)
            )
        }
    }

    private fun enableTouch(layout: ViewGroup) {
        layout.isEnabled = true
        for (i in 0 until layout.childCount) {
            val child = layout.getChildAt(i)
            if (child is ViewGroup) {
                disableTouch(child)
            } else {
                child.isEnabled = true
            }
        }
    }

    private fun disableTouch(layout: ViewGroup) {
        layout.isEnabled = false
        for (i in 0 until layout.childCount) {
            val child = layout.getChildAt(i)
            if (child is ViewGroup) {
                disableTouch(child)
            } else {
                child.isEnabled = false
            }
        }
    }

    override fun onPause() {
        super.onPause()
        mPreferences = getSharedPreferences(sharedPrefsFile, MODE_PRIVATE)
        if (mPreferences.getBoolean(MUSIC_KEY, true)) {
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
    }

    override fun onResume() {
        super.onResume()
        mPreferences = getSharedPreferences(sharedPrefsFile, MODE_PRIVATE)
        if (mPreferences.getBoolean(MUSIC_KEY, true)) {
            doBindService()
            startService(Intent(this, MusicService::class.java))
        }
        if (mPreferences.getBoolean(SOUND_KEY, true))
            turnBtnSoundOn = true
    }

    override fun onDestroy() {
        super.onDestroy()
        doUnbindService()
        stopService(Intent(this, MusicService::class.java))
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
            if (musicFlag) {
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


    private fun showWinnerDialog(player: Int) {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.winner_dialog)
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        val window = dialog.window
        window!!.setGravity(Gravity.CENTER)
        window.attributes.windowAnimations = R.style.winnerAnimation

        val winner: TextView = dialog.findViewById(R.id.playerWon)
        winner.text = String.format(resources.getString(R.string.player_won), player)
//        " Player $player "

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
//            dialog.dismiss()
            startActivity(Intent(this, TwoPlayerActivity::class.java))
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

    private fun displayImage(p1: Int, imgResource: Int) {
        val fadeOut = AnimationUtils.loadAnimation(applicationContext, R.anim.fade_out)
        imageViews[p1].startAnimation(fadeOut)
        fadeOut.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(p0: Animation?) {
            }

            override fun onAnimationEnd(p0: Animation?) {
                val fadeIn = AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in)
                imageViews[p1].startAnimation(fadeIn)
                imageViews[p1].setImageResource(imgResource)
            }

            override fun onAnimationStart(p0: Animation?) {
            }
        })
    }

    private fun playButtonClickSound() {
        val mp = MediaPlayer.create(this, R.raw.game_bg_sound)
        mp.start()
        mp.setOnCompletionListener {
            mp.release()
        }
    }

    private fun isPlayer1Turn(p0: Boolean) {
        if (p0) {
            player1style.setBackgroundResource(layout_stroke)
            player2style.setBackgroundResource(0)
//            player1Card.setCardBackgroundColor(ContextCompat.getColor(this, R.color.colorGreen))
//            player1.setTextColor(ContextCompat.getColor(this, R.color.colorGreenFav))
//            player1Score.setTextColor(ContextCompat.getColor(this, R.color.colorGreenFav))
//            player2.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
//            player2Score.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
        } else {
            player2style.setBackgroundResource(layout_stroke)
            player1style.setBackgroundResource(0)
//            player2.setTextColor(ContextCompat.getColor(this, R.color.colorGreenFav))
//            player2Score.setTextColor(ContextCompat.getColor(this, R.color.colorGreenFav))
//            player1.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
//            player1Score.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
        }
    }

    override fun onBackPressed() {
        showQuitDialog(false)
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
            if (replay) {
                val replayIntent = Intent(this, TwoPlayerSelectionActivity::class.java)
                replayIntent.putExtra("type", typeOfImages)
                replayIntent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
                replayIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                startActivity(replayIntent)
                finish()
            } else {
                dialog.dismiss()
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
            if (replay) {
                val replayIntent = Intent(this, TwoPlayerSelectionActivity::class.java)
                replayIntent.putExtra("type", typeOfImages)
                replayIntent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
                replayIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                startActivity(replayIntent)
                finish()
            } else {
                dialog.dismiss()
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

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(p0: ComponentName?) {
            mService = null
        }

        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            mService = (p1 as MusicService.ServiceBinder).service
        }
    }

    private fun doBindService() {
        if (!mIsBound) {
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

    companion object {
        private const val sharedPrefsFile = "com.chumairzafar.flipmemory.mysharedprefs"
        private const val SOUND_KEY = "sound_key"
        private const val MUSIC_KEY = "music_key"
    }
}