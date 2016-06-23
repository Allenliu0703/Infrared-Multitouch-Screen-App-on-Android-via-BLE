package com.example.irmttest;

import com.example.irmt.*;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.view.View;
import android.view.View.OnClickListener;
import android.graphics.Canvas;

public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";
    
    private  String DeviceName = null;
    public BLCommService mBLCommService = null;
    public IrmtInt mIrmtInt = new IrmtInt();
    private Button buttonScan = null;
    private TextView mText = null;
    private int mCanvasWidth=0;
    private int mCanvasHeight=0;
    private Canvas cacheCanvas = null;
    private FrameLayout canvasLayout=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        canvasLayout = (FrameLayout)findViewById(R.id.canvasLayout);
        mCanvasHeight =canvasLayout.getHeight();
        mCanvasWidth =canvasLayout.getWidth();
        buttonScan = (Button)findViewById(R.id.buttonScan);
        mText = (TextView)findViewById(R.id.editText1);
        mBLCommService = new BLCommService(this,mIrmtInt);

        //��ʼ���ӵİ���
        buttonScan.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            	DeviceName =  mText.getText().toString();
            	//DeviceName ="irmt04";
            	mBLCommService.userConnect(DeviceName, null, null);
            	Log.v(TAG, DeviceName);
            }
        });
    }
	//
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }
    //
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //
	@Override
    protected void onStart() {
        super.onStart();
        setTitle("STATE_Start");
    }
    //
	@Override
    protected void onDestroy() {
        setTitle("STATE_Destroy");
        super.onDestroy();
    }
    //
	@Override
    public void onResume() {
        super.onResume();
    }
    protected void onStop() {
        setTitle("STATE_NONE");
        super.onStop();
    }
}