package br.com.lunaria.physics;

import java.util.ArrayList;

public abstract class Entity {

    public Universe universe;

    public final boolean fixed;
    public Vec2 position;
    public float width;
    public float height;

    protected ArrayList<Entity> currentContacts;
    protected ArrayList<Entity> lastContacts;
    protected ArrayList<Contact> maintenedContacts;
    protected int contactFilter;

    protected ContactListener listener;

    public Object data;

    public Entity(float x, float y, float width, float height, boolean fixed) {
        this.position = new Vec2(x, y);
        this.width = width;
        this.height = height;
        this.fixed = fixed;

        this.currentContacts = new ArrayList<Entity>(8);
        this.lastContacts = new ArrayList<Entity>(8);
        this.maintenedContacts = new ArrayList<Contact>(8);

        this.contactFilter = 0;
    }

    public void setUniverse(Universe universe) {
        this.universe = universe;
    }

    public int getContactFilter() {
        return contactFilter;
    }

    public void setContactFilter(int filter) {
        this.contactFilter = filter;
    }

    public ContactListener getContactListener() {
        return listener;
    }

    public void setContactListener(ContactListener listener) {
        this.listener = listener;
    }

    public void removeFromUniverse() {
        this.universe.remove(this);
    }

    public float getHorizontalDistance(Entity entity) {
        float distance = 0;
        if (position.x < entity.position.x) {
            distance = entity.position.x - (position.x + width);
        } else {
            distance = position.x - (entity.position.x + entity.width);
        }

        return distance;
    }

    public float getVerticalDistance(Entity entity) {
        float distance = 0;
        if (position.y < entity.position.y) {
            distance = entity.position.y - (position.y + height);
        } else {
            distance = position.y - (entity.position.y + entity.height);
        }

        return distance;
    }

    protected void beginContact(Contact contact) {
        if (listener != null) {
            listener.beginContact(contact);
        }
    }

    protected void endContact(Contact contact) {
        if (listener != null) {
            listener.endContact(contact);
        }
    }

    protected void processCollision() {
        preProcessCollision();

        for (int i = 0; i < universe.entities.size(); i++) {
            Entity entity = universe.entities.get(i);
            if (!(entity == this) && Contact.overlap(this, entity)) {
                currentContacts.add(entity);
            }
        }

        for (int i = 0; i < currentContacts.size(); i++) {
            Entity entity = currentContacts.get(i);
            int side = 0;
            boolean filtered = false;

            float distance_x = getHorizontalDistance(entity);
            float distance_y = getVerticalDistance(entity);
            float fdx = Math.abs(distance_x);
            float fdy = Math.abs(distance_y);

            /*
             * Ok this is a kind of workaround.
             * Sometimes the nonfixed entity would lose all it's jump power like if it had colided
             * but without colliding. That was happening because the contact was in the very edge of the entity
             * and because of the updates the contact would be processed two times, one for each axis.
             * So this will prevent the entity from hitting such edge twice.
             */
            if (Math.abs(fdx - fdy) > 3f) {
                if (fdy < fdx) {
                    if (position.y < entity.position.y) {
                        if ((entity.contactFilter & Contact.DOWN) == Contact.DOWN
                                || (this.contactFilter & Contact.UP) == Contact.UP) {
                            filtered = true;
                        }
                        side = Contact.UP;
                    } else {
                        if ((entity.contactFilter & Contact.UP) == Contact.UP
                                || (this.contactFilter & Contact.DOWN) == Contact.DOWN) {
                            filtered = true;
                        }
                        side = Contact.DOWN;
                    }
                } else {
                    if (position.x < entity.position.x) {
                        if ((entity.contactFilter & Contact.LEFT) == Contact.LEFT
                                || (this.contactFilter & Contact.RIGHT) == Contact.RIGHT) {
                            filtered = true;
                        }
                        side = Contact.RIGHT;
                    } else {
                        if ((entity.contactFilter & Contact.RIGHT) == Contact.RIGHT
                                || (this.contactFilter & Contact.LEFT) == Contact.LEFT) {
                            filtered = true;
                        }
                        side = Contact.LEFT;
                    }
                }
            }

            Contact contact = null;

            for (int j = 0; j < maintenedContacts.size(); j++) {
                Contact maintenedContact = maintenedContacts.get(j);
                if (maintenedContact.entity == entity) {
                    contact = maintenedContact;
                }
            }

            if (contact == null) {
                contact = new Contact(entity, side, filtered);
                beginContact(contact);
                maintenedContacts.add(contact);
            }

            if (!contact.filtered && side > 0 && contact.side != side) {
                endContact(contact);
                Contact newMaintenedContact = new Contact(entity, side, filtered);
                beginContact(newMaintenedContact);
                maintenedContacts.remove(contact);
                maintenedContacts.add(newMaintenedContact);
            }

            if (!contact.filtered) {
                executeProcessCollision(entity, side, distance_x, distance_y);
            }
        }

        for (int i = maintenedContacts.size() - 1; i >= 0; i--) {
            Contact contact = maintenedContacts.get(i);
            if (!currentContacts.contains(contact.entity)) {
                maintenedContacts.remove(i);
                endContact(contact);
            }
        }

        lastContacts.clear();
        for (int i = 0; i < currentContacts.size(); i++) {
            lastContacts.add(currentContacts.get(i));
        }

        currentContacts.clear();
    }

    protected abstract void preProcessCollision();

    protected abstract void executeProcessCollision(Entity entity, int side, float distance_x, float distance_y);

    public abstract void update(float dt);

    public abstract void postUpdate();

}
