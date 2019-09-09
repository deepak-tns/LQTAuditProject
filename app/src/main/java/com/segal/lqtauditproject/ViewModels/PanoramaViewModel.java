package com.segal.lqtauditproject.ViewModels;

import android.arch.lifecycle.ViewModel;
import android.graphics.Bitmap;

import com.segal.lqtauditproject.Helpers.ImageHelpers;
import com.segal.lqtauditproject.Models.Site;

/**
 * Created by Hossein on 10/30/2017.
 */

public class PanoramaViewModel extends ViewModel {

    private String bearing0ImagePath;
    private boolean bearing0Blocking;
    private String bearing0Comments;

    private String bearing30ImagePath;
    private boolean bearing30Blocking;
    private String bearing30Comments;

    private String bearing60ImagePath;
    private boolean bearing60Blocking;
    private String bearing60Comments;

    private String bearing90ImagePath;
    private boolean bearing90Blocking;
    private String bearing90Comments;

    private String bearing120ImagePath;
    private boolean bearing120Blocking;
    private String bearing120Comments;

    private String bearing150ImagePath;
    private boolean bearing150Blocking;
    private String bearing150Comments;

    private String bearing180ImagePath;
    private boolean bearing180Blocking;
    private String bearing180Comments;

    private String bearing210ImagePath;
    private boolean bearing210Blocking;
    private String bearing210Comments;

    private String bearing240ImagePath;
    private boolean bearing240Blocking;
    private String bearing240Comments;

    private String bearing270ImagePath;
    private boolean bearing270Blocking;
    private String bearing270Comments;

    private String bearing300ImagePath;
    private boolean bearing300Blocking;
    private String bearing300Comments;

    private String bearing330ImagePath;
    private boolean bearing330Blocking;
    private String bearing330Comments;



    public String getBearing0ImagePath() {
        return bearing0ImagePath;
    }

    public void setBearing0ImagePath(String bearing0ImagePath) {
        this.bearing0ImagePath = bearing0ImagePath;
    }

    public boolean getBearing0Blocking() {
        return bearing0Blocking;
    }

    public void setBearing0Blocking(boolean bearing0Blocking) {
        this.bearing0Blocking = bearing0Blocking;
    }

    public String getBearing0Comments() {
        return bearing0Comments;
    }

    public void setBearing0Comments(String bearing0Comments) {
        this.bearing0Comments = bearing0Comments;
    }

    public String getBearing30ImagePath() {
        return bearing30ImagePath;
    }

    public void setBearing30ImagePath(String bearing30ImagePath) {
        this.bearing30ImagePath = bearing30ImagePath;
    }

    public boolean getBearing30Blocking() {
        return bearing30Blocking;
    }

    public void setBearing30Blocking(boolean bearing30Blocking) {
        this.bearing30Blocking = bearing30Blocking;
    }

    public String getBearing30Comments() {
        return bearing30Comments;
    }

    public void setBearing30Comments(String bearing30Comments) {
        this.bearing30Comments = bearing30Comments;
    }

    public String getBearing60ImagePath() {
        return bearing60ImagePath;
    }

    public void setBearing60ImagePath(String bearing60ImagePath) {
        this.bearing60ImagePath = bearing60ImagePath;
    }

    public boolean getBearing60Blocking() {
        return bearing60Blocking;
    }

    public void setBearing60Blocking(boolean bearing60Blocking) {
        this.bearing60Blocking = bearing60Blocking;
    }

    public String getBearing60Comments() {
        return bearing60Comments;
    }

    public void setBearing60Comments(String bearing60Comments) {
        this.bearing60Comments = bearing60Comments;
    }

    public String getBearing90ImagePath() {
        return bearing90ImagePath;
    }

    public void setBearing90ImagePath(String bearing90ImagePath) {
        this.bearing90ImagePath = bearing90ImagePath;
    }

    public boolean getBearing90Blocking() {
        return bearing90Blocking;
    }

    public void setBearing90Blocking(boolean bearing90Blocking) {
        this.bearing90Blocking = bearing90Blocking;
    }

    public String getBearing90Comments() {
        return bearing90Comments;
    }

    public void setBearing90Comments(String bearing90Comments) {
        this.bearing90Comments = bearing90Comments;
    }

    public String getBearing120ImagePath() {
        return bearing120ImagePath;
    }

    public void setBearing120ImagePath(String bearing120ImagePath) {
        this.bearing120ImagePath = bearing120ImagePath;
    }

    public boolean getBearing120Blocking() {
        return bearing120Blocking;
    }

    public void setBearing120Blocking(boolean bearing120Blocking) {
        this.bearing120Blocking = bearing120Blocking;
    }

    public String getBearing120Comments() {
        return bearing120Comments;
    }

    public void setBearing120Comments(String bearing120Comments) {
        this.bearing120Comments = bearing120Comments;
    }

    public String getBearing150ImagePath() {
        return bearing150ImagePath;
    }

    public void setBearing150ImagePath(String bearing150ImagePath) {
        this.bearing150ImagePath = bearing150ImagePath;
    }

    public boolean getBearing150Blocking() {
        return bearing150Blocking;
    }

    public void setBearing150Blocking(boolean bearing150Blocking) {
        this.bearing150Blocking = bearing150Blocking;
    }

    public String getBearing150Comments() {
        return bearing150Comments;
    }

    public void setBearing150Comments(String bearing150Comments) {
        this.bearing150Comments = bearing150Comments;
    }

    public String getBearing180ImagePath() {
        return bearing180ImagePath;
    }

    public void setBearing180ImagePath(String bearing180ImagePath) {
        this.bearing180ImagePath = bearing180ImagePath;
    }

    public boolean getBearing180Blocking() {
        return bearing180Blocking;
    }

    public void setBearing180Blocking(boolean bearing270Blocking) {
        this.bearing180Blocking = bearing270Blocking;
    }

    public String getBearing180Comments() {
        return bearing180Comments;
    }

    public void setBearing180Comments(String bearing180Comments) {
        this.bearing180Comments = bearing180Comments;
    }

    public String getBearing210ImagePath() {
        return bearing210ImagePath;
    }

    public void setBearing210ImagePath(String bearing210ImagePath) {
        this.bearing210ImagePath = bearing210ImagePath;
    }

    public boolean getBearing210Blocking() {
        return bearing210Blocking;
    }

    public void setBearing210Blocking(boolean bearing210Blocking) {
        this.bearing210Blocking = bearing210Blocking;
    }

    public String getBearing210Comments() {
        return bearing210Comments;
    }

    public void setBearing210Comments(String bearing210Comments) {
        this.bearing210Comments = bearing210Comments;
    }

    public void fillFrom(Site site) {
        this.setBearing0Blocking(site.getBearing0Blocking());
        this.setBearing0Comments(site.getBearing0Comments());
        this.setBearing0ImagePath(site.getBearing0ImagePath());

        this.setBearing30Blocking(site.getBearing30Blocking());
        this.setBearing30Comments(site.getBearing30Comments());
        this.setBearing30ImagePath(site.getBearing30ImagePath());

        this.setBearing60Blocking(site.getBearing60Blocking());
        this.setBearing60Comments(site.getBearing60Comments());
        this.setBearing60ImagePath(site.getBearing60ImagePath());

        this.setBearing90Blocking(site.getBearing90Blocking());
        this.setBearing90Comments(site.getBearing90Comments());
        this.setBearing90ImagePath(site.getBearing90ImagePath());

        this.setBearing120Blocking(site.getBearing120Blocking());
        this.setBearing120Comments(site.getBearing120Comments());
        this.setBearing120ImagePath(site.getBearing120ImagePath());

        this.setBearing150Blocking(site.getBearing150Blocking());
        this.setBearing150Comments(site.getBearing150Comments());
        this.setBearing150ImagePath(site.getBearing150ImagePath());

        this.setBearing180Blocking(site.getBearing180Blocking());
        this.setBearing180Comments(site.getBearing180Comments());
        this.setBearing180ImagePath(site.getBearing180ImagePath());

        this.setBearing210Blocking(site.getBearing210Blocking());
        this.setBearing210Comments(site.getBearing210Comments());
        this.setBearing210ImagePath(site.getBearing210ImagePath());

        this.setBearing240Blocking(site.getBearing240Blocking());
        this.setBearing240Comments(site.getBearing240Comments());
        this.setBearing240ImagePath(site.getBearing240ImagePath());

        this.setBearing270Blocking(site.getBearing270Blocking());
        this.setBearing270Comments(site.getBearing270Comments());
        this.setBearing270ImagePath(site.getBearing270ImagePath());

        this.setBearing300Blocking(site.getBearing300Blocking());
        this.setBearing300Comments(site.getBearing300Comments());
        this.setBearing300ImagePath(site.getBearing300ImagePath());

        this.setBearing330Blocking(site.getBearing330Blocking());
        this.setBearing330Comments(site.getBearing330Comments());
        this.setBearing330ImagePath(site.getBearing330ImagePath());
    }


    public String getBearing240ImagePath() {
        return bearing240ImagePath;
    }

    public void setBearing240ImagePath(String bearing240ImagePath) {
        this.bearing240ImagePath = bearing240ImagePath;
    }
    public boolean getBearing240Blocking() {
        return bearing240Blocking;
    }

    public void setBearing240Blocking(boolean bearing240Blocking) {
        this.bearing240Blocking = bearing240Blocking;
    }

    public String getBearing240Comments() {
        return bearing240Comments;
    }

    public void setBearing240Comments(String bearing240Comments) {
        this.bearing240Comments = bearing240Comments;
    }

    public String getBearing270ImagePath() {
        return bearing270ImagePath;
    }

    public void setBearing270ImagePath(String bearing270ImagePath) {
        this.bearing270ImagePath = bearing270ImagePath;
    }

    public boolean getBearing270Blocking() {
        return bearing270Blocking;
    }

    public void setBearing270Blocking(boolean bearing270Blocking) {
        this.bearing270Blocking = bearing270Blocking;
    }

    public String getBearing270Comments() {
        return bearing270Comments;
    }

    public void setBearing270Comments(String bearing270Comments) {
        this.bearing270Comments = bearing270Comments;
    }

    public String getBearing300ImagePath() {
        return bearing300ImagePath;
    }

    public void setBearing300ImagePath(String bearing300ImagePath) {
        this.bearing300ImagePath = bearing300ImagePath;
    }

    public boolean getBearing300Blocking() {
        return bearing300Blocking;
    }

    public void setBearing300Blocking(boolean bearing300Blocking) {
        this.bearing300Blocking = bearing300Blocking;
    }

    public String getBearing300Comments() {
        return bearing300Comments;
    }

    public void setBearing300Comments(String bearing300Comments) {
        this.bearing300Comments = bearing300Comments;
    }

    public String getBearing330ImagePath() {
        return bearing330ImagePath;
    }

    public void setBearing330ImagePath(String bearing330ImagePath) {
        this.bearing330ImagePath = bearing330ImagePath;
    }

    public boolean getBearing330Blocking() {
        return bearing330Blocking;
    }

    public void setBearing330Blocking(boolean bearing330Blocking) {
        this.bearing330Blocking = bearing330Blocking;
    }

    public String getBearing330Comments() {
        return bearing330Comments;
    }

    public void setBearing330Comments(String bearing330Comments) {
        this.bearing330Comments = bearing330Comments;
    }
}
