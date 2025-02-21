package com.example.assignment.edits;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.assignment.R;
import com.example.assignment.mgp2d.core.GameActivity;
import com.example.assignment.mgp2d.core.GameEntity;
import com.example.assignment.mgp2d.core.Vector2;

public class pauseButton extends GameEntity {

    private static Bitmap pauseButtonBitmap = null;
    public pauseButton()
    {
        Bitmap pauseBitmap = BitmapFactory.decodeResource(GameActivity.instance.getResources(), R.drawable.pause);
        pauseButtonBitmap = Bitmap.createScaledBitmap(pauseBitmap, pauseBitmap.getWidth()/4, pauseBitmap.getHeight()/4,true);
        setPosition(new Vector2(100, 175));
        dstRect.left = (int)getPosition().x - pauseButtonBitmap.getWidth()/2;
        dstRect.top = (int)getPosition().y - pauseButtonBitmap.getWidth()/2;
        dstRect.right = (int)getPosition().x + pauseButtonBitmap.getWidth()/2;
        dstRect.bottom = (int)getPosition().y + pauseButtonBitmap.getHeight()/2;
    }

    @Override
    public void onUpdate(float dt) {

        if (GameActivity.instance.checkTapCollision(dstRect) && !pauseDialogue.isShowing())
        {
            //GameActivity.instance.finish(); //exit out of mgp2d using finish(); stop/pause game
            pauseDialogue pauseDialog = new pauseDialogue();
            pauseDialog.show(GameActivity.instance.getSupportFragmentManager(), "Back dialog");
        }
    }

    @Override
    public void onRender(Canvas canvas) {
        canvas.drawRect(dstRect, new Paint());
        canvas.drawBitmap(pauseButtonBitmap, dstRect.left, dstRect.top, null);
    }

}
