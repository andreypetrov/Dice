package com.petrovdevelopment.dice.activities;

import android.app.Activity;
import android.os.Bundle;

import com.petrovdevelopment.dice.MainApplication;
import com.petrovdevelopment.dice.R;
import com.petrovdevelopment.dice.threads.QueryDbTask;

/**
 * Created by Andrey Petrov on 2014-12-14.
 */
public class CollectionsActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collections);
        initViews();
        loadDice();
    }

    private void loadDice() {
        MainApplication app = (MainApplication) getApplication();
        new QueryDbTask(app).execute();

    }

    private void initViews() {
        findViewById(R.id.result);

    }
}
