package non.com.idc.jokrey.evade.animation;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;

import non.com.idc.jokrey.evade.animation.evade.game.EvadeGameEngine;
import evade.game.EvadeGamePipeline;
import evade.game.ScoreSet;
import util.animation.android.display.AnimationView;
import util.animation.android.pipeline.AnimationDrawerAndroid;
import util.animation.util.AEColor;

public class EvadeGameFullScreenActivity extends Activity {
    EvadeGameEngine engine;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        getWindow().setFlags(AccessibilityNodeInfoCompat.ACTION_NEXT_HTML_ELEMENT, AccessibilityNodeInfoCompat.ACTION_NEXT_HTML_ELEMENT);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());

        engine = new EvadeGameEngine();
        SharedPreferences sharedPref = getPreferences(0);
        for (int i = 0; i < 10; i++) {
            engine.highScores.add(new ScoreSet(sharedPref.getInt("high_score_" + i + "_points", -1), new AEColor(sharedPref.getInt("high_score_" + i + "_color", AEColor.DARK_GRAY.argb))));
        }
        engine.upandvalidateScores();


        AnimationView anmView = new AnimationView(this, engine, new EvadeGamePipeline(new AnimationDrawerAndroid()));
        anmView.handler.getPipeline().fpsCaps=30;
        setContentView(anmView);
        anmView.start();
    }

    protected void onPause() {
        int i;
        super.onPause();
        SharedPreferences.Editor editor = getPreferences(0).edit();
        for (i = 0; i < engine.lastScores.size(); i++) {
            editor.putInt("last_score_" + i + "_points", ((ScoreSet) engine.lastScores.get(i)).getPoints());
            editor.putInt("last_score_" + i + "_color", ((ScoreSet) engine.lastScores.get(i)).getColor().argb);
        }
        for (i = 0; i < engine.highScores.size(); i++) {
            editor.putInt("high_score_" + i + "_points", ((ScoreSet) engine.highScores.get(i)).getPoints());
            editor.putInt("high_score_" + i + "_color", ((ScoreSet) engine.highScores.get(i)).getColor().argb);
        }
        editor.commit();
    }
}
