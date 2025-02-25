package com.example.assignment.edits.box;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.assignment.R;
import com.example.assignment.mgp2d.core.GameActivity;
import com.example.assignment.mgp2d.core.GameEntity;
import com.example.assignment.mgp2d.core.Vector2;

public abstract class baseBox extends GameEntity {
    private final Bitmap binSprite; //parent base bin sprite
    //public Vector2 position;    //bin position; every child will be at different location so they got their own position; actually do i need this? its already extension of game entity, so it shld come with position variable right?
    //scruffidy scuffness, i have no idea what im doing lmao; is this even considered an abstract class
    //protected Rect frameRect = new Rect();
    protected final Paint label;
    public baseBox(){
        Bitmap binBitmap = BitmapFactory.decodeResource(GameActivity.instance.getResources(), R.drawable.bin);  //replace with default sprite thats not paper ball to make sure this class manages to init child items; in other words, for debugging

        //want to set to half size of image, so setting size after init image
        setSize(new Vector2((float)binBitmap.getWidth()/2,(float)binBitmap.getHeight()/2));
        binSprite = Bitmap.createScaledBitmap(binBitmap,(int) getSize().x,(int) getSize().y,true); //halved size of sprite here

        label = new Paint();
        label.setTextSize(100.f);
        label.setColor(Color.parseColor("#ffffff"));
    }

    @Override
    public void onRender(Canvas canvas) {
    //this is a bit scuffed, but basically tis is base for children to render below their sprite (category symbol) -> do i make tis abstract class?
    }

    public Bitmap getBinSprite() {
        return binSprite;   //going to use this so each child class can reference and render this bitmap below their category image sprite
    }
}
