package com.example.ruchi.hw10;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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
import java.util.HashMap;

/**
 * Created by Ruchi on 11/25/2016.
 */
public class ActiveBills extends Fragment {

    View activeBillsView;
    private String jsonResponse;
    ListView listview;
    BillAdapter billAdapter;
    ArrayList<HashMap<String, Object>> arraylist;
    final String legisURL="https://helloworld-09.appspot.com/index.php?activebills";
    private RequestQueue requestQueue;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activeBillsView=inflater.inflate(R.layout.bill_active_layout,container,false);

        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        Log.i("inside","outside");

        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(Request.Method.GET, legisURL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        try{

                            JSONObject data;
                            arraylist = new ArrayList<HashMap<String, Object>>();
                            try {
                                // JSONArray jsonArray = new JSONArray(response);

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    HashMap<String, Object> map = new HashMap<String, Object>();
                                    data = jsonArray.getJSONObject(i);
                                    map.put("BillID",data.getString("bill_id"));

                                    map.put("IntroducedOn",data.getString("introduced_on"));

                                    if(data.getString("short_title")=="null"){
                                        map.put("ShortTitle",data.getString("official_title"));
                                    }else{
                                        map.put("ShortTitle",data.getString("short_title"));
                                    }
                                    map.put("rowData",data);

                                    arraylist.add(map);
                                }

                                listview = (ListView)activeBillsView.findViewById(R.id.bill_list_view);


                                // Pass the results into ListViewAdapter.java
                                billAdapter = new BillAdapter(arraylist,getActivity().getApplicationContext());
                                // Set the adapter to the ListView


                                listview.setAdapter(billAdapter);
                                Log.i("method overrrrrrrrrrrrr","yooooooooo");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("error in bill",error.toString());
                    }
                });

        requestQueue.add(jsonArrayRequest);

        return activeBillsView ;
    }


}
