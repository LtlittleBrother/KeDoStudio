package com.cocos.base;




import com.kedo.commonlibrary.application.BaseApplication;

import java.util.List;

public class Live2dHelper {
    public static String defaultPath = BaseApplication.mApplication.getFilesDir().getAbsolutePath() + "/live2d/";

    public static String defaultModel = "A001爱丽丝";

    public static String defaultJsonPath = "A001.model3.json";
    /**
     * 设置模型路径
     *
     * @param modelPath
     */
    public static native void setParentPath(String modelPath);

    /**
     * 设置模型路径
     *
     * @param modelPath
     */
    public static native void setModelPath(String modelPath);

    /**
     * 设置模型json路径
     *
     * @param modelJsonPath
     */
    public static native void setJsonPath(String modelJsonPath);

    /**
     * 设置背景图片
     *
     * @param bgPath
     */
    public static native void setBackground(String bgPath);


    /**
     * 模型缩放 原始值为1
     *
     * @param scale
     */
    public static native void scaleTo(float scale,boolean isSave);

    /**
     * 模型移动
     * 默认值 0
     *
     * @param x 0
     * @param y 0
     */
    public static native void moveTo(float x, float y,boolean isSave);

    /**
     * 换装
     *
     * @param textures 纹理相对路径
     */
    public static native void changeDress(List<Part> textures);

    public static native void setIsUpdateDrag(boolean  isUpdateDrag);

    /**
     * 换脱下
     *
     */
    public static native void cancelDress(int position);

    /**
     * 切换模型
     */
    public static native void changeModel(int index);

    /**
     * 开始某个动画
     */
    public static native void startMotion(String group, int no, int priority);

    /**
     * 拍照
     *
     * @param image
     */
    public static native void takePicture(String image);

    /**
     * 设置背景音乐路径
     *
     * @param musicPath
     */
    public static native void setBackgroundMusic(String musicPath);

    /**
     * 播放背景音乐
     *
     * @param musicPath
     */
    public static native void playBackgroundMusic(String musicPath);

    /**
     * 停止播放背景音乐
     */
    public static native void stopBackgroundMusic();

    /**
     * 预穿服装
     *
     * @param textures
     */
    public static native void setDress(List<Part> textures);

    public static native void stopLive2d();

    public static native void restartLive2d();

    /**
     * 重新添加一个模型一般是打开新页面的时候使用
     *
     * @param modelPath
     * @param jsonPath
     * @param bgPath
     */
    public static native void addModel(String modelPath, String jsonPath, String bgPath);

    /**
     * 开始循环执行某个动画
     */
    public static native void startMotionCircle(String group);

    /**
     * 停止循环动作
     */
    public static native void stopMotionCircle();

    private static CocosTouchListener cocosTouchListener;
    private static LoadListener loadListener;

    public static void setTouchListener(CocosTouchListener listener) {
        cocosTouchListener = listener;
    }


    public static void touchPart(String part) {
        if (null != cocosTouchListener) {
            cocosTouchListener.touchPart(part);
        }
    }

    public static void setLoadListener(LoadListener loadListener) {
        Live2dHelper.loadListener = loadListener;
    }

    public static void loadSuccess() {
        if (null != loadListener) {
            loadListener.loadSuccess();
        }
    }

    public interface CocosTouchListener {
        void touchPart(String part);
    }

    public interface LoadListener {
        void loadSuccess();
    }

    public static native void restorePosition();

    /**
     * 显示雪花粒子特效
     *
     * @param assetsPng
     */
    public static native void startSnow(String assetsPng);

    /**
     * 停止雪花粒子特效
     */
    public static native void stopSnow();

    /**
     * 进入专注模式
     */
    public static native void switchAttentionModel(Boolean isMan);

    /**
     * 设置嘴型
     *
     * @param y 嘴巴厚度
     * @param w 嘴巴长度（目前这个参数没用，是根据厚度自动计算的）
     */
    public static native void setMouthOpen(float y, float w);

    /**
     * 设置音效音量
     * volume must be within the range of 0.0 as the minimum and 1.0 as the maximum.
     *
     * @param volume
     */
    public static native void setEffectVolume(float volume);

    /**
     * 设置背景音效音量
     * volume must be within the range of 0.0 as the minimum and 1.0 as the maximum.
     *
     * @param volume
     */
    public static native void setBackgroundMusicVolume(float volume);

    /**
     * 商城打开情况下的半身模式
     */
    public static void toHalf(Boolean isMan) {
        if (isMan) {
            moveTo(0f, -0.8f,false);
            scaleTo(1.7f,false);
        } else {
            moveTo(0f, -0.7f,false);
            scaleTo(1.8f,false);
        }
    }

    /**
     * 商城打开状态下的全身模式
     */
    public static void toLiveHole(Boolean isMan) {
        if (isMan) {
            moveTo(0f, 0f,false);
            scaleTo(0.88f,false);
        } else {
            moveTo(0f, 0f,false);
            scaleTo(1f,false);
        }
    }

    /**
     * 设置编辑模式  true为编辑模式  编辑模式下  双指放大缩小
     * @param isEdit
     */
    public static native void setEditModel(boolean isEdit);
}
