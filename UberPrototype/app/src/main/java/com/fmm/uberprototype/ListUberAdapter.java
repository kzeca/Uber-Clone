package com.fmm.uberprototype;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ListUberAdapter extends ArrayAdapter<CarUberItem> {

    private Context context;
    private List<CarUberItem> itens;

    public ListUberAdapter(@NonNull Context context, List<CarUberItem> itens) {
        super(context, R.layout.layout_drivers_list, itens);
        this.context = context;
        this.itens = itens;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.layout_drivers_list, parent, false);

        ImageView imgDriver = rowView.findViewById(R.id.layout_drivers_list_img_uber);
        TextView txtName = rowView.findViewById(R.id.layout_drivers_list_tv_driver);
        TextView txtPrice = rowView.findViewById(R.id.layout_drivers_list_tv_price);

        imgDriver.setImageResource(itens.get(position).getImageId());
        txtName.setText(itens.get(position).getName());
        txtPrice.setText(itens.get(position).getValue());

        return rowView;
    }
}
