package com.petrovdevelopment.dice;

import com.petrovdevelopment.dice.utils.DatabaseHelper;
import com.petrovdevelopment.dice.utils.Preferences;

/**
 * Created by Andrey Petrov on 2014-12-14.
 */
public class MainApplication extends android.app.Application {
    private DatabaseHelper dbHelper;
    private Preferences preferences;

    public MainApplication() {
    }

    public DatabaseHelper getDbHelper() {
        if (dbHelper == null) dbHelper = new DatabaseHelper(this);
        return dbHelper;
    }

    public Preferences getPreferences() {
        if (preferences == null) preferences = new Preferences(this);
        return preferences;
    }
}
