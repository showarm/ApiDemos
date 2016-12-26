package com.example.android.opengl;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;

public class MainActivity extends AppCompatActivity {

    MyGLView mGLView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGLView = new MyGLView(this);
        setContentView(mGLView);
    }


    @Override
    protected void onPause() {
        super.onPause();
        // The following call pauses the rendering thread.
        // If your OpenGL application is memory intensive,
        // you should consider de-allocating objects that
        // consume significant memory here.
        mGLView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // The following call resumes a paused rendering thread.
        // If you de-allocated graphic objects for onPause()
        // this is a good place to re-allocate them.
        mGLView.onResume();
    }

    public static class MyGLView extends GLSurfaceView{

        public MyGLView(Context context) {
            super(context);

            // Create an OpenGL ES 2.0 context.
            setEGLContextClientVersion(2);

            // Set the Renderer for drawing on the GLSurfaceView
            ColorfulRender mRenderer = new ColorfulRender();
            setRenderer(mRenderer);

            // Render the view only when there is a change in the drawing data
            setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
        }

        public MyGLView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }
    }

}
