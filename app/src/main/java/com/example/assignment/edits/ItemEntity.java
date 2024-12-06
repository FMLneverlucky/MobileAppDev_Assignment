package com.example.assignment.edits;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.example.assignment.R;
import com.example.assignment.mgp2d.core.GameActivity;
import com.example.assignment.mgp2d.core.GameEntity;

public class ItemEntity extends GameEntity {
    private final Bitmap Sprite; //final keyword used when  data member assigned only at one place -> this case: in constructor
    //_position x and y can be accessed from parent class because this is child of gameEntity
    public ItemEntity(){
        _position.x = (float)GameActivity.instance.getResources().getDisplayMetrics().widthPixels / 2;
        _position.y = (float)GameActivity.instance.getResources().getDisplayMetrics().heightPixels /2;
        Bitmap PaperBitmap = BitmapFactory.decodeResource(GameActivity.instance.getResources(), R.drawable.paperball);
        Sprite = Bitmap.createScaledBitmap(PaperBitmap, PaperBitmap.getWidth() /2, PaperBitmap.getHeight() /2, true); //halved size of sprite here
    }

    @Override
    public void onUpdate() {
    }

    @Override
    public void onRender(Canvas canvas){
        canvas.drawBitmap(Sprite, _position.x, _position.y, null);
    }
}
