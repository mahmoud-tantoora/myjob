package com.example.dell.navbot;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class massage extends AppCompatActivity {
    EditText msg;
    TextView name,study;
    Button send;
    String id_worker,id_company,id_compant_recv,jobname,id_email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.massage);
        msg=(EditText)findViewById(R.id.message_content);
        name=(TextView)findViewById(R.id.name_msg);
        study=(TextView)findViewById(R.id.study_msg);
        send=(Button)findViewById(R.id.msg_send);
        Intent in = getIntent();
        id_email = in.getStringExtra("id_email");
        id_worker = in.getStringExtra("idworker");
        id_company = in.getStringExtra("idcompany");
        id_compant_recv = in.getStringExtra("idcompany_recv");
        jobname = in.getStringExtra("name_job");

        String Url = "http://my-app-ammar.000webhostapp.com/get_profile.php";
        com.android.volley.toolbox.StringRequest stringRequest = new com.android.volley.toolbox.StringRequest(Request.Method.POST, Url, new Response.Listener<String>() {
            //   @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);


                    name.setText(jsonObject.getString("first_name") +" "+ jsonObject.getString("last_name"));
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

    }

    public void btn_msg_send(View view)
    {
        String Url = "http://my-app-ammar.000webhostapp.com/insert_order.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    String  success = jsonObject.getString("response");
                    if(!success.equals("ER"))
                    {
                        Toast.makeText(getApplicationContext(),"Successfully",Toast.LENGTH_SHORT).show();
                        msg.setText("");
                      /*  Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
*/
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

                params.put("details",msg.getText().toString());
                params.put("name_worker",name.getText().toString());
                params.put("study_worker",study.getText().toString());
                params.put("id_worker",id_worker);
                params.put("id_company_recv",id_compant_recv);
                params.put("job_name",jobname);



                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }


}
