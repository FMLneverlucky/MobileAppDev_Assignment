package com.example.assignment.edits;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.example.assignment.R;
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
        _gameEntities.add(new TextRender(14117033, _currentPointerID));
    }
    @Override
    public void onUpdate(float dt) {
        pointerUpdate();
        for (GameEntity entity :_gameEntities) {
            //toss flick direction to item update for position update
            if (entity.getEntityClass().equals("ItemEntity"))
            {
                ((ItemEntity)entity).receiveFlickDirection(getFlickDirection());    //looks scuffed, taken from stack overflow O|<
            }
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
