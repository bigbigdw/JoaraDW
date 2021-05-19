package Bigbigdw.JoaraDW.Fragment_Best;

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

import Bigbigdw.JoaraDW.BookList.Best_Tab;
import Bigbigdw.JoaraDW.R;
import Bigbigdw.JoaraDW.databinding.FragmentBestBinding;

public class Fragment_Best  extends Fragment {
    private FragmentBestBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBestBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Fragment_Best.SectionsPagerAdapter sectionsPagerAdapter = new Fragment_Best.SectionsPagerAdapter(getContext(), getChildFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout Fragment_BestTab = binding.tabs;
        Fragment_BestTab.setupWithViewPager(viewPager);

        return root;
    }

    public static class SectionsPagerAdapter extends FragmentPagerAdapter {

        private final int[] TAB_TITLES = new int[]{R.string.Best_Tab1, R.string.Best_Tab2, R.string.Best_Tab3, R.string.Best_Tab4};
        private final Context mContext;

        public SectionsPagerAdapter(Context context, FragmentManager fm) {
            super(fm);
            mContext = context;
        }

        @Override
        public Fragment getItem(int position) {
            return Best_Tab.newInstance(position + 1);
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
