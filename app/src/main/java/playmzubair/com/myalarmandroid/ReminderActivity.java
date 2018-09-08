package playmzubair.com.myalarmandroid;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import playmzubair.com.myalarmandroid.Adapters.ReminderRecyclerViewAdapter;
import playmzubair.com.myalarmandroid.AndroidReceiver.NotificationReceiver;

public class ReminderActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton setreminder_fab;

    ReminderRecyclerViewAdapter adapter;
    TimePickerDialog timePickerDialog;
    Calendar calendar;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    ArrayList<Reminder_Modelclass> reminderModelArrayList;
    TextView noreminder_tv;
    AlarmManager alarmManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);


        alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);
//        ComponentName receiver = new ComponentName(context, SampleBootReceiver.class);
//        PackageManager pm = context.getPackageManager();
//
//        pm.setComponentEnabledSetting(receiver,
//                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
//                PackageManager.DONT_KILL_APP);

        preferences= PreferenceManager.getDefaultSharedPreferences(ReminderActivity.this);

        setreminder_fab=findViewById(R.id.setreminder_fab);
        noreminder_tv=findViewById(R.id.noreminder_tv);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {

            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("remindeer");
        }
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        reminderModelArrayList=retrieveList("reminderlist");

        if(reminderModelArrayList!=null){
            if(reminderModelArrayList.size()>0){

                noreminder_tv.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                adapter=new ReminderRecyclerViewAdapter(ReminderActivity.this,reminderModelArrayList);
                LinearLayoutManager layoutManager=new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(adapter);
            }else
            {
                hideRecyclerView();

            }

        }else
        {
           hideRecyclerView();

        }







        setreminder_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setReminder();
            }
        });


    }


    private void setReminder(){

        calendar= Calendar.getInstance();

        timePickerDialog=new TimePickerDialog(ReminderActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                Reminder_Modelclass reminder_modelclass;

                String days="";

                  if (preferences.getString("sat_exrcise", "").equals("1")) {

                       days="Sat";

        } else if (preferences.getString("sat_exrcise", "").equals("0")) {
                      days="";

        }

        if (preferences.getString("sun_exrcise", "").equals("1")) {
            days+="Sun";


        } else if (preferences.getString("sun_exrcise", "").equals("0")) {


        }


        if (preferences.getString("mon_exrcise", "").equals("1")) {



        } else if (preferences.getString("mon_exrcise", "").equals("0")) {
            days+="Mon";


        }

        if (preferences.getString("tue_exrcise", "").equals("1")) {
            days+="Tue";

        } else if (preferences.getString("tue_exrcise", "").equals("0")) {


        }

        if (preferences.getString("wed_exrcise", "").equals("1")) {
            days+="Wed";


        } else if (preferences.getString("wed_exrcise", "").equals("0")) {



        }

        if (preferences.getString("thu_exrcise", "").equals("1")) {
            days+="Thu";


        } else if (preferences.getString("thu_exrcise", "").equals("0")) {



        }

        if (preferences.getString("fri_exrcise", "").equals("1")) {

            days+="Fri";


        } else if (preferences.getString("fri_exrcise", "").equals("0")) {


        }

                if(days.equalsIgnoreCase("")){
                      days="Sat Sun Mon";
                }



              //  alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
               /* reminderModelArrayList=retrieveList("reminderlist");
                if(reminderModelArrayList!=null){
                    reminder_modelclass=new Reminder_Modelclass(hourOfDay+":"+minute,"Sat Sun Mon Tue Wed Thu Fri",1,(int) System.currentTimeMillis(),"daily");
                    reminderModelArrayList.add(reminder_modelclass);

                        setAlarm(reminder_modelclass.getRequestCode(),hourOfDay,minute);

                    saveList("reminderlist",reminderModelArrayList);
                    displayReyclerView(reminderModelArrayList);
                    reminder_modelclass=null;

                }else
                {
                    reminderModelArrayList=new ArrayList<>();
                    reminder_modelclass=new Reminder_Modelclass(hourOfDay+":"+minute,"sat sun mon tue wed thu fri",1,(int) System.currentTimeMillis(),"daily");
                    reminderModelArrayList.add(reminder_modelclass);
                    setAlarm(reminder_modelclass.getRequestCode(),hourOfDay,minute);
                    saveList("reminderlist",reminderModelArrayList);
                    displayReyclerView(reminderModelArrayList);
                    reminder_modelclass=null;
                }*/

                reminder_modelclass=new Reminder_Modelclass(hourOfDay+":"+minute,days,1,(int) System.currentTimeMillis(),"daily");
                reminderModelArrayList.add(reminder_modelclass);

                setAlarm(reminder_modelclass.getRequestCode(),hourOfDay,minute);

                saveList("reminderlist",reminderModelArrayList);
                displayReyclerView(reminderModelArrayList);



            //    Toast.makeText(getApplicationContext(),"HOur: "+hourOfDay+"\n"+"Minute: "+minute,Toast.LENGTH_SHORT).show();
            }
        },calendar.get(Calendar.HOUR),calendar.get(Calendar.MINUTE),true);

        timePickerDialog.show();

    }



    public void customAlarm(int dayOfWeek,int hourOfDay,int minute,int requestCode){

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
            Intent intent = new Intent(getApplicationContext(),NotificationReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),requestCode,intent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),alarmManager.INTERVAL_DAY*7,pendingIntent);

    }

    public void setAlarm(int requestCode,int hourOfDay,int minute){
        calendar= Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
        calendar.set(Calendar.MINUTE,minute);
        calendar.set(Calendar.SECOND,1);

     //   calendar.
Calendar calendarCurrent=Calendar.getInstance();

if(calendar.before(calendarCurrent)){
    calendar.add(Calendar.DATE,1);
}

        long alarmTime= System.currentTimeMillis()+9000;


       /* if(calendar.getTimeInMillis()< System.currentTimeMillis()){
           calendar.add(Calendar.DAY_OF_MONTH,1);
        }*/

            Intent intent = new Intent(getApplicationContext(),NotificationReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),requestCode,intent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),alarmManager.INTERVAL_DAY,pendingIntent);

    }

    public void removeAlarm(int requestCode){
        alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);
        if (alarmManager!= null) {
            Intent intent = new Intent(getApplicationContext(),NotificationReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),requestCode,intent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.cancel(pendingIntent);
        }
    }

    public void removeCustomAlarm(int requestCode){
        alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);
        if (alarmManager!= null) {

            for(int i=1;i<=7;i++){
                Intent intent = new Intent(getApplicationContext(),NotificationReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),requestCode+i,intent, PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.cancel(pendingIntent);
            }

        }

    }

    public void displayReyclerView(ArrayList<Reminder_Modelclass> arrayList){
        try{
            noreminder_tv.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            adapter=new ReminderRecyclerViewAdapter(ReminderActivity.this ,arrayList);
            LinearLayoutManager layoutManager=new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);
        reminderModelArrayList=retrieveList("reminderlist");
        if(reminderModelArrayList!=null){
            displayReyclerView(reminderModelArrayList);
        }



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case android.R.id.home:
               navigatetoMain();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    private void navigatetoMain(){
        Intent intent=new Intent(ReminderActivity.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        navigatetoMain();
    }

    public void deleteReminder(int position){

        Reminder_Modelclass r;
        reminderModelArrayList=retrieveList("reminderlist");
        if(reminderModelArrayList!=null){

            r=reminderModelArrayList.get(position);

//            if(preferences.getString("reminder_repeat","").equals("daily")){
//                removeAlarm(r.getRequestCode());
//            }else if(preferences.getString("reminder_repeat","").equals("custom")){
//                removeCustomAlarm(r.getRequestCode());
//            }
            removeAlarm(r.getRequestCode());
            removeCustomAlarm(r.getRequestCode());

            reminderModelArrayList.remove(position);
            saveList("reminderlist",reminderModelArrayList);
            if(reminderModelArrayList.size()>0){
                displayReyclerView(reminderModelArrayList);
            }else
            {
                hideRecyclerView();
            }

            showToast(position+" reminder removed");
        }
    }

    public void turnoFFAlarm(int position){

        Reminder_Modelclass r;
        reminderModelArrayList=retrieveList("reminderlist");
        if(reminderModelArrayList!=null) {
            r = reminderModelArrayList.get(position);
            removeAlarm(r.getRequestCode());
        }
    }


    public void turnoFFCustomAlarm(int position){
        Reminder_Modelclass r;
        reminderModelArrayList=retrieveList("reminderlist");
        if(reminderModelArrayList!=null) {
            r = reminderModelArrayList.get(position);
            removeCustomAlarm(r.getRequestCode());
        }
    }




    private void hideRecyclerView(){
        noreminder_tv.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    private void showToast(String msg){

        Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_SHORT).show();
    }

    public void saveList(String key, ArrayList<Reminder_Modelclass> list){
//Set the values
        Gson gson=new Gson();
        String reminders=gson.toJson(list);

        editor=preferences.edit();
//        Set<String> set = new HashSet<String>();
//        set.addAll(list);
        editor.putString(key, reminders);
        editor.commit();
    }

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


    @Override
    public void onPause() {

        super.onPause();
    }


    @Override
    public void onDestroy() {

        super.onDestroy();
    }
}

