package com.wesoft.inventory;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.pixplicity.easyprefs.library.Prefs;
import com.wesoft.inventory.model.SettingModel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ImportActivity extends AppCompatActivity {

    private Spinner spinner;
    private EditText mTxtDelimiter;
    private String delimiter = "";
    private Button mBtnImport;
    private Button mBtnClearTransection;
    //    private TestDBAdapter db;
    private ImageButton mBtnProductDelete;
    private ImageButton mBtnCountingDelete;
    private ImageButton mBtnLocationDelete;
    private CheckBox mChkProduct;
    private EditText mTxtProduct;
    private CheckBox mChkCounting;
    private EditText mTxtCounting;
    private CheckBox mChkLocation;
    private EditText mTxtLocation;
    private RadioGroup mRdoSelectPath;
    private RadioButton mRdoDevice;
    private RadioButton mRdoNetwork;
    private EditText mTxtPathFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import);
        bindWidgets();
        setEvents();

        setPathLocation(mRdoSelectPath.getCheckedRadioButtonId());
//        final String location = ((RadioButton) findViewById(checkedId)).getTag().toString();
    }


    private void bindWidgets() {

//        db = new TestDBAdapter(getApplicationContext());

        spinner = (Spinner) findViewById(R.id.delimiter_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.delimiter_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        mTxtDelimiter = (EditText) findViewById(R.id.txtDelimiter);
        mTxtDelimiter.setVisibility(View.INVISIBLE);
        mRdoSelectPath = (RadioGroup) findViewById(R.id.rdoSelectPath);
        mRdoDevice = (RadioButton) findViewById(R.id.rdoDevice);
        mRdoNetwork = (RadioButton) findViewById(R.id.rdoNetwork);
        mTxtPathFile = (EditText) findViewById(R.id.txtPathFile);
        mBtnClearTransection = (Button) findViewById(R.id.btnClearTransection);
        mChkProduct = (CheckBox) findViewById(R.id.chkProduct);
        mTxtProduct = (EditText) findViewById(R.id.txtProduct);
        mBtnProductDelete = (ImageButton) findViewById(R.id.btnProductDelete);
        mChkCounting = (CheckBox) findViewById(R.id.chkCounting);
        mTxtCounting = (EditText) findViewById(R.id.txtCounting);
        mBtnCountingDelete = (ImageButton) findViewById(R.id.btnCountingDelete);
        mChkLocation = (CheckBox) findViewById(R.id.chkLocation);
        mTxtLocation = (EditText) findViewById(R.id.txtLocation);
        mBtnLocationDelete = (ImageButton) findViewById(R.id.btnLocationDelete);

        mBtnImport = (Button) findViewById(R.id.btnImport);

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

        mBtnImport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                db.CreateTable();
                Log.i("dlg", "Create Database: ");

//                ArrayList<LocationModel> lists=db.getTable();
//
//                for (LocationModel list: lists) {
//                    Log.i("dlg", "onClick: "+list.getCode()+","+list.getName());
//                }
            }
        });

        mRdoSelectPath.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
//                final String location = ((RadioButton) findViewById(checkedId)).getTag().toString();
//                Toast.makeText(getBaseContext(), location, Toast.LENGTH_SHORT).show();
                setPathLocation(checkedId);
            }
        });

        mBtnClearTransection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                db.TruncateTable();
            }
        });

        mBtnProductDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String val = mTxtProduct.getText().toString();
//                LocationModel loc = db.getRow(val);
//                if(loc != null) {
//                    Log.i("dlg", "getRow: " + loc.getCode() + "," + loc.getName());
////                Toast.makeText(ImportActivity.this, "mBtnProductDelete", Toast.LENGTH_SHORT).show();
//                }else{
//                    Log.i("dlg", "getRow: not found.");
//                }
            }
        });

        mBtnCountingDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ImportActivity.this, "mBtnCountingDelete", Toast.LENGTH_SHORT).show();
            }
        });

        mBtnLocationDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ImportActivity.this, "mBtnLocationDelete", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setPathLocation(int checkedId) {
        switch (checkedId) {
            case R.id.rdoDevice:
                mTxtPathFile.setText(Prefs.getString(SettingModel.COLUMN_DEVICE, ""));
                break;
            case R.id.rdoNetwork:
                mTxtPathFile.setText(Prefs.getString(SettingModel.COLUMN_NETWORK, ""));
                break;
            default:
                mTxtPathFile.setText("");
        }
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
