/*===============================================================================
Copyright (c) 2016 PTC Inc. All Rights Reserved.

Copyright (c) 2012-2014 Qualcomm Connected Experiences, Inc. All Rights Reserved.

Vuforia is a trademark of PTC Inc., registered in the United States and other 
countries.
===============================================================================*/

package com.zelering.AdReader.lib.app.CloudRecognition;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.zelering.AdReader.SampleApplication.SampleApplicationSession;
import com.zelering.AdReader.SampleApplication.utils.SampleUtils;
import com.zelering.AdReader.SampleApplication.utils.Texture;
import com.vuforia.Renderer;
import com.vuforia.State;
import com.vuforia.TrackableResult;
import com.vuforia.VIDEO_BACKGROUND_REFLECTION;
import com.vuforia.Vuforia;

import java.util.Vector;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/***
 * // The renderer class for the CloudReco sample.
 * @author Raj Kapoor
 * @since 12/05/2016
 */
public class CloudRecoRenderer implements GLSurfaceView.Renderer {
    SampleApplicationSession vuforiaAppSession;

    private Vector<Texture> mTextures;

    public CloudReco mActivity;

    public CloudRecoRenderer(SampleApplicationSession session, CloudReco activity) {
        vuforiaAppSession = session;
        mActivity = activity;
    }

    // Called when the surface is created or recreated.
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // Call function to initialize rendering:
        initRendering();
        // Call Vuforia function to (re)initialize rendering after first use
        // or after OpenGL ES context was lost (e.g. after onPause/onResume):
        vuforiaAppSession.onSurfaceCreated();
    }


    // Called when the surface changed size.
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // Call Vuforia function to handle render surface size changes:
        vuforiaAppSession.onSurfaceChanged(width, height);
    }


    // Called to draw the current frame.
    @Override
    public void onDrawFrame(GL10 gl) {
        // Call our function to render content
        renderFrame();
    }


    // Function for initializing the renderer.
    private void initRendering() {
        // Define clear color
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, Vuforia.requiresAlpha() ? 0.0f
                : 1.0f);
        for (Texture t : mTextures) {
            GLES20.glGenTextures(1, t.mTextureID, 0);
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, t.mTextureID[0]);
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
                    GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
                    GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
            GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA,
                    t.mWidth, t.mHeight, 0, GLES20.GL_RGBA,
                    GLES20.GL_UNSIGNED_BYTE, t.mData);
        }
    }


    // The render function.
    private void renderFrame() {
        // Clear color and depth buffer
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        // Get the state from Vuforia and mark the beginning of a rendering
        // section
        State state = Renderer.getInstance().begin();

        // Explicitly render the Video Background
        Renderer.getInstance().drawVideoBackground();

        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        GLES20.glEnable(GLES20.GL_CULL_FACE);
        if (Renderer.getInstance().getVideoBackgroundConfig().getReflection() == VIDEO_BACKGROUND_REFLECTION.VIDEO_BACKGROUND_REFLECTION_ON)
            GLES20.glFrontFace(GLES20.GL_CW);  // Front camera
        else
            GLES20.glFrontFace(GLES20.GL_CCW);   // Back camera

        // Set the viewport
        int[] viewport = vuforiaAppSession.getViewport();
        GLES20.glViewport(viewport[0], viewport[1], viewport[2], viewport[3]);

        // Did we find any trackables this frame?
        if (state.getNumTrackableResults() > 0) {
            // Gets current trackable result
            TrackableResult trackableResult = state.getTrackableResult(0);

            if (trackableResult == null) {
                return;
            }

            if (mActivity != null) {
                mActivity.stopFinderIfStarted();
            }

            // Renders the Augmentation View with the 3D Book data Panel
            renderAugmentation(trackableResult);

        } else {
            if (mActivity != null) {
                mActivity.startFinderIfStopped();
            }
        }

        GLES20.glDisable(GLES20.GL_DEPTH_TEST);

        Renderer.getInstance().end();
    }

    private static final String TAG = "CloudRecoRenderer";

    private void renderAugmentation(TrackableResult trackableResult) {
        SampleUtils.checkGLError("CloudReco renderFrame");
        mActivity.updateToActivity(trackableResult);
    }

    boolean toastOnce = false;
    long time = 0, currentTime = 0;




    public void setTextures(Vector<Texture> textures) {
        mTextures = textures;
    }

}
