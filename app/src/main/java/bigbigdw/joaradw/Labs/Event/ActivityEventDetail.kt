package com.example.moavara.Soon.Event


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import bigbigdw.joaradw.databinding.ActivityEventDetailBinding
import bigbigdw.joaradw.util.Param
import com.example.moavara.Retrofit.JoaraEventsResult
import com.example.moavara.Retrofit.RetrofitDataListener
import com.example.moavara.Retrofit.RetrofitJoara
import com.example.moavara.Search.AnayzeData
import com.example.moavara.Search.EventDetailData
import com.example.moavara.Util.Mining
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ActivityEventDetail : AppCompatActivity() {

    private lateinit var binding: ActivityEventDetailBinding
    private lateinit var adapter: AdapterEventDetail
    private val items = ArrayList<EventDetailData>()

    val EventRef = FirebaseDatabase.getInstance().reference.child("Event")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Mining.runMiningEvent(applicationContext)

        EventRef.addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val apiJoara = RetrofitJoara()
                val param = Param.getItemAPI(this@ActivityEventDetail)
                param["page"] = "1"
                param["show_type"] = "android"
                param["event_type"] = "normal"
                param["offset"] = "100"
                param["token"] = this@ActivityEventDetail.getSharedPreferences("LOGIN", MODE_PRIVATE)
                    ?.getString("TOKEN", "").toString()

                apiJoara.getJoaraEventList(
                    param,
                    object : RetrofitDataListener<JoaraEventsResult> {
                        override fun onSuccess(it: JoaraEventsResult) {

                            val data = it.data

                            if (data != null) {

                                for (item in data) {
                                    val dataItem = ArrayList<AnayzeData>()

                                    for (postSnapshot in dataSnapshot.child(item.idx).children) {
                                        val group: AnayzeData? =
                                            postSnapshot.getValue(AnayzeData::class.java)

                                        if (group != null) {
                                            dataItem.add(
                                                AnayzeData(
                                                    group.date,
                                                    group.cntRead
                                                )
                                            )
                                        }
                                    }

                                    items.add(
                                        EventDetailData(
                                            item.title,
                                            item.ingimg,
                                            item.e_date,
                                            item.s_date,
                                            item.cnt_read,
                                            dataItem
                                        )
                                    )
                                }
                            }

                            adapter = AdapterEventDetail(this@ActivityEventDetail, items)

                            with(binding) {
                                rview.layoutManager =
                                    LinearLayoutManager(this@ActivityEventDetail, LinearLayoutManager.VERTICAL, false)
                                rview.adapter = adapter
                            }

                            adapter.notifyDataSetChanged()
                        }
                    })
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })

    }

}