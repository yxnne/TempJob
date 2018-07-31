package com.iel.swsapp.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.iel.swsapp.R
import com.kymjs.rxvolley.toolbox.RxVolleyContext.toast
import kotlinx.android.synthetic.main.activity_version_intro.*

/**
 * Class Full Name  : com.iel.swsapp.activity.VersionIntroActivity
 * Author Name      : yxnne
 * Create Time      : 2017/5/22
 * Project Name     : SWSAPP
 * Descriptions     : 版本介绍页 ,这是一个 KotlinActivity
 *                  另外，响应Google 号召，逐步拥抱 Kotlin
 */
class VersionIntroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_version_intro)
//        tv_version_intro_1.setText("This is An Introductions About SWS APP.\n"+
//        "But Some Informations NOt Here Now , We'll Make It Soonly")

        //上面的写法是可以的，但是！
        //This is Kotlin Written Way  tv_version_intro_1.text = ""
        tv_version_intro_1.text = getString(R.string.app_slogan);
        tv_version_intro_1.text = "This is An Introductions About SWS APP.\n"+
        "But Some Informations NOT Here Now , We'll Make It Soonly"

        toast("Version Info is On the Way... ")




    }
}
