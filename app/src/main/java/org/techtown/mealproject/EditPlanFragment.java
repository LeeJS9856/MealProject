package org.techtown.mealproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
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

public class EditPlanFragment extends Fragment{

    Context context;
    RecyclerView editPlanRecyView;
    ItemAdapter itemAdapter;
    ItemTouchHelper helper;
    private final String TAG = this.getClass().getSimpleName();
    private List<String> weeklist = new ArrayList<>(List.of("일요일", "월요일", "화요일", "수요일", "목요일", "금요일"));
    private List<String> timelist = new ArrayList<>(List.of("조식", "중식", "석식"));
    private Spinner weekSpinner, timeSpinner;
    private SpinnerAdapter weekAdapter, timeAdapter;

//여기 더 추가할것
    public static EditPlanFragment newInstance() {
        return new EditPlanFragment();
    }

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
        loadPlannerListData();
        return rootView;
    }

    public void initUI(ViewGroup rootView){
        weekSpinner = rootView.findViewById(R.id.WeekSpinner);
        timeSpinner = rootView.findViewById(R.id.TimeSpinner);

        initSpinner(weekAdapter, weekSpinner, weeklist);
        initSpinner(timeAdapter, timeSpinner, timelist);

        initRecyView(rootView);
        addCardView(rootView);
        initBackButton(rootView);
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
                int listCount = 0;
                plannerList.add(new Planner(0,"", "", "메인메뉴", "카테고리", "메뉴"));
                itemAdapter.addItem(plannerList.get(listCount));
                Log.d(TAG, "Planner = " + recordCount());
                editPlanRecyView.setAdapter(itemAdapter);
                addPlan(plannerList.get(listCount), recordCount(), context);
                listCount++;
            }
        });
    }


    public void println(String data) {
        Log.d(TAG, data);
    }


    public void initBackButton(ViewGroup rootView) {
        Button backbutton = rootView.findViewById(R.id.backButton);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).replaceFragment(PlannerFragment.newInstance());
            }
        });
    }

    private void addPlan(Planner item, int id, Context context) {

        String mainSub = item.getMainsub();
        String categorie = item.getCategorie();
        String menu = item.getMenu();

        String sql = "insert into " + PlannerDatabase.TABLE_PLANNER +
                " (_id, week, time, mainSub, categorie, menu) values (" +
                "'"+ id + "', " +
                "'"+ "" + "', " +
                "'"+ "" + "', " +
                "'"+ mainSub + "', " +
                "'"+ categorie + "', " +
                "'"+ menu + id + "')";
        Log.d(TAG, "sql : "+ sql);
        PlannerDatabase database = PlannerDatabase.getInstance(context);
        database.exeSQL(sql);
    }


    @SuppressLint("NotifyDataSetChanged")
    public int loadPlannerListData() {
        println("loadPlanListData called.");
        String sql = "select _id, week, time, mainSub, categorie, menu from " + PlannerDatabase.TABLE_PLANNER;

        int recordCount = -1;

        PlannerDatabase database = PlannerDatabase.getInstance(context);
        database.open();
        if(database != null) {
            Cursor outCursor = database.rawQuery(sql);
            recordCount = outCursor.getCount();
            println("record count : " + recordCount + "\n");

            ArrayList<Planner> items = new ArrayList<Planner>();
            for(int i = 0;i<recordCount;i++) {
                outCursor.moveToNext();

                int _id = outCursor.getInt(0);
                String week = outCursor.getString(1);
                String time = outCursor.getString(2);
                String mainSub = outCursor.getString(3);
                String categorie = outCursor.getString(4);
                String menu = outCursor.getString(5);

                println("#" + i + " -> " + _id + ", " + mainSub + ", " + categorie + ", " + menu);
                items.add(new Planner(_id, week, time, mainSub, categorie, menu));
            }

            outCursor.close();

            itemAdapter.setItems(items);
            itemAdapter.notifyDataSetChanged();
        }
        return recordCount;
    }

    public int recordCount() {
        String sql = "select _id, week, time, mainSub, categorie, menu from " + PlannerDatabase.TABLE_PLANNER;

        int recordCount = -1;

        PlannerDatabase database = PlannerDatabase.getInstance(context);
        if(database != null) {
            Cursor outCursor = database.rawQuery(sql);
            recordCount = outCursor.getCount();
            println("record count : " + recordCount + "\n");
            outCursor.close();
        }
        return recordCount;
    }
}
