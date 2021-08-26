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
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import androidx.fragment.app.FragmentPagerAdapter
import bigbigdw.joaradw.R
import bigbigdw.joaradw.databinding.FragmentNewBinding
import bigbigdw.joaradw.fragment_new.NewTab
import bigbigdw.joaradw.util.MainTabInfoValue
import java.util.ArrayList
import android.R.string
import androidx.core.content.ContentProviderCompat.requireContext


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

    class SectionsPagerAdapter(private val mContext: Context?, fm: FragmentManager?) :
        FragmentPagerAdapter(
            fm!!
        ) {
        val TitleValues = mContext!!.getSharedPreferences("MAIN_MENU", AppCompatActivity.MODE_PRIVATE).getString("NEW", "!!!!")!!.replace("[","").replace("]","")

        val TitleList: List<String> = TitleValues!!.split(',').toList()

        private val tabTitles = intArrayOf(
            R.string.New_Tab1,
            R.string.New_Tab2,
            R.string.New_Tab3,
            R.string.New_Tab4,
            R.string.New_Tab5,
            R.string.New_Tab6,
            R.string.New_Tab7,
            R.string.New_Tab8,
            R.string.New_Tab9
        )

        override fun getItem(position: Int): Fragment {
            return NewTab.newInstance(position + 1)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return TitleList[position]
        }

        override fun getCount(): Int {
            return TitleList.size
        }
    }
}