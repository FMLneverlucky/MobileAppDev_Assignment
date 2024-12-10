package com.example.assignment.edits;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import com.example.assignment.R;
import com.example.assignment.mgp2d.core.GameActivity;
import com.example.assignment.mgp2d.core.GameEntity;
import com.example.assignment.mgp2d.core.GameScene;
import com.example.assignment.mgp2d.core.Vector2;

public class ItemEntity extends GameEntity{
    private final Bitmap Sprite; //final keyword used when  data member assigned only at one place -> this case: in constructor
    //_position x and y can be accessed from parent class because this is child of gameEntity
    private final Rect _srcRect;    //source rectangle -> covers the relevant area in sprite image (in context of animated sprite)
    private final Rect _dstRect;    //destination rectangle -> rectangle that reside in screen
    private GameScene.flickDirection pointerFlickDirection = GameScene.flickDirection.NONE;  //i cant exactly call a game scene protected variable here, so jus switch case the enum passed in as a diff data type -> changed function to public since its a pain in the ass, also going to be repetitive to keep declaring different methods in every class (context: was a string type before change)

    private static final int speed = 200; //speed of position update

    private final Vector2 startPosition = new Vector2((float)GameActivity.instance.getResources().getDisplayMetrics().widthPixels / 2, (float)GameActivity.instance.getResources().getDisplayMetrics().heightPixels /2); //will always start and reset at same place -> i would static this if i could but its only avaliable for int variables smh, also this fucking piece of shit is somehow updating somewhere im not aware of
    public ItemEntity(){
        //spawn position
        setPosition(startPosition);
        //init sprite for item
        Bitmap PaperBitmap = BitmapFactory.decodeResource(GameActivity.instance.getResources(), R.drawable.paperball);
        //want to set to half size of image, so setting size after init image
        setSize(new Vector2((float)PaperBitmap.getWidth() /2, (float)PaperBitmap.getHeight() /2));
        Sprite = Bitmap.createScaledBitmap(PaperBitmap, (int)getSize().x, (int)getSize().y, true); //halved size of sprite here

        //below this comment is meant for animated sprite bits (which imm not using ahaaaaaa)
        _srcRect = new Rect(0, 0, (int)getSize().x, (int)getSize().y); //rmb to reduce image size to desired scale here too
        _dstRect = new Rect();
    }

    @Override
    public void onUpdate(float dt) {
        //reset position of item if it reaches any screen boundary
        if (screenBoundaryCollision())
            _position = new Vector2(540, 1088.5f); //i tried to set position back to start position but that fucking variable is receiving position update from god knows where. i seriously have no idea where so im doing the illegal

        //update position
        //pain since want to move in axis but its vector 2 -> to implement normalized direction to check which axis to move
        //phone display starts at top left corner; increase value in right and down direction
        //init midpoint of screen to check which ddirection to move -> actually can use start direction since item spawn at middle
        //TODO: CHANGE POINTERFLICKDIRECTION TO ENUM (also consider using switch case here)
        /*
        if (pointerFlickDirection.x > (float)GameActivity.instance.getResources().getDisplayMetrics().widthPixels / 2)
            _position.x -= speed * dt;
        else if (pointerFlickDirection.x < (float)GameActivity.instance.getResources().getDisplayMetrics().widthPixels / 2)
            _position.x += speed * dt;

        if (pointerFlickDirection.y > (float)GameActivity.instance.getResources().getDisplayMetrics().heightPixels / 2)
            _position.y -= speed * dt;
        else if (pointerFlickDirection.y < (float)GameActivity.instance.getResources().getDisplayMetrics().heightPixels / 2)
            _position.y += speed * dt;
         */

        switch (pointerFlickDirection)
        {
            case LEFT: //none
                _position.x -= speed * dt;
                return;
            case RIGHT:
                _position.x += speed * dt;
                return;
            case UP:
                _position.y -= speed * dt;
                return;
            case DOWN:
                _position.y += speed * dt;
                return;
            default:

        }
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
    public void receiveFlickDirection(GameScene.flickDirection direction){ //to be changed: vector 2 to enum
        pointerFlickDirection = direction;
        Log.d("RECEIVED", "flick direction received");  //debugging dont mind me
    }

    protected boolean screenBoundaryCollision()
    {
        //returns false by default unless item reaches screen boundary
        //left boundary
        if (_dstRect.left < 0)  //since already changed image pivot to be centered when first init, might as well maximize usage
            return true;
        //right boundary
        if (_dstRect.right > GameActivity.instance.getResources().getDisplayMetrics().widthPixels)
            return true;
        //top boundary
        if (_dstRect.top < 0)
            return true;
        //bottom boundary
        if (_dstRect.bottom > GameActivity.instance.getResources().getDisplayMetrics().heightPixels)
            return true;

        //am not using if else since need to check for all sides of screen (SO GO AWAY YELLOW SQUIGGLE)

        //default
        return false;
    }
}
