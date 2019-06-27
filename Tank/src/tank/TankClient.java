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

public class TankClient extends Frame
{
	private static final long serialVersionUID = 1L;
	private int totalTankNumber;
	private int aliveTankNumber;
	private int deadTankNumber = 0;
	private int maxOnBattle = 0;
	private int onBattle = 0;//界面内的坦克计数器
	private int bornStep = 0;//界面内新出现的坦克计数器
	
	private Image screenImage = null;//画游戏界面内的游戏元素
	private PlayerOneTank playerOne = new PlayerOneTank(270,720,Constant.STOP,this,5,Constant.PLAYER_ONE);//玩家1
	private PlayerTwoTank playerTwo = null;
	private Home home = new Home(360,720);//老家
	private LevelStar star = new LevelStar(200,90);//升级星星
	
	protected List<Tank> enemyTanks = new ArrayList<Tank>();//敌方坦克
	private List<Wall> walls = new ArrayList<Wall>();//墙
	private List<River> rivers = new ArrayList<River>();//河
	private List<Tree> trees = new ArrayList<Tree>();//树
	private List<Blast> blast = new ArrayList<Blast>();	//爆炸类
	private List<Born> born = new ArrayList<Born>();//入场类，上限为3
	protected List<Bullet> enemyBullet = new ArrayList<Bullet>();//敌方子弹
	protected List<Bullet> myBullet = new ArrayList<Bullet>();//我方子弹
	 
	public TankClient(int total)
	{
		if(total>20)
			total=20;
		else if(total<=0)
			total=3;
		totalTankNumber = aliveTankNumber = total;//坦克总数等于存活的坦克数等于坦克数
	    initWindow();
	    initWarElements();
	     new Thread(new PaintThread()).start();//更新游戏界面
	}
	//界面刷新
	public void paint(Graphics g)
	{
		checkWin(g);
		updateNewTank();
		//画出游戏界面中的家，树，河
		home.draw(g);//画家
		for(Born b : born)
			b.draw(g);
		for(River r : rivers)//画河
			r.draw(g);
		for(Wall w : walls)//画墙
			w.draw(g);
		if(star !=null) //画星星
			star.draw(g);
		playerOne.draw(g);//画坦克
		if(playerTwo !=null)
			playerTwo.draw(g);
		for(Blast b : blast)//画子弹
			b.draw(g);
		for(Bullet b : enemyBullet)
			b.draw(g);
		for(Bullet b : myBullet)
			b.draw(g);
		for(Tree t : trees)//画树
			t.draw(g);
		
		checkStarEat();
		checkEnemyBullet();
		checkMyBullet();
		//更新界面内的坦克信息
		deadTankNumber = checkEnemyTankCollide();
		aliveTankNumber = totalTankNumber - deadTankNumber;
		checkPlayerTankCollide(playerOne);
		checkPlayerTankClooide(playerTwo);	
	}
	
	public void update(Graphics g)
	{
		screenImage  = this.createImage(Constant.FRAME_WIDTH,Constant.FRAME_LENGTH);
		Graphics gsi = screenImage.getGraphics();
		Color c = gsi.getColor();
		gsi.fillRect(0, 0, Constant.FRAME_WIDTH, Constant.FRAME_LENGTH);
		gsi.setColor(c);
		paint(gsi);
		g.drawImage(screenImage, 0, 26, Constant.FRAME_WIDTH,Constant.FRAME_LENGTH,null);
		
		Image numberImage = this.createImage(110, 310);
		Graphics gni = numberImage.getGraphics();
		int miniX = 0,miniY = 0;
		for(int i = 0;i< aliveTankNumber;i++)
		{
			gni.drawImage(Constant.MINI_TANK_IMAGE, miniX + i % 3 * 40,miniY + i / 3 * 40,null);
			
		}
		g.drawImage(numberImage, Constant.FRAME_WIDTH+25, 450, null);
		
	}
	

	private void checkPlayerTankClooide(PlayerTwoTank playerTwo2) {
		// TODO Auto-generated method stub
		
	}
	private void checkPlayerTankCollide(PlayerOneTank playerOne2) {
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
				for(Wall w: walls)
				{
					if(!w.isLive())
						continue;
					if(b.hitWall(w))
					{
						b.setLive(false);
						if(b.getLevel()>=w.getLevel())
							w.setLive(false);
						break;

					}
				}
				if(b.islive())
				{
					for(Tank t : enemyTanks)
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
		private void checkEnemyBullet()
		{
			for(Bullet b : enemyBullet)
			{
				//先判断子弹是否存活
				if(!b.isLive())
					continue;
				//是否集中基地
				if(b.hitHome(home))
				{
					b.setLive(false);
					home.setLive(false);
					break;
				}
				//是否集中玩家1
				else if(b.hitTank(playerOne))
				{
					b.setLive(false);
					playerOne.beHitted();
					if(!playerOne.isLive())
						blast.add(new Blast(playerOne.getX(),playerOne.getY()));	
				}
				//玩家2是否存在，存在的话是否击中玩家2
				else if(playerTwo != null && b.hitTank(playerTwo))
				{
					b.setLive(false);
					playerTwo.beHitted();
					if(!playerTwo.isLive())
						blast.add(new Blast(playerTwo.getX(),playerTwo.getY()));
				}
				else
				{
					//是否击中墙，以及墙是否被摧毁
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
	//判断星星是否存在，是否被吃掉了
	private void checkStarEat() {
		if(star != null)
		{
			if(playerOne.eatStar(star))
			{
				star.setLive(false);
			}
			else if(playerTwo  !=null&&playerTwo.eatStar(star))
			{
				star.setLive(false);
			}
			else
			{
				for(Tank t : enemyTanks)
					if(t.eatStar(star))
						star.setLive(false);
			}
		}
			
		
		
	}
	private void updateNewTank() {
		// TODO Auto-generated method stub
		
	}
	private void checkWin(Graphics g) 
	{
		// TODO Auto-generated method stub
		Color c = g.getColor();
		g.setColor(Color.RED);
		if(deadTankNumber >= totalTankNumber && home.isLive() && checkPlayerLife())
		{
			
			Font f = g.getFont();
			g.setFont(new Font("TimeRoman",Font.BOLD,60));
			g.drawString(" GRAME WIN!!!", 310, 300);
			g.setFont(f);
			clean();
		}
		else if(!home.isLive() || !checkPlayerLife())
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
	//初始化游戏界面
	private void initWarElements() {
		// TODO Auto-generated method stub
		Constant.GAME_START_SOUND.play();//添加音效
		//添加河
		for(int i =180;i<=420;i +=60)
			rivers.add(new River(0,i));
		rivers.add(new River(540,600));
		rivers.add(new River(720,600));
		rivers.add(new River(720,600));
		rivers.add(new River(720,600));
		 
		//添加铁墙
				for(int i = 600;i <= 750;i += 30)
					walls.add(new Wall(i,90,Constant.IRON_WALL));
				for(int i = 360;i <= 510;i += 30)
					walls.add(new Wall(i,360,Constant.IRON_WALL));
				for(int i = 420;i <= 510;i += 30)
					walls.add(new Wall(i,390,Constant.IRON_WALL));
				for(int i = 600; i <= 690;i += 30)
					walls.add(new Wall(180,i,Constant.IRON_WALL));
				walls.add(new Wall(0,120,Constant.IRON_WALL));
				walls.add(new Wall(30,120,Constant.IRON_WALL));
				walls.add(new Wall(0,150,Constant.IRON_WALL));
				walls.add(new Wall(30,150,Constant.IRON_WALL));
				walls.add(new Wall(0,720,Constant.IRON_WALL));
				walls.add(new Wall(30,720,Constant.IRON_WALL));
				walls.add(new Wall(0,750,Constant.IRON_WALL));
				walls.add(new Wall(30,750,Constant.IRON_WALL));
				walls.add(new Wall(0,480,Constant.IRON_WALL));
				walls.add(new Wall(30,480,Constant.IRON_WALL));
				walls.add(new Wall(0,510,Constant.IRON_WALL));
				walls.add(new Wall(30,510,Constant.IRON_WALL));
				
				//添加普通墙
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
		//窗口基本信息
		//右边窗口定义信息
		this.setSize(Constant.FRAME_WIDTH+200, Constant.FRAME_LENGTH+30);
		this.setLocation(280, 50);
		this.setResizable(false);
		this.setTitle("坦克大战");
		this.setBackground(Color.GRAY);
		this.setVisible(true);
		
		//游戏信息
		Image introImage = this.createImage(200,Constant.FRAME_LENGTH);
		Graphics gps = introImage.getGraphics();
		gps.setFont(new Font("TimesRoman", Font.BOLD, 15));
		gps.drawString("Press F1 to restart",10,40);
		gps.drawString("Press F2 to add player 2", 10, 70);
		gps.drawString("Player1: ", 10, 110);
		gps.drawString("Up: W      Down: S", 10, 140);
		gps.drawString("Left: A    Right: D", 10, 170);
		gps.drawString("Fire: Space", 10, 200);
		gps.drawString("Player2: ", 10, 240);
		gps.drawString("Up: ↑      Down: ↓", 10, 270);
		gps.drawString("Left: ←    Right: →", 10, 300);
		gps.drawString("Fire: P", 10, 330);
		gps.drawString("Remain Tanks:", 10, 400);
		this.getGraphics().drawImage(introImage,788,30,null);
		
		//监听窗口关闭
				this.addWindowListener(new WindowAdapter()
				{
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
						playerOne.keyPressed(e);
						if(playerTwo != null)
							playerTwo.keyPressed(e);
						clientKeyPressed(e);
					}

					private void clientKeyPressed(KeyEvent e) {
						// TODO Auto-generated method stub
						
					}

					public void keyReleased(KeyEvent e)
					{
						playerOne.keyReleased(e);//控制坦克的方向
						if(playerTwo != null)
							playerTwo.keyReleased(e);
					}
				});
			}
	
	private class PaintThread implements Runnable
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
	
	
	
	