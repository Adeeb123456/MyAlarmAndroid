package playmzubair.com.myalarmandroid.AndroidReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class WeekGoalReset_Receiver extends BroadcastReceiver {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    public void onReceive(Context context, Intent intent) {
        preferences= PreferenceManager.getDefaultSharedPreferences(context);
        saveData("sat_goal_acheive",0+"");
        saveData("sun_goal_acheive",0+"");
        saveData("mon_goal_acheive",0+"");
        saveData("tue_goal_acheive",0+"");
        saveData("wed_goal_acheive",0+"");
        saveData("thu_goal_acheive",0+"");
        saveData("fri_goal_acheive",0+"");

    }

    private void saveData(String key, String value){
        try
        {
            editor=preferences.edit();
            editor.putString(key,value);
            editor.commit();

        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

}
