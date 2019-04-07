package com.example.hibollywood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {
    private EditText name, email, password, cpassword;
    private Button btn_register;
    private ProgressBar loading;
    private static String URL_REGISTER = "http://192.168.43.130/bollywood/reg.php";
    String username,useremail,userpassword,userconfpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        name = findViewById(R.id.nameText);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        cpassword = findViewById(R.id.cpassword);
        loading = findViewById(R.id.pbar);
        btn_register = findViewById(R.id.btnSignup);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Regist();

            }
        });

    }

    private void Regist() {
        loading.setVisibility(View.VISIBLE);
        btn_register.setVisibility(View.GONE);

        username = name.getText().toString().trim();
        useremail = this.email.getText().toString().trim();
        userpassword = this.password.getText().toString().trim();
        userconfpass = this.cpassword.getText().toString().trim();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(SignUp.this, "Registration success!"+response, Toast.LENGTH_LONG).show();
                        if(response.contains("success")){
                            Intent i = new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(i);
                        }
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//                            String success = jsonObject.getString("success");
//                            if (success.equals("1")) {
//                                Toast.makeText(SignUp.this, "Registration success!", Toast.LENGTH_LONG).show();
//                            }
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            Toast.makeText(SignUp.this, "Registration unsuccessfull!" + e.toString(), Toast.LENGTH_LONG).show();
//                            loading.setVisibility(View.GONE);
//                            btn_register.setVisibility(View.VISIBLE);
//                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SignUp.this, "Error"+error, Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
               Map<String, String> params=new HashMap<>();
               params.put("name",username);
                params.put("email",useremail);
                params.put("password",userpassword);
                params.put("cpassword",userconfpass);

                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }


}
