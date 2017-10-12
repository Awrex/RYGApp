package com.example.alex.raiseyourgameapp;


import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class CardFragment extends Fragment {
    private String mTag;
    private Card card;
    private TextView title;
    private TextView content;


    public void addCard(Card c,String t){
        card = c;
        mTag = t;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card, container, false);
        title = (TextView) view.findViewById(R.id.titleView);
        content = (TextView) view.findViewById(R.id.descriptionView);
        String name = card.getName();
        title.setText(name);
        content.setText(card.getDescription());
        GradientDrawable border = new GradientDrawable();
        border.setColor(card.getColour());
        border.setStroke(2, 0xFF000000);
        title.setBackgroundDrawable(border);
        title.getBackground().setAlpha(170);
        return view;
    }



}