package com.example.projectyes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

public class Page2Fragment extends Fragment {

    private List<DataItem> dataItems;
    CustomAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.ordered, container, false);
        SQLhandler sqLhandler = new SQLhandler(getContext());
        dataItems = sqLhandler.readData();
        ListView listView = rootView.findViewById(R.id.listView);

        adapter = new CustomAdapter(requireContext(), dataItems);

        listView.setAdapter(adapter);

        return rootView;
    }

    public void refreshListView() {
        SQLhandler sqLhandler = new SQLhandler(getContext());
        dataItems = sqLhandler.readData();

        adapter.clear();
        adapter.addAll(dataItems);
        adapter.notifyDataSetChanged();
    }
}