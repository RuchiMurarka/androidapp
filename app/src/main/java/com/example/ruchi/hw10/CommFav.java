package com.example.ruchi.hw10;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Ruchi on 11/26/2016.
 */
public class CommFav extends Fragment {

    SharedPreferences sharedPreferences;
    View commFavView;
    ListView listview;
    ComHouseAdapter comhouseAdapter;
    ArrayList<HashMap<String, Object>> arraylist;
    private RequestQueue requestQueue;
    JSONObject data= null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("favoritee create","yipeee");

        commFavView = inflater.inflate(R.layout.fav_comm_layout, container, false);
        sharedPreferences= getActivity().getSharedPreferences("comFav",getActivity().getApplicationContext().MODE_PRIVATE);
        String jsonString= sharedPreferences.getString("comFav","");

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
                map.put("CommitteeID",data.getString("committee_id"));
                map.put("CommitteeName",data.getString("name"));
                map.put("Chamber",data.getString("chamber"));

                map.put("rowData",data);
                arraylist.add(map);
            }

            listview = (ListView)commFavView.findViewById(R.id.favcomm_list_view);


            // Pass the results into ListViewAdapter.java
            comhouseAdapter = new ComHouseAdapter(arraylist,getActivity().getApplicationContext());
            // Set the adapter to the ListView


            listview.setAdapter(comhouseAdapter);
            Log.i("method overrrrrrrrrrrrr","yooooooooo");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return commFavView;

    }

   /* @Override
    public void onActivityResult(int requestCode,int resultCode, Intent data) {
        Log.i("inside methoddd","yoo");
        String type=data.getStringExtra("section");
        if(!data.getBooleanExtra("flagfav",true)){
                Log.i("in activity resulttttt","yay");

        }

    }*/

   /* @Override
    public void onResume() {

        super.onResume();
    }*/
}
