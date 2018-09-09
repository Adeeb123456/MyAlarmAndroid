package playmzubair.com.myalarmandroid.extra.notinusebythisapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.designerclicksol.myworkout.ClickHandler;
import com.example.designerclicksol.myworkout.R;
import com.example.designerclicksol.myworkout.activities.helper.ParameterKeys;
import com.example.designerclicksol.myworkout.activities.helper.Parameters;
import com.example.designerclicksol.myworkout.activities.helper.UIHelper;
import com.example.designerclicksol.myworkout.activities.helper.UserHelper;
import com.example.designerclicksol.myworkout.databinding.ActivityMain2Binding;
import com.example.designerclicksol.myworkout.fragments.CalendarFragment;
import com.example.designerclicksol.myworkout.fragments.TrainingPlanFragment;
import com.example.designerclicksol.myworkout.managers.DataManager;
import com.example.designerclicksol.myworkout.model.HistoryExercisesPerformedModel;
import com.example.designerclicksol.myworkout.model.WeightModel;
import com.example.designerclicksol.myworkout.utils.Constants;
import com.example.designerclicksol.myworkout.utils.ExerciseTimeManager;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,ClickHandler.ClickActions {
    ActivityMain2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main2);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main2);
        if (binding == null)
            binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_main2, null, false);

        setSupportActionBar(binding.appBarLayout.toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, binding.appBarLayout.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // activityMain2Binding.setHandler(new ClickHandler());


        if (!DataManager.retrieveBoolData(Constants.isAppRunFirstTimeSpKey)) {
            DataManager.saveBoolData(Constants.isAppRunFirstTimeSpKey, true);
            initPersistantDataFirstTime();

        }


        setClickHandlers();

        intWeeklyGoalLayouts();
        weekGoalDataSet();

    }


    public void initFirstTimeApp() {

    }

    public void setClickHandlers() {
        binding.appBarLayout.contentMain.workoutlist.fullbody.setClickHandler(new ClickHandler(this, this));
        //   binding.appBarLayout.contentMain.workoutlist.weeklyGoalStart.setHandler(new ClickHandler(this,this));


        binding.appBarLayout.contentMain.workoutlist.weeklyGoalStart.weeklygoalBtn.setOnClickListener((View v) -> {


            startActivityForResult(new Intent(getApplicationContext(), ChoseGoal.class), Constants.WEEKLY_GOAL_ACTIVITY_RESULT_CODE);

        });


        binding.appBarLayout.contentMain.workoutlist.abs.absbeginnerLayout.setOnClickListener((View v) -> {
            startAbsBegginerExercise();
        });

        binding.appBarLayout.contentMain.workoutlist.abs.intermediateAbsLayout.setOnClickListener((View v) -> {
            startAbsIntermediateExercise();
        });


        binding.appBarLayout.contentMain.workoutlist.abs.advanceAbsLayout.setOnClickListener((View v) -> {
            startAbsAdvanceExercise();
        });


        binding.appBarLayout.contentMain.workoutlist.chest.chestbeginnerLayout.setOnClickListener((View v) -> {
            startChestBegginer();
        });

        binding.appBarLayout.contentMain.workoutlist.chest.intermediateChestLayout.setOnClickListener((View v) -> {
            startChestIntermediate();
        });

        binding.appBarLayout.contentMain.workoutlist.chest.advanceChestLayout.setOnClickListener((View v) -> {
            startChestAdvanced();
        });


        // binding.daysHolder
        binding.appBarLayout.contentMain.workoutlist.weeklyGoalStart.daysHolder.setOnClickListener((View v) -> {

            DataManager.saveStringData(Constants.SP_KEY_History, "history");

            startActivity(new Intent(getApplicationContext(), HistoryActivity.class));
        });


        binding.appBarLayout.contentMain.workoutlist.weeklyGoalStart.moretv.setOnClickListener((View v) -> {
            DataManager.saveStringData(Constants.SP_KEY_History, "history");

            startActivity(new Intent(getApplicationContext(), HistoryActivity.class));
        });


        //  binding.appBarLayout.contentMain.workoutlist.abs.setClickHandler(new ClickHandler(this,this));


    }


    public void initPersistantDataFirstTime() {
        DataManager.saveStringData(Constants.numberOfTrainingDaysSpKey, "3");
        DataManager.saveStringData(Constants.startweekDayNameSpKey, "Sunday");
        DataManager.saveBoolData(Constants.SPKeySound, true);


        DataManager.saveStringData("workout_layout", "hide");
        DataManager.saveStringData("week_goal_layout", "hide");
        DataManager.saveStringData("workout_days", "1");
        DataManager.saveStringData("grand_totalworkout_TME", 0 + "");
        DataManager.saveStringData("grand_total_workouts", 0 + "");
        DataManager.saveStringData("grand_total_calories", 0 + "");
        DataManager.saveStringData("break_time", 10 + "");
        DataManager.saveStringData("countdown_time", 10 + "");
        DataManager.saveStringData("reminder_repeat", "daily");
        DataManager.saveStringData("language", "en");
        DataManager.saveStringData("default_weight_unit", "kg");
        DataManager.saveStringData("default_height_unit", "cm");
        DataManager.saveStringData("tutorial_duration", 10 + "");
        DataManager.saveStringData("weight", 0 + "");
        DataManager.saveStringData("height", 0 + "");
        DataManager.saveStringData("week", 1 + "");
        DataManager.saveStringData("day", 1 + "");
        DataManager.saveStringData("gender", "m");
        DataManager.saveStringData("sendingactivity", "other");
        DataManager.saveStringData("dob", "1992");
        DataManager.saveStringData("bmishow", "hide");
        DataManager.saveStringData("bmi", 0 + "");
        DataManager.saveStringData("chest_Beginner_Perform_Day", 0 + "");
        DataManager.saveStringData("chest_Intermediate_Perform_Day", 0 + "");
        DataManager.saveStringData("chest_Advance_Perform_Day", 0 + "");
        DataManager.saveStringData("Abs_Beginner_Perform_Day", 0 + "");
        DataManager.saveStringData("Abs_Intermediate_Perform_Day", 0 + "");
        DataManager.saveStringData("Abs_Advance_Perform_Day", 0 + "");
        DataManager.saveStringData("shoulderback_Beginner_Perform_Day", 0 + "");
        DataManager.saveStringData("shoulderback_Intermediate_Perform_Day", 0 + "");
        DataManager.saveStringData("shoulderback_Advance_Perform_Day", 0 + "");
        DataManager.saveStringData("me_pushups", 8.0 + "");
        DataManager.saveStringData("light_exercise", 3.5 + "");
        DataManager.saveStringData("sat_exrcise", 0 + "");
        DataManager.saveStringData("sun_exrcise", 0 + "");
        DataManager.saveStringData("mon_exrcise", 0 + "");
        DataManager.saveStringData("tue_exrcise", 0 + "");
        DataManager.saveStringData("wed_exrcise", 0 + "");
        DataManager.saveStringData("thu_exrcise", 0 + "");
        DataManager.saveStringData("fri_exrcise", 0 + "");
        DataManager.saveStringData("goal_days", 0 + "");
        DataManager.saveStringData("fri_goal_acheive", 0 + "");
        DataManager.saveStringData("sat_goal_acheive", 0 + "");
        DataManager.saveStringData("sun_goal_acheive", 0 + "");
        DataManager.saveStringData("mon_goal_acheive", 0 + "");
        DataManager.saveStringData("tue_goal_acheive", 0 + "");
        DataManager.saveStringData("wed_goal_acheive", 0 + "");
        DataManager.saveStringData("thu_goal_acheive", 0 + "");
        DataManager.saveStringData("wakelock", "n");
        DataManager.saveList("history", new ArrayList<HistoryExercisesPerformedModel>());
        DataManager.saveWeightList("weightarraylist", new ArrayList<WeightModel>());
        DataManager.saveStringData("feel", 1 + "");
        DataManager.saveStringData("reset_week_goal", "y");
        DataManager.saveStringData("sound", "on");

    }






    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.Training_plans) {
            // Handle the camera action
        } else if (id == R.id.Report) {
            //"sendingactivity").equals("report")
            DataManager.saveStringData("sendingactivity", "report");

            startActivity(new Intent(getApplicationContext(), ReportActivity.class));
        } else if (id == R.id.Reminder) {
            startActivity(new Intent(Main2Activity.this, ReminderActivity.class));
        } else if (id == R.id.history) {
            startActivity(new Intent(Main2Activity.this, HistoryActivity.class));
        } else if (id == R.id.setting) {

            //sendingactivity
            DataManager.saveStringData("sendingactivity", "setting");


            startActivity(new Intent(Main2Activity.this, SettingsActivity.class));

        } else if (id == R.id.nav_share) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void addFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container_layout, new TrainingPlanFragment()).commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.weeklygoal_btn:
                startActivity(new Intent(getApplicationContext(), ChoseGoal.class));
                break;
            case R.id.fullbody_cardd1:
                DataManager.saveStringData("exerciseNameAD","fullbody1");
                //startFullBodyExList();
             startActivity(new Intent(getApplicationContext(),FourWeekGoalActivity.class));
                break;
            case R.id.lower_body:
                DataManager.saveStringData("exerciseNameAD","fullbody2");
                //startLowerBodyEx();
                startActivity(new Intent(getApplicationContext(),FourWeekGoalActivity.class));
                break;

            case R.id.absbeginner_layout:
                startAbsBegginerExercise();
                break;
            case R.id.intermediate_abs_layout:
                startAbsIntermediateExercise();
                break;

            case R.id.advance_abs_layout:
                startAbsAdvanceExercise();
                break;

            case R.id.chestbeginner_layout:
                startChestBegginer();
                break;
            case R.id.intermediate_chest_layout:

                startChestIntermediate();
                break;

            case R.id.advance_chest_layout:
                startChestAdvanced();
                break;

        }
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


    public void startAbsBegginerExercise() {
        Intent intent = new Intent(getApplicationContext(), ExercisesListGeneralActivity.class);
        intent.putExtra(Constants.intentKeyExcerciseType, getResources().getString(R.string.ABS_Begginer));
        startActivity(intent);
    }


    public void startAbsIntermediateExercise() {
        Intent intent = new Intent(getApplicationContext(), ExercisesListGeneralActivity.class);
        intent.putExtra(Constants.intentKeyExcerciseType, getResources().getString(R.string.ABSINTERMEDIATE));
        startActivity(intent);
    }

    public void startAbsAdvanceExercise() {
        Intent intent = new Intent(getApplicationContext(), ExercisesListGeneralActivity.class);
        intent.putExtra(Constants.intentKeyExcerciseType, getResources().getString(R.string.ABSADVANCED));
        startActivity(intent);
    }


    public void startChestBegginer() {
        Intent intent = new Intent(getApplicationContext(), ExercisesListGeneralActivity.class);
        intent.putExtra(Constants.intentKeyExcerciseType, getResources().getString(R.string.CHESTBEGINNER));
        startActivity(intent);
    }

    public void startChestIntermediate() {
        Intent intent = new Intent(getApplicationContext(), ExercisesListGeneralActivity.class);
        intent.putExtra(Constants.intentKeyExcerciseType, getResources().getString(R.string.CHESTINTERMEDIATE));
        startActivity(intent);
    }

    public void startChestAdvanced() {
        Intent intent = new Intent(getApplicationContext(), ExercisesListGeneralActivity.class);
        intent.putExtra(Constants.intentKeyExcerciseType, getResources().getString(R.string.CHESTADVANCED));
        startActivity(intent);
    }


    public void intWeeklyGoalLayouts() {

        if (DataManager.retrieveStringData("totalworkout_TME") != null) {
            if (!DataManager.retrieveStringData("totalworkout_TME").equals("")) {
                long ttl_tme = Long.parseLong(DataManager.retrieveStringData("totalworkout_TME"));
                if (ttl_tme > 0) {
                    binding.appBarLayout.contentMain.workoutlist.weeklyGoalStart.firstValuesLayout.setVisibility(View.VISIBLE);
                }
            }
        }


        binding.appBarLayout.contentMain.workoutlist.weeklyGoalStart.workoutDaysTv.setText(DataManager.retrieveStringData("goal_days") + "/7");
        //binding.appBarLayout.contentMain.workoutlist.weeklyGoalStart.workout_days_tv.setText(DataManager.retrieveStringData("goal_days")+"/7");

        binding.appBarLayout.contentMain.workoutlist.weeklyGoalStart.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), ChoseGoal.class);
                startActivityForResult(intent, Constants.WEEKLY_GOAL_ACTIVITY_RESULT_CODE);

            }
        });

        binding.appBarLayout.contentMain.workoutlist.weeklyGoalStart.weeklygoalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChoseGoal.class);
                startActivityForResult(intent, Constants.WEEKLY_GOAL_ACTIVITY_RESULT_CODE);
            }
        });

        if (DataManager.retrieveStringData("week_goal_layout").equals("show")) {
            binding.appBarLayout.contentMain.workoutlist.weeklyGoalStart.weekgoalLayout.setVisibility(View.VISIBLE);
            binding.appBarLayout.contentMain.workoutlist.weeklyGoalStart.setgoalLayout.setVisibility(View.GONE);
        } else {
            binding.appBarLayout.contentMain.workoutlist.weeklyGoalStart.weekgoalLayout.setVisibility(View.GONE);
            binding.appBarLayout.contentMain.workoutlist.weeklyGoalStart.setgoalLayout.setVisibility(View.VISIBLE);

            // binding.appBarLayout.contentMain.workoutlist.weeklyGoalStart.setgoalLayout.setVisibility();
        }


    }


    public void weekGoalDataSet() {
        View view = binding.getRoot();
        weeklyGoalDataSet2(view);

    }


    Button weeklyGoal_btn;
    LinearLayout weekgoal_layout, set_weekgoal_layout, edit_btn, report_layout;
    TextView workout_days_tv, weekGoal_day1_tv, weekGoal_day2_tv, weekGoal_day3_tv, weekGoal_day4_tv, weekGoal_day5_tv, weekGoal_day6_tv, weekGoal_day7_tv,
            workout_val_tv, total_time_tv, total_kal_tv;
    ImageView weekGoal_day1_iv, weekGoal_day2_iv, weekGoal_day3_iv, weekGoal_day4_iv, weekGoal_day5_iv, weekGoal_day6_iv, weekGoal_day7_iv;

    String kcal, time, workout;
    long cal, tme, wrkout;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;


    public void weeklyGoalDataSet2(View itemView) {

//            this.txtType = (TextView) itemView.findViewById(R.id.type);
//            this.fab = (FloatingActionButton) itemView.findViewById(R.id.fab);
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        weeklyGoal_btn = itemView.findViewById(R.id.weeklygoal_btn);
        weekgoal_layout = itemView.findViewById(R.id.weekgoal_layout);
        set_weekgoal_layout = itemView.findViewById(R.id.setgoal_layout);
        edit_btn = itemView.findViewById(R.id.edit_btn);
        workout_days_tv = itemView.findViewById(R.id.workout_days_tv);
        report_layout = itemView.findViewById(R.id.first_values_layout);
        weekGoal_day1_iv = itemView.findViewById(R.id.weekgoal_day1_iv);
        weekGoal_day2_iv = itemView.findViewById(R.id.weekgoal_day2_iv);
        weekGoal_day3_iv = itemView.findViewById(R.id.weekgoal_day3_iv);
        weekGoal_day4_iv = itemView.findViewById(R.id.weekgoal_day4_iv);
        weekGoal_day5_iv = itemView.findViewById(R.id.weekgoal_day5_iv);
        weekGoal_day6_iv = itemView.findViewById(R.id.weekgoal_day6_iv);
        weekGoal_day7_iv = itemView.findViewById(R.id.weekgoal_day7_iv);
        weekGoal_day1_tv = itemView.findViewById(R.id.weekgoal_day1_tv);
        weekGoal_day2_tv = itemView.findViewById(R.id.weekgoal_day2_tv);
        weekGoal_day3_tv = itemView.findViewById(R.id.weekgoal_day3_tv);
        weekGoal_day4_tv = itemView.findViewById(R.id.weekgoal_day4_tv);
        weekGoal_day5_tv = itemView.findViewById(R.id.weekgoal_day5_tv);
        weekGoal_day6_tv = itemView.findViewById(R.id.weekgoal_day6_tv);
        weekGoal_day7_tv = itemView.findViewById(R.id.weekgoal_day7_tv);
        total_time_tv = itemView.findViewById(R.id.workout_total_time_tv);
        total_kal_tv = itemView.findViewById(R.id.calvalue_tv);
        workout_val_tv = itemView.findViewById(R.id.workoutval_tv);

        weekGoal_day1_tv.setVisibility(View.VISIBLE);
        weekGoal_day2_tv.setVisibility(View.VISIBLE);
        weekGoal_day3_tv.setVisibility(View.VISIBLE);
        weekGoal_day4_tv.setVisibility(View.VISIBLE);
        weekGoal_day5_tv.setVisibility(View.VISIBLE);
        weekGoal_day6_tv.setVisibility(View.VISIBLE);
        weekGoal_day7_tv.setVisibility(View.VISIBLE);


        kcal = preferences.getString("grand_total_calories", "");
        workout = preferences.getString("grand_total_workouts", "");
        time = preferences.getString("grand_totalworkout_TME", "");

        if (preferences.getString("sat_exrcise", "").equals("1")) {

            if (preferences.getString("sat_goal_acheive", "").equals("1")) {
                weekGoal_day1_iv.setImageResource(R.drawable.ic_tickmy);
                weekGoal_day4_iv.setBackgroundResource(R.drawable.round_white);

                weekGoal_day1_tv.setVisibility(View.INVISIBLE);
            } else if (preferences.getString("sat_goal_acheive", "").equals("0")) {
                weekGoal_day1_iv.setImageResource(R.drawable.round_blue_stroke);
            }
            weekGoal_day1_tv.setTextColor(getResources().getColor(R.color.dark_blue));

        } else if (preferences.getString("sat_exrcise", "").equals("0")) {
            weekGoal_day1_tv.setTextColor(getResources().getColor(R.color.ligh_gray));
            weekGoal_day1_iv.setImageResource(R.drawable.round_layout_weekgoal);

        }

        if (preferences.getString("sun_exrcise", "").equals("1")) {

            if (preferences.getString("sun_goal_acheive", "").equals("1")) {
                weekGoal_day2_iv.setImageResource(R.drawable.ic_tickmy);
                weekGoal_day4_iv.setBackgroundResource(R.drawable.round_white);
                weekGoal_day2_tv.setVisibility(View.INVISIBLE);
            } else if (preferences.getString("sun_goal_acheive", "").equals("0")) {
                weekGoal_day2_iv.setImageResource(R.drawable.round_blue_stroke);
            }
            weekGoal_day2_tv.setTextColor(getResources().getColor(R.color.dark_blue));

        } else if (preferences.getString("sun_exrcise", "").equals("0")) {
            weekGoal_day2_tv.setTextColor(getResources().getColor(R.color.ligh_gray));
            weekGoal_day2_iv.setImageResource(R.drawable.round_layout_weekgoal);

        }


        if (preferences.getString("mon_exrcise", "").equals("1")) {

            if (preferences.getString("mon_goal_acheive", "").equals("1")) {
                weekGoal_day3_iv.setImageResource(R.drawable.ic_tickmy);
                weekGoal_day4_iv.setBackgroundResource(R.drawable.round_white);
                weekGoal_day3_tv.setVisibility(View.INVISIBLE);
            } else if (preferences.getString("mon_goal_acheive", "").equals("0")) {
                weekGoal_day3_iv.setImageResource(R.drawable.round_blue_stroke);
            }
            weekGoal_day3_tv.setTextColor(getResources().getColor(R.color.dark_blue));

        } else if (preferences.getString("mon_exrcise", "").equals("0")) {
            weekGoal_day3_tv.setTextColor(getResources().getColor(R.color.ligh_gray));
            weekGoal_day3_iv.setImageResource(R.drawable.round_layout_weekgoal);


        }

        if (preferences.getString("tue_exrcise", "").equals("1")) {

            if (preferences.getString("tue_goal_acheive", "").equals("1")) {
                weekGoal_day4_iv.setImageResource(R.drawable.ic_tickmy);
                weekGoal_day4_iv.setBackgroundResource(R.drawable.round_white);
                weekGoal_day4_tv.setVisibility(View.INVISIBLE);
            } else if (preferences.getString("tue_goal_acheive", "").equals("0")) {
                weekGoal_day4_iv.setImageResource(R.drawable.round_blue_stroke);
            }
            weekGoal_day4_tv.setTextColor(getResources().getColor(R.color.dark_blue));


        } else if (preferences.getString("tue_exrcise", "").equals("0")) {
            weekGoal_day4_tv.setTextColor(getResources().getColor(R.color.ligh_gray));
            weekGoal_day4_iv.setImageResource(R.drawable.round_layout_weekgoal);

        }

        if (preferences.getString("wed_exrcise", "").equals("1")) {

            if (preferences.getString("wed_goal_acheive", "").equals("1")) {
                weekGoal_day5_iv.setImageResource(R.drawable.ic_tickmy);
                weekGoal_day4_iv.setBackgroundResource(R.drawable.round_white);
                weekGoal_day5_tv.setVisibility(View.INVISIBLE);
                weekGoal_day5_iv.setBackgroundResource(R.drawable.round_white);
            } else if (preferences.getString("wed_goal_acheive", "").equals("0")) {
                weekGoal_day5_iv.setImageResource(R.drawable.round_blue_stroke);
            }
            weekGoal_day5_tv.setTextColor(getResources().getColor(R.color.dark_blue));


        } else if (preferences.getString("wed_exrcise", "").equals("0")) {
            weekGoal_day5_tv.setTextColor(getResources().getColor(R.color.ligh_gray));
            weekGoal_day5_iv.setImageResource(R.drawable.round_layout_weekgoal);


        }

        if (preferences.getString("thu_exrcise", "").equals("1")) {

            if (preferences.getString("thu_goal_acheive", "").equals("1")) {
                weekGoal_day6_iv.setImageResource(R.drawable.ic_tickmy);
                weekGoal_day4_iv.setBackgroundResource(R.drawable.round_white);
                weekGoal_day6_tv.setVisibility(View.INVISIBLE);
            } else if (preferences.getString("thu_goal_acheive", "").equals("0")) {
                weekGoal_day6_iv.setImageResource(R.drawable.round_blue_stroke);
            }
            weekGoal_day6_tv.setTextColor(getResources().getColor(R.color.dark_blue));

        } else if (preferences.getString("thu_exrcise", "").equals("0")) {
            weekGoal_day6_tv.setTextColor(getResources().getColor(R.color.ligh_gray));
            weekGoal_day6_iv.setImageResource(R.drawable.round_layout_weekgoal);


        }

        if (preferences.getString("fri_exrcise", "").equals("1")) {

            if (preferences.getString("fri_goal_acheive", "").equals("1")) {
                weekGoal_day7_iv.setImageResource(R.drawable.ic_tickmy);
                weekGoal_day4_iv.setBackgroundResource(R.drawable.round_white);
                weekGoal_day7_iv.setBackgroundResource(R.drawable.round_white);
                weekGoal_day7_tv.setVisibility(View.INVISIBLE);
            } else if (preferences.getString("fri_goal_acheive", "").equals("0")) {
                weekGoal_day7_iv.setImageResource(R.drawable.round_blue_stroke);
            }
            weekGoal_day7_tv.setTextColor(getResources().getColor(R.color.dark_blue));


        } else if (preferences.getString("fri_exrcise", "").equals("0")) {
            weekGoal_day7_tv.setTextColor(getResources().getColor(R.color.ligh_gray));
            weekGoal_day7_iv.setImageResource(R.drawable.round_layout_weekgoal);

        }

        workout_val_tv.setText(workout);
        total_kal_tv.setText(kcal);

        if (!TextUtils.isEmpty(kcal)) {
            cal = Long.parseLong(kcal);
        }

        if (!TextUtils.isEmpty(workout)) {
            wrkout = Long.parseLong(workout);

        }

        if (!TextUtils.isEmpty(time)) {
            tme = Long.parseLong(time);
        }

        if (cal > 0 || wrkout > 0 || tme > 0) {

            report_layout.setVisibility(View.VISIBLE);
        } else {
            report_layout.setVisibility(View.GONE);
        }

      /*  time=  String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(tme),
                TimeUnit.MILLISECONDS.toMinutes(tme) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(tme)), // The change is in this line
                TimeUnit.MILLISECONDS.toSeconds(tme) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(tme)));*/

        time = ExerciseTimeManager.getDurationBreakdown(tme);
        total_time_tv.setText(time);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == Constants.WEEKLY_GOAL_ACTIVITY_RESULT_CODE) {
                intWeeklyGoalLayouts();
                weekGoalDataSet();
            }
        }
    }


    CalendarFragment calendarFragment;

    private void initCalendarFragment(Bundle savedInstanceState) {
        calendarFragment = new CalendarFragment();


        Bundle args = new Bundle();
        Calendar cal = Calendar.getInstance();
        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
        args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true);
        args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, false);
        args.putInt(CalendarFragment.START_DAY_OF_WEEK, UserHelper.getFirstDayOfWeek(this));
        args.putBoolean(CalendarFragment.ENABLE_CLICK_ON_DISABLED_DATES, false);
        args.putInt(CaldroidFragment.THEME_RESOURCE, R.style.caldroid_style);

        calendarFragment.setArguments(args);
        calendarFragment.setSelectedDates(new Date(), new Date());

        Date minDate = new Date(Parameters.getInstance(this).getLong(ParameterKeys.INIT_DATE, new Date().getTime()));
        calendarFragment.setMinDate(minDate);


        final CaldroidListener listener = new CaldroidListener() {
            @Override
            public void onSelectDate(Date date, View view) {
              /*  selectedDaet = date;
                view.clearAnimation();
                AnimationUtility.zoomIn_OutAnimationOnce(MainActivity.this, view, null);
                refreshAllForDate(date);*/
            }

            @Override
            public void onLongClickDate(Date date, View view) // Add expense on long press
            {
               /* Intent startIntent = new Intent(MainActivity.this, ExpenseEditActivity.class);
                startIntent.putExtra("date", date.getTime());

                if (UIHelper.areAnimationsEnabled(MainActivity.this)) {
                    // Get the absolute location on window for Y value
                    int viewLocation[] = new int[2];
                    view.getLocationInWindow(viewLocation);

                    startIntent.putExtra(ANIMATE_TRANSITION_KEY, true);
                    startIntent.putExtra(CENTER_X_KEY, (int) view.getX() + view.getWidth() / 2);
                    startIntent.putExtra(CENTER_Y_KEY, viewLocation[1] + view.getHeight() / 2);
                }

                ActivityCompat.startActivityForResult(MainActivity.this, startIntent, ADD_EXPENSE_ACTIVITY_CODE, null);*/
            }

            @Override
            public void onChangeMonth(int month, int year) {
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.MONTH, month);
                cal.set(Calendar.YEAR, year);
                Constants.userSelectedMonth = cal.getTime();

//ad
                cal.set(Calendar.MONTH, month - 1);
                cal.set(Calendar.YEAR, year);
                Constants.userSelectedMonth = cal.getTime();
            }

            public void addImageView(LinearLayout view1) {
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(getResources().getDimensionPixelSize(R.dimen.image_width_month_report), getResources().getDimensionPixelSize(R.dimen.image_width_month_report));
                layoutParams.gravity = Gravity.CENTER;
                // layoutParams.topMargin=getResources().getDimensionPixelSize(R.dimen.activity_vertical_margin);
                layoutParams.rightMargin = getResources().getDimensionPixelSize(R.dimen.activity_vertical_margin);
                ImageView imageView = new ImageView(getApplicationContext());
                imageView.setImageResource(R.drawable.ic_tick);

                imageView.setAlpha(0.8f);
                imageView.setLayoutParams(layoutParams);

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /*Intent startIntent = new Intent(getApplicationContext(), .class);
                        ActivityCompat.startActivity(Main2Activity.this, startIntent, null);*/
                    }
                });
                view1.addView(imageView, layoutParams);

            }


            @Override
            public void onCaldroidViewCreated() {
                Button leftButton = calendarFragment.getLeftArrowButton();
                Button rightButton = calendarFragment.getRightArrowButton();
                TextView textView = calendarFragment.getMonthTitleTextView();
                GridView weekDayGreedView = calendarFragment.getWeekdayGridView();
                LinearLayout topLayout = (LinearLayout) Main2Activity.this.findViewById(com.caldroid.R.id.calendar_title_view);

                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) textView.getLayoutParams();
                params.gravity = Gravity.TOP;
                params.setMargins(0, 0, 0, Main2Activity.this.getResources().getDimensionPixelSize(R.dimen.calendar_month_text_padding_bottom));
                textView.setLayoutParams(params);

                topLayout.setPadding(0, Main2Activity.this.getResources().getDimensionPixelSize(R.dimen.calendar_month_padding_top), 0, Main2Activity.this.getResources().getDimensionPixelSize(R.dimen.calendar_month_padding_bottom));

                LinearLayout.LayoutParams leftButtonParams = (LinearLayout.LayoutParams) leftButton.getLayoutParams();
                leftButtonParams.setMargins(Main2Activity.this.getResources().getDimensionPixelSize(R.dimen.calendar_month_buttons_margin), 0, 0, 0);
                leftButton.setLayoutParams(leftButtonParams);

                LinearLayout.LayoutParams rightButtonParams = (LinearLayout.LayoutParams) rightButton.getLayoutParams();
                rightButtonParams.setMargins(0, 0, Main2Activity.this.getResources().getDimensionPixelSize(R.dimen.calendar_month_buttons_margin), 0);
                rightButton.setLayoutParams(rightButtonParams);

                textView.setTextColor(ContextCompat.getColor(Main2Activity.this, android.R.color.white));
                topLayout.setBackgroundColor(ContextCompat.getColor(Main2Activity.this, R.color.primary_dark));
                addImageView(topLayout);
                leftButton.setText("<");
                leftButton.setTextSize(25);
                leftButton.setGravity(Gravity.CENTER);
                leftButton.setTextColor(ContextCompat.getColor(Main2Activity.this, android.R.color.white));
                leftButton.setBackgroundResource(R.drawable.calendar_month_switcher_button_drawable);

                rightButton.setText(">");
                rightButton.setTextSize(25);
                rightButton.setGravity(Gravity.CENTER);
                rightButton.setTextColor(ContextCompat.getColor(Main2Activity.this, android.R.color.white));
                rightButton.setBackgroundResource(R.drawable.calendar_month_switcher_button_drawable);

                weekDayGreedView.setPadding(0, Main2Activity.this.getResources().getDimensionPixelSize(R.dimen.calendar_weekdays_padding_top), 0, Main2Activity.this.getResources().getDimensionPixelSize(R.dimen.calendar_weekdays_padding_bottom));

                // Remove border on lollipop
                UIHelper.removeButtonBorder(leftButton);
                UIHelper.removeButtonBorder(rightButton);
            }
        };


        calendarFragment.setCaldroidListener(listener);

        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.calendarView, calendarFragment);
        t.commit();


    }

}
