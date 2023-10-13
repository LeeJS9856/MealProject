package org.techtown.mealproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
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

    private String choicedItem = "";
    public static String choicedWeek = "일요일";
    public static String choicedTime = "조식";
    public static String gotFragment = "daily";

    private String gotMainSub = "";
    private String gotCategorie = "";
    private String gotMenu = "";
    private String gotWeek = "";
    private String gotTime = "";

    private int position = 0;

    public static String choicedTable = DatabaseName.TABLE_SUN_BRE;
    int listCount = 0;

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

        if(getArguments() != null) {
            gotMainSub = getArguments().getString("mainsub");
            gotCategorie = getArguments().getString("categorie");
            gotMenu = getArguments().getString("menu");
            position = getArguments().getInt("position");

            if(gotMainSub!=null && gotCategorie!=null &&gotMenu!=null)
                modifyData(choicedTable, position, gotMainSub, gotCategorie, gotMenu);

            gotWeek = getArguments().getString("week");
            gotTime = getArguments().getString("time");
            if(gotWeek != null && gotTime != null) {
                choicedWeek = gotWeek;
                choicedTime = gotTime;

                println("받은 DATA : week " + choicedWeek +", time " + choicedTime);
            }

            gotFragment = getArguments().getString("fragment");


        }
        weekSpinner = rootView.findViewById(R.id.WeekSpinner);
        timeSpinner = rootView.findViewById(R.id.TimeSpinner);

        initSpinner(weekAdapter, weekSpinner, weeklist, choicedWeek);
        initSpinner(timeAdapter, timeSpinner, timelist, choicedTime);

        return rootView;
    }

    public void initUI(ViewGroup rootView){
        initRecyView(rootView);
        addCardView(rootView);
        initBackButton(rootView);
        initSavePlanButton(rootView);
    }

    public void initSpinner(SpinnerAdapter adapter,Spinner spinner, List<String> list, String item) {
        adapter = new SpinnerAdapter(context, list);
        spinner.setAdapter(adapter);
        spinner.setSelection(getIndex(spinner, item));
        println("spinner setSelection " + item);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                choicedItem = parent.getItemAtPosition(position).toString();
                SaveChoicedItem(choicedItem);
                switchTable(choicedWeek, choicedTime);
                loadPlannerListData(choicedTable);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private int getIndex(Spinner spinner, String item) {
        for(int i = 0;i<spinner.getCount();i++) {
            if(spinner.getItemAtPosition(i).toString().equalsIgnoreCase(item)) {
                return i;
            }
        }
        return 0;
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

                plannerList.add(new Planner(0,"", "", "메인메뉴", "카테고리", "메뉴"));
                itemAdapter.addItem(plannerList.get(listCount));
                Log.d(TAG, "Planner = " + recordCount(choicedTable));
                editPlanRecyView.setAdapter(itemAdapter);
                addPlan(choicedTable,plannerList.get(listCount), recordCount(choicedTable), context);
                modifyWeek(choicedTable, choicedWeek, "");
                modifyTime(choicedTable,choicedTime, "");
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
                dropDownItem(choicedTable);
                BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottom_navigation);
                bottomNavigationView.setSelectedItemId(R.id.plannerTab);

            }
        });
    }

    public void initSavePlanButton(ViewGroup rootView) {
        Button backbutton = rootView.findViewById(R.id.savePlanButton);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity activity = (MainActivity) context;
                BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottom_navigation);
                bottomNavigationView.setSelectedItemId(R.id.plannerTab);
                if(gotFragment!=null) {
                    Bundle bundle = new Bundle();
                    bundle.putString("fragment", gotFragment);
                    FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
                    PlannerFragment plannerFragment = new PlannerFragment();
                    plannerFragment.setArguments(bundle);
                    transaction.replace(R.id.container, plannerFragment);
                    transaction.commit();
                }

            }
        });
    }

    private void addPlan(String table, Planner item, int id, Context context) {

        String mainSub = item.getMainsub();
        String categorie = item.getCategorie();
        String menu = item.getMenu();

        String sql = "insert into " + table +
                " (_id, week, time, mainSub, categorie, menu) values (" +
                "'"+ id + "', " +
                "'"+ "" + "', " +
                "'"+ "" + "', " +
                "'"+ mainSub + "', " +
                "'"+ categorie + "', " +
                "'"+ menu + "')";
        Log.d(TAG, "sql : "+ sql);
        PlannerDatabase database = PlannerDatabase.getInstance(context);
        database.exeSQL(sql);
    }


    @SuppressLint("NotifyDataSetChanged")
    public int loadPlannerListData(String table) {
        println("loadPlanListData called.");
        String sql = "select _id, week, time, mainSub, categorie, menu from " + table;

        int recordCount = -1;

        PlannerDatabase database = PlannerDatabase.getInstance(context);

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

                println("#" + i + " -> " + _id + ", " + week + ", " + time + ", " + mainSub + ", " + categorie + ", " + menu);
                items.add(new Planner(_id, week, time, mainSub, categorie, menu));
            }

            outCursor.close();

            itemAdapter.setItems(items);
            itemAdapter.notifyDataSetChanged();
            listCount = 0;
        }
        return recordCount;
    }

    public int recordCount(String table) {
        String sql = "select _id, week, time, mainSub, categorie, menu from " + table;

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

    private void modifyWeek(String table, String newWeek, String oldWeek) {
        String sql = "update " + table +
                " set " +
                " week = '" + newWeek + "'" +
                " where " +
                " week = '" + oldWeek + "'";

        Log.d(TAG, "sql : " + sql);
        PlannerDatabase database = PlannerDatabase.getInstance(context);
        database.exeSQL(sql);
    }

    private void modifyTime(String table, String newTime, String oldTime) {
        String sql = "update " + table +
                " set " +
                " time = '" + newTime + "'" +
                " where " +
                " time = '" + oldTime + "'";

        Log.d(TAG, "sql : " + sql);
        PlannerDatabase database = PlannerDatabase.getInstance(context);
        database.exeSQL(sql);
    }

    private void modifyData(String table, int position, String mainSub, String categorie, String menu) {
        String sql = "update " + table +
                " set" +
                " mainSub = '" + mainSub + "' ," +
                " categorie = '" + categorie + "' ," +
                " menu = '" + menu + "' " +
                " where" +
                " _id = " + position;

        Log.d(TAG, "sql : " + sql);
        PlannerDatabase database = PlannerDatabase.getInstance(context);
        database.exeSQL(sql);
    }

    public void SaveChoicedItem(String item) {
        if(weeklist.contains(item)) {
            choicedWeek = item;
            println("choicedWeek : " + item);
        } else if (timelist.contains(item)) {
            choicedTime = item;
            println("choicedTime : " + item);
        }
    }

    public void dropDownItem(String table) {
        String sql = "delete from " + table;

        Log.d(TAG, "sql : " + sql);
        PlannerDatabase database = PlannerDatabase.getInstance(context);
        database.exeSQL(sql);
    }

    public void switchTable(String week, String time) {
        println("switchTable called, week = " + week + " , time = " + time);

        if (week.equals("일요일") && time.equals("조식")) { choicedTable = DatabaseName.TABLE_SUN_BRE; println(choicedTable);}
        else if (week.equals("일요일") && time.equals("중식")) { choicedTable = DatabaseName.TABLE_SUN_LUN; println(choicedTable);}
        else if (week.equals("일요일") && time.equals("석식")) { choicedTable = DatabaseName.TABLE_SUN_DIN; println(choicedTable);}
        else if (week.equals("월요일") && time.equals("조식")) { choicedTable = DatabaseName.TABLE_MON_BRE; println(choicedTable);}
        else if (week.equals("월요일") && time.equals("중식")) { choicedTable = DatabaseName.TABLE_MON_LUN; println(choicedTable);}
        else if (week.equals("월요일") && time.equals("석식")) { choicedTable = DatabaseName.TABLE_MON_DIN; println(choicedTable);}
        else if (week.equals("화요일") && time.equals("조식")) { choicedTable = DatabaseName.TABLE_TUE_BRE; println(choicedTable);}
        else if (week.equals("화요일") && time.equals("중식")) { choicedTable = DatabaseName.TABLE_TUE_LUN; println(choicedTable);}
        else if (week.equals("화요일") && time.equals("석식")) { choicedTable = DatabaseName.TABLE_TUE_DIN; println(choicedTable);}
        else if (week.equals("수요일") && time.equals("조식")) { choicedTable = DatabaseName.TABLE_WED_BRE; println(choicedTable);}
        else if (week.equals("수요일") && time.equals("중식")) { choicedTable = DatabaseName.TABLE_WED_LUN; println(choicedTable);}
        else if (week.equals("수요일") && time.equals("석식")) { choicedTable = DatabaseName.TABLE_WED_DIN; println(choicedTable);}
        else if (week.equals("목요일") && time.equals("조식")) { choicedTable = DatabaseName.TABLE_THU_BRE; println(choicedTable);}
        else if (week.equals("목요일") && time.equals("중식")) { choicedTable = DatabaseName.TABLE_THU_LUN; println(choicedTable);}
        else if (week.equals("목요일") && time.equals("석식")) { choicedTable = DatabaseName.TABLE_THU_DIN; println(choicedTable);}
        else if (week.equals("금요일") && time.equals("조식")) { choicedTable = DatabaseName.TABLE_FRI_BRE; println(choicedTable);}
        else if (week.equals("금요일") && time.equals("중식")) { choicedTable = DatabaseName.TABLE_FRI_LUN; println(choicedTable);}
        else if (week.equals("금요일") && time.equals("석식")) { choicedTable = DatabaseName.TABLE_FRI_DIN; println(choicedTable);}
        else{println("올바른 대상이 아닙니다"); println(choicedTable);}
    }
}
