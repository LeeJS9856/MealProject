package org.techtown.mealproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class SpinnerAdapter extends BaseAdapter {

    private final List<String> list;
    private final LayoutInflater inflater;
    private String text;

    public SpinnerAdapter(Context context , List<String> list) {
        this.list = list;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list != null ? list.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if(view == null)
            view = inflater.inflate(R.layout.spinner_layout, viewGroup, false);
        if(list != null) {
            text = list.get(position);
            ((TextView) view.findViewById(R.id.spinner_text)).setText(text);
        }
        return view;
    }

    @Override
    public View getDropDownView(int position, View view, ViewGroup viewGroup) {
        if (view == null)
            view = inflater.inflate(R.layout.spinner_inner_layout, viewGroup, false);
        if (list != null) {
            text = list.get(position);
            ((TextView) view.findViewById(R.id.spinner_inner_text)).setText(text);
        }

        return view;
    }
}
