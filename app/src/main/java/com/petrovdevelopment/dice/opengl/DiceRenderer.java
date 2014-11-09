package com.petrovdevelopment.dice.opengl;

import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Andrey Petrov on 2014-11-09.
 */
public class DiceRenderer implements GLSurfaceView.Renderer {
    private static final String TAG = "DiceRenderer";
    private int mWidth;
    private int mHeight;

    private Dice mDice;
    private Square mSquare;

    // mMVPMatrix is an abbreviation for "Model View Projection Matrix"
    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];
    private final float[] mRotationMatrix = new float[16];


    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig eglConfig) {
        GLES30.glClearColor(0.0f, 1.0f, 0.0f, 1.0f);
        // enable face culling feature
        //GLES30.glEnable(GL10.GL_CULL_FACE);
        // specify which faces to not draw
        //GLES30.glCullFace(GL10.GL_BACK);

        //Initialize a dice
        mDice = new Dice();
        mSquare = new Square();


    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        // Adjust the viewport based on geometry changes,
        // such as screen rotation
        GLES30.glViewport(0, 0, width, height);

        float ratio = (float) width / height;

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);


    }

    @Override
    public void onDrawFrame(GL10 unused) {

        // Draw background color
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT | GLES30.GL_DEPTH_BUFFER_BIT);

        // Set the camera position (View matrix)
        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        // Calculate the projection and view transformation
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

        // Draw dice
        mDice.draw(mMVPMatrix);
        //mSquare.draw(mMVPMatrix);
    }


    ///
    // Create a shader object, load the shader source, and
    // compile the shader.
    //
    public static int loadShader ( int type, String shaderSrc )
    {
        int shader;
        int[] compiled = new int[1];

        // Create the shader object
        shader = GLES30.glCreateShader ( type );

        if ( shader == 0 )
        {
            return 0;
        }

        // Load the shader source
        GLES30.glShaderSource ( shader, shaderSrc );

        // Compile the shader
        GLES30.glCompileShader ( shader );

        // Check the compile status
        GLES30.glGetShaderiv ( shader, GLES30.GL_COMPILE_STATUS, compiled, 0 );

        if ( compiled[0] == 0 )
        {
            Log.e ( TAG, GLES30.glGetShaderInfoLog ( shader ) );
            GLES30.glDeleteShader ( shader );
            return 0;
        }

        return shader;
    }



    public static void initShaders(int openGLES30program, String vertexShaderCode, String fragmentShaderCode) {
        int vertexShader = loadShader(GLES30.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = loadShader(GLES30.GL_FRAGMENT_SHADER, fragmentShaderCode);
        GLES30.glAttachShader(openGLES30program, vertexShader);   // add the vertex shader to program
        GLES30.glAttachShader(openGLES30program, fragmentShader); // add the fragment shader to program
        GLES30.glLinkProgram(openGLES30program);                  // creates OpenGL ES program executables

    }



    /**
     * Utility method for debugging OpenGL calls. Provide the name of the call
     * just after making it:
     *
     * <pre>
     * mColorHandle = GLES30.glGetUniformLocation(mProgram, "vColor");
     * MyGLRenderer.checkGlError("glGetUniformLocation");</pre>
     *
     * If the operation is not successful, the check throws an error.
     *
     * @param glOperation - Name of the OpenGL call to check.
     */
    public static void checkGlError(String glOperation) {
        int error;
        while ((error = GLES30.glGetError()) != GLES30.GL_NO_ERROR) {
            Log.e(TAG, glOperation + ": glError " + error);
            throw new RuntimeException(glOperation + ": glError " + error);
        }
    }
}
