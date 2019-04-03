package com.example.skku_food

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.example.skku_food.adapter.viewpager_adt
import com.example.skku_food.database.DatabaseCopier
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private val VPadapter by lazy { viewpager_adt(supportFragmentManager) }
    lateinit var mAdView: AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Manage Database
        thread {
            DatabaseCopier.copyAttachedDatabase(context = applicationContext)
        }


        //AdMob
        MobileAds.initialize(this, getString(R.string.admob_app_id))
        mAdView = adView
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        //ViewPager Adapter Connect
        viewpager.apply {
            adapter = VPadapter
            offscreenPageLimit = 2
            addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(state: Int) {
                }

                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                }

                override fun onPageSelected(position: Int) {
                    tablayout.apply {
                        getTabAt(0)!!.setIcon(R.drawable.ic_menu_gray)
                        getTabAt(1)!!.setIcon(R.drawable.ic_dining_gray)
                        getTabAt(2)!!.setIcon(R.drawable.ic_trophy_gray)

                        when(position){
                            0   ->  getTabAt(0)!!.setIcon(R.drawable.ic_menu_green)
                            1   ->  getTabAt(1)!!.setIcon(R.drawable.ic_dining_green)
                            2   ->  getTabAt(2)!!.setIcon(R.drawable.ic_trophy_green)
                        }
                    }
                }

            })
        }

        //TabLayout + ViewPager Connect
        tablayout.apply {
            setupWithViewPager(viewpager)
            getTabAt(0)!!.setIcon(R.drawable.ic_menu_green)
            getTabAt(1)!!.setIcon(R.drawable.ic_dining_gray)
            getTabAt(2)!!.setIcon(R.drawable.ic_trophy_gray)
        }


    }

}
