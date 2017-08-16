package com.example.android.a3dpc_testapp;

import android.icu.math.BigDecimal;
import android.renderscript.Double2;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.blackcat.currencyedittext.CurrencyEditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText prtSzTxt = (EditText) findViewById(R.id.ptMass);
        EditText splSzTxt = (EditText) findViewById(R.id.spMass);

        TextWatcher massWatcher = new TextWatcher() {
                boolean ignore = false;
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    String userInput = editable.toString();
                    if(userInput.length() == 0){
                        editable.replace(0, editable.length(), "0");
                    }
                    else if (userInput.length() > 1){
                        if(userInput.charAt(0) == '0'){
                            editable.delete(0,1);
                        }
                    }

                }
        };

        prtSzTxt.addTextChangedListener(massWatcher);
        splSzTxt.addTextChangedListener(massWatcher);
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

    public void calculate(){
        double spoolPrice = getSpoolPrice();
        double spoolSize = getSpoolSize();
        double partSize = getPartSize();


    }
}
