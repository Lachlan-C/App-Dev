package com.example.unitconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.BreakIterator;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText input;
    TextView display1, display2, display3;

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();

        TextView unit1 = (TextView) findViewById(R.id.unit1);
        TextView unit2 = (TextView) findViewById(R.id.unit2);
        TextView unit3 = (TextView) findViewById(R.id.unit3);

        if (item == "Meter"){
            unit1.setText("Centimetre");
            unit2.setText("Foot");
            unit3.setText("Inch");
            display1.setText("");
            display2.setText("");
            display3.setText("");
        } else if (item == "Celsius"){
            unit1.setText("Fahrenheit");
            unit2.setText("Kelvin");
            unit3.setText("");
            display1.setText("");
            display2.setText("");
            display3.setText("");
        } else if (item == "Kilograms")        {
            unit1.setText("Gram");
            unit2.setText("Ounce(Oz)");
            unit3.setText("Pound(lb)");
            display1.setText("");
            display2.setText("");
            display3.setText("");
        } else {
            unit1.setText("");
            unit2.setText("");
            unit3.setText("");
            display1.setText("");
            display2.setText("");
            display3.setText("");
        }
    }

    public void onNothingSelected(AdapterView<?> arg0) {
    }

    public void lengthcalc(View view)
    {
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        String item = spinner.getSelectedItem().toString();
        if (item != "Meter"){
            Toast.makeText(getApplicationContext(),"Please Select Correct conversion Icon",Toast.LENGTH_LONG).show();
        } else {
            //Maths
            Float num = Float.parseFloat(input.getText().toString());
            display1.setText(String.format(Locale.US,"%.2f",num*100));
            display2.setText(String.format(Locale.US,"%.2f",num/0.3048));
            display3.setText(String.format(Locale.US,"%.2f",num/0.0254));
        }
    }

    public void weightcalc(View view)
    {
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        String item = spinner.getSelectedItem().toString();
        if (item != "Kilograms"){
            Toast.makeText(getApplicationContext(),"Please select the correct conversion icon",Toast.LENGTH_LONG).show();
        } else {
            //Maths
            Float num = Float.parseFloat(input.getText().toString());
            display1.setText(String.format(Locale.US,"%.2f",num*1000));
            display2.setText(String.format(Locale.US,"%.2f", num/0.02834952));
            display3.setText(String.format(Locale.US,"%.2f",num/0.45359237));
        }
    }

    public void tempcalc(View view)
    {
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        String item = spinner.getSelectedItem().toString();
        if (item != "Celsius"){
            Toast.makeText(getApplicationContext(),"Please Select Correct conversion Icon",Toast.LENGTH_LONG).show();
        } else {
            //Maths
            Float num = Float.parseFloat(input.getText().toString());

            display1.setText(String.format(Locale.US,"%.2f",(num*(9/5))+32));
            display2.setText(String.format(Locale.US,"%.2f",num+273.15));

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner choice = (Spinner) findViewById(R.id.spinner);
        choice.setOnItemSelectedListener(this);

        List<String> conversions = new ArrayList<String>();
        conversions.add("Meter");
        conversions.add("Celsius");
        conversions.add("Kilograms");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, conversions);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        choice.setAdapter(dataAdapter);

        input = findViewById(R.id.NumberInput);
        display1 = findViewById(R.id.output1);
        display2 = findViewById(R.id.output2);
        display3 = findViewById(R.id.output3);
    }
}