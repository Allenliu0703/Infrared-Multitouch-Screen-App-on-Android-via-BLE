package com.example.irmtble;

import java.util.List;
import com.example.irmtble.TouchScreen.TouchPoint;

public interface IrmtInterface {

    public abstract void onGestureGet(int GestureNo);
    public abstract void onTouchUp(List<TouchPoint> mTouchList);
    public abstract void onTouchDown(List<TouchPoint> mTouchList);
    public abstract void onTouchMove(List<TouchPoint> mTouchList);
    public abstract void onSnapshot(int mSnapShot);
    public abstract void onIdGet(long touchScreenID);
    public abstract void onError(int ErrorCode);
    public abstract void onBLconnected();
//    public abstract void onErrorDetect(int ErrorNum);
}
