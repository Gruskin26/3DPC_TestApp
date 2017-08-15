package com.example.android.a3dpc_testapp;

import android.icu.math.BigDecimal;
import android.renderscript.Double2;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.blackcat.currencyedittext.CurrencyEditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public long getSpoolPrice() {
        CurrencyEditText spoolPr = (CurrencyEditText) findViewById(R.id.currencyEditText);
        long price = spoolPr.getRawValue();
        Log.d("v", "Price: C"+ price);
        return price;
    }

    public double getSpoolSize(){
        EditText spoolSzTxt = (EditText) findViewById(R.id.spMass);
        double spoolSize = Double.parseDouble(spoolSzTxt.getText().toString());
        Log.d("v", "Spool Mass: " + spoolSize);
        return spoolSize;
    }

    public double getPartSize(){
        EditText partSzTxt = (EditText) findViewById(R.id.ptMass);
        double partSize = Double.parseDouble(partSzTxt.getText().toString());
        Log.d("v", "Part Mass: " + partSize);
        return partSize;
    }

    public void verify(View view){
        getPartSize();
        getSpoolPrice();
        getSpoolSize();
    }
}
