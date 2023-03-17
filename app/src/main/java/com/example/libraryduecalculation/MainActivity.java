package com.example.libraryduecalculation;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.DatePickerDialog;


import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText bookname, authorname, charge;
    TextView i_date, s_date, r_date, dueTV;
    Button date_i, date_s, calculate, date_r;
    DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        date_i = findViewById(R.id.date_i);
        date_s = findViewById(R.id.date_s);
        i_date = findViewById(R.id.i_date);
        s_date = findViewById(R.id.s_date);
        bookname = findViewById(R.id.bookname);
        authorname = findViewById(R.id.authorname);
        charge = findViewById(R.id.charge);
        calculate = findViewById(R.id.calculate);
        date_r = findViewById(R.id.date_r);
        r_date = findViewById(R.id.r_date);
        dueTV = findViewById(R.id.dueTV);

        date_i.setOnClickListener(this);
        date_s.setOnClickListener(this);
        date_r.setOnClickListener(this);
        calculate.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.date_r:
                // calender class's instance and get current date , month and year from calender
                Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                r_date.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
                break;
            case R.id.date_i:
                // calender class's instance and get current date , month and year from calender
                c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR); // current year
                mMonth = c.get(Calendar.MONTH); // current month
                mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                i_date.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
                break;
            case R.id.date_s:
                // calender class's instance and get current date , month and year from calender
                c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR); // current year
                mMonth = c.get(Calendar.MONTH); // current month
                mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                s_date.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
                break;
            case R.id.calculate:
                if (bookname.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Enter book name", Toast.LENGTH_SHORT).show();
                } else if (authorname.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Enter book authotname", Toast.LENGTH_SHORT).show();
                } else if (i_date.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Enter issue date", Toast.LENGTH_SHORT).show();
                } else if (r_date.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Enter return date", Toast.LENGTH_SHORT).show();
                } else if (s_date.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Enter submit date", Toast.LENGTH_SHORT).show();
                } else if (charge.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Enter Charge", Toast.LENGTH_SHORT).show();
                } else {
                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    String issue_date = i_date.getText().toString();
                    String return_date = r_date.getText().toString();
                    String submit_date = s_date.getText().toString();

                    Date issueD = null, returnD = null, submitD = null;

                    try {
                        issueD = format.parse(issue_date);
                        returnD = format.parse(return_date);
                        submitD = format.parse(submit_date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if (issueD.after(returnD) || issueD.after(submitD)) {
                        Toast.makeText(this, "return date and submit date can't before issue date", Toast.LENGTH_SHORT).show();
                    }else{
                        if (returnD.before(submitD)) {
                            // calculate due
                            long difference = Math.abs(submitD.getTime() - returnD.getTime());
                            long diff_days = difference / (24 * 60 * 60 * 1000);
                            long x = Long.parseLong(charge.getText().toString());
                            long due = diff_days * x;
                            dueTV.setText("Due is " + due + "Rs.");
                        } else {
                            dueTV.setText("Due is 0");
                        }
                    }
                }
                break;
            default:
                Toast.makeText(this, "something went wrong", Toast.LENGTH_SHORT).show();
        }
    }
}