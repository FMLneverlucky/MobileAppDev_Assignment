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

    private Bitmap pointerBmp;
    public void onCreate() {
        _isCreated = true;
        Bitmap PointerBitmap = BitmapFactory.decodeResource(GameActivity.instance.getResources(), R.drawable.circle);
        pointerBmp = Bitmap.createScaledBitmap(PointerBitmap, PointerBitmap.getWidth(), PointerBitmap.getHeight(), true);
    }
    public void onEnter() { if (!_isCreated) onCreate(); }
    public abstract void onUpdate(float dt);
    public abstract void onRender(Canvas canvas);
    public void onExit() {}

    public void renderPointer(Canvas canvas)    //just a heads up, might cause pointer to render on separate canvas
    {
        //get pointer count to check for touch
        int getPointerNum = GameActivity.instance.getPointerCount();
        //render pointer image if there is touch detected
        if (getPointerNum != -1)
            canvas.drawBitmap(pointerBmp,0,0,null);
        //i could technically use interface and implement for this pointer thing, but i never actually made a working one before and i dont dare kek
    }

    public abstract void scoreUpdate();
}
