package bigbigdw.joaradw.fragment_new;

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
import bigbigdw.joaradw.databinding.FragmentNewBinding;

public class FragmentNew extends Fragment {
    int tabNum = 0;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        FragmentNewBinding fragmentNewBinding = FragmentNewBinding.inflate(inflater, container, false);
        View root = fragmentNewBinding.getRoot();

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getContext(), getChildFragmentManager());
        ViewPager viewPager = fragmentNewBinding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout fragmentNewTab = fragmentNewBinding.NewTab;
        fragmentNewTab.setupWithViewPager(viewPager);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            tabNum = bundle.getInt("TabNum");
            viewPager.setCurrentItem(tabNum);
        }


        return root;
    }

    public static class SectionsPagerAdapter extends FragmentPagerAdapter {

        private final int[] tabTitles = new int[]{R.string.New_Tab1, R.string.New_Tab2, R.string.New_Tab3, R.string.New_Tab4, R.string.New_Tab5, R.string.New_Tab6, R.string.New_Tab7, R.string.New_Tab8, R.string.New_Tab9};
        private final Context mContext;

        public SectionsPagerAdapter(Context context, FragmentManager fm) {
            super(fm);
            mContext = context;
        }

        @NotNull
        @Override
        public Fragment getItem(int position) {
            return NewTab.newInstance(position + 1);
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
