package com.neet.Entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.function.DoubleToIntFunction;

import javax.imageio.ImageIO;

import com.neet.Audio.JukeBox;
import com.neet.Entity.Bonuses.*;
import com.neet.Entity.NPCs.Magician;
import com.neet.Entity.NPCs.Merchant;
import com.neet.Handlers.Keys;
import com.neet.TileMap.TileMap;

public class Player extends MapObject {
	
	// references
	private ArrayList<Enemy> enemies;
	private ArrayList<Coin> coins;

	private ArrayList<Merchant> merchant;
	private ArrayList<Magician> magician;

	private ArrayList<smallbottle> smallbottles;
	private ArrayList<bigbottle> bigbottles;
	private ArrayList<speedbottle> speedbottles;
	private ArrayList<apple> apples;

	private ArrayList<Red> red;
	private ArrayList<Green> green;
	private ArrayList<Blue> blue;
	private ArrayList<Yellow> yellow;
	private ArrayList<Ring> rings;
	private ArrayList<CheckPoint> checkpoint;
	private ArrayList<Rune> runes;
	
	// player stuff
	private int lives;
	private int coinCount;
	private int runeCount;
	private int smallbottleCount;
	private int bigbottleCount;
	private int appleCount;
	private int speedbottleCount;
	private int currentAblity;

	private int speedparametr;
	private int speedparametrTime;

	private int hittableTime;

	private int ringClaimed;
	private int redClaimed;
	private int greenClaimed;
	private int blueClaimed;
	private int yellowClaimed;

	private int health;
	private int currentX;
	private int currentY;
	private int countParticles;
	private int maxHealth;
	private int damage;
	private int chargeDamage;
	private boolean knockback;

	private boolean hittable;

	private boolean flinching;
	private long flinchCount;
	private int score;
	private boolean doubleJump;
	private boolean alreadyDoubleJump;
	private double doubleJumpStart;
	private ArrayList<EnergyParticle> energyParticles;
	private long time;
	
	// actions
	private boolean dashing;
	private boolean attacking;
	private boolean upattacking;
	private boolean charging;
	private boolean upspeed;

	private boolean tralkingToMerchant;
	private boolean tralkingToMagician;

	private int chargingTick;
	private boolean teleporting;
	
	// animations
	private ArrayList<BufferedImage[]> sprites;
	private final int[] NUMFRAMES = {
		4, 6, 6, 3, 3, 5, 3, 6, 2, 1, 3
	};
	private final int[] FRAMEWIDTHS = {
		40, 40, 40, 40, 40, 40, 80, 40, 40, 40, 40
	};
	private final int[] FRAMEHEIGHTS = {
		40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40
	};
	private final int[] SPRITEDELAYS = {
		10, 6, 5, 10, 10, 8, 8, 5, 5, -1, 1
	};
	
	private Rectangle ar;
	private Rectangle aur;
	private Rectangle cr;
	
	// animation actions
	private static final int IDLE = 0;
	private static final int WALKING = 1;
	private static final int ATTACKING = 2;
	private static final int JUMPING = 3;
	private static final int FALLING = 4;
	private static final int UPATTACKING = 5;
	private static final int CHARGING = 6;
	private static final int DASHING = 7;
	private static final int KNOCKBACK = 8;
	private static final int DEAD = 9;
	private static final int TELEPORTING = 10;
	
	// emotes
	private BufferedImage confused;
	private BufferedImage surprised;
	public static final int NONE = 0;
	public static final int CONFUSED = 1;
	public static final int SURPRISED = 2;
	private int emote = NONE;
	
	public Player(TileMap tm) {
		
		super(tm);
		
		ar = new Rectangle(0, 0, 0, 0);
		ar.width = 30;
		ar.height = 20;
		aur = new Rectangle((int)x - 15, (int)y - 45, 30, 30);
		cr = new Rectangle(0, 0, 0, 0);
		cr.width = 50;
		cr.height = 40;
		
		width = 19;
		height = 29;
		cwidth = 19;
		cheight = 40;

		speedparametr = 1;
		
		moveSpeed = 1.6;
		maxSpeed = 1.6;
		stopSpeed = 1.6;
		fallSpeed = 0.15;
		maxFallSpeed = 4.0;
		jumpStart = -4.8;
		stopJumpSpeed = 0.3;
		doubleJumpStart = -3;
		coinCount = 0;
		runeCount = 0;
		countParticles = 0;

	/*	currentX = 3270;
		currentY = 236;*/

		currentX = 300;
		currentY = 100;

		tralkingToMerchant = false;
		tralkingToMagician = false;
		
		damage = 2;
		chargeDamage = 1;
		
		facingRight = true;
		upspeed = false;
		hittable = true;
		
		lives = 1                       ;
		health = maxHealth = 10;
		
		// load sprites
		try {
			
			//BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Player/PlayerSprites.gif"));
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Player/pers1.png"));
			
			int count = 0;
			sprites = new ArrayList<BufferedImage[]>();
			for(int i = 0; i < NUMFRAMES.length; i++) {
				BufferedImage[] bi = new BufferedImage[NUMFRAMES[i]];
				for(int j = 0; j < NUMFRAMES[i]; j++) {
					bi[j] = spritesheet.getSubimage(
						j * FRAMEWIDTHS[i],
						count,
						FRAMEWIDTHS[i],
						FRAMEHEIGHTS[i]
					);
				}
				sprites.add(bi);
				count += FRAMEHEIGHTS[i];
			}
			
			// emotes
			spritesheet = ImageIO.read(getClass().getResourceAsStream(
				"/HUD/Emotes.gif"
			));
			confused = spritesheet.getSubimage(
				0, 0, 14, 17
			);
			surprised = spritesheet.getSubimage(
				14, 0, 14, 17
			);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		energyParticles = new ArrayList<EnergyParticle>();
		
		setAnimation(IDLE);
		
		JukeBox.load("/SFX/playerjump.mp3", "playerjump");
		JukeBox.load("/SFX/playerlands.mp3", "playerlands");
		JukeBox.load("/SFX/playerattack.mp3", "playerattack");
		JukeBox.load("/SFX/playerhit.mp3", "playerhit");
		JukeBox.load("/SFX/playercharge.mp3", "playercharge");
		
	}

	public void init(ArrayList<Magician> magician, ArrayList<Merchant> merchant, ArrayList<smallbottle> smallbottles, ArrayList<bigbottle> bigbottles, ArrayList<speedbottle> speedbottles,ArrayList<apple> apples , ArrayList<Yellow> yellow, ArrayList<Blue> blue, ArrayList<Green> green, ArrayList<Red> red, ArrayList<Ring> rings, ArrayList<CheckPoint> checkpoint, ArrayList<Rune> runes, ArrayList<Coin> coins, ArrayList<Enemy> enemies, ArrayList<EnergyParticle> energyParticles) {
		this.enemies = enemies;
		this.energyParticles = energyParticles;
		this.runes = runes;
		this.coins = coins;

		this.smallbottles = smallbottles;
		this.merchant = merchant;
		this.magician = magician;
		this.bigbottles = bigbottles;
		this.speedbottles = speedbottles;
		this.apples = apples;

		this.red = red;
		this.green = green;
		this.blue = blue;
		this.yellow = yellow;

		this.rings = rings;
		this.checkpoint = checkpoint;
	}

	
	public int getHealth() { return health; }
	public int getCoinCount() { return coinCount; }

	public int getSmallbottleCount() { return smallbottleCount; }
	public int getBigbottleCount() { return bigbottleCount; }
	public int getAppleCount() { return appleCount; }
	public int getSpeedbottleCount() { return speedbottleCount; }

	public int getRuneCount() { return runeCount; }
	public int getRingClaimed() { return ringClaimed; }
	public int getRedClaimed() { return redClaimed; }
	public int getGreenClaimed() { return greenClaimed; }
	public int getBlueClaimed() { return blueClaimed; }
	public int getYellowClaimed() { return yellowClaimed; }
	public int getSpeedparametrTime() { return speedparametrTime; }

	public boolean isUpspeed() { return upspeed; }
	public boolean isHittable() { return hittable; }
	public void setHittable(boolean hittable) { this.hittable = hittable; }

	public boolean isTralkingToMerchant() { return tralkingToMerchant; }
	public void setTralkingToMerchant(boolean tralkingToMerchant) { this.tralkingToMerchant = tralkingToMerchant; }

	public boolean isTralkingToMagician() { return tralkingToMagician; }
	public void setTralkingToMagician(boolean tralkingToMagician) { this.tralkingToMagician = tralkingToMagician; }


	public int getCurrentX() { return currentX; }
	public int getCurrentY() { return currentY; }

	public void setCurrentX(int currentX) { this.currentX = currentX; }
	public void setCurrentY(int currentY) { this.currentY = currentY; }

	public int getCurrentAblity() { return currentAblity; }
	public void setCurrentAblity(int currentAblity) { this.currentAblity = currentAblity; }

	public int getMaxHealth() { return maxHealth; }
	public void setCoinCount(int coinCount) { this.coinCount = coinCount; }
	public void setRuneCount(int runeCount) { this.runeCount = runeCount; }

	public void setSmallbottleCount(int smallbottleCount) { this.smallbottleCount = smallbottleCount; }
	public void setBigbottleCount(int bigbottleCount) { this.bigbottleCount = bigbottleCount; }
	public void setAppleCount(int appleCount) {this.appleCount = appleCount; }
	public void setSpeedbottleCount(int speedbottleCount) { this.speedbottleCount = speedbottleCount; }

	public void setSpeedparametr(int speedparametr) { this.speedparametr = speedparametr; }

	public void setUpspeed(boolean upspeed) { this.upspeed = upspeed; }
	public void setEmote(int i) {
		emote = i;
	}
	public void setTeleporting(boolean b) { teleporting = b; }
	
	public void setJumping(boolean b) {
		if(knockback) return;
		if(b && !jumping && falling && !alreadyDoubleJump) {
			doubleJump = true;
		}
		jumping = b;
	}
	public void setAttacking() {
		if(knockback) return;
		if(charging) return;
		if(up && !attacking) upattacking = true;
		else attacking = true;
	}
	public void setCharging() {
		if(knockback) return;
		if(!attacking && !upattacking && !charging) {
			charging = true;
			JukeBox.play("playercharge");
			chargingTick = 0;
		}
	}
	public void setDashing(boolean b) {
		if(!b) dashing = false;
		else if(b && !falling) {
			dashing = true;
		}
	}
	public boolean isDashing() { return dashing; }
	
	public void setDead() {
		health = 0;
		stop();
	}
	
	public String getTimeToString() {
		int minutes = (int) (time / 3600);
		int seconds = (int) ((time % 3600) / 60);
		return seconds < 10 ? minutes + ":0" + seconds : minutes + ":" + seconds;
	}
	public long getTime() { return time; }
	public void setTime(long t) { time = t; }
	public void setHealth(int i) { health = i; }

	public void setLives(int lives) { this.lives = lives; }

	public void gainLife() { lives++; }
	public void loseLife() { lives--; }
	public int getLives() { return lives; }
	
	public void increaseScore(int score) {
		this.score += score; 
	}
	
	public int getScore() { return score; }
	
	public void hit(int damage) {
		if(flinching) return;
		JukeBox.play("playerhit");
		stop();
		health -= damage;
		if(health < 0) health = 0;
		flinching = true;
		flinchCount = 0;
		if(facingRight) dx = -1;
		else dx = 1;
		dy = -3;
		knockback = true;
		falling = true;
		jumping = false;
	}
	
	public void reset() {
		health = maxHealth;
		facingRight = true;
		currentAction = -1;
		stop();
		countParticles = 0;
	}
	
	public void stop() {
		left = right = up = down = flinching = 
			dashing = jumping = attacking = upattacking = charging = false;
	}
	
	private void getNextPosition() {
		
		if(knockback) {
			dy += fallSpeed * 2;
			if(!falling) knockback = false;
			return;
		}
		
		double maxSpeed = this.maxSpeed;
		if(dashing) maxSpeed *= 1.75;
		
		// movement
		if(left) {
			dx -= moveSpeed * speedparametr;
			if(dx < -maxSpeed * speedparametr) {
				dx = -maxSpeed * speedparametr;
			}
		}
		else if(right) {
			dx += moveSpeed * speedparametr;
			if(dx > maxSpeed * speedparametr) {
				dx = maxSpeed * speedparametr;
			}
		}
		else {
			if(dx > 0) {
				dx -= stopSpeed;
				if(dx < 0) {
					dx = 0;
				}
			}
			else if(dx < 0) {
				dx += stopSpeed;
				if(dx > 0) {
					dx = 0;
				}
			}
		}
		
		// cannot move while attacking, except in air
		if((attacking || upattacking || charging) &&
			!(jumping || falling)) {
			dx = 0;
		}
		
		// charging
		if(charging) {
			chargingTick++;
			if(facingRight) dx = moveSpeed  * speedparametr * (3 - chargingTick * 0.07);
			else dx = -moveSpeed  * speedparametr * (3 - chargingTick * 0.07);
		}
		
		// jumping
		if(jumping && !falling) {
			//sfx.get("jump").play();
			dy = jumpStart;
			falling = true;
			JukeBox.play("playerjump");
		}
		
		if(doubleJump) {
			dy = doubleJumpStart;
			alreadyDoubleJump = true;
			doubleJump = false;
			JukeBox.play("playerjump");
			for(int i = 0; i < 6; i++) {
				energyParticles.add(
					new EnergyParticle(
						tileMap,
						x,
						y + cheight / 4,
						EnergyParticle.DOWN));
			}
		}
		
		if(!falling) alreadyDoubleJump = false;
		
		// falling
		if(falling) {
			dy += fallSpeed;
			if(dy < 0 && !jumping) dy += stopJumpSpeed;
			if(dy > maxFallSpeed) dy = maxFallSpeed;
		}
		
	}
	
	private void setAnimation(int i) {
		currentAction = i;
		animation.setFrames(sprites.get(currentAction));
		animation.setDelay(SPRITEDELAYS[currentAction]);
		width = FRAMEWIDTHS[currentAction];
		height = FRAMEHEIGHTS[currentAction];
	}
	
	public void update() {

		System.out.println("x   " + x + "   y    " + y);

	//	System.out.println(getHealth())
		if (!hittable) {
			hittableTime++;
			if (hittableTime > 200) {
				hittable = true;
				hittableTime = 0;
			}

		}

		if (upspeed) {
			speedparametr = 2;
			speedparametrTime++;
			if (speedparametrTime > 100) {
				speedparametr = 1;
				upspeed = false;
				speedparametrTime = 0;
			}

		}


		time++;
		
		// check teleporting
		if(teleporting) {
			energyParticles.add(
				new EnergyParticle(tileMap, x, y, EnergyParticle.UP)
			);
		}
		
		// update position
		boolean isFalling = falling;
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		if(isFalling && !falling) {
			JukeBox.play("playerlands");
		}
		if(dx == 0) x = (int)x;
		
		// check done flinching
		if(flinching) {
			flinchCount++;
			if(flinchCount > 120) {
				flinching = false;
			}
		}
		
		// energy particles
		for(int i = 0; i < energyParticles.size(); i++) {
			energyParticles.get(i).update();
			if(energyParticles.get(i).shouldRemove()) {
				energyParticles.remove(i);
				i--;
			}
		}
		
		// check attack finished
		if(currentAction == ATTACKING ||
			currentAction == UPATTACKING) {
			if(animation.hasPlayedOnce()) {
				attacking = false;
				upattacking = false;
			}
		}
		if(currentAction == CHARGING) {
			if(animation.hasPlayed(1)) {
				charging = false;
			}
			cr.y = (int)y - 20;
			if(facingRight) cr.x = (int)x - 15;
			else cr.x = (int)x - 35;
			if(facingRight)
				energyParticles.add(
					new EnergyParticle(
						tileMap,
						x + 30,
						y,
						EnergyParticle.RIGHT));
			else
				energyParticles.add(
					new EnergyParticle(
						tileMap,
						x - 30,
						y,
						EnergyParticle.LEFT));
		}
		
		// check enemy interaction
		for(int i = 0; i < enemies.size(); i++) {

			Enemy e = enemies.get(i);

			// check attack
			if(currentAction == ATTACKING && animation.getFrame() == 3 && animation.getCount() == 0) {
				if(e.intersects(ar)) {
					e.hit(damage);
				}
			}
			
			// check upward attack
			if(currentAction == UPATTACKING &&
					animation.getFrame() == 3 && animation.getCount() == 0) {
				if(e.intersects(aur)) {
					e.hit(damage);
				}
			}
			
			// check charging attack
			if(currentAction == CHARGING) {
				if(animation.getCount() == 0) {
					if(e.intersects(cr)) {
						e.hit(chargeDamage);
					}
					/*if(e.intersects(this)) {
						e.hit(chargeDamage);
					}*/
				}
			}
			
			// collision with enemy
			if(!e.isDead() && intersects(e) && !charging) {
				if (hittable) {
					hit(e.getDamage());
				}


				System.out.println("keklolchikb");
			}
			
			if(e.isDead()) {
				JukeBox.play("explode", 2000);
			}

		}

		for(int i = 0; i < coins.size(); i++) {
			Coin c = coins.get(i);
			// collision with enemy
			if(!c.isDead() && intersects(c) && !charging) {
				coins.remove(i);

				energyParticles.add(new EnergyParticle(tileMap, x, y, EnergyParticle.LEFT));
				energyParticles.add(new EnergyParticle(tileMap, x, y, EnergyParticle.UP));
				energyParticles.add(new EnergyParticle(tileMap, x, y, EnergyParticle.DOWN));
				energyParticles.add(new EnergyParticle(tileMap, x, y, EnergyParticle.RIGHT));

				coinCount++;
			}
			if(c.isDead()) {
				JukeBox.play("explode", 2000);
			}
		}

		for(int i = 0; i < merchant.size(); i++) {
			Merchant c = merchant.get(i);
			// collision with enemy
			if(intersects(c)) {
				//if(Keys.isPressed(Keys.TALKTOM)) {
				setTralkingToMerchant(true);

				//}
			} else {
				setTralkingToMerchant(false);
			}
		}

		for(int i = 0; i < magician.size(); i++) {
			Magician c = magician.get(i);
			// collision with enemy
			if(intersects(c)) {
				//if(Keys.isPressed(Keys.TALKTOM)) {
				setTralkingToMagician(true);

				//}
			} else {
				setTralkingToMagician(false);
			}
		}

		for(int i = 0; i < runes.size(); i++) {

			Rune c = runes.get(i);

			// collision with enemy
			if(!c.isDead() && intersects(c) && !charging) {
				runes.remove(i);
				energyParticles.add(new EnergyParticle(tileMap, x, y, EnergyParticle.LEFT));
				energyParticles.add(new EnergyParticle(tileMap, x, y, EnergyParticle.UP));
				energyParticles.add(new EnergyParticle(tileMap, x, y, EnergyParticle.DOWN));
				energyParticles.add(new EnergyParticle(tileMap, x, y, EnergyParticle.RIGHT));
				runeCount++;
			}
			if(c.isDead()) { JukeBox.play("explode", 2000); }
		}

		for(int i = 0; i < checkpoint.size(); i++) {
			CheckPoint c = checkpoint.get(i);
			if(!c.isDead() && intersects(c) && !charging) {
				countParticles++;
				if (countParticles<20) energyParticles.add(new EnergyParticle(tileMap, x, y, EnergyParticle.UP));
				currentX = 550;
				currentY = 200;
			}
			if(c.isDead()) {
				JukeBox.play("explode", 2000);
			}
		}

		for(int i = 0; i < rings.size(); i++) {
			Ring c = rings.get(i);
			// collision with enemy
			if(!c.isDead() && intersects(c) && !charging) {
				rings.remove(i);

				energyParticles.add(new EnergyParticle(tileMap, x, y, EnergyParticle.LEFT));
				energyParticles.add(new EnergyParticle(tileMap, x, y, EnergyParticle.UP));
				energyParticles.add(new EnergyParticle(tileMap, x, y, EnergyParticle.DOWN));
				energyParticles.add(new EnergyParticle(tileMap, x, y, EnergyParticle.RIGHT));

				ringClaimed++;
			}
			if(c.isDead()) {
				JukeBox.play("explode", 2000);
			}
		}

		for(int i = 0; i < red.size(); i++) {
			Red c = red.get(i);
			// collision with enemy
			if(!c.isDead() && intersects(c) && !charging) {
				red.remove(i);

				energyParticles.add(new EnergyParticle(tileMap, x, y, EnergyParticle.LEFT));
				energyParticles.add(new EnergyParticle(tileMap, x, y, EnergyParticle.UP));
				energyParticles.add(new EnergyParticle(tileMap, x, y, EnergyParticle.DOWN));
				energyParticles.add(new EnergyParticle(tileMap, x, y, EnergyParticle.RIGHT));

				redClaimed++;
				System.out.println("red");
			}
			if(c.isDead()) {
				JukeBox.play("explode", 2000);
			}
		}

		for(int i = 0; i < green.size(); i++) {
			Green c = green.get(i);
			// collision with enemy
			if(!c.isDead() && intersects(c) && !charging) {
				green.remove(i);

				energyParticles.add(new EnergyParticle(tileMap, x, y, EnergyParticle.LEFT));
				energyParticles.add(new EnergyParticle(tileMap, x, y, EnergyParticle.UP));
				energyParticles.add(new EnergyParticle(tileMap, x, y, EnergyParticle.DOWN));
				energyParticles.add(new EnergyParticle(tileMap, x, y, EnergyParticle.RIGHT));

				greenClaimed++;
				System.out.println("green");
			}
			if(c.isDead()) {
				JukeBox.play("explode", 2000);
			}
		}

		for(int i = 0; i < blue.size(); i++) {
			Blue c = blue.get(i);
			// collision with enemy
			if(!c.isDead() && intersects(c) && !charging) {
				blue.remove(i);

				energyParticles.add(new EnergyParticle(tileMap, x, y, EnergyParticle.LEFT));
				energyParticles.add(new EnergyParticle(tileMap, x, y, EnergyParticle.UP));
				energyParticles.add(new EnergyParticle(tileMap, x, y, EnergyParticle.DOWN));
				energyParticles.add(new EnergyParticle(tileMap, x, y, EnergyParticle.RIGHT));

				blueClaimed++;
				System.out.println("blue");
			}
			if(c.isDead()) {
				JukeBox.play("explode", 2000);
			}
		}

		for(int i = 0; i < yellow.size(); i++) {
			Yellow c = yellow.get(i);
			// collision with enemy
			if(!c.isDead() && intersects(c) && !charging) {
				yellow.remove(i);

				energyParticles.add(new EnergyParticle(tileMap, x, y, EnergyParticle.LEFT));
				energyParticles.add(new EnergyParticle(tileMap, x, y, EnergyParticle.UP));
				energyParticles.add(new EnergyParticle(tileMap, x, y, EnergyParticle.DOWN));
				energyParticles.add(new EnergyParticle(tileMap, x, y, EnergyParticle.RIGHT));

				yellowClaimed++;
				System.out.println("yellow");
			}
			if(c.isDead()) {
				JukeBox.play("explode", 2000);
			}
		}

		for(int i = 0; i < smallbottles.size(); i++) {
			smallbottle c = smallbottles.get(i);
			// collision with enemy
			if(!c.isDead() && intersects(c) && !charging) {
				smallbottles.remove(i);

				energyParticles.add(new EnergyParticle(tileMap, x, y, EnergyParticle.LEFT));
				energyParticles.add(new EnergyParticle(tileMap, x, y, EnergyParticle.UP));
				energyParticles.add(new EnergyParticle(tileMap, x, y, EnergyParticle.DOWN));
				energyParticles.add(new EnergyParticle(tileMap, x, y, EnergyParticle.RIGHT));

				smallbottleCount++;
			}
			if(c.isDead()) {
				JukeBox.play("explode", 2000);
			}
		}

		for(int i = 0; i < bigbottles.size(); i++) {
			bigbottle c = bigbottles.get(i);
			// collision with enemy
			if(!c.isDead() && intersects(c) && !charging) {
				bigbottles.remove(i);

				energyParticles.add(new EnergyParticle(tileMap, x, y, EnergyParticle.LEFT));
				energyParticles.add(new EnergyParticle(tileMap, x, y, EnergyParticle.UP));
				energyParticles.add(new EnergyParticle(tileMap, x, y, EnergyParticle.DOWN));
				energyParticles.add(new EnergyParticle(tileMap, x, y, EnergyParticle.RIGHT));

				bigbottleCount++;
			}
			if(c.isDead()) {
				JukeBox.play("explode", 2000);
			}
		}

		for(int i = 0; i < speedbottles.size(); i++) {
			speedbottle c = speedbottles.get(i);
			// collision with enemy
			if(!c.isDead() && intersects(c) && !charging) {
				speedbottles.remove(i);

				energyParticles.add(new EnergyParticle(tileMap, x, y, EnergyParticle.LEFT));
				energyParticles.add(new EnergyParticle(tileMap, x, y, EnergyParticle.UP));
				energyParticles.add(new EnergyParticle(tileMap, x, y, EnergyParticle.DOWN));
				energyParticles.add(new EnergyParticle(tileMap, x, y, EnergyParticle.RIGHT));

				speedbottleCount++;
			}
			if(c.isDead()) {
				JukeBox.play("explode", 2000);
			}
		}

		for(int i = 0; i < apples.size(); i++) {
			apple c = apples.get(i);
			// collision with enemy
			if(!c.isDead() && intersects(c) && !charging) {
				apples.remove(i);

				energyParticles.add(new EnergyParticle(tileMap, x, y, EnergyParticle.LEFT));
				energyParticles.add(new EnergyParticle(tileMap, x, y, EnergyParticle.UP));
				energyParticles.add(new EnergyParticle(tileMap, x, y, EnergyParticle.DOWN));
				energyParticles.add(new EnergyParticle(tileMap, x, y, EnergyParticle.RIGHT));

				appleCount++;
			}
			if(c.isDead()) {
				JukeBox.play("explode", 2000);
			}
		}

		// set animation, ordered by priority
		if(teleporting) {
			if(currentAction != TELEPORTING) {
				setAnimation(TELEPORTING);
			}
		}
		else if(knockback) {
			if(currentAction != KNOCKBACK) {
				setAnimation(KNOCKBACK);
			}
		}
		else if(health == 0) {
			if(currentAction != DEAD) {
				setAnimation(DEAD);
			}
		}
		else if(upattacking) {
			if(currentAction != UPATTACKING) {
				JukeBox.play("playerattack");
				setAnimation(UPATTACKING);
				aur.x = (int)x - 15;
				aur.y = (int)y - 50;
			}
			else {
				if(animation.getFrame() == 4 && animation.getCount() == 0) {
					for(int c = 0; c < 3; c++) {
						energyParticles.add(
							new EnergyParticle(
								tileMap,
								aur.x + aur.width / 2,
								aur.y + 5,
								EnergyParticle.UP));
					}
				}
			}
		}
		else if(attacking) {
			if(currentAction != ATTACKING) {
				JukeBox.play("playerattack");
				setAnimation(ATTACKING);
				ar.y = (int)y - 6;
				if(facingRight) ar.x = (int)x + 10;
				else ar.x = (int)x - 40;
			}
			else {
				if(animation.getFrame() == 4 && animation.getCount() == 0) {
				for(int c = 0; c < 3; c++) {
					if(facingRight)
						energyParticles.add(
							new EnergyParticle(
								tileMap, 
								ar.x + ar.width - 4, 
								ar.y + ar.height / 2,
								EnergyParticle.RIGHT));
					else
						energyParticles.add(
							new EnergyParticle(
								tileMap,
								ar.x + 4,
								ar.y + ar.height / 2,
								EnergyParticle.LEFT));	
				}}
			}
		}
		else if(charging) {
			if(currentAction != CHARGING) {
				setAnimation(CHARGING);
			}
		}
		else if(dy < 0) {
			if(currentAction != JUMPING) {
				setAnimation(JUMPING);
			}
		}
		else if(dy > 0) {
			if(currentAction != FALLING) {
				setAnimation(FALLING);
			}
		}
		else if(dashing && (left || right)) {
			if(currentAction != DASHING) {
				setAnimation(DASHING);
			}
		}
		else if(left || right) {
			if(currentAction != WALKING) {
				setAnimation(WALKING);
			}
		}
		else if(currentAction != IDLE) {
			setAnimation(IDLE);
		}
		
		animation.update();
		
		// set direction
		if(!attacking && !upattacking && !charging && !knockback) {
			if(right) facingRight = true;
			if(left) facingRight = false;
		}
		
	}
	
	public void draw(Graphics2D g) {
		
		// draw emote
		if(emote == CONFUSED) {
			g.drawImage(confused, (int)(x + xmap - cwidth / 2), (int)(y + ymap - 40), null);
		}
		else if(emote == SURPRISED) {
			g.drawImage(surprised, (int)(x + xmap - cwidth / 2), (int)(y + ymap - 40), null);
		}
		
		// draw energy particles
		for(int i = 0; i < energyParticles.size(); i++) {
			energyParticles.get(i).draw(g);
		}
		
		// flinch
		if(flinching && !knockback) {
			if(flinchCount % 10 < 5) return;
		}
		
		super.draw(g);
		
	}
	
}