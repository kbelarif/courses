package com.test.marocourses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.List;

public class ProdAdapter extends ArrayAdapter<Produit   > {
    private RequestQueue queue;
    public ProdAdapter(@NonNull Context context, List<Produit> produits) {
        super(context,0, produits  );
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_layout,parent,false);
        }

        ProdViewHolder viewHolder = (ProdViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new ProdViewHolder();
            viewHolder.designation = (TextView) convertView.findViewById(R.id.product_row);
            viewHolder.quantite = (TextView) convertView.findViewById(R.id.qte_row);
            viewHolder.supprimer = (Button) convertView.findViewById(R.id.supp_row);
            convertView.setTag(viewHolder);
        }

        final Produit produit = getItem(position);

        viewHolder.designation.setText(produit.getDesignation());
        viewHolder.quantite.setText(Integer.toString(produit.getQuantite()));
        viewHolder.supprimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queue = Volley.newRequestQueue(v.getContext());

                String url = "http://192.168.1.41:8080/courses/courses/delete/"+produit.getId();

                StringRequest request = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        remove(produit);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
                queue.add(request);
            }
        });
        return convertView;
    }

    private class ProdViewHolder{
        TextView designation;
        TextView quantite;
        Button supprimer;
    }
}
