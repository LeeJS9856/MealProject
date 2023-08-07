package org.techtown.mealproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class EditPlanFragment extends Fragment {

    Context context;
    RecyclerView editPlanRecyView;
    ItemAdapter itemAdapter;
    ItemTouchHelper helper;
    private final String TAG = this.getClass().getSimpleName();
    private List<String> weeklist = new ArrayList<>(List.of("일요일", "월요일", "화요일", "수요일", "목요일", "금요일"));
    private List<String> timelist = new ArrayList<>(List.of("조식", "중식", "석식"));
    private Spinner weekSpinner, timeSpinner;
    private SpinnerAdapter weekAdapter, timeAdapter;

    private String choicedItem = "";
    private int CardViewCount = 0;


//여기 더 추가할것

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
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.edit_plan_fragment, container, false);

        initUI(rootView);
        return rootView;
    }

    public void initUI(ViewGroup rootView){
        weekSpinner = rootView.findViewById(R.id.WeekSpinner);
        timeSpinner = rootView.findViewById(R.id.TimeSpinner);

        initSpinner(weekAdapter, weekSpinner, weeklist);
        initSpinner(timeAdapter, timeSpinner, timelist);

        initRecyView(rootView);
        //

        Button addCardButton = rootView.findViewById(R.id.addCardviewBotton);
        List<Planner> plannerList = new ArrayList<>();
        addCardButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                plannerList.add(new Planner("일요일", "조식", "메인메뉴", "카테고리", "메뉴"+CardViewCount));
                itemAdapter.addItem(plannerList.get(CardViewCount));
                Log.d(TAG, "Planner= "  + ", CardViewCount = " + CardViewCount);
                CardViewCount++;
                editPlanRecyView.setAdapter(itemAdapter);
            }
        });
    }

    public void initSpinner(SpinnerAdapter adapter,Spinner spinner, List<String> list) {
        adapter = new SpinnerAdapter(context, list);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String choicedItem = parent.getItemAtPosition(position).toString();
                Log.d(TAG, "선택아이템 " + choicedItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void initRecyView(ViewGroup rootView) {
        editPlanRecyView = rootView.findViewById(R.id.editPlanRecyView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        itemAdapter = new ItemAdapter(context);
        editPlanRecyView.setLayoutManager(layoutManager);
        editPlanRecyView.setAdapter(itemAdapter);

        helper = new ItemTouchHelper(new ItemTouchHelperCallback(itemAdapter));
        helper.attachToRecyclerView(editPlanRecyView);
    }
}
