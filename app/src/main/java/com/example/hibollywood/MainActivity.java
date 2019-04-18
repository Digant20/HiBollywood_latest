package com.example.hibollywood;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
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

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private Button btn_login;
    private EditText email;
    private TextInputEditText password;


    private TextView signUpText;
    private static String URL_LOGIN = "http://192.168.43.130/bollywood/login.php";
    private ProgressBar progressBar;
    SessionManager sessionManager;

    private GoogleApiClient googleApiClient;
    private static final int RC_SIGN_IN = 1;
private  Button googlebtn;
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
        GoogleSignInOptions gso =  new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleApiClient=new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();



        googlebtn=findViewById(R.id.googlebtn);
        googlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent,RC_SIGN_IN);
            }
        });


        sessionManager = new SessionManager(this);
        if (sessionManager.isLoggedIn()){
            Intent i = new Intent(getApplicationContext(),NavDrawer.class);
            startActivity(i);
            finish();
        }
        email =  findViewById(R.id.email);
        password =  findViewById(R.id.pass);

        signUpText =  findViewById(R.id.signUpText);
        progressBar =  findViewById(R.id.pbar);

        btn_login =  findViewById(R.id.signin);
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

                            String success = jsonObject.getString("success");
                            if (success.equals("1")) {
//                                for (int i = 0; i < jsonArray.length(); i++) {
//                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String email = jsonObject.getString("email").trim();
                                    String pass = jsonObject.getString("pass").trim();
                                    sessionManager.createSession(email, pass);
                                    Intent intent = new Intent(MainActivity.this, NavDrawer.class);
                                    startActivity(intent);
                                    progressBar.setVisibility(View.GONE);
                                }
 //                           }
//                            progressBar.setVisibility(View.GONE);
//
//                            String pass = jsonObject.getString("cpassword").trim();
//                            if (pass.equals(password)) {
//                                Intent intent = new Intent(MainActivity.this, NavDrawer.class);
//                                startActivity(intent);
//                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "login error." + e.toString(), Toast.LENGTH_SHORT).show();
                        }
//                        Intent intent = new Intent(MainActivity.this, NavDrawer.class);
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

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RC_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }
    private void handleSignInResult(GoogleSignInResult result){
        if(result.isSuccess()){
            gotoProfile();
        }else{
            Toast.makeText(getApplicationContext(),"Sign in cancel",Toast.LENGTH_LONG).show();
        }
    }
    private void gotoProfile(){
        Intent intent=new Intent(this,NavDrawer.class);
        startActivity(intent);
    }
}