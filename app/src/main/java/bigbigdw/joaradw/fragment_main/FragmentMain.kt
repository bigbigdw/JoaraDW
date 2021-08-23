package bigbigdw.joaradw.fragment_main

import bigbigdw.joaradw.base.BookBaseFragment
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import bigbigdw.joaradw.R
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import bigbigdw.joaradw.test.FragmentTest
import androidx.fragment.app.FragmentPagerAdapter
import java.util.ArrayList

class FragmentMain : BookBaseFragment(), InterfaceMainBannerAPI {
    var tabNum = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_main, container, false)
        val viewPager: ViewPager = root.findViewById(R.id.view_pager)
        setupViewPager(viewPager)
        val tabLayout: TabLayout = root.findViewById(R.id.tabs)
        tabLayout.setupWithViewPager(viewPager)
        val bundle = this.arguments
        if (bundle != null) {
            tabNum = bundle.getInt("TabNum")
            viewPager.currentItem = tabNum
        }
        return root
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(
            childFragmentManager
        )
        adapter.addFragment(FragmentMainTabFirst(), "선호 작품")
        adapter.addFragment(FragmentTest(), "판타지")
        adapter.addFragment(FragmentTest(), "무협")
        adapter.addFragment(FragmentTest(), "퓨전")
        adapter.addFragment(FragmentTest(), "게임")
        adapter.addFragment(FragmentTest(), "로맨스")
        adapter.addFragment(FragmentTest(), "로맨스 판타지")
        adapter.addFragment(FragmentTest(), "BL")
        adapter.addFragment(FragmentTest(), "GL")
        adapter.addFragment(FragmentTest(), "스포츠")
        adapter.addFragment(FragmentTest(), "역사")
        adapter.addFragment(FragmentTest(), "패러디")
        adapter.addFragment(FragmentTest(), "팬픽")
        adapter.addFragment(FragmentTest(), "라이트노벨")
        adapter.addFragment(FragmentTest(), "일반작품")
        adapter.addFragment(FragmentTest(), "문학작품")
        viewPager.adapter = adapter
    }

    class ViewPagerAdapter(manager: FragmentManager?) : FragmentPagerAdapter(
        manager!!
    ) {
        private val mFragmentList: MutableList<Fragment> = ArrayList()
        private val mFragmentTitleList: MutableList<String> = ArrayList()
        override fun getItem(position: Int): Fragment {
            return mFragmentList[position]
        }

        override fun getCount(): Int {
            return mFragmentList.size
        }

        fun addFragment(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return mFragmentTitleList[position]
        }
    }
}