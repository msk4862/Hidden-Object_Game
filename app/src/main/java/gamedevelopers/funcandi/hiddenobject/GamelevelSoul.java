package gamedevelopers.funcandi.hiddenobject;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class GamelevelSoul extends AppCompatActivity {
    int level;
    String flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Log.d("mylog","In game level soul");
        Intent i=getIntent();
        level=i.getIntExtra("level",1);
        setContentView(new GameViewSoul(this,level));

        System.gc();

    }

    @Override
    protected void onPause() {
        super.onPause();

        finish();

    }

    @Override
    protected void onStart() {
        super.onStart();

        System.gc();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        System.gc();

    }

    @Override
    protected void onResume() {
        super.onResume();
        //setContentView(new GameViewSoul(this,level,flag));
    }

    protected void onDestroy() {
        super.onDestroy();

        System.gc();
    }


}