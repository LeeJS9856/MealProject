package org.techtown.mealproject;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class EditPlanFragment extends Fragment {

    Context context;
    TabLayout.OnTabSelectedListener listener;
    private final String TAG = this.getClass().getSimpleName();
    private List<String> weeklist = new ArrayList<>(List.of("일요일", "월요일", "화요일", "수요일", "목요일", "금요일"));

    private List<String> timeList = new ArrayList<>(List.of("조식", "중식", "석식"));
    private Spinner weekSpinner;

    private Spinner timeSpinner;
    private DayOfWeekAdapter weekAdapter;

    private DayOfWeekAdapter timeAdapter;
    private String selectedItem;

    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        this.context = context;

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
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.edit_plan_fragment, container, false);

        initUI(rootView);
        return rootView;
    }

    public void initUI(ViewGroup rootView){
        weekSpinner = rootView.findViewById(R.id.WeekSpinner);
        weekAdapter = new DayOfWeekAdapter(context, weeklist);
        timeSpinner = rootView.findViewById(R.id.TimeSpinner);
        timeAdapter = new DayOfWeekAdapter(context, timeList);


        initSpinner(rootView, weekAdapter, weekSpinner, weeklist);
        initSpinner(rootView, timeAdapter, timeSpinner, timeList);

    }

    public void initSpinner(ViewGroup rootView, DayOfWeekAdapter adapter,Spinner spinner, List<String> list) {
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedItem = adapter.getItem();
                String getItem = (String) spinner.getItemAtPosition(position);
                Log.d(TAG, "선택아이템 " + getItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
