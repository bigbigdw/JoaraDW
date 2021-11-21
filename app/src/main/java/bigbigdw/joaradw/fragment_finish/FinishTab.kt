package bigbigdw.joaradw.fragment_finish

import android.content.res.AssetManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import bigbigdw.joaradw.R
import bigbigdw.joaradw.novel.MainBookData
import bigbigdw.joaradw.novel.TabViewModel
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.ArrayList

class FinishTab : Fragment() {

    private var tabViewModel: TabViewModel? = null
    lateinit var root : View
    var type = ""
    var token : String? = null

    private var adapter: AdapterBookTabFinish? = null
    private val items = ArrayList<MainBookData?>()
    var recyclerView: RecyclerView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tabViewModel = ViewModelProvider(this).get(TabViewModel::class.java)
        var index = 1
        if (arguments != null) {
            index = requireArguments().getInt(ARG_SECTION_NUMBER)
        }
        tabViewModel!!.setIndex(index)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_finish_tab, container, false)

        token = requireContext()!!.getSharedPreferences("LOGIN", AppCompatActivity.MODE_PRIVATE)
            .getString("TOKEN", "")

        recyclerView = root.findViewById(R.id.rview_Finish)

        setLayout()

        return root
    }

    fun setLayout() {

        adapter = AdapterBookTabFinish(requireContext(), items, type)
        val assetManager = requireActivity().assets

        tabViewModel!!.text.observe(viewLifecycleOwner, { tabNum: String? ->
            type = when (tabNum) {
                "TAB1" -> "redate"
                "TAB2" -> "cnt_page_read"
                "TAB3" -> "cnt_favorite"
                else -> "cnt_recom"
            }

            getMainBookData(recyclerView, adapter, assetManager, "Finish_Tab.json")

        })
    }

    private fun getMainBookData(
        recyclerView: RecyclerView?,
        adapter: AdapterBookTabFinish?,
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

                if (i in 0..2) {
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
        fun newInstance(index: Int): FinishTab {
            val fragment = FinishTab()
            val bundle = Bundle()
            bundle.putInt(ARG_SECTION_NUMBER, index)
            fragment.arguments = bundle
            return fragment
        }
    }
}