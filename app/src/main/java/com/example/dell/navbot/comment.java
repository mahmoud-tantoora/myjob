package com.example.dell.navbot;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import butterknife.BindView;

public class comment extends AppCompatActivity {
    EditText comment = (EditText) findViewById(R.id.comment_text);
    @BindView(R.id.comment_send) Button Send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       /* Intent intent = new Intent(getApplicationContext(), login.class);
        startActivityForResult(intent, 1);
        finish();
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);*/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment);
        Toast.makeText(getApplicationContext(),"iam here ",Toast.LENGTH_SHORT).show();
        final String content_comment = comment.getText().toString();
         Intent in = getIntent();
         final String id_worker = in.getStringExtra("idworker");
        final String id_company = in.getStringExtra("idcompany");
        final String id_compant_recv = in.getStringExtra("idcompany_recv");
        final String jobname = in.getStringExtra("name_job");


        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Url = "http://my-app-ammar.000webhostapp.com/insert_comment.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String  success = jsonObject.getString("response");
                            if(!success.equals("ER"))
                            {
                                Toast.makeText(getApplicationContext(),"Successfully",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);

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

                        params.put("comment",content_comment);
                        params.put("id_worker",id_worker);
                        params.put("id_company",id_company);
                        params.put("id_company_recv",id_compant_recv);
                        params.put("job_name",jobname);


                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(stringRequest);
            }
        });








    }
}
