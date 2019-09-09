package com.segal.lqtauditproject.Map;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.segal.lqtauditproject.Data.LQTAuditDataSource;
import com.segal.lqtauditproject.Interfaces.MainCallbacks;
import com.segal.lqtauditproject.Interfaces.MapCallbacks;
import com.segal.lqtauditproject.Models.LoginInfo;
import com.segal.lqtauditproject.Models.Site;
import com.segal.lqtauditproject.R;
import com.segal.lqtauditproject.SiteDetailsActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hossein on 10/26/2017.
 */

public class MapFragment extends Fragment implements OnMapReadyCallback, MapCallbacks,GoogleMap.OnInfoWindowClickListener {
    GoogleMap mGoogleMap;
    MapView mMapView;
    View mView;
    private LQTAuditDataSource dataSource;
    private List<Site> sites;
    private List<Marker> markers;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        mView = inflater.inflate(R.layout.fragment_map, container, false);

        mMapView = mView.findViewById(R.id.map);

        if (mMapView != null) {
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }

        dataSource = new LQTAuditDataSource();

        dataSource.open();
        sites = dataSource.getCurrentUserSites();

        return mView;

    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());
        mGoogleMap = googleMap;
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(33.0943022, 54.8167238), 4));
        mGoogleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                setMapSettings();
                addMarkers();
            }
        });
        mGoogleMap.setOnInfoWindowClickListener(this);
    }

    @Override
    public void setMapSettings() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
            mGoogleMap.setMyLocationEnabled(true);
            mGoogleMap.getUiSettings().setMapToolbarEnabled(true);
        }
    }

    @Override
    public void addMarkers() {
        if (mGoogleMap == null)
            return;

        if (markers == null)
            markers = new ArrayList<>();
        else {
            for (Marker marker : markers) {
                marker.remove();
                markers = new ArrayList<>();
            }
        }
        for (int i = 0; i < sites.size(); i++) {
            Site cSite = sites.get(i);
            if (cSite.getPlanLatitude() != null && cSite.getPlanLatitude().length() > 2
                    && cSite.getPlanLongitude() != null && cSite.getPlanLongitude().length() > 2) {

                String stat = cSite.getStatus() == null ? "Scheduled" : cSite.getStatus();

                BitmapDescriptor color;
                switch (stat) {
                    case "Scheduled":
                        color = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE);
                        break;
                    case "Visited":
                        color = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE);
                        break;
                    case "Reported":
                        color = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW);
                        break;
                    case "Submitted":
                        color = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET);
                        break;
                    case "Rejected":
                        color = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);
                        break;
                    case "Accepted":
                        color = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
                        break;

                    default:
                        color = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE);
                        break;
                }


                markers.add(mGoogleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(Double.parseDouble(cSite.getPlanLatitude()),
                                Double.parseDouble(cSite.getPlanLongitude())))
                        .title(cSite.getSiteId())
                        .icon(color)
                        .snippet(cSite.getLocalCreatedAt().toString())));
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((MainCallbacks) context).setMapCallbacks(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dataSource.close();
    }
    private static final int EDIT_SITE_REQUEST_CODE = 2146;

    @Override
    public void onInfoWindowClick(Marker marker) {
        String siteId = marker.getTitle();
        Site site = dataSource.getSiteBySiteId(siteId);
        Intent intent = new Intent(getActivity(), SiteDetailsActivity.class);
        intent.putExtra("id", site.getId());
        startActivityForResult(intent, EDIT_SITE_REQUEST_CODE);
    }
}
