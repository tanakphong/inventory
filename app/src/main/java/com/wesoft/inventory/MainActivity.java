package com.wesoft.inventory;

import android.content.ContextWrapper;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;
import android.widget.Toast;

import com.pixplicity.easyprefs.library.Prefs;

public class MainActivity extends AppCompatActivity {

    public static int[] osNameList = {
            R.string.register,
            R.string.setting,
            R.string.imports,
            R.string.export,
            R.string.auto,
            R.string.manual,
            R.string.verify,
            R.string.backup,
    };
    public static int[] osImages = {
            R.drawable.ic_register,
            R.drawable.ic_setting,
            R.drawable.ic_import,
            R.drawable.ic_export,
            R.drawable.ic_auto,
            R.drawable.ic_manual,
            R.drawable.ic_verify,
            R.drawable.ic_backup,};
    private GridView gridview;

    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Prefs.Builder()
                .setContext(getApplicationContext())
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();
        Prefs.putBoolean("REGISTER_STATUS", false);

        // Always cast your custom Toolbar here, and set it as the ActionBar.
//        Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(tb);

        // Get the ActionBar here to configure the way it behaves.
        final ActionBar ab = getSupportActionBar();
        //ab.setHomeAsUpIndicator(R.drawable.ic_menu); // set a custom icon for the default home button
        ab.setDisplayShowHomeEnabled(true); // show or hide the default home button
        ab.setDisplayHomeAsUpEnabled(false);
        ab.setDisplayShowCustomEnabled(true); // enable overriding the default toolbar layout
        ab.setDisplayShowTitleEnabled(true); // disable the default title element here (for centered title)


        gridview = (GridView) findViewById(R.id.customgrid);
//        gridview.setBackgroundColor(Color.WHITE);
//        gridview.setVerticalSpacing(1);
//        gridview.setHorizontalSpacing(1);
        gridview.setAdapter(new CustomAdapter(this, osNameList, osImages));

        String android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);

        Toast.makeText(this, android_id, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}
