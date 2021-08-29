package bigbigdw.joaradw.fragment_main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import bigbigdw.joaradw.R
import bigbigdw.joaradw.base.BookBaseFragment
import bigbigdw.joaradw.fragment_new.NewTab
import bigbigdw.joaradw.main.TabViewModel

class FragmentMainTabPage : BookBaseFragment() {

    private var tabviewmodel: TabViewModel? = null

    private var tab1Title1: TextView? = null
    private var tab1Title2: TextView? = null
    private var tab1More: TextView? = null
    private var tab1RecyclerView: RecyclerView? = null

    var Tab2_Title1: TextView? = null
    var Tab2_Title2: TextView? = null
    var Tab2_More: TextView? = null
    var Tab2_RecyclerView: RecyclerView? = null

    var Tab3_Title1: TextView? = null
    var Tab3_Title2: TextView? = null
    var Tab3_More: TextView? = null
    var Tab3_RecyclerView: RecyclerView? = null

    var Tab4_Title1: TextView? = null
    var Tab4_Title2: TextView? = null
    var Tab4_More: TextView? = null
    var Tab4_RecyclerView: RecyclerView? = null

    var Tab5_Title1: TextView? = null
    var Tab5_Title2: TextView? = null
    var Tab5_More: TextView? = null
    var Tab5_RecyclerView: RecyclerView? = null

    var Tab6_Title1: TextView? = null
    var Tab6_Title2: TextView? = null
    var Tab6_More: TextView? = null
    var Tab6_RecyclerView: RecyclerView? = null

    var Tab7_Title1: TextView? = null
    var Tab7_Title2: TextView? = null
    var Tab7_More: TextView? = null
    var Tab7_RecyclerView: RecyclerView? = null

    var Tab8_Title1: TextView? = null
    var Tab8_Title2: TextView? = null
    var Tab8_More: TextView? = null
    var Tab8_RecyclerView: RecyclerView? = null

    var Tab9_Title1: TextView? = null
    var Tab9_Title2: TextView? = null
    var Tab9_More: TextView? = null
    var Tab9_RecyclerView: RecyclerView? = null

    var Tab10_Title1: TextView? = null
    var Tab10_Title2: TextView? = null
    var Tab10_More: TextView? = null
    var Tab10_RecyclerView: RecyclerView? = null

    var Tab11_Title1: TextView? = null
    var Tab11_Title2: TextView? = null
    var Tab11_More: TextView? = null
    var Tab11_RecyclerView: RecyclerView? = null

    var Tab12_Title1: TextView? = null
    var Tab12_Title2: TextView? = null
    var Tab12_More: TextView? = null
    var Tab12_RecyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tabviewmodel = ViewModelProvider(this).get(TabViewModel::class.java)
        var index = 1
        if (arguments != null) {
            index = requireArguments().getInt(FragmentMainTabPage.ARG_SECTION_NUMBER)
        }
        tabviewmodel!!.setIndex(index)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_main_tab, container, false)

        tab1Title1 = root.findViewById(R.id.Tab1_Title1)
        tab1Title2 = root.findViewById(R.id.Tab1_Title2)
        tab1More = root.findViewById(R.id.Tab1_More)
        tab1RecyclerView = root.findViewById(R.id.Tab1_RecyclerView)

        Tab2_Title1 = root.findViewById(R.id.Tab2_Title1)
        Tab2_Title2 = root.findViewById(R.id.Tab2_Title2)
        Tab2_More = root.findViewById(R.id.Tab2_More)
        Tab2_RecyclerView = root.findViewById(R.id.Tab2_RecyclerView)

        Tab3_Title1 = root.findViewById(R.id.Tab3_Title1)
        Tab3_Title2 = root.findViewById(R.id.Tab3_Title2)
        Tab3_More = root.findViewById(R.id.Tab3_More)
        Tab3_RecyclerView = root.findViewById(R.id.Tab3_RecyclerView)

        Tab4_Title1 = root.findViewById(R.id.Tab4_Title1)
        Tab4_Title2 = root.findViewById(R.id.Tab4_Title2)
        Tab4_More = root.findViewById(R.id.Tab4_More)
        Tab4_RecyclerView = root.findViewById(R.id.Tab4_RecyclerView)

        Tab5_Title1 = root.findViewById(R.id.Tab5_Title1)
        Tab5_Title2 = root.findViewById(R.id.Tab5_Title2)
        Tab5_More = root.findViewById(R.id.Tab5_More)
        Tab5_RecyclerView = root.findViewById(R.id.Tab5_RecyclerView)

        Tab6_Title1 = root.findViewById(R.id.Tab6_Title1)
        Tab6_Title2 = root.findViewById(R.id.Tab6_Title2)
        Tab6_More = root.findViewById(R.id.Tab6_More)
        Tab6_RecyclerView = root.findViewById(R.id.Tab6_RecyclerView)

        Tab7_Title1 = root.findViewById(R.id.Tab7_Title1)
        Tab7_Title2 = root.findViewById(R.id.Tab7_Title2)
        Tab7_More = root.findViewById(R.id.Tab7_More)
        Tab7_RecyclerView = root.findViewById(R.id.Tab7_RecyclerView)

        Tab8_Title1 = root.findViewById(R.id.Tab8_Title1)
        Tab8_Title2 = root.findViewById(R.id.Tab8_Title2)
        Tab8_More = root.findViewById(R.id.Tab8_More)
        Tab8_RecyclerView = root.findViewById(R.id.Tab8_RecyclerView)

        Tab9_Title1 = root.findViewById(R.id.Tab9_Title1)
        Tab9_Title2 = root.findViewById(R.id.Tab9_Title2)
        Tab9_More = root.findViewById(R.id.Tab9_More)
        Tab9_RecyclerView = root.findViewById(R.id.Tab9_RecyclerView)

        Tab10_Title1 = root.findViewById(R.id.Tab10_Title1)
        Tab10_Title2 = root.findViewById(R.id.Tab10_Title2)
        Tab10_More = root.findViewById(R.id.Tab10_More)
        Tab10_RecyclerView = root.findViewById(R.id.Tab10_RecyclerView)

        Tab11_Title1 = root.findViewById(R.id.Tab11_Title1)
        Tab11_Title2 = root.findViewById(R.id.Tab11_Title2)
        Tab11_More = root.findViewById(R.id.Tab11_More)
        Tab11_RecyclerView = root.findViewById(R.id.Tab11_RecyclerView)

        Tab12_Title1 = root.findViewById(R.id.Tab12_Title1)
        Tab12_Title2 = root.findViewById(R.id.Tab12_Title2)
        Tab12_More = root.findViewById(R.id.Tab12_More)
        Tab12_RecyclerView = root.findViewById(R.id.Tab12_RecyclerView)

        setLayout()

        return root
    }

    fun setLayout() {


    }

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"
        fun newInstance(index: Int): FragmentMainTabPage {
            val fragment = FragmentMainTabPage()
            val bundle = Bundle()
            bundle.putInt(ARG_SECTION_NUMBER, index)
            fragment.arguments = bundle
            return fragment
        }
    }
}