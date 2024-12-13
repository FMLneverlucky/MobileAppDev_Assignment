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
    //_position x and y can be accessed from parent class because this is child of gameEntity                    j
    private final Rect _srcRect;    //source rectangle -> covers the relevant area in sprite image (in context of animated sprite)
    private final Rect _dstRect;    //destination rectangle -> rectangle that reside in screen
    private GameScene.flickDirection pointerFlickDirection = GameScene.flickDirection.NONE;  //i cant exactly call a game scene protected variable here, so jus switch case the enum passed in as a diff data type -> changed function to public since its a pain in the ass, also going to be repetitive to keep declaring different methods in every class (context: was a string type before change)
    private static final int speed = 400; //speed of position update

    private final Vector2 startPosition = new Vector2((float)GameActivity.instance.getResources().getDisplayMetrics().widthPixels / 2, (float)GameActivity.instance.getResources().getDisplayMetrics().heightPixels /2); //will always start and reset at same place -> i would static this if i could but its only avaliable for int variables smh, also this fucking piece of shit is somehow updating somewhere im not aware of
    public ItemEntity(){
        //spawn position
        setPosition(startPosition);
        //init sprite for item
        Bitmap PaperBitmap = BitmapFactory.decodeResource(GameActivity.instance.getResources(), R.drawable.paperball);  //replace with default sprite thats not paper ball to make sure this class manages to init child items; in other words, for debugging
        //want to set to half size of image, so setting size after init image
        setSize(new Vector2((float)PaperBitmap.getWidth() /2, (float)PaperBitmap.getHeight() /2));
        Sprite = Bitmap.createScaledBitmap(PaperBitmap, (int)getSize().x, (int)getSize().y, true); //halved size of sprite here

        //below this comment is meant for animated sprite bits (which imm not using ahaaaaaa)
        _srcRect = new Rect(0, 0, (int)getSize().x, (int)getSize().y); //rmb to reduce image size to desired scale here too
        _dstRect = new Rect();
    }

    @Override
    public void onUpdate(float dt) {
        //current bug: receive flick direction is set in main game scene every frame. this function is overwriting set to none direction when reset position in here.
        //possible fixes:
        //reset position of item if it reaches any screen boundary
        if (screenBoundaryCollision()) {
            _position = new Vector2((float) GameActivity.instance.getResources().getDisplayMetrics().widthPixels / 2, (float) GameActivity.instance.getResources().getDisplayMetrics().heightPixels / 2); //makiing it so it resets to center on different device
            //want to make it so that item can only move in one direction until reset -> reset item direction here
            pointerFlickDirection = GameScene.flickDirection.NONE;
            //reset game scene flick direction so when item reset position here, wont take in old direction
            GameScene.getCurrent().reset_flickDirection();
        }

        //update position
        //item should only move in one direction until reset -> only move when direction is not "none"
        if (pointerFlickDirection != GameScene.flickDirection.NONE)
        {
            switch (pointerFlickDirection) {
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
        }
        //something about position and vector having different point of origin -> one is top left one is bottom right?? anyways problem here if move in inverse direction
        else if (pointerFlickDirection == GameScene.flickDirection.NONE)
        {
            GameScene currentScene = GameScene.getCurrent();
            receiveFlickDirection(currentScene.getFlickDirection());
        }
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

    protected void get_ItemEnum(singleType.ID type)
    {
        //get item bitmap, size and other stuff
    }
}
