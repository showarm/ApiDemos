package com.example.android.customview.views.percentProgressView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;

import info.ipeanut.youngsamples.R;


/**
 * http://www.tuicool.com/articles/BZv2UfY
 *
 * 下载   圆环进度条  带百分比  能暂停
 *
 * Created by chenshaosina on 15/10/16.
 */
public class PercentProgressView extends View {

    public final static int STATUS_PEDDING = 1;
    public final static int STATUS_WAITING = 2;
    public final static int STATUS_DOWNLOADING = 3;
    public final static int STATUS_PAUSED = 4;
    public final static int STATUS_FINISHED = 5;


    // 画实心圆的画笔
    private Paint mCirclePaint;
    // 画圆环的画笔
    private Paint mRingPaint;
    // 绘制进度文字的画笔
    private Paint mTxtPaint;
    // 圆形颜色
    private int mCircleColor;
    // 圆环颜色
    private int mRingColor;
    // 半径
    private int mRadius;
    // 圆环宽度    圆形画笔 和 扇形画笔的宽度是 同一个
    private int mStrokeWidth = 2;
    // 圆心x坐标
    private int mXCenter;
    // 圆心y坐标
    private int mYCenter;
    // 总进度
    private int mTotalProgress = 100;
    // 当前进度
    private int mProgress;
    //下载状态
    private int mStatus = 1;


    //默认显示的图片
    private Bitmap mNotBeginImg;
    //暂停时中间显示的图片
    private Bitmap mPausedImg;
    //等待时显示的图片
    private Bitmap mWatiImg;
    //下载完成时显示的图片
    private Bitmap mFinishedImg;

    public PercentProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // 获取自定义的属性
        initAttrs(context, attrs);
        initVariable();

    }

    private void initAttrs(Context context, AttributeSet attrs) {

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.PercentProgressView,0,0);

        mRadius = (int) typedArray.getDimension(R.styleable.PercentProgressView_radius,100);
        mStrokeWidth = (int) typedArray.getDimension(R.styleable.PercentProgressView_strokeWidth,2);
        mCircleColor = typedArray.getColor(R.styleable.PercentProgressView_circleColor, 0xff0000);
        mRingColor = typedArray.getColor(R.styleable.PercentProgressView_ringColor, 0x00ff00);

        mNotBeginImg = ((BitmapDrawable)typedArray.getDrawable(R.styleable.PercentProgressView_notBeginImg)).getBitmap();
        mPausedImg = ((BitmapDrawable)typedArray.getDrawable(R.styleable.PercentProgressView_pausedImg)).getBitmap();
        mWatiImg = ((BitmapDrawable)typedArray.getDrawable(R.styleable.PercentProgressView_waitImg)).getBitmap();
        mFinishedImg = ((BitmapDrawable)typedArray.getDrawable(R.styleable.PercentProgressView_finishedImg)).getBitmap();

        mNotBeginImg = big(mNotBeginImg, mRadius * 2, mRadius * 2);
        mPausedImg = big(mPausedImg, mRadius * 2, mRadius * 2);
        mWatiImg = big(mWatiImg, mRadius * 2, mRadius * 2);
        mFinishedImg = big(mFinishedImg,mRadius*2,mRadius*2);

    }

    /**
     * 三支画笔
     */
    private void initVariable() {

        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setColor(mCircleColor);
//        画笔为空心
        mCirclePaint.setStyle(Paint.Style.STROKE);
        //空心图形边线的宽度   就是画笔的宽度
        mCirclePaint.setStrokeWidth(mStrokeWidth);

        mRingPaint = new Paint();
        mRingPaint.setAntiAlias(true);
        mRingPaint.setColor(mRingColor);
        mRingPaint.setStyle(Paint.Style.STROKE);
        mRingPaint.setStrokeWidth(mStrokeWidth);


        mTxtPaint = new Paint();
        mTxtPaint.setAntiAlias(true);
        mTxtPaint.setColor(Color.parseColor("#52ce90"));
        mTxtPaint.setTextAlign(Paint.Align.CENTER);
        mTxtPaint.setTextSize(24);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // ceil 返回大于或等于a 的最小值
        int width = (int)Math.ceil(mRadius) * 2;
        //决定View的宽高
        setMeasuredDimension(width, width);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        mXCenter = getWidth() / 2;
        mYCenter = getHeight() / 2;
        switch (mStatus) {
            case STATUS_PEDDING:
                canvas.drawBitmap(mNotBeginImg, 0, 0, null);
                break;
            case STATUS_WAITING:
                canvas.drawBitmap(mWatiImg, 0, 0, null);
                break;
            case STATUS_DOWNLOADING:
                drawDownloadingView(canvas);
                break;
            case STATUS_PAUSED:
                drawPausedView(canvas);
                break;
            case STATUS_FINISHED:
                canvas.drawBitmap(mFinishedImg, 0, 0, null);
                break;
        }

    }

    private void drawDownloadingView(Canvas canvas) {
        //画一个空心圆   但画笔是有宽度的
        canvas.drawCircle(mXCenter,mYCenter,mRadius - mStrokeWidth/2,mCirclePaint);

        //绘制扇形需要一个外接矩形
        RectF rectF = new RectF();
        rectF.left = mXCenter - mRadius + mStrokeWidth/2;//考虑画笔宽度
        rectF.top = mYCenter - mRadius + mStrokeWidth/2;
        rectF.right = mXCenter + mRadius - mStrokeWidth/2;
        rectF.bottom = mYCenter + mRadius - mStrokeWidth/2;
        //外接矩形     起始角度    扫描弧度    stroked是false
        canvas.drawArc(rectF,-90,((float)mProgress/mTotalProgress)*360,false,mRingPaint);

        //======================*************************************??????????????????
        //绘制中间百分比文字
        String percentTxt = String.valueOf(mProgress);
        //计算文字垂直居中的baseline
        Paint.FontMetricsInt fontMetrics = mTxtPaint.getFontMetricsInt();
        float baseline = rectF.top + (rectF.bottom - rectF.top - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
        canvas.drawText(percentTxt, mXCenter, baseline, mTxtPaint);

    }

    private void drawPausedView(Canvas canvas) {
        //绘制灰色圆环
        canvas.drawCircle(mXCenter, mYCenter, mRadius - mStrokeWidth/2, mCirclePaint);

        //绘制进度扇形圆环
        RectF oval = new RectF();
        //设置椭圆上下左右的坐标
        oval.left = mXCenter - mRadius + mStrokeWidth/2;
        oval.top = mYCenter - mRadius + mStrokeWidth/2;
        oval.right = mXCenter + mRadius - mStrokeWidth/2;
        oval.bottom = mYCenter + mRadius - mStrokeWidth/2;
        canvas.drawArc(oval, -90, ((float) mProgress / mTotalProgress) * 360, false, mRingPaint);

        //绘制中间暂停图标
        canvas.drawBitmap(mPausedImg, 0, 0, null);
    }

    /**
     * 把图片 fitXY
     * @param b
     * @param x
     * @param y
     * @return
     */
    public static Bitmap big(Bitmap b,float x,float y){
        int width = b.getWidth();
        int height = b.getHeight();

        //比例尺寸
//        float sw = x/width;
//        float sh = y/height;

        float sw=(float)x/width;//要强制转换，不转换我的在这总是死掉。
        float sh=(float)y/height;

        Matrix matrix = new Matrix();
        //缩放作用在图片上  图片宽高作为被除数
        matrix.postScale(sw, sh);

        //width    The number of pixels in each row
        return Bitmap.createBitmap(b,0,0,width,height,matrix,true);

    }


    //＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝对外

    /**
     * 更新进度
     * @param progress
     */
    public void setProgress(int progress) {
        mProgress = progress;
        postInvalidate();
    }

    /**
     * 设置下载状态
     * @param status
     */
    public void setStatus(int status) {
        this.mStatus = status;
        postInvalidate();
    }

    /**
     * 获取下载状态
     * @return
     */
    public int getStatus() {
        return mStatus;
    }



}
