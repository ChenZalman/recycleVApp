package com.example.recyclevapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //Declaring all variables so they can be used in all parts of the MainActivity class
    private ArrayList<DataModel> dataSet;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private CustomeAdapter adapter;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Must function calls to initiate the onCreate so we have a display to see
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setting all variables so they won't be nullPointerException
        editText = findViewById(R.id.editTextText);
        dataSet = new ArrayList<>();
        recyclerView = findViewById(R.id.resView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        for (int i = 0; i < myData.nameArray.length; i++) {
            dataSet.add(new DataModel(
                    myData.nameArray[i],
                    myData.versionArray[i],
                    myData.drawableArray[i],
                    myData.id_[i]
            ));
        }

        //This part provides the functionality of the click and long click login for the cardViews in recyclerView
        CustomeAdapter.RecyclerViewListener listener = new CustomeAdapter.RecyclerViewListener() {
            @Override
            public void onClick(View view, int position) {
                TextView textView = view.findViewById(R.id.textView);
                String text = textView.getText().toString();
                Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
            }

            @Override
            public boolean onLongClick(View view, int position) {
                TextView textView = view.findViewById(R.id.textView);
                String text = textView.getText().toString();
                Toast.makeText(MainActivity.this, text + " will be deleted from list", Toast.LENGTH_SHORT).show();
                dataSet.remove(position);
                adapter.notifyDataSetChanged();
                return false;
            }
        };

        //Added a listener to the CustomeAdapter so it can get the clicks listener
        adapter = new CustomeAdapter(dataSet, listener);
        recyclerView.setAdapter(adapter);

        editText.addTextChangedListener(new TextWatcher() {
            //Not used in the project
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!(charSequence.toString().isEmpty())) {
                    filter(charSequence.toString());
                } else {
                    adapter.filterList(dataSet);
                }
                adapter.notifyDataSetChanged();
            }

            //Not used in the project
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void filter(String text) {
        // creating a new array list to filter data, so the original dataSet is kept intact
        ArrayList<DataModel> filteredList = new ArrayList<>();

        // itirating all elements of dataSet
        for (DataModel item : dataSet) {
            // checking if the entered string matches any item of our recycler view
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                // adding matched item to the filtered list
                filteredList.add(item);
            }
        }

        //Checking if the filteredList has some elements in it
        if (filteredList.isEmpty()) {
            // displaying a toast message if no data found
            Toast.makeText(this, "No character with the entered letters found..", Toast.LENGTH_SHORT).show();
            adapter.filterList(filteredList);
        } else {
            // passing the filtered list to the CustomeAdapter
            adapter.filterList(filteredList);
        }
    }
}








