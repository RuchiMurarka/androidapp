package com.example.ruchi.hw10;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Ruchi on 11/22/2016.
 */

public class StateAdapter extends BaseAdapter {

    Context context;
    HashMap<String, Object> map = new HashMap<String, Object>();
    ArrayList<HashMap<String, Object>> list;
    LayoutInflater inflater;

    public StateAdapter(ArrayList<HashMap<String, Object>> data, Context context){
        this.context=context;
        list=data;
        Log.i("inside contructor","gotchaaaaaaaaaaa");
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Log.i("inside getView","yayy");
        TextView firstName;
        TextView stateName;
        TextView partyName;
        TextView district;
        ImageView image;
        if(convertView==null){
            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.custom_list_layout,null,true);

        }
        map= list.get(position);
        image = (ImageView) convertView.findViewById(R.id.imageList);
        Picasso.with(context).load(map.get("Image").toString()).resize(50,50).into(image);
       // image.setImageDrawable(map.get("Image").);
        firstName = (TextView) convertView.findViewById(R.id.firstName);
        firstName.setText(map.get("LastName").toString()+", "+map.get("FirstName").toString());
        /*lastName = (TextView) convertView.findViewById(R.id.lastName);
        lastName.setText(map.get("LastName").toString());*/

        partyName = (TextView) convertView.findViewById(R.id.partyName);
        partyName.setText("("+map.get("PartyName").toString()+")");
        stateName = (TextView) convertView.findViewById(R.id.stateName);
        stateName.setText(map.get("StateName").toString()+" - ");


        district = (TextView) convertView.findViewById(R.id.districtName);
        district.setText(map.get("DistrictName").toString());
        ImageView button= (ImageView) convertView.findViewById(R.id.click);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Log.i("inside click","pleaseeee");
                map= list.get(position);
                Intent intent= new Intent(context,LegisDetails.class);
                Log.i("intent",intent.toString());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("bioguideID",map.get("bioguideID").toString());
                intent.putExtra("rowData",map.get("rowData").toString());

                context.startActivity(intent);

            }
        });  /*onItemClickListener=new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("inside click","pleaseeee");
                map= list.get(position);
                Intent intent= new Intent(context,LegisDetails.class);
                intent.putExtra("bioguideID",map.get("bioguideID").toString());
                intent.putExtra("rowData",map.get("rowData").toString());
                context.startActivity(intent);
            }
        };*/

        return convertView;
    }
}
