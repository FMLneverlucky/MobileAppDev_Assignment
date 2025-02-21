
// README
/*
################ Introduction ################

MGP2D is a very simple and light-weight game development framework for Android studio.

MGP2D "Core" is designed to have a small API footprint. It is only opinionated in managing the
lifecycle of "GameScenes", but is non-opinionated in how resources should be managed (e.g. player
input, loading bitmap and audio resources, getting display metrics, etc). This should leave little
room for errors on the part of the framework developers. Hence, instead of "learning the framework"
(or "blaming the framework"), Android Studio's official documentation is still the go-to resource
for help and support.

Given the light-weight and non-opinionated nature of MGP2D, it should be CLOSED FROM MODIFICATION!
This is to avoid the chance of introducing unnecessary breaking changes.

################ Architecture ################

"GameActivity" hosts a single SurfaceView and worker thread for frame renders and updates. On every
frame, GameActivity will call the update(deltaTime) and render() methods of the current active
GameScene. In turn, the current GameScene can call the onUpdate(deltaTime) and onRender() methods
of the GameEntity objects kept by it.

All GameScene child classes contain a list of overridable lifecycle methods, namely:
- onCreate()            <-- only ever gets triggered once in the scene's lifetime
- onEnter()             <-- triggered when we enter this scene. Runs after 'onCreate'!
- onUpdate(deltaTime)   <-- triggered on every frame update. Only for updating data
- onRender()            <-- triggered after frame update. Only for rendering visuals on screen
- onExit()              <-- triggered when we are changing to another scene.
- onDestroy()           <-- // TODO: To be implemented

All dynamic/static actors/objects in a GameScene is a GameEntity (e.g. background, player, etc).
The method or data structure used by the GameScene to store all GameEntities is up to developers.
Each GameEntity contains these methods:
- destroy()             <-- sets a flag that signals that an entity is ready to be destroyed.
- canDestroy()          <-- can be called by a GameScene to check if an entity should be removed from it's collection.
- onUpdate(deltaTime)   <-- triggered on every frame update. Only for updating data.
- onRender()            <-- triggered on every frame update. Only for rendering.
- getOrdinal()          <-- helper method to get _ordinal which you can use for sorting purposes.
                            You may set _ordinal when constructing the class. Since it is 0
                            by default, you do not need to use it if you prefer to use your own
                            strategy to sort game entities in your scenes instead.

################ Usage and API ################

== startActivity(new Intent(this, GameActivity.class)); // enter GameActivity from any other Activity
== GameScene.enter(ExampleGameScene.class);             // switch to another GameScene any time
== GameActivity.instance.finish();                      // "stop/pause" the game any time by exiting GameActivity
== GameActivity.instance.getResources();                // helpful for getting resource-related methods like loadBitmap(), getDisplayMetrics(), etc
== GameActivity.instance.setTimeScale(float timeScale); // sets scale at which time passes (slow motion / speed up). Set to 0 to pause game
== GameActivity.instance.getMotionEvent();              // getting the last motion event retrieved from onTouchEvent()

 */

package com.example.assignment.mgp2d.core;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.example.assignment.edits.BackDialogue;

public class GameActivity extends FragmentActivity {

    private static class UpdateThread extends Thread {
        public boolean _isRunning = true;
        public void terminate() { _isRunning = false; }
        public boolean isRunning() { return _isRunning; }
        private final SurfaceHolder _surfaceHolder;

        public UpdateThread(SurfaceView surfaceView) {
            _surfaceHolder = surfaceView.getHolder();
            _surfaceHolder.addCallback(new SurfaceHolder.Callback() {
                @Override public void surfaceCreated(@NonNull SurfaceHolder holder) { if (!isAlive()) start(); }
                @Override public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {}
                @Override public void surfaceDestroyed(@NonNull SurfaceHolder holder) { terminate(); }
            });
        }

        @Override
        public void run()
        {
            long prevTime = System.nanoTime();
            _isRunning = true;

            while(_isRunning) {
                // Calculating deltaTime
                long currentTime = System.nanoTime();
                float deltaTime = (currentTime-prevTime)/1000000000.0f;
                prevTime = currentTime;

                if (GameScene.getNext() != GameScene.getCurrent())
                    GameScene.enter(GameScene.getNext());

                if (GameScene.getCurrent() == null)
                    continue;

                // Update current game scene
                GameScene.getCurrent().onUpdate(deltaTime * _timeScale);

                // Render current game scene
                Canvas canvas = _surfaceHolder.lockCanvas(null);
                if (canvas != null) {
                    synchronized (_surfaceHolder) {
                        canvas.drawColor(Color.BLACK); // reset canvas
                        if (GameScene.getCurrent() != null)
                            GameScene.getCurrent().onRender(canvas);
                    }
                    _surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }

    public static GameActivity instance = null;

    private static float _timeScale = 1;
    public void setTimeScale(float timeScale) { _timeScale = timeScale; }

    private static MotionEvent _motionEvent = null;
    public MotionEvent getMotionEvent() { return _motionEvent; }

    private UpdateThread _updateThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        SurfaceView surfaceView = new SurfaceView(this);
        setContentView(surfaceView);
        _updateThread = new UpdateThread(surfaceView);
        currentDirection = flickDirection.NONE;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        _motionEvent = event;
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!_updateThread.isRunning())
            _updateThread.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        _updateThread.terminate();
        GameScene.exitCurrent();
    }

    @Override
    protected void onPause() {
        super.onPause();
        _updateThread.terminate();
        GameScene.exitCurrent();
    }

//---------------------------pointer stuff----------------------------------------------------------
    protected int _currentPointerID = -1; //start detecting touch when enter a scene, every scene should have a touch detector -> i mean there's event listeners but since this exists might as well use it
    //pointers is all part of motionEvent class, which is a singleton. having pointer count here means theres no need to make multiple pointer counters in different scenes

    //in case need reference from other class, making a pointer count getter here
    public int getPointerCount() {return _currentPointerID;}
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
    public void pointerUpdate()
    {
        //to be called in every scene update to track touch detection, put it here because abstract and i dont want to mess up working codes as much as possible
        MotionEvent motionEvent = getMotionEvent();

        if (motionEvent == null) return;

        int action = motionEvent.getActionMasked();
        int actionIndex = motionEvent.getActionIndex();
        int pointerID = motionEvent.getPointerId(actionIndex);  //this bit seems a bit redundant in some way not its in game activity, but i dont dare touch ' -'

        //i rly want to shift this entire bit into another class to act as a singleton for entire app but can only include 1 class ugh -> i totally didnt forget this is an abstract class, where is app run again O|<
        //ahahhahahaha turns out there isnt rly another class encapsulating the entire thing to run as an app (well i guess thats android manifest but its xml file :/). i guess i will need to stick to having a singleton controller running within game activity instead.
        //game activity is a public class so technically, i could just toss this entire bit into that class and call it from children of game scene

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

    //am small braining rn, but since move pointer code to activity, item entity need to change how to set flick direction
    public void set_flickDirection(flickDirection newDirection)
    {
        currentDirection = newDirection;
    }

    public boolean checkTapCollision(Rect rect1)
    {
        MotionEvent motionEvent = getMotionEvent();
        if (_currentPointerID != -1)
            return rect1.contains((int)motionEvent.getX(), (int)motionEvent.getY());
        //cant use initial
        return false;
    }
    //----------------------------end of pointer stuff--------------------------------------------------

//--------------------------------------double tap bs-----------------------------------------------
    protected boolean isTimerRunning = false; //flag to start/stop timer running between each tap
    protected boolean isChainTap = false; //to identify if players action is connected
    protected float chainTimer = 0; //timer that runs between taps
    protected static final float timeBetweenTaps = 0; //constant value to reset timer
    protected int numOfTaps = 0;
    protected boolean chainTapAllowed = true;
    protected void runTimer(float dt)
    {
        if (chainTimer > 0 && isTimerRunning)
            chainTimer -= dt;
        else if (chainTimer < 0)
            //stop timer when overshoot duration to be considered chain tap
            isTimerRunning = false;
    }
//-------------------------------------------double tap end-----------------------------------------

}
