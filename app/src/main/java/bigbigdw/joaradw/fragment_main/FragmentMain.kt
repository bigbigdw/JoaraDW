package bigbigdw.joaradw.fragment_main

import bigbigdw.joaradw.base.BookBaseFragment
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import bigbigdw.joaradw.databinding.FragmentMainBinding

class FragmentMain : BookBaseFragment() {
    var tabNum = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val fragmentMainBinding = FragmentMainBinding.inflate(inflater, container, false)

        val root: View = fragmentMainBinding.root

        val sectionsPagerAdapter = SectionsPagerAdapter(
            childFragmentManager
        )

        val viewPager = fragmentMainBinding.viewPager
        viewPager.adapter = sectionsPagerAdapter

        val fragmentNewTab = fragmentMainBinding.tabs
        fragmentNewTab.setupWithViewPager(viewPager)

        viewPager.offscreenPageLimit = 1

        val bundle = this.arguments

        if (bundle != null) {
            tabNum = bundle.getInt("TabNum")
            viewPager.currentItem = tabNum
        }

        return root
    }

    class SectionsPagerAdapter(fm: FragmentManager?) :
        FragmentPagerAdapter(
            fm!!
        ) {
        private val titleValues = "전체,판타지,무협,퓨전,게임,로맨스,로맨스 판타지,BL,GL,스포츠,역사,패러디,팬픽,라이트노벨,일반작품,문학작품"
        private val titleList: List<String> = titleValues!!.split(',').toList()

        override fun getItem(position: Int): Fragment {
            return if(position == 0){
                FragmentMainTabFirst.newInstance(position)
            } else {
                FragmentMainTabPage.newInstance(position)
            }
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titleList[position]
        }

        override fun getCount(): Int {
            return titleList.size
        }
    }
}