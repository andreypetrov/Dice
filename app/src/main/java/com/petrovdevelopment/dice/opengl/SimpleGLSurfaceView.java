package com.petrovdevelopment.dice.opengl;

import android.content.Context;
import android.opengl.GLSurfaceView;

/**
 * Created by Andrey Petrov on 2014-11-09.
 */
public class SimpleGLSurfaceView extends GLSurfaceView {
    public SimpleGLSurfaceView(Context context) {
        super(context);

        setEGLContextClientVersion(3);
        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(new DiceRenderer());
        // Render the view only when there is a change in the drawing data
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }



}
