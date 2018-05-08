package com.example.zhonghuazheng.mycircleview.circle;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * Created by zhonghua.zheng on 2018/4/24.
 */

public class Circleview extends View {
    Paint textPaint,linePatit,lineColorPatit;//画圆的画笔
    RectF oval;//外界正方形
    private float sweepAngle=300;
    private float radius;//圆半径
    //定义进度
    float progress = 0;
    float limitAngle;//旋转角度

    public Circleview(Context context) {
        super(context);
    }

    public Circleview(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        lineColorPatit =new Paint();
        linePatit = new Paint();
        //线宽
        lineColorPatit.setStrokeWidth(3);
        //设置画笔抗锯齿
        lineColorPatit.setAntiAlias(true);
        //设置画笔颜色
        linePatit.setColor(Color.GRAY);
        //线宽
        linePatit.setStrokeWidth(3);
        //设置画笔抗锯齿
        linePatit.setAntiAlias(true);

    }

    public float getProgress() {

        return progress;

    }

    public void setProgress(float progress) {

        this.progress = progress;

        invalidate();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        //以最小值为正方形的长
        int len = Math.min(width, height);
        radius = len/2;
        //实例化矩形
        oval=new RectF(0,0,len,len);
        //设置测量高度和宽度（必须要调用，不然无效果）
        setMeasuredDimension(len, len);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画圆弧的方法
//        canvas.drawArc(oval, startAngle, sweepAngle, false,paint);
        //画刻度线的方法
        drawViewLine(canvas);
        //画有颜色刻度线的方法
        drawViewColorLine(canvas);
        //绘制文字
        drawScoreText(canvas);

    }

    private void drawViewColorLine(Canvas canvas) {

        //先保存之前canvas的内容
        canvas.save();
        //移动canvas(X轴移动距离，Y轴移动距离)
        canvas.translate(radius,radius);
        //旋转坐标系
        canvas.rotate(30);

        lineColorPatit.setColor(Color.GREEN);

        //确定每次旋转的角度
        float rotateAngle=sweepAngle/60;
        for(int i=0;i<progress;i++){
            if (i > 12 && i < 30) {
                lineColorPatit.setColor(Color.YELLOW);
            } else if (i > 29) {
                lineColorPatit.setColor(Color.RED);
            }
            //画一条刻度线
            canvas.drawLine(0,radius,0,radius-20,lineColorPatit);
            canvas.rotate(rotateAngle);
        }

        //操作完成后恢复状态
        canvas.restore();

    }

    private void drawViewLine(Canvas canvas) {
        //先保存之前canvas的内容
        canvas.save();
        //移动canvas(X轴移动距离，Y轴移动距离)
        canvas.translate(radius,radius);
        //旋转坐标系
        canvas.rotate(30);

        //确定每次旋转的角度
        float rotateAngle=sweepAngle/60;
        for(int i=0;i<61;i++){
            //画一条刻度线
            canvas.drawLine(0,radius,0,radius-20,linePatit);
            canvas.rotate(rotateAngle);
        }

        //操作完成后恢复状态
        canvas.restore();
    }

    private void drawScoreText(Canvas canvas) {
        float smallRadius=radius-60;
        //绘制文本
        textPaint=new Paint();
        //设置文本居中对齐
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setColor(Color.BLUE);
        textPaint.setTextSize(smallRadius/2);
        //score需要通过计算得到
        canvas.drawText(""+limitAngle,radius,radius,textPaint);
        //绘制分，在分数的右上方
        textPaint.setColor(Color.GRAY);
        textPaint.setTextSize(smallRadius/4);
        canvas.drawText("平均血糖",radius,radius-smallRadius/2,textPaint);
        //绘制点击优化在分数的下方
        textPaint.setTextSize(smallRadius/4);
        canvas.drawText("mmol/L",radius,radius+smallRadius/2,textPaint);

    }

    public void startAnimator() {
        ObjectAnimator mAnimator = ObjectAnimator.ofFloat(this, "progress", 0, limitAngle);
        mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mAnimator.setDuration(1000);
        mAnimator.start();
    }

    public void changeAngle(Float LimitAngle) {
        limitAngle = LimitAngle;

        invalidate();
        startAnimator();
    }
}
