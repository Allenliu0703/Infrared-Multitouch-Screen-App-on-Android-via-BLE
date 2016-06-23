package com.example.irmtbletest;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.example.irmtble.*;


public class MainActivity extends Activity {
    public IrmtInt mIrmtInt = new IrmtInt();
    public BLCommService mBLCommService = null;

    private Button mButton = null;
    private Button mButton_disc = null;
    private Button buttonClear = null;
    private Button buttonSmooth =null;
    private EditText mInputText = null;
    public BleCommUUIDs userUUIDs = new BleCommUUIDs();
    
    // Draw part
	public DrawView mDrawView = null;
    private int mCanvasWidth = 0;
    private int mCanvasHeight = 0;
    private Bitmap mBitmap;  //
    private Canvas cacheCanvas = null;
    private FrameLayout canvasLayout=null;

    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Log.v("BLService", "OnCreate");
		mButton = (Button)findViewById(R.id.button1);
		mButton_disc = (Button)findViewById(R.id.button2);
		buttonClear = (Button)findViewById(R.id.buttonClear);
        buttonSmooth = (Button)findViewById(R.id.buttonSmooth);
		mInputText = (EditText)findViewById(R.id.editText1);
		mBLCommService = new BLCommService(this,mIrmtInt);
		// Draw part
        canvasLayout = (FrameLayout)findViewById(R.id.canvasLayout);
        mDrawView =new DrawView(this, true); // true means turn on smooth processing

        mDrawView.setMinimumHeight(300);
        mDrawView.setMinimumWidth(300);
        canvasLayout.addView(mDrawView);
        
		mButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mInputText.getText().toString();
                String[] uuidStr;
                uuidStr = userUUIDs.lookup(mInputText.getText().toString());
                mBLCommService.userConnect(mInputText.getText().toString(), null, null, uuidStr);

                // Draw part
                mCanvasHeight = canvasLayout.getHeight();
                mCanvasWidth = canvasLayout.getWidth();
                mBitmap = Bitmap.createBitmap(mCanvasWidth, mCanvasHeight, Bitmap.Config.ARGB_8888);
                cacheCanvas = new Canvas();
                cacheCanvas.setBitmap(mBitmap);
                mDrawView.AddDrawPara(mBitmap, mCanvasWidth, mCanvasHeight);
                mIrmtInt.AddDrawPara(mDrawView, cacheCanvas);
                cacheCanvas.drawColor(Color.WHITE);
                mDrawView.invalidate();
            }
        });
		mButton_disc.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            	mBLCommService.UserDisconn();

            }
        });
		buttonClear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                cacheCanvas.drawColor(Color.WHITE);
                mDrawView.invalidate();
            }
        });
        buttonSmooth.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mDrawView.isSmooth == false){
                    mDrawView.isSmooth = true;
                    mDrawView.mPaint.setColor(Color.BLACK);
                    buttonSmooth.setText("SMOOTH ON");
                }
                else {
                    mDrawView.isSmooth = false;
                    mDrawView.mPaint.setColor(Color.RED);
                    buttonSmooth.setText("SMOOTH OFF");
                }
            }
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}

