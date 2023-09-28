package com.neet.Entity.NPCs;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.neet.Entity.Enemy;
import com.neet.Entity.NPC;
import com.neet.Entity.Player;
import com.neet.Handlers.Content;
import com.neet.Main.GamePanel;
import com.neet.TileMap.TileMap;

public class Magician extends NPC {

    private BufferedImage[] sprites;
    private Player player;
    private boolean active;

    public Magician(TileMap tm, Player p) {

        super(tm);
        player = p;

        width = 17;
        height = 18;
        cwidth = 17;
        cheight = 18;

        damage = 1;
        moveSpeed = 0.8;
        fallSpeed = 0.15;
        maxFallSpeed = 4.0;
        jumpStart = -5;

        sprites = Content.Magician[0];

        animation.setFrames(sprites);
        animation.setDelay(12);

        left = false;
        facingRight = false;

    }

    private void getNextPosition() {
    }

    public void update() {

        if(!active) {
            if(Math.abs(player.getx() - x) < GamePanel.WIDTH) active = true;
            return;
        }
        checkTileMapCollision();
        if(!bottomLeft) {
            left = false;
            right = facingRight = false;
        }
        setPosition(xtemp, ytemp);


        // update animation
        animation.update();

    }

    public void draw(Graphics2D g) {

        super.draw(g);

    }

}