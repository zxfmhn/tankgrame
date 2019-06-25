package tank;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;


public class tank extends Frame{
	private int totaltankNumber;//坦克总数
	private int alivetankNumber;//存活坦克数
	private int deadtankNumber = 0;//已摧毁坦克数
	private int maxOnBattle = 6;//战场上坦克上线
	private int onBattle = 0;//战场坦克计数器
	private int bornStep = 0;//新出现坦克计数器
	
	private Image screenImage = null;
	private PlayerTank player = new PlayerTank();
	Home home = new Home(360,720);
	protected List<Tank1> enemyTanks = new ArrayList<Tank1>();
	private List<Wall> walls = new ArrayList<Wall>(); //墙
	private List<River> rivers = new ArrayList<River>();//河
	private List<Tree> trees = new ArrayList<Tree>();//树
	private List<Blast> blast = new ArrayList<Blast>();	//爆炸类
	private List<Born> born = new ArrayList<Born>();//入场类，上限为3
	protected List<Bullet> enemyBullet = new ArrayList<Bullet>();//敌方子弹
	protected List<Bullet> myBullet = new ArrayList<Bullet>();//我方子弹
	
	public tank(int total) {//total敌方界面中最多坦克数
		if(total>20)
			total=20;
		else if(total<=0)
			total=3;
		totaltankNumber = alivetankNumber = total;//坦克的总数等于存活的坦克数等于敌方坦克总数
		initWindow();
		initWarElements();
		new Thread(new PaintThread()).start();
	}
	public void paint(Graphics g) {
		chechWin(g);
		updateNewTank();
		home.draw(g);
		for(Born b :born)
			b.draw(g);
		for(River r : rivers)
			r.draw(g);
		for(Wall w : walls)
			w.draw(g);
		for(Tank1 t : enemyTanks)
			t.draw(g);
		player.draw(g);
		for(Blast b : blast)
			b.draw(g);

		checkStarEat();
		checkEnemyBullet();
		checkMyBullet();
		//画出子弹与树叶
		for(Bullet b : enemyBullet)
			b.draw(g);
		for(Bullet b : myBullet)
			b.draw(g);
		for(Tree t :trees)
			t.draw(g);
		//更新战场上坦克的数据
		deadtankNumber = checkEnemyTankCollide();
		alivetankNumber = totaltankNumber - deadtankNumber;
		checkPlayerTankCollide(player);
	}
	public void update(Graphics g) 
	{
		screenImage = this.createImage(Constant.FRAME_WIDTH);
		Graphics gsi =screenImage.getGraphics();
		Color c = gsi.getColor();
		gsi.setColor(Color.BLACK);
		gsi.fillRect(0, 0, Constant.FRAME_WIDTH, Constant.FRAME_LENGTH);
		gsi.setColor(c);
		paint(gsi);
		g.drawImage(screenImage,0,26,Constant.FRAME_WIDTH,Constant.FRAME_LENGTH,null);
		
		Image numberImage = this.createImage(110,310);
		Graphics gni = numberImage.getGraphics();
		int miniX = 0,miniY = 0;
		for(int i = 0;i < alivetankNumber;i++)
		{
			gni.drawImage(Constant.MINI_TANK_IMAGE, miniX + i % 3 * 40, miniY + i / 3 *40, null);		
		}
		g.drawImage(numberImage, Constant.FRAME_WIDTH + 25, 450, null);	
	 }
	

	
	private Image createImage(int frameWidth) {
		// TODO Auto-generated method stub
		return null;
	}
	private void checkPlayerTankCollide(PlayerTank player2) {
		// TODO Auto-generated method stub
		
	}
	private int checkEnemyTankCollide() {
		// TODO Auto-generated method stub
		return 0;
	}
	private void checkMyBullet() {
		// TODO Auto-generated method stub
		for(Bullet b : myBullet)
		{
			if(!b.isLive())
				continue;
			
			if(b.hitHome(home))
			{
				b.setLive(false);
				home.setLive(false);
				break;
			}
			else
			{
				for(Wall w : walls)
				{
					if(!w.isLive())
						continue;
					if(b.hitWall(w))
					{
						b.setLive(false);
						if(b.getLevel() >= w.getLevel())
							w.setLive(false);
						break;
					}
				}
				
				if(b.isLive())
				{
					for(Tank1 t : enemyTanks)
					{
						if(!t.isLive())
							continue;
						if(b.hitTank(t))
						{
							b.setLive(false);
							t.beHitted();
							if(!t.isLive())
							{
								Constant.BLAST_SOUND.play();
								blast.add(new Blast(t.getX(),t.getY()));
								onBattle--;
							}
							break;
						}
					}
				}
			}
		}
	}
	//判断敌方子弹的射中情况
	private void checkEnemyBullet() {
		// TODO Auto-generated method stub
		for(Bullet b : enemyBullet)
		{
		//判断子弹是否存在
		if(!b.isLive())
			continue;
		
		if(b.hitHome(home))
		{
			b.setLive(false);
			home.setLive(false);
			break;
		}
		else if(b.hitTank(player)) {
			b.setLive(false);
			player.beHitted();
			if(!player.isLive())
				blast.add(new Blast(player.getX(),player.getY()));
		}
		else
		{
			for(Wall w : walls)
			{
				if(!w.isLive())
					continue;
				if(b.hitWall(w))
				{
					b.setLive(false);
					if(b.getLevel() >= w.getLevel())
						w.setLive(false);
					break;
				}
			}
		}
		}
}
		
	private void checkStarEat() {
		// TODO Auto-generated method stub
		
	}
	private void updateNewTank() {
		// TODO Auto-generated method stub
		
	}
	private void chechWin(Graphics g) {
		// TODO Auto-generated method stub
		
	}
	//定义游戏界面
	private void initWarElements() {
		// TODO Auto-generated method stub
		//添加游戏界面中的河
		for(int i = 180; i<= 420;i += 60)
			rivers.add(new River(0,i));
		rivers.add(new River(540,600));
		rivers.add(new River(720,600));
		rivers.add(new River(720,600));
		rivers.add(new River(720,600));
		//添加墙
		for(int i = 0;i < 2;i++)
			for(int j = 60;j <= 210;j += 30)
				walls.add(new Wall(j,720 + i * 30,Constant.NORMAL_WALL));
		for(int i = 0;i < 2;i++)
			for(int j = 120;j <= 240;j += 30)
				walls.add(new Wall(j,480 + i * 30,Constant.NORMAL_WALL));
		for(int i = 0;i < 2;i++)
			for(int j = 600;j <= 690;j += 30)
				walls.add(new Wall(0 + i * 30,j,Constant.NORMAL_WALL));
		for(int i = 0;i < 2;i++)
			for(int j = 540;j <= 630;j += 30)
				walls.add(new Wall(j,720 + i * 30,Constant.NORMAL_WALL));
		for(int i = 0;i < 2;i++)
			for(int j = 420;j <= 510;j += 30)
				walls.add(new Wall(j,480 + i * 120,Constant.NORMAL_WALL));
		for(int i = 330;i <= 420;i += 30)
			walls.add(new Wall(i,690,Constant.NORMAL_WALL));
		for(int i = 0;i < 2;i++)
			for(int j = 720;j <= 750;j += 30)
				walls.add(new Wall(330 + i * 90,j,Constant.NORMAL_WALL));
		for(int i = 570;i <= 750;i += 30)
			walls.add(new Wall(i,210,Constant.NORMAL_WALL));
		
		//添加树
		for(int i = 60;i <= 300;i += 60)
			trees.add(new Tree(120,i));
		for(int i = 60;i <= 300;i += 60)
			trees.add(new Tree(180,i));
		for(int i = 540;i <= 720;i += 60)
			trees.add(new Tree(i,480));
		trees.add(new Tree(600,600));
		trees.add(new Tree(660,600));
		for(int i = 540;i <= 660;i += 60)
			trees.add(new Tree(i,660));	
	}

	private void initWindow() {
		// TODO Auto-generated method stub
		Image introImage = this.createImage(200,Constant.FRAME_LENGTH);
		Graphics gps = introImage.getGraphics();
		gps.setFont(new Font("TimesRoman",Font.BOLD,15));;
		gps.drawString("Press F1 to restart", 10, 40);
		gps.drawString("Player:", 10, 110);
		gps.drawString("Up: W      Down: S", 10, 140);
		gps.drawString("Left: A    Right: D", 10, 170);
		gps.drawString("Fire: Space", 10, 200);
		gps.drawString("Remain Tanks:", 10, 400);
		this.getGraphics().drawImage(introImage, 780, 30, null);
		//关闭窗口监听器
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
			});
		//监听键盘
		this.addKeyListener(new KeyAdapter()
		{
			public void keyPressed(KeyEvent e)
			{
				player.keyPressed(e);
				clientKeyPressed(e);
			}
			public void keyReleased(KeyEvent e)
			{
				player.keyPressed(e);
			}	
		});
	
	}
	private void checkWin(Graphics g)
	{
		Color c = g.getColor();
		g.setColor(Color.RED);
		if(deadtankNumber >= totaltankNumber && home.islive() && checkPlayeLife())
		{
			Font f = g.getFont();
			g.setFont(new Font("timeRoman",Font.BOLD,60));
			g.drawString("GrameWIN!!", 310, 300);
			g.setFont(f);
			clean();
		}
		else if(!home.islive() || !checkPlayerLife())
		{
			Font f = g.getFont();
			g.setFont(new Font("TimeRoman",Font.BOLD,60));
			g.drawString("GRAM OVER!!!", 260, 300);
			g.setFont(f);
			clean();
		}
		g.setColor(c);
	}
	private boolean checkPlayerLife() {
		// TODO Auto-generated method stub
		return false;
	}
	private void clean() {
		// TODO Auto-generated method stub
		
	}
	private boolean checkPlayeLife() {
		// TODO Auto-generated method stub
		return false;
	}
	protected void clientKeyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	public class PaintThread implements Runnable
	{
		public void run()
		{
			while(true)
			{
				repaint();
				try
				{
					Thread.sleep(50);
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}	
	}
}
