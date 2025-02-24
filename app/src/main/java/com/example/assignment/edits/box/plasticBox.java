package com.example.assignment.edits.box;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.assignment.mgp2d.core.GameActivity;
import com.example.assignment.mgp2d.core.Vector2;

public class plasticBox extends baseBox {
    @Override
    public String box_getType() {
        return "plastic";
    }

    private final Bitmap boxPlastic;

    public plasticBox(){
        boxPlastic = getBinSprite().copy(Bitmap.Config.ARGB_8888, false);
        //shift sprite to desired location: left mid
        setPosition(new Vector2(0, (float)GameActivity.instance.getResources().getDisplayMetrics().heightPixels/2));
        dstRect.left = (int)getPosition().x - boxPlastic.getWidth()/2;
        dstRect.top = (int) getPosition().y - boxPlastic.getHeight()/2;
        dstRect.right = (int)getPosition().x + boxPlastic.getWidth()/2;
        dstRect.bottom = (int) getPosition().y + boxPlastic.getHeight()/2;

    }

    @Override
    public void onUpdate(float dt) {

    }

    @Override
    public void onRender(Canvas canvas) {
        super.onRender(canvas);
        canvas.save();  //want to save initial orientation and position of bitmap before edit
        canvas.rotate(90, getPosition().x, getPosition().y);
        //want to show lesser of bin because screen width too small
        //major important note: when canvas is rotated, x and y axis is also rotated; YES LITERALLY THE ENTIRE ORIENTATION IS ROTATED; so yeah, translate added value to y because y is now inverse x axis and when not rotated, canvas shifted downwards from center
        canvas.translate(0, (float)dstRect.top/4);
        canvas.drawBitmap(boxPlastic, dstRect.left, dstRect.top, null);
        canvas.drawText("plastic", dstRect.left + label.getTextSize(), dstRect.top + label.getTextSize(), label);
        canvas.restore();   //return bitmap to original state so values wont be fucked for next rendering object
    }
}
