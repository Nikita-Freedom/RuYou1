package com.example.ruyou1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ruyou1.Model.GET.ItemList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    Context context;
    public interface ItemAdapterListener {
        void onClick(ItemList item);
    }
    private ItemList data;
    private ItemAdapterListener itemAdapterListener;

    public ItemAdapter(){

    }
    public ItemAdapter(ItemList data){
        this.data = data;
    }

    public void setData(ItemList data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void setItemAdapterListener(ItemAdapterListener itemAdapterListener){
        this.itemAdapterListener = itemAdapterListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.id.setText(data.getData().get(position).toString());
        holder.target.setText(data.getData().get(position).target);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Description.class);
                Bundle bundle = new Bundle();
                bundle.putString("key1", data.getData().get(position).getTarget());
                intent.putExtras(bundle);
                v.getContext().startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        if (data != null)
            return data.getData().size();
        else
            return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView id;
        TextView target;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            id = view.findViewById(R.id.id_user);
            target = view.findViewById(R.id.target);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemAdapterListener != null)
                        itemAdapterListener.onClick(data);
                }
            });
        }
    }
}



