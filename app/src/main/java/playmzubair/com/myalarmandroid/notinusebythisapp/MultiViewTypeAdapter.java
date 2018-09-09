package playmzubair.com.myalarmandroid.notinusebythisapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.designerclicksol.myworkout.AppBase;
import com.example.designerclicksol.myworkout.R;
import com.example.designerclicksol.myworkout.Views.CustomSeekBar;
import com.example.designerclicksol.myworkout.activities.Main2Activity;
import com.example.designerclicksol.myworkout.activities.Metric_ImperialUnit_activity;
import com.example.designerclicksol.myworkout.activities.ReminderActivity;
import com.example.designerclicksol.myworkout.activities.ReportActivity;
import com.example.designerclicksol.myworkout.activities.RewardActivity;
import com.example.designerclicksol.myworkout.activities.SettingsActivity;
import com.example.designerclicksol.myworkout.managers.DataManager;
import com.example.designerclicksol.myworkout.managers.ToastManager;
import com.example.designerclicksol.myworkout.model.ModelMultiViews;
import com.example.designerclicksol.myworkout.model.ProgressItem;
import com.example.designerclicksol.myworkout.model.ViewModelForAdapter;
import com.example.designerclicksol.myworkout.model.WeightModel;
import com.example.designerclicksol.myworkout.utils.AminUtils;
import com.example.designerclicksol.myworkout.utils.ExerciseTimeManager;
import com.example.designerclicksol.myworkout.utils.Keep;
import com.google.gson.Gson;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarListener;
import devs.mulham.horizontalcalendar.HorizontalCalendarView;


public class MultiViewTypeAdapter extends RecyclerView.Adapter {

    private ArrayList<ViewModelForAdapter> dataSet;
    Context mContext;
    int total_types;
    private boolean fabStateVolume = false;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    public static Context cntxt;
    RewardActivity rewardActivity;

    Dialog dialog;
    int counter;
   ArrayList<WeightModel> weightModel_List;
   String generalDate;
    WeightModel weightModel;
    Date weightDate;
    ReportActivity reportActivity;

  SettingsActivity settingsActivity;


    public MultiViewTypeAdapter(ArrayList<ViewModelForAdapter> data, Context context) {
        this.dataSet = data;
        this.mContext = context;
        total_types = dataSet.size();
        preferences= PreferenceManager.getDefaultSharedPreferences(context);
        cntxt=context;
        weightModel_List=retrieveList("weightarraylist");

        if(retrieveData("sendingactivity").equals("reward")){

            rewardActivity= (RewardActivity) context;
        }else if(retrieveData("sendingactivity").equals("report")){

            reportActivity= (ReportActivity)context;
        }else if(retrieveData("sendingactivity").equals("setting")){

            settingsActivity= (SettingsActivity) context;
        }

    }




    public MultiViewTypeAdapter(ArrayList<ViewModelForAdapter> data, Context context, RewardActivity rewardActivity) {
        this.dataSet = data;
        this.mContext = context;
        total_types = dataSet.size();
        preferences= PreferenceManager.getDefaultSharedPreferences(context);
        cntxt=context;

        if(retrieveData("sendingactivity").equals("reward")){

           this.rewardActivity=rewardActivity;
        }else if(retrieveData("sendingactivity").equals("report")){

            reportActivity= (ReportActivity)context;
        }



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

    private String retrieveData(String key){

        return preferences.getString(key,"");
    }





    public static class firstRewardViewHolder extends RecyclerView.ViewHolder{

        TextView day1_tv,day2_tv,day3_tv,day4_tv,day5_tv,day6_tv,day7_tv,week_tv,day_count_tv;
        public firstRewardViewHolder(View itemView) {
            super(itemView);

            day1_tv=itemView.findViewById(R.id.day1_reward_tv);
            day2_tv=itemView.findViewById(R.id.day2_reward_tv);
            day3_tv=itemView.findViewById(R.id.day3_reward_tv);
            day4_tv=itemView.findViewById(R.id.day4_reward_tv);
            day5_tv=itemView.findViewById(R.id.day5_reward_tv);
            day6_tv=itemView.findViewById(R.id.day6_reward_tv);
            day7_tv=itemView.findViewById(R.id.day7_reward_tv);
            week_tv=itemView.findViewById(R.id.week_reward_tv);
            day_count_tv=itemView.findViewById(R.id.daycount_reward_tv);
        }
    }

    public static class secondRewardViewHolder extends RecyclerView.ViewHolder{

        private float totalSpan = 1650;
        private float redSpan = 300;
        private float blueSpan = 150;
        private float greenSpan = 500;
        private float limeSpan = 300;
        private float darkGreySpan=100;
        private float orangeSpan=300;

        TextView weight_val_tv,edit_tv,hide_tv,kg_tv,lb_tv,taphere_tv;
        TextView bmiTv;




        private ArrayList<ProgressItem> progressItemList;
        private ProgressItem mProgressItem;

        private CustomSeekBar seekbar;
        public secondRewardViewHolder(View itemView) {
            super(itemView);
            seekbar=itemView.findViewById(R.id.custom_seebar);
            initDataToSeekbar();
            weight_val_tv=itemView.findViewById(R.id.weight_val_tv);
            edit_tv=itemView.findViewById(R.id.edit_tv);
            hide_tv=itemView.findViewById(R.id.hide_tv);
            kg_tv=itemView.findViewById(R.id.kg_tv);
            lb_tv=itemView.findViewById(R.id.lb_tv);
            taphere_tv=itemView.findViewById(R.id.tap_here_to_input_weight_tv);
            bmiTv=itemView.findViewById(R.id.bmi);
        }




        private void initDataToSeekbar() {
            progressItemList = new ArrayList<ProgressItem>();

            // blue span
            mProgressItem = new ProgressItem();
            mProgressItem.progressItemPercentage = (darkGreySpan / totalSpan) * 100;
            mProgressItem.color = R.color.gryish;
            progressItemList.add(mProgressItem);
            // green span
            mProgressItem = new ProgressItem();
            mProgressItem.progressItemPercentage = (blueSpan / totalSpan) * 100;
            mProgressItem.color = R.color.bluish;
            progressItemList.add(mProgressItem);
            // yellow span
            mProgressItem = new ProgressItem();
            mProgressItem.progressItemPercentage = (greenSpan / totalSpan) * 100;
            mProgressItem.color = R.color.greenish;
            progressItemList.add(mProgressItem);
            // greyspan
            mProgressItem = new ProgressItem();
            mProgressItem.progressItemPercentage = (limeSpan / totalSpan) * 100;
            mProgressItem.color = R.color.limish;
            progressItemList.add(mProgressItem);

            mProgressItem = new ProgressItem();
            mProgressItem.progressItemPercentage = (orangeSpan / totalSpan) * 100;
            mProgressItem.color = R.color.orange;
            progressItemList.add(mProgressItem);

            mProgressItem = new ProgressItem();
            mProgressItem.progressItemPercentage = (redSpan / totalSpan) * 100;
            mProgressItem.color = R.color.redish;
            progressItemList.add(mProgressItem);

            seekbar.initData(progressItemList,cntxt);
            seekbar.invalidate();
        }
    }

    public static class thirdRewardViewHolder extends RecyclerView.ViewHolder{


        LinearLayout l1,l2,l3,l4,l5;

        Button feedback_btn;
        TextView feedbacktv;

        ImageView iv1,iv2,iv3,iv4,iv5;

        public thirdRewardViewHolder(View itemView) {
            super(itemView);
            l1=itemView.findViewById(R.id.l1_layout);
            l2=itemView.findViewById(R.id.l2_layout);
            l3=itemView.findViewById(R.id.l3_layout);
            l4=itemView.findViewById(R.id.l4_layout);
            l5=itemView.findViewById(R.id.l5_layout);

            iv1=(ImageView)itemView.findViewById(R.id.easy1_iv) ;
            iv2=(ImageView)itemView.findViewById(R.id.easy2_iv) ;
            iv3=(ImageView)itemView.findViewById(R.id.easy3_iv) ;
            iv4=(ImageView)itemView.findViewById(R.id.easy4_iv) ;
            iv5=(ImageView)itemView.findViewById(R.id.easy5_iv) ;



            feedback_btn=itemView.findViewById(R.id.feedback_btn);
            feedbacktv=itemView.findViewById(R.id.feedback_tv);


        }
    }



    public static class remiderCardViewHolder extends RecyclerView.ViewHolder{
        public remiderCardViewHolder(View itemView) {
            super(itemView);
        }
    }


    public static class firstReportCardViewHolder extends RecyclerView.ViewHolder{

        LinearLayout linear_layout_history_check;
        TextView more_btn_history_check,workout_val,kcal_val,time_val;
        SharedPreferences preferences;


        public firstReportCardViewHolder(View itemView) {
            super(itemView);
            linear_layout_history_check=itemView.findViewById(R.id.report_linearLayout);
            more_btn_history_check=itemView.findViewById(R.id.more_report_history_btn);
            workout_val=itemView.findViewById(R.id.workout_val_report);
            kcal_val=itemView.findViewById(R.id.kcal_val_report);
            time_val=itemView.findViewById(R.id.time_val_report);
            preferences= PreferenceManager.getDefaultSharedPreferences(cntxt);

            workout_val.setText(preferences.getString("grand_total_workouts",""));
            kcal_val.setText(preferences.getString("grand_total_calories",""));


            if(!TextUtils.isEmpty(preferences.getString("grand_totalworkout_TME",""))){

                long total_time= Long.parseLong(preferences.getString("grand_totalworkout_TME",""));
                /*String s=  String.format("%02d:%02d:%02d",
                        TimeUnit.MILLISECONDS.toHours(total_time),
                        TimeUnit.MILLISECONDS.toMinutes(total_time) -
                                TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(total_time)), // The change is in this line
                        TimeUnit.MILLISECONDS.toSeconds(total_time) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(total_time)));*/

                String s=ExerciseTimeManager.getDurationBreakdown(total_time);
                time_val.setText(s);
            }





        }
    }

    public static class secondReportCardViewHolder extends RecyclerView.ViewHolder{


        Calendar calendar;

        GraphView graphView;
        TextView add_tv;
        SharedPreferences preferences;
        int day=0;
        int indi=0;


        public secondReportCardViewHolder(View itemView) {
            super(itemView);
            preferences= PreferenceManager.getDefaultSharedPreferences(cntxt);
           //add
            graphView=itemView.findViewById(R.id.graphView);
            initGraph(graphView);
            add_tv=itemView.findViewById(R.id.add_tv);


        }


        public void initGraph(GraphView graph) {
            // first series is a line
            calendar= Calendar.getInstance();
            final ArrayList<WeightModel> list=retrieveList("weightarraylist");



            if(list!=null){

                if(list.size()>0){

                    try{

                        Collections.sort(list, new Comparator<WeightModel>() {
                            @Override
                            public int compare(WeightModel o1, WeightModel o2) {
                                return Long.valueOf(o1.getWeightDate().getTime()).compareTo(o2.getWeightDate().getTime());
                            }
                        });

                        DataPoint[] points = new DataPoint[list.size()];
                        for (int i = 0; i < points.length; i++) {

                            points[i] = new DataPoint(list.get(i).getWeightDate().getTime(), list.get(i).getWeight());
                        }
                        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(points);

                        graph.addSeries(series);

                        long min=list.get(0).getWeightDate().getTime();

                        for(int i=0;i<list.size();i++){
                            if(list.get(i).getWeightDate().getTime()<min){
                                min=list.get(i).getWeightDate().getTime();
                            }
                        }

                        long max=list.get(0).getWeightDate().getTime();

                        for(int i=0;i<list.size();i++){
                            if(list.get(i).getWeightDate().getTime()>max){
                                max=list.get(i).getWeightDate().getTime();
                            }
                        }

                        series.setDrawDataPoints(true);
                        series.setDataPointsRadius(10);
                        series.setThickness(5);
                        series.setDrawBackground(false);
                        series.setTitle("weight line");
                        series.setDrawAsPath(true);

                        graph.getGridLabelRenderer().setHorizontalAxisTitleTextSize(cntxt.getResources().getDimension(R.dimen._30sdp));
                        graph.getGridLabelRenderer().setHorizontalLabelsAngle(110);
                        graph.getGridLabelRenderer().setPadding((int)cntxt.getResources().getDimension(R.dimen._20sdp));
                        //  graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(graph.getContext()));

                        DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
                        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(graph.getContext(),df ));


                        graph.getViewport().setBackgroundColor(Color.WHITE);
                        graph.getGridLabelRenderer().setGridColor(Color.LTGRAY);
                        graph.getGridLabelRenderer().setLabelsSpace(10);
                        graph.getViewport().setDrawBorder(true);

                        graph.getGridLabelRenderer().setLabelHorizontalHeight((int)cntxt.getResources().getDimension(R.dimen._20sdp));

                        //  graph.getGridLabelRenderer().setNumHorizontalLabels(4);
                        // set manual X bounds
//                    graph.getViewport().setYAxisBoundsManual(true);
//                    graph.getViewport().setMinY(list.get(0).getWeight());
//                    graph.getViewport().setMaxY(list.get(list.size()-1).getWeight());

                        //   graph.getGridLabelRenderer().setNumHorizontalLabels(3);
                        graph.getGridLabelRenderer().setVerticalAxisTitleTextSize(cntxt.getResources().getDimension(R.dimen._25sdp));
                        if(preferences.getString("default_weight_unit","").equals("lb")){
                            graph.getGridLabelRenderer().setVerticalAxisTitle("lbs");
                        }else
                        {
                            graph.getGridLabelRenderer().setVerticalAxisTitle("kg");

                        }

                        graph.getViewport().setXAxisBoundsManual(true);
                        graph.getViewport().setMinX(list.get(0).getWeightDate().getTime());
                        graph.getViewport().setMaxX(list.get(list.size()-1).getWeightDate().getTime());

                        // enable scaling
                        graph.getViewport().setScalable(true);
                        // graph.getViewport().setScrollable(true);
//                        graph.getViewport().setScrollableY(true);

                        series.setTitle("Random Curve");
                        graph.getViewport().setScalable(true);



                        graph.getLegendRenderer().setVisible(false);
                        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
                        graph.getGridLabelRenderer().setHumanRounding(false);

                    }catch (Exception ex){
                        ex.printStackTrace();
                        graph.setVisibility(View.GONE);
                    }
                } else {
                    graph.setVisibility(View.GONE);
                }


            }
            else {
                graph.setVisibility(View.GONE);
            }

        }


        private ArrayList<WeightModel> retrieveList(String key){


            List<WeightModel> weightList;
            if(preferences.contains(key)){

                try
                {
                    String json_weights=preferences.getString(key,"");
                    Gson gson=new Gson();
                    WeightModel[] weight_array=gson.fromJson(json_weights,WeightModel[].class);
                    weightList= Arrays.asList(weight_array);
                    return new ArrayList<WeightModel>(weightList);

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

    public static class thirdReportCardViewHolder extends RecyclerView.ViewHolder{

        private float totalSpan = 1650;
        private float redSpan = 300;
        private float blueSpan = 150;
        private float greenSpan = 500;
        private float limeSpan = 300;
        private float darkGreySpan=100;
        private float orangeSpan=300;
        SharedPreferences preferences;
        TextView edit_1,edit_2,height_val_tv,tap_here_tv_report;


        private ArrayList<ProgressItem> progressItemList;
        private ProgressItem mProgressItem;

        private CustomSeekBar seekbar;
        public thirdReportCardViewHolder(View itemView) {
            super(itemView);
            preferences= PreferenceManager.getDefaultSharedPreferences(cntxt);

            seekbar=itemView.findViewById(R.id.bmi_report_seekbar);

            initDataToSeekbar();

            seekbar.setClickable(false);
            seekbar.setFocusable(false);
            seekbar.setEnabled(false);


//            try {
//                seekbar.setProgress(Integer.parseInt(preferences.getString("bmi","")));
//            }catch (Exception ex){
//                ex.printStackTrace();
//            }
//

            edit_1=itemView.findViewById(R.id.edit_report_tv1);
            edit_2=itemView.findViewById(R.id.edit_report_tv2);
            height_val_tv=itemView.findViewById(R.id.height_val_report_tv);
            tap_here_tv_report=itemView.findViewById(R.id.tap_here_to_input_weight_report_tv);
        }


        private void initDataToSeekbar() {
            progressItemList = new ArrayList<ProgressItem>();

            // blue span
            mProgressItem = new ProgressItem();
            mProgressItem.progressItemPercentage = (darkGreySpan / totalSpan) * 100;
            mProgressItem.color = R.color.gryish;
            progressItemList.add(mProgressItem);
            // green span
            mProgressItem = new ProgressItem();
            mProgressItem.progressItemPercentage = (blueSpan / totalSpan) * 100;
            mProgressItem.color = R.color.bluish;
            progressItemList.add(mProgressItem);
            // yellow span
            mProgressItem = new ProgressItem();
            mProgressItem.progressItemPercentage = (greenSpan / totalSpan) * 100;
            mProgressItem.color = R.color.greenish;
            progressItemList.add(mProgressItem);
            // greyspan
            mProgressItem = new ProgressItem();
            mProgressItem.progressItemPercentage = (limeSpan / totalSpan) * 100;
            mProgressItem.color = R.color.limish;
            progressItemList.add(mProgressItem);

            mProgressItem = new ProgressItem();
            mProgressItem.progressItemPercentage = (orangeSpan / totalSpan) * 100;
            mProgressItem.color = R.color.orange;
            progressItemList.add(mProgressItem);

            mProgressItem = new ProgressItem();
            mProgressItem.progressItemPercentage = (redSpan / totalSpan) * 100;
            mProgressItem.color = R.color.redish;
            progressItemList.add(mProgressItem);

            seekbar.initData(progressItemList,cntxt);
            seekbar.invalidate();
        }
    }


    public  static class dayViewHolder extends RecyclerView.ViewHolder{

        public dayViewHolder(View itemView) {
            super(itemView);
        }
    }


    public  static class historyViewHolder extends RecyclerView.ViewHolder{

        public historyViewHolder(View itemView) {
            super(itemView);
        }
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        switch (viewType) {

            case ViewModelForAdapter.FIRST_REWARD_CARDVIEW:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reward_cardlayout_1, parent, false);
                return new firstRewardViewHolder(view);
            case ViewModelForAdapter.SECOND_REWARD_CARDVIEW:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reward_cardlayout_2, parent, false);
                return new secondRewardViewHolder(view);
            case ViewModelForAdapter.THIRD_REWARD_CARDVIEW:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reward_cardlayout_3, parent, false);
                return new thirdRewardViewHolder(view);


            case ViewModelForAdapter.FIRST_REPORT_CARDVIEW:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.report_cardlayout_1, parent, false);
                return new firstReportCardViewHolder(view);

            case ViewModelForAdapter.SECOND_REPORT_CARVIEW:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.report_cardlayout_2, parent, false);
                return new secondReportCardViewHolder(view);

            case ViewModelForAdapter.THIRD_REPORT_CARVIEW:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.report_cardlayout_3, parent, false);
                return new thirdReportCardViewHolder(view);

            case ViewModelForAdapter.SETTINGS_CARDVIEW:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.settings_cardlayout, parent, false);
                return new settingsCardViewHolder(view);

            case ViewModelForAdapter.REMINDER_CARVIEW:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reminder_cardlayout, parent, false);
                return new remiderCardViewHolder(view);

        }
        return null;
    }


    public static class settingsCardViewHolder extends RecyclerView.ViewHolder{

        TextView rest_settings_tv,restset_sub_tv,countdown_settings_tv,countdown_settings_sub_tv,health_data_tv,
                remindMe_tv,MetricImperial_tv;
        SwitchCompat keepScreenON,soundSwitch;
        LinearLayout shareUslayout,rateUslayout,feedbackLayout,privacyPOlicylayout,
                restset_layout,countdownlayout,healthdatalayout,reminderlayout,metricimperial_layout;



        public settingsCardViewHolder(View itemView) {
            super(itemView);

            rest_settings_tv=itemView.findViewById(R.id.rest_settings_tv);
            restset_sub_tv=itemView.findViewById(R.id.restset_sub_tv);
            countdown_settings_tv=itemView.findViewById(R.id.countdown_settings_tv);
            countdown_settings_sub_tv=itemView.findViewById(R.id.countdown_settings_sub_tv);
            health_data_tv=itemView.findViewById(R.id.health_data_settings_tv);
            remindMe_tv=itemView.findViewById(R.id.remindMe_tv);
            MetricImperial_tv=itemView.findViewById(R.id.metricImperial_tv);
            keepScreenON=itemView.findViewById(R.id.keep_screenON_switch);
            soundSwitch=itemView.findViewById(R.id.sound_switch);
            shareUslayout=itemView.findViewById(R.id.shareApp_layout);
            rateUslayout=itemView.findViewById(R.id.rateUs_layout);
            privacyPOlicylayout=itemView.findViewById(R.id.privacyPolicy_layout);
            feedbackLayout=itemView.findViewById(R.id.feedback_layout);
            restset_layout=itemView.findViewById(R.id.restset_layout);
            countdownlayout=itemView.findViewById(R.id.countdown_layout);
            healthdatalayout=itemView.findViewById(R.id.healthData_layout);
            metricimperial_layout=itemView.findViewById(R.id.metricImperial_layout);
            reminderlayout=itemView.findViewById(R.id.reminder_layout);

        }
    }









    @Override
    public int getItemViewType(int position) {

        switch (dataSet.get(position).type) {
            case 0:
                return ViewModelForAdapter.REPORT_TYPE;
            case 1:
                return ViewModelForAdapter.FULLBODY_TYPE;
            case 2:
                return ViewModelForAdapter.CHEST_TYPE;
            case 3:
                return ViewModelForAdapter.ABS_TYPE;
            case 4:
                return ViewModelForAdapter.SHOULDERBACK_TYPE;
            case 5:
                return ViewModelForAdapter.FIRST_REWARD_CARDVIEW;
            case 6:
                return ViewModelForAdapter.SECOND_REWARD_CARDVIEW;
            case 7:
                return ViewModelForAdapter.THIRD_REWARD_CARDVIEW;
            case 8:
                return ViewModelForAdapter.SETTINGS_CARDVIEW;
            case 9:
                return ViewModelForAdapter.REMINDER_CARVIEW;
            case 10:
                return ViewModelForAdapter.FIRST_REPORT_CARDVIEW;
            case 11:
                return ViewModelForAdapter.SECOND_REPORT_CARVIEW;
            case 12:
                return ViewModelForAdapter.THIRD_REPORT_CARVIEW;
            case 13:
                return ViewModelForAdapter.DAYS_CARVIEW;
            case 14:
                return ViewModelForAdapter.HISTORY_CARVIEW;
            default:
                return -1;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int listPosition) {

        ViewModelForAdapter object = dataSet.get(listPosition);
        if (object != null) {
            switch (object.type) {

                case ModelMultiViews.FIRST_REPORT_CARDVIEW:
                    try{
                        ((firstReportCardViewHolder)holder).more_btn_history_check.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                //mContext.startActivity(new Intent(mContext, HistoryActivity.class));
                            }
                        });

                        ((firstReportCardViewHolder)holder).linear_layout_history_check.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                              //  mContext.startActivity(new Intent(mContext, HistoryActivity.class));

                            }
                        });

                    }catch (Exception ex){
                        ex.printStackTrace();
                    }

                    break;

                case ModelMultiViews.SECOND_REPORT_CARVIEW:
                    try{
                        ((secondReportCardViewHolder)holder).add_tv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showhorizontalCalenarDialog();
                            }
                        });


//                        ((secondReportCardViewHolder)holder).graphView.setEnabled(true);
//                        ((secondReportCardViewHolder)holder).graphView.setScrollContainer(true);



                    }catch (Exception ex){
                        ex.printStackTrace();
                    }

                    break;

                case ModelMultiViews.THIRD_REPORT_CARVIEW:

                    try{
                        double prgrs= Double.parseDouble(retrieveData("bmi"));

                        ((thirdReportCardViewHolder)holder).seekbar.setProgress((int)prgrs);
                        String weight=retrieveData("weight");
                        String height=retrieveData("height");
                        float h=0f;
                        float w=0f;

                        if(!TextUtils.isEmpty(weight)){
                            h= Float.parseFloat(height);
                        }

                        if(!TextUtils.isEmpty(height)){
                            w= Float.parseFloat(weight);
                        }



                        if(h>0 && w>0){
                            ((thirdReportCardViewHolder)holder).seekbar.setVisibility(View.VISIBLE);
                            ((thirdReportCardViewHolder)holder).tap_here_tv_report.setVisibility(View.GONE);
                        }else {

                            ((thirdReportCardViewHolder)holder).seekbar.setVisibility(View.GONE);
                            ((thirdReportCardViewHolder)holder).tap_here_tv_report.setVisibility(View.VISIBLE);
                        }


                        if(!TextUtils.isEmpty(retrieveData("height"))){

                            double heg= Double.parseDouble(retrieveData("height"));

                            String s= String.format("%.2f", heg);
                            ((thirdReportCardViewHolder)holder).height_val_tv.setText(s+" "+retrieveData("default_height_unit"));

                        }else {
                            ((thirdReportCardViewHolder)holder).height_val_tv.setText(retrieveData("height")+" "+retrieveData("default_height_unit"));

                        }

                        ((thirdReportCardViewHolder)holder).edit_1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                saveData("show","height");
                                showWeightHeightDialog(((thirdReportCardViewHolder)holder).height_val_tv);

                            }
                        });


                        ((thirdReportCardViewHolder)holder).edit_2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                saveData("show","height");
                                showWeightHeightDialog(((thirdReportCardViewHolder)holder).height_val_tv);

                            }
                        });



                        ((thirdReportCardViewHolder)holder).tap_here_tv_report.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                saveData("show","height");
                                showWeightHeightDialog(((thirdReportCardViewHolder)holder).height_val_tv);
                            }
                        });

                    }catch (Exception ex){
                        ex.printStackTrace();
                    }


                    break;
                case ModelMultiViews.FIRST_REWARD_CARDVIEW:
                    ((firstRewardViewHolder)holder).day_count_tv.setText(1+"/"+7);
                    ((firstRewardViewHolder)holder).week_tv.setText("WEEK 1");

                    break;
                case ModelMultiViews.SECOND_REWARD_CARDVIEW:



                    String we=retrieveData("weight");
                    String he=retrieveData("height");

                    AminUtils.zoomInOut(rewardActivity, ((secondRewardViewHolder)holder).taphere_tv);
                    ((secondRewardViewHolder)holder).bmiTv.setText((Build.VERSION.SDK_INT<= Build.VERSION_CODES.M)? Html.fromHtml("BMI(kg/m<sup>2</sup>)"): Html.fromHtml("BMI(kg/m<sup>2</sup>)", Html.FROM_HTML_MODE_COMPACT)) ;

                    if(!TextUtils.isEmpty(we) &&! TextUtils.isEmpty(he)){

                        float wght= Float.parseFloat(we);
                        float hght= Float.parseFloat(he);






                        if(wght>0 && hght>0) {

                            ((secondRewardViewHolder)holder).taphere_tv.setVisibility(View.GONE);
                            ((secondRewardViewHolder)holder).seekbar.setVisibility(View.VISIBLE);
                            ((secondRewardViewHolder)holder).seekbar.setEnabled(false);
                            if(!TextUtils.isEmpty(retrieveData("bmi"))){
                                double bmi= Double.parseDouble(retrieveData("bmi"));
                                ((secondRewardViewHolder)holder).seekbar.setProgress((int)bmi);
                            }
                        }

                        }

                    ((secondRewardViewHolder)holder).weight_val_tv.setText(retrieveData("weight"));

                    ((secondRewardViewHolder)holder).taphere_tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                       //     showWeightHeightDialog(((secondRewardViewHolder)holder).weight_val_tv);
                           // saveData("show","weight");
                            DataManager.saveStringData("show","weight");
                            showWeightHeightDialog(((secondRewardViewHolder)holder).weight_val_tv);
//                            AlertDialog.Builder dlg=new AlertDialog.Builder(mContext);
//                            dlg.setTitle("dlg");
//                                    dlg.show();

                        }
                    });


                    ((secondRewardViewHolder)holder).edit_tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            saveData("show","weight");
                            showWeightHeightDialog(((secondRewardViewHolder)holder).weight_val_tv);
                        }
                    });

                    ((secondRewardViewHolder)holder).hide_tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            Toast.makeText(mContext,"clikd",Toast.LENGTH_SHORT).show();
                            if(retrieveData("bmishow").equals("show")){
                                saveData("bmishow","hide");
                                ((secondRewardViewHolder)holder).seekbar.setVisibility(View.GONE);
                                ((secondRewardViewHolder)holder).hide_tv.setText("show");


                            }else if(retrieveData("bmishow").equals("hide")){
                                saveData("bmishow","show");
                                ((secondRewardViewHolder)holder).seekbar.setVisibility(View.VISIBLE);
                                ((secondRewardViewHolder)holder).hide_tv.setText("Hide");

                            }
                        }
                    });

                    if(retrieveData("default_weight_unit").equals("kg")){

                        ((secondRewardViewHolder)holder).kg_tv.setBackgroundResource(R.drawable.squrare_tv_bg);
                        ((secondRewardViewHolder)holder).lb_tv.setBackgroundResource(R.drawable.square_tv_bg_simple);

                        ((secondRewardViewHolder)holder).kg_tv.setTextColor(Color.WHITE);
                        ((secondRewardViewHolder)holder).lb_tv.setTextColor(Color.LTGRAY);


                    }else  if(retrieveData("default_weight_unit").equals("lb")){
                        ((secondRewardViewHolder)holder).lb_tv.setBackgroundResource(R.drawable.squrare_tv_bg);
                        ((secondRewardViewHolder)holder).kg_tv.setBackgroundResource(R.drawable.square_tv_bg_simple);

                        ((secondRewardViewHolder)holder).kg_tv.setTextColor(Color.LTGRAY);
                        ((secondRewardViewHolder)holder).lb_tv.setTextColor(Color.WHITE);

                    }


                    ((secondRewardViewHolder)holder).kg_tv.setOnClickListener((View v)->{
                        saveData("default_weight_unit","kg");
                        ((secondRewardViewHolder)holder).kg_tv.setBackgroundResource(R.drawable.squrare_tv_bg);
                        ((secondRewardViewHolder)holder).lb_tv.setBackgroundResource(R.drawable.square_tv_bg_simple);
                        if(!TextUtils.isEmpty(((secondRewardViewHolder)holder).weight_val_tv.getText().toString())){
                            ((secondRewardViewHolder)holder).weight_val_tv.setText(lbToKg(Double.parseDouble(((secondRewardViewHolder)holder).weight_val_tv.getText().toString()))+"");
                        }
                        changeWeightUnit(retrieveData("default_weight_unit"));

                        ((secondRewardViewHolder)holder).kg_tv.setTextColor(Color.WHITE);
                        ((secondRewardViewHolder)holder).lb_tv.setTextColor(Color.LTGRAY);

                    });

                    ((secondRewardViewHolder)holder).lb_tv.setOnClickListener((View v)->{
                        saveData("default_weight_unit","lb");
                        ((secondRewardViewHolder)holder).lb_tv.setBackgroundResource(R.drawable.squrare_tv_bg);
                        ((secondRewardViewHolder)holder).kg_tv.setBackgroundResource(R.drawable.square_tv_bg_simple);
                        if(!TextUtils.isEmpty(((secondRewardViewHolder)holder).weight_val_tv.getText().toString())){
                            ((secondRewardViewHolder)holder).weight_val_tv.setText(kgToLb(Double.parseDouble(((secondRewardViewHolder)holder).weight_val_tv.getText().toString()))+"");
                        }
                        changeWeightUnit(retrieveData("default_weight_unit"));
                        ((secondRewardViewHolder)holder).kg_tv.setTextColor(Color.LTGRAY);
                        ((secondRewardViewHolder)holder).lb_tv.setTextColor(Color.WHITE);
                    });



                    break;
                case ModelMultiViews.THIRD_REWARD_CARDVIEW:

                    ((thirdRewardViewHolder)holder).l1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            saveData("feel","hatered");
                          // ToastManager.showToast(rewardActivity,retrieveData("feel"));
                            ToastManager.showSnackBarToast(rewardActivity,retrieveData("feel"),((thirdRewardViewHolder)holder).l1);
                            ((thirdRewardViewHolder)holder).l1.setBackgroundResource(R.drawable.round_dot_feeedback);
                            ((thirdRewardViewHolder)holder).l2.setBackgroundResource(R.drawable.roundlayout_simple);
                            ((thirdRewardViewHolder)holder).l3.setBackgroundResource(R.drawable.roundlayout_simple);
                            ((thirdRewardViewHolder)holder).l4.setBackgroundResource(R.drawable.roundlayout_simple);
                            ((thirdRewardViewHolder)holder).l5.setBackgroundResource(R.drawable.roundlayout_simple);



                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    ((thirdRewardViewHolder)holder).iv1.setImageResource(R.drawable.ic_check_mark);
                                    ((thirdRewardViewHolder)holder).iv2.setImageResource(0);
                                    ((thirdRewardViewHolder)holder).iv3.setImageResource(0);
                                    ((thirdRewardViewHolder)holder).iv4.setImageResource(0);
                                    ((thirdRewardViewHolder)holder).iv5.setImageResource(0);

                                    AminUtils.zoomInCheckBox(rewardActivity,((thirdRewardViewHolder)holder).l1,null);

                                }
                            },10);


                        }
                    });


                    ((thirdRewardViewHolder)holder).l2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            saveData("feel","Helpful");
                            ToastManager.showSnackBarToast(rewardActivity,retrieveData("feel"),((thirdRewardViewHolder)holder).l1);
                            ((thirdRewardViewHolder)holder).l1.setBackgroundResource(R.drawable.roundlayout_simple);
                            ((thirdRewardViewHolder)holder).l2.setBackgroundResource(R.drawable.round_dot_feeedback);
                            ((thirdRewardViewHolder)holder).l3.setBackgroundResource(R.drawable.roundlayout_simple);
                            ((thirdRewardViewHolder)holder).l4.setBackgroundResource(R.drawable.roundlayout_simple);
                            ((thirdRewardViewHolder)holder).l5.setBackgroundResource(R.drawable.roundlayout_simple);



                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    ((thirdRewardViewHolder)holder).iv1.setImageResource(0);
                                    ((thirdRewardViewHolder)holder).iv2.setImageResource(R.drawable.ic_check_mark);
                                    ((thirdRewardViewHolder)holder).iv3.setImageResource(0);
                                    ((thirdRewardViewHolder)holder).iv4.setImageResource(0);
                                    ((thirdRewardViewHolder)holder).iv5.setImageResource(0);
                                    AminUtils.zoomInCheckBox(rewardActivity,((thirdRewardViewHolder)holder).l2,null);


                                }
                            },10);
                        }
                    });

                    ((thirdRewardViewHolder)holder).l3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            saveData("feel","Fair");
                            //ToastManager.showToast(rewardActivity,retrieveData("feel"));
                            ToastManager.showSnackBarToast(rewardActivity,retrieveData("feel"),((thirdRewardViewHolder)holder).l1);
                            ((thirdRewardViewHolder)holder).l1.setBackgroundResource(R.drawable.roundlayout_simple);
                            ((thirdRewardViewHolder)holder).l2.setBackgroundResource(R.drawable.roundlayout_simple);
                            ((thirdRewardViewHolder)holder).l3.setBackgroundResource(R.drawable.round_dot_feeedback);
                            ((thirdRewardViewHolder)holder).l4.setBackgroundResource(R.drawable.roundlayout_simple);
                            ((thirdRewardViewHolder)holder).l5.setBackgroundResource(R.drawable.roundlayout_simple);


                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    ((thirdRewardViewHolder)holder).iv1.setImageResource(0);
                                    ((thirdRewardViewHolder)holder).iv2.setImageResource(0);
                                    ((thirdRewardViewHolder)holder).iv3.setImageResource(R.drawable.ic_check_mark);
                                    ((thirdRewardViewHolder)holder).iv4.setImageResource(0);
                                    ((thirdRewardViewHolder)holder).iv5.setImageResource(0);
                                    AminUtils.zoomInCheckBox(rewardActivity,((thirdRewardViewHolder)holder).l3,null);


                                }
                            },10);

                        }
                    });


                    ((thirdRewardViewHolder)holder).l4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            saveData("feel","Like");
                           // ToastManager.showToast(rewardActivity,retrieveData("feel"));
                            ToastManager.showSnackBarToast(rewardActivity,retrieveData("feel"),((thirdRewardViewHolder)holder).l1);
                            ((thirdRewardViewHolder)holder).l1.setBackgroundResource(R.drawable.roundlayout_simple);
                            ((thirdRewardViewHolder)holder).l2.setBackgroundResource(R.drawable.roundlayout_simple);
                            ((thirdRewardViewHolder)holder).l3.setBackgroundResource(R.drawable.roundlayout_simple);
                            ((thirdRewardViewHolder)holder).l4.setBackgroundResource(R.drawable.round_dot_feeedback);
                            ((thirdRewardViewHolder)holder).l5.setBackgroundResource(R.drawable.roundlayout_simple);


                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    ((thirdRewardViewHolder)holder).iv1.setImageResource(0);
                                    ((thirdRewardViewHolder)holder).iv2.setImageResource(0);
                                    ((thirdRewardViewHolder)holder).iv3.setImageResource(0);
                                    ((thirdRewardViewHolder)holder).iv4.setImageResource(R.drawable.ic_check_mark);
                                    ((thirdRewardViewHolder)holder).iv5.setImageResource(0);


                                    AminUtils.zoomInCheckBox(rewardActivity,((thirdRewardViewHolder)holder).l4,null);


                                }
                            },10);


                        }
                    });


                    ((thirdRewardViewHolder)holder).l5.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            saveData("feel","Extremely like");
                           // ToastManager.showToast(rewardActivity,retrieveData("feel"));
                            ToastManager.showSnackBarToast(rewardActivity,retrieveData("feel"),((thirdRewardViewHolder)holder).l1);
                            ((thirdRewardViewHolder)holder).l1.setBackgroundResource(R.drawable.roundlayout_simple);
                            ((thirdRewardViewHolder)holder).l2.setBackgroundResource(R.drawable.roundlayout_simple);
                            ((thirdRewardViewHolder)holder).l3.setBackgroundResource(R.drawable.roundlayout_simple);
                            ((thirdRewardViewHolder)holder).l4.setBackgroundResource(R.drawable.roundlayout_simple);
                            ((thirdRewardViewHolder)holder).l5.setBackgroundResource(R.drawable.round_dot_feeedback);




                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    ((thirdRewardViewHolder)holder).iv1.setImageResource(0);
                                    ((thirdRewardViewHolder)holder).iv2.setImageResource(0);
                                    ((thirdRewardViewHolder)holder).iv3.setImageResource(0);
                                    ((thirdRewardViewHolder)holder).iv4.setImageResource(0);
                                    ((thirdRewardViewHolder)holder).iv5.setImageResource(R.drawable.ic_check_mark);


                                    AminUtils.zoomInCheckBox(rewardActivity,((thirdRewardViewHolder)holder).l5,null);



                                }
                            },10);
                        }
                    });

                    ((thirdRewardViewHolder)holder).feedback_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            rewardActivity.startReportActivity();
                        }
                    });


                    ((thirdRewardViewHolder)holder).feedbacktv.setOnClickListener((View v)->{
                        rewardActivity.openGmail(rewardActivity,new String[]{"clicksol.apps@gmail.com"},"Feedback","Feel: "+retrieveData("feel")+"\n"+"Please write your valuable feedback here..");

                    });




                    break;

                case ModelMultiViews.SETTINGS_CARDVIEW:

                    try {


                        ((settingsCardViewHolder)holder).countdown_settings_sub_tv.setText(retrieveData("countdown_time")+" sec");
                        ((settingsCardViewHolder)holder).restset_sub_tv.setText(retrieveData("break_time")+" sec");

                        ((settingsCardViewHolder)holder).restset_layout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showRestDialog(((settingsCardViewHolder)holder).restset_sub_tv,"other");
                            }
                        });


                        ((settingsCardViewHolder)holder).countdownlayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showRestDialog(((settingsCardViewHolder)holder).countdown_settings_sub_tv,"countdown");
                            }
                        });


                        ((settingsCardViewHolder)holder).healthdatalayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showHealthDataDialog();
                            }
                        });


                        ((settingsCardViewHolder)holder).reminderlayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                settingsActivity.startActivity(new Intent(settingsActivity, ReminderActivity.class));
                            }
                        });

                        ((settingsCardViewHolder)holder).metricimperial_layout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                settingsActivity.startActivity(new Intent(settingsActivity, Metric_ImperialUnit_activity.class));
                            }
                        });

                        if(retrieveData("wakelock").equals("y")){
                            ((settingsCardViewHolder)holder).keepScreenON.setChecked(true);
                        }else {
                            ((settingsCardViewHolder)holder).keepScreenON.setChecked(false);
                        }
                        ((settingsCardViewHolder)holder).keepScreenON.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if(isChecked){

                                    saveData("wakelock","y");
                                    Keep.keepScreenOn(settingsActivity);
                                }else {
                                    saveData("wakelock","n");

                                    Keep.offScreen(settingsActivity);
                                }
                            }
                        });



                        if(retrieveData("sound").equals("on")){
                            ((settingsCardViewHolder)holder).soundSwitch.setChecked(true);
                        }else {
                            ((settingsCardViewHolder)holder).soundSwitch.setChecked(false);
                        }
                        ((settingsCardViewHolder)holder).soundSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if(isChecked){

                                    saveData("sound","on");
                                }else {
                                    saveData("sound","off");
                                }
                            }
                        });

                        ((settingsCardViewHolder)holder).rateUslayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                settingsActivity.rateUs();
                            }
                        });


                        ((settingsCardViewHolder)holder).shareUslayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                settingsActivity.shareApplink();
                            }
                        });


                        ((settingsCardViewHolder)holder).feedbackLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                SettingsActivity.openGmail(settingsActivity,new String[]{"clicksol@gmail.com"},"Feedback","Feel: "+retrieveData("feel")+"\n"+"Please write your valuable feedback here..");
                            }
                        });

                        ((settingsCardViewHolder)holder).privacyPOlicylayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                settingsActivity.privacyPolicy();
                            }
                        });
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                    break;





            }
        }
    }



    private ArrayList<WeightModel> retrieveList(String key){


        List<WeightModel> weightList;
        if(preferences.contains(key)){

            try
            {
                String json_weights=preferences.getString(key,"");
                Gson gson=new Gson();
                WeightModel[] weight_array=gson.fromJson(json_weights,WeightModel[].class);
                weightList= Arrays.asList(weight_array);
                return new ArrayList<WeightModel>(weightList);

            }catch (Exception ex){

                ex.printStackTrace();
                return null;
            }

        }else
        {
            return null;
        }

    }



//add
  public void showhorizontalCalenarDialog(){


        try{

            weightModel_List=retrieveList("weightarraylist");

            Calendar start= Calendar.getInstance();
            Calendar end= Calendar.getInstance();
            end.set(Calendar.YEAR,start.get(Calendar.YEAR));
            end.set(Calendar.MONTH,start.get(Calendar.MONTH));
            end.set(Calendar.DAY_OF_MONTH,start.getActualMaximum(Calendar.DAY_OF_MONTH)-1);
            start.set(Calendar.YEAR,start.get(Calendar.YEAR));
            start.set(Calendar.MONTH,start.get(Calendar.MONTH));
            start.set(Calendar.DAY_OF_MONTH,start.getActualMinimum(Calendar.DAY_OF_MONTH));

            dialog=new Dialog(reportActivity);
            LayoutInflater inflater=(LayoutInflater)reportActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v=inflater.inflate(R.layout.horizontal_calendar_layout,null,false);
            final TextView label=v.findViewById(R.id.label_tv);
            final EditText weight_val_et=v.findViewById(R.id.weight_val_dialog_et);
            final Button save_btn=v.findViewById(R.id.save_btn);
            final TextView cancel_btn=v.findViewById(R.id.cancel_btn);

            final TextView kg_tv=v.findViewById(R.id.kg_dialog_tv);
            final TextView lb_tv=v.findViewById(R.id.lb_dialog_tv);

            if(retrieveData("default_weight_unit").equals("kg")){
                kg_tv.setBackgroundResource(R.drawable.squrare_tv_bg);
                lb_tv.setBackgroundResource(R.drawable.square_tv_bg_simple);
            }else {
                lb_tv.setBackgroundResource(R.drawable.squrare_tv_bg);
                kg_tv.setBackgroundResource(R.drawable.square_tv_bg_simple);
            }

            weight_val_et.setText(0+"");


            HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder(v, R.id.calendarView)
                    .startDate(start.getTime())
                    .endDate(end.getTime())
                    .centerToday(true)
                    .showDayName(false)
                    .showMonthName(true)
                    .datesNumberOnScreen(5)   // Number of Dates cells shown on screen (Recommended 5)
                    // WeekDay text format
                    .dayNumberFormat("dd")    // Date format
                    .monthFormat("MMM") 	  // Month format
                    .showDayName(false)	  // Show or Hide dayName text
                    .showMonthName(true)

                    .textColor(Color.LTGRAY, Color.BLUE)    // Text color for none selected Dates, Text color for selected Date.
                    .selectedDateBackground(Color.TRANSPARENT)// Background color of the selected date cell.
                    .selectorColor(Color.BLUE)
                    .build();

            //  horizontalCalendar.show();
            dialog.setContentView(v);
            dialog.show();

            horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
                @Override
                public void onDateSelected(Date date, int position) {

                    boolean isExist=false;

                    weightDate=date;
                    String d =new SimpleDateFormat("MMM yyyy").format(date);
                    String dd =new SimpleDateFormat("dd MMM yyyy").format(date);
                    generalDate=dd;
                    label.setText(d);
                    int index=0;
                    if(weightModel_List!=null){
                        if(weightModel_List.size()>0){

                            for(int i=0;i<weightModel_List.size();i++){
                                weightModel=weightModel_List.get(i);

                                if(!TextUtils.isEmpty(weightModel.getDate())){

                                    if(weightModel.getDate().equals(dd)){
                                        isExist=true;
                                        index=i;
                                    }
                                }
                        }

                        }else {
                         //   Toast.makeText(cntxt,"empty list",Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if(isExist){
                            // int ind=weightModel_List.indexOf(dd);
                            weight_val_et.setText(weightModel_List.get(index).getWeight()+"");
                            isExist=false;
                        }else {

                          //  weightModel_List.add(new WeightModel(0f,dd,date));
                            weight_val_et.setText(0+"");
                        }

                    }else {
                        weightModel_List=new ArrayList<>();
                        saveWeightList("weightarraylist",weightModel_List);
                    }

                }

                @Override
                public void onCalendarScroll(HorizontalCalendarView calendarView,
                                             int dx, int dy) {

                }

                @Override
                public boolean onDateLongClicked(Date date, int position) {
                    return true;
                }
            });


            save_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WeightModel model;

                    if(TextUtils.isEmpty(weight_val_et.getText().toString())){
                        Toast.makeText(cntxt,"Enter valid weight value", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if(retrieveData("default_weight_unit").equals("kg")){

                        if(Double.parseDouble(weight_val_et.getText().toString())>900){
                            Toast.makeText(cntxt,"Enter valid weight value", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }else  if(retrieveData("default_weight_unit").equals("lb")){

                        if(Double.parseDouble(weight_val_et.getText().toString())>1980){
                            Toast.makeText(cntxt,"Enter valid weight value", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }

                    int index=0;
                    boolean isExist=false;
                    for(int i=0;i<weightModel_List.size();i++){
                        weightModel=weightModel_List.get(i);
                        if(!TextUtils.isEmpty(weightModel.getDate())){
                            if(weightModel.getDate().equals(generalDate)){
                                index=i;
                                isExist=true;
                                break;
                            }
                        }
                    }

                    if(!isExist){
                        if(TextUtils.isEmpty(generalDate)){
                            Toast.makeText(cntxt,"Not valid data", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(weightDate==null){
                            Toast.makeText(cntxt,"Not valid data", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        weightModel_List.add(new WeightModel(Float.parseFloat(weight_val_et.getText().toString()),generalDate,weightDate));

                    }else {

                        model=weightModel_List.get(index);
                        model.setWeight(Float.parseFloat(weight_val_et.getText().toString()));
                    }

                    saveWeightList("weightarraylist",weightModel_List);

                    dialog.dismiss();
                    reportActivity.reloadRecyclerView();
                }
            });


            kg_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(!retrieveData("default_weight_unit").equals("kg")){
                        saveData("default_weight_unit","kg");
                        kg_tv.setBackgroundResource(R.drawable.squrare_tv_bg);
                        lb_tv.setBackgroundResource(R.drawable.square_tv_bg_simple);
                        lb_tv.setTextColor(reportActivity.getResources().getColor(R.color.ligh_gray));

                        kg_tv.setTextColor(Color.WHITE);
                        lb_tv.setTextColor(Color.LTGRAY);
                        changeWeightUnitList(retrieveData("default_weight_unit"));
                        if(!TextUtils.isEmpty(weight_val_et.getText().toString())){
                            weight_val_et.setText(lbToKg(Double.parseDouble(weight_val_et.getText().toString()))+"");
                        }


                    }
                }
            });


            lb_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(!retrieveData("default_weight_unit").equals("lb")){

                        saveData("default_weight_unit","lb");
                        lb_tv.setBackgroundResource(R.drawable.squrare_tv_bg);
                        lb_tv.setTextColor(Color.WHITE);
                        kg_tv.setTextColor(Color.LTGRAY);
                        kg_tv.setBackgroundResource(R.drawable.square_tv_bg_simple);

                        changeWeightUnitList(retrieveData("default_weight_unit"));
                        if(!TextUtils.isEmpty(weight_val_et.getText().toString())){
                            weight_val_et.setText(kgToLb(Double.parseDouble(weight_val_et.getText().toString()))+"");
                        }
                    }

                }
            });

            cancel_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel();
                }
            });


        }catch (Exception ex){
            ex.printStackTrace();
        }




    }


    private double inchesToCentimeters(double height){


//        1 feet=30.48cm
//        1 inch=2.54 cm

        String s= String.format("%.2f", height*2.54);
        return Double.parseDouble(s) ;
    }


    private double centimetersToInches(double height){

        String s= String.format("%.2f", (height/2.54));
        return Double.parseDouble(s) ;
    }


    private double lbToKg(double weight){


        String s= String.format("%.2f", (weight/2.2));
        return Double.parseDouble(s);
    }

    private double kgToLb(double weight){

        String s= String.format("%.2f", weight*2.2);
        return Double.parseDouble(s);
    }


    private void saveWeightList(String key, ArrayList<WeightModel> list){
//Set the values
        Gson gson=new Gson();
        String weight=gson.toJson(list);

        editor=preferences.edit();
//        Set<String> set = new HashSet<String>();
//        set.addAll(list);
        editor.putString(key, weight);
        editor.commit();
    }
//add
    public void showHealthDataDialog(){

        dialog=new Dialog(settingsActivity);
        LayoutInflater inflater=(LayoutInflater)settingsActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v=inflater.inflate(R.layout.healthdata_dialog,null,false);

        final EditText dob_et=v.findViewById(R.id.dob_dlg_et_healthdata);
        TextView set_tv=v.findViewById(R.id.set_healthdata_tv);
        TextView cancel_tv=v.findViewById(R.id.cancel_healthdata_tv);
        final TextView male_tv=v.findViewById(R.id.male_healthdatadialog_tv);
        final TextView female_tv=v.findViewById(R.id.female_healthdatadialog_tv);
        if(retrieveData("gender").equals("m")){

            male_tv.setBackgroundResource(R.drawable.squrare_tv_bg);
            female_tv.setBackgroundResource(R.drawable.square_tv_bg_simple);
        }else {
            male_tv.setBackgroundResource(R.drawable.square_tv_bg_simple);
            female_tv.setBackgroundResource(R.drawable.squrare_tv_bg);
        }

        dob_et.setText(retrieveData("dob"));

        female_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData("gender","f");
                male_tv.setBackgroundResource(R.drawable.square_tv_bg_simple);
                female_tv.setBackgroundResource(R.drawable.squrare_tv_bg);
            }
        });

        male_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveData("gender","m");
                male_tv.setBackgroundResource(R.drawable.squrare_tv_bg);
                female_tv.setBackgroundResource(R.drawable.square_tv_bg_simple);
            }
        });


        set_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveData("dob",dob_et.getText().toString().trim());
                dialog.dismiss();

            }
        });


        cancel_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
        dialog.setContentView(v);
        dialog.show();

    }
//add
    public void showRestDialog(final TextView txtvew, final String sender){
        dialog=new Dialog(settingsActivity);
        LayoutInflater inflater=(LayoutInflater)settingsActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v=inflater.inflate(R.layout.rest_dialouge_layout,null,false);

        final TextView timeval=v.findViewById(R.id.time_val_restset);
        TextView set=v.findViewById(R.id.set_tv);
        TextView cancel=v.findViewById(R.id.cancel_tv_restset);
        ImageView increase=v.findViewById(R.id.increase_restset);
        ImageView decrease=v.findViewById(R.id.decrese_restset);

         counter=0;

        if(sender.equals("countdown")){

            if(!TextUtils.isEmpty(retrieveData("countdown_time"))){

                counter= Integer.parseInt(retrieveData("countdown_time"));
            }
        }else {

            if(!TextUtils.isEmpty(retrieveData("break_time"))){

                counter= Integer.parseInt(retrieveData("break_time"));
            }
        }

        timeval.setText(counter+"");

        increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(counter<=300){
                   counter++;
                   timeval.setText(counter+"");
               }

            }
        });


        decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(counter>5){
                    counter--;
                    timeval.setText(counter+"");
                }

            }
        });


        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(sender.equals("countdown")){

                    saveData("countdown_time",counter+"");
                    txtvew.setText(counter+" sec");
                }else
                {
                    txtvew.setText(counter+" sec");
                    saveData("break_time",counter+"");
                }

                dialog.dismiss();

            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

        dialog.setContentView(v);
        dialog.show();
    }
    @Override
    public int getItemCount() {
        return dataSet.size();
    }


    private void sendFeedBackMail(){

        try {
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
// The intent does not have a URI, so declare the "text/plain" MIME type
            emailIntent.setType("text/plain");

            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {"jon@example.com"}); // recipients
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Email subject");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message text");
          //  emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("content://path/to/email/attachment"));


            PackageManager packageManager = cntxt.getPackageManager();
            List activities = packageManager.queryIntentActivities(emailIntent,
                    PackageManager.MATCH_DEFAULT_ONLY);
            boolean isIntentSafe = activities.size() > 0;


            if(isIntentSafe){
                cntxt.startActivity(emailIntent);
            }else {
                Toast.makeText(cntxt,"NO app found", Toast.LENGTH_SHORT).show();
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }

    }
//add
    private void showWeightHeightDialog(TextView wght){

        View v=null;
        LayoutInflater inflater;

        if(retrieveData("sendingactivity").equals("reward")){


            if(rewardActivity!=null){
             //   inflater=(LayoutInflater)rewardActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                dialog =new Dialog(rewardActivity);
                inflater=(LayoutInflater)rewardActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v=inflater.inflate(R.layout.height_weight_dialog_layout,null);
            }else {

            }



        }else{
            if(reportActivity!=null){
                inflater=(LayoutInflater)reportActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }else {
                dialog =new Dialog(AppBase.context);
                inflater=(LayoutInflater)AppBase.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }


            // ialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);

           // v=inflater.inflate(R.layout.height_weight_dialog_layout,null);
        }

if(v==null)return;

        final EditText weight_val_tv=v.findViewById(R.id.weight_val_dialog_tv);
        final EditText height_val_tv=v.findViewById(R.id.height_val_dialog_tv);
        final TextView kg_tv=v.findViewById(R.id.kg_dialog_tv);
        final TextView lb_tv=v.findViewById(R.id.lb_dialog_tv);
        final TextView cm_tv=v.findViewById(R.id.cm_dialog_tv);
        final TextView in_tv=v.findViewById(R.id.in_dialog_tv);
        TextView cancel_tv=v.findViewById(R.id.cancl_weightdlg_tv);
        TextView next_tv=v.findViewById(R.id.next_weightdlg_tv);
        final TextView f_tv=v.findViewById(R.id.female_dialog_tv);
        final TextView m_tv=v.findViewById(R.id.male_dialog_tv);
        final EditText dob_et=v.findViewById(R.id.dob_dlg_et);
        final TextView w=wght;

        weight_val_tv.setText(DataManager.retrieveStringData("weight"));
        height_val_tv.setText(DataManager.retrieveStringData("height"));

        if(DataManager.retrieveStringData("default_weight_unit").equals("kg")){

            lb_tv.setBackgroundResource(R.drawable.square_tv_bg_simple);
         //   int color=rewardActivity.getResources().getColor(R.color.ligh_gray);
            int color;
            color= AppBase.context.getResources().getColor(R.color.ligh_gray);

            lb_tv.setTextColor(color);

            kg_tv.setBackgroundResource(R.drawable.squrare_tv_bg);

        }else if(DataManager.retrieveStringData("default_weight_unit").equals("lb")){

            kg_tv.setBackgroundResource(R.drawable.square_tv_bg_simple);
            kg_tv.setTextColor(AppBase.context.getResources().getColor(R.color.ligh_gray));
            lb_tv.setBackgroundResource(R.drawable.squrare_tv_bg);
        }


        if(DataManager.retrieveStringData("default_height_unit").equals("cm")){

            in_tv.setBackgroundResource(R.drawable.square_tv_bg_simple);
            cm_tv.setBackgroundResource(R.drawable.squrare_tv_bg);

        }else if(DataManager.retrieveStringData("default_height_unit").equals("in")){

            cm_tv.setBackgroundResource(R.drawable.square_tv_bg_simple);
            in_tv.setBackgroundResource(R.drawable.squrare_tv_bg);
        }

        dob_et.setText(retrieveData("dob"));


        if(DataManager.retrieveStringData("gender").equals("m")){

            f_tv.setBackgroundResource(R.drawable.square_tv_bg_simple);
            m_tv.setBackgroundResource(R.drawable.squrare_tv_bg);

        }else if(DataManager.retrieveStringData("gender").equals("f")){

            m_tv.setBackgroundResource(R.drawable.square_tv_bg_simple);
            f_tv.setBackgroundResource(R.drawable.squrare_tv_bg);
        }



        kg_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!DataManager.retrieveStringData("default_weight_unit").equals("kg")){

                    saveData("default_weight_unit","kg");
                    lb_tv.setBackgroundResource(R.drawable.square_tv_bg_simple);
                    kg_tv.setBackgroundResource(R.drawable.squrare_tv_bg);
                    if(!TextUtils.isEmpty(weight_val_tv.getText().toString())){
                        weight_val_tv.setText(lbToKg(Double.parseDouble(weight_val_tv.getText().toString()))+"");
                    }
                    changeWeightUnit(DataManager.retrieveStringData("default_weight_unit"));
                  //  weightModel_List=retrieveList("weightarraylist");
                }

            }
        });


        lb_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(!retrieveData("default_weight_unit").equals("lb")){

                    DataManager.saveStringData("default_weight_unit","lb");
                    kg_tv.setBackgroundResource(R.drawable.square_tv_bg_simple);
                    lb_tv.setBackgroundResource(R.drawable.squrare_tv_bg);

                    if(!TextUtils.isEmpty(weight_val_tv.getText().toString())){
                        weight_val_tv.setText(kgToLb(Double.parseDouble(weight_val_tv.getText().toString()))+"");
                    }
                    changeWeightUnit(DataManager.retrieveStringData("default_weight_unit"));
                }

            }
        });


        cm_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!retrieveData("default_height_unit").equals("cm")){

                    saveData("default_height_unit","cm");
                    in_tv.setBackgroundResource(R.drawable.square_tv_bg_simple);
                    cm_tv.setBackgroundResource(R.drawable.squrare_tv_bg);

                    changeHeightUnit(retrieveData("default_height_unit"));

                    if(!TextUtils.isEmpty(height_val_tv.getText().toString())){
                        height_val_tv.setText( inchesToCentimeters(Double.parseDouble(height_val_tv.getText().toString()))+"");
                    }
                }

            }
        });

        in_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!retrieveData("default_height_unit").equals("in")){

                    saveData("default_height_unit","in");
                    cm_tv.setBackgroundResource(R.drawable.square_tv_bg_simple);
                    in_tv.setBackgroundResource(R.drawable.squrare_tv_bg);
                    changeHeightUnit(retrieveData("default_height_unit"));
                    if(!TextUtils.isEmpty(height_val_tv.getText().toString())){
                        height_val_tv.setText( centimetersToInches(Double.parseDouble(height_val_tv.getText().toString()))+"");
                    }
                }


            }
        });

        next_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(!TextUtils.isEmpty(weight_val_tv.getText().toString())){

                    if(DataManager.retrieveStringData("default_weight_unit").equals("kg")){
                        if(Double.parseDouble(weight_val_tv.getText().toString())>900){
                            Toast.makeText(cntxt,"Invalid weight entered", Toast.LENGTH_SHORT).show();
                            return;
                        }else {
                            saveData("weight",weight_val_tv.getText()+"");
                        }
                    }else if(retrieveData("default_weight_unit").equals("lb")){

                        if(Double.parseDouble(weight_val_tv.getText().toString())>1980){
                            Toast.makeText(cntxt,"Invalid weight entered", Toast.LENGTH_SHORT).show();
                            return;
                        }else {
                            saveData("weight",weight_val_tv.getText()+"");
                        }
                    }
                }
                if(!TextUtils.isEmpty(height_val_tv.getText().toString())){

                    if(DataManager.retrieveStringData("default_height_unit").equals("cm")){
                        if(Double.parseDouble(height_val_tv.getText().toString())>400){
                            Toast.makeText(cntxt,"Invalid height entered", Toast.LENGTH_SHORT).show();
                            return;
                        }else {
                            saveData("height",height_val_tv.getText()+"");
                        }
                    }else if(DataManager.retrieveStringData("default_height_unit").equals("in")){

                        if(Double.parseDouble(height_val_tv.getText().toString())>157.480){
                            Toast.makeText(cntxt,"Invalid height entered", Toast.LENGTH_SHORT).show();
                            return;
                        }else {
                            saveData("height",height_val_tv.getText()+"");
                        }

                    }
                }


                if(!TextUtils.isEmpty(weight_val_tv.getText().toString()) && !TextUtils.isEmpty(height_val_tv.getText().toString()) ){
//

                    if(DataManager.retrieveStringData("show").equals("height")){
                        // String s= String.format("%.1f",);
                        w.setText( height_val_tv.getText().toString()+"cm");
                    }else if(retrieveData("show").equals("weight")){
                        w.setText(weight_val_tv.getText().toString());
                    }

                    if(DataManager.retrieveStringData("sendingactivity").equals("reward")){

                        saveData("bmi",rewardActivity.calculateBMI(Double.parseDouble(weight_val_tv.getText().toString()), Double.parseDouble(height_val_tv.getText().toString()))+"");

                        rewardActivity.reloadRecyclerView();
                    }else if(DataManager.retrieveStringData("sendingactivity").equals("report")){

                        saveData("bmi",reportActivity.calculateBMI(Double.parseDouble(weight_val_tv.getText().toString()), Double.parseDouble(height_val_tv.getText().toString()))+"");

                        reportActivity.reloadRecyclerView();
                    }

                    DataManager.saveStringData("dob",dob_et.getText().toString());
                    String weight=weight_val_tv.getText().toString();
                    Calendar calendar= Calendar.getInstance();

                    String dateStr=new SimpleDateFormat("dd,mm").format(calendar.getTime());
                    WeightModel weightModel=new WeightModel(Float.parseFloat(weight),dateStr,calendar.getTime());

                    ArrayList<WeightModel> weightModels=DataManager.retrieveWeightList("weightarraylist");

                    if(weightModels==null){
                        weightModels=new ArrayList<>();
                        weightModels.add(weightModel);
                    }else {
                      weightModels.add(weightModel);
                    }

                    DataManager.saveWeightList("weightarraylist",weightModels);

                    dialog.cancel();

                }else {

                   if(TextUtils.isEmpty(weight_val_tv.getText().toString())){
                       Toast.makeText(cntxt,"Enter weight value", Toast.LENGTH_LONG).show();
                   }else if(TextUtils.isEmpty(height_val_tv.getText().toString())){
                       Toast.makeText(cntxt,"Enter height value", Toast.LENGTH_LONG).show();
                   }
                }


                //  wght.setText(weight_val_tv.getText().toString());
                //showBirthDateDialog();
            }
        });

        cancel_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.cancel();
               // Toast.makeText(mContext,"cancel clikced",Toast.LENGTH_SHORT).show();
                // showToast("lb");
            }
        });



        f_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveData("gender","f");
                f_tv.setBackgroundResource(R.drawable.squrare_tv_bg);
               m_tv.setBackgroundResource(R.drawable.square_tv_bg_simple);

            }
        });


        m_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveData("gender","m");
                f_tv.setBackgroundResource(R.drawable.square_tv_bg_simple);
                m_tv.setBackgroundResource(R.drawable.squrare_tv_bg);
            }
        });

        dialog.setContentView(v);
        dialog.show();
    }



    private void navigateToMain(){

        try
        {
            try
            {
                Intent intent=new Intent(mContext,Main2Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                rewardActivity.finish();
               rewardActivity.startActivity(intent);
            }catch (Exception ex){
                ex.printStackTrace();
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }


    }

    public void changeWeightUnit(String unit){

        if(!TextUtils.isEmpty(unit)) {
            if(!TextUtils.isEmpty(retrieveData("weight"))){
                double d= Double.parseDouble(retrieveData("weight"));
                if(unit.equals("kg")){
                    saveData("weight",lbToKg(d)+"");
                }else  if(unit.equals("lb")){
                    saveData("weight",kgToLb(d)+"");
                }
            }
        }
    }


    public void changeHeightUnit(String unit){

        if(!TextUtils.isEmpty(unit)) {
            if(!TextUtils.isEmpty(retrieveData("height"))){
                double d= Double.parseDouble(retrieveData("height"));
                if(unit.equals("cm")){
                    DataManager.saveStringData("height",inchesToCentimeters(d)+"");
                }else  if(unit.equals("in")){
                    DataManager.saveStringData("height",centimetersToInches(d)+"");
                }
            }
        }
    }


    public void changeWeightUnitList(String unit){

        if(!TextUtils.isEmpty(unit)) {

            ArrayList<WeightModel> list=retrieveList("weightarraylist");
            if(list!=null){
                if(list.size()>0){
                    for(int i=0;i<list.size();i++){
                        if(list.get(i).getWeight()>0){
                            if(unit.equals("kg")){
                                list.get(i).setWeight((float)lbToKg(list.get(i).getWeight()));

                            }else  if(unit.equals("lb")){
                                list.get(i).setWeight((float)kgToLb(list.get(i).getWeight()));
                            }
                        }
                    }

                    DataManager.saveWeightList("weightarraylist",list);
                    weightModel_List=retrieveList("weightarraylist");
                }
            }
        }
    }


    public void changeHeightUnitList(String unit){

        if(!TextUtils.isEmpty(unit)) {
            if(!TextUtils.isEmpty(retrieveData("height"))){
                double d= Double.parseDouble(retrieveData("height"));
                if(unit.equals("cm")){
                    DataManager.saveStringData("height",inchesToCentimeters(d)+"");
                }else  if(unit.equals("in")){
                    DataManager.saveStringData("height",centimetersToInches(d)+"");
                }
            }
        }
    }




}


