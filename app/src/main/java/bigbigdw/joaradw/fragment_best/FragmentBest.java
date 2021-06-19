package bigbigdw.joaradw.fragment_best;

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

import org.jetbrains.annotations.NotNull;

import bigbigdw.joaradw.R;
import bigbigdw.joaradw.databinding.FragmentBestBinding;

public class FragmentBest extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        bigbigdw.joaradw.databinding.FragmentBestBinding binding = FragmentBestBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        FragmentBest.SectionsPagerAdapter sectionsPagerAdapter = new FragmentBest.SectionsPagerAdapter(getContext(), getChildFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout fragmentBestTab = binding.tabs;
        fragmentBestTab.setupWithViewPager(viewPager);

        return root;
    }

    public static class SectionsPagerAdapter extends FragmentPagerAdapter {

        private final int[] tabTitles = new int[]{R.string.Best_Tab1, R.string.Best_Tab2, R.string.Best_Tab3, R.string.Best_Tab4};
        private final Context mContext;

        public SectionsPagerAdapter(Context context, FragmentManager fm) {
            super(fm);
            mContext = context;
        }

        @NotNull
        @Override
        public Fragment getItem(int position) {
            return BestTab.newInstance(position + 1);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mContext.getResources().getString(tabTitles[position]);
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }
    }
}
