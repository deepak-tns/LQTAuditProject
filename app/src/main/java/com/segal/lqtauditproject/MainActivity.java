package com.segal.lqtauditproject;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.segal.lqtauditproject.Adapter.RecyclerAdapter;
import com.segal.lqtauditproject.Data.LQTAuditDataSource;
import com.segal.lqtauditproject.Data.WebsiteDataSource;
import com.segal.lqtauditproject.Helpers.HttpHelpers;
import com.segal.lqtauditproject.Helpers.PermissionHelpers;
import com.segal.lqtauditproject.Interfaces.MainCallbacks;
import com.segal.lqtauditproject.Interfaces.MapCallbacks;
import com.segal.lqtauditproject.Interfaces.OnRecyclerItemClickListener;
import com.segal.lqtauditproject.Interfaces.SiteListCallbacks;
import com.segal.lqtauditproject.Map.MapFragment;
import com.segal.lqtauditproject.Models.LoginInfo;
import com.segal.lqtauditproject.Models.Site;
import com.segal.lqtauditproject.ViewModels.AntennaViewModel;
import com.segal.lqtauditproject.ViewModels.ApiAntennaResult;
import com.segal.lqtauditproject.ViewModels.ApiSiteResult;
import com.segal.lqtauditproject.ViewModels.ApiSiteResultList;
import com.segal.lqtauditproject.ViewModels.SiteListItem;

import junit.framework.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements MainCallbacks {

    private static final String TAG = "MAIN";
    static final int SITE_DETAILS_REQUEST_CODE = 6632;
    SiteListCallbacks siteListCallbacks;
    MapCallbacks mapCallbacks;
    String token;
    private LQTAuditDataSource dataSource;

    private Handler handler;
    private List<ApiSiteResult> apiSites = new ArrayList<>();

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        token = getIntent().getStringExtra("token");
        userId = getIntent().getStringExtra("userId");



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        toolbar.set
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                Intent siteDetailsIntent = new Intent(MainActivity.this, SiteDetailsActivity.class);
                startActivityForResult(siteDetailsIntent, SITE_DETAILS_REQUEST_CODE);
            }
        });
        PermissionHelpers.askForPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE, PermissionHelpers.WRITE_EXST);
        PermissionHelpers.askForPermission(this, Manifest.permission.ACCESS_FINE_LOCATION, PermissionHelpers.ACCESS_FINE_LOCATION);
        PermissionHelpers.askForPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION, PermissionHelpers.ACCESS_COARSE_LOCATION);


        dataSource = new LQTAuditDataSource();

        dataSource.open();

        if(token == null || userId == null){
            LoginInfo user = dataSource.getLastLoginInfo();
            token = user.getToken();
            userId = user.getUserId();
        }
        handler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                for (ApiSiteResult apiSite : apiSites) {
                    Site existingSite = dataSource.getSiteBySiteId(apiSite.SiteId);
                    if (existingSite == null) {
                        Site s = new Site();
                        s.setSiteId(apiSite.SiteId);
                        s.setSiteName(apiSite.SiteName);
                        s.setPlanLatitude(apiSite.PlanLatitude);
                        s.setPlanLongitude(apiSite.PlanLongitude);
                        s.setStatus(apiSite.Status);
                        s.setExtra1ImagePath(apiSite.SubContractorComments);
                        s.setExtra2ImagePath(apiSite.VendorComments);
                        s.setUserId(userId);
                        for (ApiAntennaResult antenna : apiSite.Antennas) {
                            AntennaViewModel a = new AntennaViewModel();
                            a.setPlanAntennaName(antenna.PlannedAntenna);
                            a.setPlanAzimuth(antenna.PlannedAzimuth);
                            a.setPlanMTilt(antenna.PlannedMTilt);
                            a.setPlanHeight(antenna.PlannedHeight);
                            a.setPlanETilt900(antenna.PlannedETilt900);
                            a.setPlanETilt1800(antenna.PlannedETilt1800);
                            a.setPlanETilt2100(antenna.PlannedETilt2100);
                            a.setPlanETilt2600(antenna.PlannedETilt2600);
                            a.setPlanETilt3500(antenna.PlannedETilt3500);

                            s.addAntenna(a, antenna.SectorIndex, antenna.AntennaIndex);
                        }
                        dataSource.createSite(s);
                    } else {
                        dataSource.updateSiteWithApiSiteResult(existingSite.getId(), apiSite);
                    }
                }
                siteListCallbacks.notifySitesChanged();
                mapCallbacks.addMarkers();
            }
        };
        if (apiSites == null || apiSites.size() < 1)
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET)
                    == PackageManager.PERMISSION_GRANTED) {
                Call response = HttpHelpers.getAsync(HttpHelpers.ROOTURL + "api/SitesApi/GetSites?token=" + token, new Callback() {

                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d("OKHTTP", "Failed: " + e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String res = response.body().string();
                        apiSites = new Gson().fromJson(res, new TypeToken<ArrayList<ApiSiteResult>>() {
                        }.getType());
                        handler.sendEmptyMessage(0);
                    }
                });
            }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dataSource.close();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            siteListCallbacks.notifySitesChanged();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PermissionHelpers.ACCESS_FINE_LOCATION) {
            if (permissions.length == 1 &&
                    permissions[0] == Manifest.permission.ACCESS_FINE_LOCATION &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mapCallbacks.setMapSettings();
            } else {
                // Permission was denied. Display an error message.
            }
        }

        if (requestCode == PermissionHelpers.Internet) {
            if (permissions.length == 1 &&
                    permissions[0] == Manifest.permission.INTERNET &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mapCallbacks.setMapSettings();
            } else {
                // Permission was denied. Display an error message.
            }
        }
    }

    @Override
    public void onBackPressed() {
        this.finishAffinity();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_log_off) {
            dataSource.removeLoginInfo();
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setSiteListCallbacks(SiteListCallbacks siteListCallbacks) {
        this.siteListCallbacks = siteListCallbacks;
    }

    @Override
    public void setMapCallbacks(MapCallbacks mapCallbacks) {
        this.mapCallbacks = mapCallbacks;
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements SiteListCallbacks {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private static final int EDIT_SITE_REQUEST_CODE = 2146;
        private View rootView;
        private LQTAuditDataSource dataSource;
        private List<Site> sites;
        private RecyclerView recyclerView;
        private RecyclerAdapter adapter;


        public PlaceholderFragment() {
        }


        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.fragment_main, container, false);
            dataSource = new LQTAuditDataSource();
            dataSource.open();
            LoginInfo user = dataSource.getLastLoginInfo();
            sites = dataSource.getAllSites(user.getUserId());

            setUpRecyclerView();


            return rootView;
        }


        @Override
        public void onDestroy() {
            super.onDestroy();
            dataSource.close();
        }


        private void setUpRecyclerView() {

            recyclerView = rootView.findViewById(R.id.recycler_view);
            adapter = new RecyclerAdapter(getActivity(), SiteListItem.getSiteListItemsFrom(sites));
            recyclerView.setAdapter(adapter);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(linearLayoutManager);

            recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(getActivity(), recyclerView, new OnRecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Intent intent = new Intent(getActivity(), SiteDetailsActivity.class);
                            intent.putExtra("id", sites.get(position).getId());
                            startActivityForResult(intent, EDIT_SITE_REQUEST_CODE);
                        }

                        @Override
                        public void onLongItemClick(View view, int position) {
                            // do whatever
//                            Toast.makeText(getActivity(), "NAME: " + sites.get(position).getSiteName(), Toast.LENGTH_LONG).show();

                        }
                    })
            );
        }

        @Override
        public void notifySitesChanged(int position) {
            sites = dataSource.getCurrentUserSites();
            adapter.setDataSet(SiteListItem.getSiteListItemsFrom(sites));
            adapter.notifyItemChanged(position);
        }

        @Override
        public void notifySitesChanged() {

            sites = dataSource.getCurrentUserSites();
            adapter.setDataSet(SiteListItem.getSiteListItemsFrom(sites));
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onAttach(Context context) {
            super.onAttach(context);

            ((MainCallbacks) context).setSiteListCallbacks(this);
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            if (position == 1)
                return new MapFragment();
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }
    }
}
