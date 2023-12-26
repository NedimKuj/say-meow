package com.pearshadow.saymeow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.viewpager2.widget.ViewPager2

class MainActivity : AppCompatActivity() {

    private lateinit var infiniteViewPager: ViewPager2
    private lateinit var infiniteRecyclerAdapter: InfiniteRecyclerAdapter

    private var catList: MutableList<Cat> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // getting the required Cat data for filling the ViewPager
        for (i in 1..50) {
            getCatData()
        }

        // setting up the infinite ViewPager
        infiniteViewPager = findViewById(R.id.infiniteViewPager)
        infiniteViewPager.offscreenPageLimit = 4
        infiniteRecyclerAdapter = InfiniteRecyclerAdapter(catList) { adapterPositon, meowDirection ->
            infiniteViewPager.currentItem = infiniteViewPager.currentItem + meowDirection.directionValue
        }
        infiniteViewPager.adapter = infiniteRecyclerAdapter

        // setting the current item of the infinite ViewPager to the actual first element
        infiniteViewPager.currentItem = 1

        // function for registering a callback to update the ViewPager and provide a smooth flow for infinite scroll
        // this will always be for, regardless of the full list size
        onInfinitePageChangeCallback(4)
    }

    private fun getCatData() {
        catList.add(Cat("1 Lina", "https://purr.objects-us-east-1.dream.io/i/2015-09-17-214.jpg"))
        catList.add(Cat("2 Nina", "https://purr.objects-us-east-1.dream.io/i/image1___22342.jpg"))
        catList.add(Cat("3 Fina", "https://purr.objects-us-east-1.dream.io/i/11226561_939465719444268_5011472048854498701_n.jpg"))
        catList.add(Cat("4 Dina", "https://purr.objects-us-east-1.dream.io/i/id0Wu.jpg"))
        catList.add(Cat("5 Bina", "https://purr.objects-us-east-1.dream.io/i/cam00531.jpg"))
        catList.add(Cat("6 Vina", "https://purr.objects-us-east-1.dream.io/i/G1ZII.jpg"))
    }

    private fun onInfinitePageChangeCallback(listSize: Int) {
        infiniteViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)

                if (state == ViewPager2.SCROLL_STATE_IDLE) {
                    when (infiniteViewPager.currentItem) {
                        listSize - 1 -> {
                            Log.e("MEOW SCROLL STATE CHANGED", "From ${listSize - 1} to 1")
                            infiniteViewPager.setCurrentItem(1, false)
                        }

                        0 -> {
                            Log.e("MEOW SCROLL STATE CHANGED", "From 0 to ${listSize - 2}")
                            infiniteViewPager.setCurrentItem(listSize - 2, false)
                        }
                    }
                }
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                Log.e("MEOW PAGE", "Current page: $position")
            }
        })
    }

}