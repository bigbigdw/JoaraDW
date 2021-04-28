package Bigbigdw.JoaraDW.Fragment_New;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import Bigbigdw.JoaraDW.R;
import Bigbigdw.JoaraDW.Test.Fragment_Test;
import Bigbigdw.JoaraDW.Test.Fragment_Test_Z;
import Bigbigdw.JoaraDW.Test.Test;

public class Fragment_New extends Fragment {

    private TabLayout Fragment_NewTab;
    private ViewPager viewPager;
    int TabNum = 0;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_new, container, false);

        Bundle bundle = this.getArguments();
        viewPager = root.findViewById(R.id.view_pager);
        setupViewPager(viewPager);

        if (bundle != null) {
            TabNum = bundle.getInt("TabNum");
            viewPager.setCurrentItem(TabNum);
        }

        Fragment_NewTab = root.findViewById(R.id.Fragment_NewTab);
        Fragment_NewTab.setupWithViewPager(viewPager);


        return root;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new New_Tab_ALL(), "전체");
        adapter.addFragment(new New_Tab_77FES(), "77FES");
        adapter.addFragment(new New_Tab_Kidamu(), "기다무");
        adapter.addFragment(new New_Tab_Promised(), "노블성실");
        adapter.addFragment(new New_Tab_Nobless(), "노블레스");
        adapter.addFragment(new New_Tab_Premium(), "프리미엄");
        adapter.addFragment(new New_Tab_Series(), "무료");
        adapter.addFragment(new New_Tab_Finished(), "완결");
        adapter.addFragment(new New_Tab_Short(), "단편");
        viewPager.setAdapter(adapter);
    }

    public static class ViewPagerAdapter extends FragmentPagerAdapter {
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
