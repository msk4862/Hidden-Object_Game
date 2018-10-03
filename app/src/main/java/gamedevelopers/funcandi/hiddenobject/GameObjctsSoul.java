package gamedevelopers.funcandi.hiddenobject;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;


public class GameObjctsSoul {

    private float x1, y1;
    private int width,height;
    private boolean drawrec=false;
    Bitmap bmp;
    String name;


    GameObjctsSoul(GameViewSoul gameView, Bitmap bmp, int x1, int y1, String name) {
        Log.d("mylog","in Game obj soul");
        this.drawrec=false;
        if(bmp!=null) {
            this.bmp = bmp;

            Bitmap resizedBitmap = Bitmap.createScaledBitmap(bmp, gameView.getWidth() / 18, gameView.getWidth() / 18, false);
            this.bmp = resizedBitmap;
            this.width = this.bmp.getWidth();
            this.height = this.bmp.getHeight();
        }
        else{
            this.drawrec=true;
            this.width=gameView.getWidth() / 15;
            this.height=gameView.getWidth() / 15;
        }
        this.name=name;
        this.x1 = x1;
        this.y1= y1;


    }
    public void  Ondraw(Canvas canvas){

        Paint p=new Paint(Color.BLACK);
        if(this.drawrec){
            p.setAlpha(200);
            canvas.drawRect(x1,y1,x1+width,y1+height,p);
        }else {

            p.setAlpha(220);
            canvas.drawBitmap(bmp, x1, y1, p);
        }
    }
    public boolean isCollision(float x2, float y2) {
        return x2 > x1 && x2 < x1 + width && y2 > y1 && y2 < y1 + height;
    }
    public String getname(){
        return name;
    }

}
