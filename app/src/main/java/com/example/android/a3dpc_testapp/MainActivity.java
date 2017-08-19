package com.example.android.a3dpc_testapp;

import android.content.Context;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.blackcat.currencyedittext.CurrencyEditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Grab the Edit Texts I need to validate
        EditText prtSzTxt = (EditText) findViewById(R.id.ptMass);
        EditText splSzTxt = (EditText) findViewById(R.id.spMass);

        // This text watcher is for the part and spool mass fields. It allows them to start with a
        // default value of Zero which is overwritten by user, but if user tries to clear the
        // edit text, the value is restored to a zero in order to prevent bad value entry.
        TextWatcher massWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                // This is the method which changes the text for the user
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
        // Adding the actual text watchers.
        prtSzTxt.addTextChangedListener(massWatcher);
        splSzTxt.addTextChangedListener(massWatcher);
    }

    /**
     * Grab the price of the spool as entered by the user. Returns a long
     * in order to represent currency in its lowest denomination. This works for US, UK, and
     * a few others. Will need to tweak this in the future for other currencies.
     *
     * @return price - A long containing the price of the spool.
     */
    public long getSpoolPrice() {
        CurrencyEditText spoolPr = (CurrencyEditText) findViewById(R.id.currencyEditText);
        long price = spoolPr.getRawValue();
        Log.d("v", "Price: C"+ price);
        return price;
    }

    /**
     * Get the mass of the spool in grams
     *
     * @return spoolSize - the mass of the spool
     */
    public int getSpoolSize(){
        EditText spoolSzTxt = (EditText) findViewById(R.id.spMass);
        int spoolSize = Integer.parseInt(spoolSzTxt.getText().toString());
        Log.d("v", "Spool Mass: " + spoolSize);
        return spoolSize;
    }

    /**
     * Get the mass of the user's part in grams
     *
     * @return partSize - the mass of the part to calculate the price of
     */
    public int getPartSize(){
        EditText partSzTxt = (EditText) findViewById(R.id.ptMass);
        int partSize = Integer.parseInt(partSzTxt.getText().toString());
        Log.d("v", "Part Mass: " + partSize);
        return partSize;
    }


    /**
     * Calculate the price of the print and display it. This is where the bulk of the work is done
     * for this part of the application.
     *
     * @param view Required so that this method can be called with onClick
     */
    public void calculate(View view){
        double spoolPrice = getSpoolPrice();
        double spoolSize = getSpoolSize();
        double partSize = getPartSize();
        // These two lines hide the keyboard if it is open when the calculate button is pressed.
        // Apparently getWindowToken may throw a Null Pointer Exception, so wrapped in try catch
        // just in case. Should not occur as there are views to have focus, but being safe here.
        try {
            Button calcButton = (Button) findViewById(R.id.calcButton);
            InputMethodManager inputManager = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(calcButton.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
        catch (NullPointerException npe){
            Log.d("e", "Null pointer exception caused by hiding the keyboard");
        }



    }
}
