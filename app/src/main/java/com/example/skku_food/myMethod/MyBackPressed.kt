package com.example.skku_food.myMethod

import android.app.Activity
import android.widget.Toast

class MyBackPressed{

    companion object {
        private var backKeyClickTime: Long = 0

        fun onBackPressed(activity: Activity){
            if(System.currentTimeMillis() > backKeyClickTime + 2000){
                backKeyClickTime = System.currentTimeMillis()
                showToast(activity)
                return
            }
            else{
                android.os.Process.killProcess(android.os.Process.myPid())
            }
        }

        private fun showToast(activity: Activity){
            Toast.makeText(activity, "한 번 더 누르면 종료합니다.", Toast.LENGTH_SHORT).show()
        }
    }


}