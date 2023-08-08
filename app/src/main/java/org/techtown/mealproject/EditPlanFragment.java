package org.techtown.mealproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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


    DatabaseHleper dbHelper;
    SQLiteDatabase database;
    String tableName;
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
        addCardView(rootView);

        executeQuery();
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

    public void addCardView(ViewGroup rootView) {
        Button addCardButton = rootView.findViewById(R.id.addCardviewBotton);
        List<Planner> plannerList = new ArrayList<>();
        addCardButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                plannerList.add(new Planner(0,"일요일", "조식", "메인메뉴", "카테고리", "메뉴"+CardViewCount));
                itemAdapter.addItem(plannerList.get(CardViewCount));
                Log.d(TAG, "Planner= "  + ", CardViewCount = " + CardViewCount);
                CardViewCount++;
                editPlanRecyView.setAdapter(itemAdapter);
            }
        });
    }

    private void createDatabase(String name) {
        println("createDatabase 호출됨.");

        dbHelper = new DatabaseHleper(context);
        database = dbHelper.getWritableDatabase();

        println("데이터베이스 생성함: " + name);
    }

    public void println(String data) {
        Log.d("myTagr", data);
    }

    public void executeQuery() {
        println("executeQuery 호출됨.");

        Cursor cursor = database.rawQuery("select _id, week, time, mainSub, categorie, menu", null);
        int recordCount = cursor.getCount();
        println("레코드 개수: "+recordCount);

        for (int i=0;i<recordCount;i++) {
            cursor.moveToNext();
            int id = cursor.getInt(0);
            String week = cursor.getString(1);
            String time = cursor.getString(2);
            String mainSub = cursor.getString(3);
            String categorie = cursor.getString(4);
            String menu = cursor.getString(5);

            println("레코드#"+i+" : "+ id + ", "+week+", "+time+", "+mainSub+", "+categorie+", "+menu);
        }
        cursor.close();
    }
}
