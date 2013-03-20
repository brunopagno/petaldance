package br.com.lunaria.physics;

public class Contact {

    public static final int UP = 1;
    public static final int DOWN = 2;
    public static final int LEFT = 4;
    public static final int RIGHT = 8;
    public static final int HORIZONTAL = 12;
    public static final int VERTICAL = 3;
    public static final int ALL = 15;

    public Entity entity;
    public int side;

    public Contact(Entity entity, int side) {
        this.entity = entity;
        this.side = side;
    }

    public static boolean overlap(Entity e1, Entity e2) {
        return overlap(e1.position.x, e1.position.y, e1.width, e1.height, e2.position.x, e2.position.y, e2.width, e2.height);
    }

    public static boolean overlap(float x1, float y1, float w1, float h1, float x2, float y2, float w2, float h2) {
        return !(x1 > x2 + w2 || x1 + w1 < x2 || y1 > y2 + h2 || y1 + h1 < y2);
    }

}
