package com.example.viewpagers


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import android.animation.ArgbEvaluator
import android.media.Image


class MainActivity : AppCompatActivity() {
    lateinit var viewPager2: ViewPager2
    lateinit var pageNumberTv: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager2 = findViewById(R.id.viewPager2)
        pageNumberTv = findViewById(R.id.page_number_tv)

        val list = mutableListOf(
            ImageItem("https://cdn.britannica.com/18/114418-004-2A12F087/Flag-Kosovo.jpg"),
            ImageItem("https://upload.wikimedia.org/wikipedia/commons/thumb/3/36/Flag_of_Albania.svg/800px-Flag_of_Albania.svg.png?20190303164713"),
            ImageItem("https://www.pngall.com/wp-content/uploads/2016/06/Germany-Flag.png"),
            ImageItem("https://upload.wikimedia.org/wikipedia/commons/thumb/7/79/Flag_of_Nigeria.svg/1280px-Flag_of_Nigeria.svg.png")
        )

        val adapter = ExampleAdapter(list)
        viewPager2.adapter = adapter

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)

                println("position: $position, positionOffset: $positionOffset, positionOffsetPixels: $positionOffsetPixels ")
                val blueColor: Int = resources.getColor(R.color.blue)
                val redColor: Int = resources.getColor(R.color.red)
                val greenColor: Int = resources.getColor(R.color.green)
                val yellowColor: Int = resources.getColor(R.color.yellow)

                val colors = arrayOf(blueColor, redColor, yellowColor, greenColor)
                val argbEvaluator = ArgbEvaluator()


                if (position < (list.size - 1) && position < (colors.size - 1)) {
                    viewPager2.setBackgroundColor(
                        (argbEvaluator.evaluate(
                            positionOffset,
                            colors[position],
                            colors[position + 1]
                        )).toString().toInt()
                    )
                } else {
                    viewPager2.setBackgroundColor(colors[colors.size - 1])
                }
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                val pageNumber = position + 1
                val pages = list.size

                pageNumberTv.text = "$pageNumber/$pages"


            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
            }
        })


    }
}