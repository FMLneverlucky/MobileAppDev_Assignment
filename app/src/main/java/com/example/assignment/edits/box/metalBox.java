package com.example.assignment.edits.box;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.assignment.mgp2d.core.GameActivity;
import com.example.assignment.mgp2d.core.Vector2;

public class metalBox extends baseBox {
    private final Bitmap boxMetal;

    public metalBox(){
        boxMetal = getBinSprite().copy(Bitmap.Config.ARGB_8888, false);
        //shift sprite to desired location: top mid
        setPosition(new Vector2((float) GameActivity.instance.getResources().getDisplayMetrics().widthPixels/2, 0));
        dstRect.left = (int)getPosition().x - boxMetal.getWidth()/2;
        dstRect.top = (int) getPosition().y - boxMetal.getHeight()/2;
        dstRect.right = (int)getPosition().x + boxMetal.getWidth()/2;
        dstRect.bottom = (int) getPosition().y + boxMetal.getHeight()/2;
        setEntityType("metal");
    }

    @Override
    public void onUpdate(float dt) {
    }

    @Override
    public void onRender(Canvas canvas) {
        super.onRender(canvas);
        canvas.save();
        canvas.rotate(180, getPosition().x, getPosition().y);
        canvas.drawBitmap(boxMetal, dstRect.left, dstRect.top, null);
        canvas.drawText("metal", dstRect.left + label.getTextSize(), dstRect.top + label.getTextSize(), label);
        canvas.restore();
    }
}
