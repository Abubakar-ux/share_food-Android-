package com.abubakar.share_food;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FoodAdapter extends ArrayAdapter<Food> implements Filterable {

    private Context context;
    private List<Food> foodList;
    List<Food> mStringFilterList;
    ValueFilter valueFilter;

    public FoodAdapter(@NonNull Context context, ArrayList<Food> androidFood) {
        super(context, 0, androidFood);
        this.context = context;
        this.foodList = androidFood;
        this.mStringFilterList = androidFood;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.row, parent, false);
        }

        // get food information
        Food currentFood = getItem(position);
        TextView nameView = (TextView) listItemView.findViewById(R.id.foodname);
        nameView.setText(currentFood.getName());
        TextView amountView = (TextView) listItemView.findViewById(R.id.amount);
        amountView.setText("Amount: " + currentFood.getAmount());
        TextView locationView = (TextView) listItemView.findViewById(R.id.location);
        locationView.setText("Location: " + currentFood.getFridge());
        CircleImageView img = listItemView.findViewById(R.id.img);
        Picasso.get().load(Id.getIp()+currentFood.getImg()).fit().centerCrop().into(img);
        return listItemView;
    }

    // search for location

    @Override
    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }

    private class ValueFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > 0) {
                ArrayList<Food> filterList = new ArrayList<Food>();
                for (int i = 0; i < mStringFilterList.size(); i++) {
                    if ((mStringFilterList.get(i).getFridge().toUpperCase())
                            .contains(constraint.toString().toUpperCase())) {

                        Food food = new Food(mStringFilterList.get(i).getId(),mStringFilterList.get(i).getName(), mStringFilterList.get(i).getAmount(),
                                mStringFilterList.get(i).getDonor(), mStringFilterList.get(i).getRecipient(),
                                mStringFilterList.get(i).getExpiryDate(), mStringFilterList.get(i).getFridge(), mStringFilterList.get(i).getImg());
                        filterList.add(food);
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = mStringFilterList.size();
                results.values = mStringFilterList;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            foodList = (ArrayList<Food>) results.values;
            for (int i = 0; i < foodList.size(); i++)
                Log.e("publishResult", foodList.get(i).getName());
            notifyDataSetChanged();
        }
    }
}
