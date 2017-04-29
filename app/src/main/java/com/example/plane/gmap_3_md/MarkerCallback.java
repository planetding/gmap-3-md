package org.planetding.gmap_3_md;

import android.util.Log;
import android.view.SurfaceHolder;
import com.squareup.picasso.Callback;

import com.google.android.gms.maps.model.Marker;

// public abstract MarkerCallback();
// This is to solve the two click issue with using Picasso downloading url photo and insert into customized InfoWindow
// 4/29/17, MD

public class MarkerCallback extends java.lang.Object implements Callback {
    Marker marker=null;

    MarkerCallback(Marker marker) {
        this.marker=marker;
    }

    @Override
    public void onError() {
        Log.e(getClass().getSimpleName(), "Error loading thumbnail!");
    }

    @Override
    public void onSuccess() {
        if (marker != null && marker.isInfoWindowShown()) {
            marker.hideInfoWindow();
            marker.showInfoWindow();
        }
    }
}