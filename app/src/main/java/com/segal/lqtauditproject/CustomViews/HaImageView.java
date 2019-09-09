package com.segal.lqtauditproject.CustomViews;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import com.segal.lqtauditproject.Helpers.ImageHelpers;


import java.io.File;

/**
 * Created by Hossein on 10/31/2017.
 */

public class HaImageView extends android.support.v7.widget.AppCompatImageView {
    private OnImageChangeListener onImageChangeListener;
    private String sourcePath;

    public HaImageView(Context context) {
        super(context);
    }

    public HaImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }


    public void setImageChangeListener(
            OnImageChangeListener onImageChangeListener) {
        this.onImageChangeListener = onImageChangeListener;
    }


    @Override
    public void setBackgroundResource(int resId) {
        super.setBackgroundResource(resId);
        if (onImageChangeListener != null)
            onImageChangeListener.imageChanged(this);
    }

    @Override
    public void setBackgroundDrawable(Drawable background) {
        super.setBackgroundDrawable(background);
        if (onImageChangeListener != null)
            onImageChangeListener.imageChanged(this);
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        if (onImageChangeListener != null)
            onImageChangeListener.imageChanged(this);
    }


    public String getSourcePath() {
        Log.d("HaImageView", "getSourcePath: " + this.getTag() + " - " + this.sourcePath);

        return this.sourcePath;
    }

    public void setSourcePath(String src) {
        this.sourcePath = src;
        setImageBitmap(ImageHelpers.loadBitmapFromFileForView(src));
    }

    public interface OnImageChangeListener {
        void imageChanged(HaImageView mImageView);
    }


    @Override
    public Parcelable onSaveInstanceState() {
        //begin boilerplate code that allows parent classes to save state
        Parcelable superState = super.onSaveInstanceState();

        SavedState ss = new SavedState(superState);
        //end
        ss.sourcePath = this.sourcePath;

        return ss;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        //begin boilerplate code so parent classes can restore state
        if(!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }
        SavedState ss = (SavedState)state;
        super.onRestoreInstanceState(ss.getSuperState());
        //end

        this.sourcePath = ss.sourcePath;
    }

    static class SavedState extends BaseSavedState {
        String sourcePath;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            this.sourcePath = in.readString();
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeString(this.sourcePath);
        }

        //required field that makes Parcelables from a Parcel
        public static final Parcelable.Creator<SavedState> CREATOR =
                new Parcelable.Creator<SavedState>() {
                    public SavedState createFromParcel(Parcel in) {
                        return new SavedState(in);
                    }
                    public SavedState[] newArray(int size) {
                        return new SavedState[size];
                    }
                };
    }
}
