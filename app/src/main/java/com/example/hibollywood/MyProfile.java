package com.example.hibollywood;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MyProfile extends AppCompatActivity {
    private Spinner spinner,exp,lang,state;
    private RadioGroup radioGroup;
    private RadioButton male,female,other;
    private EditText dob;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

        spinner = findViewById(R.id.spinner);
        String[] items = new String[]{"Singer", "Dancer", "Actor", "Model", "Guitarist"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_selectable_list_item, items);
        spinner.setAdapter(adapter);

        //spinner for experience
        exp = findViewById(R.id.exp);
        String[] items1 = new String[]{"New Face", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_selectable_list_item, items1);
        exp.setAdapter(adapter1);

        //languages spinner
        lang = findViewById(R.id.lang);
        String[] items2 = new String[]{"Hindi", "English", "Tamil", "Telugu", "Malayalam", "Gujarati", "Urdu", "Marathi", "Odia", "Punjabi", "Bhojpuri"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_selectable_list_item, items2);
        lang.setAdapter(adapter2);

        //states spinner
        state = findViewById(R.id.lang);
        String[] items3 = new String[]{"Select State", "Agra", "Delhi", "Tamilnadu", "Bhubaneswar", "Bihar", "Mumbai", "Rajastan", "Kerela", "Kolkata", "Bangalore"};
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_selectable_list_item, items3);
        state.setAdapter(adapter3);


        male=findViewById(R.id.male);
        female=findViewById(R.id.female);
        other=findViewById(R.id.other);

        radioGroup=findViewById(R.id.radiogroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == male.getId()){
                    int maleId=checkedId;
                    Toast.makeText(MyProfile.this, +maleId, Toast.LENGTH_SHORT).show();
                }else if (checkedId == female.getId()){
                    int femaleId=checkedId;
                    Toast.makeText(MyProfile.this, +femaleId, Toast.LENGTH_SHORT).show();
                }else {
                    int otherId=checkedId;
                    Toast.makeText(MyProfile.this, +otherId, Toast.LENGTH_SHORT).show();
                }
            }
        });
         calendar= Calendar.getInstance();

       final DatePickerDialog.OnDateSetListener date=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);

            }
        };
       dob=findViewById(R.id.dob);
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(MyProfile.this,date,calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
                //copied code from updateLabel()
                String myFormat="MM/dd/yy";
                SimpleDateFormat sdf= new SimpleDateFormat(myFormat, Locale.US);
                dob.setText(sdf.format(calendar.getTime()));
            }
        });



    }

//    private void updateLabel() {
//        String myFormat="MM/dd/yy";
//        SimpleDateFormat sdf= new SimpleDateFormat(myFormat, Locale.US);
//        dob.setText(sdf.format(calendar.getTime()));
//    }
}
