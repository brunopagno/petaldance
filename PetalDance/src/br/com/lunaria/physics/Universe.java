package br.com.lunaria.physics;

import java.util.ArrayList;

public class Universe {

    public float gravity;
    public ArrayList<Entity> entities;

    public Universe(float gravity) {
        entities = new ArrayList<Entity>(32);
        this.gravity = gravity;
    }

    public void add(Entity entity) {
        entity.setUniverse(this);
        entities.add(entity);
    }

    public void remove(Entity entity) {
        entities.remove(entity);
    }

    public ArrayList<Entity> getEntitiesByRegion(float x, float y, float width, float height) {
        ArrayList<Entity> region = new ArrayList<Entity>();
        for (Entity entity : entities) {
            if (!entity.fixed
                    && Contact.overlap(entity.position.x, entity.position.y, entity.width, entity.height, x, y, width, height))
                region.add(entity);
        }
        return region;
    }

    public void update(float dt) {
        for (int i = entities.size() - 1; i >= 0; i--) {
            entities.get(i).update(dt);
        }
        for (int i = entities.size() - 1; i >= 0; i--) {
            entities.get(i).processCollision();
        }
        for (int i = entities.size() - 1; i >= 0; i--) {
            entities.get(i).postUpdate();
        }
    }
}
