package com.example.assignment.edits.box;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.assignment.mgp2d.core.GameActivity;
import com.example.assignment.mgp2d.core.Vector2;

public class paperBox extends baseBox {
    private final Bitmap boxPaper;

    public paperBox(){
        boxPaper = getBinSprite().copy(Bitmap.Config.ARGB_8888, false);
        //shift sprite to desired location: mid btm
        setPosition(new Vector2((float) GameActivity.instance.getResources().getDisplayMetrics().widthPixels / 2, (float)GameActivity.instance.getResources().getDisplayMetrics().heightPixels));

        //setting rect position to render center of sprite at assigned location; yes center of sprite is at assigned location, that's why thrs addition and subtraction of half of width and height; just a note cos ik me with small brain will be confused; NOTHING TO SEE HERE, THERES NOTHING WRONG WITH THIS. LOOK ELSEWHERE
        destRect.left = (int)getPosition().x - boxPaper.getWidth()/2;
        destRect.top = (int) getPosition().y - boxPaper.getHeight()/2;
        destRect.right = (int)getPosition().x + boxPaper.getWidth()/2;
        destRect.bottom = (int) getPosition().y + boxPaper.getHeight()/2;
    }

    @Override
    public void onUpdate(float dt) {

    }

    @Override
    public void onRender(Canvas canvas) {
        super.onRender(canvas);
        canvas.drawBitmap(boxPaper, destRect.left, destRect.top, null);
        canvas.drawText("paper", destRect.left + label.getTextSize(), destRect.top + label.getTextSize(), label);   //adding text size to act as offset so text doesn't hover at top left corner of sprite
    }
}
