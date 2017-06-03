package com.sda.balys.robert.projektsda.fragments;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.sda.balys.robert.projektsda.MainActivity;
import com.sda.balys.robert.projektsda.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class StopwatchFragment extends Fragment {

    @BindView(R.id.button)
    Button start;
    @BindView(R.id.button2)
    Button pause;
    @BindView(R.id.button3)
    Button reset;
    @BindView(R.id.button4)
    Button lap;
    @BindView(R.id.textView)
    TextView textView;

    private long MilisecondTime;
    private long StartTime;
    private long TimeBuf;
    private long UpdateTime = 0L;
    private Handler handler;
    private int Seconds, Minutes, MiliSeconds;
    private ListView    listView;
    private String[] listElements = new String[]{};
    private List<String> listElementsArrayList;
    private ArrayAdapter<String> adapter;
    private Context context;



    public StopwatchFragment() {

        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_stopwatch, container, false);
        ButterKnife.bind(this,view);

        listView = (ListView)view.findViewById(R.id.listview1);
        handler = new Handler();
        listElementsArrayList = new ArrayList<>(Arrays.asList(listElements));
        adapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,listElementsArrayList);
        listView.setAdapter(adapter);


        return view;
    }

    @OnClick(R.id.button2)
    void clickOnPause(){
        TimeBuf += MilisecondTime;
        handler.removeCallbacks(runnable);
        reset.setEnabled(true);
    }
    @OnClick(R.id.button)
    void clickOnStart(){
        StartTime = SystemClock.uptimeMillis();
        handler.postDelayed(runnable,0);
        reset.setEnabled(false);

    }
    @OnClick(R.id.button3)
    void clickOnReset(){
        MilisecondTime=0L;
        StartTime = 0L;
        TimeBuf = 0L;
        UpdateTime = 0L;
        Seconds = 0;
        Minutes = 0;
        MiliSeconds=0;
        textView.setText("00:00:00");
        listElementsArrayList.clear();
        adapter.notifyDataSetChanged();


    }

    @OnClick(R.id.button4)
    void clickOnLap(){
        listElementsArrayList.add(textView.getText().toString());
        adapter.notifyDataSetChanged();
    }

    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            MilisecondTime = SystemClock.uptimeMillis() - StartTime;
            UpdateTime = TimeBuf+MilisecondTime;
            Seconds = (int)(UpdateTime/1000);
            Minutes = Seconds/60;
            Seconds = Seconds%60;
            MiliSeconds= (int)(UpdateTime%1000);
            textView.setText("" + Minutes + ":"
                    + String.format("%02d", Seconds) + ":"
                    + String.format("%03d", MiliSeconds));
            handler.postDelayed(this,0);
        }
    };

}
