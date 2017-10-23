package com.ryg;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Alex Stewart
 * A fragment used in the FirstSortActivity, displays each card separately with its description and title.
 * The background of the title is coloured according to the category or position it's in.
 */
public class CardFragment extends Fragment {
    private String mTag;
    private Card card;
    private TextView title;
    private TextView content;
    private ImageView moreInfo;

    //Simply some setter code.
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
        moreInfo = (ImageView) view.findViewById(R.id.moreInfoImage);
        String name = card.getName();
        title.setText(name);
        int id = 0;
        /**This try catch is checking if there's any image that exists with the name specified in the card.
        * If it does not then the id will be 0 and the if statement to change the blank ImageView, stays blank.
        * If it does, the id will be associated to that particular image and is displayed in the ImageView.
        */
        try {
            id = R.drawable.class.getField(card.getInfoPath().toLowerCase()).getInt(null);
            } catch (Exception e) {
                id = 0;
            }
        if (id != 0) {
            moreInfo.setBackgroundDrawable(getResources().getDrawable(id));
        }
        content.setText(card.getDescription());
        GradientDrawable border = new GradientDrawable();
        border.setColor(card.getColour());
        border.setStroke(2, 0xFF000000);
        title.setBackgroundDrawable(border);
        title.getBackground().setAlpha(170);
        return view;
    }



}