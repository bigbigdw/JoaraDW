package bigbigdw.joaradw.joara_post

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import bigbigdw.joaradw.R
import bigbigdw.joaradw.databinding.FragmentPostListBinding
import bigbigdw.joaradw.fragment_new.NewTab

class Fragment_Post_List : Fragment() {

    var tabNum = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentPostListBinding = FragmentPostListBinding.inflate(inflater, container, false)
        val root: View = fragmentPostListBinding.root
        val sectionsPagerAdapter = SectionsPagerAdapter(
            context, childFragmentManager
        )
        val viewPager = fragmentPostListBinding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val fragmentPostTabs = fragmentPostListBinding.postTab
        fragmentPostTabs.setupWithViewPager(viewPager)
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
        private val tabTitles = intArrayOf(
            R.string.New_Tab1,
            R.string.New_Tab2
        )

        override fun getItem(position: Int): Fragment {
            return NewTab.newInstance(position + 1)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return mContext!!.resources.getString(tabTitles[position])
        }

        override fun getCount(): Int {
            return tabTitles.size
        }
    }
}