package com.petrovdevelopment.dice.viewmodels;

import com.petrovdevelopment.dice.logic.Die;

/**
 * A view model that holds the model and also some data specific to the way this model will be printed on the screen
 * Created by Andrey Petrov on 2014-11-09.
 */
public class DieVm<E>  implements SpriteVm{

    private Die<E> die;
    private int spriteResourceId;

    public DieVm(Die<E> die, int spriteResourceId) {
        this.die = die;
        this.spriteResourceId = spriteResourceId;
    }

    public Die<E> getDie() {
        return die;
    }

    @Override
    public int getSpriteResourceId() {
        return spriteResourceId;
    }

    @Override
    public int getFrameCount() {
        return die.getSideCount();
    }


}
