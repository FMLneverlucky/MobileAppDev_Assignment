package com.example.assignment.mgp2d.core;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;

import com.example.assignment.R;

import java.util.HashMap;

public abstract class GameScene {

    // region Static props for managing multiple scenes

    private static GameScene _current = null;

    private static GameScene _next = null;

    public static GameScene getCurrent() { return _current; }

    public static GameScene getNext() { return _next; }

    private static final HashMap<Class<?>, GameScene> map = new HashMap<>();

    public static void enter(Class<?> gameSceneClass) {
        if (!map.containsKey(gameSceneClass)) {
            try {
                map.put(gameSceneClass, (GameScene) gameSceneClass.newInstance());
            } catch (IllegalAccessException | InstantiationException e) {
                throw new RuntimeException(e);
            }
        }
        _next = map.get(gameSceneClass);
    }

    public static void enter(GameScene gameScene) {
        if (_current != null) _current.onExit();
        _current = gameScene;
        if (_current == null) return;
        _current.onEnter();
    }

    public static void exitCurrent() {
        if (_current == null) return;
        _current.onExit();
        _current = null;

    }

    // endregion

    // region Props for handling internal behaviour of game scene

    private boolean _isCreated = false;
    protected int _currentPointerID = -1; //start detecting touch when enter a scene, every scene should have a touch detector -> i mean there's event listeners but since this exists might as well use it
    private Vector2 initial_pointerPosition = new Vector2(0, 0);    //touch detect
    private Vector2 final_pointerPosition = new Vector2(0, 0);  //touch release
    public enum flickDirection{
        NONE,
        LEFT,
        RIGHT,
        UP,
        DOWN
    }
    public flickDirection currentDirection; //to store pointer flick direction
    private Bitmap pointerBmp;
    public void onCreate() {
        _isCreated = true;
        Bitmap PointerBitmap = BitmapFactory.decodeResource(GameActivity.instance.getResources(), R.drawable.circle);
        pointerBmp = Bitmap.createScaledBitmap(PointerBitmap, PointerBitmap.getWidth(), PointerBitmap.getHeight(), true);
        currentDirection = flickDirection.NONE;
    }
    public void onEnter() { if (!_isCreated) onCreate(); }
    public abstract void onUpdate(float dt);
    public abstract void onRender(Canvas canvas);
    public void onExit() {}

    public void pointerUpdate()
    {
        //to be called in every scene update to track touch detection, put it here because abstract and i dont want to mess up working codes as much as possible
        MotionEvent motionEvent = GameActivity.instance.getMotionEvent();

        if (motionEvent == null) return;

        int action = motionEvent.getActionMasked();
        int actionIndex = motionEvent.getActionIndex();
        int pointerID = motionEvent.getPointerId(actionIndex);

        if (_currentPointerID == -1 && (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_POINTER_DOWN))
        {
            _currentPointerID = pointerID;   //touch has been detected, tie touch reference in index to start of touch detection list
            initial_pointerPosition.x =  motionEvent.getX();
            initial_pointerPosition.y = motionEvent.getY();
        }
        else if (_currentPointerID == pointerID && (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_POINTER_UP))
        {
            final_pointerPosition.x =  motionEvent.getX();
            final_pointerPosition.y = motionEvent.getY();
            _currentPointerID = -1;  //touch release or no multiple touch detected, reset pointer reference
            determineFlickDirection(); //pass it somewhere idk, item will need to update position
        }
        if (_currentPointerID != -1)
        {
            for (int i = 0; i < motionEvent.getPointerCount(); i++)
            {
                if (motionEvent.getPointerId(i) != _currentPointerID) continue;
                //else {
                    //ooga booga what do when theres a touch -> maybe handle touch events here using timer; how long user hold tap/ how long until next tap
                //}
            }
        }

    }

    public void renderPointer(Canvas canvas)    //just a heads up, might cause pointer to render on separate canvas
    {
        //render pointer image if there is touch detected
        if (_currentPointerID != -1)
            canvas.drawBitmap(pointerBmp,0,0,null);

        //i could technically use interface and implement for this pointer thing, but i never actually made a working one before and i dont dare kek
    }

    public void determineFlickDirection()
    {
        Vector2 pointerPositionChange = final_pointerPosition.subtract(initial_pointerPosition);
        if (Math.abs(pointerPositionChange.x) > Math.abs(pointerPositionChange.y))  //using absolute so value compared will always be positive to determine more accurately which way player swiped/flicked
        {
            //flick left or right
            //if pointer start at left and release on right, x of position change will be negative
            if (pointerPositionChange.x > 0)
                currentDirection = flickDirection.RIGHT;
            else if (pointerPositionChange.x < 0)
                currentDirection = flickDirection.LEFT;
        }
        else if (Math.abs(pointerPositionChange.y) > Math.abs(pointerPositionChange.x))
        {
            //flick up or down
            //if pointer starts above and release below, y of position change will be negative
            if (pointerPositionChange.y < 0)
                currentDirection = flickDirection.UP;
            else if (pointerPositionChange.y > 0)
                currentDirection = flickDirection.DOWN;
        }
    }

    public flickDirection getFlickDirection() {return currentDirection;}
    //endregion

    public void reset_flickDirection()
    {
        currentDirection = flickDirection.NONE;
    }
}
