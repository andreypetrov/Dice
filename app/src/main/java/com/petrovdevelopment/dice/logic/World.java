package com.petrovdevelopment.dice.logic;

import android.graphics.Canvas;

import com.petrovdevelopment.dice.R;
import com.petrovdevelopment.dice.drawables.Background;
import com.petrovdevelopment.dice.drawables.Loopable;
import com.petrovdevelopment.dice.threads.GameSurfaceView;
import com.petrovdevelopment.dice.viewmodels.DieVm;

import java.util.Arrays;
import java.util.List;

/**
 * Created by andrey on 2014-10-18.
 */
public class World implements Loopable<Integer> {
    private DieVm<Integer> dieVm;
    private Background background;
    private GameSurfaceView gameSurfaceView;
    private int rowDurationInMilliseconds;

    public World(GameSurfaceView gameSurfaceView, int rowDurationInMilliseconds) {
        this.gameSurfaceView = gameSurfaceView;
        this.rowDurationInMilliseconds = rowDurationInMilliseconds;
        initBackground();
        initDieVm();

    }

    public static World createWorld(GameSurfaceView gameSurfaceView, int rowDurationInMilliseconds) {
        return new World(gameSurfaceView, rowDurationInMilliseconds);
    }

//    public void initialize(GameSurfaceView gameSurfaceView) {
//        this.gameSurfaceView = gameSurfaceView;
//        initialize();
//    }


    public void initBackground() {
        background = new Background(gameSurfaceView.getResources(), R.drawable.wall_bg, gameSurfaceView.getWidth(), gameSurfaceView.getHeight());
    }

    private void initDieVm() {
        dieVm = new DieVm<Integer>(gameSurfaceView, createDie(), R.drawable.die, 0, 0, 0);
    }

    private Die createDie() {
        Integer[] array = new Integer[]{1, 2, 3, 4, 5, 6};
        List<Integer> sides = Arrays.asList(array);
        Die die = new Die<Integer>(0, sides, Integer.class);
        return die;
    }


    public int getDiceResult() {
        return dieVm.getDie().getCurrentSideIndex();
    }

    public void drawBackground(Canvas canvas) {
        background.render(canvas);
    }


    //region Loopable
    @Override
    public void init() {
    }

    @Override
    public void update(Integer externalState) {
        dieVm.update(externalState);
    }

    @Override
    public void render(Canvas canvas) {
       background.render(canvas);
       dieVm.render(canvas);
    }

    @Override
    public long getDuration() {
        return rowDurationInMilliseconds;
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void finish() {
    }
    //endregion
}
