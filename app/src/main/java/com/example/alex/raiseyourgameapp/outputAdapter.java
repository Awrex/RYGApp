package com.example.alex.raiseyourgameapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class outputAdapter extends BaseAdapter {
    private Card card = new Card();
    private Context context;
    private ArrayList<Card> cards  = new ArrayList<>();
    private DBController db;
    private Drawable d;
    ListView list;
    private static LayoutInflater inflater = null;

    public outputAdapter(Context con, ArrayList<Card> c) {
        context = con;
        cards = c;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    public outputAdapter(Context con){
        context = con;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    public void add(Card c)
    {
        cards.add(c);
    }
    @Override
    public int getCount() {
        return cards.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null)
            view = inflater.inflate(R.layout.fragment_output, null);
        db = new DBController(context);
        TextView title = (TextView) view.findViewById(R.id.cardFragName);
        TextView comment = (TextView) view.findViewById(R.id.cardFragComment);
        Button circle = (Button) view.findViewById(R.id.cardFragButton);
        Button commentButton = (Button) view.findViewById(R.id.fragmentEdit);
        card = cards.get(position);

        view.setTag(card);
        title.setText(card.getName());
        comment.setText(card.getComment());
        commentButton.setOnClickListener(onButtonClickListener);

        switch (card.getCategory()) {
            case "Mental":
                circle.setBackgroundDrawable(view.getResources().getDrawable(R.drawable.mental_button));
                break;
            case "Tactical":
                circle.setBackgroundDrawable(view.getResources().getDrawable(R.drawable.tactical_button));
                break;
            case "Physical":
                circle.setBackgroundDrawable(view.getResources().getDrawable(R.drawable.physical_button));
                break;
            case "Wellbeing":
                circle.setBackgroundDrawable(view.getResources().getDrawable(R.drawable.wellbeing_button));
                break;
            case "Skills":
                circle.setBackgroundDrawable(view.getResources().getDrawable(R.drawable.skills_button));
                break;
            case "Character/Team":
                circle.setBackgroundDrawable(view.getResources().getDrawable(R.drawable.character_button));
                break;
        }
        title.setText(card.getName());
        comment.setText(card.getComment());

        return view;
    }
    private View.OnClickListener onButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            list = (ListView)v.getParent().getParent().getParent();
            final int pos = list.getPositionForView((View) v.getParent());
            card = cards.get(pos);
            Log.v(TAG, "Title clicked, card name: "+ cards.get(pos).getName() + pos);
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Add comment for: " + card.getName());
            final EditText input = new EditText(context);
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(input);
            builder.setTitle("Enter your comment for: " + card.getName());
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    try {
                        if (!card.getComment().equals(null))
                            if(!card.getComment().equals(""))
                            card.setComment(card.getComment() + ", " + input.getText().toString());
                        else
                            card.setComment(input.getText().toString());
                    }
                    catch(NullPointerException e){
                        card.setComment(input.getText().toString());
                    }
                    db.updateComment(card);
                    notifyDataSetChanged();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
        }
    };
}
