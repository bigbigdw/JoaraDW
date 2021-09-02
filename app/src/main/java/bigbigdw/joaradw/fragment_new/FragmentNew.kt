package bigbigdw.joaradw.fragment_new

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import bigbigdw.joaradw.databinding.FragmentNewBinding


class FragmentNew : Fragment() {
    var tabNum = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val fragmentNewBinding = FragmentNewBinding.inflate(inflater, container, false)

        val root: View = fragmentNewBinding.root

        val sectionsPagerAdapter = SectionsPagerAdapter(
            context, childFragmentManager
        )

        val viewPager = fragmentNewBinding.viewPager
        viewPager.adapter = sectionsPagerAdapter

        val fragmentNewTab = fragmentNewBinding.postTab
        fragmentNewTab.setupWithViewPager(viewPager)

        val bundle = this.arguments

        if (bundle != null) {
            tabNum = bundle.getInt("TabNum")
            viewPager.currentItem = tabNum
        }

        return root
    }

    class SectionsPagerAdapter(mContext: Context?, fm: FragmentManager?) :
        FragmentPagerAdapter(
            fm!!
        ) {
        private val titleValues = mContext!!.getSharedPreferences("MAIN_MENU", AppCompatActivity.MODE_PRIVATE).getString("NEW_TITLE", "!!!!")!!.replace("[","").replace("]","")
        private val titleList: List<String> = titleValues!!.split(',').toList()

        override fun getItem(position: Int): Fragment {
            return NewTab.newInstance(position)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titleList[position]
        }

        override fun getCount(): Int {
            return titleList.size
        }
    }
}