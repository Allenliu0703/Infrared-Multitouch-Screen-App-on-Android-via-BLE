package com.example.irmtbletest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import com.example.irmtble.*;

public class DrawView extends View {
    public Paint mPaint = new Paint();
    private Bitmap mDrawBitmap;
    private Smooth mSmooth = new Smooth();
    private int[][] lastPoint;
    private int[][] pointSet;
    private float mScreenXMax = 32768;
    private float mScreenYMax = 32768;
    private float mScreenWeight;
    private float mScreenHeight;
    public boolean isSmooth = true;
    private float ratioX;
    private float ratioY;
    boolean isFoundMatch;
    boolean pointArrayIsSet;
    boolean isTouchUp [];

    public DrawView(Context context, boolean startSmooth) {
        super(context);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStrokeWidth(2);
        this.setWillNotDraw(false);
        lastPoint = new int[31][3];
        isTouchUp = new boolean[31];
        for(int i = 0; i <= 30; i++){
            lastPoint[i][2] = -1;
            isTouchUp[i] = false;
        }
        isSmooth = startSmooth;
        pointArrayIsSet = false;

    }

    public void AddDrawPara(Bitmap mBitmap, int ScreenWight, int ScreenHeight){
        mDrawBitmap = mBitmap;
        mScreenWeight = (float) ScreenWight;
        mScreenHeight = (float) ScreenHeight;
        ratioX = mScreenHeight / mScreenXMax;
        ratioY = mScreenWeight / mScreenYMax;
    }
    public void updateTouchStatus(int[] touchstatus){
        for(int i = 0; i <= 30; i++){
            if (touchstatus[i] == -1) {
                isTouchUp[i] = true;
                touchstatus[i] = 1;
            }
        }
    }
    public void pointIsSet(boolean isSet){
        if (isSet == true){
            pointArrayIsSet = true;
        }else{
            pointArrayIsSet = false;
        }
    }
    public void setPoints(int[][] Points){
        pointSet = new int[Points.length][];
        int i,j;
        if (isSmooth){
//            for(i = 0; i < Points.length; i++){
//                for(j = 1; j < Points.length; j++){
//                    if (Points[i][2] == Points[j][2]){
//
//                    }
//                }
//            }
            Log.v("debug", "Smooth");
            pointSet = mSmooth.SmoothLine(Points);
            int l;
            for (l = 0; l < pointSet.length; l++)
            {
                pointSet[l][0] = (int)(mScreenHeight-pointSet[l][0] * ratioX);
                pointSet[l][1] = (int)(pointSet[l][1] * ratioY);
                //Log.v("PointSet Info", "ID = " + pointSet[l][2] + " X = " + pointSet[l][0] +" Y = " + pointSet[l][1]);
            }
        }
        else {
            Log.v("debug", "No Smooth");
            for (i = 0; i < Points.length; i++) {
                pointSet[i] = new int[3];
                pointSet[i][0] = (int) (mScreenHeight - Points[i][0] * ratioX);
                pointSet[i][1] = (int) (Points[i][1] * ratioY);
                pointSet[i][2] = Points[i][2];
                //Log.v("PointSet Info", "ID = " + pointSet[i][2] + " X = " + pointSet[i][0] +" Y = " + pointSet[i][1]);
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        isFoundMatch = false;
        Log.v("Draw","xuebeng");
        for(int i = 1; i < 31; i++){
            Log.v("TouchUp", " ID = " + i + " value = "+ isTouchUp[i]);
            if(isTouchUp[i]){
                lastPoint[i][0] = 0;
                lastPoint[i][1] = 0;
                lastPoint[i][2] = 0;
            }
        }
        if (pointSet != null && pointArrayIsSet) {
            for (int i = 0; i<pointSet.length; i++) {
                //Log.v("PointSet Info", "ID = " + pointSet[i][2] + " X = " + pointSet[i][0] +" Y = " + pointSet[i][1]);
            }
            for(int i = 1; i < 31; i++){
                if(lastPoint[i][2] == 1){
                    //Log.v("lastPoint", " ID = " + i + " lastPoint = " + lastPoint[i][2]);
                    for(int j = 0; j < pointSet.length; j++){
                        if(pointSet[j][2] == i){
                            //Log.v("pointSet", "!!!!!!!!!!!!!!!!! ID = " + i);
                            canvas.drawLine(pointSet[j][1], pointSet[j][0], lastPoint[i][1], lastPoint[i][0], mPaint);
                            //Log.v("line", "lastpoint i = " + i + " X = " + lastPoint[i][0]+ " Y = " + lastPoint[i][1]);
                            //Log.v("line", "currentpoint X = " + pointSet[j][0]+ " Y = " + pointSet[j][1]);
                            lastPoint[i][2] = 0;
                            break;
                        }
                    }
                }
            }
            for(int i = 0; i < pointSet.length; i++){
                for (int j = i+1; j < pointSet.length; j++){
                    if (pointSet[i][2] == pointSet[j][2]){
                        canvas.drawLine(pointSet[i][1], pointSet[i][0], pointSet[j][1], pointSet[j][0], mPaint);
                        isFoundMatch = true;
                        lastPoint[(pointSet[i][2])][2] = 0;
                        //Log.v("pointSet", " i = " + i + " ID = " + pointSet[i][2]);
                        break;
                    }
                }
                if(isFoundMatch == false && !isTouchUp[(pointSet[i][2])]) {
                    lastPoint[(pointSet[i][2])][0] = pointSet[i][0];
                    lastPoint[(pointSet[i][2])][1] = pointSet[i][1];
                    lastPoint[(pointSet[i][2])][2] = 1;

                }
                isFoundMatch = true;
            }
            for(int i = pointSet.length-1; i >= 0; i--){
                if ((pointSet[i][2] != 0) && !isTouchUp[(pointSet[i][2])]){
                    lastPoint[(pointSet[i][2])][0] = pointSet[i][0];
                    lastPoint[(pointSet[i][2])][1] = pointSet[i][1];
                    lastPoint[(pointSet[i][2])][2] = 1;
                }
            }
            for(int i = 1; i < 31; i++){
                isTouchUp[i] = false;
                Log.v("lastPoint info", "ID = "+ i + " X = " + lastPoint[i][0] + " Y = " + lastPoint[i][1] + " Value = " + lastPoint[i][2]);
                //Log.v("TouchUp", "value = "+ isTouchUp[i]);
            }
        }
        if (mDrawBitmap != null) {
            canvas.drawBitmap(mDrawBitmap, 0, 0, mPaint);
        }
    }
}


