package bigbigdw.joaradw.fragment_main;

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

import bigbigdw.joaradw.base.BookBaseFragment;
import bigbigdw.joaradw.fragment_fav.FavTabFav;
import bigbigdw.joaradw.fragment_fav.FavTabHistory;
import bigbigdw.joaradw.fragment_fav.FragmentFav;
import bigbigdw.joaradw.R;
import bigbigdw.joaradw.test.FragmentTest;


public class FragmentMain extends BookBaseFragment implements InterfaceMainBannerAPI {

    int tabNum = 0;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        ViewPager viewPager = root.findViewById(R.id.view_pager);
        setupViewPager(viewPager);
        TabLayout tabLayout = root.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            tabNum = bundle.getInt("TabNum");
            viewPager.setCurrentItem(tabNum);
        }

        return root;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new FragmentTabMain(), "선호 작품");
        adapter.addFragment(new FragmentTest(), "판타지");
        adapter.addFragment(new FragmentTest(), "무협");
        adapter.addFragment(new FragmentTest(), "퓨전");
        adapter.addFragment(new FragmentTest(), "게임");
        adapter.addFragment(new FragmentTest(), "로맨스");
        adapter.addFragment(new FragmentTest(), "로맨스 판타지");
        adapter.addFragment(new FragmentTest(), "BL");
        adapter.addFragment(new FragmentTest(), "GL");
        adapter.addFragment(new FragmentTest(), "스포츠");
        adapter.addFragment(new FragmentTest(), "역사");
        adapter.addFragment(new FragmentTest(), "패러디");
        adapter.addFragment(new FragmentTest(), "팬픽");
        adapter.addFragment(new FragmentTest(), "라이트노벨");
        adapter.addFragment(new FragmentTest(), "일반작품");
        adapter.addFragment(new FragmentTest(), "문학작품");
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