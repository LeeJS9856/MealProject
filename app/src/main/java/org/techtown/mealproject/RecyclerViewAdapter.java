package org.techtown.mealproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;
import org.techtown.mealproject.R;
import org.techtown.mealproject.SpinnerAdapter;

import java.util.ArrayList;


public class RecyclerViewAdapter extends RecyclerView.Adapter<Holder> {

    ArrayList<Planner> items = new ArrayList<Planner>();

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.edit_plan_cardview, parent, false);
        return new Holder(view) {
        };
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(Planner item) {
        items.add(item);
    }


}

class Holder extends RecyclerView.ViewHolder {
    Spinner mainSubSpinner;
    Spinner categorieSpinner;
    Spinner menuSpinner;
    public Holder(@NonNull View itemView) {
        super(itemView);
        mainSubSpinner = itemView.findViewById(R.id.MainSubSpinner);
        categorieSpinner = itemView.findViewById(R.id.CategorieSpinner);
        menuSpinner = itemView.findViewById(R.id.MenuSpinner);
    }
}
