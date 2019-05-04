package com.example.skku_food.menuActivity

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.os.AsyncTask
import android.text.Html
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.text.HtmlCompat
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
                        continue
                    }
                    val priceStr=
                        doc.select("div.corner_info > ul > li:nth-child(2) > span").text()

                    val menuList = menuStr.split(" ")
                    val priceList = priceStr.split("가격 : ")

                    when(fTime){
                        "B" -> resultStr += "<h4><font color='black'>조식</font></h4>"
                        "L" -> resultStr += "<h4><font color='black'>중식</font></h4>"
                        "D" -> resultStr += "<h4><font color='black'>석식</font></h4>"
                    }

                    menuList.forEachIndexed { index, s ->
                        resultStr += String.format("%s=%s ", s, priceList[index + 1])
                    }

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
                        continue
                    }
                    val priceStr=
                        doc.select("div.corner_info > ul > li:nth-child(2) > span").text()

                    val priceList = priceStr.split("가격 : ")

                    when(fTime){
                        "B" -> resultStr += "<h4><font color='black'>조식</font></h4>"
                        "L" -> resultStr += "<h4><font color='black'>중식</font></h4>"
                        "D" -> resultStr += "<h4><font color='black'>석식</font></h4>"
                    }

                    menuStr.split(" ").forEachIndexed { index, s ->
                        resultStr += String.format("%s=%s ", s, priceList[index + 1])
                    }

                }
            }
            // 긱식
            "기숙사식당(봉룡학사)" -> {

                hakURL = "https://dorm.skku.edu/_custom/skku/_common/board/schedule_menu/food_menu_page.jsp?date=$time&board_no=61&lng=ko"
                doc = Jsoup.connect(hakURL).get()

                for (i in 1..3){
                    val menuStr = doc.select("#foodlist0$i > ul > li > p").text()
                    val titleStr = doc.select("#foodlist0$i > ul > li > span").text()
                    val menuList = menuStr.split("[0-9]{2}:[0-9]{2}~[0-9]{2}:[0-9]{2},".toRegex())

                    if (menuStr == ""){
                        continue
                    }

                    when(i){
                        1 -> resultStr += "<h4><font color='black'>조식</font></h4>"
                        2 -> resultStr += "<h4><font color='black'>중식</font></h4>"
                        3 -> resultStr += "<h4><font color='black'>석식</font></h4>"
                    }

                    titleStr.split(" ").forEachIndexed { index, s ->
                        resultStr += String.format("<b>- %s</b><br>%s<br>", s, menuList[index + 1].trim())
                    }

                }

            }

        }
    }

    // UI 변경 (결과)
    override fun onPostExecute(result: Unit?) {
        val txtView = weakView.get()
        txtView?.text = Html.fromHtml(resultStr, HtmlCompat.FROM_HTML_MODE_LEGACY)
        dialog.dismiss()
    }

}
