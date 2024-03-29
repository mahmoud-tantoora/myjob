package com.example.dell.navbot;

import android.app.ActionBar;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import java.util.*;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public boolean state=true;
    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    private RequestQueue requestQueue;
    /////////////////
    private MyPagerAdapter mFragmentAdapter;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private String id_email = "";
    private String idworker = "";
    private String idcompany = "",idCompany="";
    ///////////////////////
    TextView email,phone,work,address,study,name_profile;
    ImageView image_profile;
    ListView qualification;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
       /* Intent intent = new Intent(getApplicationContext(), login.class);
        startActivityForResult(intent, 1);
        finish();
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);*/
        Intent i = getIntent();
        id_email = i.getStringExtra("id_email");
        idworker = i.getStringExtra("idworker");
        idcompany = i.getStringExtra("idcompany");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFragmentAdapter = new MyPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.photos_viewpager);
        mViewPager.setAdapter(mFragmentAdapter);

        // link the tabLayout and the viewpager together
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mTabLayout.setupWithViewPager(mViewPager);

            dl = (DrawerLayout) findViewById(R.id.activity_main);
            t = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);
            dl.addDrawerListener(t);
            t.syncState();
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            nv = (NavigationView) findViewById(R.id.nv);


         if(myservice.ServiceIsRun==false)
         {
             myservice.ServiceIsRun = true;
             Intent i1 = new Intent(this, myservice.class);
             startService(i1);
         }

         mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
             @Override
             public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                 //Toast.makeText(getApplicationContext(),"onPageScrolled",Toast.LENGTH_SHORT).show();
                 if(state)
                 {
                     state=false;
                     if(mViewPager.getCurrentItem()==0)
                     {     try {
                         final RecyclerView recyclerView=(RecyclerView)findViewById(R.id.list_recycler);
                         recyclerView.setHasFixedSize(true);

                         requestQueue = Volley.newRequestQueue(getApplicationContext());


                         String url = "http://my-app-ammar.000webhostapp.com/getall_opportunity.php";
                         JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                                 new Response.Listener<JSONObject>() {
                                     @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                                     @Override
                                     public void onResponse(JSONObject response) {
                                         try {

                                             JSONArray jsonArray = response.getJSONArray("sss");
                                             Itemdata []items = new  Itemdata[jsonArray.length()];
                                             for (int i = 0; i < jsonArray.length(); i++) {
                                                 JSONObject jsonObject = jsonArray.getJSONObject(i);
                                                 String jopname = jsonObject.getString("jop_name");
                                                 String jopdesc = jsonObject.getString("jop_describtion");
                                                 String numberworker = jsonObject.getString("number_workers");
                                                 String namecompany = jsonObject.getString("name");
                                                  idCompany = jsonObject.getString("id_company");
                                                 items[i] = new Itemdata(namecompany,jopname, jopdesc,numberworker,R.drawable.a,idCompany);
                                             }
                                             //   Adapter adapter = new Adapter(MainActivity.this,arrayList_opp);
                                             //   recyclerView.setAdapter(adapter);
                                             recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                             MyAdapter myAdapter=new MyAdapter(items,MainActivity.this,idworker,idcompany,id_email);
                                             recyclerView.setAdapter(myAdapter);
                                             recyclerView.setItemAnimator(new DefaultItemAnimator());
                                         } catch (JSONException e) {
                                             Toast.makeText(getApplicationContext(),"something  is error",Toast.LENGTH_LONG).show();
                                         }
                                     }
                                 }, new Response.ErrorListener() {
                             @Override
                             public void onErrorResponse(VolleyError error) {
                                 Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_LONG).show();

                             }
                         });
                         requestQueue.add(request);

                         //new gridLayoutManager(this,2)

                     }catch (Exception e){}


                     }
                     else  if(mViewPager.getCurrentItem()==1)
                     {
                         name_profile =(TextView)findViewById(R.id.name_profile);
                         email=(TextView)findViewById(R.id.email_profile);
                         phone=(TextView)findViewById(R.id.phone_profile);
                         work=(TextView)findViewById(R.id.work_profile);
                         address=(TextView)findViewById(R.id.address_profile);
                         study=(TextView)findViewById(R.id.study_profile);
                         qualification=(ListView)findViewById(R.id.list_profile);

                          String Url = "http://my-app-ammar.000webhostapp.com/get_profile.php";
                         com.android.volley.toolbox.StringRequest stringRequest = new com.android.volley.toolbox.StringRequest(Request.Method.POST, Url, new Response.Listener<String>() {
                          //   @SuppressLint("SetTextI18n")
                             @Override
                             public void onResponse(String response) {
                                 try {
                                     JSONObject jsonObject = new JSONObject(response);

                                     email.setText(jsonObject.getString("email"));
                                     name_profile.setText(jsonObject.getString("first_name") + jsonObject.getString("last_name"));
                                     phone.setText(jsonObject.getString("+0"+"phone"));
                                     address.setText(jsonObject.getString("address"));
                                     work.setText(jsonObject.getString("type_work"));
                                     study.setText(jsonObject.getString("studying"));



                                 }catch (Exception e)
                                 {
                                     Toast.makeText(getApplicationContext(),"something  is error",Toast.LENGTH_LONG).show();
                                 }

                             }
                         }, new Response.ErrorListener() {
                             @Override
                             public void onErrorResponse(VolleyError error) {
                                 Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_LONG).show();
                             }
                         }){
                             @Override
                             protected Map<String, String> getParams() {
                                 Map<String,String> params = new java.util.HashMap<>();

                                 params.put("id_email",id_email);

                                 return params;
                             }
                         };
                         RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                         requestQueue.add(stringRequest);



                         ///          get all qualification

                         String Urlqual = "http://my-app-ammar.000webhostapp.com/get_qualification.php";
                         com.android.volley.toolbox.StringRequest stringRequestqual = new com.android.volley.toolbox.StringRequest(Request.Method.POST, Urlqual, new Response.Listener<String>() {
                             @Override
                             public void onResponse(String response) {
                                 try {
                                     ArrayList<String> values = new ArrayList<String>();
                                     JSONArray jsonArray = new JSONArray(response);
                                     for (int i = 0; i < jsonArray.length(); i++) {
                                         JSONObject jsonObject = jsonArray.getJSONObject(i);
                                         values.add(jsonObject.getString("name_qualification"));
                                     }
                                     ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1, values);

                                     qualification.setAdapter(arrayAdapter);

                                 }catch (Exception e)
                                 {
                                     Toast.makeText(getApplicationContext(),"something  is error",Toast.LENGTH_LONG).show();
                                 }

                             }




                         }, new Response.ErrorListener() {
                             @Override
                             public void onErrorResponse(VolleyError error) {
                                 Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_LONG).show();
                             }
                         }){
                             @Override
                             protected Map<String, String> getParams() {
                                 Map<String,String> params = new java.util.HashMap<>();

                                 params.put("id_email",id_email);

                                 return params;
                             }
                         };


                         RequestQueue requestQueuequal = Volley.newRequestQueue(getApplicationContext());
                         requestQueuequal.add(stringRequestqual);




                     }
                     else  if(mViewPager.getCurrentItem()==2)
                     {
                         final int[] t = {0};
                         final Itemdata_notify[][] itemss = new Itemdata_notify[1][100];
                         final RecyclerView recyclerView=(RecyclerView)findViewById(R.id.list_recycler_notify);
                          String Urlcomment = "http://my-app-ammar.000webhostapp.com/get_comment.php";
                         com.android.volley.toolbox.StringRequest stringRequestcomment = new com.android.volley.toolbox.StringRequest(Request.Method.POST, Urlcomment, new Response.Listener<String>() {
                             @Override
                             public void onResponse(String response) {
                                 try {

                                     JSONArray jsonArray = new JSONArray(response);
                                     Itemdata_notify []items = new  Itemdata_notify[jsonArray.length()];
                                     for (int i = 0; i < jsonArray.length(); i++) {
                                         JSONObject jsonObject = jsonArray.getJSONObject(i);
                                        String comm = jsonObject.getString("comment");
                                         String nameworker = jsonObject.getString("name_worker");
                                         String jname = jsonObject.getString("job_name");

                                         items[t[0]] = new Itemdata_notify("Comment From "+nameworker,comm,"On Post "+jname);
                                         itemss[0][t[0]]=items[t[0]];
                                         t[0]++;
                                     }



                                 }catch (Exception e)
                                 {
                                     Toast.makeText(getApplicationContext(),"something  is error",Toast.LENGTH_LONG).show();
                                 }

                             }

                         }, new Response.ErrorListener() {
                             @Override
                             public void onErrorResponse(VolleyError error) {
                                 Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_LONG).show();
                             }
                         }){
                             @Override
                             protected Map<String, String> getParams() {
                                 Map<String,String> params = new java.util.HashMap<>();

                                 params.put("idcompany",idcompany);

                                 return params;
                             }
                         };


                         RequestQueue requestQueuecomment = Volley.newRequestQueue(getApplicationContext());
                         requestQueuecomment.add(stringRequestcomment);

                         String Urlorder = "http://my-app-ammar.000webhostapp.com/get_order.php";
                         com.android.volley.toolbox.StringRequest stringRequestorder = new com.android.volley.toolbox.StringRequest(Request.Method.POST, Urlorder, new Response.Listener<String>() {
                             @Override
                             public void onResponse(String response) {
                                 try {

                                     JSONArray jsonArray = new JSONArray(response);
                                     Itemdata_notify []items = new  Itemdata_notify[jsonArray.length()];
                                     for (int i = 0; i < jsonArray.length(); i++) {
                                         JSONObject jsonObject = jsonArray.getJSONObject(i);
                                         String details = jsonObject.getString("details");
                                         String nameworker = jsonObject.getString("name_worker");
                                         String st_worker = jsonObject.getString("study_worker");
                                         String jname = jsonObject.getString("job_name");

                                         items[i] = new Itemdata_notify("Order From "+nameworker,details+" , he is study "+st_worker,"For Post "+jname);
                                         itemss[0][t[0]]=items[i];
                                         t[0]++;
                                     }
                                     Itemdata_notify []items2 = new Itemdata_notify[t[0]];
                                     for(int j=0;j<t[0];j++)
                                     {
                                         items2[j] = itemss[0][j];


                                     }
                                     recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                                     MyAdpter_notify myAdapter=new MyAdpter_notify(items2,MainActivity.this);
                                     recyclerView.setAdapter(myAdapter);
                                     recyclerView.setItemAnimator(new DefaultItemAnimator());




                                 }catch (Exception e)
                                 {
                                     Toast.makeText(getApplicationContext(),"something  is error",Toast.LENGTH_LONG).show();
                                 }

                             }

                         }, new Response.ErrorListener() {
                             @Override
                             public void onErrorResponse(VolleyError error) {
                                 Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_LONG).show();
                             }
                         }){
                             @Override
                             protected Map<String, String> getParams() {
                                 Map<String,String> params = new java.util.HashMap<>();

                                 params.put("idcompany",idcompany);

                                 return params;
                             }
                         };


                         RequestQueue requestQueueorder = Volley.newRequestQueue(getApplicationContext());
                         requestQueueorder.add(stringRequestorder);




                         dl.closeDrawers();
                         dl.animate();
                     }
                 }

             }

             @Override
             public void onPageSelected(int position) {

                 if(mViewPager.getCurrentItem()==0)
                 {
                     try {
                         final RecyclerView recyclerView=(RecyclerView)findViewById(R.id.list_recycler);
                         recyclerView.setHasFixedSize(true);

                         requestQueue = Volley.newRequestQueue(getApplicationContext());


                         String url = "http://my-app-ammar.000webhostapp.com/getall_opportunity.php";
                         JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                                 new Response.Listener<JSONObject>() {
                                     @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                                     @Override
                                     public void onResponse(JSONObject response) {
                                         try {

                                             JSONArray jsonArray = response.getJSONArray("sss");
                                             Itemdata []items = new  Itemdata[jsonArray.length()];
                                             for (int i = 0; i < jsonArray.length(); i++) {
                                                 JSONObject jsonObject = jsonArray.getJSONObject(i);
                                                 String jopname = jsonObject.getString("jop_name");
                                                 String jopdesc = jsonObject.getString("jop_describtion");
                                                 String numberworker = jsonObject.getString("number_workers");
                                                 String namecompany = jsonObject.getString("name");
                                                 items[i] = new Itemdata(namecompany,jopname, jopdesc,numberworker,R.drawable.a,idCompany);
                                             }
                                             //   Adapter adapter = new Adapter(MainActivity.this,arrayList_opp);
                                             //   recyclerView.setAdapter(adapter);
                                             recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                             MyAdapter myAdapter=new MyAdapter(items,MainActivity.this,idworker,idcompany,id_email);
                                             recyclerView.setAdapter(myAdapter);
                                             recyclerView.setItemAnimator(new DefaultItemAnimator());
                                         } catch (JSONException e) {
                                             Toast.makeText(getApplicationContext(),"something  is error",Toast.LENGTH_LONG).show();
                                         }
                                     }
                                 }, new Response.ErrorListener() {
                             @Override
                             public void onErrorResponse(VolleyError error) {
                                 Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_LONG).show();

                             }
                         });
                         requestQueue.add(request);

                         //new gridLayoutManager(this,2)

                     }catch (Exception e){}


                 }
                 else  if(mViewPager.getCurrentItem()==1)
                 {
                     name_profile =(TextView)findViewById(R.id.name_profile);
                         email=(TextView)findViewById(R.id.email_profile);
                         phone=(TextView)findViewById(R.id.phone_profile);
                         work=(TextView)findViewById(R.id.work_profile);
                         address=(TextView)findViewById(R.id.address_profile);
                         study=(TextView)findViewById(R.id.study_profile);
                         qualification=(ListView)findViewById(R.id.list_profile);

                          String Url = "http://my-app-ammar.000webhostapp.com/get_profile.php";
                         com.android.volley.toolbox.StringRequest stringRequest = new com.android.volley.toolbox.StringRequest(Request.Method.POST, Url, new Response.Listener<String>() {
                          //   @SuppressLint("SetTextI18n")
                             @Override
                             public void onResponse(String response) {
                                 try {
                                     JSONObject jsonObject = new JSONObject(response);

                                     email.setText(jsonObject.getString("email"));
                                     name_profile.setText(jsonObject.getString("first_name") +" "+ jsonObject.getString("last_name"));
                                     phone.setText(jsonObject.getString("phone"));
                                     address.setText(jsonObject.getString("address"));
                                     work.setText(jsonObject.getString("type_work"));
                                     study.setText(jsonObject.getString("studying"));



                                 }catch (Exception e)
                                 {
                                     Toast.makeText(getApplicationContext(),"something  is error",Toast.LENGTH_LONG).show();
                                 }

                             }
                         }, new Response.ErrorListener() {
                             @Override
                             public void onErrorResponse(VolleyError error) {
                                 Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_LONG).show();
                             }
                         }){
                             @Override
                             protected Map<String, String> getParams() {
                                 Map<String,String> params = new java.util.HashMap<>();

                                 params.put("id_email",id_email);

                                 return params;
                             }
                         };
                         RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                         requestQueue.add(stringRequest);

                     ///          get all qualification

                     String Urlqual = "http://my-app-ammar.000webhostapp.com/get_qualification.php";
                     com.android.volley.toolbox.StringRequest stringRequestqual = new com.android.volley.toolbox.StringRequest(Request.Method.POST, Urlqual, new Response.Listener<String>() {
                         @Override
                         public void onResponse(String response) {
                             try {
                                 ArrayList<String> values = new ArrayList<String>();
                                 JSONArray jsonArray = new JSONArray(response);
                                 for (int i = 0; i < jsonArray.length(); i++) {
                                     JSONObject jsonObject = jsonArray.getJSONObject(i);
                                     values.add(jsonObject.getString("name_qualification"));
                                 }
                                 ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1, values);

                                 qualification.setAdapter(arrayAdapter);

                             }catch (Exception e)
                             {
                                 Toast.makeText(getApplicationContext(),"something  is error",Toast.LENGTH_LONG).show();
                             }

                         }




                     }, new Response.ErrorListener() {
                         @Override
                         public void onErrorResponse(VolleyError error) {
                             Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_LONG).show();
                         }
                     }){
                         @Override
                         protected Map<String, String> getParams() {
                             Map<String,String> params = new java.util.HashMap<>();

                             params.put("id_email",id_email);

                             return params;
                         }
                     };


                     RequestQueue requestQueuequal = Volley.newRequestQueue(getApplicationContext());
                     requestQueuequal.add(stringRequestqual);


                 }
                 else  if(mViewPager.getCurrentItem()==2)
                 {
                     final int[] t = {0};
                     final Itemdata_notify[][] itemss = new Itemdata_notify[1][100];
                     final RecyclerView recyclerView=(RecyclerView)findViewById(R.id.list_recycler_notify);
                     String Urlcomment = "http://my-app-ammar.000webhostapp.com/get_comment.php";
                     com.android.volley.toolbox.StringRequest stringRequestcomment = new com.android.volley.toolbox.StringRequest(Request.Method.POST, Urlcomment, new Response.Listener<String>() {
                         @Override
                         public void onResponse(String response) {
                             try {

                                 JSONArray jsonArray = new JSONArray(response);
                                 Itemdata_notify []items = new  Itemdata_notify[jsonArray.length()];
                                 for (int i = 0; i < jsonArray.length(); i++) {
                                     JSONObject jsonObject = jsonArray.getJSONObject(i);
                                     String comm = jsonObject.getString("comment");
                                     String nameworker = jsonObject.getString("name_worker");
                                     String jname = jsonObject.getString("job_name");

                                     items[t[0]] = new Itemdata_notify("Comment From "+nameworker,comm,"On Post "+jname);
                                     itemss[0][t[0]]=items[t[0]];
                                     t[0]++;
                                 }





                             }catch (Exception e)
                             {
                                 Toast.makeText(getApplicationContext(),"something  is error",Toast.LENGTH_LONG).show();
                             }

                         }

                     }, new Response.ErrorListener() {
                         @Override
                         public void onErrorResponse(VolleyError error) {
                             Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_LONG).show();
                         }
                     }){
                         @Override
                         protected Map<String, String> getParams() {
                             Map<String,String> params = new java.util.HashMap<>();

                             params.put("idcompany",idcompany);

                             return params;
                         }
                     };


                     RequestQueue requestQueuecomment = Volley.newRequestQueue(getApplicationContext());
                     requestQueuecomment.add(stringRequestcomment);

                     String Urlorder = "http://my-app-ammar.000webhostapp.com/get_order.php";
                     com.android.volley.toolbox.StringRequest stringRequestorder = new com.android.volley.toolbox.StringRequest(Request.Method.POST, Urlorder, new Response.Listener<String>() {
                         @Override
                         public void onResponse(String response) {
                             try {

                                 JSONArray jsonArray = new JSONArray(response);
                                 Itemdata_notify []items = new  Itemdata_notify[jsonArray.length()];
                                 for (int i = 0; i < jsonArray.length(); i++) {
                                     JSONObject jsonObject = jsonArray.getJSONObject(i);
                                     String details = jsonObject.getString("details");
                                     String nameworker = jsonObject.getString("name_worker");
                                     String st_worker = jsonObject.getString("study_worker");
                                     String jname = jsonObject.getString("job_name");

                                     items[i] = new Itemdata_notify("Order From "+nameworker,details+" , he is study "+st_worker,"For Post "+jname);
                                     itemss[0][t[0]]=items[i];
                                     t[0]++;
                                 }
                                 Itemdata_notify []items2 = new Itemdata_notify[t[0]];
                                 for(int j=0;j<t[0];j++)
                                 {
                                     items2[j] = itemss[0][j];


                                 }
                                 recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                                 MyAdpter_notify myAdapter=new MyAdpter_notify(items2,MainActivity.this);
                                 recyclerView.setAdapter(myAdapter);
                                 recyclerView.setItemAnimator(new DefaultItemAnimator());




                             }catch (Exception e)
                             {
                                 Toast.makeText(getApplicationContext(),"something  is error",Toast.LENGTH_LONG).show();
                             }

                         }

                     }, new Response.ErrorListener() {
                         @Override
                         public void onErrorResponse(VolleyError error) {
                             Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_LONG).show();
                         }
                     }){
                         @Override
                         protected Map<String, String> getParams() {
                             Map<String,String> params = new java.util.HashMap<>();

                             params.put("idcompany",idcompany);

                             return params;
                         }
                     };


                     RequestQueue requestQueueorder = Volley.newRequestQueue(getApplicationContext());
                     requestQueueorder.add(stringRequestorder);


                     dl.closeDrawers();
                     dl.animate();
                 }
                 }


             @Override
             public void onPageScrollStateChanged(int state) {
                // Toast.makeText(getApplicationContext(),"onPageScrollStateChanged",Toast.LENGTH_SHORT).show();
             }
         });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_nav,menu);
        SearchManager searchManager=(SearchManager)getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView=(SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        final Context co=this;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(co,query,Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Toast.makeText(co,newText,Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        return  true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    public void posts_company(MenuItem item) {
        mViewPager.setCurrentItem(0);
        try {
            final RecyclerView recyclerView=(RecyclerView)findViewById(R.id.list_recycler);
            recyclerView.setHasFixedSize(true);

            requestQueue = Volley.newRequestQueue(getApplicationContext());


            String url = "http://my-app-ammar.000webhostapp.com/getall_opportunity.php";
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                        @Override
                        public void onResponse(JSONObject response) {
                            try {

                                JSONArray jsonArray = response.getJSONArray("sss");
                                Itemdata []items = new  Itemdata[jsonArray.length()];
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String jopname = jsonObject.getString("jop_name");
                                    String jopdesc = jsonObject.getString("jop_describtion");
                                    String numberworker = jsonObject.getString("number_workers");
                                    String namecompany = jsonObject.getString("name");
                                    items[i] = new Itemdata(namecompany,jopname, jopdesc,numberworker,R.drawable.a,idCompany);
                                }
                                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                MyAdapter myAdapter=new MyAdapter(items,MainActivity.this,idworker,idcompany,id_email);
                                recyclerView.setAdapter(myAdapter);
                                recyclerView.setItemAnimator(new DefaultItemAnimator());
                            } catch (JSONException e) {
                                Toast.makeText(getApplicationContext(),"something  is error",Toast.LENGTH_LONG).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_LONG).show();

                }
            });
            requestQueue.add(request);

            //new gridLayoutManager(this,2)

        }catch (Exception e){}

        dl.closeDrawers();
        dl.animate();
    }

    public void worker_profile(MenuItem item) {
        mViewPager.setCurrentItem(0);
        try {
            final RecyclerView recyclerView=(RecyclerView)findViewById(R.id.list_recycler);
            recyclerView.setHasFixedSize(true);

            requestQueue = Volley.newRequestQueue(getApplicationContext());


            String url = "http://my-app-ammar.000webhostapp.com/getinfo_worker.php";
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                        @Override
                        public void onResponse(JSONObject response) {
                            try {

                                JSONArray jsonArray = response.getJSONArray("sss");
                                Itemdata_profile []items = new  Itemdata_profile[jsonArray.length()];
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String first_name = jsonObject.getString("first_name");
                                    String last_name = jsonObject.getString("last_name");
                                    String Country = jsonObject.getString("Country");
                                    String studying = jsonObject.getString("studying");
                                    items[i] = new Itemdata_profile(first_name +" "+last_name, Country,studying,R.drawable.a);
                                }

                                recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
                                My_Adapter_Profile myAdapter=new My_Adapter_Profile(items,MainActivity.this,false);
                                recyclerView.setAdapter(myAdapter);
                                recyclerView.setItemAnimator(new DefaultItemAnimator());
                            } catch (JSONException e) {
                                Toast.makeText(getApplicationContext(),"something  is error",Toast.LENGTH_LONG).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_LONG).show();

                }
            });
            requestQueue.add(request);

            //new gridLayoutManager(this,2)

        }catch (Exception e){}
        //new GridLayoutManager(this,2)
        dl.closeDrawers();
        dl.animate();
    }

    public void project(MenuItem item) {
        mViewPager.setCurrentItem(0);
        try {
            final RecyclerView recyclerView=(RecyclerView)findViewById(R.id.list_recycler);
            recyclerView.setHasFixedSize(true);

            requestQueue = Volley.newRequestQueue(getApplicationContext());


            String url = "http://my-app-ammar.000webhostapp.com/getall_projectn.php";
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                        @Override
                        public void onResponse(JSONObject response) {
                            try {

                                JSONArray jsonArray = response.getJSONArray("sss");
                                itemdata_project []items = new  itemdata_project[jsonArray.length()];
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String first_name = jsonObject.getString("first_name");
                                    String last_name = jsonObject.getString("last_name");
                                    String describtion = jsonObject.getString("describtion");
                                    String cost = jsonObject.getString("cost");
                                    String time = jsonObject.getString("time");
                                    items[i] = new itemdata_project(first_name,last_name, cost,describtion,time,R.drawable.a);
                                }
                                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                My_Adapter_project myAdapter=new My_Adapter_project(items,MainActivity.this);
                                recyclerView.setAdapter(myAdapter);
                                recyclerView.setItemAnimator(new DefaultItemAnimator());
                            } catch (JSONException e) {
                                Toast.makeText(getApplicationContext(),"something  is error",Toast.LENGTH_LONG).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_LONG).show();

                }
            });
            requestQueue.add(request);

            //new gridLayoutManager(this,2)

        }catch (Exception e){}
        dl.closeDrawers();
        dl.animate();
    }

    public void company(MenuItem item) {
        mViewPager.setCurrentItem(0);

        try {
            final RecyclerView recyclerView=(RecyclerView)findViewById(R.id.list_recycler);
            recyclerView.setHasFixedSize(true);

            requestQueue = Volley.newRequestQueue(getApplicationContext());


            String url = "http://my-app-ammar.000webhostapp.com/getinfo_company.php";
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                        @Override
                        public void onResponse(JSONObject response) {
                            try {

                                JSONArray jsonArray = response.getJSONArray("sss");
                                Itemdata_profile []items = new  Itemdata_profile[jsonArray.length()];
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String name = jsonObject.getString("name");
                                    String specializing = jsonObject.getString("specializing");
                                    String Country = jsonObject.getString("Country");

                                    items[i] = new Itemdata_profile(name, Country,specializing,R.drawable.a);
                                }
                                recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
                                My_Adapter_Profile myAdapter=new My_Adapter_Profile(items,MainActivity.this,false);
                                recyclerView.setAdapter(myAdapter);
                                recyclerView.setItemAnimator(new DefaultItemAnimator());
                            } catch (JSONException e) {
                                Toast.makeText(getApplicationContext(),"something  is error",Toast.LENGTH_LONG).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_LONG).show();

                }
            });
            requestQueue.add(request);

            //new gridLayoutManager(this,2)

        }catch (Exception e){}
        //new GridLayoutManager(this,2)

        dl.closeDrawers();
        dl.animate();
    }

    public void groups(MenuItem item) {

        mViewPager.setCurrentItem(0);
        try {
            final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list_recycler);
            recyclerView.setHasFixedSize(true);

            requestQueue = Volley.newRequestQueue(getApplicationContext());


            String url = "http://my-app-ammar.000webhostapp.com/getall_group.php";
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                        @Override
                        public void onResponse(JSONObject response) {
                            try {

                                JSONArray jsonArray = response.getJSONArray("sss");
                                Itemdata_profile[] items = new Itemdata_profile[jsonArray.length()];
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String name = jsonObject.getString("name");
                                    String describtion = jsonObject.getString("describtion");
                                    String count = jsonObject.getString("idcount");
                                    items[i] = new Itemdata_profile(name, describtion, count, R.drawable.a);
                                }
                                //[{"name":"PVM","describtion":"work in building","idcount":"3"}

                                recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
                                My_Adapter_Profile myAdapter = new My_Adapter_Profile(items, MainActivity.this, false);
                                recyclerView.setAdapter(myAdapter);
                                recyclerView.setItemAnimator(new DefaultItemAnimator());
                            } catch (JSONException e) {
                                Toast.makeText(getApplicationContext(), "something  is error", Toast.LENGTH_LONG).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();

                }
            });
            requestQueue.add(request);

            dl.closeDrawers();
            dl.animate();
        }catch (Exception e){}

    }

    public void log_out(MenuItem item) {
        dl.closeDrawers();
        dl.animate();
    }

    public void settings(MenuItem item) {
        Intent i = new Intent(this,SettingsActivity.class);
        startActivity(i);
        dl.closeDrawers();
        dl.animate();
    }



}
