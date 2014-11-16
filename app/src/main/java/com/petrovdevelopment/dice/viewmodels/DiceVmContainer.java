package com.petrovdevelopment.dice.viewmodels;

import android.graphics.Canvas;

import com.petrovdevelopment.dice.drawables.Loopable;
import com.petrovdevelopment.dice.models.DiceContainer;
import com.petrovdevelopment.dice.models.Die;
import com.petrovdevelopment.dice.threads.GameSurfaceView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrey Petrov on 2014-11-16.
 * TODO add multiple rolls for dice
 */
public class DiceVmContainer implements  Loopable<Integer>{
    private List<DieVm<Integer>> dieVms;
    private int viewWidth;
    private int viewHeight;
    private int dieWidth;
    private int dieHeight;
    private int diceCount;

    public DiceVmContainer(GameSurfaceView gameSurfaceView, DiceContainer diceContainer) {
        viewWidth = gameSurfaceView.getWidth();
        viewHeight = gameSurfaceView.getHeight();
        diceCount = diceContainer.getDice().size();
        dieWidth = calculateSingleDieWidth();
        dieHeight = calculateSingleDieHeight();

        dieVms = new ArrayList<DieVm<Integer>>();
        for (int i = 0; i< diceContainer.getDice().size(); i++) {
            Die<Integer> die = diceContainer.getDice().get(i);
            DieVm<Integer> dieVm = new DieVm<Integer>(gameSurfaceView, die, dieWidth*i, 0, dieWidth, dieHeight);
            dieVms.add(dieVm);
        }
    }



    /**
     * Die viewWidth is the shorter between viewWidth/diceCount and viewHeight.
     * This assures in the case of one die and a tablet view, the die still is on screen completely
     * @return
     */
    private int calculateSingleDieWidth() {
       return Math.min(viewWidth /diceCount, viewHeight);
       // return 500;
    }

    /**
     * All dice will be square
     * @return
     */
    private int calculateSingleDieHeight() {
        return calculateSingleDieWidth();
    }

    public List<DieVm<Integer>> getDieVms() {
        return dieVms;
    }

    public void setDieVms(List<DieVm<Integer>> dieVms) {
        this.dieVms = dieVms;
    }

    //region Loopable
    @Override
    public void init() {
        for (DieVm<Integer> dieVm : dieVms) {
            dieVm.init();
        }
    }

    @Override
    public void update(Integer frameIndex) {
        for (DieVm<Integer> dieVm : dieVms) {
            dieVm.update(frameIndex);
        }
    }

    public void update(boolean useRealDieIndices) {
        if (useRealDieIndices) {
            for (DieVm<Integer> dieVm : dieVms) {
                dieVm.update(dieVm.getDie().getCurrentSideIndex());
            }
        } else {
            update(-1);
        }
    }

    @Override
    public void render(Canvas canvas) {
        for (DieVm<Integer> dieVm : dieVms) {
            dieVm.render(canvas);
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void finish() {
        for (DieVm<Integer> dieVm : dieVms) {
            dieVm.finish();
        }
    }
    //endregion
}
