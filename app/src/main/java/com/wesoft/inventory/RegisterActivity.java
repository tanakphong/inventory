package com.wesoft.inventory;

import android.content.ContextWrapper;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.pixplicity.easyprefs.library.Prefs;

public class RegisterActivity extends AppCompatActivity {

    private TextView mTxtAndroidID;
    private TextView mTxtResult;
    private TextView mTxtCodeTemp;
    private TextView mTxtCode;
    private Button mBtnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        new Prefs.Builder()
                .setContext(getApplicationContext())
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();

        //Config Actionbar
        final ActionBar ab = getSupportActionBar();
        //ab.setHomeAsUpIndicator(R.drawable.ic_menu); // set a custom icon for the default home button
        ab.setDisplayShowHomeEnabled(true); // show or hide the default home button
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowCustomEnabled(true); // enable overriding the default toolbar layout
        ab.setDisplayShowTitleEnabled(true); // disable the default title element here (for centered title)

        mTxtResult = (TextView) findViewById(R.id.txtResult);
        mTxtAndroidID = (TextView) findViewById(R.id.txtAndroidID);
        mTxtCodeTemp = (TextView) findViewById(R.id.txtCodeTemp);
        mTxtCode = (TextView) findViewById(R.id.txtCode);
        mBtnRegister = (Button) findViewById(R.id.btnRegister);

        boolean register_status = Prefs.getBoolean("REGISTER_STATUS", false);

        if (register_status) {
            mTxtResult.setText("Registed");
            mTxtCode.setVisibility(View.GONE);
            mBtnRegister.setVisibility(View.GONE);
        } else {
            mTxtResult.setText("Not register");
            mTxtCode.setVisibility(View.VISIBLE);
            mBtnRegister.setVisibility(View.VISIBLE);
        }


        String android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        final String reg = registerLogic(android_id);
//        ConvertBaseNumbers convert = new ConvertBaseNumbers();
//        String dec = convert.hexToDec(android_id);
//        Toast.makeText(this, "Dec:" + reg, Toast.LENGTH_SHORT).show();
        mTxtAndroidID.setText(android_id);
        mTxtCodeTemp.setText(reg);
        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = mTxtCode.getText().toString();
                if (code.equals(reg)) {
                    Prefs.putBoolean("REGISTER_STATUS", true);
                    Toast.makeText(RegisterActivity.this, "Code complete.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegisterActivity.this, "Code invalid.", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private String registerLogic(String val) {

        String str1 = new String(val.substring(0, 4));
        String str2 = new String(val.substring(4, 8));
        String str3 = new String(val.substring(8, 12));
        String str4 = new String(val.substring(12, 16));

        ConvertBaseNumbers convert = new ConvertBaseNumbers();
        String v1 = String.valueOf(Integer.valueOf(convert.hexToDec(str1)) * 4 + 120).substring(0, 3);
        String v2 = String.valueOf(Integer.valueOf(convert.hexToDec(str2)) * 3 + 115).substring(0, 3);
        String v3 = String.valueOf(Integer.valueOf(convert.hexToDec(str3)) * 2 + 110).substring(0, 3);
        String v4 = String.valueOf(Integer.valueOf(convert.hexToDec(str4)) * 1 + 105).substring(0, 3);

        return v4 + v2 + v1 + v3;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
//                NavUtils.navigateUpFromSameTask(this);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
