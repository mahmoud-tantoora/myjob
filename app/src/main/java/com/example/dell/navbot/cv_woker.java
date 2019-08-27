package com.example.dell.navbot;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class cv_woker extends AppCompatActivity {
   EditText firstname;
    EditText lastname;

    EditText address;
    EditText country;
    EditText typework,timework;
    String   stud,qual ;
    Spinner study;
    String idworker,mobile;
    Spinner qualification;
    RequestQueue requestQueue;
    Button create_cv;
    ImageView profileimage;
    final int PICK_IMAGE_REQUST=234;
    private Uri file_path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cv_worker);
        firstname=(EditText)findViewById(R.id.firstname);
        lastname=(EditText)findViewById(R.id.lastname);
        address=(EditText)findViewById(R.id.address);
        typework=(EditText)findViewById(R.id.typework);
        timework=(EditText)findViewById(R.id.timework);
        country=(EditText)findViewById(R.id.country);
       study=(Spinner)findViewById(R.id.study);
        qualification=(Spinner)findViewById(R.id.Qualification);
        create_cv=(Button)findViewById(R.id.create_cv);
        profileimage=(ImageView)findViewById(R.id.profileimage);
        String id_email = getIntent().getStringExtra("id_email");
        idworker = getIntent().getStringExtra("idworker");
        String idcompany = getIntent().getStringExtra("idcompany");
         mobile = getIntent().getStringExtra("mobile");

        ///  get all study to chose one
        if(getall_study()==false)getall_study();


        ///  get all qualification to chose one
        if(getall_qualification()==false)getall_qualification();



    }

    public boolean getall_study()
    {
        final boolean[] f = {false};
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url_study = "http://my-app-ammar.000webhostapp.com/getall_study.php";
        JsonObjectRequest request_study = new JsonObjectRequest(Request.Method.GET, url_study, null,
                new Response.Listener<JSONObject>() {
                    //    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray jsonArray = response.getJSONArray("sss");
                            ArrayList<String> values = new ArrayList<String>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                values.add(jsonObject.getString("specializing"));

                            }
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item, values);

                            study.setAdapter(arrayAdapter);
                            f[0] =true;

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
        requestQueue.add(request_study);
   return f[0];
    }
    public boolean getall_qualification()
    {
        final boolean[] f = {false};
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url_qualification = "http://my-app-ammar.000webhostapp.com/getall_qualification.php";
        JsonObjectRequest request_qualification = new JsonObjectRequest(Request.Method.GET, url_qualification, null,
                new Response.Listener<JSONObject>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray jsonArray = response.getJSONArray("sss");
                            ArrayList<String> values = new ArrayList<String>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                values.add(jsonObject.getString("name_qualification"));
                            }
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item, values);

                            qualification.setAdapter(arrayAdapter);
                            f[0]=true;

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
        requestQueue.add(request_qualification);



        return f[0];
    }

    public void create_cv(View view)
    {
        try
        {
            final ProgressDialog progressDialog = new ProgressDialog(cv_woker.this,
                    R.style.AppTheme_Dark_Dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Creating Account...");
            progressDialog.show();
            final boolean[] f = {false};

            stud = study.getSelectedItem().toString();
            qual = qualification.getSelectedItem().toString();

            String Url = "http://my-app-ammar.000webhostapp.com/insert_cv.php";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try{
                        JSONObject jsonObject = new JSONObject(response);
                        String  success = jsonObject.getString("response");
                        if(!success.equals("Error"))
                        {
                            Toast.makeText(getApplicationContext(),"Successfully",Toast.LENGTH_SHORT).show();
                            f[0] =true;


                        }else {Toast.makeText(getApplicationContext(),"The Information Not Correct",Toast.LENGTH_SHORT).show();}

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
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<>();

                    params.put("firstname",firstname.getText().toString());
                    params.put("lastname",lastname.getText().toString());
                    params.put("mobile",mobile);
                    params.put("address",address.getText().toString());
                    params.put("typework",typework.getText().toString());
                    params.put("timework",timework.getText().toString());
                    params.put("country",country.getText().toString());
                    params.put("idworker",idworker);
                    params.put("stud",stud);
                    params.put("qual",qual);

                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {

                            progressDialog.dismiss();
                        }
                    }, 3000);
            int image =profileimage.getId();

        }
        catch (Exception ex)
        {

        }
    }

    public void addimgae(View view) {
        showfileshosen();
    }

     private void showfileshosen()
     {
         Intent intent=new Intent();
         intent.setType("image/*");
         intent.setAction(Intent.ACTION_GET_CONTENT);
         startActivityForResult(Intent.createChooser(intent,"select image to your profile"),PICK_IMAGE_REQUST);
     }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if((requestCode==PICK_IMAGE_REQUST) && (resultCode==RESULT_OK) && (data!=null) && (data.getData()!=null))
        {
            file_path=data.getData();
            try
            {
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),file_path);
                profileimage.setImageBitmap(bitmap);
            }
            catch (Exception e)
            {

            }
        }
    }
    public  String getImagPath(Uri uri)
    {
        String[] projection ={MediaStore.Images.Media.DATA};
        Cursor cursor=getContentResolver().query(uri,projection,null,null,null);
        if(cursor==null)
        {
            return  null;
        }
        int columindex=cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String s=cursor.getString(columindex);
        cursor.close();
        return  s;
    }
}
