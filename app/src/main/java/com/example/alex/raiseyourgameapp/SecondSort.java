package com.example.alex.raiseyourgameapp;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class SecondSort extends AppCompatActivity {
    private ArrayList<Card> cardList = new ArrayList<>();
    private ArrayList<Card> otherList = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapter;
    private ListView list;
    private ImageView textBox;
    private MainController mc;

    class DragDropListener implements View.OnDragListener {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            Drawable enterShape = getResources().getDrawable(R.drawable.button_back);
            textBox = (ImageView) findViewById(R.id.priorityPicture);
            switch (action) {
                case DragEvent.ACTION_DRAG_ENTERED:
                    v.setBackgroundDrawable(enterShape);
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    v.setBackgroundDrawable(enterShape);

                    break;
                case DragEvent.ACTION_DROP:
                    RelativeLayout r = (RelativeLayout) findViewById(R.id.colourRelativeLayout);
                    ImageView myImage = (ImageView) findViewById(R.id.priorityPicture);
                    TextView target = new TextView(getBaseContext());
                    TextView drag = (TextView) event.getLocalState();
                    target.setText(drag.getText());
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(300, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.leftMargin = (int)event.getX() -80;
                    params.topMargin = (int)event.getY();
                    target.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            ClipData data = ClipData.newPlainText("", "");
                            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);

                            v.startDrag(data, shadowBuilder, v, 0);
                            v.setVisibility(View.INVISIBLE);
                            return true;
                        }
                    });
                    r.addView(target,params);
                    v.setVisibility(View.VISIBLE);
                    break;
                default:
                    break;
            }
            return true;
        }
    }
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        textBox = (ImageView) findViewById(R.id.priorityPicture);
        setContentView(R.layout.activity_second_sort);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Prioritise the competencies you have chosen to work on for your next training block.\n This is done via dragging the item and placing it on the right from top to bottom. Top being the highest priority and bottom being the lowest.")
                .setCancelable(false)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //do things
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
        mc = new MainController(getBaseContext());
        findViewById(R.id.priorityPicture).setOnDragListener(new DragDropListener());
        otherList = (ArrayList<Card>)getIntent().getSerializableExtra("CARDLIST");
        cardList = new ArrayList<>();
        for(int i = 0; i<otherList.size(); i++)
        {
            if(otherList.get(i).getRating() != 4) {
                if (otherList.get(i).getSelected()) {
                    cardList.add(otherList.get(i));
                }
            }
        }
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        for (int i = 0; i < cardList.size(); i++) {
            String s;
            s = cardList.get(i).getName();
            arrayAdapter.add(s);
        }
        list = (ListView) findViewById(R.id.list2);
        list.setAdapter(arrayAdapter);
        list.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);

                view.startDrag(data, shadowBuilder, view, 0);
                view.setVisibility(View.INVISIBLE);
                return true;
            }

        });

    }
    public void toOutput(View v)
    {
        Intent intent = new Intent(getBaseContext(), OutputActivity.class);
        startActivity(intent);
        finish();
    }
}
