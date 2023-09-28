package com.neet.Entity.Bonuses;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.neet.Entity.Bonus;
import com.neet.Entity.Enemy;
import com.neet.Entity.Player;
import com.neet.Handlers.Content;
import com.neet.Main.GamePanel;
import com.neet.TileMap.TileMap;

public class CheckPoint extends Bonus {

    private BufferedImage[] sprites;
    private Player player;
    private boolean active;

    public CheckPoint(TileMap tm, Player p) {

        super(tm);
        player = p;

        width = 48;
        height = 48;
        cwidth = 48;
        cheight = 48;

        sprites = Content.CheckPoint[0];

        animation.setFrames(sprites);
        animation.setDelay(10);

        left = true;
        facingRight = false;

    }


    public void update() {

        if(!active) {
            if(Math.abs(player.getx() - x) < GamePanel.WIDTH) active = true;
            return;
        }
        checkTileMapCollision();
        if(!bottomLeft) {
            left = false;
            right = facingRight = true;
        }
        setPosition(xtemp, ytemp);


        // update animation
        animation.update();

    }

    public void draw(Graphics2D g) {

        super.draw(g);

    }

}