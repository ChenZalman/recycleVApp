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

    private ArrayList<DataModel> dataSet;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private CustomeAdapter adapter;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        adapter = new CustomeAdapter(dataSet, listener);
        recyclerView.setAdapter(adapter);

        editText.addTextChangedListener(new TextWatcher() {
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

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void filter(String text) {
        // creating a new array list to filter data
        ArrayList<DataModel> filteredList = new ArrayList<>();

        // running a for loop to compare elements
        for (DataModel item : dataSet) {
            // checking if the entered string matches any item of our recycler view
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                // adding matched item to the filtered list
                filteredList.add(item);
            }
        }

        if (filteredList.isEmpty()) {
            // displaying a toast message if no data found
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();
            adapter.filterList(filteredList);
        } else {
            // passing the filtered list to the adapter class
            adapter.filterList(filteredList);
        }
    }
}








