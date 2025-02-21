package com.example.assignment.edits;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.assignment.R;
import com.example.assignment.edits.box.glassBox;
import com.example.assignment.edits.box.metalBox;
import com.example.assignment.edits.box.paperBox;
import com.example.assignment.edits.box.plasticBox;
import com.example.assignment.mgp2d.core.GameActivity;
import com.example.assignment.mgp2d.core.GameEntity;
import com.example.assignment.mgp2d.core.GameScene;

import java.util.Vector;

public class AppGameScene extends GameScene{
    //scene will be observing itemEntity to see when item hits boundary, so scene is observer in this situation
    private Bitmap _backgroundBitmap;
    Vector<GameEntity> _gameEntities = new Vector<>();
    @Override
    public void onCreate(){
        super.onCreate();
        int screenWidth = GameActivity.instance.getResources().getDisplayMetrics().widthPixels;
        int screenHeight = GameActivity.instance.getResources().getDisplayMetrics().heightPixels;
        Bitmap BackgroundBitmap = BitmapFactory.decodeResource(GameActivity.instance.getResources(), R.drawable.kyuu_pic);
        _backgroundBitmap = Bitmap.createScaledBitmap(BackgroundBitmap, screenWidth, screenHeight, true);
        _gameEntities.add(new ItemEntity());
        //thrs prob a better way to do tis but brain struggled to render them at intended location already, am not going to squish my brain even more to come out wif hypothetical unreliable bs
        _gameEntities.add(new paperBox());
        _gameEntities.add(new plasticBox());
        _gameEntities.add(new metalBox());
        _gameEntities.add(new glassBox());
    }
    @Override
    public void onUpdate(float dt) {
        if (gameTimer < 0)  //lazy stop run condition
            return;
        else
            gameTimer -= dt;

        GameActivity.instance.pointerUpdate();
        for (GameEntity entity :_gameEntities) {
            //toss flick direction to item update for position update
             /*if (entity.getEntityClass().equals("ItemEntity"))    //if entity list is current an item entity
            {
                if (((ItemEntity)entity).get_direction_permit()) //is allowing receiving of flick direction
                    ((ItemEntity)entity).receiveFlickDirection(getFlickDirection());    //looks scuffed, taken from stack overflow O|< -> error because receive takes in vector 2, change receive function parameter to enum
                //current bug: when app starts, runs receive flick direction since get_direction is true by default. but since item doesnt actually update and hit screen boundary, get_direction is never reset to true and remains stuck at false state
            }*/
            entity.onUpdate(dt);
        }
    }

    @Override
    public void onRender(Canvas canvas) {
        canvas.drawBitmap(_backgroundBitmap,0,0,null);
        renderPointer(canvas);
        for (GameEntity entity :_gameEntities)
            entity.onRender(canvas);
        Paint paint = new Paint();
        paint.setTextSize(100);
        paint.setColor(Color.parseColor("#000000"));
        canvas.drawText(Float.toString(gameTimer), 25, 75, paint);
    }


    //------------------------------------------------------scoring stuff-------------------------------------------------------------
    protected static float gameTimer = 30; //duration left until game ends
    protected int score = 0;
    private static float addToTime = 5;



    public void scoreUpdate()
    {
        //first item in entity list is always itemEntity
        GameEntity itemObj = _gameEntities.get(0);
        for (int num = 1; num < _gameEntities.size(); num++)
        {
            if (Rect.intersects(itemObj.getEntityRect(), _gameEntities.get(num).getEntityRect()))
            {
                score += 1;
                gameTimer += addToTime;
            }
        }
    }
}
