package com.example.irmtbletest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import java.util.ArrayList;
import java.lang.Integer;
import com.example.irmtble.*;

public class DrawView extends View {
    public Paint mPaint = new Paint();
    private Bitmap mDrawBitmap;
    private Smooth mSmooth = new Smooth();
    private int[][] lastPoint;
    private int[][] pointSet;
    private int[][] pointSettemp;
    private int[][] temp;
    private int[] position;
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
        pointSet = new int[Points.length][3];
        temp = new int[Points.length][3];
        int num = 0;
        position = new int[Points.length];
        int i,j,x,y;
        boolean isChecked;
        if (isSmooth){
            Log.v("debug", "Smooth");
            for(i = 0; i < Points.length; i++){
                Log.v("Times", "i = " + i);
                num = 0;
                isChecked = false;
                position[num] = i;
                for(int h = 0; h < i; h++){
                    if(Points[i][2] == Points[h][2]){
                        isChecked = true;
                        break;
                    }
                }
                if(isChecked == false) {
                    for (j = i+1; j < Points.length; j++) {
                        if (Points[i][2] == Points[j][2]) {
                            num++;
                            position[num] = j;
                        }
                    }
                    if (num >= 1) {
                        int size = num + 1;
                        Log.v("size", "size = " + size);
                        pointSettemp = new int[size][3];
                        /* Add previous point
                        if (lastPoint[Points[position[0]][2]][2] == 1) {
                            int index = Points[position[0]][2];
                            pointSettemp[0][0] = (int)(((double)mScreenHeight-(double)lastPoint[index][0])/(double)ratioX);;
                            pointSettemp[0][1] = (int)((double)lastPoint[index][1]/ratioY);
                            pointSettemp[0][2] = lastPoint[index][2];
                            Log.v("Parameters", "ratioX = " + ratioX + "ratioY = "+ratioY + "screen height = " + mScreenHeight);
                            Log.v("PointSettemp Last Infop", "ID = " + lastPoint[index][2] + " X = " + lastPoint[index][0] +" Y = " + lastPoint[index][1]);
                            Log.v("PointSettemp Last Info", "ID = " + pointSettemp[0][2] + " X = " + pointSettemp[0][0] +" Y = " + pointSettemp[0][1]);
                        }
                        */
                        for (x = 0; x < size; x++) {
                            pointSettemp[x][0] = Points[position[x]][0];
                            pointSettemp[x][1] = Points[position[x]][1];
                            pointSettemp[x][2] = Points[position[x]][2];
                            Log.v("PointSettemp Info", "ID = " + pointSettemp[x][2] + " X = " + pointSettemp[x][0] +" Y = " + pointSettemp[x][1]);
                        }
                        temp = mSmooth.SmoothLine(pointSettemp);
                        for (y = 0; y < size; y++) {
                            pointSet[position[y]][0] = temp[y][0];
                            pointSet[position[y]][1] = temp[y][1];
                            pointSet[position[y]][2] = temp[y][2];
                            Log.v("temp Info", "ID = " + temp[y][2] + " X = " + temp[y][0] +" Y = " + temp[y][1]);
                        }
                    } else {
                        pointSet[i][0] = Points[i][0];
                        pointSet[i][1] = Points[i][1];
                        pointSet[i][2] = Points[i][2];
                    }
                }
            }
            for (int l = 0; l < pointSet.length; l++)
            {
                pointSet[l][0] = (int)(mScreenHeight-pointSet[l][0] * ratioX);
                pointSet[l][1] = (int)(pointSet[l][1] * ratioY);
                Log.v("PointSet Info", "ID = " + pointSet[l][2] + " X = " + pointSet[l][0] +" Y = " + pointSet[l][1]);
            }


            /*
            pointSet = mSmooth.SmoothLine(Points);
            int l;
            for (l = 0; l < pointSet.length; l++)
            {
                pointSet[l][0] = (int)(mScreenHeight-pointSet[l][0] * ratioX);
                pointSet[l][1] = (int)(pointSet[l][1] * ratioY);
                //Log.v("PointSet Info", "ID = " + pointSet[l][2] + " X = " + pointSet[l][0] +" Y = " + pointSet[l][1]);
            }
            */
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
            if(isTouchUp[i]){
                //Log.v("TouchUp", " ID = " + i + " value = "+ isTouchUp[i]);
                lastPoint[i][0] = 0;
                lastPoint[i][1] = 0;
                lastPoint[i][2] = 0;
            }
        }
        if (pointSet != null && pointArrayIsSet) {
            for (int i = 0; i<pointSet.length; i++) {
                //Log.v("PointSet Info", "i = " + i + " ID = " + pointSet[i][2] + " X = " + pointSet[i][0] +" Y = " + pointSet[i][1]);
            }
            for(int i = 1; i < 31; i++){
                if(lastPoint[i][2] == 1){
                    //Log.v("lastPoint", " ID = " + i + " lastPoint = " + lastPoint[i][2]);
                    for(int j = 0; j < pointSet.length; j++){
                        if(pointSet[j][2] == i){
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
                isFoundMatch = false;
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
            if (!isTouchUp[(pointSet[pointSet.length-1][2])]) {
                lastPoint[(pointSet[pointSet.length - 1][2])][0] = pointSet[pointSet.length - 1][0];
                lastPoint[(pointSet[pointSet.length - 1][2])][1] = pointSet[pointSet.length - 1][1];
                lastPoint[(pointSet[pointSet.length - 1][2])][2] = 1;
            }
            if(pointArrayIsSet) {
                for (int i = 1; i < 31; i++) {
                    isTouchUp[i] = false;
                    if (lastPoint[i][2] == 1) {
                        //Log.v("lastPoint info", "ID = " + i + " X = " + lastPoint[i][0] + " Y = " + lastPoint[i][1] + " Value = " + lastPoint[i][2]);
                    }
                    //Log.v("TouchUp", "value = "+ isTouchUp[i]);
                }
            }
        }
        if (mDrawBitmap != null) {
            canvas.drawBitmap(mDrawBitmap, 0, 0, mPaint);
        }
    }
}


