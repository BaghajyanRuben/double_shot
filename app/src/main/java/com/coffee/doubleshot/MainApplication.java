package com.coffee.doubleshot;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by rubenbaghajyan on 10/3/17.
 */

public class MainApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		Fresco.initialize(this);
	}
}
