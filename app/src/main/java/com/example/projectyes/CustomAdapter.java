package com.example.projectyes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CustomAdapter extends ArrayAdapter<DataItem> {

    public CustomAdapter(Context context, List<DataItem> dataItems) {
        super(context, 0, dataItems);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        DataItem dataItem = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.singleitem, parent, false);
        }

        TextView textView = convertView.findViewById(R.id.textView);

        if (dataItem != null) {
            String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(Calendar.getInstance().getTime());
            textView.setText(dataItem.getId() + " \n Email: \n " + dataItem.getEmail() + " \n Ilość zamówionych gitar: " + dataItem.getAmount() + " \n Cena: " +  dataItem.getPrice() + " \n Data zamówienia: " + timeStamp);
        }

        return convertView;
    }


}
