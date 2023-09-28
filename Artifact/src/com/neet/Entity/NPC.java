package com.neet.Entity;

import com.neet.Audio.JukeBox;
import com.neet.TileMap.TileMap;

public class NPC extends MapObject {

    protected boolean dead;
    protected int damage;
    protected boolean remove;

    protected long flinchCount;

    public NPC(TileMap tm) {
        super(tm);
        remove = false;
    }

    public boolean isDead() { return dead; }
    public boolean shouldRemove() { return remove; }

    public int getDamage() { return damage; }

    public void update() {}

}



