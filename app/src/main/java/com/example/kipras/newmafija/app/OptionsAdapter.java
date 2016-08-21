package com.example.kipras.newmafija.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.kipras.newmafija.R;

import java.util.List;

public class OptionsAdapter extends ArrayAdapter<Options> {

    private int layoutResource;

    public OptionsAdapter(Context context, int layoutResource, List<Options> threeStringsList) {
        super(context, layoutResource, threeStringsList);
        this.layoutResource = layoutResource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            view = layoutInflater.inflate(layoutResource, null);
        }

        Options opts = getItem(position);

        if (opts != null) {
            TextView roleTextView = (TextView) view.findViewById(R.id.role);
            TextView numberTextView = (TextView) view.findViewById(R.id.number);

            if (roleTextView != null) {
                roleTextView.setText(opts.getRole());
            }

            if (numberTextView != null) {
                numberTextView.setText(opts.getNumber());
            }
        }

        return view;
    }
}