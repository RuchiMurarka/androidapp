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

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.ruchi.hw10.UtilityClass.formating;

/**
 * Created by Ruchi on 11/25/2016.
 */

public class BillAdapter extends BaseAdapter {


    Context context;
    HashMap<String, Object> map = new HashMap<String, Object>();
    ArrayList<HashMap<String, Object>> list;
    LayoutInflater inflater;

    public BillAdapter(ArrayList<HashMap<String, Object>> data, Context context){
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
        TextView billID;
        TextView title;
        TextView IntroducedOn;
        if(convertView==null){
            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.bill_list_layout,null,true);

        }
        map= list.get(position);
        /*image = (ImageView) convertView.findViewById(R.id.imageList);
        Picasso.with(context).load(map.get("Image").toString()).into(image);*/
        // image.setImageDrawable(map.get("Image").);
        billID = (TextView) convertView.findViewById(R.id.billID);
        billID.setText(map.get("BillID").toString().toUpperCase());
        /*lastName = (TextView) convertView.findViewById(R.id.lastName);
        lastName.setText(map.get("LastName").toString());*/

        title = (TextView) convertView.findViewById(R.id.billTitle);
        title.setText(map.get("ShortTitle").toString());
        IntroducedOn = (TextView) convertView.findViewById(R.id.IntroducedOn);
        IntroducedOn.setText(formating(map.get("IntroducedOn").toString()));
        ImageView button= (ImageView) convertView.findViewById(R.id.click);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Log.i("inside click","pleaseeee");
                map= list.get(position);
                Intent intent= new Intent(context,billDetails.class);
                Log.i("intent",intent.toString());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("rowData",map.get("rowData").toString());

                context.startActivity(intent);
            }
        });

        return convertView;
    }
}
