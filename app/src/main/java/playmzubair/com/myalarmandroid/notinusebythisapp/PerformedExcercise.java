package playmzubair.com.myalarmandroid.notinusebythisapp;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.designerclicksol.myworkout.AppBase;
import com.example.designerclicksol.myworkout.R;
import com.example.designerclicksol.myworkout.activities.RewardActivity;
import com.example.designerclicksol.myworkout.databinding.ActivityPerformedExcerciseBinding;
import com.example.designerclicksol.myworkout.managers.DataManager;
import com.example.designerclicksol.myworkout.managers.TtsManager;
import com.example.designerclicksol.myworkout.model.CurrentExercise;
import com.example.designerclicksol.myworkout.model.ExerciseModel;
import com.example.designerclicksol.myworkout.model.ExerciseTimeModel;
import com.example.designerclicksol.myworkout.model.HistoryExercisesPerformedModel;
import com.example.designerclicksol.myworkout.model.History_model;
import com.example.designerclicksol.myworkout.utils.AminUtils;
import com.example.designerclicksol.myworkout.utils.Constants;
import com.example.designerclicksol.myworkout.utils.CountDownTimerAd;
import com.example.designerclicksol.myworkout.utils.ExerciseTimeManager;
import com.example.designerclicksol.myworkout.utils.dialogues.BreakTimeDialogue;
import com.jackandphantom.circularprogressbar.CircleProgressbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class PerformedExcercise extends AppCompatActivity implements BreakTimeDialogue.ClickListeners {


    CurrentExercise currentExercise;


    String chestStretch = "CHEST STRETCH";
    String cobraStretch = "COBRA STRETCH";
    String shoulderStretch = "SHOULDER STRETCH";
    String sidePlankRight="SIDE PLANK RIGHT";
    String sidePlankLEFT="SIDE PLANK RIGHT";
    String SPINElUMBERTSL="SPINE LUMBAR TWIST STRETCH LEFT";
    String SPINElUMBERTSR="SPINE LUMBAR TWIST STRETCH RIGHT";

    String PLANK="PLANK";

    String[] linearProgessDutation;


    public int counttimeForChestCobraShoulder = 20;
    public int currentExerciseIndex = 0;


    ActivityPerformedExcerciseBinding binding;


    ExerciseTimeModel timeModel;


    long kcals=0;

    TtsManager ttsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // overridePendingTransition(R.anim.zoomout, 0);

        //getWindow().setWindowAnimations(R.style.WindowAnimationTransition);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_performed_excercise);
      initTts();
        initCurrentExercise();


        initViewDataFirstTime();
        initExerciseStartTime();

       ttsManager= ((AppBase)getApplicationContext()).getTtsInst();

        zoomIn(binding.bottomContainer);




        setProgressBarColor(binding.horizontalprogressBarContainer.progressBar);

        initClickListners();



    }

public void initTts(){
ttsManager=new TtsManager(getApplicationContext(),new MyTextToSpechCallBack());
}

    public void initClickListners(){
        binding.back.setOnClickListener((View v) -> {
            finish();
        });


        binding.next.setOnClickListener((View v) -> {
            AminUtils.zoomIn(getApplicationContext(),binding.next,null);
            nextExercise();
        });

        binding.back.setOnClickListener((View v) -> {
            AminUtils.zoomIn(getApplicationContext(),binding.back,null);
            previousExercise();
        });


        binding.help.setOnClickListener((View view)->{
              performedHelpBtnClick();

        });

        binding.closeBottomSheetIv.setOnClickListener((View view)->{
       slidedwnBottomSheetLayout();
        });
    }





    ArrayList<ExerciseModel> currentExercisesModelList;

    public void performedHelpBtnClick(){
        slideupBottomSheetLayout();

    currentExercisesModelList=    currentExercise.getCurrentExerciseModelList();
    binding.exerciseNameBsht.setText(currentExercisesModelList.get(currentExerciseIndex).getTitle());
    binding.textViewdiscriptionBst.setText(currentExercisesModelList.get(currentExerciseIndex).getDiscription());


    }





    // bottomSheets view



    public void slidedwnBottomSheetLayout(){


        AminUtils.slideUp(this,binding.bottomSheetHolder1,null);
        binding.bottomSheetHolder1.setVisibility(View.GONE);
    }



    public void slideupBottomSheetLayout(){
        binding.bottomSheetHolder1.setVisibility(View.VISIBLE);
        AminUtils.slideDown(this,binding.bottomSheetHolder1,null);



    }







    public class MyTextToSpechCallBack implements TtsManager.TextToSpeachCallback {
        @Override
        public void ttsInitComplete(int status) {
            if (DataManager.retrieveBoolData(Constants.SPKeySound)) {


                ttsManager.speakOut("Ready to go.");
            }
        }
    }




    public void speak(String txt){
        if (DataManager.retrieveBoolData(Constants.SPKeySound)) {
            if(ttsManager!=null){
                ttsManager.speakOut(txt);
            }
        }

    }

    public void stopSpeak(){
        if (DataManager.retrieveBoolData(Constants.SPKeySound)) {
            if(ttsManager!=null){
                ttsManager.stopTTS();
            }
        }

    }

    private String retrieveStringData(String key) {

        return AppBase.getSp().getString(key, "");
    }

    public void initCurrentExercise() {
        currentExercise = new CurrentExercise(this);
        currentExercise.initData();
    }


    public void initExerciseStartTime() {

        timeModel = new ExerciseTimeModel();
        timeModel.setExerciseStartTime(System.currentTimeMillis());

    }


    public void ExerciseEnd() {

        if (timeModel != null) {

            timeModel.setExerciseEndTime(System.currentTimeMillis()-40000);
        }
    }


    public long getTimeForExercisePerformed() {

        return new ExerciseTimeManager().getExercisedPerformedTime(timeModel.getExerciseStartTime(), timeModel.getExerciseEndTime());
    }


    //next and previous exercise logic and data


    boolean flagSoundOn = true;

    public void initViewDataFirstTime() {
        binding.gifIv.setImageResource(currentExercise.currentExercisesGifs[currentExerciseIndex]);

        binding.exercisetv1.setText(currentExercise.current_exerciseListAry[currentExerciseIndex]);
        binding.exercisetv2.setText(currentExercise.current_exerciseCountAry[currentExerciseIndex]);

        speak(currentExercise.current_exerciseListAry[currentExerciseIndex]);

        if (new DataManager().retrieveBoolData(Constants.SPKeySound)) {
            binding.soundIv.setImageResource(R.drawable.ic_volume_up);
        } else {
            binding.soundIv.setImageResource(R.drawable.ic_volume_off);
        }


        binding.soundIv.setOnClickListener((View v) -> {
            if (flagSoundOn) {
                 flagSoundOn=false;
                binding.soundIv.setImageResource(R.drawable.ic_volume_off);
                DataManager.saveBoolData(Constants.SPKeySound,false);

                stopSpeak();


            } else {
                flagSoundOn=true;
                binding.soundIv.setImageResource(R.drawable.ic_volume_up);
                DataManager.saveBoolData(Constants.SPKeySound,true);

            }

        });


    }

    CountDownTimerAd countDownTimerAd = null;



    public void nextExercise() {
        if (currentExerciseIndex < currentExercise.currentExercisesGifs.length - 1) {
            currentExerciseIndex++;

            kcals=kcals+calculateKCAL(timeModel.getExerciseStartTime(), System.currentTimeMillis());
            calculteGrandTotalWorkout();

            if(countDownTimerAd!=null){
                countDownTimerAd.skipTimer();
            }

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    new BreakTimeDialogue().showDialogue(PerformedExcercise.this, new BreakTimeDialogue.ClickListeners() {
                        @Override
                        public void onNextClick() {

                        }

                        @Override
                        public void onPreviousClick() {

                        }

                        @Override
                        public void onSkipClick() {

                            if (currentExercise.current_exerciseListAry[currentExerciseIndex].equals(chestStretch) ||
                                    currentExercise.current_exerciseListAry[currentExerciseIndex].equals(cobraStretch) ||
                                    currentExercise.current_exerciseListAry[currentExerciseIndex].equals(shoulderStretch)
                                    ||
                                    currentExercise.current_exerciseListAry[currentExerciseIndex].equals(sidePlankLEFT)
                                    ||
                                    currentExercise.current_exerciseListAry[currentExerciseIndex].equals(sidePlankRight)

                                    ||
                                    currentExercise.current_exerciseListAry[currentExerciseIndex].equals(SPINElUMBERTSL)
                                    ||
                                    currentExercise.current_exerciseListAry[currentExerciseIndex].equals(SPINElUMBERTSR)
                                    ||
                                    currentExercise.current_exerciseListAry[currentExerciseIndex].equals(PLANK)
                                    ) {

                                binding.bottomContainer.setVisibility(View.INVISIBLE);

                                binding.gifIv.setImageResource(currentExercise.currentExercisesGifs[currentExerciseIndex]);

                                binding.exercisetv1.setText(currentExercise.current_exerciseListAry[currentExerciseIndex]);
                                binding.exercisetv2.setText(currentExercise.current_exerciseCountAry[currentExerciseIndex]);
                                zoomIn(binding.gifCotainer);
                                int lineardur=0;
                                try{
                                    linearProgessDutation=currentExercise.current_exerciseCountAry[currentExerciseIndex].split(":");

                                     lineardur= Integer.parseInt(linearProgessDutation[1]);
                                }catch (Exception e){
                                  e.printStackTrace();
                                }

                                if(lineardur!=0){
                                    counttimeForChestCobraShoulder=lineardur;
                                }


                                     speak(currentExercise.current_exerciseListAry[currentExerciseIndex]);

                                countDownTimerAd = new CountDownTimerAd(counttimeForChestCobraShoulder, new CountDownTimerAd.UpdateUIHandlerCall() {
                                    @Override
                                    public void upDateUI(int opt, float progress, int countDown) {

                                        switch (opt) {

                                            case 0:
                                                binding.horizontalprogressBarContainer.getRoot().setVisibility(View.GONE);
                                              //  countDownTimerAd.interruptLinearThread();


                                                nextExercise();
                                                break;

                                            case 1:
                                                binding.horizontalprogressBarContainer.getRoot().setVisibility(View.VISIBLE);
                                             // binding.horizontalprogressBarContainer.progressBar.setProgress((int) progress);
                                                new AminUtils().smoothProgressAnimate(binding.horizontalprogressBarContainer.progressBar, (int) progress);
                                               setRemainingTimeTv(countDown);

                                                break;
                                        }

                                    }
                                });


                               countDownTimerAd.startTimerLinearProgress();


                                return;
                            }

                            binding.bottomContainer.setVisibility(View.VISIBLE);

                            speak(currentExercise.current_exerciseListAry[currentExerciseIndex]);
                            binding.gifIv.setImageResource(currentExercise.currentExercisesGifs[currentExerciseIndex]);

                            binding.exercisetv1.setText(currentExercise.current_exerciseListAry[currentExerciseIndex]);
                            binding.exercisetv2.setText(currentExercise.current_exerciseCountAry[currentExerciseIndex]);
                            zoomIn(binding.gifCotainer);

                        }
                    }, currentExercise.currentExercisesGifs[currentExerciseIndex], currentExercise.current_exerciseListAry[currentExerciseIndex]);

                }
            }, 5);


        } else {
            showRewardActivity();
        }


    }

    public void setRemainingTimeTv(int timeVal){
        String time="";
        if(timeVal<10){
            time="0"+timeVal;
        }else {
            time=""+timeVal;
        }

        binding.horizontalprogressBarContainer.horiProgressText.setText(time + "");
        binding.exercisetv2.setText("00:"+time);

    }


    public String getTotalTimeForCurrentExercisePerformed() {
        return ExerciseTimeManager.getDurationBreakdown(getTimeForExercisePerformed());
    }




    public void showRewardActivity() {

        DataManager.saveStringData("activitytype","reward");

        if(DataManager.retrieveStringData("exerciseNameAD").equalsIgnoreCase("fullbody1"))
        {
            String days=DataManager.retrieveStringData("Listof30DaysFullBody1");
            if(days!=null||days.equalsIgnoreCase("")){
                days="1,";
                DataManager.saveStringData("Listof30DaysFullBody1",days);
            }else {
                String[] day=days.split(",");
                //  Calendar calendar=Calendar.getInstance();
                int dayofmonth=day.length;

                dayofmonth++;
                days+=day+",";
                DataManager.saveStringData("Listof30DaysFullBody1",days);



            }
        }
        else  if(DataManager.retrieveStringData("exerciseNameAD").equalsIgnoreCase("fullbody2")){
String days=DataManager.retrieveStringData("Listof30DaysFullBody2");

     if(days!=null||days.equalsIgnoreCase("")){
         days="1,";
         DataManager.saveStringData("Listof30DaysFullBody2",days);
     }else {
         String[] day=days.split(",");
       //  Calendar calendar=Calendar.getInstance();
         int dayofmonth=day.length;

         dayofmonth++;
         days+=day+",";
         DataManager.saveStringData("Listof30DaysFullBody2",days);



     }

        }

        ExerciseEnd();
        Intent intentReward = new Intent(getApplicationContext(), RewardActivity.class);
        intentReward.putExtra(Constants.intentKeyExcercisecount, currentExercise.current_exerciseListAry.length);

        String totalTime = getTotalTimeForCurrentExercisePerformed();

        saveCurrentDateInHistory();
          saveGrandTotalCalories();
        intentReward.putExtra(Constants.intentKeyTotalTimeCount, totalTime);
        intentReward.putExtra(Constants.intentKeyKcal, kcals);
        intentReward.putExtra("startTime",timeModel.getExerciseStartTime());


        long totalwrkouttime= Long.parseLong(DataManager.retrieveStringData("grand_totalworkout_TME"));


        long curTime=getTimeForExercisePerformed();
        totalwrkouttime= totalwrkouttime+curTime;

        DataManager.saveStringData("grand_totalworkout_TME",totalwrkouttime+"");




        startActivity(intentReward);
    }



    ArrayList<HistoryExercisesPerformedModel> history_modelArrayList;
    History_model historyInstance;

    public void saveCurrentDateInHistory() {
        Calendar calendar = Calendar.getInstance();

        Date dateR=new Date();

        String time = new SimpleDateFormat("hh:mm a").format(calendar.getTime());
        String date = new SimpleDateFormat("yyyy:MM:dd").format(calendar.getTime());

        String dateMonth=new SimpleDateFormat("EEE, MMM d").format(calendar.getTime());

        String dayOfWeek= String.valueOf(calendar.get(Calendar.DAY_OF_WEEK));

        HistoryExercisesPerformedModel exercisesPerformedModel = new HistoryExercisesPerformedModel(currentExercise.currentExerciseType, getTotalTimeForCurrentExercisePerformed(),
                date, time,kcals+"",dateMonth,dayOfWeek,dateR);




        history_modelArrayList= DataManager.retrieveList(Constants.SPKeyHistoryExercisePerformed);
        if(history_modelArrayList!=null){
            history_modelArrayList.add(exercisesPerformedModel);
            DataManager.saveList(Constants.SPKeyHistoryExercisePerformed,history_modelArrayList);
        }else
        {
            history_modelArrayList=new ArrayList<HistoryExercisesPerformedModel>();
            history_modelArrayList.add(exercisesPerformedModel);
            DataManager.saveList(Constants.SPKeyHistoryExercisePerformed,history_modelArrayList);

        }


    }


    private void saveGrandTotalCalories(){
        String c= DataManager.retrieveStringData(Constants.SPKeyGradCallories);
        if(!TextUtils.isEmpty(c)){

            long cl= Long.parseLong(c);
            cl=cl+kcals;
            DataManager.saveStringData(Constants.SPKeyGradCallories,cl+"");
        }
    }


    private void calculteGrandTotalWorkout(){

        if(DataManager.retrieveStringData("grand_total_workouts")!=null){
            if(!DataManager.retrieveStringData("grand_total_workouts").equals("")){

                long wrkot= Long.parseLong(DataManager.retrieveStringData("grand_total_workouts"));
                wrkot++;
                DataManager.saveStringData("grand_total_workouts",wrkot+"");
            }
        }
    }


    private long calculateKCAL(long st_time,long e_time){
        // Calories Burned (kcal) = METs x (WEIGHT_IN_KILOGRAM) x (DURATION_IN_HOUR)

        long time=e_time-st_time;
        double sec=time/1000;
        double min=sec/60;
        double hours=min/60;
        String weight= DataManager.retrieveStringData("weight");
        if(!TextUtils.isEmpty(weight)){

            Double w= Double.parseDouble(weight.toString());
            if (DataManager.retrieveStringData("default_weight_unit").equals("lb")){

                w=lbToKg(w);
            }

            return (long)(8*w*hours);
        }
        else
        {
            return 0;
        }
    }


    private double lbToKg(double weight){

        return (weight/2.2);
    }



    public void previousExercise() {

        if (currentExerciseIndex > 0) {
            currentExerciseIndex--;


            new BreakTimeDialogue().showDialogue(PerformedExcercise.this, new BreakTimeDialogue.ClickListeners() {
                @Override
                public void onNextClick() {

                }

                @Override
                public void onPreviousClick() {

                }

                @Override
                public void onSkipClick() {

                    binding.gifIv.setImageResource(currentExercise.currentExercisesGifs[currentExerciseIndex]);

                    binding.exercisetv1.setText(currentExercise.current_exerciseListAry[currentExerciseIndex]);
                    binding.exercisetv2.setText(currentExercise.current_exerciseCountAry[currentExerciseIndex]);
                    zoomIn(binding.gifCotainer);
                }
            }, currentExercise.currentExercisesGifs[currentExerciseIndex], currentExercise.current_exerciseListAry[currentExerciseIndex]);


        }
    }


    public void zoomIn(View view) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                AminUtils.zoomIn(getApplicationContext(), view, new AminUtils.AnimationCalllback() {
                    @Override
                    public void onAnimationEnds() {


                    }
                });
            }
        }, 10);

    }

    public void zoomInPrevious(View view) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                AminUtils.zoomIn(getApplicationContext(), view, new AminUtils.AnimationCalllback() {
                    @Override
                    public void onAnimationEnds() {
                        new BreakTimeDialogue().showDialogue(PerformedExcercise.this, new PerformedExcercise(), 0, "ercecise name");
                    }
                });
            }
        }, 10);

    }


    public void zoomOut(View view) {
        AminUtils.zoomOut(getApplicationContext(), view, new AminUtils.AnimationCalllback() {
            @Override
            public void onAnimationEnds() {

            }
        });
    }

    public void zoomOutPrevious(View view) {
        AminUtils.zoomOut(getApplicationContext(), view, new AminUtils.AnimationCalllback() {
            @Override
            public void onAnimationEnds() {

            }
        });
    }


    public void selectCurrentExercise() {

    }

    @Override
    public void onNextClick() {

    }

    @Override
    public void onPreviousClick() {

    }

    @Override
    public void onSkipClick() {

    }


    CircleProgressbar circleProgressbar;
    TextView circulatProgressTv;

    public void initBreakTimeLayout() {
        circleProgressbar = (CircleProgressbar) findViewById(R.id.circularProgress);
        circulatProgressTv = (TextView) findViewById(R.id.progressTv);
    }

    public void showBreakTimeLayout() {
        binding.breakTimelayoutContainer.setVisibility(View.VISIBLE);

    }

    public void hideBreakTimeLayout() {
        binding.breakTimelayoutContainer.setVisibility(View.INVISIBLE);
    }


    public void setProgressBarColor(ProgressBar progressBar) {
        progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.progress_bar_drawable));

    }


    @Override
    public void onBackPressed() {
        if(countDownTimerAd!=null){
            countDownTimerAd.interruptLinearThread();
            countDownTimerAd.interruptThreadForCircualrAnim();
            countDownTimerAd.skipTimerAndHandler();
        }
        super.onBackPressed();
    }
}
