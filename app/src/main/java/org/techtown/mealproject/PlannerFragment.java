package org.techtown.mealproject;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;
import com.nex3z.togglebuttongroup.SingleSelectToggleGroup;
import com.nex3z.togglebuttongroup.button.CircularToggle;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PlannerFragment extends Fragment {

    Context context;
    TabLayout.OnTabSelectedListener listener;
    DailyPlannerFragment dailyPlannerFragment;
    WeeklyPlannerFragment weeklyPlannerFragment;

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

        TextView today = rootView.findViewById(R.id.today);
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM월dd일");
        today.setText(dateFormat.format(currentTime));
        Log.d("Date", dateFormat.format(currentTime).toString());

        Button resetButton = rootView.findViewById(R.id.reset_button);
        resetButton.setOnClickListener(v -> {
            Toast.makeText(getContext(), "초기화 하시겠습니까?", Toast.LENGTH_LONG).show();
        });

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
    private void setXMLToggle(boolean isViewClicked, ViewGroup rootView)
    {
        TextView DailyText = rootView.findViewById(R.id.Daily);
        TextView WeeklyText = rootView.findViewById(R.id.Weekly);

        if(isViewClicked) {
            DailyText.setTextColor(getResources().getColor(R.color.gray));
            DailyText.setBackgroundResource(0);
            WeeklyText.setTextColor(getResources().getColor(R.color.white));
            WeeklyText.setBackgroundResource(R.drawable.switch_btn_bg_on);
        }
        else {
            DailyText.setTextColor(getResources().getColor(R.color.white));
            DailyText.setBackgroundResource((R.drawable.switch_btn_bg_on));
            WeeklyText.setTextColor(getResources().getColor(R.color.gray));
            WeeklyText.setBackgroundResource(0);
        }
    }


}
