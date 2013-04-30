package br.com.lunaria.demo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JPanel;

import br.com.lunaria.physics.Entity;
import br.com.lunaria.physics.GravityEntity;
import br.com.lunaria.physics.Universe;

@SuppressWarnings("serial")
public class PetalDanceRender extends JPanel implements Runnable, KeyListener {

    private Universe map;
    private float power = 40;
    private boolean left;
    private boolean right;
    private GravityEntity love;
    //    private ManualEntity manual;
    private int[] attack = new int[4];

    public PetalDanceRender(Universe map) {
        this.map = map;
        for (Entity entity : map.entities) {
            if (entity instanceof GravityEntity) {
                this.love = (GravityEntity) entity;
                break;
            }
            //            if (entity instanceof ManualEntity) {
            //                this.manual = (ManualEntity) entity;
            //            }
        }
        this.love.topSpeed.x = 255;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.BLUE);
        for (int i = 0; i < map.entities.size(); i++) {
            Entity entity = map.entities.get(i);
            g.drawString("(" + entity.position.x + " " + entity.position.y + ")", 10, 20 * (i + 1));
        }

        ((Graphics2D) g).scale(1, -1);
        ((Graphics2D) g).translate(0, -getHeight());

        for (Entity entity : map.entities) {
            if (entity.fixed)
                g.setColor(Color.BLACK);
            else
                g.setColor(Color.RED);
            g.drawRect((int) entity.position.x, (int) entity.position.y, (int) entity.width, (int) entity.height);
        }

        g.setColor(Color.CYAN);
        g.drawRect(attack[0], attack[1], attack[2], attack[3]);
    }

    @Override
    public void run() {
        while (true) {
            this.updateMap();
            this.repaint();

            if (left) {
                love.impulse(-power, 0);
            }
            if (right) {
                love.impulse(power, 0);
            }

            //            man.position.y -= 1f;

            try {
                Thread.sleep(17);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateMap() {
        map.update(1f / 60f);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            left = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            right = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_Z || e.getKeyCode() == KeyEvent.VK_SPACE) {
            love.speed.y = 0;
            love.impulse(0, power * 14);
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            attack[0] = (int) love.position.x;
            attack[1] = (int) love.position.y;
            attack[2] = (int) love.width;
            attack[3] = (int) love.height;

            ArrayList<Entity> hits = map.getEntitiesByRegion(attack[0], attack[1], attack[2], attack[3]);
            for (Entity hit : hits) {
                System.out.println("HIT " + hit);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            left = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            right = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // nothing
    }

}
