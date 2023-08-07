package org.techtown.mealproject;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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

public class AddMenuFragment extends Fragment {
    Context context;
    TabLayout.OnTabSelectedListener listener;
    RecyclerView addMenuRecyclerView;
    MenuItemAdapter menuItemAdapter;

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
        addMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText.length()!=0) {
                    menuItemAdapter.addItem(editText.getText().toString());
                    addMenuRecyclerView.setAdapter(menuItemAdapter);
                    editText.setText(null);
                    hideKeyboard();
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

        setButton(main_banchan, main_bread, main_noodle, main_bunsic, main_bap);
        setButton(main_bread, main_noodle, main_bunsic, main_bap, main_banchan);
        setButton(main_bunsic, main_bap, main_banchan,main_bread, main_noodle);
        setButton(main_bap, main_banchan,main_bread, main_noodle, main_bunsic);
        setButton(main_noodle, main_bunsic, main_bap, main_banchan,main_bread);

        setButton(sub_side, sub_soup, sub_busic);
        setButton(sub_soup, sub_busic,sub_side);
        setButton(sub_busic, sub_side, sub_soup);
    }

    public void choicedButton(Button button) {
        button.setTextColor(getResources().getColor(R.color.customBlue));
        button.setBackground(getResources().getDrawable(R.drawable.img_filled_bg));
    }

    public void unchoicedButton(Button button) {
        button.setTextColor(getResources().getColor(R.color.white));
        button.setBackgroundColor(getResources().getColor(R.color.transparent));
    }

    public void setButton(Button choicedButton, Button button1, Button button2, Button button3, Button button4) {
        choicedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unchoicedButton(button1);
                unchoicedButton(button2);
                unchoicedButton(button3);
                unchoicedButton(button4);
                choicedButton(choicedButton);
            }
        });
    }

    public void setButton(Button choicedButton, Button button1, Button button2) {
        choicedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unchoicedButton(button1);
                unchoicedButton(button2);
                choicedButton(choicedButton);
            }
        });
    }
}



