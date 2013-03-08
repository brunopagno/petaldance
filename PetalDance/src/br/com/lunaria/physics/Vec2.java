package br.com.lunaria.physics;

public class Vec2 {

    public float x;
    public float y;

    public Vec2() {
        this(0, 0);
    }

    public Vec2(float x, float y) {
        set(x, y);
    }

    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vec2 add(Vec2 p) {
        return new Vec2(x + p.x, y + p.y);
    }

    public void add(float x, float y) {
        this.x += x;
        this.y += y;
    }

    public Vec2 sub(Vec2 p) {
        return new Vec2(x - p.x, y - p.y);
    }

    public void sub(float x, float y) {
        this.x -= x;
        this.y -= y;
    }

    @Override
    protected Vec2 clone() {
        return new Vec2(x, y);
    }

}
