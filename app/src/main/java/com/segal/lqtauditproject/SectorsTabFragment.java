package com.segal.lqtauditproject;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.EventLogTags;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.segal.lqtauditproject.Interfaces.SectorCallbacks;
import com.segal.lqtauditproject.Interfaces.SiteDetailsCallbacks;
import com.segal.lqtauditproject.R;

import java.util.List;

import static com.segal.lqtauditproject.Helpers.ViewHelpers.getViewsByTag;

/**
 * Created by Hossein on 10/26/2017.
 */

public class SectorsTabFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";

    SiteDetailsCallbacks activity;
    private String editingId;

    public SectorsTabFragment() {

    }

    public static SectorsTabFragment newInstance(Bundle bundle) {
        SectorsTabFragment fragment = new SectorsTabFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            editingId = getArguments().getString("id");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_site_sectors, container, false);
        ViewPager mViewPager = view.findViewById(R.id.site_sectors_container);
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = view.findViewById(R.id.sectors_tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        mViewPager.setOffscreenPageLimit(3);

        if (editingId != null && editingId.length() > 1)
            mSectionsPagerAdapter.setEditingId(editingId);

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.activity = (SiteDetailsCallbacks) context;
    }

    private class SectionsPagerAdapter extends FragmentPagerAdapter {

        private String editingId;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void setEditingId(String id) {
            editingId = id;
        }

        @Override
        public Fragment getItem(int position) {
            Bundle b = new Bundle();
            if (editingId != null) {
                b.putString("id", editingId);
            }
            b.putInt("position", position + 1);
            return SectorFragment.newInstance(b);

//            switch (position) {
//                case 0:
//                    return SectorFragment.newInstance(1);
//                default:
//                    return SectorFragment.newInstance(2);
//            }

        }

        @Override
        public int getCount() {
            // Show 4 total pages.
            return 4;
        }


        @Override
        public CharSequence getPageTitle(int position) {

            return "SECTOR " + (position + 1);
//            switch (position) {
//                case 0:
//                    return "Sector ";
//                default:
//                    return "Nested 2";
//            }


        }
    }


}