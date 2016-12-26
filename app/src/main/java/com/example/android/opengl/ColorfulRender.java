package com.example.android.opengl;

import android.opengl.GLSurfaceView;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by chenshaosina on 16/10/4.
 */
public class ColorfulRender implements GLSurfaceView.Renderer {

    //顶点坐标
    private float[] mTriangleArray = {
            0f,1f,2f,
            -1f,-1f,2f,
            1f,-1f,2f
    };
    //三角形各顶点颜色(三个顶点)
    private float[] mColor = new float[]{
            1, 1, 0, 1,
            0, 1, 1, 1,
            1, 0, 1, 1
    };
    private FloatBuffer mTriangleBuffer,mColorBuffer;

    public ColorfulRender() {
        //先初始化buffer，数组的长度*4，因为一个float占4个字节
        ByteBuffer bb = ByteBuffer.allocateDirect(mTriangleArray.length * 4);
        //逆时针
        bb.order(ByteOrder.nativeOrder());
        mTriangleBuffer = bb.asFloatBuffer();
        //缓冲区依次放入顶点数据
        mTriangleBuffer.put(mTriangleArray);
        mTriangleBuffer.position(0);

        //颜色相关
        ByteBuffer colorB = ByteBuffer.allocateDirect(mColor.length * 4);
        colorB.order(ByteOrder.nativeOrder());
        mColorBuffer = colorB.asFloatBuffer();
        mColorBuffer.put(mColor);
        mColorBuffer.position(0);
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        // 设置白色为清屏
        gl10.glClearColor(1, 0, 1, 1);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        float ratio = (float) width / height;
        // 设置OpenGL场景的大小,(0,0)表示窗口内部视口的左下角，(w,h)指定了视口的大小
        gl.glViewport(0, 0, width, height);
        // 设置投影矩阵
        gl.glMatrixMode(GL10.GL_PROJECTION);
        // 重置成单位矩阵
        gl.glLoadIdentity();
        // 设置 透视投影空间，平截头体   (left,right,bottom,top,near,far)
        gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);
        //以下两句声明，以后所有的变换都是针对模型(即我们绘制的图形)
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();

    }

    @Override
    public void onDrawFrame(GL10 gl) {
        // 清除屏幕和深度缓存
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        // 重置当前的模型观察矩阵
        gl.glLoadIdentity();
        // 允许设置顶点
        //GL10.GL_VERTEX_ARRAY顶点数组
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        // 允许设置颜色
        //GL10.GL_COLOR_ARRAY颜色数组
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

        //将三角形在z轴上移动
        gl.glTranslatef(0f, 0.0f, -2.0f);
        // 设置三角形
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mTriangleBuffer);
        // 设置三角形颜色
        gl.glColorPointer(4, GL10.GL_FLOAT, 0, mColorBuffer);
        // 绘制三角形
        gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);

        // 取消颜色设置
        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
        // 取消顶点设置
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);

        //绘制结束
        gl.glFinish();

    }
}
