package com.example.android.a3dpc_testapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Currency;
import java.util.Locale;

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
    public double getSpoolSize(){
        EditText spoolSzTxt = (EditText) findViewById(R.id.spMass);
        double spoolSize = Double.parseDouble(spoolSzTxt.getText().toString());
        Log.d("v", "Spool Mass: " + spoolSize);
        return spoolSize;
    }


    /**
     * Get the mass of the user's part in grams
     *
     * @return partSize - the mass of the part to calculate the price of
     */
    public double getPartSize(){
        EditText partSzTxt = (EditText) findViewById(R.id.ptMass);
        double partSize = Double.parseDouble(partSzTxt.getText().toString());
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
        // Get the information needed for price calculation and displaying
        TextView priceOutput = (TextView) findViewById(R.id.priceOutputTxt);
        long spoolPrice = getSpoolPrice();
        double spoolSize = getSpoolSize();
        double partSize = getPartSize();
        Locale current = Locale.getDefault();
        Currency curr = Currency.getInstance(current);
        String currSymbol = curr.getSymbol(current);
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
        // This is where values are validated and the calculations are performed
        // TODO Figure out a less verbose way of giving an error toast
        if (spoolPrice == 0){
            errorToast("Please enter a spool price");
        }
        else if(spoolSize == 0){
            errorToast("Please enter a spool size");
        }
        else if(partSize == 0){
            errorToast("Please enter a part size");
        }
        else {
            double centsPerGram = (spoolPrice / spoolSize);
            // While this says cents, it works for any currency where minimal denomination
            // is 1/100th of the main denomination
            double partPriceCents = partSize * centsPerGram;
            double partPriceDollars = partPriceCents / 100.00;
            // This should format the price for the appropriate currency
            String prOut = "Price: " + currSymbol +
                    String.format(current, "%.2f", partPriceDollars);
            priceOutput.setText(prOut);
        }
    }


    /**
     * Makes a toast appear on the bottom of the screen
     *
     * @param message - The message to be shown within the toast
     */
    public void errorToast (String message){
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast errorToast = Toast.makeText(context, message, duration);
        errorToast.show();
    }
}
