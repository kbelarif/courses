package com.test.marocourses;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class FragProd extends Fragment {

    private TextView mView;
    private EditText mEdit;
    private EditText mQte;
    private Button mButton;
    private RequestQueue queue;
    JSONObject json  = new JSONObject();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragprod_layout,container,false);

        mView = (TextView) view.findViewById(R.id.main_view);
        mEdit = (EditText) view.findViewById(R.id.main_edit);
        mQte = (EditText) view.findViewById(R.id.main_qte);
        mButton = (Button) view.findViewById(R.id.main_button);

        mButton.setEnabled(false);
        mQte.setEnabled(false);

        mEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mQte.setEnabled(s.toString().length() !=0);
                mButton.setEnabled(s.toString().length() !=0);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queue = Volley.newRequestQueue(v.getContext());
                String url = "http://192.168.1.41:8080/courses/courses/save";

                try {
                    json.put("designation",mEdit.getText());
                    json.put("id",0);
                    json.put("quantite",mQte.getText());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        mQte.setText("");
                        mEdit.setText("");
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }){
                    @Override
                    public String getBodyContentType(){
                        return "application/json; charset=utf-8";
                    }
                    @Override
                    public byte[] getBody() throws AuthFailureError {
                        return json.toString().getBytes();
                    }
                };
                queue.add(request);
            }
        });

        return view;
    }
}
