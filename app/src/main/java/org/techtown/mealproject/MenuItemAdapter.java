package org.techtown.mealproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class MenuItemAdapter extends RecyclerView.Adapter<MenuItemAdapter.ItemViewHolder> implements ItemTouchHelperListener {
    ArrayList<String> item = new ArrayList<>();
    Context context;

    public MenuItemAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.add_menu_cardview, parent, false);
        return new ItemViewHolder(v);
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
    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public void addItem(String menuItem) {
        item.add(menuItem);
    }

    @Override
    public void onItemClick(int position) {
        item.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void onItemSwipe(int position) {
        item.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public boolean onItemMove(int from_position, int to_position) {
        String menuItem = item.get(from_position);
        item.remove(from_position);
        item.add(to_position,menuItem);
        notifyItemMoved(from_position,to_position);
        return true;
    }

    public void removeItem(int position) {
        item.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, item.size());
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        CardView itemCard;
        TextView menuText;
        Button deleteCardButton;
        public ItemViewHolder(View itemView) {
            super(itemView);
            itemCard = itemView.findViewById(R.id.add_menu_cardView);
            menuText = itemView.findViewById(R.id.MenuTextView);
            deleteCardButton = itemView.findViewById(R.id.deleteCardViewButton);


        }

        public void onBind(String menuItem) {
            menuText.setText(menuItem);
        }


    }

}
