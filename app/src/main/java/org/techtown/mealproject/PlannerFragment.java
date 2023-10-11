package org.techtown.mealproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PlannerFragment extends Fragment {

    Context context;
    TabLayout.OnTabSelectedListener listener;
    DailyPlannerFragment dailyPlannerFragment;
    WeeklyPlannerFragment weeklyPlannerFragment;

    String dailyWeekly = "daily";
    FragmentThread thread;
    public static String TAG = "plannerFragment";



    public static PlannerFragment newInstance() {
        return new PlannerFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        this.context = context;

        if(context instanceof TabLayout.OnTabSelectedListener) {
            listener = (TabLayout.OnTabSelectedListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        if(context != null)
        {
            context = null;
            listener = null;
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.planner_fragment, container, false);

        dailyPlannerFragment = new DailyPlannerFragment();
        weeklyPlannerFragment = new WeeklyPlannerFragment();
        getChildFragmentManager().beginTransaction().replace(R.id.container, dailyPlannerFragment).commit();

        initUI(rootView);
        return rootView;
    }

    private void initUI(ViewGroup rootView) {
        todayDate(rootView);
        clickResetButton(rootView);
        setDailyWeekly(rootView);
    }
    private void setXMLToggle(boolean isViewClicked, ViewGroup rootView)
    {
        TextView DailyText = rootView.findViewById(R.id.Daily);
        TextView WeeklyText = rootView.findViewById(R.id.Weekly);

        if(isViewClicked) {
            DailyText.setTextColor(getResources().getColor(R.color.gray));
            DailyText.setBackgroundResource(0);
            WeeklyText.setTextColor(getResources().getColor(R.color.white));
            WeeklyText.setBackgroundResource(R.drawable.switch_btn_bg_on);
            dailyWeekly = "weekly";
        }
        else {
            DailyText.setTextColor(getResources().getColor(R.color.white));
            DailyText.setBackgroundResource((R.drawable.switch_btn_bg_on));
            WeeklyText.setTextColor(getResources().getColor(R.color.gray));
            WeeklyText.setBackgroundResource(0);
            dailyWeekly = "daily";
        }
    }


    public void todayDate(ViewGroup rootView) {
        TextView today = rootView.findViewById(R.id.today);
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM월dd일");
        today.setText(dateFormat.format(currentTime));
        Log.d("Date", dateFormat.format(currentTime).toString());
    }
    public void clickResetButton(ViewGroup rootView) {
        Button resetButton = rootView.findViewById(R.id.reset_button);
        resetButton.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("안내");
            builder.setMessage("식단표를 초기화하시겠습니까?");
            DailyPlannerFragment dailyPlannerFragment = new DailyPlannerFragment();
            TextView breakfastCategorie = rootView.findViewById(R.id.breakfastCategorie);
            TextView lunchCategorie = rootView.findViewById(R.id.lunchCategorie);
            TextView dinnerCategorie = rootView.findViewById(R.id.dinnerCategorie);
            TextView breakfastMenu = rootView.findViewById(R.id.breakfastMenu);
            TextView lunchMenu = rootView.findViewById(R.id.lunchMenu);
            TextView dinnerMenu = rootView.findViewById(R.id.dinnerMenu);


            builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    for(String table : DatabaseName.TABLE) {
                        thread = new FragmentThread(table);
                        thread.start();
                    }

                    breakfastCategorie.setText("카테고리");
                    lunchCategorie.setText("카테고리");
                    dinnerCategorie.setText("카테고리");

                    breakfastMenu.setText("메인메뉴\n서브메뉴\n서브메뉴");
                    lunchMenu.setText("메인메뉴\n서브메뉴\n서브메뉴");
                    dinnerMenu.setText("메인메뉴\n서브메뉴\n서브메뉴");

                    Toast.makeText(context, "초기화가 완료되었습니다.", Toast.LENGTH_SHORT).show();
                }
            });

            builder.setNeutralButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        });
    }

    public void setDailyWeekly(ViewGroup rootView) {
        TextView DailyText = rootView.findViewById(R.id.Daily);
        TextView WeeklyText = rootView.findViewById(R.id.Weekly);

        DailyText.setOnClickListener(v ->
        {
            setXMLToggle(false, rootView);
            getChildFragmentManager().beginTransaction().replace(R.id.container, dailyPlannerFragment).commitAllowingStateLoss();
        });
        WeeklyText.setOnClickListener(v ->
        {
            setXMLToggle(true, rootView);
            getChildFragmentManager().beginTransaction().replace(R.id.container, weeklyPlannerFragment).commitAllowingStateLoss();
        });
    }

    public void resetData(String table) {
        println("resetData called");
        String sql = "delete from " + table;
        Log.d(TAG, "sql : "+ sql);
        PlannerDatabase database = PlannerDatabase.getInstance(context);
        database.exeSQL(sql);
    }

    public void println(String text){
        Log.d(TAG, text);
    }

    private class FragmentThread extends Thread {
        String table = "";
        public FragmentThread(String table) {
            this.table = table;
        }

        public void run() {
            println("FragmentThread called");
            try {
                resetData(table);
            } catch (Exception e) {
                Log.e(TAG, "Exception in Thread", e);
            }
        }
    }
}
