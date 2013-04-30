package br.com.lunaria.physics;

public class GravityEntity extends Entity {

    public Vec2 speed;
    public Vec2 topSpeed;
    public float drag;
    private Vec2 push;
    private boolean filterFixed;

    public GravityEntity(float x, float y, float width, float height) {
        this(x, y, width, height, 0);
    }

    public GravityEntity(float x, float y, float width, float height, float drag) {
        super(x, y, width, height, false);

        this.speed = new Vec2();
        this.topSpeed = new Vec2(Float.MAX_VALUE, Float.MAX_VALUE);
        this.drag = drag;
        this.push = new Vec2();
        this.filterFixed = true;
    }

    public void setFilterFixed(boolean filterFixed) {
        this.filterFixed = filterFixed;
    }

    public void impulse(float x, float y) {
        speed.x += x;
        speed.y += y;
    }

    @Override
    protected void preProcessCollision() {
        push.set(0, 0);
    }

    @Override
    protected void executeProcessCollision(Entity entity, int side, float distance_x, float distance_y) {
        if (filterFixed && !entity.fixed) {
            return;
        }

        if ((side & Contact.LEFT) == Contact.LEFT) {
            push.x += distance_x;
        } else if ((side & Contact.RIGHT) == Contact.RIGHT) {
            push.x -= distance_x;
        } else if ((side & Contact.UP) == Contact.UP) {
            push.y -= distance_y;
        } else if ((side & Contact.DOWN) == Contact.DOWN) {
            push.y += distance_y;
        }
    }

    @Override
    public void update(float dt) {
        if (speed.x > 0) {
            speed.x -= drag;
        } else if (speed.x < 0) {
            speed.x += drag;
        }

        if (Math.abs(speed.x) <= drag / 2f) {
            speed.x = 0;
        }

        speed.y -= universe.gravity;

        if (Math.abs(speed.x) > topSpeed.x) {
            speed.x = speed.x > 0 ? topSpeed.x : -topSpeed.x;
        }
        if (Math.abs(speed.y) > topSpeed.y) {
            speed.y = speed.y > 0 ? topSpeed.y : -topSpeed.y;
        }

        position.x += speed.x * dt;
        position.y += speed.y * dt;
    }

    @Override
    public void postUpdate() {
        position.x -= push.x;
        position.y -= push.y;

        if (Math.abs(push.x) > 0.02f) {
            speed.x = 0;
        }
        if (Math.abs(push.y) > 0.02f) {
            speed.y = 0;
        }
    }

}
