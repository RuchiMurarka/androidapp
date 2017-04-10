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
public class BillFav extends Fragment {
    SharedPreferences sharedPreferences;
    View billFavView;
    ListView listview;
    BillAdapter billAdapter;
    ArrayList<HashMap<String, Object>> arraylist;
    private RequestQueue requestQueue;
    JSONObject data= null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("favoritee create","yipeee");

        billFavView = inflater.inflate(R.layout.fav_bill_layout, container, false);
        sharedPreferences= getActivity().getSharedPreferences("billFav",getActivity().getApplicationContext().MODE_PRIVATE);
        String jsonString= sharedPreferences.getString("billFav","");

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

            listview = (ListView)billFavView.findViewById(R.id.favbill_list_view);


            // Pass the results into ListViewAdapter.java
            billAdapter = new BillAdapter(arraylist,getActivity().getApplicationContext());
            // Set the adapter to the ListView


            listview.setAdapter(billAdapter);
            Log.i("method overrrrrrrrrrrrr","yooooooooo");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return billFavView;

    }

}
