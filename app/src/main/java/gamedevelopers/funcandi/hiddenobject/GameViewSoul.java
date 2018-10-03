package gamedevelopers.funcandi.hiddenobject;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class GameViewSoul extends SurfaceView {

    private ArrayList<String> hiddenobj = new ArrayList<>();
    Bitmap background, scaled;
    private SurfaceHolder holder;
    private int timer,timer1=200, timer2=150,timer3=100,level=1;
    private CopyOnWriteArrayList<GameObjctsSoul> gameobj= new CopyOnWriteArrayList<>();
    Typeface typeface;
    private Bitmap pausemusic, Scaledplay, playmusic, Scaledmute;
    MediaPlayer  musicon;
    MediaPlayer ScoreUp;
    public static int flag=0,Soulscore=0,foundallitems=0,infoflag=0;
    private GameLoopThreadSoul gameLoopThread;
    private long lastClick;
    Bitmap infobutton, Scaledinfo;//bmp;

    Bitmap hand, Scaledhand;
    Intent i, i1;
    Context context;




    public int Score;
    public int RemainingTime;

    Paint p;


    public GameViewSoul(final Context context, int l) {

        super(context);
        this.level=l;

        deleteCache(context);


       // prefManager=new PrefManager(getContext());
        Log.d("mylog","In game view  soul");
        musicon= MediaPlayer.create(context, R.raw.chimera);
        musicon.setLooping(true);
        ScoreUp = MediaPlayer.create(context, R.raw.scoreup);
        gameLoopThread = new GameLoopThreadSoul(this);
        Log.d("mylog","run loop");


        p = new Paint();


        if(level==1)
        {
            this.background = BitmapFactory.decodeResource(getResources(), R.drawable.hiddenobject_back2);
        }

        else if(level==2)
        {
            this.background = BitmapFactory.decodeResource(getResources(), R.drawable.back);
        }

        else if (level==3)
        {
            this.background = BitmapFactory.decodeResource(getResources(), R.drawable.back3);
        }
        Log.d("mylog","get background");



        int x=context.getResources().getDisplayMetrics().widthPixels;
        int y=context.getResources().getDisplayMetrics().heightPixels;

        scaled = Bitmap.createScaledBitmap(this.background,  x,y,  false);

        hand = BitmapFactory.decodeResource(getResources(), R.drawable.hiddenobject_hand);
        Scaledhand = Bitmap.createScaledBitmap(hand, x/10, x/10,  false);

        typeface = Typeface.createFromAsset(context.getAssets(), "raleway.ttf");
        this.context=context;
        i1 = new Intent(context.getApplicationContext(), MainActivity.class);


        // gameobj=new GameObjcts(this,bmp);
        infobutton= BitmapFactory.decodeResource(getResources(), R.drawable.hiddenobject_hint);
        Scaledinfo = Bitmap.createScaledBitmap(infobutton, x/20, x/20, false);
        pausemusic = BitmapFactory.decodeResource(getResources(), R.drawable.hiddenobject_play);
        Scaledmute = Bitmap.createScaledBitmap(pausemusic, x/20, x/20, false);
        playmusic = BitmapFactory.decodeResource(getResources(), R.drawable.hiddenobject_mute);
        Scaledplay = Bitmap.createScaledBitmap(playmusic, x/20, x/20 , false);

        Log.d("mylog","get resources");
        holder = getHolder();

        holder.addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

                boolean retry = true;
                Log.d("mylog","surface destroyed");
                gameLoopThread.setRunning(false);

                musicon.stop();
                while (retry) {

                    try {
                        gameLoopThread.join();
                        retry = false;
                    }

                    catch (InterruptedException e)
                    {
                        Log.e("mylog","error");
                    }

                }

            }


            @Override

            public void surfaceCreated(SurfaceHolder holder) {
                if (level == 1) {
                    timer = timer1;
                }

                else if (level == 2) {
                    timer = timer2;
                }

                else {
                    timer = timer3;
                }

                Soulscore=0;
                infoflag=0;
                foundallitems=0;
                Log.d("mylog","create characters");
                gameLoopThread.setRunning(true);

                try
                {
                    gameLoopThread.start();
                    Log.d("mylog","game stars");
                }

                catch (Exception e)
                {
                    Log.e("mylog","error");
                }


                allcharacters();

                Collections.shuffle(hiddenobj);
                musicon.start();


            }


            @Override

            public void surfaceChanged(SurfaceHolder holder, int format,

                                       int width, int height) {

            }

        });


    }

    public void allcharacters() {

        if (level == 1) {

            gameobj.add(createcharacter(R.drawable.hiddenobject_drums,getWidth()/5,getHeight()/2+getHeight()/14,"Drum"));
            gameobj.add(createcharacter(R.drawable.hiddenobject_hat,getWidth()/5,getHeight()/12,"Hat"));
            gameobj.add(createcharacter(R.drawable.hiddenobject_gloves,getWidth()/16,getHeight()/2,"Gloves"));
            gameobj.add(createcharacter(R.drawable.hiddenobject_bag,getWidth()/2+getWidth()/6,getHeight()/5,"Bag"));
            gameobj.add(createcharacter(R.drawable.hiddenobject_corn, getWidth()/2, getHeight()/2+getHeight()/16, "Corn"));
            gameobj.add(createcharacter(R.drawable.hiddenobject_ballons, getWidth()/2+getWidth()/8, 0, "Balloons"));
            gameobj.add(createcharacter(R.drawable.hiddenobject_hen, getWidth()-getWidth()/14, getHeight()-getHeight()/3, "Hen"));
            gameobj.add(createcharacter(R.drawable.hiddenobject_sword, getWidth()/7, getHeight()/7, "Sword"));
            gameobj.add(createcharacter(R.drawable.hiddenobject_pizza, getWidth()/7, getHeight()-getHeight()/4, "Pizza"));
            gameobj.add(createcharacter(R.drawable.hiddenobject_rugby,getWidth()/3+getWidth()/12,getHeight()/8,"Rugby Ball"));
            gameobj.add(createcharacter(R.drawable.hiddenobject_spoon, getWidth()/2, getHeight()-getHeight()/4, "Spoon"));
            gameobj.add(createcharacter(R.drawable.hiddenobject_binoculurs, getWidth()/2+getWidth()/3, getHeight()/2+ getHeight()/12, "Binocular"));
            gameobj.add(createcharacter(R.drawable.hiddenobject_sandwitch, getWidth()/2, getHeight()/2-getHeight()/12, "Sandwitch"));
            gameobj.add(createcharacter(R.drawable.hiddenobject_candy, getWidth()/2-getWidth()/12, getHeight()/4, "Candy"));
            gameobj.add(createcharacter(R.drawable.hiddenobject_lamp2, getWidth()/2-getWidth()/12, getHeight()-getHeight()/5, "Lamp"));



            Log.d("mylog","get chr");

            hiddenobj.add("Bag");
            hiddenobj.add("Hat");
            hiddenobj.add("Candy");
            hiddenobj.add("Drum");
            hiddenobj.add("Gloves");
            hiddenobj.add("Rugby Ball");
            hiddenobj.add("Corn");
            hiddenobj.add("Balloons");
            hiddenobj.add("Hen");
            hiddenobj.add("Sword");
            hiddenobj.add("Pizza");
            hiddenobj.add("Spoon");
            hiddenobj.add("Binocular");
            hiddenobj.add("Sandwitch");
            hiddenobj.add("Lamp");

        }



        else if(level==2){



            gameobj.add(createcharacter(R.drawable.hiddenobject_horn,getWidth()/5+getWidth()/28,getHeight()/2+getHeight()/14,"Horn"));
            gameobj.add(createcharacter(R.drawable.hiddenobject_car,getWidth()-getWidth()/13,getHeight()/2+getHeight()/17,"Car"));
            gameobj.add(createcharacter(R.drawable.hiddenobject_pot,getWidth()/16,getHeight()/2,"Pot"));
            gameobj.add(createcharacter(R.drawable.hiddenobject_kite,getWidth()/2+getWidth()/6,getHeight()/5,"Kite"));
            gameobj.add(createcharacter(R.drawable.hiddenobject_pencil, getWidth()/2, getHeight()/2+getHeight()/16, "Pencil"));
            gameobj.add(createcharacter(R.drawable.hiddenobject_mic, getWidth()/2+getWidth()/8, 0, "Mic"));
            gameobj.add(createcharacter(R.drawable.hiddenobject_bucket, getWidth()-getWidth()/14, getHeight()-getHeight()/3, "Bucket"));
            gameobj.add(createcharacter(R.drawable.hiddenobject_knife, getWidth()/7, getHeight()/7, "Knife"));
            gameobj.add(createcharacter(R.drawable.hiddenobject_hotdog, getWidth()/7, getHeight()-getHeight()/4, "Hotdog"));
            gameobj.add(createcharacter(R.drawable.hiddenobject_helemet,getWidth()/3+getWidth()/12,getHeight()/16,"Helmet"));
            gameobj.add(createcharacter(R.drawable.hiddenobject_magnifier, getWidth()/2-getWidth()/28, getHeight()-getHeight()/4, "Magnifier"));
            gameobj.add(createcharacter(R.drawable.hiddenobject_headphones, getWidth()/2+getWidth()/8, getHeight()/2+ getHeight()/16, "Headphones"));
            gameobj.add(createcharacter(R.drawable.hiddenobject_teddy2, getWidth()/2-getWidth()/20, getHeight()/2-getHeight()/12, "Teddy"));
            gameobj.add(createcharacter(R.drawable.hiddenobject_flag, getWidth()/2-getWidth()/12, getHeight()/4, "Flag"));
            gameobj.add(createcharacter(R.drawable.hiddenobject_gun1, getWidth()/2+getWidth()/28, getHeight()/3, "Gun"));

            Log.d("mylog","get chr");

            hiddenobj.add("Pot");
            hiddenobj.add("Car");
            hiddenobj.add("Knife");
            hiddenobj.add("Flag");
            hiddenobj.add("Horn");
            hiddenobj.add("Helmet");
            hiddenobj.add("Magnifier");
            hiddenobj.add("Teddy");
            hiddenobj.add("Bucket");
            hiddenobj.add("Headphones");
            hiddenobj.add("Hotdog");
            hiddenobj.add("Pencil");
            hiddenobj.add("Gun");
            hiddenobj.add("Kite");
            hiddenobj.add("Mic");


        }

        else if (level==3)
        {


            gameobj.add(createcharacter(R.drawable.hiddenobject_arrow,getWidth()/2+getWidth()/6,getHeight()/2,"Arrow"));
            gameobj.add(createcharacter(R.drawable.rose,getWidth()-getWidth()/10,getHeight()/2-getHeight()/10,"Vase"));
            gameobj.add(createcharacter(R.drawable.pan,getWidth()-getWidth()/3+getWidth()/30,getHeight()/12,"Pan"));
            gameobj.add(createcharacter(R.drawable.hiddenobject_penholder,getWidth()/2+getWidth()/7,getHeight()/4+getHeight()/14,"Penholder"));
            gameobj.add(createcharacter(R.drawable.hiddenobject_train,getWidth()/2+getWidth()/13,getHeight()/16,"Train"));
            gameobj.add(createcharacter(R.drawable.hiddenobject_trophy,getWidth()/2-getWidth()/16,getHeight()-getHeight()/4,"Trophy"));
            gameobj.add(createcharacter(R.drawable.hiddenobject_bag,getWidth()-getWidth()/4,getHeight()/2,"Bag"));
            //gameobj.add(createcharacter(R.drawable.guitar2,getWidth()/2 +getWidth()/10,getHeight()/2,"UFO"));
            gameobj.add(createcharacter(R.drawable.magichat,getWidth()-getWidth()/4,getHeight()/2+getHeight()/7,"Hat"));
            gameobj.add(createcharacter(R.drawable.hiddenobject_duck, getWidth()/2, getHeight()/3, "Duck"));
            gameobj.add(createcharacter(R.drawable.hiddenobject_banana, getWidth()/4+getWidth()/12,getHeight()/2 + getHeight()/13, "Banana"));
            //gameobj.add(createcharacter(R.drawable.hiddenobject_ship, getWidth()/2-getWidth()/13, getHeight()/2, "Ship"));
            //gameobj.add(createcharacter(R.drawable.hiddenobject_globe, getWidth()/2, getHeight()/2+getHeight()/8, "Globe"));
            //gameobj.add(createcharacter(R.drawable.hiddenobject_plane, getWidth()/8, getHeight()/2, "Plane"));
            gameobj.add(createcharacter(R.drawable.hiddenobject_bandage, getWidth()/8, getHeight()/4, "Bandage"));

            Log.d("mylog","get chr");

            hiddenobj.add("Train");
            hiddenobj.add("Vase");
            hiddenobj.add("Bag");
            hiddenobj.add("Trophy");
            hiddenobj.add("Hat");
            hiddenobj.add("Arrow");
            hiddenobj.add("Penholder");
            hiddenobj.add("Duck");
            hiddenobj.add("Banana");
            hiddenobj.add("Pan");
            hiddenobj.add("Bandage");



        }
    }

    public GameObjctsSoul createcharacter (int resouce,int x,int y, String name) {


        Bitmap bmp = BitmapFactory.decodeResource(getResources(), resouce);

        return new GameObjctsSoul(this,bmp,x,y,name);

    }

    protected  void update() {
        foundallitems=0;
        Soulscore=0;
        infoflag = 0;
        musicon.start();

        if (level == 1) {
            timer = timer1;
        }
        else if (level ==2) {
            timer=timer2;
        }
        else{
            timer=timer3;
        }

        gameobj.removeAll(gameobj);
        allcharacters();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    protected void onDraw(Canvas canvas) {

        Log.d("mylog","draw");
        p.setColor(Color.BLACK);
        p.setTextSize(getWidth()/40);
        p.setTypeface(Typeface.create(typeface, Typeface.BOLD));
        //this.background=scaled;
        if (canvas!=null && scaled!=null) {
            canvas.drawBitmap(scaled, 0, 0, null);
        }
        else {
            callEndActivity();
        }

        Log.d("mylog","background draw");

        //  if(write.isEmpty()) {
        Log.d("mylog","write is empty");

            /*for (GameObjctsSoul sp : gameobj) {

                sp.Ondraw(canvas);
            }*/


        try {
            for (GameObjctsSoul it : gameobj) {

                it.Ondraw(canvas);

            }
        }
        catch (ConcurrentModificationException e) {
            e.printStackTrace();
        }


        p.setAlpha(200);

        canvas.drawRoundRect( getWidth()/150,getHeight()/150,  getWidth()/14+getWidth()/8+getWidth()/9,getHeight()/18+getWidth()/40,getHeight()/8,getHeight()/8, p);
        canvas.drawRoundRect(getWidth()-3*Scaledinfo.getWidth()-Scaledinfo.getWidth()/2, getHeight()/150, getWidth(),Scaledplay.getHeight()+getHeight()/40, getHeight()/8,getHeight()/8, p);
        p.setColor(Color.WHITE);
        p.setTypeface(typeface);

        canvas.drawText("SCORE: "+ Soulscore,  getWidth()/17+getWidth()/8,getHeight()/20+getHeight()/50, p);
        canvas.drawText("TIMER: "+timer , getWidth()/30, getHeight()/20+getHeight()/50, p);
        if(foundallitems==0) {
            timer--;
        }
        Log.d("mylog","draw score and timer");
        if (timer<0) {

            infoflag = 0;
            flag=0;
            timer = 0;
            p.setColor(Color.BLACK);
            p.setAlpha(99);
            canvas.drawRect(0, 0, getWidth(), getHeight(), p);
            p.setAlpha(100);
            p.setColor(Color.WHITE);

            p.setTextSize(getWidth() / 20);
            p.setTypeface(typeface);
            canvas.drawText("TIME UP!!", getWidth()/2-getWidth()/8, getHeight()/4, p);
            canvas.drawText("Score is "+Soulscore, getWidth()/2-getWidth()/8, getHeight()/2, p);
            canvas.drawText("Click on the screen ", getWidth()/2-getWidth()/5, getHeight()/2+getHeight()/7, p);                    //gameobj.removeAll(gameobj);

            Log.d("mylog","game over");
        }
        if (foundallitems==1) {

            int remTime = timer;
            int score = Soulscore;
            score = remTime+score;


            ///////////////////////////////////////////////
            Score = score;
            RemainingTime = remTime;
            //////////////////////////////////////////////



            p.setColor(Color.BLACK);
            p.setAlpha(99);
            canvas.drawRect(0,0,getWidth(),getHeight(),p);
            p.setAlpha(100);
            p.setColor(Color.WHITE);

            p.setTextSize(getWidth()/20);
            p.setTypeface(typeface);
            canvas.drawText("YOU WON!!", getWidth()/2-getWidth()/8, getHeight()/4, p);
            canvas.drawText("Score is "+score, getWidth()/2-getWidth()/8, getHeight()/2, p);
            canvas.drawText("Click on the screen ", getWidth()/2-getWidth()/5, getHeight()/2+getHeight()/7, p);
            gameobj.removeAll(gameobj);
            //  foundallitems=0;
            Log.d("mylog","found all items");
        }

        if(infoflag==1){
            p.setColor(Color.BLACK);
            p.setAlpha(150);

            canvas.drawRoundRect(getWidth()/4,getHeight()/6,getWidth()-getWidth()/4,getHeight()-getHeight()/5,getHeight()/8,getHeight()/8,p);
            p.setColor(Color.WHITE);

            p.setTypeface(typeface);
            p.setTextSize(getWidth()/25);
            canvas.drawText("Search hidden items ",getWidth()/4+ getWidth()/18, getHeight()/4+getHeight()/10, p);
            p.setTextSize(getWidth()/35);

            String s = hiddenobj.get(0);


            try {
                for (GameObjctsSoul sp : gameobj) {

                    if (sp.getname().equals(s)) {

                        Bitmap sc = Bitmap.createScaledBitmap(sp.bmp, getWidth()/10, getWidth()/10, false);
                        canvas.drawBitmap(sc, getWidth()/2-sc.getWidth()/2, getHeight()/4+getHeight()/6, null);
                        canvas.drawText(""+s, getWidth()/2-sc.getWidth()/2, getHeight()/4+getHeight()/6+sc.getHeight()+sc.getHeight()/3, p);
                        canvas.drawBitmap(Scaledhand, getWidth()/2+sc.getWidth()/2, getHeight()/4+getHeight()/6, null);
                        break;
                    }


                }

            }
            catch (Exception e){
                e.printStackTrace();
            }

            //canvas.drawText("Click on the item",getWidth()/bb2+ getWidth()/10,getHeight()/4+getHeight()/12, p);
            //canvas.drawText(" you discover", getWidth()/bb2+ getWidth()/10, getHeight()/4+getHeight()/12+getHeight()/12, p);


        }
        p.setAlpha(120);


        canvas.drawBitmap(Scaledinfo, getWidth() - 3*Scaledinfo.getWidth(), getHeight()/65, p);
        if (flag == 0) {
            canvas.drawBitmap(Scaledmute, getWidth() - Scaledmute.getWidth()-Scaledmute.getWidth()/2, getHeight()/65, p);
        } else {

            canvas.drawBitmap(Scaledplay, getWidth() - Scaledplay.getWidth()-Scaledmute.getWidth()/2, getHeight()/65, p);
        }
        Log.d("mylog","draw buttons");

        p.setAlpha(100);

        //}

        p.setColor(Color.BLACK);
        p.setAlpha(99);
        canvas.drawRect(0, getHeight() / 2+getHeight() / 3,getWidth(), getHeight(), p);
        p.setTextSize(getWidth() / 25);
        p.setTypeface(typeface);
        p.setColor(Color.WHITE);
        //canvas.drawText("Find hidden objects  ",getWidth()/4+ getWidth() / 15,getHeight() / bb2+getHeight() / 4+getWidth()/ 20, p);
        p.setTextSize(getWidth() / 35);
        int j=getHeight()/ 2+getHeight() / 4+getHeight()/6,i=getWidth()/12,k=3;

        for(Object str:hiddenobj){

            canvas.drawText(""+str.toString(), i, j, p);
            if(j==getHeight()-getHeight() / 12) {
                i = i +getWidth()/12;

                //j=getHeight()/ bb2+getHeight()/6+getHeight() / 4;
                i=getWidth()/12*k;
                k=k+3;
            }
            i=i+getWidth()/12;
        }

         /*    for (int i = write.size() - bb1; i >= 0; i--) {
                 write.get(i).onDraw(canvas);
             }*/
        Log.d("mylog","draw alert");

    }


    public boolean onTouchEvent(MotionEvent event) {

        Log.d("mylog","touch event");
        if (System.currentTimeMillis() - lastClick > 500) {

            lastClick = System.currentTimeMillis();

            synchronized (getHolder()) {

                int x = (int) event.getX(), y = (int) event.getY();

                if(infoflag == 1) {
                    if (x>getWidth()/4 && x<getWidth()-getWidth()/4 && y >getHeight()/6 && y<getHeight()-getHeight()/5) {
                        infoflag=0;
                        invalidate();
                    }
                }


                else {

                    if ((timer == 0) || (foundallitems == 1)) {
                        // update();
                        callEndActivity();
                    }

                    int f=0, displayedObject;

                    for (int i = gameobj.size() - 1; i >= 0; i--) {

                        f=0;

                        GameObjctsSoul obj = gameobj.get(i);
                        if (obj.isCollision(event.getX(), event.getY())) {
                            String id = obj.getname();
                            int nam = hiddenobj.indexOf(id);
                            if (nam != -1) {
                                if (hiddenobj.size()>= 4) {
                                    displayedObject= 4;
                                }
                                else{
                                    displayedObject= hiddenobj.size();
                                }

                                for (int j = 0; j < displayedObject; ++j) {
                                    if (id.equals(hiddenobj.get(j))) {
                                        f=1;
                                    }

                                }
                                if (f ==1) {
                                    gameobj.remove(obj);
                                    hiddenobj.remove(nam);
                                    Soulscore++;

                                    if (flag == 0) {
                                        ScoreUp.start();
                                    }
                                }

                            }


                            if ((gameobj.isEmpty())) {
                                Soulscore += timer;
                                foundallitems = 1;
                            }

                            break;
                        }
                    }
                }


                if ((x > getWidth() -  Scaledplay.getWidth()-Scaledmute.getWidth()/2) && (y < Scaledplay.getHeight())) {

                    if (flag == 0) {
                        flag = 1;

                        musicon.pause();


                    } else {

                        flag = 0;
                        musicon.start();
                        musicon.setLooping(true);

                    }

                }
                if ((x > getWidth() - 3*Scaledinfo.getWidth()) &&(x < getWidth() -  2*Scaledplay.getWidth())&& (y < Scaledplay.getHeight())) {

                    if (infoflag == 0) {

                        infoflag = 1;
                    }
                    else {

                        infoflag = 0;
                    }
                }

            }
        }
        return true;
    }

    private void callEndActivity()
  {


            i1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.getApplicationContext().startActivity(i1);

    }


    //CACHE DELETION

    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();

            deleteDir(dir);
        }catch (Exception e){

        }
    }


    public  static boolean deleteDir(File dir) {
        if((dir != null) && dir.isDirectory()) {
            String[] c=  dir.list();

            for (int i=0; i < c.length; ++i) {
                boolean success = deleteDir(new File(dir, c[i]));

                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if(dir!=null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }
}