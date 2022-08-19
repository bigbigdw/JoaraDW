package com.example.moavara.Soon.Event


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.WorkManager
import bigbigdw.joaradw.databinding.ActivityEventDetailBinding
import bigbigdw.joaradw.util.Param
import com.example.moavara.Retrofit.JoaraEventsResult
import com.example.moavara.Retrofit.RetrofitDataListener
import com.example.moavara.Retrofit.RetrofitJoara
import com.example.moavara.Search.AnayzeData
import com.example.moavara.Search.EventDetailData
import com.example.moavara.Search.EventDetailDataMining
import com.example.moavara.Util.Mining
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ActivityEventDetail : AppCompatActivity() {

    private lateinit var binding: ActivityEventDetailBinding
    private lateinit var adapter: AdapterEventDetail
    private val items = ArrayList<EventDetailData>()
    val dataItem = ArrayList<AnayzeData>()

    val miningRef = FirebaseDatabase.getInstance().reference.child("Event")
    val EventRef = FirebaseDatabase.getInstance().reference.child("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Mining.runMiningEvent(applicationContext)
        val workManager = WorkManager.getInstance(applicationContext)

        adapter = AdapterEventDetail(this@ActivityEventDetail, items, dataItem)

        with(binding) {
            rview.layoutManager =
                LinearLayoutManager(this@ActivityEventDetail, LinearLayoutManager.VERTICAL, false)
            rview.adapter = adapter
        }

        items.clear()

        EventRef.addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (postSnapshot in dataSnapshot.children) {
                    val group: EventDetailDataMining? =
                        postSnapshot.getValue(EventDetailDataMining::class.java)
                    if (group != null) {
                        dataItem.add(
                            AnayzeData(
                                group.date,
                                group.cntRead
                            )
                        )
                    }
                }
                getEventJoara()
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })

    }

    private fun getEventJoara() {

        val apiJoara = RetrofitJoara()
        val param = Param.getItemAPI(this@ActivityEventDetail)
        param["page"] = "1"
        param["show_type"] = "android"
        param["event_type"] = "normal"
        param["offset"] = "25"
        param["token"] = this@ActivityEventDetail?.getSharedPreferences("pref", MODE_PRIVATE)
            ?.getString("TOKEN", "").toString()

        apiJoara.getJoaraEventList(
            param,
            object : RetrofitDataListener<JoaraEventsResult> {
                override fun onSuccess(it: JoaraEventsResult) {

                    val data = it.data

                    if (data != null) {
                        for (item in data) {
                            items.add(
                                EventDetailData(
                                    item.title,
                                    item.ingimg,
                                    item.e_date,
                                    item.s_date,
                                    item.cnt_read,
                                )
                            )
                        }
                    }
                    adapter.notifyDataSetChanged()
                }
            })


    }

}