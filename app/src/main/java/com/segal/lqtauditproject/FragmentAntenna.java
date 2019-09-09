package com.segal.lqtauditproject;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.segal.lqtauditproject.Data.LQTAuditDataSource;
import com.segal.lqtauditproject.Helpers.ImageHelpers;
import com.segal.lqtauditproject.Helpers.StringHelpers;
import com.segal.lqtauditproject.Interfaces.SiteDetailsCallbacks;
import com.segal.lqtauditproject.Interfaces.SiteSection;
import com.segal.lqtauditproject.Models.Site;
import com.segal.lqtauditproject.ViewEvents.SiteDetailsEvents;
import com.segal.lqtauditproject.ViewModels.AntennaViewModel;
import com.segal.lqtauditproject.ViewModels.SiteInfoViewModel;
import com.segal.lqtauditproject.databinding.FragmentAntennaBinding;

import java.util.List;

import static com.segal.lqtauditproject.Helpers.ViewHelpers.getViewsByTag;

/**
 * Created by Hossein on 10/26/2017.
 */

public class FragmentAntenna extends Fragment implements SiteSection {

    private static final String BUNDLE_KEY = "AntennaViewModel";
    private static final String TAG = "Antennaaaaaaaaaaa";
    private SiteDetailsCallbacks activity;
    AntennaViewModel mViewModel;
    SiteDetailsEvents mViewEvents;

    private int antennaIndex;
    private int sectorIndex;
    private LQTAuditDataSource dataSource;

    public static FragmentAntenna newInstance(int position) {
        FragmentAntenna fragment = new FragmentAntenna();
        Bundle args = new Bundle();
        args.putInt("position", position);
        fragment.setArguments(args);

        return fragment;
    }

    public FragmentAntenna() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AntennaViewModel.class);
        if (StringHelpers.isNullOrEmpty(mViewModel.getOnSiteAntennaName()))
            mViewModel.setOnSiteAntennaName("Other");
        if (StringHelpers.isNullOrEmpty(mViewModel.getOnSiteETilt900()))
            mViewModel.setOnSiteETilt900("RET");
        if (StringHelpers.isNullOrEmpty(mViewModel.getOnSiteETilt1800()))
            mViewModel.setOnSiteETilt1800("RET");
        if (StringHelpers.isNullOrEmpty(mViewModel.getOnSiteETilt2100()))
            mViewModel.setOnSiteETilt2100("RET");
        if (StringHelpers.isNullOrEmpty(mViewModel.getOnSiteETilt2600()))
            mViewModel.setOnSiteETilt2600("RET");
        if (StringHelpers.isNullOrEmpty(mViewModel.getOnSiteETilt3500()))
            mViewModel.setOnSiteETilt3500("RET");

        if (StringHelpers.isNullOrEmpty(mViewModel.getRru1Band()))
            mViewModel.setRru1Band("900");
        if (StringHelpers.isNullOrEmpty(mViewModel.getRru2Band()))
            mViewModel.setRru2Band("1800");
        if (StringHelpers.isNullOrEmpty(mViewModel.getRru3Band()))
            mViewModel.setRru3Band("2100");
        if (StringHelpers.isNullOrEmpty(mViewModel.getRru4Band()))
            mViewModel.setRru4Band("2600");
        if (savedInstanceState != null) {
//            String viewModel = savedInstanceState.getString(BUNDLE_KEY);
            antennaIndex = savedInstanceState.getInt("antennaIndex");
            sectorIndex = savedInstanceState.getInt("sectorIndex");
//            Log.d(TAG, "onCreate: " + antennaIndex + ", " + sectorIndex);

//            if (viewModel != null) {
//                mViewModel = new Gson().fromJson(viewModel, AntennaViewModel.class);
//            }
//            Site site = activity.requestSite();
//            mViewModel.fillFrom(site, sectorIndex, antennaIndex);
        } else if (getArguments() != null) {
            antennaIndex = getArguments().getInt("antennaIndex");
            sectorIndex = getArguments().getInt("sectorIndex");
//            String id = getArguments().getString("id");
//            if (id != null) {
//                mViewModel = ViewModelProviders.of(this).get(AntennaViewModel.class);
////                dataSource = new LQTAuditDataSource();
////                dataSource.open();
//
//            }
        }
        requestData();

        mViewEvents = new SiteDetailsEvents(this.getContext());

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (dataSource != null)
            dataSource.close();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        Log.d(TAG, "onSaveInstanceState Antenna: " + sectorIndex + ", " +antennaIndex);
//        outState.putString(BUNDLE_KEY, new Gson().toJson(mViewModel, AntennaViewModel.class));
        outState.putInt("antennaIndex", antennaIndex);
        outState.putInt("sectorIndex", sectorIndex);
        passData();
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        requestData();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        passData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentAntennaBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_antenna, container, false);
        binding.setMViewModel(mViewModel);
        binding.setMViewEvents(mViewEvents);
        requestData();

        View view = binding.getRoot();
        String sec = "A";
        switch (sectorIndex) {
            case 1:
                sec = "A";
                break;
            case 2:
                sec = "B";
                break;
            case 3:
                sec = "C";
                break;
            case 4:
                sec = "D";
                break;

            default:
                sec = "A";
        }
        final String finalSec = sec;
        final String finalNamePrefix = "S" + finalSec + "_" + antennaIndex + "_";
        List<View> camBtn = getViewsByTag(view.getRootView(), "camBtn");
        final Fragment self = this;
        for (final View view1 : camBtn) {
            view1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String tag = view1.getTag().toString().replace("camBtn,", "").trim();
                    int index = (sectorIndex - 1);
                    List<View> imgViews = getViewsByTag(view.getRootView(), "imgView," + tag);
//                    Log.d(TAG, "Image Views: " + imgViews.size() + " index: " + index + " tag:" + tag);
                    ImageView imgView = (ImageView) imgViews.get(index);

                    activity.onCameraBtnClicked(imgView,finalNamePrefix , tag, "_" + sectorIndex + "_" + antennaIndex);
                }
            });
        }

        List<View> imgViews = getViewsByTag(view.getRootView(), "imgView");
        for (final View view1 : imgViews) {
            ImageHelpers.setImageViewPic((ImageView) view1, activity.requestSite().getSiteId(), finalNamePrefix);

            view1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String tag = view1.getTag().toString().replace("imgView,", "");
                    Intent intent = new Intent(getActivity(), ImageViewerActivity.class);
                    intent.putExtra("imageUrl", activity.requestSite().getSiteId() + "/" + activity.requestSite().getSiteId() + "_" + finalNamePrefix  + tag);

                    getActivity().startActivity(intent);
                }
            });
        }


        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.activity = (SiteDetailsCallbacks) context;
        this.activity.addSiteSection(this);
    }

    @Override
    public void passData() {
        this.activity.passAntennaViewModel(mViewModel, sectorIndex, antennaIndex);
    }

    @Override
    public void requestData() {
        Site site = activity.requestSite();
        mViewModel.fillFrom(site, sectorIndex, antennaIndex);
    }
}