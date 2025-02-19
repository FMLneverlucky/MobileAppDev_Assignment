package com.example.assignment.edits;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.assignment.edits.itemTypes.defaultItem;
import com.example.assignment.edits.itemTypes.paperItem;
import com.example.assignment.mgp2d.core.GameActivity;
import com.example.assignment.mgp2d.core.GameEntity;
import com.example.assignment.mgp2d.core.Vector2;

import com.example.assignment.edits.itemTypes.ItemType;

import java.util.Random;

public class ItemEntity extends GameEntity{

    private final Rect _srcRect;    //source rectangle -> covers the relevant area in sprite image (in context of animated sprite)
    private final Rect _dstRect;    //destination rectangle -> rectangle that reside in screen
    private static final int speed = 400; //speed of position update
    private ItemType item = null;

    ItemType.ID[] numItems = ItemType.ID.values();
    Random random = new Random();
    private int randomType;

    public ItemEntity(){
        //spawn position
        setPosition(new Vector2((float)GameActivity.instance.getResources().getDisplayMetrics().widthPixels / 2, (float)GameActivity.instance.getResources().getDisplayMetrics().heightPixels /2));

        //below this comment is meant for animated sprite bits (which imm not using ahaaaaaa)
        _srcRect = new Rect(); //rmb to reduce image size to desired scale here too
        _dstRect = new Rect();
    }

    @Override
    public void onUpdate(float dt) {
        //current bug: receive flick direction is set in main game scene every frame. this function is overwriting set to none direction when reset position in here.
        //possible fixes:
        //reset position of item if it reaches any screen boundary
        if (screenBoundaryCollision()) {
            setPosition(new Vector2((float) GameActivity.instance.getResources().getDisplayMetrics().widthPixels / 2, (float) GameActivity.instance.getResources().getDisplayMetrics().heightPixels / 2)); //making it so it resets to center on different device
            //want to make it so that item can only move in one direction until reset -> reset item direction here
            //GameActivity.instance.set_flickDirection(GameActivity.flickDirection.NONE);
            //reset game scene flick direction so when item reset position here, wont take in old direction
            GameActivity.instance.reset_flickDirection();
            item = null;
        }

        //update position
        //item should only move in one direction until reset -> only move when direction is not "none"
        if (GameActivity.instance.getFlickDirection() != GameActivity.flickDirection.NONE)
        {
            switch (GameActivity.instance.getFlickDirection()) {
                case LEFT: //none
                    _position.x -= speed * dt;
                    return;
                case RIGHT:
                    _position.x += speed * dt;
                    return;
                case UP:
                    _position.y -= 2 * speed * dt;
                    return;
                case DOWN:
                    _position.y += 2 * speed * dt;
                    return;
                default:
            }
        }
        //something about position and vector having different point of origin -> one is top left one is bottom right?? anyways problem here if move in inverse direction
        else if (GameActivity.instance.getFlickDirection() == GameActivity.flickDirection.NONE)
        {
            receiveFlickDirection(GameActivity.instance.getFlickDirection());
        }



        if (item == null) {
            randomType = random.nextInt(numItems.length);

            switch (randomType) {
                case 1:
                    item = new paperItem();
                    break;
                default:
                    item = new defaultItem();
                    break;
            }
        }
    }

    @Override
    public void onRender(Canvas canvas){
        //shifted src rect here since size of all item sprites vary and needs to be constantly updated
        _srcRect.set(0, 0, (int)item.getBitmap().getWidth(), (int)item.getBitmap().getWidth());
        //initial sprite pivot point is located at top left of image -> want to shift the rectangle containing image until pivot is centered
        //note to self: since item will be receiving position updates, destRect needs to calculate new position every frame -> put here since function is ran every frame
        _dstRect.left = (int)getPosition().x - item.getBitmap().getWidth() /2;
        _dstRect.top = (int)getPosition().y - item.getBitmap().getHeight() /2;
        _dstRect.right = (int)getPosition().x + item.getBitmap().getWidth() /2;
        _dstRect.bottom = (int)getPosition().y + item.getBitmap().getHeight()/2;
        canvas.drawBitmap(item.getBitmap(), _srcRect, _dstRect, null);
    }

    @Override
    public String getEntityClass() {
        return "ItemEntity";
    }
    public void receiveFlickDirection(GameActivity.flickDirection direction){ //to be changed: vector 2 to enum
        GameActivity.instance.set_flickDirection(direction);  //am small braining and forgot why this exists
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
        return _dstRect.bottom > GameActivity.instance.getResources().getDisplayMetrics().heightPixels;

        //am not using if else since need to check for all sides of screen (SO GO AWAY YELLOW SQUIGGLE)

        //default
    }


}
