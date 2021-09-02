package bigbigdw.joaradw.fragment_best

import android.content.res.AssetManager
import bigbigdw.joaradw.main.TabViewModel
import android.widget.LinearLayout
import androidx.core.widget.NestedScrollView
import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.ViewModelProvider
import android.view.LayoutInflater
import android.view.ViewGroup
import bigbigdw.joaradw.R
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import bigbigdw.joaradw.fragment_main.MainBookData
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.ArrayList

class BestTab : Fragment() {
    private var tabViewModel: TabViewModel? = null
    var best = "weekly"
    var token = ""
    var footer: LinearLayout? = null
    var index = 0

    private var adapter: AdapterBookTabBest? = null
    private val items = ArrayList<MainBookData?>()
    var recyclerView: RecyclerView? = null

    lateinit var root: View
    var type = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tabViewModel = ViewModelProvider(this).get(TabViewModel::class.java)
        index = 1
        if (arguments != null) {
            index = requireArguments().getInt(ARG_SECTION_NUMBER)
        }
        tabViewModel!!.setIndex(index)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_best_tab, container, false)
        footer = root.findViewById(R.id.footer)
        recyclerView = root.findViewById(R.id.rview_Best)

        token = requireContext().getSharedPreferences("LOGIN", AppCompatActivity.MODE_PRIVATE)
            .getString("TOKEN", "").toString()

        setLayout()
        return root
    }

    fun setLayout() {

        adapter = AdapterBookTabBest(requireContext(), items, best)
        val assetManager = requireActivity().assets

        tabViewModel!!.text.observe(viewLifecycleOwner, { tabNum: String? ->
            when (tabNum) {
                "TAB1" -> {
                    best = "realtime"
                }
                "TAB2" -> {
                    best = "today"
                }
                "TAB3" -> {
                    best = "weekly"
                }
                else -> {
                    best = "monthly"
                }
            }

            getMainBookData(recyclerView, adapter, assetManager, "Best_Tab.json")

        })
    }

    private fun getMainBookData(
        recyclerView: RecyclerView?,
        adapter: AdapterBookTabBest?,
        assetManager: AssetManager,
        BookType: String?
    ) {
        val linearLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        try {
            val `is` = assetManager.open(BookType!!)
            val isr = InputStreamReader(`is`)
            val reader = BufferedReader(isr)
            val buffer = StringBuilder()
            var line = reader.readLine()
            while (line != null) {
                buffer.append(line).append("\n")
                line = reader.readLine()
            }
            val jsonData = buffer.toString()
            val jsonObject = JSONObject(jsonData)
            val flag = jsonObject.getJSONArray("main_info")

            for (i in 0 until flag.length()) {
                val jo = flag.getJSONObject(i)
                val sectionType = jo.getString("section_type")
                val sectionCategory = jo.getString("section_category")
                val sectionSubType = jo.getString("section_sub_type")

                if (i in 0..6) {
                    items.add(
                        MainBookData(
                            sectionCategory,
                            sectionSubType,
                            sectionType
                        )
                    )
                }
            }

            recyclerView!!.layoutManager = linearLayoutManager
            recyclerView!!.adapter = adapter
            adapter!!.notifyDataSetChanged()

        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"
        fun newInstance(index: Int): BestTab {
            val fragment = BestTab()
            val bundle = Bundle()
            bundle.putInt(ARG_SECTION_NUMBER, index)
            fragment.arguments = bundle
            return fragment
        }
    }
}