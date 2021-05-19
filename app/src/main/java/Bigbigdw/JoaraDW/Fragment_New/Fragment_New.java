package Bigbigdw.JoaraDW.Fragment_New;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import Bigbigdw.JoaraDW.BookList.New_Tab;
import Bigbigdw.JoaraDW.R;

public class Fragment_New extends Fragment {

    int TabNum = 0;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_new, container, false);

        ViewPager viewPager = root.findViewById(R.id.view_pager);
        TabLayout fragment_NewTab = root.findViewById(R.id.Fragment_NewTab);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            TabNum = bundle.getInt("TabNum");
            viewPager.setCurrentItem(TabNum);
        }

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getContext(), getFragmentManager());
        viewPager.setAdapter(sectionsPagerAdapter);
        fragment_NewTab.setupWithViewPager(viewPager);

        return root;
    }

    public static class SectionsPagerAdapter extends FragmentPagerAdapter {

        private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_3, R.string.tab_text_4, R.string.tab_text_5, R.string.tab_text_6, R.string.tab_text_7, R.string.tab_text_8, R.string.tab_text_9};
        private final Context mContext;

        public SectionsPagerAdapter(Context context, FragmentManager fm) {
            super(fm);
            mContext = context;
        }

        @Override
        public Fragment getItem(int position) {
            return New_Tab.newInstance(position + 1);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mContext.getResources().getString(TAB_TITLES[position]);
        }

        @Override
        public int getCount() {
            return TAB_TITLES.length;
        }
    }
}
