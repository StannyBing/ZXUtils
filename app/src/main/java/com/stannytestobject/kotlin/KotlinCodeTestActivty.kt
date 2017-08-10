package com.stannytestobject.kotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.stannytestobject.R
import com.stannytestobject.kotlin.KotlinCodeTestActivty.MyAdapter.KHolder
import com.zx.zxutils.forutil.ZXRecordListener
import com.zx.zxutils.other.ZXItemClickSupport
import com.zx.zxutils.util.ZXLogUtil
import com.zx.zxutils.util.ZXRecordUtil
import com.zx.zxutils.util.ZXSystemUtil
import com.zx.zxutils.util.ZXToastUtil
import kotlinx.android.synthetic.main.activity_kotlin_code_test_activty.*
import java.io.File

class KotlinCodeTestActivty : AppCompatActivity(), ZXRecordListener, View.OnClickListener {
    var num: Int = 0
    var times: Int = 0

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.btn_mathTest -> {
                num = (Math.random() * 10000).toInt()
                times = 0
                logNum()
            }
        }
    }

    fun logNum() {
        times++
        if (num == 1) {
            ZXLogUtil.loge("计算完成,共$times 次")
        } else if (num % 2 == 0) {
            num = num / 2
            ZXLogUtil.loge("${num * 2}->$num")
            logNum()
        } else {
            num = num * 3 + 1
            ZXLogUtil.loge("${(num - 1) / 3}->$num")
            logNum()
        }
    }

    var datalist = arrayListOf<MyEntity>()
    var recordUtil = ZXRecordUtil(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_code_test_activty)
        initView()
    }

    fun initView() {
        rv_records.layoutManager = GridLayoutManager(this, 4)
        rv_records.adapter = MyAdapter(datalist)
        recordUtil.playMedia("")
        ZXItemClickSupport
                .addTo(rv_records)
                .setOnItemClickListener { recyclerView, position, view ->
                    recordUtil.playMedia(datalist[position].file)
                }
        recordUtil.bindView(btn_record)
        recordUtil.setOnRecordListener(this)
        btn_mathTest.setOnClickListener(this)
    }

    override fun onInitPath(): String {
        return ZXSystemUtil.getSDCardPath() + System.currentTimeMillis().toString() + "x.amr"
    }

    override fun onSuccess(file: File) {
        ZXToastUtil.showToast("地址:" + file.absolutePath)
        datalist.add(MyEntity(file.name, file))
        rv_records.adapter.notifyDataSetChanged()
    }

    class MyAdapter(var datalist: ArrayList<MyEntity>) : RecyclerView.Adapter<KHolder>() {


        override fun onBindViewHolder(holder: KHolder, position: Int) {
            holder.tvTime.text = datalist[position].name
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): KHolder? {
            var view = LayoutInflater.from(parent!!.context).inflate(R.layout.item_record, parent, false)
            return KHolder(view)
        }

        override fun getItemCount(): Int {
            return datalist.size
        }


        class KHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var tvTime: TextView = itemView.findViewById(R.id.tv_record_time) as TextView
        }
    }

    data class MyEntity(var name: String, var file: File)
}
