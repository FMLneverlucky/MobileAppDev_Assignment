package com.example.assignment.edits;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.assignment.mgp2d.core.GameActivity;
import com.example.assignment.mgp2d.core.GameEntity;

import com.example.assignment.mgp2d.core.Vector2;

public class toMenuButton extends GameEntity{

    public toMenuButton()
    {
        setPosition(new Vector2((float) GameActivity.instance.getResources().getDisplayMetrics().widthPixels /2, (float) GameActivity.instance.getResources().getDisplayMetrics().widthPixels / 0.75f));
    }
    @Override
    public void onUpdate(float dt) {
        if (GameActivity.instance.checkTapCollision(dstRect))
        {
            //exit to menu
            GameActivity.instance.finish();
        }
    }

    @Override
    public void onRender(Canvas canvas) {
        Paint MenuButtonBackground = new Paint();
        MenuButtonBackground.setColor(Color.parseColor("#453a43"));
        dstRect.left = (int) (getPosition().x - 75);
        dstRect.top = (int) (getPosition().y - 50);
        dstRect.right = (int) (getPosition().x + 75);
        dstRect.bottom = (int) (getPosition().y + 50);

        Paint menuButtonText = new Paint();
        menuButtonText.setColor(Color.parseColor("#000000"));
        menuButtonText.setTextAlign(Paint.Align.CENTER);

        canvas.drawRect(dstRect, MenuButtonBackground);
        canvas.drawText("To Menu", 0, 0, menuButtonText);
    }
}
