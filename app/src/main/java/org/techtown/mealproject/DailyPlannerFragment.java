package org.techtown.mealproject;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.tabs.TabLayout;

public class DailyPlannerFragment extends Fragment {
    Context context;
    TabLayout.OnTabSelectedListener listener;

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
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.daily_planner_fragment, container, false);

        initUI(rootView);

        return rootView;
    }


    private void initUI(ViewGroup rootView) {

    }
}
