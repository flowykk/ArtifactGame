package com.neet.Entity;

import com.neet.Handlers.Keys;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class HUD {
	
	private Player player;
	
	private BufferedImage heart;
	private BufferedImage life;
	private BufferedImage empty;
	private BufferedImage right;
	private BufferedImage left;
	private BufferedImage coin;
	private BufferedImage rune;
	private BufferedImage rinventory;
	private BufferedImage c1inventory, c2inventory, c3inventory, cinventory;
	private BufferedImage linventory;
	private BufferedImage Bcoin;
	private BufferedImage Erune;
	private BufferedImage strength, reducing, demonmode, speed, poisoning, godmode, Earmor, Einvisibilty;

	private BufferedImage ering;
	private BufferedImage ered;
	private BufferedImage egreen;
	private BufferedImage eblue;
	private BufferedImage eyellow;

	private BufferedImage Espeed;
	private BufferedImage Epoisoning, w1;

	private BufferedImage ring, book, unhittable;
	private BufferedImage red, Linventory, Rinventory;
	private BufferedImage green, apple, Cinventory;
	private BufferedImage blue, smallbottle, bigbottle;
	private BufferedImage yellow, Gm, speedbottle;

	private Font font;
	
	public HUD(Player p) {
		player = p;
		try {
			BufferedImage image = ImageIO.read(getClass().getResourceAsStream("/HUD/bars.png"));
			BufferedImage image1 = ImageIO.read(getClass().getResourceAsStream("/HUD/Hud.gif"));
			BufferedImage coin1 = ImageIO.read(getClass().getResourceAsStream("/Bonuses/Coin.png"));
			BufferedImage runes = ImageIO.read(getClass().getResourceAsStream("/Bonuses/Rune.png"));
			BufferedImage emptyrune = ImageIO.read(getClass().getResourceAsStream("/Bonuses/emptyR.png"));
			BufferedImage inventory = ImageIO.read(getClass().getResourceAsStream("/HUD/inventory.png"));
			BufferedImage bcoin = ImageIO.read(getClass().getResourceAsStream("/Bonuses/BCoin.png"));
			BufferedImage icons = ImageIO.read(getClass().getResourceAsStream("/HUD/icons.png"));
			BufferedImage godem = ImageIO.read(getClass().getResourceAsStream("/Bonuses/emptyG.png"));
			BufferedImage GM = ImageIO.read(getClass().getResourceAsStream("/Bonuses/GodMode.png"));

			BufferedImage Ering = ImageIO.read(getClass().getResourceAsStream("/Bonuses/emptyRing.png"));
			BufferedImage Ered = ImageIO.read(getClass().getResourceAsStream("/Bonuses/emptyRed.png"));
			BufferedImage Egreen = ImageIO.read(getClass().getResourceAsStream("/Bonuses/emptyGreen.png"));
			BufferedImage Eblue = ImageIO.read(getClass().getResourceAsStream("/Bonuses/emptyBlue.png"));
			BufferedImage Eyellow = ImageIO.read(getClass().getResourceAsStream("/Bonuses/emptyYellow.png"));

			BufferedImage Ring = ImageIO.read(getClass().getResourceAsStream("/Bonuses/Ring.png"));
			BufferedImage Red = ImageIO.read(getClass().getResourceAsStream("/Bonuses/Red.png"));
			BufferedImage Green = ImageIO.read(getClass().getResourceAsStream("/Bonuses/Green.png"));
			BufferedImage Blue = ImageIO.read(getClass().getResourceAsStream("/Bonuses/Blue.png"));
			BufferedImage Yellow = ImageIO.read(getClass().getResourceAsStream("/Bonuses/Yellow.png"));
			BufferedImage Book = ImageIO.read(getClass().getResourceAsStream("/Bonuses/book.png"));

			BufferedImage sprite = ImageIO.read(getClass().getResourceAsStream("/HUD/spritesheet.png"));
			BufferedImage abilities = ImageIO.read(getClass().getResourceAsStream("/HUD/abilities.png"));

			BufferedImage Books = ImageIO.read(getClass().getResourceAsStream("/HUD/Books.png"));
			BufferedImage window1 = ImageIO.read(getClass().getResourceAsStream("/HUD/dwindow.png"));


			//heart = image.getSubimage(0, 0, 13, 12);
			life = image1.getSubimage(0, 12, 12, 11);
			right = image.getSubimage(0, 0, 9, 20);
			left = image.getSubimage(9, 0, 9, 20);
			empty = image.getSubimage(23, 20, 7, 14);
			heart = image.getSubimage(9, 21, 7, 14);
			coin = coin1.getSubimage(0,0,10,10);
			Bcoin = bcoin.getSubimage(0,0,14,16);
			rune = runes.getSubimage(0,0,16,16);
			Erune = emptyrune.getSubimage(0,0,11,14);

			cinventory = inventory.getSubimage(0,0,24,24);
			rinventory = inventory.getSubimage(0,0,24,36);
			c1inventory = inventory.getSubimage(24,0,24,36);
			c2inventory = inventory.getSubimage(48,0,24,36);
			c3inventory = inventory.getSubimage(72,0,24,36);
			linventory = inventory.getSubimage(96,0,24,36);

			Linventory = inventory.getSubimage(0,0,24,24);
			Cinventory = inventory.getSubimage(24,0,24,24);
			Rinventory = inventory.getSubimage(96,0,24,24);

			ering = Ering.getSubimage(0,0,16,16);
			ered = Ered.getSubimage(0,0,16,16);
			egreen = Egreen.getSubimage(0,0,16,16);
			eblue = Eblue.getSubimage(0,0,16,16);
			eyellow = Eyellow.getSubimage(0,0,16,16);
			//ebook

			godmode = godem.getSubimage(0,0,16,16);
			Gm = GM.getSubimage(0,0,16,16);

			ring = Ring.getSubimage(0,0,16,16);
			red = Red.getSubimage(0,0,16,16);
			green = Green.getSubimage(0,0,16,16);
			blue = Blue.getSubimage(0,0,16,16);
			yellow = Yellow.getSubimage(0,0,16,16);
			book = Book.getSubimage(0,0,16,16);

			smallbottle = sprite.getSubimage(16, 96, 16, 16);
			bigbottle = sprite.getSubimage(80, 240, 16, 16);
			speedbottle = sprite.getSubimage(16, 240, 16, 16);
			apple = sprite.getSubimage(48, 144, 16, 16);

			Espeed = abilities.getSubimage(0,16,16,16);
			Epoisoning = abilities.getSubimage(16,16,16,16);
			Earmor = abilities.getSubimage(48,16,16,16);
			Einvisibilty = abilities.getSubimage(32,16,16,16);

		//	speed = abilities.getSubimage(0,0,16,16);
			//poisoning = abilities.getSubimage(16,0,16,16);

			speed = icons.getSubimage(416,96,32,32);
			unhittable  = icons.getSubimage(448,96,32,32);
			poisoning = icons.getSubimage(16,0,16,16);

			w1 = window1.getSubimage(0,0,464,272);

			//life = image.getSubimage(7, 0, 7, 14);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g) {
		g.drawImage(right, 10, 10, null);
		g.drawImage(left, 89, 10, null);
		//g.drawImage(coin, 290, 20, null);

		g.drawImage(Cinventory, 110, 8, null);

		g.drawImage(rinventory, 145, 8, null);
		g.drawImage(c1inventory, 169, 8, null);
		g.drawImage(c2inventory, 193, 8, null);
		g.drawImage(c3inventory, 217, 8, null);
		g.drawImage(linventory, 241, 8, null);


		g.drawImage(Linventory, 276, 8, null);
		g.drawImage(Cinventory, 300, 8, null);
		g.drawImage(Rinventory, 324, 8, null);

		if(player.isUpspeed()) {
			//g.drawImage(Espeed, 295, 30, null);

			g.drawImage(speed, 360, 5, null);
			//g.drawImage(speed, 360, 5, null);
		}

		if(!player.isHittable()) {
			//g.drawImage(Espeed, 295, 30, null);

			g.drawImage(unhittable, 400, 5, null);
			//g.drawImage(unhittable, 360, 5, null);
		}

	/*	g.drawImage(Epoisoning, 295, 55, null);
		g.drawImage(Earmor, 295, 70, null);
		g.drawImage(Einvisibilty, 295, 85, null);*/



		for(int i = 0; i < 10; i++) {
			g.drawImage(empty, 19 + i * 7, 13, null);
		}
		for(int i = 0; i < player.getHealth(); i++) {
			g.drawImage(heart, 19 + i * 7, 13, null);
		}
		for(int i = 0; i < player.getLives(); i++) {
			//g.drawImage(life, 10 + i * 15, 25, null);
		}

		for(int i = 0; i < 3; i++) {
			g.drawImage(Erune, 16 + i * 15, 35, null);
		}

		g.drawImage(godmode, 14, 55, null);
		if(player.getRingClaimed() + player.getRedClaimed() + player.getGreenClaimed() + player.getBlueClaimed() + player.getYellowClaimed() == 5) {
			g.drawImage(Gm, 14, 55, null);
		}

		g.drawImage(ering, 274, 35, null);
		for(int i = 0; i < player.getRingClaimed(); i++) {
			g.drawImage(ring, 274, 35, null);
		}

		g.drawImage(ered, 289, 35, null);
		for(int i = 0; i < player.getRedClaimed(); i++) {
			g.drawImage(red, 289, 35, null);
		}

		g.drawImage(egreen, 304, 35, null);
		for(int i = 0; i < player.getGreenClaimed(); i++) {
			g.drawImage(green, 304, 35, null);
		}

		g.drawImage(eblue, 319, 35, null);
		for(int i = 0; i < player.getBlueClaimed(); i++) {
			g.drawImage(blue, 319, 35, null);
		}

		g.drawImage(eyellow, 334, 35, null);
		for(int i = 0; i < player.getYellowClaimed(); i++) {
			g.drawImage(yellow, 334, 35, null);
		}

		for(int i = 0; i < player.getRuneCount(); i++) {
			g.drawImage(rune, 14 + i * 15, 33, null);
		}

		if(Keys.isPressed(Keys.BUTTON4)) player.setCharging();

		g.setFont(font = new Font("progresspixel", Font.PLAIN, 10));
		g.setColor(java.awt.Color.WHITE);
	//	g.drawString(player.getTimeToString(), 290, 15);
		if (player.getCoinCount() < 10 && player.getCoinCount() >0) {g.drawImage(Bcoin, 110, 10, null); g.drawString(Integer.toString(player.getCoinCount()), 125, 28);}
		else if (player.getCoinCount() >= 10){ g.drawImage(Bcoin, 110, 10, null); g.drawString(Integer.toString(player.getCoinCount()), 120, 28);}

		if (player.getSmallbottleCount() < 10 && player.getSmallbottleCount() >0) {
			g.drawImage(smallbottle, 148, 11, null);
			g.drawString(Integer.toString(player.getSmallbottleCount()), 160, 28);
		}
		else if (player.getSmallbottleCount() >= 10) {
			g.drawImage(smallbottle, 148, 11, null);
			g.drawString(Integer.toString(player.getSmallbottleCount()), 155, 28);
		}

		if (player.getBigbottleCount() < 10 && player.getBigbottleCount() >0) {
			g.drawImage(bigbottle, 172, 11, null);
			g.drawString(Integer.toString(player.getBigbottleCount()), 185, 28);
		}
		else if (player.getBigbottleCount() >= 10) {
			g.drawImage(bigbottle, 172, 11, null);
			g.drawString(Integer.toString(player.getBigbottleCount()), 180, 28);
		}

		if (player.getAppleCount() < 10 && player.getAppleCount() >0) {
			g.drawImage(apple, 196, 11, null);
			g.drawString(Integer.toString(player.getAppleCount()), 209, 28);
		}
		else if (player.getAppleCount() >= 10) {
			g.drawImage(apple, 196, 11, null);
			g.drawString(Integer.toString(player.getAppleCount()), 204, 28);
		}

		if (player.getSpeedbottleCount() < 10 && player.getSpeedbottleCount() >0) {
			g.drawImage(speedbottle, 220, 11, null);
			g.drawString(Integer.toString(player.getSpeedbottleCount()), 233, 28);
		}
		else if (player.getSpeedbottleCount() >= 10) {
			g.drawImage(speedbottle, 220, 11, null);
			g.drawString(Integer.toString(player.getSpeedbottleCount()), 228, 28);
		}

		g.setFont(font = new Font("progresspixel", Font.PLAIN, 13));
		g.setColor(java.awt.Color.WHITE);
		if (player.isTralkingToMerchant()) {
			g.drawImage(w1, 64, 64, null);
			g.drawString("Привет, путешественник! Меня зовут Боб! Ты очередной новенький??", 80,95);
			g.drawString("Таких в последнее время много... Но почти никто не заканчивает", 80,112);
			g.drawString("начатое путешествие!", 80,129);

			g.drawString("Итак, начнём! Первое задание, которое тебе предстоит вполнить это..", 80,159);
			g.drawString("Просто дойти до Старика-Мага! На пути тебя ждёт несколько врагов!", 80,176);

			g.drawString("попробуй собрать на своём пути побольше монеток, они тебе", 80,193);
			g.drawString("обязательно понадобятся! А также собирай зелёные Руны, их", 80,209);
			g.drawString("ты можешь собрать максимум 3 штуки! Их количество", 80,227);
			g.drawString("отображается у тебя слева вверху экрана! За Руны ты также", 80,243);
			g.drawString("сможешь что-то прикупить! ", 80,261);

			g.drawString("Ах да! Чуть не забыл! Для управления используй клавиши ←, →, ", 80,291);
			g.drawString("для быстрого бега - E, для атаки - R, F, для прыжка - W ", 80,308);
		}

		if (player.isTralkingToMagician()) {
			g.drawImage(w1, 64, 110, null);
			g.drawString("Привет, дружище! Меня зовут Гели! Я маг и Я достаточно известен", 80,151);
			g.drawString("в кругах этого острова! Итак, начну свой монолог... У меня ты сможешь", 80,168);
			g.drawString("купить различные зелья.", 80,185);

			g.drawString("У меня ты сможешь купить: зелье скорости, зелье сильного", 80,215);
			g.drawString("восстановления здоровья, а также конфету, позволяющую стать", 80,232);
			g.drawString("невидимым на некоторое время для врагов. Если пойдёшь дальше,", 80,249);
			g.drawString("наткнёшься на моего отца - самого великого мага этого острова!", 80,265);
			g.drawString("Мой старик продаёт сильные книги заклинаний, которые дают", 80,283);
			g.drawString("невероятные способности!", 80,299);

			g.drawString("На пути к моему отцу постарайся собрать побольше монеток и Рун!", 80,327);
		}

	//	g.drawImage(book, 209, 11, null);

	}
	
}













