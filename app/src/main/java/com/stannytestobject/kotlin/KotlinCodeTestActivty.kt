package com.stannytestobject.kotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import com.stannytestobject.R
import com.zx.zxutils.util.ZXLogUtil
import kotlinx.android.synthetic.main.activity_kotlin_code_test_activty.*

class KotlinCodeTestActivty : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_code_test_activty)

        initView()
    }

    fun initView() {
        btn_openRecord.setOnClickListener {
            //TODO
        }
        btn_startRecord.setOnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    ZXLogUtil.loge("按住了")
                }
                MotionEvent.ACTION_UP -> {
                    ZXLogUtil.loge("松开了")
                }
                else -> {

                }
            }
            false
        }
    }
}
