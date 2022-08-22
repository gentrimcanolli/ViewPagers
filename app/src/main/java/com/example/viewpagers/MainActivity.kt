package com.example.viewpagers


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import android.animation.ArgbEvaluator
import android.media.Image
import android.widget.ImageView
import android.widget.LinearLayout
import kotlin.math.roundToInt


class MainActivity : AppCompatActivity() {
    lateinit var viewPager2: ViewPager2
    lateinit var indicatorHolder: LinearLayout
    val argbEvaluator = ArgbEvaluator()
    lateinit var list: MutableList<ImageItem>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager2 = findViewById(R.id.viewPager2)
        indicatorHolder = findViewById(R.id.indicator_linear)

        list = mutableListOf(
            ImageItem(
                "https://cdn.britannica.com/18/114418-004-2A12F087/Flag-Kosovo.jpg",
                getColor(R.color.blue)
            ),
            ImageItem(
                "https://upload.wikimedia.org/wikipedia/commons/thumb/3/36/Flag_of_Albania.svg/800px-Flag_of_Albania.svg.png?20190303164713",
                getColor(R.color.red)
            ),
            ImageItem(
                "https://www.pngall.com/wp-content/uploads/2016/06/Germany-Flag.png",
                getColor(R.color.yellow)
            ),
            ImageItem(
                "https://upload.wikimedia.org/wikipedia/commons/thumb/7/79/Flag_of_Nigeria.svg/1280px-Flag_of_Nigeria.svg.png",
                getColor(R.color.green)
            )
        )

        setupIndicator()

        val adapter = ExampleAdapter(list)
        viewPager2.adapter = adapter

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)

                if (position < (list.size - 1)) {
                    viewPager2.setBackgroundColor(
                        (argbEvaluator.evaluate(
                            positionOffset,
                            list[position].color,
                            list[position + 1].color
                        )).toString().toInt()
                    )
                } else {
                    viewPager2.setBackgroundColor(list[list.size - 1].color)
                }
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                selectPosition(position)

            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
            }
        })
    }

    private fun selectPosition(position: Int) {
        for (indicator in 0 until indicatorHolder.childCount) {
            indicatorHolder.getChildAt(indicator).isSelected = false
            indicatorHolder.getChildAt(indicator).setOnClickListener {
                viewPager2.currentItem = indicator
            }
        }

        indicatorHolder.getChildAt(position).isSelected = true

    }

    private fun setupIndicator() {
        for (i in 0 until list.size) {
            val indicator = ImageView(this)
            val params = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT)
            params.marginStart = resources.getDimension(R.dimen.size_2).roundToInt()
            params.marginEnd = resources.getDimension(R.dimen.size_2).roundToInt()
            params.weight = 1f
            indicator.background = getDrawable(R.drawable.indicator_background)
            indicator.layoutParams = params
            indicatorHolder.addView(indicator)
        }
    }
}