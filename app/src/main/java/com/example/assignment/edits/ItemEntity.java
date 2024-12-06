package com.example.assignment.edits;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.example.assignment.R;
import com.example.assignment.mgp2d.core.GameActivity;
import com.example.assignment.mgp2d.core.GameEntity;
import com.example.assignment.mgp2d.core.Vector2;

public class ItemEntity extends GameEntity {
    private final Bitmap Sprite; //final keyword used when  data member assigned only at one place -> this case: in constructor
    //_position x and y can be accessed from parent class because this is child of gameEntity
    private final Rect _srcRect;    //source rectangle -> covers the relevant area in sprite image (in context of animated sprite)
    private final Rect _dstRect;    //destination rectangle -> rectangle that reside in screen

    //below this comment are members for pointer
    private int currentPointerID;
    public Vector2 position;
    private final Bitmap TempPointer;

    public ItemEntity(){
        //spawn position
        _position.x = (float)GameActivity.instance.getResources().getDisplayMetrics().widthPixels / 2;
        _position.y = (float)GameActivity.instance.getResources().getDisplayMetrics().heightPixels /2;

        //init sprites for item and pointer
        Bitmap PaperBitmap = BitmapFactory.decodeResource(GameActivity.instance.getResources(), R.drawable.paperball);
        Sprite = Bitmap.createScaledBitmap(PaperBitmap, PaperBitmap.getWidth() /2, PaperBitmap.getHeight() /2, true); //halved size of sprite here
        Bitmap PointerBitmap = BitmapFactory.decodeResource(GameActivity.instance.getResources(), R.drawable.circle);
        TempPointer = Bitmap.createScaledBitmap(PointerBitmap, PointerBitmap.getWidth(), PointerBitmap.getHeight(), true);

        //below this comment is meant for animated sprite bits (which imm not using ahaaaaaa)
        _srcRect = new Rect(0, 0, PaperBitmap.getWidth() /2, PaperBitmap.getHeight() /2); //rmb to reduce image size to desired scale here too
        _dstRect = new Rect();

        //pointer stuff
        currentPointerID = -1;  //game start state, so set to -1 for no touch detected; i cant believe it took me an hour to figure this out O|<
        //create joystick bitmap and render???

    }

    @Override
    public void onUpdate() {
        PointerUpdate();
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

        //render pointer image if there is touch detected
        if (currentPointerID != -1)
            canvas.drawBitmap(TempPointer,0,0,null);

    }

    public void PointerUpdate()
    {
        MotionEvent motionEvent = GameActivity.instance.getMotionEvent();

        if (motionEvent == null) return;

        int action = motionEvent.getActionMasked();
        int actionIndex = motionEvent.getActionIndex();
        int pointerID = motionEvent.getPointerId(actionIndex);

        if (currentPointerID == -1 && (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_POINTER_DOWN))
        {
            currentPointerID = pointerID;   //touch has been detected, tie touch reference in index to start of touch detection list
        }
        else if (currentPointerID == pointerID && (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_POINTER_DOWN))
        {
            currentPointerID = -1;  //touch release or no multiple touch detected, reset pointer reference
        }

        if (currentPointerID != -1)
        {
            for (int i = 0; i < motionEvent.getPointerCount(); i++)
            {
                if (motionEvent.getPointerId(i) != currentPointerID) continue;
                else {
                    position.x = motionEvent.getPointerId(i);
                    position.y = motionEvent.getPointerId(i);
                }
            }
        }
    }
}
