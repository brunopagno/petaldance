package br.com.lunaria.physics;

public class StaticEntity extends Entity {

    public StaticEntity(float x, float y, float width, float height) {
        super(x, y, width, height, true);
    }

    @Override
    protected void preProcessCollision() {}

    @Override
    protected void executeProcessCollision(Entity entity, int side, float distance_x, float distance_y) {}

    @Override
    public void update(float dt) {}

    @Override
    public void postUpdate() {}

}
