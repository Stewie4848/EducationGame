package com.example.educationgame;

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

    private static final String DEFAULT_TIME = "60";
    private static final String DEFAULT_DIFFICULTY = "1";
    private static final String DEFAULT_SKIP_AMOUNT = "3";


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

            // Set time setting to numerical only
            EditTextPreference pref = findPreference("time_setting");
            if (pref != null) {
                pref.setOnBindEditTextListener(
                        editText -> editText.setInputType(InputType.TYPE_CLASS_NUMBER)
                );


            }

        }
    }

    public void onDefaultPressed(View view) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        System.out.println(sharedPreferences.getAll());
        sharedPreferences
                .edit()
                .putString("time_setting", DEFAULT_TIME)
                .putString("difficulty_setting", DEFAULT_DIFFICULTY)
                .putString("username", getResources().getString(R.string.default_username))
                .putString("skip_setting", DEFAULT_SKIP_AMOUNT)
                .apply();
        onBackPressed();
    }


    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
        super.onBackPressed();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}