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
 * Created by Ruchi on 11/20/2016.
 */
public class By_senate extends Fragment {

    View legisSenateView;
    private String jsonResponse;
    ListView listview;
    StateAdapter stateAdapter;
    ArrayList<HashMap<String, Object>> arraylist;
    final String legisURL="https://helloworld-09.appspot.com/index.php?legisSenate";
    private RequestQueue requestQueue;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        legisSenateView=inflater.inflate(R.layout.legis_senate,container,false);
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
                                    map.put("Image","https://theunitedstates.io/images/congress/original/"+data.getString("bioguide_id")+".jpg");
                                    map.put("FirstName",data.getString("first_name"));
                                    map.put("LastName",data.getString("last_name"));
                                    map.put("PartyName",data.getString("party"));
                                    map.put("StateName",data.getString("state_name"));
                                    if(data.getString("district")=="null"){
                                        map.put("DistrictName","N.A");
                                    }else{
                                        map.put("DistrictName","District "+data.getString("district"));
                                    }
                                    map.put("rowData",data);
                                    map.put("bioguideID",data.getString("bioguide_id"));

                                    arraylist.add(map);
                                }

                                listview = (ListView)legisSenateView.findViewById(R.id.senate_list_view);


                                // Pass the results into ListViewAdapter.java
                                stateAdapter = new StateAdapter(arraylist,getActivity().getApplicationContext());
                                // Set the adapter to the ListView


                                listview.setAdapter(stateAdapter);
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
                        Log.i("error in legislator",error.toString());
                    }
                });

        requestQueue.add(jsonArrayRequest);
        return legisSenateView;
    }
}
