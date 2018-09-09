package playmzubair.com.myalarmandroid.extra.notinusebythisapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.designerclicksol.myworkout.R;
import com.example.designerclicksol.myworkout.adapters.MultiViewTypeAdapter2;
import com.example.designerclicksol.myworkout.managers.DataManager;
import com.example.designerclicksol.myworkout.model.ViewModelForAdapter;
import com.example.designerclicksol.myworkout.utils.AminUtils;
import com.example.designerclicksol.myworkout.utils.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class FourWeekGoalActivity extends AppCompatActivity implements View.OnClickListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four_week_goal);
       // View view= LayoutInflater.from(getApplicationContext()).inflate(R.layout.activity_four_week_goal,null);
        initViews(null);
    }



    RelativeLayout d1sat,d2sun,d3mon,d4tue,d5wed,d6thu,d7fri,cup;

    Button buttonStart;

    public void  initViews(View view){


   /*     d1sat=(RelativeLayout)view.findViewById(R.id.d1);

        d2sun=(RelativeLayout)view.findViewById(R.id.d2);

        d3mon=(RelativeLayout)view.findViewById(R.id.d3);
        d4tue=(RelativeLayout)view.findViewById(R.id.d4);
        d5wed=(RelativeLayout)view.findViewById(R.id.d5);
        d6thu=(RelativeLayout)view.findViewById(R.id.d6);

        d7fri=(RelativeLayout)view.findViewById(R.id.d7);*/

        buttonStart=(Button)findViewById(R.id.btnStart);

        AminUtils.zoomInOut(getApplicationContext(),buttonStart);
//initRecyclerAdapter();

buttonStart.setOnClickListener((View v)->{
    if(DataManager.retrieveStringData("exerciseNameAD").equalsIgnoreCase("fullbody1")){
        startFullBodyExList();
    }else {
     startLowerBodyEx();
    }
});



setCurrentDateCircle();
    }


    public void startFullBodyExList() {
        Intent intent = new Intent(getApplicationContext(), ExercisesListGeneralActivity.class);
        intent.putExtra(Constants.intentKeyExcerciseType, getResources().getString(R.string.CHESTBEGINNER));


        startActivity(intent);

    }

    public void startLowerBodyEx() {
        Intent intent = new Intent(getApplicationContext(), ExercisesListGeneralActivity.class);
        intent.putExtra(Constants.intentKeyExcerciseType, getResources().getString(R.string.CHESTBEGINNER));
        startActivity(intent);
    }


    public void initRecyclerAdapter(){
//aad
        ArrayList<ViewModelForAdapter> list= new ArrayList();
        list.add(new ViewModelForAdapter(ViewModelForAdapter.FIRST_REWARD_CARDVIEW));
        list.add(new ViewModelForAdapter(ViewModelForAdapter.CARVIEW_ForeWeek));
        list.add(new ViewModelForAdapter(ViewModelForAdapter.THIRD_REWARD_CARDVIEW));

        MultiViewTypeAdapter2 adapter = new MultiViewTypeAdapter2(list,getApplicationContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), OrientationHelper.VERTICAL, false);
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(adapter);
    }




    public void setCurrentDateCircle(){
        Calendar calendar= Calendar.getInstance();

        String day =new SimpleDateFormat("MM").format(calendar.getTime());

        String daysList;
        if(DataManager.retrieveStringData("exerciseNameAD").equalsIgnoreCase("fullbody1")){
            daysList= DataManager.retrieveStringData("Listof30DaysFullBody1");
        }else {
            daysList= DataManager.retrieveStringData("Listof30DaysFullBody2");
        }

        String daykOfMonth;
        if(daysList==null||daysList.length()<=0){
            return;
        }

        String[] dysArray=daysList.split(",");

        for(int i=0; i<dysArray.length;i++) {
            daykOfMonth = dysArray[i];


            if (daykOfMonth.equalsIgnoreCase("1")) {
                findViewById(R.id.d1).setBackgroundResource(R.drawable.ic_tickmy);
            } else if (daykOfMonth.equalsIgnoreCase("2")) {
                findViewById(R.id.d2).setBackgroundResource(R.drawable.ic_tickmy);
            } else if (daykOfMonth.equalsIgnoreCase("3")) {
                findViewById(R.id.d3).setBackgroundResource(R.drawable.ic_tickmy);
            } else if (daykOfMonth.equalsIgnoreCase("4")) {
                findViewById(R.id.d4).setBackgroundResource(R.drawable.ic_tickmy);
            } else if (daykOfMonth.equalsIgnoreCase("5")) {
                findViewById(R.id.d5).setBackgroundResource(R.drawable.ic_tickmy);
            } else if (daykOfMonth.equalsIgnoreCase("6")) {
                findViewById(R.id.d6).setBackgroundResource(R.drawable.ic_tickmy);
            } else if (daykOfMonth.equalsIgnoreCase("7")) {
                findViewById(R.id.d7).setBackgroundResource(R.drawable.ic_tickmy);
            } else if (daykOfMonth.equalsIgnoreCase("8")) {
                findViewById(R.id.d8).setBackgroundResource(R.drawable.ic_tickmy);
            } else if (daykOfMonth.equalsIgnoreCase("9")) {
                findViewById(R.id.d9).setBackgroundResource(R.drawable.ic_tickmy);
            } else if (daykOfMonth.equalsIgnoreCase("10")) {
                findViewById(R.id.d10).setBackgroundResource(R.drawable.ic_tickmy);
            } else if (daykOfMonth.equalsIgnoreCase("11")) {
                findViewById(R.id.d11).setBackgroundResource(R.drawable.ic_tickmy);
            } else if (daykOfMonth.equalsIgnoreCase("12")) {
                findViewById(R.id.d12).setBackgroundResource(R.drawable.ic_tickmy);
            } else if (daykOfMonth.equalsIgnoreCase("13")) {
                findViewById(R.id.d13).setBackgroundResource(R.drawable.ic_tickmy);
            } else if (daykOfMonth.equalsIgnoreCase("14")) {
                findViewById(R.id.d14).setBackgroundResource(R.drawable.ic_tickmy);
            } else if (daykOfMonth.equalsIgnoreCase("15")) {
                findViewById(R.id.d14).setBackgroundResource(R.drawable.ic_tickmy);
            } else if (daykOfMonth.equalsIgnoreCase("16")) {
                findViewById(R.id.d1).setBackgroundResource(R.drawable.ic_tickmy);
            } else if (daykOfMonth.equalsIgnoreCase("17")) {

            } else if (daykOfMonth.equalsIgnoreCase("18")) {

            } else if (daykOfMonth.equalsIgnoreCase("19")) {

            } else if (daykOfMonth.equalsIgnoreCase("20")) {

            } else if (daykOfMonth.equalsIgnoreCase("21")) {

            } else if (daykOfMonth.equalsIgnoreCase("22")) {

            } else if (daykOfMonth.equalsIgnoreCase("23")) {

            } else if (daykOfMonth.equalsIgnoreCase("24")) {

            } else if (daykOfMonth.equalsIgnoreCase("25")) {

            } else if (daykOfMonth.equalsIgnoreCase("26")) {

            } else if (daykOfMonth.equalsIgnoreCase("27")) {

            } else if (daykOfMonth.equalsIgnoreCase("28")) {

            } else if (daykOfMonth.equalsIgnoreCase("29")) {

            } else if (daykOfMonth.equalsIgnoreCase("30")) {

            }

        }
      /*  if(day.equals("Fri")){

            if(DataManager.retrieveStringData("fri_exrcise").equals("1")){

                if(DataManager.retrieveStringData("fri_goal_acheive").equals("1")){
                    d7fri.setBackgroundResource(R.drawable.ic_tickmy);

                }else {

                    d7fri.setBackgroundResource(R.drawable.round_blue_stroke);
                }

            }

        }else  if(day.equals("Sat")){

            if(DataManager.retrieveStringData("fri_exrcise").equals("1")){

                if(DataManager.retrieveStringData("fri_goal_acheive").equals("1")){
                    d1sat.setBackgroundResource(R.drawable.ic_tickmy);

                }else {

                    d1sat.setBackgroundResource(R.drawable.round_blue_stroke);
                }

            }

        }
        else  if(day.equals("Sun")){

            if(DataManager.retrieveStringData("fri_exrcise").equals("1")){

                if(DataManager.retrieveStringData("fri_goal_acheive").equals("1")){
                    d2sun.setBackgroundResource(R.drawable.ic_tickmy);

                }else {

                    d2sun.setBackgroundResource(R.drawable.round_blue_stroke);
                }

            }

        }else  if(day.equals("Mon")){

            if(DataManager.retrieveStringData("fri_exrcise").equals("1")){

                if(DataManager.retrieveStringData("fri_goal_acheive").equals("1")){
                    d3mon.setBackgroundResource(R.drawable.ic_tickmy);

                }else {

                    d3mon.setBackgroundResource(R.drawable.round_blue_stroke);
                }

            }

        }
        else  if(day.equals("Tue")){

            if(DataManager.retrieveStringData("fri_exrcise").equals("1")){

                if(DataManager.retrieveStringData("fri_goal_acheive").equals("1")){
                    d4tue.setBackgroundResource(R.drawable.ic_tickmy);

                }else {

                    d4tue.setBackgroundResource(R.drawable.round_blue_stroke);
                }

            }

        }
        else  if(day.equals("Wed")){

            if(DataManager.retrieveStringData("fri_exrcise").equals("1")){

                if(DataManager.retrieveStringData("fri_goal_acheive").equals("1")){
                    d5wed.setBackgroundResource(R.drawable.ic_tickmy);

                }else {

                    d5wed.setBackgroundResource(R.drawable.round_blue_stroke);
                }

            }

        }   else  if(day.equals("Thu")){

            if(DataManager.retrieveStringData("fri_exrcise").equals("1")){

                if(DataManager.retrieveStringData("fri_goal_acheive").equals("1")){
                    d6thu.setBackgroundResource(R.drawable.ic_tickmy);

                }else {

                    d6thu.setBackgroundResource(R.drawable.round_blue_stroke);
                }

            }

        }*/


    }

    @Override
    public void onClick(View v) {

    }



    public void getDaysInMonth(){
       String month =new SimpleDateFormat("mm").format(Calendar.getInstance().getTimeInMillis());
int numberOfDaysInMonth=30;
        switch(month){
            case "1":
                numberOfDaysInMonth=31;
                break;
            case "2":
                numberOfDaysInMonth=28;
                break;
            case "3":
                numberOfDaysInMonth=31;
                break;
            case "4":
                numberOfDaysInMonth=30;
                break;
            case "5":
                numberOfDaysInMonth=31;
                break;
            case "6":
                numberOfDaysInMonth=30;
                break;
            case "7":
                numberOfDaysInMonth=31;
                break;
            case "8":
                numberOfDaysInMonth=31;
                break;
            case "9":
                numberOfDaysInMonth=30;
                break;
            case "10":
                numberOfDaysInMonth=31;
                break;
            case "11":
                numberOfDaysInMonth=30;
                break;
            case "12":
                numberOfDaysInMonth=31;
                break;

        }

    }
}
