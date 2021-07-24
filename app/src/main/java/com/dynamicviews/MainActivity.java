package com.dynamicviews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout layoutList;
    Button buttonAdd;
    Button buttonSubmitList;


    List<String> teamList = new ArrayList<>();
    ArrayList<Cricketer> cricketersList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layoutList = findViewById(R.id.layout_list);
        buttonAdd = findViewById(R.id.button_add);
        buttonSubmitList = findViewById(R.id.button_submit_list);

        buttonAdd.setOnClickListener(this);
        buttonSubmitList.setOnClickListener(this);


        teamList.add("Team");
        teamList.add("India");
        teamList.add("Australia");
        teamList.add("England");

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.button_add:

                addView();

                break;

            case R.id.button_submit_list:

                if (checkIfValidAndRead()) {

                    Intent intent = new Intent(MainActivity.this, ActivityCricketers.class);
                    Bundle bundle = new Bundle();


                    bundle.putSerializable("list", cricketersList);
                    intent.putExtras(bundle);
                    startActivity(intent);

                }

                break;

        }


    }

    private boolean checkIfValidAndRead() {
        cricketersList.clear();
        boolean result = true;
        int[] x = new int[20];
        int add = 0;
        int N = 0;
        double avg = 0;
        double SD = 0;
        double CV = 0;
        int sum = 0;
        int max = 0;
        int min =Integer.MAX_VALUE;



        for (int i = 0; i < layoutList.getChildCount(); i++) {

            View cricketerView = layoutList.getChildAt(i);

            EditText editTextName = (EditText) cricketerView.findViewById(R.id.edit_cricketer_name);


            if (!editTextName.getText().toString().equals("")) {


                add += Integer.parseInt(editTextName.getText().toString());
                x[i] = Integer.parseInt(editTextName.getText().toString());
                N++;

                avg = add / N;
                sum += x[i] - avg;

                if (x[i] > max) {
                    max = x[i];
                }

                if (x[i] < min) {
                    min = x[i];
                }


            } else {
                result = false;
                break;
            }


        }


        SD = Math.sqrt((sum * sum) / N);
        CV = (SD / avg) * 100;


        String s = String.valueOf(min);
        String TotalNo = String.valueOf(max);
        String Avg = String.valueOf(avg);
        String StandardDerivasion = String.valueOf(SD);
        String cv = String.valueOf(CV);

        Cricketer alladd = new Cricketer();
        Cricketer totalNumberOfGm = new Cricketer();
        Cricketer AVrg = new Cricketer();
        Cricketer Sderivasion = new Cricketer();
        Cricketer cV = new Cricketer();

        AVrg.setCricketerName("Avarage = " + Avg);
        cV.setCricketerName("CV  % = " + cv + " %");

        totalNumberOfGm.setCricketerName("Max - " + TotalNo);
        alladd.setCricketerName("Min - " + s );


//        cricketersList.add(alladd);
        cricketersList.add(AVrg);
        cricketersList.add(cV);
        cricketersList.add(totalNumberOfGm);

        cricketersList.add(alladd);



        if (cricketersList.size() == 0) {
            result = false;
            Toast.makeText(this, "Add Cricketers First!", Toast.LENGTH_SHORT).show();
        } else if (!result) {
            Toast.makeText(this, "Enter All Details Correctly!", Toast.LENGTH_SHORT).show();
        }


        return result;
    }

    private void addView() {

        final View cricketerView = getLayoutInflater().inflate(R.layout.row_add_cricketer, null, false);

        EditText editText = (EditText) cricketerView.findViewById(R.id.edit_cricketer_name);

        ImageView imageClose = (ImageView) cricketerView.findViewById(R.id.image_remove);

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, teamList);


        imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeView(cricketerView);
            }
        });

        layoutList.addView(cricketerView);

    }

    private void removeView(View view) {

        layoutList.removeView(view);

    }
}
