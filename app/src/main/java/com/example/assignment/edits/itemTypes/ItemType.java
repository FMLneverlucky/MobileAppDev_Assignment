package com.example.assignment.edits.itemTypes;

import android.graphics.Bitmap;

public abstract class ItemType {

    public enum ID{
        none,   //default value, for debugging
        paper,
        plastic,
        metal,
        glass
    };
    protected ID IdNum = null;

    protected Bitmap itemBitmap = null;

    public Bitmap getBitmap() {
        return itemBitmap;
    }

    public abstract String get_itemType();

}
