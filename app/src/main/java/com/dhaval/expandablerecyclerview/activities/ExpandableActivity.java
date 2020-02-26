package com.dhaval.expandablerecyclerview.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dhaval.expandablerecyclerview.R;
import com.dhaval.expandablerecyclerview.adapters.ExpandableAdapter;
import com.dhaval.expandablerecyclerview.data.Data;
import com.dhaval.expandablerecyclerview.expandable.ExpandableRecyclerAdapter;
import com.dhaval.expandablerecyclerview.models.DataModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by Dhaval Jayani on 26/02/2020.
 */

public class ExpandableActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ExpandableAdapter expandableAdapter;
    private int listCollapsedIndex = -1;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        expandableAdapter.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        expandableAdapter.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandable);

        initialization();
    }

    private void initialization() {
        recyclerView = findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        Gson gson = new GsonBuilder().create();
        DataModel model = gson.fromJson(Data.dataObject, DataModel.class);

        setDataInAdapter(model);
    }

    private void setDataInAdapter(final DataModel model) {
        expandableAdapter = new ExpandableAdapter(ExpandableActivity.this, model.getDataList());
        expandableAdapter.setExpandCollapseListener(new ExpandableRecyclerAdapter.ExpandCollapseListener() {
            @Override
            public void onListItemExpanded(int position) {

                if (listCollapsedIndex != position && listCollapsedIndex != -1) {
                    //Collapsed Previous List Section
                    expandableAdapter.collapseParent(listCollapsedIndex);
                    //Expand The Selected Section List
                    expandableAdapter.expandParent(position);

                    listCollapsedIndex = position;
                } else {
                    expandableAdapter.expandParent(position);
                    listCollapsedIndex = position;
                }
            }

            @Override
            public void onListItemCollapsed(int position) {

                if (listCollapsedIndex == -1) {
                    listCollapsedIndex = position;
                } else {
                    expandableAdapter.collapseParent(position);
                    listCollapsedIndex = -1;
                }
            }
        });

        recyclerView.setAdapter(expandableAdapter);
        expandableAdapter.notifyDataSetChanged();
    }
}
