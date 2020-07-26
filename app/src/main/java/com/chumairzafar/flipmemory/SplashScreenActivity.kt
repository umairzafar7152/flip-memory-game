package com.chumairzafar.flipmemory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash_screen)

        //  splash screen
        Handler().postDelayed( {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, SPLASH_TIME_OUT)

        val gameTextAnim = AnimationUtils.loadAnimation(this, R.anim.slide_right)
        val descTextAnim = AnimationUtils.loadAnimation(this, R.anim.slide_left)
        gameText.animation = gameTextAnim
        gameDescription.animation = descTextAnim
        gameTextAnim.start()
        descTextAnim.start()
    }

    companion object {
        private const val SPLASH_TIME_OUT: Long = 2500
    }
}
