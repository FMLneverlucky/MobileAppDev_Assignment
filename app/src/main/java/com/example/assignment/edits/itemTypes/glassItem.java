package com.example.assignment.edits.itemTypes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.assignment.R;
import com.example.assignment.mgp2d.core.GameActivity;

public class glassItem implements ItemType{
    public String item_getType() {
        return "glass";
    }

    private final Bitmap glassBitmap;
    private final ID idNum= ID.glass;

    public glassItem()
    {
        Bitmap plasticbitmap = BitmapFactory.decodeResource(GameActivity.instance.getResources(), R.drawable.glass_bottle);
        glassBitmap = Bitmap.createScaledBitmap(plasticbitmap, plasticbitmap.getWidth()/2, plasticbitmap.getHeight()/2, true);
    }

    @Override
    public Bitmap getBitmap() {
        return glassBitmap;
    }

    @Override
    public ID getIdNum() {
        return idNum;
    }

}
