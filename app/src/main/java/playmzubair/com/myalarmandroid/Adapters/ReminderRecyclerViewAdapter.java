package playmzubair.com.myalarmandroid.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;



import java.util.ArrayList;
import java.util.Calendar;

import playmzubair.com.myalarmandroid.R;
import playmzubair.com.myalarmandroid.ReminderActivity;
import playmzubair.com.myalarmandroid.Reminder_Modelclass;


public class ReminderRecyclerViewAdapter extends RecyclerView.Adapter {

    ArrayList<Reminder_Modelclass> reminderList;

    ReminderActivity reminderActivity;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;


    public ReminderRecyclerViewAdapter(ReminderActivity mContext, ArrayList<Reminder_Modelclass> reminderList) {
        this.reminderList = reminderList;
        this.reminderActivity=mContext;
        preferences= PreferenceManager.getDefaultSharedPreferences(reminderActivity);

    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=  LayoutInflater.from(parent.getContext()).inflate(R.layout.reminder_cardlayout,parent,false);
        return new reminderViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        final Reminder_Modelclass reminder=reminderList.get(position);
        if(reminder!=null){

            ((reminderViewHolder)holder).time.setText(reminder.getTime());
            ((reminderViewHolder)holder).days.setText(reminder.getDays());
            if (reminder.getBtnVal()==1)
            {
                ((reminderViewHolder)holder).onOff_btn.setChecked(true);
            }else if(reminder.getBtnVal()==0)
            {
                ((reminderViewHolder)holder).onOff_btn.setChecked(false);
            }
            ((reminderViewHolder)holder).onOff_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if(isChecked)
                    {
                        reminder.setBtnVal(1);
                        if(reminder.getReminder_type().equals("daily")){
                            String s[]=reminder.getTime().split(":");
                            //   Toast.makeText(reminderActivity,Calendar.SATURDAY+"",Toast.LENGTH_SHORT).show();
                            reminderActivity.setAlarm(reminder.getRequestCode(), Integer.parseInt(s[0]), Integer.parseInt(s[1]));

                        }else if(reminder.getReminder_type().equals("custom")){
                            String s[]=reminder.getTime().split(":");
                            String dys[]=reminder.getDays().split(" ");

                            for(int i=0;i<dys.length;i++){
                                if(dys[i].trim().equals("sat")){

                                    reminderActivity.customAlarm(Calendar.SATURDAY, Integer.parseInt(s[0]), Integer.parseInt(s[1]),reminder.getRequestCode()+7);

                                }else if(dys[i].trim().equals("sun")){
                                    reminderActivity.customAlarm(Calendar.SUNDAY, Integer.parseInt(s[0]), Integer.parseInt(s[1]),reminder.getRequestCode()+1);

                                }else if(dys[i].trim().equals("mon")){
                                    reminderActivity.customAlarm(Calendar.MONDAY, Integer.parseInt(s[0]), Integer.parseInt(s[1]),reminder.getRequestCode()+2);

                                }else if(dys[i].trim().equals("tue")){
                                    reminderActivity.customAlarm(Calendar.TUESDAY, Integer.parseInt(s[0]), Integer.parseInt(s[1]),reminder.getRequestCode()+3);

                                }else if(dys[i].trim().equals("wed")){
                                    reminderActivity.customAlarm(Calendar.WEDNESDAY, Integer.parseInt(s[0]), Integer.parseInt(s[1]),reminder.getRequestCode()+4);

                                }else if(dys[i].trim().equals("thu")){
                                    reminderActivity.customAlarm(Calendar.THURSDAY, Integer.parseInt(s[0]), Integer.parseInt(s[1]),reminder.getRequestCode()+5);

                                }else if(dys[i].trim().equals("fri")){
                                    reminderActivity.customAlarm(Calendar.FRIDAY, Integer.parseInt(s[0]), Integer.parseInt(s[1]),reminder.getRequestCode()+6);

                                }
                            }
                        }

                        reminderActivity.saveList("reminderlist",reminderList);

                    }else
                    {
                        reminder.setBtnVal(0);
                        reminderActivity.turnoFFAlarm(position);
                        reminderActivity.turnoFFCustomAlarm(position);
                        reminderActivity.saveList("reminderlist",reminderList);

                    }
                }
            });
            ((reminderViewHolder)holder).del_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    deleteReminder(position);

                }
            });

            ((reminderViewHolder)holder).repeat_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String s[]=reminder.getTime().split(":");
                    showRepeatDaysDialog(position, Integer.parseInt(s[0]), Integer.parseInt(s[1]),reminder.getRequestCode());
                }
            });
        }

    }

    private void deleteReminder(int Position){
        reminderActivity.deleteReminder(Position);

    }


    @Override
    public int getItemCount() {
        return reminderList.size();
    }

    public static class reminderViewHolder extends RecyclerView.ViewHolder{

        TextView time;
        TextView days;
        SwitchCompat onOff_btn;
        ImageView del_btn;
        TextView repeat_tv;

        public reminderViewHolder(View itemView) {
            super(itemView);
            time=itemView.findViewById(R.id.reminder_time_tv);
            days=itemView.findViewById(R.id.days_reminder_tv);
            onOff_btn=itemView.findViewById(R.id.switch_reminder);
            del_btn=itemView.findViewById(R.id.del_btn_reminder);
            repeat_tv=itemView.findViewById(R.id.repeat_tv);

        }
    }

    private void showRepeatDaysDialog(final int pos, final int hour, final int minute, final int requestCode){


        final Reminder_Modelclass reminder=reminderList.get(pos);
        final Dialog dialog =new Dialog(reminderActivity);
        LayoutInflater inflater=(LayoutInflater)reminderActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v;
        v=inflater.inflate(R.layout.repeat_days_layout,null,false);
        final CheckBox sat_chkbx=v.findViewById(R.id.sat_chkbx);
        final CheckBox sun_chkbx=v.findViewById(R.id.sun_chkbx);
        final CheckBox mon_chkbx=v.findViewById(R.id.mon_chkbx);
        final CheckBox tue_chkbx=v.findViewById(R.id.tue_chkbx);
        final CheckBox wed_chkbx=v.findViewById(R.id.wed_chkbx);
        final CheckBox thu_chkbx=v.findViewById(R.id.thu_chkbx);
        final CheckBox fri_chkbx=v.findViewById(R.id.fri_chkbx);
        Button ok_btn=v.findViewById(R.id.ok_btn_repeatdays_selection);
        final String[]days=new String[7];




        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reminderActivity.removeAlarm(requestCode);
                reminderActivity.removeCustomAlarm(requestCode);
                reminder.setDays("");

                if(sat_chkbx.isChecked()){
                    reminderActivity.customAlarm(Calendar.SATURDAY,hour,minute,requestCode+7);
                    days[0]="sat ";
                }
                else {
                    days[0]="";
                 //   reminderActivity.removeCustomAlarm(requestCode);
                }



                    if(sun_chkbx.isChecked()){
                        reminderActivity.customAlarm(Calendar.SUNDAY,hour,minute,requestCode+1);
                        days[1]="sun ";

                }else {
                        days[1]="";
                    }


                if(mon_chkbx.isChecked()){
                    reminderActivity.customAlarm(Calendar.MONDAY,hour,minute,requestCode+2);
                    days[2]="mon ";

                }else {
                    days[2]="";
                }


                if(tue_chkbx.isChecked()){
                    reminderActivity.customAlarm(Calendar.TUESDAY,hour,minute,requestCode+3);
                    days[3]="tue ";

                }else {
                    days[3]="";
                }


                if(wed_chkbx.isChecked()){
                    reminderActivity.customAlarm(Calendar.WEDNESDAY,hour,minute,requestCode+4);
                    days[4]="wed ";
                }else {
                    days[4]="";
                }


                if(thu_chkbx.isChecked()){
                    reminderActivity.customAlarm(Calendar.THURSDAY,hour,minute,requestCode+5);
                    days[5]="thu ";
                }else {
                    days[5]="";
                }


                if(fri_chkbx.isChecked()){
                    reminderActivity.customAlarm(Calendar.FRIDAY,hour,minute,requestCode+6);
                   days[6]="fri ";
                }else
                {
                    days[6]="";
                }
                if(!sat_chkbx.isChecked()&&!sun_chkbx.isChecked()&&!mon_chkbx.isChecked()&&!tue_chkbx.isChecked()&&!wed_chkbx.isChecked()&&!thu_chkbx.isChecked()&&!fri_chkbx.isChecked()) {

                    reminderActivity.setAlarm(requestCode,hour,minute);
                    reminder.setDays("sat sun mon tue wed thu fri");
                    reminder.setReminder_type("daily");
                }else {

                    reminder.setReminder_type("custom");
                    String sp="";
                    for(int i=0;i<7;i++){

                        if(!days[i].equals("")){
                            sp=sp+days[i];
                            if(i!=6){
                                sp=sp+" ";
                            }
                        }
                    }

                    reminder.setDays(sp);
                }

                dialog.dismiss();
                reminderActivity.saveList("reminderlist",reminderList);
                reminderActivity.displayReyclerView(reminderList);

            }
        });


        dialog.setContentView(v);
        dialog.show();

    }

    private void saveData(String key, String val){

        try
        {
            editor=preferences.edit();
            editor.putString(key,val);
            editor.commit();

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private String retrieveData(String key){

        return preferences.getString(key,"");
    }



}
