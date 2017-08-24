package com.example.alex.raiseyourgameapp;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class CardFragment extends Fragment {
    private String mTag;
    private TextView title;
    private TextView subtitle;
    private TextView content;
    private Button workon;
    private Button medium;
    private Button high;
    private Button toTitle;
    private Card card;
    private ImageButton undo;
    private Button sortLater;
    FragmentRemover removerListener;

    public void addCard(Card c,String t){
        card = c;
        mTag = t;
    }
    public void toTitle()
    {

    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        try{
            removerListener = (FragmentRemover) activity;
        } catch (ClassCastException e){
            throw new RuntimeException(getActivity().getClass().getSimpleName() + "Class does not implement fragment remover, cannot use fragment.\n", e);
        }
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
        addCloseFragment(workon);
        medium = (Button) view.findViewById(R.id.mediumButton);
        addCloseFragment(medium);
        high = (Button) view.findViewById(R.id.highButton);
        addCloseFragment(high);
        toTitle = (Button) view.findViewById(R.id.titleScreenButton);
        toTitle.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){ removerListener.toTitle();}
        });
        sortLater = (Button) view.findViewById(R.id.laterButton);
        sortLater.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){ removerListener.sortLater();}
        });
        undo = (ImageButton) view.findViewById(R.id.undoButton);
        undo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) { removerListener.undoSort();}
        });
        title.setText(card.getName());
        subtitle.setText(card.getCategory());
        content.setText(card.getDescription());
        return view;
    }

    private void addCloseFragment(Button button)
    {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removerListener.onFragmentRemove();
            }
        });
    }

}
