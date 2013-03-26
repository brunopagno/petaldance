package br.com.lunaria.demo;

import javax.swing.JFrame;

import br.com.lunaria.physics.GravityEntity;
import br.com.lunaria.physics.ManualEntity;
import br.com.lunaria.physics.StaticEntity;
import br.com.lunaria.physics.Universe;

public class PetalDanceDemo {

    public PetalDanceDemo(Universe map) {
        PetalDanceRender render = new PetalDanceRender(map);

        final JFrame frame = new JFrame("PetalDance Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1024, 600);
        frame.setLocationRelativeTo(null);
        frame.add(render);
        frame.addKeyListener(render);
        frame.setVisible(true);

        render.run();
    }

    public static void main(String args[]) {
        Universe map = new Universe(35);

        map.add(new StaticEntity(0, 0, 1024, 10));
        map.add(new StaticEntity(150, 150, 80, 80));
        map.add(new StaticEntity(150, 150, 200, 40));
        map.add(new StaticEntity(350, 150, 140, 40));
        map.add(new StaticEntity(350, 190, 50, 40));
        map.add(new StaticEntity(550, 150, 140, 40));
        map.add(new StaticEntity(750, 150, 140, 40));
        map.add(new ManualEntity(450, 450, 140, 40));

        GravityEntity hero = new GravityEntity(200, 60, 32, 64);
        hero.drag = 14;
        map.add(hero);

        map.add(new GravityEntity(400, 60, 24, 12));

        new PetalDanceDemo(map);
    }

}
