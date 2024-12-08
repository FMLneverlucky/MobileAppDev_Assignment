package com.example.assignment.edits;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import com.example.assignment.R;
import com.example.assignment.mgp2d.core.GameActivity;
import com.example.assignment.mgp2d.core.GameEntity;
import com.example.assignment.mgp2d.core.Vector2;

public class ItemEntity extends GameEntity{
    private final Bitmap Sprite; //final keyword used when  data member assigned only at one place -> this case: in constructor
    //_position x and y can be accessed from parent class because this is child of gameEntity
    private final Rect _srcRect;    //source rectangle -> covers the relevant area in sprite image (in context of animated sprite)
    private final Rect _dstRect;    //destination rectangle -> rectangle that reside in screen
    private Vector2 pointerFlickDirection = new Vector2(0, 0);

    private static final int speed = 50; //speed of position update

    private final Vector2 startPosition = new Vector2((float)GameActivity.instance.getResources().getDisplayMetrics().widthPixels / 2, (float)GameActivity.instance.getResources().getDisplayMetrics().heightPixels /2); //will always start and reset at same place
    public ItemEntity(){
        //spawn position
        _position = startPosition;
        //init sprites for item and pointer
        Bitmap PaperBitmap = BitmapFactory.decodeResource(GameActivity.instance.getResources(), R.drawable.paperball);
        setSize(new Vector2((float)PaperBitmap.getWidth() /2, (float)PaperBitmap.getHeight() /2));
        Sprite = Bitmap.createScaledBitmap(PaperBitmap, (int)getSize().x, (int)getSize().y, true); //halved size of sprite here

        //below this comment is meant for animated sprite bits (which imm not using ahaaaaaa)
        _srcRect = new Rect(0, 0, (int)getSize().x, (int)getSize().y); //rmb to reduce image size to desired scale here too
        _dstRect = new Rect();
    }

    @Override
    public void onUpdate(float dt) {
        //update position
        //pain since want to move in axis but its vector 2 -> to implement normalized direction to check which axis to move
        //phone display starts at top left corner; increase value in right and down direction
        //init midpoint of screen to check which ddirection to move -> actually can use start direction since item spawn at middle
        if (pointerFlickDirection.x > startPosition.x)
            _position.x -= speed * dt;
        else if (pointerFlickDirection.x < startPosition.x)
            _position.x += speed * dt;

        if (pointerFlickDirection.y > startPosition.y)
            _position.y -= speed * dt;
        else if (pointerFlickDirection.y < startPosition.y)
            _position.y += speed * dt;
        //something about position and vector having different point of origin -> one is top left one is bottom right?? anyways problem here if move in inverse direction
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

    @Override
    public String getEntityClass() {
        return "ItemEntity";
    }
    public void receiveFlickDirection(Vector2 directionVector){
        pointerFlickDirection = directionVector;
        Log.d("RECEIVED", "flick direction received");  //debugging dont mind me
    }
}
