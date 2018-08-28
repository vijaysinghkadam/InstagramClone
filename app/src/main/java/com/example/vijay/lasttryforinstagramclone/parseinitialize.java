package com.example.vijay.lasttryforinstagramclone;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;

/**
 * Created by vijay on 8/17/18.
 */

public class parseinitialize extends Application {
    @Override
    public void onCreate(){
        super.onCreate();
        Parse.enableLocalDatastore(this);
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("a3c3c950ec9ae96f6b2e6aecbfcc079b72ef048b")
                // if defined
                .clientKey("88546b718e44f05fe133337f2b5571c42ce485c9")
                .server("http://18.222.189.3:80/parse")
                .enableLocalDataStore()
                .build()
        );

        //ParseUser.enableAutomaticUser();
        ParseACL defaultACL =new ParseACL();
        defaultACL.setPublicReadAccess(true);
        defaultACL.setPublicWriteAccess(true);
        ParseACL.setDefaultACL(defaultACL,true);
    }
}
