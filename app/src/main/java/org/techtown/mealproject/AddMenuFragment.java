package org.techtown.mealproject;

import android.content.Context;
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
import android.widget.TextView;

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

        Button addMenuButton = rootView.findViewById(R.id.add_menu_button);
        EditText editText = rootView.findViewById(R.id.editText);
        addMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuItemAdapter.addItem(editText.getText().toString());
                addMenuRecyclerView.setAdapter(menuItemAdapter);
                editText.setText(null);
                hideKeyboard();
            }
        });

        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                switch (keyCode) {
                    case KeyEvent.KEYCODE_ENTER:
                        addMenuButton.callOnClick();
                        break;
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
}



