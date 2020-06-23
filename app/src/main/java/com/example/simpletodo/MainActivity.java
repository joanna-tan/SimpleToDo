package com.example.simpletodo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<String> items;
    Button buttonAdd;
    EditText edItem;
    RecyclerView rvItems;
    ItemsAdapter itemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set the variables to their IDs
        buttonAdd = findViewById(R.id.buttonAdd);
        edItem = findViewById(R.id.edItem);
        rvItems = findViewById(R.id.rvItems);

        //use function to load saved items
        loadItems();

        //create onLongClick to detect long press for removal
        ItemsAdapter.OnLongClickListener onLongClickListener = new ItemsAdapter.OnLongClickListener() {
            @Override
            public void onItemLongClicked(int position) {
                //Delete the item from the model
                items.remove(position);
                //Notify the adapter which position the item was deleted
                itemsAdapter.notifyItemRemoved(position);
                //Inform the user of item removal
                Toast.makeText(getApplicationContext(), "Item was removed", Toast.LENGTH_SHORT).show();
                saveItems();

            }
        };

        itemsAdapter = new ItemsAdapter(items, onLongClickListener);
        rvItems.setAdapter(itemsAdapter);
        //linear layout for vertical alignment
        rvItems.setLayoutManager(new LinearLayoutManager(this));

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String todoItem = edItem.getText().toString();
                //Add todoItem to the model
                items.add(todoItem);
                //Notify the adapter that an item is inserted
                itemsAdapter.notifyItemInserted(items.size() - 1);
                //clear the text after submission
                edItem.setText("");

                //notify the user that item has been added with Toast
                Toast.makeText(getApplicationContext(), "Item was added", Toast.LENGTH_SHORT).show();
                saveItems();


            }
        });

    }

    private File getDataFile() {
        return new File(getFilesDir(), "data.txt");
    }

    //function to load items by reading every line of date file
    private void loadItems() {
        //use fileUtils to read
        try {
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            Log.e("MainActivity", "Error reading items", e);
            items = new ArrayList<>();
        }
    }

    //function to save items by writing them into the data file
    private void saveItems() {
        try {
            FileUtils.writeLines(getDataFile(), items);
        } catch (IOException e) {
            Log.e("MainActivity", "Error writing items", e);
        }
    }
}