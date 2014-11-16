package com.petrovdevelopment.dice.activities;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.TextView;

import com.petrovdevelopment.dice.R;
import com.petrovdevelopment.dice.logic.GameController;
import com.petrovdevelopment.dice.logic.ShakeDetector;
import com.petrovdevelopment.dice.threads.GameSurfaceView;

import java.util.List;

/**
 * Created by Andrey Petrov on 2014-11-09.
 */
public class GameActivity extends Activity implements ShakeDetector.OnShakeListener, GameSurfaceView.GameSurfaceViewCallback {
    public static final String GAME = "game";

    private TextView resultView;
    private ShakeDetector shakeDetector;
    private SensorManager sensorManager;
    private GameSurfaceView gameSurfaceView;


    private GameController gameController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roll);
        initViews();

    }

    private void initViews() {
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        shakeDetector = new ShakeDetector(this);
        resultView = (TextView) findViewById(R.id.result);
        initGameView();
    }

    private void initGameView() {
        gameSurfaceView = (GameSurfaceView)findViewById(R.id.gameSurfaceView);
        gameSurfaceView.setGameSurfaceViewCallback(this);
    }


    private void startListenShake() {
        sensorManager.registerListener(shakeDetector, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void stopListenShake() {
        sensorManager.unregisterListener(shakeDetector);
    }

    public void onRoll(View v) {
        gameController.rollDice();
    }

    private void updateUi() {
        List<Integer> diceResult = gameController.getDiceResult();
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
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
       // outState.putParcelable(GAME, game);
    }


    //region ShakeDetector.OnShakeListener
    @Override
    public void onShake() {
        onRoll(null);
    }
    //endregion

    //region GameSurfaceView.GameSurfaceViewCallback
    @Override
    public void onTouch(float x, float y) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        gameController = GameController.createGame(gameSurfaceView);
        gameController.clear();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }
    //endregion
}
