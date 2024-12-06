package com.example.assignment.edits;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.assignment.R;
import com.example.assignment.mgp2d.core.GameActivity;
import com.example.assignment.mgp2d.core.GameEntity;

public class ItemEntity extends GameEntity {
    private final Bitmap Sprite; //final keyword used when  data member assigned only at one place -> this case: in constructor
    //_position x and y can be accessed from parent class because this is child of gameEntity
    private final Rect _srcRect;    //source rectangle -> covers the relevant area in sprite image (in context of animated sprite)
    private final Rect _dstRect;    //destination rectangle -> rectangle that reside in screen
    public ItemEntity(){
        _position.x = (float)GameActivity.instance.getResources().getDisplayMetrics().widthPixels / 2;
        _position.y = (float)GameActivity.instance.getResources().getDisplayMetrics().heightPixels /2;
        Bitmap PaperBitmap = BitmapFactory.decodeResource(GameActivity.instance.getResources(), R.drawable.paperball);
        Sprite = Bitmap.createScaledBitmap(PaperBitmap, PaperBitmap.getWidth() /2, PaperBitmap.getHeight() /2, true); //halved size of sprite here
        _srcRect = new Rect(0, 0, PaperBitmap.getWidth() /2, PaperBitmap.getHeight() /2); //rmb to make
        _dstRect = new Rect();
    }

    @Override
    public void onUpdate() {
    }

    @Override
    public void onRender(Canvas canvas){
        //canvas.drawBitmap(Sprite, _position.x, _position.y, null);
        //initial sprite pivot point is located at top left of image -> want to shift the rectangle containing image until pivot is centered
        _dstRect.left = (int)_position.x - Sprite.getWidth() /2;
        _dstRect.top = (int)_position.y - Sprite.getHeight() /2;
        _dstRect.right = (int)_position.x + Sprite.getWidth() /2;
        _dstRect.bottom = (int)_position.y + Sprite.getHeight()/2;
        canvas.drawBitmap(Sprite, _srcRect, _dstRect, null);
    }
}
