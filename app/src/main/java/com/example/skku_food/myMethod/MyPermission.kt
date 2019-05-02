package com.example.skku_food.myMethod

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MyPermission{

    companion object {
        const val PERMISSION_CODE = 200

        fun myPermissionCheck(context: Context): Boolean {
            val chk: Int = ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION)
            return chk == PackageManager.PERMISSION_GRANTED
        }

        fun myRequestPermission(chk: Boolean, activity: Activity) {
            if (!chk)
                ActivityCompat.requestPermissions(
                    activity, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    PERMISSION_CODE
                )
        }

    }


}