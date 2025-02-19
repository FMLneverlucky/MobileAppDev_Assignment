package com.example.assignment.edits;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.example.assignment.R;
import com.example.assignment.edits.box.baseBox;
import com.example.assignment.edits.box.paperBox;
import com.example.assignment.edits.box.plasticBox;
import com.example.assignment.mgp2d.core.GameActivity;
import com.example.assignment.mgp2d.core.GameEntity;
import com.example.assignment.mgp2d.core.GameScene;

import java.util.Vector;

public class AppGameScene extends GameScene {
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
        _gameEntities.add(new paperBox());
        _gameEntities.add(new plasticBox());
    }
    @Override
    public void onUpdate(float dt) {
        GameActivity.instance.pointerUpdate();
        for (GameEntity entity :_gameEntities) {
            //toss flick direction to item update for position update
            /*
            if (entity.getEntityClass().equals("ItemEntity"))    //if entity list is current an item entity
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
    }
}
