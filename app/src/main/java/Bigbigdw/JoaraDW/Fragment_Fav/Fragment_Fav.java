package Bigbigdw.JoaraDW.Fragment_Fav;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import Bigbigdw.JoaraDW.Fragment_New.Fragment_New;
import Bigbigdw.JoaraDW.R;


public class Fragment_Fav extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    int TabNum = 0;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_fav, container, false);
        viewPager = root.findViewById(R.id.view_pager);
        setupViewPager(viewPager);
        tabLayout = root.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            TabNum = bundle.getInt("TabNum");
            viewPager.setCurrentItem(TabNum);
        }

        return root;
    }

    private void setupViewPager(ViewPager viewPager) {
        Fragment_New.ViewPagerAdapter adapter = new Fragment_New.ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new Fav_Tab_Fav(), "선호 작품");
        adapter.addFragment(new Fav_Tab_History(), "이어보기");
        viewPager.setAdapter(adapter);
    }

    public  class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
