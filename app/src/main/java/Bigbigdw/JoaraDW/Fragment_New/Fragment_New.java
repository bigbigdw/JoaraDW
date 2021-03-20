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

public class Fragment_New extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_new, container, false);

        viewPager = root.findViewById(R.id.view_pager);
        setupViewPager(viewPager);

        tabLayout = root.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                Toast.makeText(getActivity().getApplicationContext(),"adasd",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

//        System.out.println(TabTitle);


        return root;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new New_Tab_New(), "전체");
        adapter.addFragment(new New_Tab_77FES(), "77FES");
        adapter.addFragment(new New_Tab_New(), "기다무");
        adapter.addFragment(new New_Tab_New(), "노블성실");
        adapter.addFragment(new New_Tab_New(), "노블레스");
        adapter.addFragment(new New_Tab_New(), "프리미엄");
        adapter.addFragment(new New_Tab_New(), "무료");
        adapter.addFragment(new New_Tab_New(), "완결");
        adapter.addFragment(new New_Tab_New(), "단편");
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
            ;
            return mFragmentTitleList.get(position);
        }
    }

}
