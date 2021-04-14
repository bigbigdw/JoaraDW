package Bigbigdw.JoaraDW.Fragment_New;

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

import Bigbigdw.JoaraDW.R;
import Bigbigdw.JoaraDW.Test.Fragment_Test;
import Bigbigdw.JoaraDW.Test.Fragment_Test_Z;
import Bigbigdw.JoaraDW.Test.Test;

public class Fragment_New extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_new, container, false);

        viewPager = root.findViewById(R.id.view_pager);
        setupViewPager(viewPager);

        tabLayout = root.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        return root;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new Fragment_Test(), "전체");
        adapter.addFragment(new New_Tab_77FES(), "77FES");
        adapter.addFragment(new New_Tab_Kidamu(), "기다무");
        adapter.addFragment(new New_Tab_Promised(), "노블성실");
        adapter.addFragment(new Fragment_Test(), "노블레스");
        adapter.addFragment(new Fragment_Test_Z(), "프리미엄");
        adapter.addFragment(new New_Tab_ALL(), "무료");
        adapter.addFragment(new New_Tab_ALL(), "완결");
        adapter.addFragment(new New_Tab_ALL(), "단편");
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
