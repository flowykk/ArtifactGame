package com.neet.Entity.Bonuses;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.neet.Entity.Bonus;
import com.neet.Entity.Enemy;
import com.neet.Entity.Player;
import com.neet.Handlers.Content;
import com.neet.Main.GamePanel;
import com.neet.TileMap.TileMap;

public class speedbottle extends Bonus {

    private BufferedImage[] sprites;
    private Player player;
    private boolean active;

    public speedbottle(TileMap tm, Player p) {

        super(tm);
        player = p;

        width = 16;
        height = 16;
        cwidth = 16;
        cheight = 16;

        sprites = Content.SpeedBottle[0];

        animation.setFrames(sprites);
        animation.setDelay(7);

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