package com.petrovdevelopment.common.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.petrovdevelopment.common.R;
import com.petrovdevelopment.common.adapters.MainMenuAdapter;
import com.petrovdevelopment.common.util.U;

import java.util.List;

/**
 * Created by Andrey Petrov on 2014-10-26.
 */


public abstract class BaseMenuActivity extends Activity {
    ListView mMainMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getActivityLayoutId());
        initMainMenu();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (getMenuLayoutId() != -1 ) {
            getMenuInflater().inflate(getMenuLayoutId(), menu);
            return true;
        } else return false;
    }



    /**
     * Get a list of titles for the buttons in the main menu
     * @return
     */
    public abstract List<String> getMenuOptions();

    private void initMainMenu() {
        mMainMenu = (ListView) findViewById(getMainMenuId());
        List<String> menuOptions = getMenuOptions();
        mMainMenu.setAdapter(getMainMenuAdapter(menuOptions));
        mMainMenu.setOnItemClickListener(getMainMenuOnItemClickListener());
    }

    private ListAdapter getMainMenuAdapter(List<String> menuOptions) {
        return new MainMenuAdapter(menuOptions, this, getMainMenuCellId());
    }

    /**
     * Override this method to handle clicks on the main menu
     * @return
     */
    public AdapterView.OnItemClickListener getMainMenuOnItemClickListener() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                switch (position) {
                    case 0:
                        U.log(this, "first button");
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



    ///RESOURCE METHODSk, override them to get different resources if needed

    /**
     * Implement to get the id of the home activity layout
     * @return
     */
    public int getActivityLayoutId() {
        return R.layout.activity_home;
    };

    /**
     * Implement to get a menu layout id. If you return -1 the menu will not be shown.
     * @return
     */
    public int getMenuLayoutId() {
        return -1;
    };

    /**
     * Implement to get the id of the main menu cell layout.
     * The cell should containt a @+id/text text view in itself, because MainMenuAdapter depends on that
     * @return
     */
    public int getMainMenuCellId() {
        return R.layout.cell_main_menu;
    }

    /**
     * Implement to get the id of the main menu list view
     * @return
     */
    public int getMainMenuId() {
        return R.id.mainMenu;
    }
}

