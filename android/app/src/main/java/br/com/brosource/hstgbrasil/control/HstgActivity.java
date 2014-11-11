package br.com.brosource.hstgbrasil.control;

import android.app.Activity;

import com.facebook.AppEventsLogger;

/**
 * Created by rodrigohenriques on 11/10/14.
 */
public class HstgActivity extends Activity {
    @Override
    protected void onResume() {
        super.onResume();
        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }
}
