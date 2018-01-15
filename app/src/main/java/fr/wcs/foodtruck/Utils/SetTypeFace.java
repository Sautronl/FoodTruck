package fr.wcs.foodtruck.Utils;

import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by apprenti on 15/01/18.
 */

public class SetTypeFace {

    public static final void setAppFont(ViewGroup mainContainer, Typeface mainFont) {
        if (mainContainer == null || mainFont == null) return;

        final int mCount = mainContainer.getChildCount();

        // Loop through all of the children.
        for (int i = 0; i < mCount; ++i) {
            final View mChild = mainContainer.getChildAt(i);
            if (mChild instanceof TextView) {
                // Set the font if it is a TextView.
                ((TextView) mChild).setTypeface(mainFont);
            } else if (mChild instanceof ViewGroup) {
                // Recursively attempt another ViewGroup.
                setAppFont((ViewGroup) mChild, mainFont);
            }
            if (mChild instanceof EditText) {
                // Set the font if it is a TextView.
                ((TextView) mChild).setTypeface(mainFont);
            } else if (mChild instanceof ViewGroup) {
                // Recursively attempt another ViewGroup.
                setAppFont((ViewGroup) mChild, mainFont);
            }
            if (mChild instanceof Button){
                ((TextView) mChild).setTypeface(mainFont);
            }else if (mChild instanceof ViewGroup){
                setAppFont((ViewGroup)mChild,mainFont);
            }
        }
    }
}