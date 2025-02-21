package com.example.assignment.edits;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.example.assignment.mgp2d.core.GameActivity;
import com.example.assignment.mgp2d.core.GameEntity;
import com.example.assignment.mgp2d.core.Vector2;

public class TextRender extends GameEntity {
    private Paint text;
    private final int screenWidth, screenHeight;
    private int pointerAction;
    public TextRender(int color, int pAction) {
        text = new Paint();
        text.setColor(color);
        text.setTextSize(75);
        text.setTextAlign(Paint.Align.RIGHT);
        screenWidth = GameActivity.instance.getResources().getDisplayMetrics().widthPixels;
        screenHeight = GameActivity.instance.getResources().getDisplayMetrics().heightPixels;
        pointerAction = pAction;
    }
    @Override
    public void onUpdate(float dt) {
        MotionEvent motionChecker = GameActivity.instance.getMotionEvent();

        if (motionChecker == null) return;
        //pointer here to get action to render onto screen, for debugging purposes
        if (pointerAction != -1 && (motionChecker.getActionMasked() == MotionEvent.ACTION_UP || motionChecker.getActionMasked() == MotionEvent.ACTION_POINTER_UP))
            pointerAction = -1; //reset whenever lift finger
        else //always track pointer action (no touch already handled to step out earlier)
            pointerAction = GameActivity.instance.getMotionEvent().getActionMasked();
        //pretty sure this is redundant since theres exact same code in game scene, but leaving it just in case theres dependency i havent realised yet
    }

    @Override
    public void onRender(Canvas canvas) {
        canvas.drawText(actionToString(pointerAction), (float)screenWidth /4, screenHeight, text);
        if (GameActivity.instance.getMotionEvent() != null) {   //render pointer position only if touch is detected
            Vector2 pointerPosition = new Vector2(GameActivity.instance.getMotionEvent().getX(), GameActivity.instance.getMotionEvent().getY());
            String pointerPosX = "" + pointerPosition.x;
            String pointerPosY = "" + pointerPosition.y;
            canvas.drawText(pointerPosX + " " + pointerPosY, (float) screenWidth / 2, (float) screenHeight / 2, text);
        }
    }

    public String actionToString(int action)
    {
        switch (action)
        {
            case 0:
                return "down";
            case 1:
                return "move";
            case 2:
                return "pointer down";
            case 3:
                return "cancel";
            case 4:
                return "outside/masked";
            case 5:
                return "non primary pointer down";
            case 6:
                return "non primary pointer up";
            default:
                return "nothing";
        }
    }
}
