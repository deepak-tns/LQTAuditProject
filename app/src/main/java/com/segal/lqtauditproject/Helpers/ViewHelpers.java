package com.segal.lqtauditproject.Helpers;

import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Hossein on 10/28/2017.
 */

public class ViewHelpers {


    public static List<View> getViewsByTag(View root, String tag) {
        List<View> result = new LinkedList<>();

        if (root instanceof ViewGroup) {
            final int childCount = ((ViewGroup) root).getChildCount();
            for (int i = 0; i < childCount; i++) {
                result.addAll(getViewsByTag(((ViewGroup) root).getChildAt(i), tag));
            }
        }

        final Object rootTag = root.getTag();
        // handle null tags, code from Guava's Objects.equal
        if (rootTag != null)
            if (rootTag.toString().contains(tag) || (tag != null && rootTag.toString().contains(tag))) {
                result.add(root);
            }

        return result;
    }
}
