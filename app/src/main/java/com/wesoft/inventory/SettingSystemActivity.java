package com.wesoft.inventory;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.wesoft.inventory.model.PreCountModel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import pl.tajchert.nammu.Nammu;
import pl.tajchert.nammu.PermissionCallback;

public class SettingSystemActivity extends AppCompatActivity {

    private Button mButton;
    private Spinner spinner;
    private String delimiter = "";
    private EditText mTxtDelimiter;
    private Button mSQLite;

    // Check Runtime Permission -- BEGIN
    public void checkRuntimPermission() {
        Nammu.init(this);
        // Check Runtime Permission
        Nammu.askForPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE, new PermissionCallback() {
            @Override
            public void permissionGranted() {
//                Toast.makeText(SettingActivity.this, "Manifest.permission.WRITE_EXTERNAL_STORAGE - Granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void permissionRefused() {
                finish();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Nammu.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    // Check Runtime Persion -- END

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_system);

        checkRuntimPermission();
        bindWidgets();
        setEvents();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Uri selectedImageUri = data.getData();
            //String s = getRealPathFromURI(selectedImageUri);
            if (requestCode == 1) {
                Log.i("dlg", "getPath() : " + selectedImageUri.getPath());
                Log.i("dlg", "path 1 : " + readTextFilePath(selectedImageUri.getPath(), 1));
            } else if (requestCode == 2) {
                Log.i("dlg", "path 2 : " + selectedImageUri);
            }


        } else if (resultCode == Activity.RESULT_CANCELED) {
            Log.i("dlg", "onActivityResult: Cancle");
        }
    }

    private void bindWidgets() {
        spinner = (Spinner) findViewById(R.id.planets_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.delimiter_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        mTxtDelimiter = (EditText) findViewById(R.id.txtDelimiter);
        mTxtDelimiter.setVisibility(View.INVISIBLE);

        mButton = (Button) findViewById(R.id.button);

        mSQLite = (Button) findViewById(R.id.sqlite);
    }

    private void setEvents() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                boolean bo = false;
                switch (parent.getItemAtPosition(position).toString()) {
                    case "Comma":
                        delimiter = ",";
                        break;
                    case "Tab":
                        delimiter = "\t";
                        break;
                    case "SemiColon":
                        delimiter = ";";
                        break;
                    case "Space":
                        delimiter = " ";
                        break;
                    case "Pipe":
                        delimiter = "\\|";
                        break;
                    case "Manual":
                        bo = true;
                        break;
                    default:
                        delimiter = "";
                }
                if (bo) {
                    mTxtDelimiter.setVisibility(View.VISIBLE);
                } else {
                    mTxtDelimiter.setVisibility(View.INVISIBLE);
                }

                Toast.makeText(getApplicationContext(), parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mTxtDelimiter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
//                Toast.makeText(SettingActivity.this, "s:" + s, Toast.LENGTH_SHORT).show();
                delimiter = getDelimiter(s.toString());
            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String sp = spinner.getSelectedItem().toString();
                Toast.makeText(getApplicationContext(), delimiter, Toast.LENGTH_SHORT).show();

                boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
                if (isKitKat) {
                    Intent intent = new Intent();
                    intent.setType("text/plain");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent, 1);
                } else {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("*/*");
                    startActivityForResult(intent, 2);
                }
            }
        });

        mSQLite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AssetBundle.copyAssetFile("wicra.sqlite",false,getApplicationContext());

                //Test DB
                PreCountDBAdapter db=new PreCountDBAdapter(getApplicationContext());
                //dbAdapter.insert(new UserBean(_username,_password,0));
                PreCountModel resultBn = db.query("8851959132012");

                if(resultBn!=null){
                    Toast.makeText(getApplicationContext(), resultBn.getExpDate(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public String readTextFilePath(String path, int loop) {
        try {

            File myFile = new File(path);
            FileInputStream fIn = new FileInputStream(myFile);
            BufferedReader bufferedreader = new BufferedReader(
                    new InputStreamReader(fIn));

            String line;
            StringBuilder stringBuilder = new StringBuilder();

//            delimiter=getDelimiter(spinner.getSelectedItem().toString());


            while ((line = bufferedreader.readLine()) != null) {
//                for (int i = 0; i < loop; i++) {
//                    StringTokenizer st = new StringTokenizer(line);
//                    while (st.hasMoreTokens('')) {
//                        System.out.println(st.nextToken());
//                        Log.i("dlg", "st: ");
//                    }
                line = line.replaceAll("\n", "");
                String[] arr = line.split(delimiter);
                boolean bo = true;
                for (String val : arr) {
//                    Log.i("dlg", "val : "+val+',');
                    if (!bo) {
                        stringBuilder.append(',');
                    } else {
                        bo = false;
                    }
                    stringBuilder.append(val.trim());
                }
//                    stringBuilder.append(line);
                stringBuilder.append('\n');
//                }
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            return null;
        }
    }

    private String getDelimiter(String value) {
        String ret = "";
        switch (value.toUpperCase()) {
            case "COMMA":
                ret = ",";
                break;
            case "TAB":
                ret = "\t";
                break;
            case "SEMICOLON":
                ret = ";";
                break;
            case "SPACE":
                ret = " ";
                break;
            case "PIPE":
                ret = "\\|";
                break;
            default:
                ret = value;
        }
        return ret;
    }
}
