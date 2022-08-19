package com.example.moavara.Util

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import bigbigdw.joaradw.util.DBDate
import bigbigdw.joaradw.util.Param
import com.example.moavara.Retrofit.*
import com.example.moavara.Search.AnayzeData
import com.google.firebase.database.FirebaseDatabase

object Mining {

    fun runMiningEvent(context: Context) {
        getEventJoara(context)
    }

    private fun getEventJoara(context: Context) {

        val apiJoara = RetrofitJoara()
        val param = Param.getItemAPI(context)
        param["page"] = "1"
        param["show_type"] = "android"
        param["event_type"] = "normal"
        param["offset"] = "100"
        param["token"] = context.getSharedPreferences("LOGIN", AppCompatActivity.MODE_PRIVATE)
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
                                    AnayzeData(
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

