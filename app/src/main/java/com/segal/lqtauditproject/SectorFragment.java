package com.segal.lqtauditproject;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.segal.lqtauditproject.Interfaces.SectorCallbacks;
import com.segal.lqtauditproject.Interfaces.SiteDetailsCallbacks;

/**
 * Created by Hossein on 10/26/2017.
 */

public class SectorFragment extends Fragment {
    SiteDetailsCallbacks activity;

    SectorCallbacks parentFragment;
    int mPosition;
    private View view;

    FragmentAntenna antenna1Fragment;
    FragmentAntenna antenna2Fragment;
    FragmentAntenna antenna3Fragment;
    FragmentAntenna antenna4Fragment;
    private String editingId;


    public static SectorFragment newInstance(Bundle b) {
        SectorFragment fragment = new SectorFragment();
        fragment.setArguments(b);

        return fragment;
    }

    public SectorFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPosition = getArguments().getInt("position");
            editingId = getArguments().getString("id");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sector, container, false);
        // Setup spinner
        Spinner spinner = view.findViewById(R.id.sectorAntennaSelector);
        spinner.setAdapter(new SectorFragment.MyAdapter(
                getContext(),
                new String[]{
                        "Antenna 1",
                        "Antenna 2",
                        "Antenna 3",
                        "Antenna 4",
                }));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // When the given dropdown item is selected, show its contents in the
                // container view.
                FragmentAntenna frag;
                switch (position) {
                    case 0:
                        if (antenna1Fragment == null) {
                            antenna1Fragment = new FragmentAntenna();
                            Bundle args = new Bundle();
                            args.putInt("sectorIndex", mPosition);
                            args.putInt("antennaIndex", 1);
                            if (editingId != null && editingId.length() > 1)
                                args.putString("id", editingId);
                            antenna1Fragment.setArguments(args);
                        }
                        frag = antenna1Fragment;
                        break;
                    case 1:
                        if (antenna2Fragment == null) {
                            antenna2Fragment = new FragmentAntenna();
                            Bundle args = new Bundle();
                            args.putInt("sectorIndex", mPosition);
                            args.putInt("antennaIndex", 2);
                            if (editingId != null && editingId.length() > 1)
                                args.putString("id", editingId);
                            antenna2Fragment.setArguments(args);
                        }
                        frag = antenna2Fragment;
                        break;
                    case 2:
                        if (antenna3Fragment == null) {
                            antenna3Fragment = new FragmentAntenna();
                            Bundle args = new Bundle();
                            args.putInt("sectorIndex", mPosition);
                            args.putInt("antennaIndex", 3);
                            if (editingId != null && editingId.length() > 1)
                                args.putString("id", editingId);
                            antenna3Fragment.setArguments(args);
                        }
                        frag = antenna3Fragment;
                        break;
                    case 3:
                        if (antenna4Fragment == null) {
                            antenna4Fragment = new FragmentAntenna();
                            Bundle args = new Bundle();
                            args.putInt("sectorIndex", mPosition);
                            args.putInt("antennaIndex", 4);
                            if (editingId != null && editingId.length() > 1)
                                args.putString("id", editingId);
                            antenna4Fragment.setArguments(args);
                        }
                        frag = antenna4Fragment;
                        break;
                    default:
                        if (antenna1Fragment == null) {
                            antenna1Fragment = new FragmentAntenna();
                            Bundle args = new Bundle();
                            args.putInt("sectorIndex", mPosition);
                            args.putInt("antennaIndex", 1);
                            if (editingId != null && editingId.length() > 1)
                                args.putString("id", editingId);
                            antenna1Fragment.setArguments(args);
                        }
                        frag = antenna1Fragment;
                        break;
                }

                getChildFragmentManager().beginTransaction()
                        .replace(R.id.antenna_container, frag)
                        .commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

//        Spinner antennaSpinner = view.findViewById(R.id.sectorAntennaSelector);
//
//        antennaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                onAntennaChanged(i);
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);


        // Make sure that we are currently visible
        if (this.isVisible()) {
            // If we are becoming invisible, then...
            if (isVisibleToUser) {

            }
        }
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    public void onAntennaChanged(int position) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        activity = (SiteDetailsCallbacks) context;
    }

    private static class MyAdapter extends ArrayAdapter<String> implements ThemedSpinnerAdapter {
        private final ThemedSpinnerAdapter.Helper mDropDownHelper;

        public MyAdapter(Context context, String[] objects) {
            super(context, android.R.layout.simple_list_item_1, objects);
            mDropDownHelper = new ThemedSpinnerAdapter.Helper(context);
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            View view;

            if (convertView == null) {
                // Inflate the drop down using the helper's LayoutInflater
                LayoutInflater inflater = mDropDownHelper.getDropDownViewInflater();
                view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            } else {
                view = convertView;
            }

            TextView textView = view.findViewById(android.R.id.text1);
            textView.setText(getItem(position));

            return view;
        }

        @Override
        public Resources.Theme getDropDownViewTheme() {
            return mDropDownHelper.getDropDownViewTheme();
        }

        @Override
        public void setDropDownViewTheme(Resources.Theme theme) {
            mDropDownHelper.setDropDownViewTheme(theme);
        }
    }
}