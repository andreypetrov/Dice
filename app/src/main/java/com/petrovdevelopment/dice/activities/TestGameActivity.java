package com.petrovdevelopment.dice.activities;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.petrovdevelopment.dice.R;
import com.petrovdevelopment.dice.logic.Game;
import com.petrovdevelopment.dice.logic.ShakeDetector;

/**
 * Created by Andrey Petrov on 2014-11-09.
 */
public class TestGameActivity extends Activity implements ShakeDetector.OnShakeListener {
    public static final String GAME = "game";

    private TextView resultView;
    private ShakeDetector shakeDetector;
    private SensorManager sensorManager;

    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restoreOrInitGame(savedInstanceState);
        setContentView(R.layout.activity_menu);
        initViews();
        updateUi();
    }

    private void restoreOrInitGame(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            //game = (Game) savedInstanceState.get(GAME);
        }
        else initGame();

    }

    private void initViews() {
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        shakeDetector = new ShakeDetector(this);
        resultView = (TextView) findViewById(R.id.result);

    }


    private void startListenShake() {
        sensorManager.registerListener(shakeDetector, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void stopListenShake() {
        sensorManager.unregisterListener(shakeDetector);
    }

    private void initGame() {
        game = new Game(null);
    }

    public void onRoll(View v) {
        game.rollDice();
        updateUi();
    }

    private void updateUi() {
        int diceResult = game.getDiceResult();
        resultView.setText(String.valueOf(diceResult));
    }


    @Override
    protected void onResume() {
        super.onResume();
        startListenShake();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopListenShake();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onShake() {
        onRoll(null);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //outState.putParcelable(GAME, game);
    }
}
