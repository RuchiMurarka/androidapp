package com.example.ruchi.hw10;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.RequestQueue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Ruchi on 11/26/2016.
 */
public class LegisFav extends Fragment {

    SharedPreferences sharedPreferences;
    View legisFavView;
    ListView listview;
    StateAdapter stateAdapter;
    ArrayList<HashMap<String, Object>> arraylist;
    private RequestQueue requestQueue;
    JSONObject data= null;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("favoritee legis create","yipeee");

        legisFavView = inflater.inflate(R.layout.fav_legis_layout, container, false);
        sharedPreferences= getActivity().getSharedPreferences("legisFav",getActivity().getApplicationContext().MODE_PRIVATE);
        String jsonString= sharedPreferences.getString("legisFav","");

        Log.i("jsonnnnnnn",jsonString);

        JSONObject data;
        arraylist = new ArrayList<HashMap<String, Object>>();
        try {
            // JSONArray jsonArray = new JSONArray(response);
            JSONArray jsonArray;
            if(jsonString!=null&&!jsonString.isEmpty()){
                jsonArray= new JSONArray(jsonString);
            }else{
                jsonArray= new JSONArray();

            }
            for (int i = 0; i < jsonArray.length(); i++) {
                HashMap<String, Object> map = new HashMap<String, Object>();
                data = jsonArray.getJSONObject(i).getJSONObject("data");
                map.put("Image","https://theunitedstates.io/images/congress/original/"+data.getString("bioguide_id")+".jpg");
                map.put("FirstName",data.getString("first_name"));
                map.put("LastName",data.getString("last_name"));
                map.put("PartyName",data.getString("party"));
                map.put("StateName",data.getString("state_name"));
                if(data.getString("district")=="null"){
                    map.put("DistrictName","");
                }else{
                    map.put("DistrictName","District "+data.getString("district"));
                }
                map.put("rowData",data);
                map.put("bioguideID",data.getString("bioguide_id"));

                arraylist.add(map);
            }

            listview = (ListView)legisFavView.findViewById(R.id.favlegis_list_view);


            // Pass the results into ListViewAdapter.java
            stateAdapter = new StateAdapter(arraylist,getActivity().getApplicationContext());
            // Set the adapter to the ListView


            listview.setAdapter(stateAdapter);
            Log.i("method overrrrrrrrrrrrr","yooooooooo");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return legisFavView;

    }
}
