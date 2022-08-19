package com.example.moavara.Util

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import bigbigdw.joaradw.util.DBDate
import bigbigdw.joaradw.util.Param
import com.example.moavara.Retrofit.*
import com.example.moavara.Search.EventDetailDataMining
import com.google.firebase.database.FirebaseDatabase

object Mining {

    fun runMiningEvent(context: Context) {
        getNoticeJoara(context)
        getEventJoara(context)
    }

    private fun getNoticeJoara(context: Context) {

        val apiJoara = RetrofitJoara()
        val param = Param.getItemAPI(context)
        param["board"] = ""
        param["page"] = "1"
        param["token"] = context.getSharedPreferences("pref", AppCompatActivity.MODE_PRIVATE)
            ?.getString("TOKEN", "").toString()
        var num = 0
        val EventRef = FirebaseDatabase.getInstance().reference.child("Notice").child(DBDate.DateMMDD())

        apiJoara.getNoticeList(
            param,
            object : RetrofitDataListener<JoaraNoticeResult> {
                override fun onSuccess(data: JoaraNoticeResult) {

                    val data = data.notices

                    if (data != null) {
                        for (item in data) {
                            EventRef.child(DBDate.Week() + ((DBDate.DayInt() * 1000) + num).toString())
                                .setValue(
                                    EventDetailDataMining(
                                        DBDate.DateMMDD(),
                                        item.cnt_read,
                                    )
                                )
                        }
                        num += 1
                    }
                }
            })
    }

    private fun getEventJoara(context: Context) {

        val apiJoara = RetrofitJoara()
        val param = Param.getItemAPI(context)
        param["page"] = "1"
        param["show_type"] = "android"
        param["event_type"] = "normal"
        param["offset"] = "25"
        param["token"] = context.getSharedPreferences("pref", AppCompatActivity.MODE_PRIVATE)
            ?.getString("TOKEN", "").toString()

        var num = 0
        val EventRef = FirebaseDatabase.getInstance().reference.child("Event")

        apiJoara.getJoaraEventList(
            param,
            object : RetrofitDataListener<JoaraEventsResult> {
                override fun onSuccess(data: JoaraEventsResult) {

                    val data = data.data

                    if (data != null) {
                        for (item in data) {
                            EventRef.child(item.idx).child(DBDate.DateMMDD())
                                .setValue(
                                    EventDetailDataMining(
                                        DBDate.DateMMDD(),
                                        item.cnt_read,
                                    )
                                )
                            num += 1
                        }
                    }
                }
            })
    }



}

