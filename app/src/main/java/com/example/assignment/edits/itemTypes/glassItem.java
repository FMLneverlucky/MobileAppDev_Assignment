package com.example.assignment.edits.itemTypes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.assignment.R;
import com.example.assignment.mgp2d.core.GameActivity;

public class glassItem extends ItemType {
    @Override
    public String get_itemType() {
        return "glass";
    }

    public glassItem()
    {
        IdNum = ID.glass;
        Bitmap plasticbitmap = BitmapFactory.decodeResource(GameActivity.instance.getResources(), R.drawable.glass_bottle);
        itemBitmap = Bitmap.createScaledBitmap(plasticbitmap, plasticbitmap.getWidth()/2, plasticbitmap.getHeight()/2, true);
    }

}
