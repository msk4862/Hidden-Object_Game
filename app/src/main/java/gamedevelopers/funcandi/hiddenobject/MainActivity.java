package gamedevelopers.funcandi.hiddenobject;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements  View.OnClickListener {
    Button level1,level2, level3;
    TextView t1;

    Typeface typeface;@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        level1=(Button) findViewById(R.id.button);
        t1=(TextView) findViewById(R.id.textView);

        level1.setOnClickListener(this);

        level2=(Button) findViewById(R.id.button2);
        level2.setOnClickListener(this);


        level3 = (Button) findViewById(R.id.button3);
        level3.setOnClickListener(this);



        typeface = Typeface.createFromAsset(this
                .getAssets(), "raleway.ttf");
        t1.setTypeface(Typeface.create(typeface, Typeface.BOLD));
        //t2.setTypeface(typeface);
        level1.setTypeface(typeface);
        level2.setTypeface(typeface);
        level3.setTypeface(typeface);

        System.gc();
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.button){

            Intent i=new Intent(this,GamelevelSoul.class);
            i.putExtra("level",1);
            Log.d("mylog","In Main Activity");
            startActivity(i);
        }
        else if(v.getId()==R.id.button2){


            Intent i=new Intent(this,GamelevelSoul.class);
            i.putExtra("level",2);
            Log.d("mylog","In Main Activity");
            startActivity(i);
        }

        else if (v.getId() == R.id.button3) {


            Intent i = new Intent(this, GamelevelSoul.class);
            i.putExtra("level", 3);
            startActivity(i);
        }
    }

    protected void onDestroy() {
        super.onDestroy();

        unbindDrawables(findViewById(R.id.hidden_main));
        System.gc();
    }

    private void unbindDrawables(View view) {
        if (view.getBackground() != null) {
            view.getBackground().setCallback(null);
        }
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                unbindDrawables(((ViewGroup) view).getChildAt(i));
            }
            ((ViewGroup) view).removeAllViews();
        }
    }
}
