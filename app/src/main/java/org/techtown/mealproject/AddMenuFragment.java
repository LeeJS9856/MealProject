package org.techtown.mealproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class AddMenuFragment extends Fragment {
    public static final String TAG = "AddMenuFragment";

    public static String choicedTable = DatabaseName.TABLE_BANCHAN;
    Context context;
    TabLayout.OnTabSelectedListener listener;
    RecyclerView addMenuRecyclerView;
    MenuItemAdapter menuItemAdapter;

    String choicedMainSub = "메인메뉴";
    String choicedCategorie = "반찬";

    int listCount = 0;


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
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.add_menu_fragment, container, false);
        initUI(rootView);
        loadPlannerListData(choicedTable);
        return rootView;
    }


    private void initUI(ViewGroup rootView) {
        initRecyView(rootView);
        initAddMenu(rootView);
        setMainSubButton(rootView);
        setCategorieButton(rootView);

    }

    public void initRecyView(ViewGroup rootView) {
        addMenuRecyclerView = rootView.findViewById(R.id.add_menu_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        menuItemAdapter = new MenuItemAdapter(context);
        addMenuRecyclerView.setLayoutManager(layoutManager);
        addMenuRecyclerView.setAdapter(menuItemAdapter);
    }

    void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public void initAddMenu(ViewGroup rootView) {
        Button addMenuButton = rootView.findViewById(R.id.add_menu_button);
        EditText editText = rootView.findViewById(R.id.editText);

        List<Planner> menuList = new ArrayList<>();

        addMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText.length()!=0) {

                    String enterdMenu = editText.getText().toString();
                    menuList.add(new Planner(choicedMainSub, choicedCategorie, enterdMenu));
                    menuItemAdapter.addItem(menuList.get(listCount));
                    addMenuRecyclerView.setAdapter(menuItemAdapter);
                    addMenu(choicedTable,menuList.get(listCount), recordCount(choicedTable), context);
                    editText.setText(null);
                    hideKeyboard();
                    listCount++;
                }
                else {
                    Toast.makeText(getContext(), "메뉴를 입력하세요", Toast.LENGTH_LONG).show();
                }
            }
        });

        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_ENTER) {
                    addMenuButton.callOnClick();
                } else if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_DEL)) {
                    String str = editText.getText().toString();
                    if (editText.length() > 0) {
                        str = str.substring(0, str.length() - 1);
                        editText.setText(str);
                        editText.setSelection(editText.length());
                    }
                }

                return true;
            }
        });

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if(actionId == EditorInfo.IME_ACTION_DONE) {
                    addMenuButton.callOnClick();
                    handled = true;
                }
                return handled;
            }
        });
    }

    public void setMainSubButton(ViewGroup rootView) {
        Button mainMenuButton = rootView.findViewById(R.id.main_menu_button);
        Button subMenuButton = rootView.findViewById(R.id.sub_menu_button);
        View mainMenuView = rootView.findViewById(R.id.mainMenuView);
        View subMenuView = rootView.findViewById(R.id.subMenuView);

        Button main_banchan = rootView.findViewById(R.id.Main_banchan);
        Button main_bread = rootView.findViewById(R.id.Main_bread);
        Button main_noodle = rootView.findViewById(R.id.Main_noodle);
        Button main_bunsic = rootView.findViewById(R.id.Main_bunsic);
        Button main_bap = rootView.findViewById(R.id.Main_bap);

        Button sub_side = rootView.findViewById(R.id.Sub_side);
        Button sub_soup = rootView.findViewById(R.id.Sub_soup);
        Button sub_busic = rootView.findViewById(R.id.Sub_busic);

        LinearLayout mainLayout1 = rootView.findViewById(R.id.mainLayout1);
        LinearLayout mainLayout2 = rootView.findViewById(R.id.mainLayout2);
        LinearLayout subLayout1 = rootView.findViewById(R.id.subLayout1);
        LinearLayout subLayout2 = rootView.findViewById(R.id.subLayout2);

        mainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainMenuButton.setTextColor(getResources().getColor(R.color.white));
                mainMenuView.setVisibility(View.VISIBLE);
                subMenuButton.setTextColor(getResources().getColor(R.color.customSky));
                subMenuView.setVisibility(View.INVISIBLE);
                mainLayout1.setVisibility(View.VISIBLE);
                mainLayout2.setVisibility(View.VISIBLE);
                subLayout1.setVisibility(View.INVISIBLE);
                subLayout2.setVisibility(View.INVISIBLE);

                unchoicedButton(main_bap);
                unchoicedButton(main_bread);
                unchoicedButton(main_bunsic);
                unchoicedButton(main_noodle);
                choicedButton(main_banchan);

                choicedMainSub = "메인메뉴";
                choicedCategorie = "반찬";
                choicedTable = DatabaseName.TABLE_BANCHAN;
                println(choicedMainSub + ", 카테고리 : " + choicedCategorie);

                loadPlannerListData(choicedTable);
            }
        });
        subMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainMenuButton.setTextColor(getResources().getColor(R.color.customSky));
                mainMenuView.setVisibility(View.INVISIBLE);
                subMenuButton.setTextColor(getResources().getColor(R.color.white));
                subMenuView.setVisibility(View.VISIBLE);
                mainLayout1.setVisibility(View.INVISIBLE);
                mainLayout2.setVisibility(View.INVISIBLE);
                subLayout1.setVisibility(View.VISIBLE);
                subLayout2.setVisibility(View.VISIBLE);

                unchoicedButton(sub_soup);
                unchoicedButton(sub_busic);
                choicedButton(sub_side);

                choicedMainSub = "서브메뉴";
                choicedCategorie = "사이드";
                choicedTable = DatabaseName.TABLE_SIDE;
                println(choicedMainSub + ", 카테고리 : " + choicedCategorie);
                loadPlannerListData(choicedTable);
            }
        });
    }

    public void setCategorieButton(ViewGroup rootView) {
        Button main_banchan = rootView.findViewById(R.id.Main_banchan);
        Button main_bread = rootView.findViewById(R.id.Main_bread);
        Button main_noodle = rootView.findViewById(R.id.Main_noodle);
        Button main_bunsic = rootView.findViewById(R.id.Main_bunsic);
        Button main_bap = rootView.findViewById(R.id.Main_bap);

        Button sub_side = rootView.findViewById(R.id.Sub_side);
        Button sub_soup = rootView.findViewById(R.id.Sub_soup);
        Button sub_busic = rootView.findViewById(R.id.Sub_busic);

        setButton(main_banchan, main_bread, main_noodle, main_bunsic, main_bap, "반찬", DatabaseName.TABLE_BANCHAN);
        setButton(main_bread, main_noodle, main_bunsic, main_bap, main_banchan, "빵", DatabaseName.TABLE_BREAD);
        setButton(main_bunsic, main_bap, main_banchan,main_bread, main_noodle, "분식", DatabaseName.TABLE_BUNSIC);
        setButton(main_bap, main_banchan,main_bread, main_noodle, main_bunsic, "밥", DatabaseName.TABLE_BAP);
        setButton(main_noodle, main_bunsic, main_bap, main_banchan,main_bread, "면", DatabaseName.TABLE_NOODLE);

        setButton(sub_side, sub_soup, sub_busic, "사이드", DatabaseName.TABLE_SIDE);
        setButton(sub_soup, sub_busic,sub_side, "국", DatabaseName.TABLE_SOUP);
        setButton(sub_busic, sub_side, sub_soup, "부식", DatabaseName.TABLE_BUSIC);
    }

    public void choicedButton(Button button) {
        button.setTextColor(getResources().getColor(R.color.customBlue));
        button.setBackground(getResources().getDrawable(R.drawable.img_filled_bg));
    }

    public void unchoicedButton(Button button) {
        button.setTextColor(getResources().getColor(R.color.white));
        button.setBackgroundColor(getResources().getColor(R.color.transparent));
    }

    public void setButton(Button choicedButton, Button button1, Button button2, Button button3, Button button4,
                          String categorie, String table) {
        choicedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unchoicedButton(button1);
                unchoicedButton(button2);
                unchoicedButton(button3);
                unchoicedButton(button4);
                choicedButton(choicedButton);

                choicedCategorie = categorie;
                println("카테고리 -> " + choicedCategorie);
                choicedTable = table;

                loadPlannerListData(choicedTable);
            }
        });
    }

    public void setButton(Button choicedButton, Button button1, Button button2, String categorie, String table) {
        choicedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unchoicedButton(button1);
                unchoicedButton(button2);
                choicedButton(choicedButton);

                choicedCategorie = categorie;
                println("카테고리 -> " + choicedCategorie);
                choicedTable = table;

                loadPlannerListData(choicedTable);
            }
        });
    }



    @SuppressLint("NotifyDataSetChanged")
    public int loadPlannerListData(String table) {
        println("loadPlanListData called.");
        String sql = "select _id, mainSub, categorie, menu from " + table;

        int recordCount = -1;

        MenuDatabase database = MenuDatabase.getInstance(context);

        if(database != null) {
            Cursor outCursor = database.rawQuery(sql);
            recordCount = outCursor.getCount();
            println("record count : " + recordCount + "\n");

            ArrayList<Planner> items = new ArrayList<Planner>();
            for(int i = 0;i<recordCount;i++) {
                outCursor.moveToNext();
                int _id = outCursor.getInt(0);
                String mainSub = outCursor.getString(1);
                String categorie = outCursor.getString(2);
                String menu = outCursor.getString(3);

                println("#" + i + " -> " + _id + " -> " + mainSub + ", " + categorie + ", " + menu);
                items.add(new Planner(mainSub, categorie, menu));
            }

            outCursor.close();

            menuItemAdapter.setItems(items);
            menuItemAdapter.notifyDataSetChanged();
            listCount = 0;
        }
        return recordCount;
    }

    public int recordCount(String table) {
        String sql = "select _id, mainSub, categorie, menu from " + table;

        int recordCount = -1;

        MenuDatabase database = MenuDatabase.getInstance(context);
        if(database != null) {
            Cursor outCursor = database.rawQuery(sql);
            recordCount = outCursor.getCount();
            println("record count : " + recordCount + "\n");
            outCursor.close();
        }
        return recordCount;
    }

    private void addMenu(String table, Planner item, int id, Context context) {

        String mainSub = item.getMainsub();
        String categorie = item.getCategorie();
        String menu = item.getMenu();

        String sql = "insert into " + table +
                " (_id, mainSub, categorie, menu) values (" +
                "'"+ id + "', " +
                "'"+ mainSub + "', " +
                "'"+ categorie + "', " +
                "'"+ menu + "')";
        Log.d(TAG, "sql : "+ sql);
        MenuDatabase database = MenuDatabase.getInstance(context);
        database.exeSQL(sql);
    }

    private void println(String msg) {
        Log.d(TAG, msg);
    }
}



