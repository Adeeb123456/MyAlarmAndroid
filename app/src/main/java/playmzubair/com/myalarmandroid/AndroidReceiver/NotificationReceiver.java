package playmzubair.com.myalarmandroid.AndroidReceiver;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import playmzubair.com.myalarmandroid.MainActivity;
import playmzubair.com.myalarmandroid.R;
import playmzubair.com.myalarmandroid.ReminderActivity;
import playmzubair.com.myalarmandroid.Reminder_Modelclass;


public class NotificationReceiver extends BroadcastReceiver {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    public void onReceive(Context context, Intent intent) {
        preferences= PreferenceManager.getDefaultSharedPreferences(context);

        String day=new SimpleDateFormat("EE").format(Calendar.getInstance().getTimeInMillis());


       /* if (preferences.getString("sat_exrcise", "").equals("1")
                ||preferences.getString("sun_exrcise", "").equals("1")
                ||preferences.getString("mon_exrcise", "").equals("1")
                ||preferences.getString("tue_exrcise", "").equals("1")
                ||preferences.getString("wed_exrcise", "").equals("1")
                ||preferences.getString("thu_exrcise", "").equals("1")
                ||preferences.getString("fri_exrcise", "").equals("1")) {



        }*/

        if (preferences.getString("sat_exrcise", "").equals("1")) {
            if(day.equalsIgnoreCase("Sat")){
showNotification(context);
            }


        }else

        if (preferences.getString("sun_exrcise", "").equals("1")) {

if(day.equalsIgnoreCase("Sun")){
    showNotification(context);
}

        } else


        if (preferences.getString("mon_exrcise", "").equals("1")) {
            if(day.equalsIgnoreCase("Mon")){
                showNotification(context);
            }


        } else

        if (preferences.getString("tue_exrcise", "").equals("1")) {
            if(day.equalsIgnoreCase("Tue")){
                showNotification(context);
            }

        }
        else

        if (preferences.getString("wed_exrcise", "").equals("1")) {
            if(day.equalsIgnoreCase("Wed")){
                showNotification(context);
            }


        }
        else

        if (preferences.getString("thu_exrcise", "").equals("1")) {

            if(day.equalsIgnoreCase("Thu")){
                showNotification(context);
            }

        }
        else

        if (preferences.getString("fri_exrcise", "").equals("1")) {

            if(day.equalsIgnoreCase("Fri")){
                showNotification(context);
            }


        }












    }

    public void isAlaramedTriggereToday(){
        String day=new SimpleDateFormat("EE").format(Calendar.getInstance().getTimeInMillis());

        ArrayList<Reminder_Modelclass> reminder_modelclasses;



    }

    public void showNotification(Context context){
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent1 = new Intent(context,MainActivity.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //if we want ring on notifcation then uncomment below line//
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,100,intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context).
                setSmallIcon(R.mipmap.ic_launcher).
                setContentIntent(pendingIntent).
                setContentText("Daily exercise is good for health").
                setContentTitle("Workout Time").
                setSound(alarmSound).
                setAutoCancel(true);
        notificationManager.notify(100,builder.build());
    }

}
