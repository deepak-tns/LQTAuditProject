package com.segal.lqtauditproject.Helpers;

import android.app.Activity;
import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingListener;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.segal.lqtauditproject.CustomViews.HaImageView;

/**
 * Created by Hossein on 10/29/2017.
 */

public class BindingHelpers {
//    @BindingAdapter("bind:src")
//    public void loadImage(ImageView iv, BitmapDrawable bitmapDrawable) {
//        Log.d("Binding", "Bound " + buildingHeightBitmap + "  ---- " + bitmapDrawable.getBitmap());
//        this.buildingHeightBitmap = bitmapDrawable.getBitmap();
//        iv.setImageDrawable(bitmapDrawable);
//    }
//
//    @BindingAdapter("app:src")
//    public static void loadImage(ImageView iv, Bitmap bitmap) {
//        iv.setImageBitmap(bitmap);
//    }

//    @BindingAdapter("bind:selectedItemPosition")
//    public void spinnerSelectedItem(Spinner spinner, String itemName) {
//        if (itemName == null || itemName.length() < 1)
//            return;
//
//        SpinnerAdapter adapter = spinner.getAdapter();
//        int n = adapter.getCount();
//        for (int i = 0; i < n; i++) {
//
//            if (adapter.getItem(i).toString().trim().equals(itemName.trim())) {
//                spinner.setSelection(i);
//                break;
//            }
//        }
//    }

    @BindingAdapter(value = {"app:holder", "app:holderAttrChanged"}, requireAll = false)
    public static void setHolder(Spinner spinner, String selectedItem, final InverseBindingListener listener) {
        // setup spinner content

        if (selectedItem != null && selectedItem.length() > 0) {
            SpinnerAdapter adapter = spinner.getAdapter();
            int n = adapter.getCount();
            for (int i = 0; i < n; i++) {
                if (adapter.getItem(i).toString().trim().equals(selectedItem.trim())) {
                    spinner.setSelection(i);
                    break;
                }
            }
        }
        // setup listener of notifications to @InverseBindingAdapter
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                listener.onChange();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                listener.onChange();
            }
        });
    }

    @InverseBindingAdapter(attribute = "app:holder", event = "app:holderAttrChanged")
    public static String getHolder(Spinner spinner) {
        return (String) spinner.getSelectedItem();
    }


    @BindingAdapter(value = {"app:image", "app:imageAttrChanged"}, requireAll = false)
    public static void setImage(final HaImageView imageView, final String imagePath, final InverseBindingListener listener) {
        if (imagePath != null) {
            new Thread(new Runnable() {
                @Override
                public void run() {

                    final Bitmap b = ImageHelpers.loadBitmapFromFileForView(imagePath);
                    ((Activity) imageView.getContext()).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            imageView.setImageBitmap(b);
                            imageView.setImageChangeListener(new HaImageView.OnImageChangeListener() {
                                @Override
                                public void imageChanged(HaImageView mImageView) {
                                    listener.onChange();
                                }
                            });

                        }
                    });
                }
            }).start();
//            imageView.setImageBitmap(ImageHelpers.loadBitmapFromFileForView(imagePath));
        }
    }

    @InverseBindingAdapter(attribute = "app:image", event = "app:imageAttrChanged")
    public static String getImage(HaImageView imageView) {
        return imageView.getSourcePath();
    }
}
