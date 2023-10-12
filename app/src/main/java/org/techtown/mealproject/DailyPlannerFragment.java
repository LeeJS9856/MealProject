package org.techtown.mealproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DailyPlannerFragment extends Fragment {
    Context context;
    TabLayout.OnTabSelectedListener listener;
    private final String TAG = "DailyPlannerFragment";

    TextView breakfastCategorie,lunchCategorie,dinnerCategorie;
    TextView breakfastMenu,lunchMenu,dinnerMenu;
    String table_bre = "";
    String table_lun = "";
    String table_din = "";
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.daily_planner_fragment, container, false);

        findID(rootView);
        defineTable(getCurrentWeek());
        initUI(rootView);

        return rootView;
    }


    public void initUI(ViewGroup rootView) {
        initClickEvent(rootView);
        initCardViewMenu(table_bre,breakfastCategorie, breakfastMenu);
        initCardViewMenu(table_lun,lunchCategorie, lunchMenu);
        initCardViewMenu(table_din,dinnerCategorie, dinnerMenu);
    }

    public void initClickEvent(ViewGroup rootView) {
        CardView breakfastCardView = rootView.findViewById(R.id.breakfastCardView);
        CardView lunchCardView = rootView.findViewById(R.id.lunchCardView);
        CardView dinnerCardView = rootView.findViewById(R.id.dinnerCardView);
        clickPlanEvent(breakfastCardView, "조식");
        clickPlanEvent(lunchCardView, "중식");
        clickPlanEvent(dinnerCardView, "석식");
    }

    public void clickPlanEvent(CardView cardView, String time) {
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("week", getCurrentWeek());
                bundle.putString("time", time);
                bundle.putString("fragment", "daily");
                MainActivity activity = (MainActivity) context;
                BottomNavigationView bottomNavigationView = activity.findViewById(R.id.bottom_navigation);
                bottomNavigationView.setSelectedItemId(R.id.planEditTab);
                FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
                EditPlanFragment editPlanFragment = new EditPlanFragment();
                editPlanFragment.setArguments(bundle);
                transaction.replace(R.id.container, editPlanFragment);
                transaction.commit();

            }
        });
    }

    public static String getCurrentWeek() {
        Date currentDate = Calendar.getInstance().getTime();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);

        int dayOfWeekNumber = calendar.get(Calendar.DAY_OF_WEEK);
        String dayOfWeek = "";
        Log.d("tag", "오늘의 요일은 "+dayOfWeek + ", " + dayOfWeekNumber);

        switch (dayOfWeekNumber) {
            case 1: dayOfWeek = "일요일"; break;
            case 2: dayOfWeek = "월요일"; break;
            case 3: dayOfWeek = "화요일"; break;
            case 4: dayOfWeek = "수요일"; break;
            case 5: dayOfWeek = "목요일"; break;
            case 6: dayOfWeek = "금요일"; break;
            case 7: dayOfWeek = "일요일"; break;
        }
        return dayOfWeek;
    }
    public int initCardViewMenu(String table, TextView categorieText, TextView menuText) {
        println("initCardViewMenu called.");
        String sql = "select _id, week, time, mainSub, categorie, menu from " + table;

        int recordCount = -1;

        PlannerDatabase database = PlannerDatabase.getInstance(context);

        if(database != null) {
            Cursor outCursor = database.rawQuery(sql);
            recordCount = outCursor.getCount();
            println("record count : " + recordCount + "\n");
            String text = "";

            ArrayList<Planner> items = new ArrayList<Planner>();
            for(int i = 0;i<recordCount;i++) {
                outCursor.moveToNext();

                int _id = outCursor.getInt(0);
                String week = outCursor.getString(1);
                String time = outCursor.getString(2);
                String mainSub = outCursor.getString(3);
                String categorie = outCursor.getString(4);
                String menu = outCursor.getString(5);

                println("#" + i + " -> " + _id + ", " + week + ", " + time + ", " + mainSub + ", " + categorie + ", " + menu);
                items.add(new Planner(_id, week, time, mainSub, categorie, menu));



                if(i==0) {
                    categorieText.setText(categorie);
                    menuText.setText(menu);
                    text = menu;
                }
                else {
                    text = text + "\n" + menu;
                    menuText.setText(text);
                }
            }

            outCursor.close();
        }
        return recordCount;
    }

    public void findID(ViewGroup rootView) {
        breakfastCategorie = rootView.findViewById(R.id.breakfastCategorie);
        lunchCategorie = rootView.findViewById(R.id.lunchCategorie);
        dinnerCategorie = rootView.findViewById(R.id.dinnerCategorie);
        breakfastMenu = rootView.findViewById(R.id.breakfastMenu);
        lunchMenu = rootView.findViewById(R.id.lunchMenu);
        dinnerMenu = rootView.findViewById(R.id.dinnerMenu);
    }

    public void println(String data) {
        Log.d(TAG, data);
    }

    public void defineTable(String week) {
        switch (week) {
            case "일요일" :
                table_bre = DatabaseName.TABLE_SUN_BRE;
                table_lun = DatabaseName.TABLE_SUN_LUN;
                table_din = DatabaseName.TABLE_SUN_DIN;
                break;

            case "월요일" :
                table_bre = DatabaseName.TABLE_MON_BRE;
                table_lun = DatabaseName.TABLE_MON_LUN;
                table_din = DatabaseName.TABLE_MON_DIN;
                break;

            case "화요일" :
                table_bre = DatabaseName.TABLE_TUE_BRE;
                table_lun = DatabaseName.TABLE_TUE_LUN;
                table_din = DatabaseName.TABLE_TUE_DIN;
                break;

            case "수요일" :
                table_bre = DatabaseName.TABLE_WED_BRE;
                table_lun = DatabaseName.TABLE_WED_LUN;
                table_din = DatabaseName.TABLE_WED_DIN;
                break;

            case "목요일" :
                table_bre = DatabaseName.TABLE_THU_BRE;
                table_lun = DatabaseName.TABLE_THU_LUN;
                table_din = DatabaseName.TABLE_THU_DIN;
                break;

            case "금요일" :
                table_bre = DatabaseName.TABLE_FRI_BRE;
                table_lun = DatabaseName.TABLE_FRI_LUN;
                table_din = DatabaseName.TABLE_FRI_DIN;
                break;
        }
    }
}
