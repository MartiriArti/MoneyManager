package com.whoami.moneytracker.ui.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

public class PieChartView extends View {

    private Paint slicePaint;
    private float[] dataPoints;

    public PieChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        slicePaint = new Paint();
        slicePaint.setAntiAlias(true); // smoothing
        slicePaint.setDither(true);
        slicePaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int startTop = 0;
        int startLeft = 0;
        int endBottom = getWidth();
        int endRight = endBottom;

        RectF rectF = new RectF(startLeft,startTop,endRight,endBottom);
        float[] scaledValues = scale();
        float sliceStartPoint = 0;

        for(float scaledValue : scaledValues)
        {
            Random random = new Random();
            int color = Color.argb(100, random.nextInt(256),random.nextInt(256),random.nextInt(256));
            slicePaint.setColor(color);
            canvas.drawArc(rectF, sliceStartPoint, scaledValue, true, slicePaint);
            sliceStartPoint += scaledValue;
        }
    }

    public void setDataPoints(float[] dataPoints)
    {
        this.dataPoints = dataPoints;
        invalidate();

    }

    private float[] scale()
    {
        float[] scaledValues = new float[dataPoints.length];

        for(int i = 0; i < dataPoints.length; i++)
        {
            scaledValues[i] = (dataPoints[i] / getTotal()) * 360;
        }
        return scaledValues;
    }

    private float getTotal()
    {
        float total = 0;
        for(float val : dataPoints)
        {
            total += val;
        }
        return total;
    }
}