package com.ryg;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Button;

/**
 * Created by Alex on 31/08/2017.
 */

public class CustomAdapter extends ArrayAdapter<Button>{
    private Context context;

    public CustomAdapter(Context context) {
        super(context, R.layout.activity_second_sort);
        this.context = context;
    }
}
