package org.techtown.mealproject;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.Calendar;
import java.util.Date;

public class DailyPlannerFragment extends Fragment {
    Context context;
    TabLayout.OnTabSelectedListener listener;

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

        initUI(rootView);

        return rootView;
    }


    private void initUI(ViewGroup rootView) {
        initClickEvent(rootView);
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
}
