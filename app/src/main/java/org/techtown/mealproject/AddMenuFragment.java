package org.techtown.mealproject;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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
    ItemTouchHelper helper;
    public int count = 0;

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
        addMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sampleItem = "메뉴"+count;
                menuItemAdapter.addItem(sampleItem);
                count++;
                addMenuRecyclerView.setAdapter(menuItemAdapter);
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
}

