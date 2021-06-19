package bigbigdw.joaradw.fragment_finish;

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
import bigbigdw.joaradw.databinding.FragmentFinishBinding;

public class FragmentFinish extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        FragmentFinishBinding fragmentFinishBinding = FragmentFinishBinding.inflate(inflater, container, false);
        View root = fragmentFinishBinding.getRoot();

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getContext(), getChildFragmentManager());
        ViewPager viewPager = fragmentFinishBinding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout fragmentNewTab = fragmentFinishBinding.tabs;
        fragmentNewTab.setupWithViewPager(viewPager);

        return root;
    }

    public static class SectionsPagerAdapter extends FragmentPagerAdapter {

        private final int[] tabTitles = new int[]{R.string.Finish_Tab1, R.string.Finish_Tab2, R.string.Finish_Tab3, R.string.Finish_Tab4};
        private final Context mContext;

        public SectionsPagerAdapter(Context context, FragmentManager fm) {
            super(fm);
            mContext = context;
        }

        @NotNull
        @Override
        public Fragment getItem(int position) {
            return FinishTab.newInstance(position + 1);
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
