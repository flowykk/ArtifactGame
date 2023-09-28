package com.neet.GameState;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.neet.Audio.JukeBox;
import com.neet.Entity.Bonuses.*;
import com.neet.Entity.Enemies.Tengu;
import com.neet.Entity.Enemy;
import com.neet.Entity.EnemyProjectile;
import com.neet.Entity.EnergyParticle;
import com.neet.Entity.Explosion;
import com.neet.Entity.HUD;
import com.neet.Entity.NPCs.Magician;
import com.neet.Entity.NPCs.Merchant;
import com.neet.Entity.Player;
import com.neet.Entity.PlayerSave;
import com.neet.Entity.Teleport;
import com.neet.Entity.Title;
import com.neet.Entity.Enemies.Gazer;
import com.neet.Entity.Enemies.GelPop;
import com.neet.Handlers.Keys;
import com.neet.Main.GamePanel;
import com.neet.TileMap.Background;
import com.neet.TileMap.TileMap;

public class Level1AState extends GameState {

	private Background sky;
	private Background clouds;
	private Background mountains;

	private Player player;
	private TileMap tileMap;
	private ArrayList<Enemy> enemies;
	private ArrayList<EnemyProjectile> eprojectiles;

	private ArrayList<Coin> coins;

	private ArrayList<Merchant> merchant;
	private ArrayList<Magician> magician;

	private ArrayList<smallbottle> smallbottles;
	private ArrayList<bigbottle> bigbottles;
	private ArrayList<apple> apples;
	private ArrayList<speedbottle> speedbottles;

	private ArrayList<Ring> rings;
	private ArrayList<Red> red;
	private ArrayList<Green> green;
	private ArrayList<Blue> blue;
	private ArrayList<Yellow> yellow;
	private ArrayList<CheckPoint> checkpoint;
	private ArrayList<Rune> runes;
	private ArrayList<EnergyParticle> energyParticles;
	private ArrayList<Explosion> explosions;

	private HUD hud;
	private BufferedImage hageonText;
	private Title title;
	private Title subtitle;
	private Teleport teleport;

	// events
	private boolean blockInput = false;
	private int eventCount = 0;
	private int speedParametrTime = 0;
	private boolean eventStart;
	private ArrayList<Rectangle> tb;
	private boolean eventFinish;
	private boolean eventDead;

	public Level1AState(GameStateManager gsm) {
		super(gsm);
		init();
	}

	public void init() {

		// backgrounds
		//sky = new Background("/Backgrounds/sky.gif", 0);
		//clouds = new Background("/Backgrounds/clouds.gif", 0.1);
		mountains = new Background("/Backgrounds/back.png", 0.2);

		// tilemap
		tileMap = new TileMap(32);
		//tileMap.loadTiles("/Tilesets/ruinstileset.gif");
		tileMap.loadTiles("/Tilesets/test.png");
		tileMap.loadMap("/Maps/level1a.map");
		tileMap.setPosition(140, 0);
		tileMap.setBounds(
			tileMap.getWidth() - 1 * tileMap.getTileSize(),
			tileMap.getHeight() - 2 * tileMap.getTileSize(),
			0, 0
		);
		tileMap.setTween(1);

		// player
		player = new Player(tileMap);
		player.setPosition(player.getCurrentX(), player.getCurrentY());
		player.setHealth(PlayerSave.getHealth());
		player.setLives(PlayerSave.getLives());
		player.setTime(PlayerSave.getTime());

		// enemies
		enemies = new ArrayList<Enemy>();
		eprojectiles = new ArrayList<EnemyProjectile>();
		populateEnemies();

		coins = new ArrayList<Coin>();
		populateCoins();

		merchant = new ArrayList<Merchant>();
		populateMerchant();
		magician = new ArrayList<Magician>();
		populateMagician();

		smallbottles = new ArrayList<smallbottle>();
		populateSmallBottles();
		bigbottles = new ArrayList<bigbottle>();
		populateBigBottles();
		speedbottles = new ArrayList<speedbottle>();
		populateSpeedBottles();
		apples = new ArrayList<apple>();
		populateApple();

		red = new ArrayList<Red>();
		populateRed();

		green = new ArrayList<Green>();
		populateGreen();

		blue = new ArrayList<Blue>();
		populateBlue();

		yellow = new ArrayList<Yellow>();
		populateYellow();

		rings = new ArrayList<Ring>();
		populateRing();

		checkpoint = new ArrayList<CheckPoint>();
		populateCheckPoints();

		runes = new ArrayList<Rune>();
		populateRunes();

		// energy particle
		energyParticles = new ArrayList<EnergyParticle>();

		// init player
		player.init(magician,merchant, smallbottles, bigbottles, speedbottles, apples, yellow, blue, green, red, rings, checkpoint, runes, coins, enemies, energyParticles);

		// explosions
		explosions = new ArrayList<Explosion>();

		// hud
		hud = new HUD(player);

		// title and subtitle
		try {
			hageonText = ImageIO.read(
				getClass().getResourceAsStream("/HUD/HageonTemple.gif")
			);
			title = new Title(hageonText.getSubimage(0, 0, 178, 20));
			title.sety(60);
			subtitle = new Title(hageonText.getSubimage(0, 20, 82, 13));
			subtitle.sety(85);
		}
		catch(Exception e) {
			e.printStackTrace();
		}

		// teleport
		teleport = new Teleport(tileMap);
	//	teleport.setPosition(100, 200);

		// start event
		eventStart = true;
		tb = new ArrayList<Rectangle>();
		eventStart();

		// sfx
		JukeBox.load("/SFX/teleport.mp3", "teleport");
		JukeBox.load("/SFX/explode.mp3", "explode");
		JukeBox.load("/SFX/enemyhit.mp3", "enemyhit");

		// music
		JukeBox.load("/Music/level1.mp3", "level1");
		JukeBox.loop("level1", 600, JukeBox.getFrames("level1") - 2200);

	}

	private void populateEnemies() {
		enemies.clear();
		GelPop gp;
		Gazer g;
		Tengu t;

		gp = new GelPop(tileMap, player);
		gp.setPosition(1400, 375);
		enemies.add(gp);
		gp = new GelPop(tileMap, player);
		gp.setPosition(3270, 236);
		enemies.add(gp);

		g = new Gazer(tileMap);
		g.setPosition(1960, 180);
		enemies.add(g);
		g = new Gazer(tileMap);
		g.setPosition(1775, 280);
		enemies.add(g);
		g = new Gazer(tileMap);
		g.setPosition(3010, 280);
		enemies.add(g);
		g = new Gazer(tileMap);
		g.setPosition(3093, 230);
		enemies.add(g);
		g = new Gazer(tileMap);
		g.setPosition(3500, 218);
		enemies.add(g);

		t = new Tengu(tileMap, player, enemies);
		t.setPosition(2770, 364);
		enemies.add(t);
		t = new Tengu(tileMap, player, enemies);
		t.setPosition(2810, 364);
		enemies.add(t);
		t = new Tengu(tileMap, player, enemies);
		t.setPosition(2850, 364);
		enemies.add(t);

	}

	private void populateCoins() {
		coins.clear();
		Coin c;

		c = new Coin(tileMap, player);
		c.setPosition(866, 350);
		coins.add(c);
		c = new Coin(tileMap, player);
		c.setPosition(885, 350);
		coins.add(c);
		c = new Coin(tileMap, player);
		c.setPosition(905, 350);
		coins.add(c);
		c = new Coin(tileMap, player);
		c.setPosition(925, 350);
		coins.add(c);
		c = new Coin(tileMap, player);
		c.setPosition(945, 350);
		coins.add(c);
		c = new Coin(tileMap, player);
		c.setPosition(960, 350);
		coins.add(c);

		c = new Coin(tileMap, player);
		c.setPosition(1027, 316);
		coins.add(c);
		c = new Coin(tileMap, player);
		c.setPosition(1047, 316);
		coins.add(c);


	}

	private void populateRunes() {
		runes.clear();
		Rune r;

		r = new Rune(tileMap, player);
		r.setPosition(1262, 350);
		runes.add(r);


	}

	private void populateCheckPoints() {
		checkpoint.clear();
		CheckPoint c;

		/*c = new CheckPoint(tileMap, player);
		c.setPosition(550, 360);
		checkpoint.add(c);*/

	}

	private void populateRing() {
		rings.clear();
		Ring c;

		c = new Ring(tileMap, player);
		c.setPosition(590, 370);
		rings.add(c);

	}

	private void populateRed() {
		red.clear();
		Red c;

		c = new Red(tileMap, player);
		c.setPosition(610, 370);
		red.add(c);

	}

	private void populateGreen() {
		green.clear();
		Green c;

		c = new Green(tileMap, player);
		c.setPosition(630, 370);
		green.add(c);

	}

	private void populateBlue() {
		blue.clear();
		Blue c;

		c = new Blue(tileMap, player);
		c.setPosition(650, 370);
		blue.add(c);

	}

	private void populateYellow() {
		yellow.clear();
		Yellow c;

		c = new Yellow(tileMap, player);
		c.setPosition(670, 370);
		yellow.add(c);

	}

	private void populateSmallBottles() {
		smallbottles.clear();
		smallbottle c;

		c = new smallbottle(tileMap, player);
		c.setPosition(800, 370);
		smallbottles.add(c);

	}

	private void populateBigBottles() {
		bigbottles.clear();
		bigbottle c;

		c = new bigbottle(tileMap, player);
		c.setPosition(850, 370);
		bigbottles.add(c);

	}

	private void populateSpeedBottles() {
		speedbottles.clear();
		speedbottle c;

		c = new speedbottle(tileMap, player);
		c.setPosition(900, 370);
		speedbottles.add(c);

	}

	private void populateApple() {
		apples.clear();
		apple c;

		c = new apple(tileMap, player);
		c.setPosition(950, 370);
		apples.add(c);

	}

	private void populateMerchant() {
		merchant.clear();
		Merchant c;

		c = new Merchant(tileMap, player);
		c.setPosition(400, 375);
		merchant.add(c);

	}

	private void populateMagician() {
		magician.clear();
		Magician c;

		c = new Magician(tileMap, player);
		c.setPosition(2255, 88);
		magician.add(c);

	}

	public void update() {

		// check keys
		handleInput();

		//System.out.println("x   " + player.getx() + "y    " + player.gety());
		/*if( player.getx() >= 95 && player.getx() <=  104) {
			count++;
			JukeBox.play("teleport");
			player.setTeleporting(true);
			player.stop();
			if(count == 120) {
				tb.clear();
				tb.add(new Rectangle(
						GamePanel.WIDTH / 2, GamePanel.HEIGHT / 2, 0, 0));
			}
			else if(count > 120) {
				tb.get(0).x -= 6;
				tb.get(0).y -= 4;
				tb.get(0).width += 12;
				tb.get(0).height += 8;
				JukeBox.stop("teleport");
			}
			if(count >= 130) {
				PlayerSave.setHealth(player.getHealth());
				PlayerSave.setLives(player.getLives());
				PlayerSave.setTime(player.getTime());
				gsm.setState(GameStateManager.LEVEL1BSTATE);
			}
		}*/

		// check if end of level event should start
		if(teleport.contains(player)) {
			eventFinish = blockInput = true;
		}

		// check if player dead event should start
		if(player.getHealth() == 0 || player.gety() > tileMap.getHeight()) {
			eventDead = blockInput = true;
		}

		// play events
		if(eventStart) eventStart();
		if(eventDead) eventDead();
		if(eventFinish) eventFinish();

		// move title and subtitle
		if(title != null) {
			title.update();
			if(title.shouldRemove()) title = null;
		}
		if(subtitle != null) {
			subtitle.update();
			if(subtitle.shouldRemove()) subtitle = null;
		}

		// move backgrounds
		//clouds.setPosition(tileMap.getx(), tileMap.gety());
		mountains.setPosition(tileMap.getx(), tileMap.gety());

		// update player
		player.update();

		// update tilemap
		tileMap.setPosition(
			GamePanel.WIDTH / 2 - player.getx(),
			GamePanel.HEIGHT / 2 - player.gety()
		);
		tileMap.update();
		tileMap.fixBounds();

		// update enemies
		for(int i = 0; i < enemies.size(); i++) {
			Enemy e = enemies.get(i);
			e.update();
			if(e.isDead()) {
				enemies.remove(i);
				i--;
				explosions.add(new Explosion(tileMap, e.getx(), e.gety()));
			}
		}

		for(int i = 0; i < coins.size(); i++) {
			Coin c = coins.get(i);
			c.update();
			if(c.isDead()) {
				coins.remove(i);
				i--;
				explosions.add(new Explosion(tileMap, c.getx(), c.gety()));
			}
		}

		for(int i = 0; i < merchant.size(); i++) {
			Merchant c = merchant.get(i);
			c.update();
		}

		for(int i = 0; i < magician.size(); i++) {
			Magician c = magician.get(i);
			c.update();
		}

		for(int i = 0; i < smallbottles.size(); i++) {
			smallbottle c = smallbottles.get(i);
			c.update();
			if(c.isDead()) {
				smallbottles.remove(i);
				i--;
				explosions.add(new Explosion(tileMap, c.getx(), c.gety()));
			}
		}

		for(int i = 0; i < bigbottles.size(); i++) {
			bigbottle c = bigbottles.get(i);
			c.update();
			if(c.isDead()) {
				bigbottles.remove(i);
				i--;
				explosions.add(new Explosion(tileMap, c.getx(), c.gety()));
			}
		}

		for(int i = 0; i < speedbottles.size(); i++) {
			speedbottle c = speedbottles.get(i);
			c.update();
			if(c.isDead()) {
				speedbottles.remove(i);
				i--;
				explosions.add(new Explosion(tileMap, c.getx(), c.gety()));
			}
		}

		for(int i = 0; i < apples.size(); i++) {
			apple c = apples.get(i);
			c.update();
			if(c.isDead()) {
				apples.remove(i);
				i--;
				explosions.add(new Explosion(tileMap, c.getx(), c.gety()));
			}
		}

		for(int i = 0; i < rings.size(); i++) {
			Ring c = rings.get(i);
			c.update();
			if(c.isDead()) {
				rings.remove(i);
				i--;
				explosions.add(new Explosion(tileMap, c.getx(), c.gety()));
			}
		}

		for(int i = 0; i < red.size(); i++) {
			Red c = red.get(i);
			c.update();
			if(c.isDead()) {
				red.remove(i);
				i--;
				explosions.add(new Explosion(tileMap, c.getx(), c.gety()));
			}
		}

		for(int i = 0; i < green.size(); i++) {
			Green c = green.get(i);
			c.update();
			if(c.isDead()) {
				green.remove(i);
				i--;
				explosions.add(new Explosion(tileMap, c.getx(), c.gety()));
			}
		}

		for(int i = 0; i < blue.size(); i++) {
			Blue c = blue.get(i);
			c.update();
			if(c.isDead()) {
				blue.remove(i);
				i--;
				explosions.add(new Explosion(tileMap, c.getx(), c.gety()));
			}
		}

		for(int i = 0; i < yellow.size(); i++) {
			Yellow c = yellow.get(i);
			c.update();
			if(c.isDead()) {
				yellow.remove(i);
				i--;
				explosions.add(new Explosion(tileMap, c.getx(), c.gety()));
			}
		}

		for(int i = 0; i < runes.size(); i++) {
			Rune c = runes.get(i);
			c.update();
			if(c.isDead()) {
				runes.remove(i);
				i--;
				explosions.add(new Explosion(tileMap, c.getx(), c.gety()));
			}
		}

		for(int i = 0; i < checkpoint.size(); i++) {
			CheckPoint c = checkpoint.get(i);
			c.update();
			if(c.isDead()) {
				//checkpoint.remove(i);
				i--;
				explosions.add(new Explosion(tileMap, c.getx(), c.gety()));
			}
		}

		// update enemy projectiles
		for(int i = 0; i < eprojectiles.size(); i++) {
			EnemyProjectile ep = eprojectiles.get(i);
			ep.update();
			if(ep.shouldRemove()) {
				eprojectiles.remove(i);
				i--;
			}
		}

		// update explosions
		for(int i = 0; i < explosions.size(); i++) {
			explosions.get(i).update();
			if(explosions.get(i).shouldRemove()) {
				explosions.remove(i);
				i--;
			}
		}

		// update teleport
		teleport.update();

	}

	public void draw(Graphics2D g) {

		// draw background
	//	sky.draw(g);
		//clouds.draw(g);
		mountains.draw(g);

		// draw tilemap
		tileMap.draw(g);

	//	if(player.getCoinCount() >= 5) System.out.println("lol");

		// draw enemies
		for(int i = 0; i < enemies.size(); i++) {
			enemies.get(i).draw(g);
		}



		for(int i = 0; i < coins.size(); i++) {
			coins.get(i).draw(g);
		}

		for(int i = 0; i < merchant.size(); i++) {
			merchant.get(i).draw(g);
		}
		for(int i = 0; i < magician.size(); i++) {
			magician.get(i).draw(g);
		}

		for(int i = 0; i < smallbottles.size(); i++) {
			smallbottles.get(i).draw(g);
		}

		for(int i = 0; i < bigbottles.size(); i++) {
			bigbottles.get(i).draw(g);
		}

		for(int i = 0; i < speedbottles.size(); i++) {
			speedbottles.get(i).draw(g);
		}

		for(int i = 0; i < apples.size(); i++) {
			apples.get(i).draw(g);
		}

		for(int i = 0; i < red.size(); i++) {
			red.get(i).draw(g);
		}

		for(int i = 0; i < green.size(); i++) {
			green.get(i).draw(g);
		}

		for(int i = 0; i < blue.size(); i++) {
			blue.get(i).draw(g);
		}

		for(int i = 0; i < yellow.size(); i++) {
			yellow.get(i).draw(g);
		}

		for(int i = 0; i < red.size(); i++) {
			red.get(i).draw(g);
		}

		for(int i = 0; i < rings.size(); i++) {
			rings.get(i).draw(g);
		}

		for(int i = 0; i < checkpoint.size(); i++) {
			checkpoint.get(i).draw(g);
		}

		for(int i = 0; i < runes.size(); i++) {
			runes.get(i).draw(g);
		}

		// draw enemy projectiles
		for(int i = 0; i < eprojectiles.size(); i++) {
			eprojectiles.get(i).draw(g);
		}

		// draw explosions
		for(int i = 0; i < explosions.size(); i++) {
			explosions.get(i).draw(g);
		}

		// draw player
		player.draw(g);

		// draw teleport
		teleport.draw(g);

		// draw hud
		hud.draw(g);

		// draw title
		if(title != null) title.draw(g);
		if(subtitle != null) subtitle.draw(g);

		// draw transition boxes
		g.setColor(java.awt.Color.BLACK);
		for(int i = 0; i < tb.size(); i++) {
			g.fill(tb.get(i));
		}



	}

	public void handleInput() {
		if(Keys.isPressed(Keys.ESCAPE)) gsm.setPaused(true);
		if(blockInput || player.getHealth() == 0) return;
		player.setUp(Keys.keyState[Keys.UP]);
		player.setLeft(Keys.keyState[Keys.LEFT]);
		player.setDown(Keys.keyState[Keys.DOWN]);
		player.setRight(Keys.keyState[Keys.RIGHT]);
		player.setJumping(Keys.keyState[Keys.BUTTON1]);
		player.setDashing(Keys.keyState[Keys.BUTTON2]);
		if(Keys.isPressed(Keys.BUTTON3)) player.setAttacking();
		if(Keys.isPressed(Keys.BUTTON4)) player.setCharging();
		if(Keys.isPressed(Keys.TALKTOM)) {

		}
		if(Keys.isPressed(Keys.UPSPEED)) {
			if(player.getSpeedbottleCount() > 0){
				player.setSpeedbottleCount(player.getSpeedbottleCount() - 1);
				player.setUpspeed(true);

			}
		}

		if(Keys.isPressed(Keys.UNHITTABLE)) {
			if(player.getSmallbottleCount() > 0) {
				player.setHittable(false);
				player.setSmallbottleCount(player.getSmallbottleCount() - 1);
			}
		}

		if(Keys.isPressed(Keys.BHEAL)) {
			if (player.getBigbottleCount() > 0) {
				if (player.getHealth() >= 10) {
					player.setHealth(10);
				} else if (player.getHealth() >= 9) {
					player.setHealth(player.getHealth() + 1);
					player.setBigbottleCount(player.getBigbottleCount() - 1);
				} else {
					player.setHealth(player.getHealth() + 2);
					player.setBigbottleCount(player.getBigbottleCount() - 1);
				}
			}
		}

		if(Keys.isPressed(Keys.APPLE)) {
			if (player.getAppleCount() > 0) {
				if (player.getHealth() >= 10) {
					player.setHealth(10);
				} else {
					player.setHealth(player.getHealth() + 1);
					player.setAppleCount(player.getAppleCount() - 1);
				}
			}
		}


	}

///////////////////////////////////////////////////////
//////////////////// EVENTS
///////////////////////////////////////////////////////

	// reset level
	private void reset() {
		player.reset();
		player.setPosition(player.getCurrentX(), player.getCurrentY());
		populateEnemies();
		populateCoins();
		populateRunes();
		populateRing();
		populateCheckPoints();
		player.setCoinCount(0);
		player.setRuneCount(0);
		blockInput = true;
		eventCount = 0;
		tileMap.setShaking(false, 0);
		eventStart = true;
		eventStart();
		title = new Title(hageonText.getSubimage(0, 0, 178, 20));
		title.sety(60);
		subtitle = new Title(hageonText.getSubimage(0, 33, 91, 13));
		subtitle.sety(85);
	}

	// level started
	private void eventStart() {
		eventCount++;

		if(eventCount == 1) {
			tb.clear();
			tb.add(new Rectangle(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT / 2));
			tb.add(new Rectangle(0, 0, GamePanel.WIDTH / 2, GamePanel.HEIGHT));
			tb.add(new Rectangle(0, GamePanel.HEIGHT / 2, GamePanel.WIDTH, GamePanel.HEIGHT / 2));
			tb.add(new Rectangle(GamePanel.WIDTH / 2, 0, GamePanel.WIDTH / 2, GamePanel.HEIGHT));
		}
		if(eventCount > 1 && eventCount < 60) {
			tb.get(0).height -= 4;
			tb.get(1).width -= 6;
			tb.get(2).y += 4;
			tb.get(3).x += 6;
		}
		if(eventCount == 30) title.begin();
		if(eventCount == 60) {
			eventStart = blockInput = false;
			eventCount = 0;
			subtitle.begin();
			tb.clear();
		}
	}

	// player has died
	private void eventDead() {
		eventCount++;
		if(eventCount == 1) {
			player.setDead();
			player.stop();
		}
		if(eventCount == 60) {
			tb.clear();
			tb.add(new Rectangle(
				GamePanel.WIDTH / 2, GamePanel.HEIGHT / 2, 0, 0));
		}
		else if(eventCount > 60) {
			tb.get(0).x -= 6;
			tb.get(0).y -= 4;
			tb.get(0).width += 12;
			tb.get(0).height += 8;
		}
		if(eventCount >= 120) {
			if(player.getLives() == 0) {
				gsm.setState(GameStateManager.MENUSTATE);
			}
			else {
				eventDead = blockInput = false;
				eventCount = 0;
				player.loseLife();
				reset();
			}
		}
	}

	// finished level
	private void eventFinish() {
		eventCount++;


		/*if(eventCount == 1) {
			JukeBox.play("teleport");
			player.setTeleporting(true);
			player.stop();
		}*/
		 if(eventCount == 120) {
			tb.clear();
			tb.add(new Rectangle(
				GamePanel.WIDTH / 2, GamePanel.HEIGHT / 2, 0, 0));
		}
		else if(eventCount > 120) {
			tb.get(0).x -= 6;
			tb.get(0).y -= 4;
			tb.get(0).width += 12;
			tb.get(0).height += 8;
			JukeBox.stop("teleport");
		}
		if(eventCount == 180) {
			PlayerSave.setHealth(player.getHealth());
			PlayerSave.setLives(player.getLives());
			PlayerSave.setTime(player.getTime());
			gsm.setState(GameStateManager.LEVEL1BSTATE);
		}

	}
}