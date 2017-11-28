package com.bwie.yang1128;

import android.app.Application;

import org.xutils.x;


public class RegistApplication extends Application{
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
	x.Ext.setDebug(BuildConfig.DEBUG);
    }
}