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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class EditPlanFragment extends Fragment {

    Context context;
    TabLayout.OnTabSelectedListener listener;

    RecyclerView editPlanRecyView;
    RecyclerViewAdapter recyclerViewAdapter;
    private final String TAG = this.getClass().getSimpleName();
    private List<String> weeklist = new ArrayList<>(List.of("일요일", "월요일", "화요일", "수요일", "목요일", "금요일"));

    private List<String> timelist = new ArrayList<>(List.of("조식", "중식", "석식"));
    private List<String> mainSub = new ArrayList<>(List.of("메인 메뉴", "서브 메뉴"));

    private List<String> mainCategorie = new ArrayList<>(List.of( "한식", "빵","면", "밥", "분식", "기타"));
    private List<String> subCategorie = new ArrayList<>(List.of("국", "반찬", "기타"));

    private List<String> koreanMenu = new ArrayList<>(List.of());
    private List<String> breadMenu = new ArrayList<>(List.of());
    private List<String> noodleMenu = new ArrayList<>(List.of());
    private List<String> riceMenu = new ArrayList<>(List.of());
    private List<String> flourMenu = new ArrayList<>(List.of());
    private List<String> etcMenu = new ArrayList<>(List.of());

    private List<String> soupMenu = new ArrayList<>(List.of());
    private List<String> sideMenu = new ArrayList<>(List.of());
    private Spinner weekSpinner;

    private Spinner timeSpinner;
    private SpinnerAdapter weekAdapter;
    private SpinnerAdapter timeAdapter;

    private Spinner mainSubSpinner;
    private Spinner categorieSpinner;
    private Spinner menuSpinner;

    private SpinnerAdapter mainSubAdapter;
    private SpinnerAdapter categorieAdapter;
    private SpinnerAdapter manuAdapter;

    private String choicedItem = "";


    private Planner[][] planner = new Planner[6][3];
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
        timeSpinner = rootView.findViewById(R.id.TimeSpinner);
        weekAdapter = new SpinnerAdapter(context, weeklist);
        timeAdapter = new SpinnerAdapter(context, timelist);


        initSpinner(weekAdapter, weekSpinner, weeklist);
        initSpinner(timeAdapter, timeSpinner, timelist);

        String text = weekSpinner.getSelectedItem().toString();
        Log.d(TAG, "initUI입니다 " + text);


        //

        editPlanRecyView = rootView.findViewById(R.id.editPlanRecyView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        editPlanRecyView.setLayoutManager(layoutManager);

        recyclerViewAdapter = new RecyclerViewAdapter();
        recyclerViewAdapter.addItem(new Planner("일요일", "조식", "메인 메뉴", "밥류", "제육볶음"));
        recyclerViewAdapter.addItem(new Planner("일요일", "조식", "메인 메뉴", "밥류", "제육볶음"));
        recyclerViewAdapter.addItem(new Planner("일요일", "조식", "메인 메뉴", "밥류", "제육볶음"));
        recyclerViewAdapter.addItem(new Planner("일요일", "조식", "메인 메뉴", "밥류", "제육볶음"));
        recyclerViewAdapter.addItem(new Planner("일요일", "조식", "메인 메뉴", "밥류", "제육볶음"));
        editPlanRecyView.setAdapter(recyclerViewAdapter);




/*
        mainSubSpinner = rootView.findViewById(R.id.MainSubSpinner);
        categorieSpinner = rootView.findViewById(R.id.CategorieSpinner);
        menuSpinner = rootView.findViewById(R.id.MenuSpinner);


        mainSubAdapter = new SpinnerAdapter(context, mainSub);
        initSpinner(mainSubAdapter, mainSubSpinner, mainSub);

        if(choicedMainSub.equals("메인 메뉴")) {
            initSpinner(rootView, categorieAdapter, categorieSpinner, mainCategorie);
            choicedCategorie = categorieAdapter.getItem();
        }
        else if (choicedMainSub.equals("서브 메뉴")) {
            initSpinner(rootView, categorieAdapter, categorieSpinner, subCategorie);
        }*/


    }

    public void initSpinner(SpinnerAdapter adapter,Spinner spinner, List<String> list) {
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

    //public String choicedSpinner(ViewGroup rootView, SpinnerAdapter adapter, Spinner spinner, Lis)
}
