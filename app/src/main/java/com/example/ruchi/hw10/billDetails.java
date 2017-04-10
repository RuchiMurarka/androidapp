package com.example.ruchi.hw10;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.ruchi.hw10.UtilityClass.capitalize;
import static com.example.ruchi.hw10.UtilityClass.convert;
import static com.example.ruchi.hw10.UtilityClass.formating;
import static com.example.ruchi.hw10.UtilityClass.validate;

/**
 * Created by Ruchi on 11/25/2016.
 */

public class billDetails extends AppCompatActivity {

    TextView BillID,BillType,Title,Sponsor,Chamber,Status,IntroducedOn,CongressURL,VersionStatus,BillURL;
    JSONObject sponsor,status,pdf,lastversion,url;
    ImageView comStar;
    JSONObject data;
    static int set=0;
    SharedPreferences sharedpreferences;
    ArrayList<HashMap<String, Object>> arraylist;
    JSONArray jsonArray = new JSONArray();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bill_detail_layout);
        Log.i("detail classss","wohooo");

        Toolbar toolbar = (Toolbar) findViewById(R.id.detailtoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String rowData = getIntent().getStringExtra("rowData");
        Log.i("dataaaa",rowData);
        try {
           final JSONObject data=new JSONObject(rowData);

            comStar=(ImageView)findViewById(R.id.comStar);
            comStar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(set==0)
                    {
                        comStar.setBackgroundResource(R.drawable.starfilled);
                        set=1;
                        sharedpreferences=getSharedPreferences("billFav",getApplicationContext().MODE_PRIVATE);
                        String jsonString= sharedpreferences.getString("billFav","");
                        SharedPreferences.Editor editor= sharedpreferences.edit();
                        if(jsonString!=""){

                            try {
                                Log.i("in iffffff","check");
                                JSONArray jsonArray = new JSONArray(jsonString);
                                JSONObject  jsonObject=new JSONObject();
                                jsonObject.put("data",data);
                                //jsonArray= new JSONArray(jsonString);
                                jsonArray.put(jsonObject);
                                editor.putString("billFav",jsonArray.toString());

                                editor.commit();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                        else{

                            try {
                                Log.i("in elsee","check");
                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put("data",data);
                                jsonArray.put(jsonObject);
                                editor.putString("billFav",jsonArray.toString());

                                editor.commit();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            //jsonArray= new JSONArray(jsonString);

                        }

                    }
                    else
                    {
                        comStar.setBackgroundResource(R.drawable.star);
                        set=0;
                        final String rowData = getIntent().getStringExtra("rowData");
                        int flag=-1;
                        try {

                            String rowcomID=data.get("bill_id").toString();
                            sharedpreferences=getSharedPreferences("billFav",getApplicationContext().MODE_PRIVATE);
                            String jsonString= sharedpreferences.getString("billFav","");
                            SharedPreferences.Editor editor= sharedpreferences.edit();
                            Log.i("starttt jsonnn",jsonString);
                            if(jsonString!=""){

                                JSONArray jsonArray = new JSONArray(jsonString);
                                JSONObject jsonObject1;
                                for(int i=0;i<jsonArray.length();i++){
                                    jsonObject1=jsonArray.getJSONObject(i).getJSONObject("data");
                                    Log.i("favv jsonn",jsonObject1.toString());
                                    String jsonID=jsonObject1.get("bill_id").toString();
                                    if(jsonID.equals(rowcomID)){
                                        Log.i("saved the index",jsonID);
                                        flag=i;
                                        break;
                                    }
                                }

                                JSONArray jsonArrayNew = new JSONArray();
                                for(int j=0;j<jsonArray.length();j++){
                                    if(j!=flag)
                                        jsonArrayNew.put(jsonArray.get(j));
                                }
                                Log.i("new json after removal",jsonArrayNew.toString());
                                editor.putString("billFav",jsonArrayNew.toString());

                                editor.commit();
                            }
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }



                    }
                }
            });



            BillID=(TextView) findViewById(R.id.billid);
            BillID.setText(validate(data.get("bill_id").toString()).toUpperCase());

            Title=(TextView) findViewById(R.id.billtitle);
            Title.setText(capitalize(data.get("official_title").toString()));

            BillType=(TextView) findViewById(R.id.billtype);
            BillType.setText(validate(data.get("bill_type").toString()).toUpperCase());

            Sponsor=(TextView) findViewById(R.id.sponsor);
            sponsor=data.getJSONObject("sponsor");
            Log.i("nameee",sponsor.get("last_name").toString());
            Sponsor.setText(validate(sponsor.get("title").toString())+" "+validate(sponsor.get("last_name").toString())+", "+validate(sponsor.get("first_name").toString()));

            Chamber=(TextView)findViewById(R.id.chamber);
            Chamber.setText(capitalize(validate(data.get("chamber").toString())));

            Status=(TextView)findViewById(R.id.status);
            status=data.getJSONObject("history");
            Status.setText(convert(status.get("active").toString()));

            IntroducedOn=(TextView) findViewById(R.id.introducedon);
            IntroducedOn.setText(formating(validate(data.get("introduced_on").toString())));

            CongressURL=(TextView)findViewById(R.id.congressurl);
            url=data.getJSONObject("urls");
            CongressURL.setText(validate(url.get("congress").toString()));

            VersionStatus=(TextView)findViewById(R.id.versionstatus);
            lastversion=data.getJSONObject("last_version");
            VersionStatus.setText(validate(lastversion.get("version_name").toString()));

            BillURL=(TextView)findViewById(R.id.billurl);
            pdf=lastversion.getJSONObject("urls");
            BillURL.setText(validate(pdf.get("html").toString()));
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onStart(){
        Log.i("on startttt","yipeee");

        final String rowData = getIntent().getStringExtra("rowData");
        int flag=0;
        try {
            JSONObject jsonObject = new JSONObject(rowData);
            String rowcomID=jsonObject.get("bill_id").toString();
            Log.i("current json data",jsonObject.toString());
            sharedpreferences=getSharedPreferences("billFav",getApplicationContext().MODE_PRIVATE);
            String jsonString= sharedpreferences.getString("billFav","");
            Log.i("starttt jsonnn",jsonString);
            if(jsonString!=""){

                JSONArray jsonArray = new JSONArray(jsonString);
                JSONObject jsonObject1;
                for(int i=0;i<jsonArray.length();i++){
                    jsonObject1=jsonArray.getJSONObject(i).getJSONObject("data");
                    Log.i("favv jsonn",jsonObject1.toString());
                    String jsonID=jsonObject1.get("bill_id").toString();
                    if(jsonID.equals(rowcomID)){
                        Log.i("inside flaggg","yayyy we done it");
                        flag=1;
                        break;
                    }
                }
                if(flag==1){
                    set=1;
                    comStar.setBackgroundResource(R.drawable.starfilled);

                }else{
                    set=0;
                    comStar.setBackgroundResource(R.drawable.star);
                }


            }
            else{
                set=0;
                comStar.setBackgroundResource(R.drawable.star);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        super.onStart();
    }

}
