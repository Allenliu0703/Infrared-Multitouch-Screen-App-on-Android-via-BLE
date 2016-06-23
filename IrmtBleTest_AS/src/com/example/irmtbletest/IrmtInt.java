package com.example.irmtbletest;

import java.util.Arrays;
import java.util.List;
import android.util.Log;
import com.example.irmtble.*;
import android.graphics.Canvas;
import com.example.irmtble.TouchScreen.TouchPoint;



public class IrmtInt implements IrmtInterface {
	private final String TAG = "TouchManager";
	private int[][] points;
	private int[] touchstatus;
	public int pointsNum = 10;
	private int pointsCurNum = 0;
	private DrawView mmDrawView = null;
	private Canvas mCanvas = null;
	
	public IrmtInt(){
		points = new int[pointsNum][];
		int i;
		for (i = 0; i<pointsNum; i++) {
			points[i] = new int[3];
		}
		touchstatus = new int [31];
		for (i = 0; i < 31; i++){
			touchstatus[i] = 0;
		}
	}

	public void AddDrawPara(DrawView mDrawView, Canvas cacheCanvas){
		mmDrawView = mDrawView;
		mCanvas = cacheCanvas;
	}
	
	@Override
	public void onSnapshot(int mSnapShot) {
		// TODO Auto-generated method stub
		Log.v(TAG,"mSnapShot " + mSnapShot);
	}

	@Override
	public void onError(int ErrorCode) {
		// TODO Auto-generated method stub
		if (ErrorCode > 0)
			Log.v(TAG, "Bluetooth Connect Error " + ErrorCode);
		else
			Log.v(TAG,"Data Transmission Error " + ErrorCode);
	}

	@Override
	public void onBLconnected() {
		// TODO Auto-generated method stub
		Log.v(TAG, "Conn ");
	}

	@Override
	public void onGestureGet(int GestureNo) {
		// TODO Auto-generated method stub
		Log.v(TAG, "gesture "+GestureNo);
	}

	@Override
	public void onTouchUp(List<TouchPoint> mTouchList) {
		for (TouchPoint mTP:mTouchList) {
			Log.v(TAG, "onTouchUp  " + mTP.pointId + ": " + mTP.pointColor);
			touchstatus[mTP.pointId] = -1;
			//Log.v(TAG, "Point ID " + mTP.pointId + " touchstatus" + touchstatus[mTP.pointId]);
		}
		mmDrawView.updateTouchStatus(touchstatus);
	}

	@Override
	public void onTouchDown(List<TouchPoint> mTouchList) {
		// TODO Auto-generated method stub
        for (int i = 0; i < pointsNum; i++){
            points[i][2] = 0;
            points[i][1] = 0;
            points[i][0] = 0;
        }
		for (TouchPoint mTP : mTouchList) {
			if (pointsCurNum < pointsNum) {
				points[pointsCurNum][0] = mTP.pointX;
				points[pointsCurNum][1] = mTP.pointY;
				points[pointsCurNum][2] = mTP.pointId;
				pointsCurNum++;
				Log.v(TAG, "onTouchDown X " + mTP.pointX + " Y " + mTP.pointY +" Touch ID " + mTP.pointId);
				//touchstatus[mTP.pointId] = 1;
				//Log.v(TAG, "Point ID " + mTP.pointId + " touchstatus" + touchstatus[mTP.pointId]);
			}
			if (pointsCurNum == pointsNum){
				//mmDrawView.updateTouchStatus(touchstatus);
				mmDrawView.setPoints(points);
                Log.v("debug", "Enter setPoint Function");
				mmDrawView.draw(mCanvas);
				mmDrawView.postInvalidate();
                mmDrawView.pointIsSet(true);
				pointsCurNum = 0;
			}
		}
	}

	@Override
	public void onIdGet(long Id) {
		// TODO Auto-generated method stub
		Log.v(TAG,"Id"+Id);
	}


	@Override
	public void onTouchMove(List<TouchPoint> mTouchList) {
		// TODO Auto-generated method stub
		for (TouchPoint mTP:mTouchList){
			Log.v(TAG,"onTouchMove  "+mTP.pointId +":  "+mTP.pointStatus +" "+mTP.pointY);
		}
	}
}
