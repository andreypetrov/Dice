package com.petrovdevelopment.dice.activities;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;


import com.petrovdevelopment.dice.opengl.SimpleGLSurfaceView;

/**
 *
 * Activity to test the open gl es 3.0 framework of drawing
 * Created by Andrey Petrov on 2014-11-09.
 */
public class OpenGLES30Activity extends Activity {

    private GLSurfaceView mGLView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create a GLSurfaceView instance and set it
        // as the ContentView for this Activity.
        mGLView = new SimpleGLSurfaceView(this);
        setContentView(mGLView);
    }
}
