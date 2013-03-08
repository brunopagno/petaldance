package br.com.lunaria.physics;

public class StaticEntity extends Entity {

    public StaticEntity(float x, float y, float width, float height) {
        super(x, y, width, height, true);
    }

    @Override
    protected void preProcessCollision() {
        // nothing
    }

    @Override
    protected void processContact(int side, float distance_x, float distance_y) {
        // nothing
    }

    @Override
    public void update(float dt) {
        // nothing
    }

    @Override
    public void postUpdate() {
        // nothing
    }

}
