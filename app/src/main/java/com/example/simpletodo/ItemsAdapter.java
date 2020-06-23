package com.example.simpletodo;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

//Adapter for displaying data from the model into a row in the recycler view
public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    public interface OnLongClickListener {
        //so that MainActivity will know which position had a long click (and needs to be removed)
        void onItemLongClicked(int position);
    }

    List<String> items;
    OnLongClickListener longClickListener;

    public ItemsAdapter(List<String> items, OnLongClickListener longClickListener) {
        this.items = items;
        this.longClickListener = longClickListener;
    }

    @NonNull
    @Override
    //creates each view
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Use layout inflater to inflate a view
        View todoView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent,false);

        //Then wrap it inside a View Holder and return the view
        return new ViewHolder(todoView);
    }

    @Override
    //binds data to a particular view holder
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        //first grab the item at position
        String item = items.get(i);
        //bind item into the input viewHolder
        viewHolder.bind(item); //method created in ViewHolder class

    }

    @Override
    //Tells the recycler view how many items are in the list
    public int getItemCount() {
        return items.size(); //arraylist will carry the length
    }

    //View holder: a container to provide easy access to views that represent each row of the list

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(android.R.id.text1);
        }

        //Update the view inside of the viewHolder with the data of item
        public void bind(String item) {
            tvItem.setText(item);
            tvItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    //Notifies the listener on which position was long pressed (to then remove it)
                    longClickListener.onItemLongClicked(getAdapterPosition());
                    //return true so that the longClick is consumed
                    return true;
                }
            });
        }
    }
}
