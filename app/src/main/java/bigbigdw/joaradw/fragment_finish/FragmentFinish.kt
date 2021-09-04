package bigbigdw.joaradw.fragment_finish

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import bigbigdw.joaradw.R
import bigbigdw.joaradw.databinding.FragmentFinishBinding

class FragmentFinish : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentFinishBinding = FragmentFinishBinding.inflate(inflater, container, false)
        val root: View = fragmentFinishBinding.root
        val sectionsPagerAdapter = SectionsPagerAdapter(
            context, childFragmentManager
        )
        val viewPager = fragmentFinishBinding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val fragmentNewTab = fragmentFinishBinding.tabs
        fragmentNewTab.setupWithViewPager(viewPager)
        return root
    }

    class SectionsPagerAdapter(private val mContext: Context?, fm: FragmentManager?) :
        FragmentPagerAdapter(
            fm!!
        ) {
        private val tabTitles = intArrayOf(
            R.string.Finish_Tab1,
            R.string.Finish_Tab2,
            R.string.Finish_Tab3,
            R.string.Finish_Tab4
        )

        override fun getItem(position: Int): Fragment {
            return FinishTab.newInstance(position + 1)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return mContext!!.resources.getString(tabTitles[position])
        }

        override fun getCount(): Int {
            return tabTitles.size
        }
    }
}