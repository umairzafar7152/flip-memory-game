package com.chumairzafar.flipmemory

import android.content.Context
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.view.*
import androidx.fragment.app.Fragment
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import kotlinx.android.synthetic.main.fragment_single_player43.*
import com.chumairzafar.flipmemory.R.drawable.*

class SinglePlayerFragment43 : Fragment() {
    private var lastCard = -1
    private var attempts = 11
    private var found = 0
    private lateinit var dataPasser: SinglePlayerDataPasser
    private lateinit var imageViews: Array<ImageView>
    private lateinit var images: MutableList<Int>
    private lateinit var typeOfImages: String
    private lateinit var imagesList: MutableList<Pair<Int, Int>>
    private lateinit var mPreferences: SharedPreferences
    private var turnBtnSoundOn: Boolean? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bundle = arguments
        typeOfImages = bundle!!.getString("type")!!
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_single_player43, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        dataPasser = context as SinglePlayerDataPasser
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        passData(attempts, false)
        mPreferences = activity!!.getSharedPreferences(sharedPrefsFile, Context.MODE_PRIVATE)
        turnBtnSoundOn = mPreferences.getBoolean(SOUND_KEY, true)
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

        val imagesList5: MutableList<Pair<Int, Int>> = mutableListOf(
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
                imagesList = imagesList5
            }
        }
        imagesList.shuffle()
        images = ArrayList()
        for (i in 0..5) {
            images.add(imagesList[i].first)
            images.add(imagesList[i].second)
        }

        images.shuffle()

        imageViews =
            arrayOf(img1, img2, img3, img4, img5, img6, img7, img8, img9, img10, img11, img12)
        lastCard = -1
        val cardBack = thumbnail_flags
        for (i in 0..11) {
            imageViews[i].contentDescription = "cardBack"
            imageViews[i].setOnClickListener {
                mPreferences = activity!!.applicationContext.getSharedPreferences(
                    sharedPrefsFile,
                    Context.MODE_PRIVATE
                )
                turnBtnSoundOn = mPreferences.getBoolean(SOUND_KEY, true)
                if (imageViews[i].contentDescription == "cardBack") {
                    if (lastCard < 0) {
                        if (turnBtnSoundOn!!) {
                            playButtonClickSound()
                        }
                        displayImage(i, images[i])
                        imageViews[i].contentDescription = imageViews[i].toString()
                        lastCard = i
                    } else {    //  there is some card previously opened
                        attempts--
                        displayImage(i, images[i])
                        imageViews[i].contentDescription = imageViews[i].toString()
                        if (turnBtnSoundOn!!) {
                            playButtonClickSound()
                        }
                        disableTouch(imagesLayout)
                        Handler().postDelayed({
                            if (images[i] == images[lastCard]) {
                                //  both cards are same
                                found++
                                displayImage(i, android.R.color.transparent)
                                imageViews[i].contentDescription = imageViews[i].toString()
                                displayImage(lastCard, android.R.color.transparent)
                                imageViews[lastCard].contentDescription =
                                    imageViews[lastCard].toString()
                                //  when all images are found
                                if (found == 6) {
                                    passData(attempts, true)
                                } else {
                                    passData(attempts, false)
                                }
                            } else {
                                //  second card is different
                                displayImage(i, cardBack)
                                imageViews[i].contentDescription = "cardBack"
                                displayImage(lastCard, cardBack)
                                imageViews[lastCard].contentDescription = "cardBack"
                                passData(attempts, false)
                            }
                            lastCard = -1
                            enableTouch(imagesLayout)
                            if (attempts <= 0 && found != 6) {
                                notifyGameOver()
                            }
                        }, 600)
                    }
                }
            }
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

    private fun displayImage(p1: Int, imgResource: Int) {
        val fadeOut = AnimationUtils.loadAnimation(activity!!.applicationContext, R.anim.fade_out)
        imageViews[p1].startAnimation(fadeOut)
        fadeOut.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(p0: Animation?) {
            }

            override fun onAnimationEnd(p0: Animation?) {
                val fadeIn =
                    AnimationUtils.loadAnimation(activity!!.applicationContext, R.anim.fade_in)
                imageViews[p1].startAnimation(fadeIn)
                imageViews[p1].setImageResource(imgResource)
            }

            override fun onAnimationStart(p0: Animation?) {
            }
        })
    }

    private fun passData(pAttempts: Int, pLevelCompleted: Boolean) {
        dataPasser.onDataPass("frag42", pAttempts, pLevelCompleted)
    }

    private fun notifyGameOver() {
        dataPasser.isGameOver(true)
    }

    private fun playButtonClickSound() {
        val mp = MediaPlayer.create(activity!!.applicationContext, R.raw.game_bg_sound)
        mp.start()
        mp.setOnCompletionListener {
            mp.release()
        }
    }

    companion object {
        private const val sharedPrefsFile = "com.chumairzafar.flipmemory.mysharedprefs"
        private const val SOUND_KEY = "sound_key"
    }
}
