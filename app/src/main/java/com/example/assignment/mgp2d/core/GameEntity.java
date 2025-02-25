package com.example.assignment.mgp2d.core;

import android.graphics.Canvas;
import android.graphics.Rect;

public abstract class GameEntity {
    protected String entityType = "";
    protected Vector2 _position = new Vector2(0, 0);
    public Vector2 getPosition() { return _position.copy(); }
    public void setPosition(Vector2 position) { _position = position; }
    protected Vector2 size = new Vector2(0, 0);
    public Vector2 getSize() {return size.copy();}
    public void setSize(Vector2 newsize) {size = newsize;}
    protected int _ordinal = 0;
    public int getOrdinal() { return _ordinal; }

    private boolean _isDone = false;
    public void destroy() { _isDone = true; }
    public boolean canDestroy() { return _isDone; }

    public void onUpdate() {}

    public abstract void onUpdate(float dt);    //why wasnt it the abstract with float dt @_@

    public abstract void onRender(Canvas canvas);

    protected Rect dstRect = new Rect();
    public Rect getEntityRect()
    {
        return dstRect;
    }
    public boolean checkCollision(Rect rect1, Rect rect2)
    {
        return Rect.intersects(rect1, rect2);
    }
    public String getEntityType()
    {
        return entityType;
    }

    public void setEntityType(String s)
    {
        entityType = s;
    }
}
