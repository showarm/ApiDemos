package com.example.android.customview.views.rollingTextView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import java.util.Random;

/**
 * http://www.tuicool.com/articles/nE3A3yQ
 * 弹幕View
 *
 *
 * Created by chenshaosina on 15/10/20.
 */
public class RollingTextView extends TextView {

    private Paint paint = new Paint();
    private int posX; //x坐标
    private int posY; //y坐标
    private int windowWidth; //屏幕宽
    private int windowHeight; //屏幕高

    private Random random = new Random();
    //随机字体大小
    private int textSize = 30; //字体大小
    public static final int TEXT_MIN = 10;
    public static final int TEXT_MAX = 60;
    //随机字体颜色
    private int color = 0xffffffff;

    public RollingTextView(Context context) {
        this(context, null);
    }

    public RollingTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RollingTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化
     *
     * posX 第一次绘制时应该为屏幕宽，表示从屏幕最右边开始移动，然后调用滚动线程，直到滚出屏幕。
     这里要提一下 getWidth() 方法，这个方法如果在构造函数里面调用，得到的是0，在 onDraw() 方法里面调用得到的是本view的宽度，
     这是因为自定义View的机制，要在调用过 onMeasure() 后才能得到自身的宽高。
     我这里用 getWindowVisibleDisplayFrame(rect); 能在初始化时就得到屏幕宽高。
     */
    private void init() {

        //得到屏幕宽高
        Rect rect = new Rect();
        getWindowVisibleDisplayFrame(rect);
        windowWidth = rect.width();
        windowHeight = rect.height();
        posX = windowWidth;
        //5.设置y为屏幕高度内内随机，需要注意的是，文字是以左下角为起始点计算坐标的，所以要加上TextSize的大小
        posY = textSize + random.nextInt(windowHeight - textSize);


        //1.设置文字大小
        textSize = TEXT_MIN + random.nextInt(TEXT_MAX - TEXT_MIN);
        paint.setTextSize(textSize);
        //2.设置文字颜色
        color = Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
        paint.setColor(color);


    }

    /**
     * 绘制
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        paint.setTextSize(30);
//        paint.setColor(0xffffffff);//白色

        //因为文字的坐标是从左下角开始算的，文字大小设为了30，y也要设为30
//        canvas.drawText(getText().toString(),0,30,paint);

        canvas.drawText(getText().toString(),posX,posY,paint);

    }

    /**
     * 移动可以用Animation动画，但是我这里用的是线程重绘
     * 在onDraw里面新建一个线程，该线程会一直运行，每次运行主函数时会对 BarrageView 的 x 值产生影响（减少）。
     */
    class RollThread extends Thread{

        @Override
        public void run() {

            while (true){
                //1 动画逻辑
                animLogic();

                //2 重新绘制  该方法会自动调用 onDraw() 方法
                postInvalidate();

                //3.延迟，不然会造成执行太快动画一闪而过
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //关闭线程逻辑判断
                if (needStopRollThread()) {
                    Log.i("azzz", getText() + "   -线程停止！");
                    //监听器 用来资源回收等
                    if (mOnRollEndListener != null) {
                        mOnRollEndListener.onRollEnd();
                    }
                    break;
                }

            }
        }
    }

    /**
     * 把x坐标减少8像素
     */
    private void animLogic(){

        posX -= 8;
    }

    private boolean needStopRollThread() {
        if (posX <= -paint.measureText(getText().toString())) {
            return true;
        }
        return false;
    }

    /**
     * 滚动结束接听器
     */
    interface OnRollEndListener {
        void onRollEnd();
    }
    private OnRollEndListener mOnRollEndListener;
    public void setOnRollEndListener(OnRollEndListener onRollEndListener) {
        this.mOnRollEndListener = onRollEndListener;
    }
}
