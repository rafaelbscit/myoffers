package br.com.battista.myoffers.controller;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

import br.com.battista.myoffers.constants.SharedPreferencesKeys;

/**
 * Created by rabsouza on 15/04/16.
 */
public class SharedPreferencesController {

    private final Activity activity;
    private final SharedPreferences.Editor editor;
    private final SharedPreferences sharedPref;

    public SharedPreferencesController(Activity activity) {
        this.activity = activity;

        if (activity == null) {
            throw new IllegalArgumentException("Activyt can not be null!");
        }


        sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();
    }

    public void putBoolean(SharedPreferencesKeys key, boolean value) {
        editor.putBoolean(key.name(), value);
        editor.commit();
    }

    public void putString(SharedPreferencesKeys key, String value) {
        editor.putString(key.name(), value);
        editor.commit();
    }

    public void putStringSet(SharedPreferencesKeys key, Set<String> values) {
        editor.putStringSet(key.name(), values);
        editor.commit();
    }

    public void putInt(SharedPreferencesKeys key, int value) {
        editor.putInt(key.name(), value);
        editor.commit();
    }

    public void putLong(SharedPreferencesKeys key, long value) {
        editor.putLong(key.name(), value);
        editor.commit();
    }

    public void putFloat(SharedPreferencesKeys key, float value) {
        editor.putFloat(key.name(), value);
        editor.commit();
    }

    public void remove(SharedPreferencesKeys key) {
        editor.remove(key.name());
        editor.commit();
    }

    public boolean contains(SharedPreferencesKeys key) {
        return sharedPref.contains(key.name());
    }

    public String getString(SharedPreferencesKeys key, String defValue) {
        return sharedPref.getString(key.name(), defValue);
    }

    public Set<String> getStringSet(SharedPreferencesKeys key, Set<String> defValues) {
        return sharedPref.getStringSet(key.name(), defValues);
    }

    public int getInt(SharedPreferencesKeys key, int defValue) {
        return sharedPref.getInt(key.name(), defValue);
    }

    public long getLong(SharedPreferencesKeys key, long defValue) {
        return sharedPref.getLong(key.name(), defValue);
    }

    public float getFloat(SharedPreferencesKeys key, float defValue) {
        return sharedPref.getFloat(key.name(), defValue);
    }

    public boolean getBoolean(SharedPreferencesKeys key, boolean defValue) {
        return sharedPref.getBoolean(key.name(), defValue);
    }
}
