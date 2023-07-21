package org.techtown.mealproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.time.DayOfWeek;
import java.util.List;

public class DayOfWeekAdapter extends BaseAdapter {

    private final List<String> weeklist;
    private final LayoutInflater inflater;
    private String text;

    public DayOfWeekAdapter(Context context , List<String> weeklist) {
        this.weeklist = weeklist;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return weeklist != null ? weeklist.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return weeklist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if(view == null)
            view = inflater.inflate(R.layout.spinner_layout, viewGroup, false);
        if(weeklist != null) {
            text = weeklist.get(position);
            ((TextView) view.findViewById(R.id.DayOfWeek)).setText(text);
        }
        return view;
    }

    @Override
    public View getDropDownView(int position, View view, ViewGroup viewGroup) {
        if (view == null)
            view = inflater.inflate(R.layout.spinner_inner_layout, viewGroup, false);
        if (weeklist != null) {
            text = weeklist.get(position);
            ((TextView) view.findViewById(R.id.spinner_inner_text)).setText(text);
        }

        return view;
    }

    public String getItem() {
        return text;
    }
}
