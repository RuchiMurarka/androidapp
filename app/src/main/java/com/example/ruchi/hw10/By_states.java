package com.example.ruchi.hw10;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

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
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ruchi on 11/20/2016.
 */
public class By_states extends Fragment implements View.OnClickListener{

    View legisStatesView;
    private String jsonResponse;
    ListView  listview;
    StateAdapter stateAdapter;
    ArrayList<HashMap<String, Object>> arraylist;
    final String legisURL="https://helloworld-09.appspot.com/index.php?type";
    private RequestQueue requestQueue;
    String[] statesName;
    Map<String, Integer> mapIndex;


    @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            legisStatesView=inflater.inflate(R.layout.legis_state,container,false);
        setHasOptionsMenu(true);
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
                                    statesName = new String[jsonArray.length()];
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
                                        String statename=  UtilityClass.capitalize(data.get("state_name").toString());
                                        statesName[i]=statename;

                                        arraylist.add(map);
                                    }
                                    Arrays.sort(statesName);
                                    Arrays.asList(statesName);


                                    listview = (ListView)legisStatesView.findViewById(R.id.legis_list_view);


                                    // Pass the results into ListViewAdapter.java
                                    stateAdapter = new StateAdapter(arraylist,getActivity().getApplicationContext());
                                    // Set the adapter to the ListView


                                    listview.setAdapter(stateAdapter);
                                    getIndexList(statesName);


                                    displayIndex();
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


            return legisStatesView;
    }

    public List sortOnState(List arraylist) {
        Collections.sort(arraylist, new Comparator<HashMap< String,Object >>() {
            @Override
            public int compare(HashMap<String,Object> o1, HashMap<String,Object> o2) {
                try {
                    int c;
                    c = o1.get("state_name").toString().compareToIgnoreCase(o2.get("state_name").toString());
                    return c;
                }
                catch (Exception e){
                    return -1;
                }
            }
        });
        return arraylist;
    }



    private void getIndexList(String[] state) {
        mapIndex = new LinkedHashMap<String, Integer>();
        for (int i = 0; i < state.length; i++) {
            String statename = state[i];
            String index = statename.substring(0, 1);

            if (mapIndex.get(index) == null)
                mapIndex.put(index, i);
        }
    }

    private void displayIndex() {
        LinearLayout indexLayout = (LinearLayout) legisStatesView.findViewById(R.id.side_index);

        TextView textView;
        List<String> indexList = new ArrayList<String>(mapIndex.keySet());
        for (String index : indexList) {
            textView = (TextView) getActivity().getLayoutInflater().inflate(R.layout.side_index_item, null);
            textView.setText(index);
            textView.setOnClickListener(this);
            indexLayout.addView(textView);
        }
    }

    @Override
    public void onClick(View view) {
        TextView selectedIndex = (TextView) view;
        listview.setSelection(mapIndex.get(selectedIndex.getText()));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.main, menu);
    }
}
