package com.petrovdevelopment.dice.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;

import com.petrovdevelopment.common.activities.BaseMenuActivity;
import com.petrovdevelopment.common.util.U;
import com.petrovdevelopment.dice.R;

import java.util.Arrays;
import java.util.List;


public class MenuActivity extends BaseMenuActivity {
    private boolean isActionBarDark = false;
    private View backgroundDarkOverlay;

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
                        U.log(this, "second button");
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
        backgroundDarkOverlay = new View(this.getBaseContext());
        backgroundDarkOverlay.setMinimumWidth(500);
        backgroundDarkOverlay.setMinimumHeight(500);
        backgroundDarkOverlay.setBackgroundColor(getResources().getColor(R.color.transparent_black));
    }


    private void darkenActionBar() {
       // getActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.transparent_black)));
        // getActionBar().hide();

//        ViewGroup contentView = (ViewGroup) findViewById(R.id.content);
//        contentView.addView(backgroundDarkOverlay, 0);

        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.width = 2500;
        params.height = 2500;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        params.type = WindowManager.LayoutParams.TYPE_APPLICATION_PANEL;
        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        windowManager.addView(backgroundDarkOverlay, params);
    }

    private void brightenActionBar() {
        ViewGroup contentView = (ViewGroup) findViewById(R.id.content);
        contentView.removeView(backgroundDarkOverlay);
    }

    private void openDiceRoll() {
        isActionBarDark = !isActionBarDark;
        if (isActionBarDark) darkenActionBar();
        else brightenActionBar();


//        Intent intent = new Intent(this, GameActivity.class);
//        startActivity(intent);
    }

}
