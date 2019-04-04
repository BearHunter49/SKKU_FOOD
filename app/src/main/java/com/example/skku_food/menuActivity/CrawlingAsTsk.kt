package com.example.skku_food.menuActivity

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.os.AsyncTask
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_res_menu.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.lang.ref.WeakReference
import java.text.SimpleDateFormat
import java.util.*

class CrawlingAsTsk(menuView:TextView, context:Context, activity: Activity):AsyncTask<String, Void, Unit>(){

    private var resultStr: String = ""
    private val weakView: WeakReference<TextView> = WeakReference(menuView)
    private val weakActivity: WeakReference<Activity> = WeakReference(activity)
    private val dialog:ProgressDialog = ProgressDialog(context)

    // 로딩
    override fun onPreExecute() {

        dialog.apply {
            setProgressStyle(ProgressDialog.STYLE_SPINNER)
            setMessage("로딩 중...")
            setCanceledOnTouchOutside(false)
            setOnCancelListener {
                val act = weakActivity.get()
                act?.finish()
            }
            show()
        }

        super.onPreExecute()
    }

    override fun doInBackground(vararg params: String?) {

        val mTime = System.currentTimeMillis()
        val mToday = Date(mTime)
        val mFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val time = mFormat.format(mToday)

        val fTimeList = listOf("B", "L", "D")
        var hakURL: String
        var doc: Document

        when(params[0]){
            //학식
            "학생회관(행단골)" -> {
                for (fTime in fTimeList) {
                    hakURL =
                            "https://www.skku.edu/skku/campus/support/welfare_11_1.do?mode=info&srDt=$time&srCategory=$fTime&conspaceCd=20201104&srResId=3&srShowTime=D"
                    doc = Jsoup.connect(hakURL).get()

                    val menuStr =
                        doc.select("div.menu_title").text()
                    // Non-contents
                    if (menuStr == ""){
                        resultStr += "\n"
                        continue
                    }
                    val priceStr=
                        doc.select("div.corner_info > ul > li:nth-child(2) > span").text()

                    val menuList = menuStr.split(" ")
                    val priceList = priceStr.split("가격 : ")

                    when(fTime){
                        "B" -> resultStr += "조식\n"
                        "L" -> resultStr += "중식\n"
                        "D" -> resultStr += "석식\n"
                    }

                    menuList.forEachIndexed { index, s ->
                        resultStr += String.format("%s=%s ", s, priceList[index + 1])
                    }
                    resultStr += "\n\n"
                }
            }
            // 공식
            "공대식당(해오름)" -> {

                for (fTime in fTimeList) {
                    hakURL =
                            "https://www.skku.edu/skku/campus/support/welfare_11_1.do?mode=info&srDt=$time&srCategory=$fTime&conspaceCd=20201097&srResId=10&srShowTime=D"
                    doc = Jsoup.connect(hakURL).get()

                    val menuStr =
                        doc.select("div.menu_title").text()
                    // Non-contents
                    if (menuStr == ""){
                        resultStr += "\n\n"
                        continue
                    }
                    val priceStr=
                        doc.select("div.corner_info > ul > li:nth-child(2) > span").text()

                    val priceList = priceStr.split("가격 : ")

                    when(fTime){
                        "B" -> resultStr += "조식\n"
                        "L" -> resultStr += "중식\n"
                        "D" -> resultStr += "석식\n"
                    }

                    menuStr.split(" ").forEachIndexed { index, s ->
                        resultStr += String.format("%s=%s ", s, priceList[index + 1])
                    }
                    resultStr += "\n\n"
                }
            }
            // 긱식
            "기숙사식당(봉룡학사)" -> {

                Thread.sleep(2000)
                hakURL = "https://dorm.skku.edu/_custom/skku/_common/board/schedule_menu/food_menu_page.jsp?date=$time&board_no=61&lng=ko"
                doc = Jsoup.connect(hakURL).get()

                for (i in 1..3){
                    val menuStr = doc.select("#foodlist0$i > ul > li > p").text()
                    val titleStr = doc.select("#foodlist0$i > ul > li > span").text()
                    val menuList = menuStr.split("[0-9]{2}:[0-9]{2}~[0-9]{2}:[0-9]{2},".toRegex())

                    when(i){
                        1 -> resultStr += "조식\n"
                        2 -> resultStr += "중식\n"
                        3 -> resultStr += "석식\n"
                    }

                    titleStr.split(" ").forEachIndexed { index, s ->
                        resultStr += String.format("<%s>\n%s\n", s, menuList[index + 1].trim())
                    }

                    resultStr += "\n"
                }

            }

        }
    }

    // UI 변경 (결과)
    override fun onPostExecute(result: Unit?) {
        val txtView = weakView.get()
        txtView?.text = resultStr
        dialog.dismiss()
    }

    // 취소 처리
    override fun onCancelled() {
        super.onCancelled()
    }
}
