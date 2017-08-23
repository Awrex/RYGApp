package com.example.alex.raiseyourgameapp;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class CardFragment extends Fragment {
    private TextView title;
    private TextView subtitle;
    private TextView content;
    private Button workon;
    private Button medium;
    private Button high;
    private Card card;

    public void addCard(Card c){
        card = c;
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
        subtitle = (TextView) view.findViewById(R.id.subtitleView);
        content = (TextView) view.findViewById(R.id.descriptionView);
        workon = (Button) view.findViewById(R.id.lowButton);
        medium = (Button) view.findViewById(R.id.mediumButton);
        high = (Button) view.findViewById(R.id.highButton);
        title.setText(card.getName());
        subtitle.setText(card.getCategory());
        content.setText(card.getDescription());
        return view;
    }

}
