package com.example.ruchi.hw10;

import android.content.Intent;
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
import static com.example.ruchi.hw10.UtilityClass.validate;

/**
 * Created by Ruchi on 11/26/2016.
 */

public class ComDetails extends AppCompatActivity {

    TextView ComID,ComName,Chamber,Contact,Office,parentCom;
    ImageView comStar,chamberlogo;
    JSONObject data;
    static int set=0;
    SharedPreferences sharedpreferences;
    ArrayList<HashMap<String, Object>> arraylist;
    JSONArray jsonArray = new JSONArray();
    Boolean isFav;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.com_detail_layout);
        Log.i("detail classss","wohooo");
        Toolbar toolbar = (Toolbar) findViewById(R.id.detailtoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);





        final String rowData = getIntent().getStringExtra("rowData");
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
                        sharedpreferences=getSharedPreferences("comFav",getApplicationContext().MODE_PRIVATE);
                        String jsonString= sharedpreferences.getString("comFav","");
                        SharedPreferences.Editor editor= sharedpreferences.edit();
                        if(jsonString!=""){

                            try {
                                Log.i("in iffffff","check");
                                JSONArray jsonArray = new JSONArray(jsonString);
                              JSONObject  jsonObject=new JSONObject();
                                jsonObject.put("data",data);
                                //jsonArray= new JSONArray(jsonString);
                                jsonArray.put(jsonObject);
                                editor.putString("comFav",jsonArray.toString());

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
                                editor.putString("comFav",jsonArray.toString());

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
                        isFav=true;

                        final String rowData = getIntent().getStringExtra("rowData");
                        int flag=-1;
                        try {

                            String rowcomID=data.get("committee_id").toString();
                            sharedpreferences=getSharedPreferences("comFav",getApplicationContext().MODE_PRIVATE);
                            String jsonString= sharedpreferences.getString("comFav","");
                            SharedPreferences.Editor editor= sharedpreferences.edit();
                            Log.i("starttt jsonnn",jsonString);
                            if(jsonString!=""){

                                JSONArray jsonArray = new JSONArray(jsonString);
                                JSONObject jsonObject1;
                                for(int i=0;i<jsonArray.length();i++){
                                    jsonObject1=jsonArray.getJSONObject(i).getJSONObject("data");
                                    Log.i("favv jsonn",jsonObject1.toString());
                                    String jsonID=jsonObject1.get("committee_id").toString();
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
                                editor.putString("comFav",jsonArrayNew.toString());

                                editor.commit();
                        }
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }



                    }
                }
            });

            ComID=(TextView) findViewById(R.id.comid);
            Log.i("iddddd",data.get("committee_id").toString());
            ComID.setText(validate(data.get("committee_id").toString()));

            ComName=(TextView) findViewById(R.id.comName);
            Log.i("nameee",data.get("name").toString());
            ComName.setText(validate(data.get("name").toString()));

            Chamber=(TextView)findViewById(R.id.comChamber);
            Log.i("iddddd",data.get("chamber").toString());
            String chamber=data.get("chamber").toString();
            Chamber.setText(capitalize(validate(data.get("chamber").toString())));

            chamberlogo=(ImageView)findViewById(R.id.chamberlogo);
            if(chamber.equals("house"))
                chamberlogo.setImageResource(R.drawable.ushr);
            else
                chamberlogo.setBackgroundResource(R.drawable.senate);


            parentCom=(TextView)findViewById(R.id.comParent);
            //Log.i("iddddd",data.get("parent_committee_id").toString());
            if(!data.has("parent_committee_id") || data.get("parent_committee_id").toString()=="null"){
                parentCom.setText("N.A");
            }
            else{
                parentCom.setText(data.get("parent_committee_id").toString());
            }




            Contact=(TextView) findViewById(R.id.comContact);

            if(!data.has("phone") || data.get("phone").toString()=="null"){
                Contact.setText("N.A");
            }
            else{
                Log.i("iddddd",data.get("phone").toString());
                Contact.setText(data.get("phone").toString());
            }



            Office=(TextView) findViewById(R.id.comOffice);

            if(!data.has("office") || data.get("office").toString()=="null"){
                Office.setText("N.A");
            }
            else{
                Log.i("iddddd",data.get("office").toString());
                Office.setText(data.get("office").toString());
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
               /* Intent intent= new Intent();
                intent.putExtra("section","Comm");
                intent.putExtra("flagfav",isFav);
                setResult(RESULT_OK,intent);
Log.i("finishhh soon","yayy");
                finish();
              break;*/
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
            String rowcomID=jsonObject.get("committee_id").toString();
            Log.i("current json data",jsonObject.toString());
            sharedpreferences=getSharedPreferences("comFav",getApplicationContext().MODE_PRIVATE);
            String jsonString= sharedpreferences.getString("comFav","");
            Log.i("starttt jsonnn",jsonString);
            if(jsonString!=""){

                JSONArray jsonArray = new JSONArray(jsonString);
                JSONObject jsonObject1;
                for(int i=0;i<jsonArray.length();i++){
                    jsonObject1=jsonArray.getJSONObject(i).getJSONObject("data");
                    Log.i("favv jsonn",jsonObject1.toString());
                    String jsonID=jsonObject1.get("committee_id").toString();
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
