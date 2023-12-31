package org.techtown.mealproject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.location.GnssAntennaInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.animation.AnimatableView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> implements ItemTouchHelperListener {

    ArrayList<Planner> item = new ArrayList<>();
    Context context;
    String choicedWeek = "일요일";
    String choicedTime = "조식";
    String gotFragment = "daily";
    private static final String TAG = "ItemAdapter";

    public ItemAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.edit_plan_cardview, parent, false);

        return new ItemViewHolder(v, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.onBind(item.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choicedWeek = EditPlanFragment.choicedWeek;
                choicedTime = EditPlanFragment.choicedTime;
                gotFragment = EditPlanFragment.gotFragment;
                holder.clickCardViewEvent(holder.getAdapterPosition(), choicedWeek, choicedTime, gotFragment);
            }
        });
    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public void addItem(Planner planner) {
        item.add(planner);
    }

    @Override
    public void onItemClick(int position) {

    }

    public void setItems(ArrayList<Planner> items) {
        this.item = items;
    }


    @Override
    public boolean onItemMove(int from_position, int to_position) {
        Planner planner = item.get(from_position);
        item.remove(from_position);
        item.add(to_position,planner);
        notifyItemMoved(from_position,to_position);
        return true;
    }

    @Override
    public void onItemSwipe(int position) {
        item.remove(position);
        notifyItemRemoved(position);
        deletePlan(position);


        for(int i=position;i<item.size();i++) {
            modifyID(i , i+1);
        }
    }



    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        Context context;
        CardView itemCard;
        TextView mainsubText;
        TextView categorieText;
        TextView menuText;
        public ItemViewHolder(View itemView, Context context) {
            super(itemView);
            this.context = context;

            itemCard = itemView.findViewById(R.id.edit_plan_cardview);
            mainsubText = itemView.findViewById(R.id.MainSubText);
            categorieText = itemView.findViewById(R.id.CategorieText);
            menuText = itemView.findViewById(R.id.MenuText);
        }

        public void onBind(Planner planner) {
            mainsubText.setText(planner.getMainsub());
            categorieText.setText(planner.getCategorie());
            menuText.setText(planner.getMenu());


        }

        public void clickCardViewEvent(int position, String week, String time, String fragment) {
            Bundle bundle = new Bundle();
            bundle.putInt("position", position);
            bundle.putString("week", week);
            bundle.putString("time", time);
            bundle.putString("fragment", fragment);
            MainActivity activity = (MainActivity) context;
            BottomNavigationView bottomNavigationView = activity.findViewById(R.id.bottom_navigation);
            bottomNavigationView.setSelectedItemId(R.id.addMenuTab);
            FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
            AddMenuFragment addMenuFragment = new AddMenuFragment();
            addMenuFragment.setArguments(bundle);
            transaction.replace(R.id.container, addMenuFragment);
            transaction.commit();
        }

    }

    private void deletePlan(int id) {
        println("deletePlan called.");
        String sql = "delete from " + EditPlanFragment.choicedTable +
                " where " +
                " _id = " + id;

        Log.d(TAG, "sql : " + sql);
        PlannerDatabase database = PlannerDatabase.getInstance(context);
        database.exeSQL(sql);
    }

    private void modifyID(int newNum, int oldNum) {
        String sql = "update " + EditPlanFragment.choicedTable +
                " set " +
                " _id = " + newNum +
                " where " +
                " _id = " + oldNum;

        Log.d(TAG, "sql : " + sql);
        PlannerDatabase database = PlannerDatabase.getInstance(context);
        database.exeSQL(sql);
    }

    public void println(String data) {
        Log.d(TAG, data);
    }

}
