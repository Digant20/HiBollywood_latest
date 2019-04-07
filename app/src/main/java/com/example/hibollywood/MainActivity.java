package com.example.hibollywood;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Button btn_login;
    private EditText email;
    private TextInputEditText password;

    private TextView signUpText;
    private static String URL_LOGIN = "http://192.168.43.130/bollywood/login.php";
    private ProgressBar progressBar;
    SessionManager sessionManager;

    String memail, mpass;

    //html string code
    //private final String htmlText="<body><h5>BollyWoodConnect</h5></body>";

    @Override
    protected void onCreate(Bundle savedInstanceState) {


//        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
//        getSupportActionBar().hide(); // hide the title bar
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //html string code
        // TextView txtV=(TextView) findViewById(R.id.text);
        // txtV.setText(Html.fromHtml(htmlText));
        sessionManager = new SessionManager(this);
        email = (EditText) findViewById(R.id.email);
        password = (TextInputEditText) findViewById(R.id.pass);

        signUpText = (TextView) findViewById(R.id.signUpText);
        progressBar = (ProgressBar) findViewById(R.id.pbar);

        btn_login = (Button) findViewById(R.id.signin);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                memail = email.getText().toString().trim();
                mpass = password.getText().toString().trim();
                if (!memail.isEmpty() || !mpass.isEmpty()) {
                    Login(memail, mpass);
                } else {
                    email.setError("Please insert email");
                    password.setError("Please insert password");
                }


            }
        });
        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SignUp.class));

            }
        });


    }

    private void Login(final String email, final String password) {
        progressBar.setVisibility(View.VISIBLE);
        btn_login.setVisibility(View.GONE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String name = jsonObject.getString("name").trim();
                            progressBar.setVisibility(View.GONE);

                            String pass = jsonObject.getString("cpassword").trim();
                            if(pass.equals(password)){
                                Intent intent = new Intent(MainActivity.this, NavDrawer.class);
                                startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Intent intent = new Intent(MainActivity.this, NavDrawer.class);
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//                            String success = jsonObject.getString("success");
//                            JSONArray jsonArray = jsonObject.getJSONArray("Login");
//                            if (success.equals("1")) {
//                                for (int i = 0; i < jsonArray.length(); i++) {
//                                    JSONObject object = jsonArray.getJSONObject(i);
//                                    String email = object.getString("email").trim();
//                                    String pass = object.getString("pass").trim();
//                                    sessionManager.createSession(email,pass);
//                                    Intent intent = new Intent(MainActivity.this, NavDrawer.class);
//                                    startActivity(intent);
//                                    progressBar.setVisibility(View.GONE);
//                                }
//                            }
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            Toast.makeText(MainActivity.this, "login error." + e.toString(), Toast.LENGTH_SHORT).show();
//
//                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "login error" + error.toString(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        btn_login.setVisibility(View.VISIBLE);


                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("email", email);
                params.put("password", password);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

}