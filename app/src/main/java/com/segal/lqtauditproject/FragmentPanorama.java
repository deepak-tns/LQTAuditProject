package com.segal.lqtauditproject;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.segal.lqtauditproject.Data.LQTAuditDataSource;
import com.segal.lqtauditproject.Helpers.ImageHelpers;
import com.segal.lqtauditproject.Interfaces.SiteDetailsCallbacks;
import com.segal.lqtauditproject.Interfaces.SiteSection;
import com.segal.lqtauditproject.Models.Site;
import com.segal.lqtauditproject.ViewEvents.SiteDetailsEvents;
import com.segal.lqtauditproject.ViewModels.PanoramaViewModel;
import com.segal.lqtauditproject.databinding.FragmentSitePanoramaBinding;

import java.util.List;

import static com.segal.lqtauditproject.Helpers.ViewHelpers.getViewsByTag;

/**
 * Created by Hossein on 10/26/2017.
 */

public class FragmentPanorama extends Fragment implements SiteSection {

    private static final String BUNDLE_KEY = "PanoramaViewModel";
    PanoramaViewModel mViewModel;
    SiteDetailsEvents mViewEvents;

    private SiteDetailsCallbacks activity;
    private LQTAuditDataSource dataSource;


    public static FragmentPanorama newInstance(Bundle bundle) {
        FragmentPanorama fragment = new FragmentPanorama();
        fragment.setArguments(bundle);

        return fragment;
    }

    public FragmentPanorama() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(PanoramaViewModel.class);
        if (savedInstanceState != null) {
            String viewModel = savedInstanceState.getString(BUNDLE_KEY);
            if (viewModel != null) {
                mViewModel = new Gson().fromJson(viewModel, PanoramaViewModel.class);
            }
        } else if (getArguments() != null) {
            String id = getArguments().getString("id");
            if (id != null) {
                mViewModel = ViewModelProviders.of(this).get(PanoramaViewModel.class);
//                dataSource = new LQTAuditDataSource();
//                dataSource.open();
//                Site site = dataSource.getSite(id);
                requestData();
            }
        }
        mViewEvents = new SiteDetailsEvents(this.getContext());

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (dataSource != null)
            dataSource.close();
    }

    @Override
    public void requestData() {
        Site site = activity.requestSite();
        mViewModel.fillFrom(site);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        outState.putString(BUNDLE_KEY, new Gson().toJson(mViewModel, PanoramaViewModel.class));
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        requestData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentSitePanoramaBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_site_panorama, container, false);
        binding.setMViewModel(mViewModel);
        binding.setMViewEvents(mViewEvents);
        View view = binding.getRoot();
        requestData();

        List<View> camBtn = getViewsByTag(view.getRootView(), "camBtn");
        final Fragment self = this;
        for (final View view1 : camBtn) {
            view1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String tag = view1.getTag().toString().replace("camBtn,", "");
                    ImageView imgView = (ImageView) getViewsByTag(view1.getRootView(), "imgView," + tag).get(0);
                    activity.onCameraBtnClicked(imgView,"P_", tag, "");
                }
            });
        }

        List<View> imgViews = getViewsByTag(view.getRootView(), "imgView");

        for (final View view1 : imgViews) {
            ImageHelpers.setImageViewPic((ImageView) view1, activity.requestSite().getSiteId(), "P_");

            view1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String tag = view1.getTag().toString().replace("imgView,", "");

                    Intent intent = new Intent(getActivity(), ImageViewerActivity.class);
                    intent.putExtra("imageUrl", activity.requestSite().getSiteId() + "/" + activity.requestSite().getSiteId() + "_P_"  +tag);

                    getActivity().startActivity(intent);
                }
            });
        }

        LinearLayout btnContainer = view.findViewById(R.id.buttonsContainer);
        btnContainer.requestFocus();

        Button btnSave = view.findViewById(R.id.btnSave);
        Button btnUpload = view.findViewById(R.id.btnUpload);
        Button btnCancel = view.findViewById(R.id.btnCancel);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.saveSite();
            }
        });
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.uploadSite();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.d("BTN CANCEL", "onClick: ");
                activity.cancel();
            }
        });
        btnCancel.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
//                    Log.d("BTN CANCEL", "onFocusChange: ");
                    v.performClick();
                }
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requestData();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.activity = (SiteDetailsCallbacks) context;
        this.activity.addSiteSection(this);
    }

    @Override
    public void passData() {
        this.activity.passPanoramaViewModel(mViewModel);
    }
}