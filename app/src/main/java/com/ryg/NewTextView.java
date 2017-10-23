package com.ryg;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Created by Alex Stewart.
 * A basic TextView that displays the text in a different font.
 */

public class NewTextView extends android.support.v7.widget.AppCompatTextView {
    public NewTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/calibril.ttf"));
    }
}
