package org.techtown.mealproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;


public class MenuItemAdapter extends RecyclerView.Adapter<MenuItemAdapter.ItemViewHolder> implements ItemTouchHelperListener {
    private static final String TAG = "MenuItemAdapter";
    ArrayList<Planner> item = new ArrayList<>();
    Context context;

    String choicedMainSub = "메인메뉴";
    String choicedCategorie = "반찬";
    int getPosition = 0;

    public MenuItemAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.add_menu_cardview, parent, false);
        return new ItemViewHolder(v, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder,int position) {
        holder.onBind(item.get(position));
        holder.deleteCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeItem(holder.getAdapterPosition());
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choicedMainSub = AddMenuFragment.choicedMainSub;
                choicedCategorie = AddMenuFragment.choicedCategorie;
                getPosition = AddMenuFragment.position;
                holder.clickCardViewEvent(getPosition,choicedMainSub, choicedCategorie, item.get(holder.getAdapterPosition()).getMenu());
            }
        });
    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public void addItem(Planner menuItem) {
        item.add(menuItem);
    }

    public void setItems(ArrayList<Planner> items) {
        this.item = items;
    }

    @Override
    public void onItemClick(int position) {
    }

    @Override
    public void onItemSwipe(int position) {
        item.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public boolean onItemMove(int from_position, int to_position) {
        Planner menuItem = item.get(from_position);
        item.remove(from_position);
        item.add(to_position,menuItem);
        notifyItemMoved(from_position,to_position);
        return true;
    }

    public void removeItem(int position) {
        item.remove(position);
        notifyItemRemoved(position);
        deletePlan(position);


        for(int i=position;i<item.size();i++) {
            modifyID(i , i+1);
        }
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        CardView itemCard;
        Context context;
        TextView menuText;

        Button deleteCardButton;
        public ItemViewHolder(View itemView, Context context) {
            super(itemView);
            this.context = context;
            itemCard = itemView.findViewById(R.id.add_menu_cardView);
            menuText = itemView.findViewById(R.id.MenuTextView);
            deleteCardButton = itemView.findViewById(R.id.deleteCardViewButton);
        }

        public void onBind(Planner menuItem) {
            menuText.setText(menuItem.getMenu());
        }


        public void clickCardViewEvent(int position, String mainSub, String categorie, String menu) {
            Bundle bundle = new Bundle();
            bundle.putString("categorie", categorie);
            bundle.putString("menu", menu);
            bundle.putString("mainsub", mainSub);
            bundle.putInt("position", position);
            MainActivity activity = (MainActivity) context;
            FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
            EditPlanFragment editPlanFragment = new EditPlanFragment();
            editPlanFragment.setArguments(bundle);
            transaction.replace(R.id.container, editPlanFragment);
            transaction.commit();
            BottomNavigationView bottomNavigationView = activity.findViewById(R.id.bottom_navigation);
            bottomNavigationView.setSelectedItemId(R.id.addMenuTab);
        }
    }

    private void deletePlan(int id) {
        println("deletePlan called.");
        String sql = "delete from " + AddMenuFragment.choicedTable +
                " where " +
                " _id = " + id;

        Log.d(TAG, "sql : " + sql);
        MenuDatabase database = MenuDatabase.getInstance(context);
        database.exeSQL(sql);
    }

    private void modifyID(int newNum, int oldNum) {
        String sql = "update " + AddMenuFragment.choicedTable +
                " set " +
                " _id = " + newNum +
                " where " +
                " _id = " + oldNum;

        Log.d(TAG, "sql : " + sql);
        MenuDatabase database = MenuDatabase.getInstance(context);
        database.exeSQL(sql);
    }



    private void println(String msg) {
        Log.d(TAG, msg);
    }
}
