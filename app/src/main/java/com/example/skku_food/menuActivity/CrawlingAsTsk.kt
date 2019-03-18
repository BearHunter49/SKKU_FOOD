package com.example.skku_food.menuActivity

import android.os.AsyncTask
import java.text.SimpleDateFormat
import java.util.*

class CrawlingAsTsk:AsyncTask<Void, Void, Unit>(){

    override fun doInBackground(vararg params: Void?) {

        val mTime = System.currentTimeMillis()
        val mToday = Date(mTime)
        val mFormat = SimpleDateFormat("yyyy-mm-dd", Locale.US)
        val time = mFormat.format(mToday)
        var fTime = "B"

        //https://www.skku.edu/skku/campus/support/welfare_11_1.do?mode=info&srDt=2019-03-18&srCategory=L&conspaceCd=20201104&srResId=3&srShowTime=D#none
        //학식
        var hak_url = "https://www.skku.edu/skku/campus/support/welfare_11_1.do?mode=info&srDt=$time&srCategory=$fTime&conspaceCd=20201104&srResId=3&srShowTime=D#none"

    }

}
