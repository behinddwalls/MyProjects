package com.traffic.userinterface;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;

public class TrafficPortalActivity extends Activity {

	protected int _splashTime = 5000;

	private Thread splashTread;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		final TrafficPortalActivity sPlashScreen = this;

		// thread for displaying the PocketMessenger
		splashTread = new Thread() {
			@Override
			public void run() {
				try {
					synchronized (this) {
						wait(_splashTime);
					}

				} catch (InterruptedException e) {
				} finally {
					finish();

					Intent i = new Intent();
					i.setClass(getApplicationContext(), LoginActivity.class);
					startActivity(i);

					stop();
				}
			}
		};

		splashTread.start();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			synchronized (splashTread) {
				splashTread.notifyAll();
			}
		}
		return true;
	}
}
