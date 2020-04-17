package com.test.marocourses;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class FragList extends Fragment {
    private ListView mList;
    private RequestQueue queue;
    List<Produit> produits;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fraglist_layout,container,false);

        mList = (ListView) view.findViewById(R.id.listView);
        queue = Volley.newRequestQueue(view.getContext());
        produits = new ArrayList<Produit>();
        String url = "http://192.168.1.41:8080/courses/courses/all";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,url,null,new Response.Listener<JSONArray>(){

            @Override
            public void onResponse(JSONArray response) {
                for(int i = 0; i< response.length(); i++) {
                    try {
                        JSONObject produit = response.getJSONObject(i);

                        produits.add( new Produit(Long.parseLong(produit.getString("id")),produit.getString("designation"),
                                Integer.parseInt(produit.getString("quantite"))));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                ProdAdapter adapter = new ProdAdapter(view.getContext(), produits);
                mList.setAdapter(adapter);
            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        queue.add(request);


        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }
}
