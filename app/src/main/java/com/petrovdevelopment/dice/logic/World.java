package com.petrovdevelopment.dice.logic;

import android.graphics.Canvas;

import com.petrovdevelopment.dice.R;
import com.petrovdevelopment.dice.drawables.Background;
import com.petrovdevelopment.dice.drawables.Loopable;
import com.petrovdevelopment.dice.models.DiceContainer;
import com.petrovdevelopment.dice.threads.GameSurfaceView;
import com.petrovdevelopment.dice.viewmodels.DiceVmContainer;
import com.petrovdevelopment.dice.viewmodels.DieVm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andrey on 2014-10-18.
 */
public class World implements Loopable<Integer> {
    private DiceVmContainer diceVmContainer;
    private Background background;
    private GameSurfaceView gameSurfaceView;


    private World(GameSurfaceView gameSurfaceView, DiceContainer diceContainer) {

        this.gameSurfaceView = gameSurfaceView;
        initBackground();
        this.diceVmContainer = new DiceVmContainer(gameSurfaceView, diceContainer);
//        int[] testDiceRolls = diceContainer.testRandomness();
//        U.log(this, "dice rolls: ");
//        U.log(this, testDiceRolls);
    }

    public static World createWorld(GameSurfaceView gameSurfaceView, DiceContainer diceContainer) {
        return new World(gameSurfaceView, diceContainer);
    }

    public void initBackground() {
        background = new Background(gameSurfaceView.getResources(), R.drawable.wall_bg, gameSurfaceView.getWidth(), gameSurfaceView.getHeight());
    }


    public List<Integer> getDiceResults() {
        List<Integer> results = new ArrayList<Integer>();
        for (DieVm<Integer> dieVm : diceVmContainer.getDieVms()) {
            results.add(dieVm.getDie().getCurrentSideIndex());
        }
        return results;
    }

    public void drawBackground(Canvas canvas) {
        background.render(canvas);
    }


    //region Loopable
    @Override
    public void init() {
        diceVmContainer.init();
    }

    public void update(boolean useRealIndices) {
        diceVmContainer.update(useRealIndices);
    }

    @Override
    public void update(Integer frameIndex) {
        diceVmContainer.update(frameIndex);
    }

    @Override
    public void render(Canvas canvas) {
       background.render(canvas);
       diceVmContainer.render(canvas);
    }

    @Override
    public boolean isFinished() {
       return diceVmContainer.isFinished();
    }

    @Override
    public void finish() {
        diceVmContainer.finish();
    }
    //endregion
}
