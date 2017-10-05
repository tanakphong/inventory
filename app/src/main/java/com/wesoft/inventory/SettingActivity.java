package com.wesoft.inventory;

import android.Manifest;
import android.content.ContextWrapper;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.pixplicity.easyprefs.library.Prefs;
import com.wesoft.inventory.model.SettingModel;

import pl.tajchert.nammu.Nammu;
import pl.tajchert.nammu.PermissionCallback;

public class SettingActivity extends AppCompatActivity {

    private EditText mTxtUser;
    private RadioButton mRdoLanguateThai;
    private RadioButton mRdoLanguateEnglish;
    private EditText mTxtDevice;
    private EditText mTxtNetwork;
    private EditText mTxtDatabase;
    private CheckBox mChkFound;
    private EditText mTxtFound;
    private ImageButton mBtnSelectFound;
    private CheckBox mChkNotFound;
    private EditText mTxtNotFound;
    private ImageButton mBtnSelectNotFound;
    private Button mBtnSave;
    private RadioGroup mRdoLanguateGroup;
    private String language;
    private AwesomeValidation awesomeValidation;

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
        setContentView(R.layout.activity_setting);

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

        //Validate
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.txtUser, "\\d+", R.string.validate_text);
        awesomeValidation.addValidation(this, R.id.txtDevice, "[a-zA-Z0-9/\\\\]+", R.string.validate_text);
        awesomeValidation.addValidation(this, R.id.txtNetwork, "[a-zA-Z0-9./\\\\]+", R.string.validate_text);
        awesomeValidation.addValidation(this, R.id.txtDatabase, "[a-zA-Z0-9/\\\\]+", R.string.validate_text);
//        awesomeValidation.addValidation(this, R.id.txtFound, "[a-zA-Z0-9.]+", R.string.validate_text);
//        awesomeValidation.addValidation(this, R.id.txtNotFound, "[a-zA-Z0-9.]+", R.string.validate_text);

        checkRuntimPermission();
        bindWidgets();
        setEvents();
        foundStatus(false);
        notFoundStatus(false);

        mTxtUser.setText(Prefs.getString(SettingModel.COLUMN_USER, ""));

        if (Prefs.getString(SettingModel.COLUMN_LANGUAGE, "Th").toLowerCase().equals("th")) {
            mRdoLanguateThai.setChecked(true);
        } else {
            mRdoLanguateEnglish.setChecked(true);
        }
        mTxtDevice.setText(Prefs.getString(SettingModel.COLUMN_DEVICE, ""));
        mTxtNetwork.setText(Prefs.getString(SettingModel.COLUMN_NETWORK, ""));
        mTxtDatabase.setText(Prefs.getString(SettingModel.COLUMN_DATABASE, ""));
        mChkFound.setChecked(Prefs.getBoolean(SettingModel.COLUMN_BFOUND, false));
        mTxtFound.setText(Prefs.getString(SettingModel.COLUMN_FOUND, ""));
        mChkNotFound.setChecked(Prefs.getBoolean(SettingModel.COLUMN_BNOTFOUND, false));
        mTxtNotFound.setText(Prefs.getString(SettingModel.COLUMN_NOTFOUND, ""));


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        //super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == Activity.RESULT_OK) {
//            Uri selectedImageUri = data.getData();
//            //String s = getRealPathFromURI(selectedImageUri);
//            if (requestCode == 1) {
//                Log.i("dlg", "getPath() : " + selectedImageUri.getPath());
//                Log.i("dlg", "path 1 : " + readTextFilePath(selectedImageUri.getPath(), 1));
//            } else if (requestCode == 2) {
//                Log.i("dlg", "path 2 : " + selectedImageUri);
//            }
//
//
//        } else if (resultCode == Activity.RESULT_CANCELED) {
//            Log.i("dlg", "onActivityResult: Cancle");
//        }
    }

    private void bindWidgets() {
        mTxtUser = (EditText) findViewById(R.id.txtUser);
        mRdoLanguateGroup = (RadioGroup) findViewById(R.id.rdoLanguateGroup);
        mRdoLanguateThai = (RadioButton) findViewById(R.id.rdoLanguateThai);
        mRdoLanguateEnglish = (RadioButton) findViewById(R.id.rdoLanguateEnglish);
        mTxtDevice = (EditText) findViewById(R.id.txtDevice);
        mTxtNetwork = (EditText) findViewById(R.id.txtNetwork);
        mTxtDatabase = (EditText) findViewById(R.id.txtDatabase);
        mChkFound = (CheckBox) findViewById(R.id.chkFound);
        mTxtFound = (EditText) findViewById(R.id.txtFound);
        mBtnSelectFound = (ImageButton) findViewById(R.id.btnSelectFound);
        mChkNotFound = (CheckBox) findViewById(R.id.chkNotFound);
        mTxtNotFound = (EditText) findViewById(R.id.txtNotFound);
        mBtnSelectNotFound = (ImageButton) findViewById(R.id.btnSelectNotFound);
        mBtnSave = (Button) findViewById(R.id.btnSave);
    }

    private void foundStatus(boolean b) {
        mTxtFound.setEnabled(b);
        mBtnSelectFound.setEnabled(b);
        if (b) {
            mTxtFound.setText(Prefs.getString(SettingModel.COLUMN_FOUND, ""));
        } else {
            mTxtFound.setText("");
            mTxtFound.setError(null);
        }
    }

    private void notFoundStatus(boolean b) {
        mTxtNotFound.setEnabled(b);
        mBtnSelectNotFound.setEnabled(b);
        if (b) {
            mTxtNotFound.setText(Prefs.getString(SettingModel.COLUMN_FOUND, ""));
        } else {
            mTxtNotFound.setText("");
            mTxtFound.setError(null);
        }
    }

    private void setEvents() {
        final String value =
                ((RadioButton) findViewById(mRdoLanguateGroup.getCheckedRadioButtonId()))
                        .getText().toString();

        mRdoLanguateGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
//                final String language = ((RadioButton)findViewById(checkedId)).getText().toString();
//                Toast.makeText(getBaseContext(), language, Toast.LENGTH_SHORT).show();
                switch (checkedId) {
                    case R.id.rdoLanguateThai:
                        language = "Th";
                        break;
                    case R.id.rdoLanguateEnglish:
                        language = "En";
                        break;

                }
                Toast.makeText(SettingActivity.this, "Language : " + language, Toast.LENGTH_SHORT).show();
            }
        });

        mChkFound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                foundStatus(isChecked);
            }
        });

        mChkNotFound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                notFoundStatus(isChecked);
            }
        });

        mBtnSelectFound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SettingActivity.this, "Found", Toast.LENGTH_SHORT).show();
            }
        });

        mBtnSelectNotFound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SettingActivity.this, "Not Found", Toast.LENGTH_SHORT).show();
            }
        });

        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer mediaPlayer = MediaPlayer.create(getBaseContext(), R.raw.button);
                mediaPlayer.start();
                if (awesomeValidation.validate()) {
                    if (mChkFound.isChecked()) {
                        if (mTxtFound.getText().toString().equals("")) {
                            mTxtFound.setError(getString(R.string.validate_text));
                            return;
                        } else {
                            mTxtFound.setError(null);
                        }
                    }
                    if (mChkNotFound.isChecked()) {
                        if (mTxtNotFound.getText().toString().equals("")) {
                            mTxtNotFound.setError(getString(R.string.validate_text));
                            return;
                        } else {
                            mTxtNotFound.setError(null);
                        }
                    }
                    Prefs.putString(SettingModel.COLUMN_USER, mTxtUser.getText().toString());
                    Prefs.putString(SettingModel.COLUMN_LANGUAGE, language);
                    Prefs.putString(SettingModel.COLUMN_DEVICE, mTxtDevice.getText().toString());
                    Prefs.putString(SettingModel.COLUMN_NETWORK, mTxtNetwork.getText().toString());
                    Prefs.putString(SettingModel.COLUMN_DATABASE, mTxtDatabase.getText().toString());
                    Prefs.putBoolean(SettingModel.COLUMN_BFOUND, mChkFound.isChecked());
                    Prefs.putString(SettingModel.COLUMN_FOUND, mTxtFound.getText().toString());
                    Prefs.putBoolean(SettingModel.COLUMN_BNOTFOUND, mChkNotFound.isChecked());
                    Prefs.putString(SettingModel.COLUMN_NOTFOUND, mTxtNotFound.getText().toString());
                    Toast.makeText(SettingActivity.this, "Save Setting", Toast.LENGTH_SHORT).show();
                }
            }
        });


//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                boolean bo = false;
//                switch (parent.getItemAtPosition(position).toString()) {
//                    case "Comma":
//                        delimiter = ",";
//                        break;
//                    case "Tab":
//                        delimiter = "\t";
//                        break;
//                    case "SemiColon":
//                        delimiter = ";";
//                        break;
//                    case "Space":
//                        delimiter = " ";
//                        break;
//                    case "Pipe":
//                        delimiter = "\\|";
//                        break;
//                    case "Manual":
//                        bo = true;
//                        break;
//                    default:
//                        delimiter = "";
//                }
//                if (bo) {
//                    mTxtDelimiter.setVisibility(View.VISIBLE);
//                } else {
//                    mTxtDelimiter.setVisibility(View.INVISIBLE);
//                }
//
//                Toast.makeText(SettingActivity.this, parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//        mTxtDelimiter.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
////                Toast.makeText(SettingActivity.this, "s:" + s, Toast.LENGTH_SHORT).show();
//                delimiter = getDelimiter(s.toString());
//            }
//        });
//
//        mButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                String sp = spinner.getSelectedItem().toString();
//                Toast.makeText(SettingActivity.this, delimiter, Toast.LENGTH_SHORT).show();
//
//                boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
//                if (isKitKat) {
//                    Intent intent = new Intent();
//                    intent.setType("text/plain");
//                    intent.setAction(Intent.ACTION_GET_CONTENT);
//                    startActivityForResult(intent, 1);
//                } else {
//                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                    intent.setType("*/*");
//                    startActivityForResult(intent, 2);
//                }
//            }
//        });
//
//        mSQLite.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AssetBundle.copyAssetFile("wicra.sqlite",false,getApplicationContext());
//
//                //Test DB
//                PreCountDBAdapter db=new PreCountDBAdapter(getApplicationContext());
//                //dbAdapter.insert(new UserBean(_username,_password,0));
//                PreCountModel resultBn = db.query("8851959132012");
//
//                if(resultBn!=null){
//                    Toast.makeText(SettingActivity.this, resultBn.getExpDate(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        Switch switch1= (Switch)menu.findItem(R.id.action_switch).getActionView().findViewById(R.id.switchForActionBar);
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(SettingActivity.this, "isChecked:" + isChecked, Toast.LENGTH_SHORT).show();
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toast.makeText(this, "item.getItemId():" + item.getItemId(), Toast.LENGTH_SHORT).show();
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
//                NavUtils.navigateUpFromSameTask(this);
                finish();
                return true;
            case R.id.action_switch:
//                Switch mySwitch = (Switch) findViewById(R.id.myswitch);
                Switch sw = (Switch) item.getActionView();
                Toast.makeText(this, "mySwitch.isChecked():" + sw.isChecked(), Toast.LENGTH_SHORT).show();
            case R.id.action_cancel:
                Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show();
            default:
                Toast.makeText(this, "No Action", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
