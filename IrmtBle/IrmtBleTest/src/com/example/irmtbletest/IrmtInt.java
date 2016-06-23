package com.example.irmtbletest;

import java.util.List;
import android.util.Log;
//import com.example.irmtble.*;
import com.example.BLCoomLib.*;
import com.example.irmtble.TouchScreen.TouchPoint;

public class IrmtInt implements IrmtInterface {
	private final String TAG = "TouchManager";

	@Override
	public void onSnapshot(int mSnapShot) {
		// TODO Auto-generated method stub
		Log.v(TAG,"mSnapShot " + mSnapShot);
	}

	@Override
	public void onError(int ErrorCode) {
		// TODO Auto-generated method stub
		Log.v(TAG,"onError " + ErrorCode);
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
		for (TouchPoint mTP:mTouchList){
			Log.v(TAG,"onTouchUp  "+mTP.pointId +": "+mTP.pointColor);
		}
	}

	@Override
	public void onTouchDown(List<TouchPoint> mTouchList) {
		// TODO Auto-generated method stub
		for (TouchPoint mTP:mTouchList){
			Log.v(TAG,"onTouchDown  "+mTP.pointId +":  "+mTP.pointStatus +" "+mTP.pointY);
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
