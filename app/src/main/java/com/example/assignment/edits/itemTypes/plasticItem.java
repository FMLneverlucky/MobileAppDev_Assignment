package com.example.assignment.edits.itemTypes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.assignment.R;
import com.example.assignment.mgp2d.core.GameActivity;

public class plasticItem extends ItemType {
    @Override
    public String get_itemType() {
        return "plastic";
    }

    public plasticItem()
    {
        IdNum = ID.plastic;
        Bitmap plasticbitmap = BitmapFactory.decodeResource(GameActivity.instance.getResources(), R.drawable.plastic_bottle);
        itemBitmap = Bitmap.createScaledBitmap(plasticbitmap, plasticbitmap.getWidth()/3, plasticbitmap.getHeight()/3, true);
    }

}
