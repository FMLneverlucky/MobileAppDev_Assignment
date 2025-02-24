package com.example.assignment.edits.itemTypes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.assignment.R;
import com.example.assignment.mgp2d.core.GameActivity;

public class defaultItem implements ItemType{
    private final Bitmap defaultSprite;
    private final ID num = ID.none;
    public defaultItem(){
    Bitmap bitmap = BitmapFactory.decodeResource(GameActivity.instance.getResources(), R.drawable.default_item_texture);
    defaultSprite = Bitmap.createScaledBitmap(bitmap, (int)bitmap.getWidth() /2, bitmap.getHeight() /2, true);
    //halved size of sprite here
    }
    @Override
    public Bitmap getBitmap() {
        return defaultSprite;
    }

    @Override
    public ID getIdNum() {
        return num;
    }

    public String item_getType() {
        return "default";
    }
}
