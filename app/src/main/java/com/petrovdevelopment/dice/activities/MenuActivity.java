package com.petrovdevelopment.dice.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.petrovdevelopment.common.activities.BaseMenuActivity;
import com.petrovdevelopment.common.util.U;
import com.petrovdevelopment.dice.R;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Andrey Petrov on 2014-11-09.
 */
public class MenuActivity extends BaseMenuActivity {
    @Override
    public List<String> getMenuOptions() {
        return Arrays.asList(getResources().getStringArray(R.array.main_menu));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    public AdapterView.OnItemClickListener getMainMenuOnItemClickListener() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                switch (position) {
                    case 0:
                        openDiceRoll();
                        break;
                    case 1:
                        openOpenGl();
                        break;
                    case 2:
                        U.log(this, "third button");
                        break;
                    default: // do nothing
                }
            }
        };
    }


    public void initView() {
        U.log(this, "view initialized");
    }


    private void openDiceRoll() {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    private void openOpenGl() {
        Intent intent = new Intent(this, OpenGLES30Activity.class);
        startActivity(intent);
    }


}
