package com.kedo.app.camera;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.SurfaceTexture;
import android.opengl.GLES20;
import android.util.AttributeSet;
import android.util.Log;


import com.kedo.commonlibrary.ext.ScreenExtKt;
import com.kedo.commonlibrary.util.BitmapUtils;

import org.cocos2dx.lib.Cocos2dxGLSurfaceView;
import org.cocos2dx.lib.Cocos2dxRenderer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class Camera2GLSurfaceView extends Cocos2dxGLSurfaceView {

    private static final String TAG = "Camera2GLSurfaceView";
    private Camera2Proxy mCameraProxy;
    private SurfaceTexture mSurfaceTexture;
    private CameraDrawer mDrawer;
    private int mRatioWidth = 0;
    private int mRatioHeight = 0;
    private float mOldDistance;
    private int mTextureId = -1;
    private int mBgTextureId = -1;

    public Camera2GLSurfaceView(Context context) {
        this(context, null);
    }

    public Camera2GLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mCameraProxy = new Camera2Proxy((Activity) context);
    }

    @Override
    public void setCocos2dxRenderer(Cocos2dxRenderer renderer) {
        super.setCocos2dxRenderer(new CameraRender());
    }


    private void setAspectRatio(int width, int height) {
        if (width < 0 || height < 0) {
            throw new IllegalArgumentException("Size cannot be negative.");
        }
        mRatioWidth = width;
        mRatioHeight = height;
        post(new Runnable() {
            @Override
            public void run() {
                requestLayout(); // must run in UI thread
            }
        });
    }

    public Camera2Proxy getCameraProxy() {
        return mCameraProxy;
    }

    public SurfaceTexture getSurfaceTexture() {
        return mSurfaceTexture;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        if (0 == mRatioWidth || 0 == mRatioHeight) {
            setMeasuredDimension(width, height);
        } else {
            if (width < height * mRatioWidth / mRatioHeight) {
                setMeasuredDimension(width, width * mRatioHeight / mRatioWidth);
            } else {
                setMeasuredDimension(height * mRatioWidth / mRatioHeight, height);
            }
        }
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        if (event.getPointerCount() == 1) {
//            // 点击聚焦
//            mCameraProxy.focusOnPoint((int) event.getX(), (int) event.getY(), getWidth(), getHeight());
//            return true;
//        }
//        switch (event.getAction() & MotionEvent.ACTION_MASK) {
//            case MotionEvent.ACTION_POINTER_DOWN:
//                mOldDistance = getFingerSpacing(event);
//                break;
//            case MotionEvent.ACTION_MOVE:
//                float newDistance = getFingerSpacing(event);
//                if (newDistance > mOldDistance) {
//                    mCameraProxy.handleZoom(true);
//                } else if (newDistance < mOldDistance) {
//                    mCameraProxy.handleZoom(false);
//                }
//                mOldDistance = newDistance;
//                break;
//            default:
//                break;
//        }
//        return super.onTouchEvent(event);
//    }
//
//    private static float getFingerSpacing(MotionEvent event) {
//        float x = event.getX(0) - event.getX(1);
//        float y = event.getY(0) - event.getY(1);
//        return (float) Math.sqrt(x * x + y * y);
//    }

    private boolean isOpenCamera = false;

    public void openCamera() {

        if (!isOpenCamera) {
            mCameraProxy.openCamera(getWidth(), getHeight());
            isOpenCamera = true;
        }
    }

    public void closeCamera() {
        if (isOpenCamera) {
            mCameraProxy.releaseCamera();
            isOpenCamera = false;
            OpenGLUtils.deleteTexture(mTextureId);
        }

    }
    private OnShortBitmapListener mShortListener = null;
    public void screenShot(OnShortBitmapListener listener){
        mShortListener = listener;
    }
    public void switchCamera(){
        mCameraProxy.switchCamera(getWidth(),getHeight());
    }
    public class CameraRender extends Cocos2dxRenderer {

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            super.onSurfaceCreated(gl, config);


            mTextureId = OpenGLUtils.getExternalOESTextureID();
            mBgTextureId = OpenGLUtils.getExternalOESTextureID();
            mSurfaceTexture = new SurfaceTexture(mTextureId);
//            mCameraProxy.setPreviewSurface(getHolder());
            mCameraProxy.setPreviewSurface(mSurfaceTexture, getWidth(), getHeight());
            mDrawer = new CameraDrawer();
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            Log.d(TAG, "onSurfaceChanged. thread: " + Thread.currentThread().getName());
            Log.d(TAG, "onSurfaceChanged. width: " + width + ", height: " + height);
            super.onSurfaceChanged(gl, width, height);
//            if (mCameraProxy.getPreviewSize() != null) {
//                int previewWidth = mCameraProxy.getPreviewSize().getWidth();
//                int previewHeight = mCameraProxy.getPreviewSize().getHeight();
//                if (width > height) {
//                    setAspectRatio(previewWidth, previewHeight);
//                } else {
//                    setAspectRatio(previewHeight, previewWidth);
//                }
//            }
            GLES20.glViewport(0, 0, width, height);
        }

        private int[] ts = new int[]{mTextureId};

        @Override
        public void onDrawFrame(GL10 gl) {
            //  clear 上一帧绘制的图
            GLES20.glDepthMask(true);
            GLES20.glClearColor(0, 0, 0, 0);
            GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
            GLES20.glDepthMask(false);
            GLES20.glFlush();
            if (isOpenCamera) {
                mSurfaceTexture.updateTexImage();
                mDrawer.draw(mTextureId, mCameraProxy.isFrontCamera());
            }
            super.onDrawFrame(gl);
            if (mShortListener != null){
                try {
                    Bitmap bitmap =  BitmapUtils.INSTANCE.createBitmapFromGLSurface(getContext()
                            , ScreenExtKt.getScreenWidth(), ScreenExtKt.getScreenHeight(),gl);
                    mShortListener.onBitmap(bitmap);
                } catch (Exception e) {

                }
                mShortListener = null;
            }
        }
    }

    public static interface OnShortBitmapListener{
        public void onBitmap(Bitmap bitmap);
    }
}
