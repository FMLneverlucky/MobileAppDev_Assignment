package com.example.assignment.edits.box;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.assignment.mgp2d.core.GameActivity;
import com.example.assignment.mgp2d.core.Vector2;

public class plasticBox extends baseBox {
    private final Bitmap boxPlastic;

    public plasticBox(){
        boxPlastic = getBinSprite().copy(Bitmap.Config.ARGB_8888, false);
        //shift sprite to desired location: left mid
        setPosition(new Vector2(0, (float)-GameActivity.instance.getResources().getDisplayMetrics().heightPixels));
    }

    @Override
    public void onUpdate(float dt) {

    }

    @Override
    public void onRender(Canvas canvas) {
        super.onRender(canvas);
        //render sprite at set position
        canvas.translate(getPosition().x, getPosition().y);
        //shift sprite so center is on assigned position
        //canvas.translate((float)-boxPlastic.getWidth()/2, (float)-boxPlastic.getHeight()/2);
        //since canvas mid point is on position now, just use position value
        //canvas.rotate(15, getPosition().x, getPosition().y);
        //canvas .scale(10,10);
        canvas.drawBitmap(boxPlastic, 0, 0, null);
        canvas.drawText("plastic", (float)boxPlastic.getWidth()/4, (float)boxPlastic.getHeight()/4, label);
        //note: origin of draw text is top left of bin sprite
    }
}
