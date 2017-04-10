package com.example.ruchi.hw10;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static com.example.ruchi.hw10.UtilityClass.capitalize;
import static com.example.ruchi.hw10.UtilityClass.formating;
import static com.example.ruchi.hw10.UtilityClass.partycheck;
import static com.example.ruchi.hw10.UtilityClass.validate;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by Ruchi on 11/23/2016.
 */

public class LegisDetails extends AppCompatActivity {

    ImageView fb, twitter, website;
    TextView party, Name, Email, Chamber, Contact, Start_Term, End_term, Term, Office, State, Fax, BirthDay;
    ImageView comStar,  logo;
    private ImageView picture;
    private Bitmap bitmap;
    ProgressBar progressBar;
    JSONObject data;
    static int set = 0;
    SharedPreferences sharedpreferences;
    ArrayList<HashMap<String, Object>> arraylist;
    JSONArray jsonArray = new JSONArray();
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.legis_detail_layout);
        Log.i("detail classss", "wohooo");

        Toolbar toolbar = (Toolbar) findViewById(R.id.detailtoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final String bioguideID = getIntent().getStringExtra("bioguideID");
        String rowData = getIntent().getStringExtra("rowData");
        Log.i("dataaaa", rowData);
        try {
            final JSONObject data = new JSONObject(rowData);

            //for star and shared preference

            comStar = (ImageView) findViewById(R.id.comStar);
            comStar.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (set == 0) {
                        comStar.setBackgroundResource(R.drawable.starfilled);
                        set = 1;
                        sharedpreferences = getSharedPreferences("legisFav", getApplicationContext().MODE_PRIVATE);
                        String jsonString = sharedpreferences.getString("legisFav", "");
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        if (jsonString != "") {

                            try {
                                Log.i("in iffffff", "check");
                                JSONArray jsonArray = new JSONArray(jsonString);
                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put("data", data);
                                //jsonArray= new JSONArray(jsonString);
                                jsonArray.put(jsonObject);
                                editor.putString("legisFav", jsonArray.toString());

                                editor.commit();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        } else {

                            try {
                                Log.i("in elsee", "check");
                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put("data", data);
                                jsonArray.put(jsonObject);
                                editor.putString("legisFav", jsonArray.toString());

                                editor.commit();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            //jsonArray= new JSONArray(jsonString);

                        }

                    } else {
                        comStar.setBackgroundResource(R.drawable.star);
                        set = 0;

                        final String rowData = getIntent().getStringExtra("rowData");
                        int flag = -1;
                        try {

                            String rowcomID = data.get("bioguide_id").toString();
                            sharedpreferences = getSharedPreferences("legisFav", getApplicationContext().MODE_PRIVATE);
                            String jsonString = sharedpreferences.getString("legisFav", "");
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            Log.i("starttt jsonnn", jsonString);
                            if (jsonString != "") {

                                JSONArray jsonArray = new JSONArray(jsonString);
                                JSONObject jsonObject1;
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    jsonObject1 = jsonArray.getJSONObject(i).getJSONObject("data");
                                    Log.i("favv jsonn", jsonObject1.toString());
                                    String jsonID = jsonObject1.get("bioguide_id").toString();
                                    if (jsonID.equals(rowcomID)) {
                                        Log.i("saved the index", jsonID);
                                        flag = i;
                                        break;
                                    }
                                }

                                JSONArray jsonArrayNew = new JSONArray();
                                for (int j = 0; j < jsonArray.length(); j++) {
                                    if (j != flag)
                                        jsonArrayNew.put(jsonArray.get(j));
                                }
                                Log.i("new json after removal", jsonArrayNew.toString());
                                editor.putString("legisFav", jsonArrayNew.toString());

                                editor.commit();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }


                }
            });

            DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                    .cacheOnDisc(true).cacheInMemory(true)
                    .imageScaleType(ImageScaleType.EXACTLY)
                    .displayer(new FadeInBitmapDisplayer(300)).build();

            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                    getApplicationContext())
                    .defaultDisplayImageOptions(defaultOptions)
                    .memoryCache(new WeakMemoryCache())
                    .discCacheSize(100 * 1024 * 1024).build();

            ImageLoader.getInstance().init(config);
            picture=(ImageView)findViewById(R.id.image);


            ImageLoader imageLoader = ImageLoader.getInstance();

            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    .resetViewBeforeLoading(false)  // default
                    .delayBeforeLoading(0)
                    .cacheInMemory(false) // default
                    .cacheOnDisk(true) // default
                    .build();


             String url="https://theunitedstates.io/images/congress/original/"+data.get("bioguide_id").toString()+".jpg";
            imageLoader.displayImage(url, picture, options);


            fb = (ImageView) findViewById(R.id.fb);
            fb.setOnClickListener(new View.OnClickListener()
            {

                public void onClick(View v)
                {
                    try
                    {
                        if(!data.has("facebook_id")|| data.get("facebook_id").toString()=="null")
                        {
                            Toast.makeText(getApplicationContext(), "No facebook page", Toast.LENGTH_SHORT).show();
                        }else
                        {
                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_VIEW);
                            intent.addCategory(Intent.CATEGORY_BROWSABLE);
                            intent.setData(Uri.parse("https://www.facebook.com/profile.php?id="+data.get("facebook_id").toString()));
                            startActivity(intent);
                        }
                    }catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
            );

            twitter = (ImageView) findViewById(R.id.twitter);
            twitter.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v)
                {
                    try
                    {
                        if(!data.has("twitter_id")|| data.get("twitter_id").toString()=="null")
                        {
                            Toast.makeText(getApplicationContext(), "No Twitter page", Toast.LENGTH_SHORT).show();
                        }else
                        {
                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_VIEW);
                            intent.addCategory(Intent.CATEGORY_BROWSABLE);
                            intent.setData(Uri.parse("https://twitter.com/"+data.get("twitter_id").toString()));
                            startActivity(intent);
                        }
                    }catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }
            });

            website = (ImageView) findViewById(R.id.website);
            website.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View v)
                {
                    try
                    {
                        if(!data.has("website")|| data.get("website").toString()=="null")
                        {
                            Toast.makeText(getApplicationContext(), "No website", Toast.LENGTH_SHORT).show();
                        }else
                        {
                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_VIEW);
                            intent.addCategory(Intent.CATEGORY_BROWSABLE);
                            intent.setData(Uri.parse(data.get("website").toString()));
                            startActivity(intent);
                        }
                    }catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }
            });
            logo = (ImageView) findViewById(R.id.partylogo);


            party = (TextView) findViewById(R.id.partyName);
            String p = partycheck(data.get("party").toString());
            party.setText(p);
            if (p == "Republican")
                logo.setBackgroundResource(R.drawable.rebupblic);
            else if (p == "Democratic")
                logo.setBackgroundResource(R.drawable.democratic);
            else
                logo.setBackgroundResource(R.drawable.independent);

            Name = (TextView) findViewById(R.id.fullName);
            Name.setText(validate(data.get("title").toString()) + ". " + validate(data.get("first_name").toString()) + ", " + validate(data.get("last_name").toString()));

            Email = (TextView) findViewById(R.id.email);
            Email.setText(validate(data.get("oc_email").toString()));

            Chamber = (TextView) findViewById(R.id.chamber);
            Chamber.setText(capitalize(validate(data.get("chamber").toString())));

            Contact = (TextView) findViewById(R.id.contact);
            Contact.setText(validate(data.get("phone").toString()));

            Start_Term = (TextView) findViewById(R.id.startTerm);
           String St=formating(validate(data.get("term_start").toString()));
            Start_Term.setText(St);

            End_term = (TextView) findViewById(R.id.endTerm);
            String et=formating(validate(data.get("term_end").toString()));
            End_term.setText(et);


            Resources res = getResources();
            Drawable drawable = res.getDrawable(R.drawable.myprogressbar);
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd,yyyy");
            int pr=0;
            try {
                Date start = dateFormat.parse(St);
                Date end = dateFormat.parse(et);
                Date d=new Date();
                long timediff1=d.getTime()-start.getTime();
                long timediff=end.getTime()-start.getTime();
                //Toast.makeText(getApplicationContext(),timediff1+"  "+timediff+" "+d,Toast.LENGTH_SHORT).show();
                 pr=(int)((double)timediff1/timediff*100);
                //Toast.makeText(getApplicationContext(),l+" ",Toast.LENGTH_SHORT).show();

            }catch (Exception e)
            {

            }
                progressBar = (ProgressBar) findViewById(R.id.progressBar);
            progressBar.setProgress(pr);
            progressBar.setMax(100);
            progressBar.setProgressDrawable(drawable);

            Office = (TextView) findViewById(R.id.office);
            Office.setText(validate(data.get("office").toString()));

            State = (TextView) findViewById(R.id.state);
            State.setText(validate(data.get("state").toString()));

            Fax = (TextView) findViewById(R.id.fax);
            Fax.setText(validate(data.get("fax").toString()));

            BirthDay = (TextView) findViewById(R.id.birthday);
            BirthDay.setText(formating(validate(data.get("birthday").toString())));





        } catch (JSONException e) {
            e.printStackTrace();
        }


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
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
    protected void onStart() {
        Log.i("on startttt", "yipeee");

        final String rowData = getIntent().getStringExtra("rowData");
        int flag = 0;
        try {
            JSONObject jsonObject = new JSONObject(rowData);
            String rowcomID = jsonObject.get("bioguide_id").toString();
            Log.i("current json data", jsonObject.toString());
            sharedpreferences = getSharedPreferences("legisFav", getApplicationContext().MODE_PRIVATE);
            String jsonString = sharedpreferences.getString("legisFav", "");
            Log.i("starttt jsonnn", jsonString);
            if (jsonString != "") {

                JSONArray jsonArray = new JSONArray(jsonString);
                JSONObject jsonObject1;
                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonObject1 = jsonArray.getJSONObject(i).getJSONObject("data");
                    Log.i("favv jsonn", jsonObject1.toString());
                    String jsonID = jsonObject1.get("bioguide_id").toString();
                    if (jsonID.equals(rowcomID)) {
                        Log.i("inside flaggg", "yayyy we done it");
                        flag = 1;
                        break;
                    }
                }
                if (flag == 1) {
                    set = 1;
                    comStar.setBackgroundResource(R.drawable.starfilled);

                } else {
                    set = 0;
                    comStar.setBackgroundResource(R.drawable.star);
                }


            } else {
                set = 0;
                comStar.setBackgroundResource(R.drawable.star);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        super.onStart();// ATTENTION: This was auto-generated to implement the App Indexing API.
// See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("LegisDetails Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

}
