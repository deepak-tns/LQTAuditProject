package com.segal.lqtauditproject;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.data.DataBufferUtils;
import com.google.gson.Gson;
import com.segal.lqtauditproject.Data.LQTAuditDataSource;
import com.segal.lqtauditproject.Helpers.ImageHelpers;
import com.segal.lqtauditproject.Interfaces.SiteDetailsCallbacks;
import com.segal.lqtauditproject.Interfaces.SiteSection;
import com.segal.lqtauditproject.Models.Site;
import com.segal.lqtauditproject.ViewEvents.SiteDetailsEvents;
import com.segal.lqtauditproject.ViewModels.SiteInfoViewModel;
import com.segal.lqtauditproject.databinding.FragmentSiteInfoBinding;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.segal.lqtauditproject.Helpers.ViewHelpers.getViewsByTag;

/**
 * Created by Hossein on 10/26/2017.
 */

public class FragmentSiteInfo extends Fragment implements SiteSection {
    private static final String BUNDLE_KEY = "SiteInfoViewModel";
    private static final String TAG = "FragmentSiteInfo";

    private SiteDetailsCallbacks activity;

    SiteInfoViewModel mViewModel;
    ImageView imageView;
    private LQTAuditDataSource dataSource;
    private SiteDetailsEvents mViewEvents;
    private EditText editTextSiteId;
    private TextView tvComments;


    public static FragmentSiteInfo newInstance(int position) {
        FragmentSiteInfo fragment = new FragmentSiteInfo();
        Bundle args = new Bundle();
        args.putInt("position", position);
        fragment.setArguments(args);

        return fragment;
    }

    public FragmentSiteInfo() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SiteInfoViewModel.class);
        requestData();

//        if (savedInstanceState != null) {
////            String viewModel = savedInstanceState.getString(BUNDLE_KEY);
////            if (viewModel != null) {
////                mViewModel = new Gson().fromJson(viewModel, SiteInfoViewModel.class);
////            }
//        }
//        if (getArguments() != null) {
//            String id = getArguments().getString("id");
//            if (id != null) {
//                mViewModel = ViewModelProviders.of(this).get(SiteInfoViewModel.class);
////                dataSource = new LQTAuditDataSource();
////                dataSource.open();
////                Site site = dataSource.getSite(id);
//                Log.d(TAG, "onCreate: Requesting Site ");
//                requestData();
//            }
//
//        }

        mViewEvents = new SiteDetailsEvents(this.getContext());
    }

    @Override
    public void requestData() {
        Site site = activity.requestSite();
        mViewModel.fillFrom(site);
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
//        outState.putString(BUNDLE_KEY, new Gson().toJson(mViewModel, SiteInfoViewModel.class));
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        requestData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_site_info, container, false);
//        getActivity().getContentResolver();

        FragmentSiteInfoBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_site_info, container, false);

        binding.setMViewModel(mViewModel);
        binding.setMViewEvents(mViewEvents);
        View view = binding.getRoot();
        requestData();

        editTextSiteId = view.findViewById(R.id.inputSiteId);
        if (mViewModel.getSiteId() != null && mViewModel.getSiteId().length() > 4) {
            editTextSiteId.setFocusable(false);
            editTextSiteId.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
            editTextSiteId.setClickable(false);
        }
        tvComments = view.findViewById(R.id.tvComments);
        String comments = "";

        if (mViewModel.getExtra1ImagePath() != null && mViewModel.getExtra1ImagePath().length() > 0) {
            comments += "SubContractor: " + mViewModel.getExtra1ImagePath();
            comments += "\n";
        }

        if (mViewModel.getExtra2ImagePath() != null && mViewModel.getExtra2ImagePath().length() > 0) {
            comments += "Vendor: " + mViewModel.getExtra2ImagePath();
        }
        tvComments.setText(comments);

        final List<View> camBtn = getViewsByTag(view.getRootView(), "camBtn");
        final Fragment self = this;
        for (final View view1 : camBtn) {
            view1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String tag = view1.getTag().toString().replace("camBtn,", "");
                    ImageView imgView = (ImageView) getViewsByTag(view1.getRootView(), "imgView," + tag).get(0);
                    String namePrefix = "SI_";
                    activity.onCameraBtnClicked(imgView, namePrefix, tag, "");
                }
            });
        }


        List<View> imgViews = getViewsByTag(view.getRootView(), "imgView");
        for (final View view1 : imgViews) {
            ImageHelpers.setImageViewPic((ImageView) view1, activity.requestSite().getSiteId(), "SI_");
            view1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String tag = view1.getTag().toString().replace("imgView,", "");
                    Intent intent = new Intent(getActivity(), ImageViewerActivity.class);
                    intent.putExtra("imageUrl", activity.requestSite().getSiteId() + "/" + activity.requestSite().getSiteId() + "_SI_" + tag);

                    getActivity().startActivity(intent);
                }
            });
        }

        EditText etSiteId, etLatitude, etLongitude;
        etSiteId = view.findViewById(R.id.inputSiteId);
        etLatitude = view.findViewById(R.id.inputOnSiteLatitude);
        etLongitude = view.findViewById(R.id.inputOnSiteLongitude);
        List<EditText> ets = new ArrayList<>();
        ets.add(etSiteId);
        ets.add(etLatitude);
        ets.add(etLongitude);
        for (EditText et : ets) {
            et.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    passData();
                }
            });
        }


//        ImageView bh = view.findViewById(R.id.imgBuildingHeightImage);
//        bh.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), ImageViewerActivity.class);
//                intent.putExtra("imageUrl", ImageHelpers.getImageFullPath(mViewModel.getBuildingHeightImagePath()));
//
//                startActivity(intent);
//            }
//        });

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
        activity.passSiteInfoViewModel(mViewModel);
    }
}