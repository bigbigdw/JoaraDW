package bigbigdw.joaradw.Fragment_New;

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

import bigbigdw.joaradw.R;
import bigbigdw.joaradw.databinding.FragmentNewBinding;

public class Fragment_New extends Fragment {
    int TabNum = 0;
    private FragmentNewBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentNewBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getContext(), getChildFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout Fragment_NewTab = binding.NewTab;
        Fragment_NewTab.setupWithViewPager(viewPager);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            TabNum = bundle.getInt("TabNum");
            viewPager.setCurrentItem(TabNum);
        }


        return root;
    }

    public static class SectionsPagerAdapter extends FragmentPagerAdapter {

        private final int[] TAB_TITLES = new int[]{R.string.New_Tab1, R.string.New_Tab2, R.string.New_Tab3, R.string.New_Tab4, R.string.New_Tab5, R.string.New_Tab6, R.string.New_Tab7, R.string.New_Tab8, R.string.New_Tab9};
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
