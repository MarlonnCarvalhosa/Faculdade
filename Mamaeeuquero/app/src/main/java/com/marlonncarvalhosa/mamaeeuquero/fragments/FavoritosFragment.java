package com.marlonncarvalhosa.mamaeeuquero.fragments;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.marlonncarvalhosa.mamaeeuquero.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritosFragment extends Fragment {

    TextView text1;
    public final static long SECOND_MILLIS = 1000;
    public final static long MINUTE_MILLIS = SECOND_MILLIS*60;
    public final static long HOUR_MILLIS = MINUTE_MILLIS*60;
    private static final int MINUTES_IN_AN_HOUR = 60;
    private static final int SECONDS_IN_A_MINUTE = 60;

    private static final String FORMAT = "%02dh %02dm %02ds";
    private Calendar data;

    int seconds , minutes;


    public FavoritosFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_favoritos, container, false);
        idcampo(view);
        data = Calendar.getInstance();
        data.set(Calendar.DAY_OF_MONTH, 28);
        data.set(Calendar.HOUR_OF_DAY, 11);
        data.set(Calendar.MINUTE, 58);


        Calendar proximoDia = Calendar.getInstance();
        proximoDia.setTimeInMillis(data.getTimeInMillis());
        proximoDia.add(Calendar.DAY_OF_MONTH, 1);

        long milisegundos = (proximoDia.getTimeInMillis() - Calendar.getInstance().getTimeInMillis());

        new CountDownTimer(	milisegundos, 1000) { // adjust the milli seconds here

            public void onTick(long millisUntilFinished) {

                text1.setText(timeConversion((int) (millisUntilFinished)/1000));

            }

            public void onFinish() {
                text1.setText("Tempo expirado!");
            }
        }.start();

        return  view;

    }

    private static String timeConversion(int totalSeconds) {
        int hours = totalSeconds / MINUTES_IN_AN_HOUR / SECONDS_IN_A_MINUTE;
        int minutes = (totalSeconds - (hoursToSeconds(hours)))
                / SECONDS_IN_A_MINUTE;
        int seconds = totalSeconds
                - ((hoursToSeconds(hours)) + (minutesToSeconds(minutes)));

        return hours + "h " + minutes + "m " + seconds + "s";
    }

    private static int hoursToSeconds(int hours) {
        return hours * MINUTES_IN_AN_HOUR * SECONDS_IN_A_MINUTE;
    }

    private static int minutesToSeconds(int minutes) {
        return minutes * SECONDS_IN_A_MINUTE;
    }

    public static int secondsDiff(Date earlierDate, Date laterDate )
    {
        if( earlierDate == null || laterDate == null ) return 0;

        return (int)((laterDate.getTime()/SECOND_MILLIS) - (earlierDate.getTime()/SECOND_MILLIS));
    }

    /**
     * Get the minutes difference
     */
    public static int minutesDiff( Date earlierDate, Date laterDate )
    {
        if( earlierDate == null || laterDate == null ) return 0;

        return (int)((laterDate.getTime()/MINUTE_MILLIS) - (earlierDate.getTime()/MINUTE_MILLIS));
    }

    /**
     * Get the hours difference
     */
    public static int hoursDiff( Date earlierDate, Date laterDate )
    {
        if( earlierDate == null || laterDate == null ) return 0;

        return (int)((laterDate.getTime()/HOUR_MILLIS) - (earlierDate.getTime()/HOUR_MILLIS));
    }


    public void idcampo (View view){

        text1 = view.findViewById(R.id.countdown_text);

    }

}