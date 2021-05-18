package com.example.educationgame;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.EditTextPreference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
            EditTextPreference pref = findPreference("time_setting");
            if (pref != null) {
                pref.setOnBindEditTextListener(
                        editText -> editText.setInputType(InputType.TYPE_CLASS_NUMBER)
                );


            }

        }
    }

    // TODO: 18/05/2021 Make this work somehow
    public void onDefaultPressed(View view) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        System.out.println(sharedPreferences);
//        sharedPreferences.edit().putInt("time_setting", 60).apply();

    }


    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
        super.onBackPressed();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}