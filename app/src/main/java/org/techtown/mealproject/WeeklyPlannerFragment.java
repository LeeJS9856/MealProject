package org.techtown.mealproject;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class WeeklyPlannerFragment extends Fragment {
    Context context;
    TabLayout.OnTabSelectedListener listener;
    private final String TAG = "WeeklyPlannerFragment";

    Button sunBre, sunLun, sunDin, monBre, monLun, monDin, tueBre, tueLun, tueDin, wedBre, wedLun, wedDin,
    thuBre, thuLun, thuDin, friBre, friLun, friDin;
    ArrayList<Button> Button = new ArrayList<>();
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
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.weekly_planner_fragment, container, false);

        findID(rootView);
        initUI(rootView);

        return rootView;
    }


    private void initUI(ViewGroup rootView) {
        initClickEvent(rootView);
        initButtonMenu();
    }

    public void initClickEvent(ViewGroup rootView) {
        clickPlanEvent(sunBre, "일요일", "조식");
        clickPlanEvent(sunLun, "일요일", "중식");
        clickPlanEvent(sunDin, "일요일", "석식");
        clickPlanEvent(monBre, "월요일", "조식");
        clickPlanEvent(monLun, "월요일", "중식");
        clickPlanEvent(monDin, "월요일", "석식");
        clickPlanEvent(tueBre, "화요일", "조식");
        clickPlanEvent(tueLun, "화요일", "중식");
        clickPlanEvent(tueDin, "화요일", "석식");
        clickPlanEvent(wedBre, "수요일", "조식");
        clickPlanEvent(wedLun, "수요일", "중식");
        clickPlanEvent(wedDin, "수요일", "석식");
        clickPlanEvent(thuBre, "목요일", "조식");
        clickPlanEvent(thuLun, "목요일", "중식");
        clickPlanEvent(thuDin, "목요일", "석식");
        clickPlanEvent(friBre, "금요일", "조식");
        clickPlanEvent(friLun, "금요일", "중식");
        clickPlanEvent(friDin, "금요일", "석식");
    }


    public void initButtonMenu() {
        for(int i=0;i<Button.size();i++) {
            loadButtonMenu(DatabaseName.TABLE.get(i+1), Button.get(i));
        }
    }
    public int loadButtonMenu(String table, Button buttonText) {
        println("loadButtonMenu called.");
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
                    buttonText.setText(menu);
                    text = menu;
                }
                else {
                    text = text + "\n" + menu;
                    buttonText.setText(text);
                }
            }

            outCursor.close();
        }
        return recordCount;
    }
    public void clickPlanEvent(Button button, String week, String time) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("week", week);
                bundle.putString("time", time);
                bundle.putString("fragment", "weekly");
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

    public void findID(ViewGroup rootView) {
        sunBre = rootView.findViewById(R.id.sun_b);
        sunLun = rootView.findViewById(R.id.sun_l);
        sunDin = rootView.findViewById(R.id.sun_d);
        monBre = rootView.findViewById(R.id.mon_b);
        monLun = rootView.findViewById(R.id.mon_l);
        monDin = rootView.findViewById(R.id.mon_d);
        tueBre = rootView.findViewById(R.id.tue_b);
        tueLun = rootView.findViewById(R.id.tue_l);
        tueDin = rootView.findViewById(R.id.tue_d);
        wedBre = rootView.findViewById(R.id.wed_b);
        wedLun = rootView.findViewById(R.id.wed_l);
        wedDin = rootView.findViewById(R.id.wed_d);
        thuBre = rootView.findViewById(R.id.thu_b);
        thuLun = rootView.findViewById(R.id.thu_l);
        thuDin = rootView.findViewById(R.id.thu_d);
        friBre = rootView.findViewById(R.id.fri_b);
        friLun = rootView.findViewById(R.id.fri_l);
        friDin = rootView.findViewById(R.id.fri_d);
        Button.add(sunBre);
        Button.add(sunLun);
        Button.add(sunDin);
        Button.add(monBre);
        Button.add(monLun);
        Button.add(monDin);
        Button.add(tueBre);
        Button.add(tueLun);
        Button.add(tueDin);
        Button.add(wedBre);
        Button.add(wedLun);
        Button.add(wedDin);
        Button.add(thuBre);
        Button.add(thuLun);
        Button.add(thuDin);
        Button.add(friBre);
        Button.add(friLun);
        Button.add(friDin);
    }
    public void println(String text) {
        Log.d(TAG, text);
    }
}
