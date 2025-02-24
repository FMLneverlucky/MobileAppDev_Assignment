package com.example.assignment.edits.itemTypes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.assignment.R;
import com.example.assignment.mgp2d.core.GameActivity;

public class plasticItem implements ItemType{
    private final Bitmap plasticBitmap;
    private final ID idNum= ID.plastic;

    public plasticItem()
    {
        Bitmap plasticbitmap = BitmapFactory.decodeResource(GameActivity.instance.getResources(), R.drawable.plastic_bottle);
        plasticBitmap = Bitmap.createScaledBitmap(plasticbitmap, plasticbitmap.getWidth()/3, plasticbitmap.getHeight()/3, true);
    }

    @Override
    public Bitmap getBitmap() {
        return plasticBitmap;
    }

    @Override
    public ID getIdNum() {
        return idNum;
    }


    public String item_getType() {
        return "plastic";
    }
}
