package playmzubair.com.myalarmandroid.AndroidReceiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import playmzubair.com.myalarmandroid.Reminder_Modelclass;

import static android.content.Context.ALARM_SERVICE;


public class onBootReceiver extends BroadcastReceiver {


    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    ArrayList<Reminder_Modelclass> reminderList;
    Calendar calendar;
    AlarmManager alarmManager;
    Reminder_Modelclass reminderModelInstance;
    String time[];
    int m,h;

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            // Set the alarm here.
            alarmManager=(AlarmManager)context.getSystemService(ALARM_SERVICE);
            preferences= PreferenceManager.getDefaultSharedPreferences(context);
            resetWeekGoal(context);

            reminderList=retrieveList("reminderlist");

            if(reminderList!=null){
                if(reminderList.size()>0){

                    for(int i=0;i<reminderList.size();i++){

                        if(retrieveList("reminder_repeat").equals("daily")){

                            reminderModelInstance=reminderList.get(i);
                            time=reminderModelInstance.getTime().split(":");
                            setAlarm(context,reminderModelInstance.getRequestCode(), Integer.parseInt(time[0]), Integer.parseInt(time[1]));
                        }else if(retrieveList("reminder_repeat").equals("custom")){

                            String s[]=reminderModelInstance.getTime().split(":");
                            String dys[]=reminderModelInstance.getDays().split(" ");
                            for(int j=0;j<dys.length;j++){
                                if(dys[j].trim().equals("sat")){

                                    customAlarm(context, Calendar.SATURDAY, Integer.parseInt(s[0]), Integer.parseInt(s[1]),reminderModelInstance.getRequestCode()+7);

                                }else if(dys[i].trim().equals("sun")){
                                    customAlarm(context, Calendar.SUNDAY, Integer.parseInt(s[0]), Integer.parseInt(s[1]),reminderModelInstance.getRequestCode()+1);

                                }else if(dys[i].trim().equals("mon")){
                                    customAlarm(context, Calendar.MONDAY, Integer.parseInt(s[0]), Integer.parseInt(s[1]),reminderModelInstance.getRequestCode()+2);

                                }else if(dys[i].trim().equals("tue")){
                                    customAlarm(context, Calendar.TUESDAY, Integer.parseInt(s[0]), Integer.parseInt(s[1]),reminderModelInstance.getRequestCode()+3);

                                }else if(dys[i].trim().equals("wed")){
                                    customAlarm(context, Calendar.WEDNESDAY, Integer.parseInt(s[0]), Integer.parseInt(s[1]),reminderModelInstance.getRequestCode()+4);

                                }else if(dys[i].trim().equals("thu")){
                                    customAlarm(context, Calendar.THURSDAY, Integer.parseInt(s[0]), Integer.parseInt(s[1]),reminderModelInstance.getRequestCode()+5);

                                }else if(dys[i].trim().equals("fri")){
                                    customAlarm(context, Calendar.FRIDAY, Integer.parseInt(s[0]), Integer.parseInt(s[1]),reminderModelInstance.getRequestCode()+6);

                                }
                            }
                        }

                    }

                }
            }


        }
    }



    public void customAlarm(Context context, int dayOfWeek, int hourOfDay, int minute, int requestCode){

        //  Calendar today=Calendar.getInstance();
        calendar= Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
        calendar.set(Calendar.MINUTE,minute);
        calendar.set(Calendar.SECOND,1);
        calendar.set(Calendar.DAY_OF_WEEK,dayOfWeek);

        //    showToast(calendar.get(Calendar.DAY_OF_MONTH)+"\n"+updated.get(Calendar.DAY_OF_MONTH));
        if(calendar.getTimeInMillis()< System.currentTimeMillis()){
            /////needs to be configured after juma...........
            calendar.add(Calendar.WEEK_OF_MONTH,1);
            //  showToast(calendar.get(Calendar.DAY_OF_WEEK)+"\n"+calendar.get(Calendar.DAY_OF_MONTH)+"\n"+calendar.get(Calendar.MONTH));
        }
        Intent intent = new Intent(context,NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,requestCode,intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),alarmManager.INTERVAL_DAY*7,pendingIntent);

    }

    public void setAlarm(Context context, int requestCode, int hourOfDay, int minute){

     //  Calendar current=Calendar.getInstance();

        calendar= Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
        calendar.set(Calendar.MINUTE,minute);
        calendar.set(Calendar.SECOND,1);

        if(calendar.getTimeInMillis()< System.currentTimeMillis()){
            calendar.add(Calendar.DAY_OF_MONTH,1);
        }

            Intent intent = new Intent(context,NotificationReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,requestCode,intent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),alarmManager.INTERVAL_DAY,pendingIntent);


    }


    public void resetWeekGoal(Context context){

        Calendar calendar= Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK,1);
        calendar.set(Calendar.HOUR_OF_DAY,00);
        calendar.set(Calendar.MINUTE,1);
        calendar.set(Calendar.SECOND,1);

        if(calendar.getTimeInMillis()< System.currentTimeMillis()){
            /////needs to be configured after juma...........
            calendar.add(Calendar.WEEK_OF_MONTH,1);
            //  showToast(calendar.get(Calendar.DAY_OF_WEEK)+"\n"+calendar.get(Calendar.DAY_OF_MONTH)+"\n"+calendar.get(Calendar.MONTH));
        }

            Intent intent = new Intent(context,WeekGoalReset_Receiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,007,intent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY*7,pendingIntent);

    }

//
//    public void saveList(String key,ArrayList<Reminder_Modelclass> list){
////Set the values
//        Gson gson=new Gson();
//        String reminders=gson.toJson(list);
//
//        editor=preferences.edit();
////        Set<String> set = new HashSet<String>();
////        set.addAll(list);
//        editor.putString(key, reminders);
//        editor.commit();
//    }


    public ArrayList<Reminder_Modelclass> retrieveList(String key){


        List<Reminder_Modelclass> remind_list;
        if(preferences.contains(key)){

            try
            {
                String json_reminders=preferences.getString(key,"");
                Gson gson=new Gson();
                Reminder_Modelclass[] reminders_array=gson.fromJson(json_reminders,Reminder_Modelclass[].class);
                remind_list= Arrays.asList(reminders_array);
                return new ArrayList<Reminder_Modelclass>(remind_list);

            }catch (Exception ex){

                ex.printStackTrace();
                return null;
            }

        }else
        {
            return null;
        }

    }
}
