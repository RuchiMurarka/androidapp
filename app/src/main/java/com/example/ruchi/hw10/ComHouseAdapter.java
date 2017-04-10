package com.example.ruchi.hw10;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Ruchi on 11/25/2016.
 */
public class ComHouseAdapter  extends BaseAdapter{

    Context context;
    HashMap<String, Object> map = new HashMap<String, Object>();
    ArrayList<HashMap<String, Object>> list;
    LayoutInflater inflater;

    public ComHouseAdapter(ArrayList<HashMap<String, Object>> data, Context context){
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
        TextView committeeID;
        TextView committeeName;
        TextView chamber;
        if(convertView==null){
            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.com_list_layout,null,true);

        }
        map= list.get(position);
        /*image = (ImageView) convertView.findViewById(R.id.imageList);
        Picasso.with(context).load(map.get("Image").toString()).into(image);*/
        // image.setImageDrawable(map.get("Image").);
        committeeID = (TextView) convertView.findViewById(R.id.comID);
        committeeID.setText(map.get("CommitteeID").toString().toUpperCase());
        /*lastName = (TextView) convertView.findViewById(R.id.lastName);
        lastName.setText(map.get("LastName").toString());*/

        committeeName = (TextView) convertView.findViewById(R.id.comName);
        committeeName.setText(map.get("CommitteeName").toString());
        chamber = (TextView) convertView.findViewById(R.id.comChamber);
        chamber.setText(UtilityClass.capitalize(map.get("Chamber").toString()));
        ImageView button= (ImageView) convertView.findViewById(R.id.click);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Log.i("inside click","pleaseeee");
                map= list.get(position);
                Intent intent= new Intent(context,ComDetails.class);
                Log.i("intent",intent.toString());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("rowData",map.get("rowData").toString());

                context.startActivity(intent);
            }
        });

        return convertView;
    }


}


