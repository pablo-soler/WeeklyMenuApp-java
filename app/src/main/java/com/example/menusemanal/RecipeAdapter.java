package com.example.menusemanal;


import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;


public class RecipeAdapter extends BaseAdapter {

    private ArrayList<Recipe> mData = new ArrayList<Recipe>();
    private LayoutInflater mInflater;




    public RecipeAdapter(Context context, List<List<Recipe>> bd) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mData = bdToDataList(bd);
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Recipe getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void updateReceiptsList(List<List<Recipe>> newlist) {
        if(mData==null){
            mData = new  ArrayList<Recipe>();
        }
        mData.clear();
        mData.addAll(bdToDataList(newlist));
        notifyDataSetChanged();
        Log.d("SAVED", "update view");
    }


    public ArrayList<Recipe> bdToDataList(List<List<Recipe>> bd){
        ArrayList<Recipe> data = new ArrayList<>();
        for (int i=0;i<=6; i++){
            for (int j=0;j<bd.get(i).size(); j++){
                data.add(bd.get(i).get(j));
            }
        }
        return data;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Recipe currentItem = (Recipe) getItem(position);
        if (convertView == null) {
            if(mData.get(position).getType()<0){
                //ES TITULO
                convertView = mInflater.inflate(R.layout.snippet_item2, null);
                TextView textSeparator = convertView.findViewById(R.id.textSeparator);
                textSeparator.setText(currentItem.getTitle());

            } else {
                //ES RECETA
                convertView = mInflater.inflate(R.layout.recipe_item_layout, null);
                TextView textViewTitle = convertView.findViewById(R.id.textViewTitle);
                TextView textViewType = convertView.findViewById(R.id.textViewType);
                textViewTitle.setText(currentItem.getTitle());
                textViewType.setText(currentItem.getTypeString());
                switch (currentItem.getType()){
                    case 0:
                        textViewType.setTextColor(Color.parseColor("#DAC100"));
                        break;
                    case 1:
                        textViewType.setTextColor(Color.parseColor("#00D5C1"));
                        break;
                    case 2:
                        textViewType.setTextColor(Color.parseColor("#FF5959"));
                }
            }
        }

        return convertView;
    }




}
