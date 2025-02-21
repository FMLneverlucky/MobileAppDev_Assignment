package com.example.assignment.edits.box;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.assignment.mgp2d.core.GameActivity;
import com.example.assignment.mgp2d.core.Vector2;

public class glassBox extends baseBox {
    private final Bitmap boxGlass;

    public glassBox(){
        boxGlass = getBinSprite().copy(Bitmap.Config.ARGB_8888, false);
        //shift sprite to desired location: right mid
        setPosition(new Vector2((float) GameActivity.instance.getResources().getDisplayMetrics().widthPixels, (float)GameActivity.instance.getResources().getDisplayMetrics().heightPixels/2));
        dstRect.left = (int)getPosition().x - boxGlass.getWidth()/2;
        dstRect.top = (int) getPosition().y - boxGlass.getHeight()/2;
        dstRect.right = (int)getPosition().x + boxGlass.getWidth()/2;
        dstRect.bottom = (int) getPosition().y + boxGlass.getHeight()/2;
    }

    @Override
    public void onUpdate(float dt) {
    }

    @Override
    public void onRender(Canvas canvas) {
        super.onRender(canvas);
        canvas.save();
        canvas.rotate(-90, getPosition().x, getPosition().y);
        canvas.translate(0, (float)dstRect.top/4);
        canvas.drawBitmap(boxGlass, dstRect.left, dstRect.top, null);
        canvas.drawText("glass", dstRect.left + label.getTextSize(), dstRect.top + label.getTextSize(), label);
        canvas.restore();
    }
}
