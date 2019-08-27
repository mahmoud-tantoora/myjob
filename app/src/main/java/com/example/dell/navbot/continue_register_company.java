package com.example.dell.navbot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class continue_register_company extends AppCompatActivity {
    EditText Specializing ;
    EditText Country;
    EditText Place;
    EditText address;
    EditText work_description;
    RequestQueue requestQueue;
    Button create_company;
    ImageView profileimage;
    final int PICK_IMAGE_REQUST=234;
    private Uri file_path;
    String id_email,idworker,idcompany,mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.continue_register_company);
        Specializing=(EditText)findViewById(R.id.specializing);
        Country=(EditText)findViewById(R.id.country_company);
        Place=(EditText)findViewById(R.id.place);
        address=(EditText)findViewById(R.id.address_company);
        work_description=(EditText)findViewById(R.id.work_description);
        create_company=(Button)findViewById(R.id.create_company);
        profileimage=(ImageView)findViewById(R.id.profileimage_company);
        id_email = getIntent().getStringExtra("id_email");
        idworker = getIntent().getStringExtra("idworker");
        idcompany = getIntent().getStringExtra("idcompany");
        mobile = getIntent().getStringExtra("mobile");

    }
    public void create_company(View view)
    {
        try
        {
            final ProgressDialog progressDialog = new ProgressDialog(continue_register_company.this,
                    R.style.AppTheme_Dark_Dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Creating Account Company ...");
            progressDialog.show();

            String Url = "http://my-app-ammar.000webhostapp.com/continue_register_company.php";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try{
                        JSONObject jsonObject = new JSONObject(response);
                        String  success = jsonObject.getString("response");
                        if(!success.equals("Error"))
                        {
                            Toast.makeText(getApplicationContext(),"Successfully",Toast.LENGTH_SHORT).show();


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

                    params.put("specializing",Specializing.getText().toString());
                    params.put("Country",Country.getText().toString());
                    params.put("place",Place.getText().toString());
                    params.put("address",address.getText().toString());
                    params.put("work_description",work_description.getText().toString());
                    params.put("mobile",mobile);

                    params.put("idcompany",idcompany);

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
