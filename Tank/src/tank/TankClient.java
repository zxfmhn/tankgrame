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
	 
	/**
	 * 主窗体类构造函数
	 * @param total 敌方坦克总数，最多20个,最少1个，如果total小于等于0，自动初始化为3
	 */
	public TankClient(int total)
	{
		if(total > 20)
			total = 20;
		else if(total <= 0)
			total = 3;
		
		totalTankNumber = aliveTankNumber = total;
		initWindow();
		initWarElements();
		//更新界面的进程
		new Thread(new PaintThread()).start();
	}

	/**
	 * 界面刷新函数，主要函数
	 */
	public void paint(Graphics g)
	{
		checkWin(g);
		updateNewTank();
		
		//画出各种元素，注意画的顺序，避免坦克和子弹浮于树林上方
		home.draw(g);
		for(Born b : born)
			b.draw(g);
		for(River r : rivers)
			r.draw(g);
		for(Wall w : walls)
			w.draw(g);
		for(Tank t : enemyTanks)
			t.draw(g);
		if(star != null)
			star.draw(g);
		playerOne.draw(g);
		if(playerTwo != null)
			playerTwo.draw(g);
		for(Blast b : blast)
			b.draw(g);
		
		checkStarEat();
		checkEnemyBullet();
		checkMyBullet();
		
		//画出子弹和树，放在这里是为了满足遮蔽情况
		for(Bullet b : enemyBullet)
			b.draw(g);
		for(Bullet b : myBullet)
			b.draw(g);
		for(Tree t : trees)
			t.draw(g);
		
		//更新战场数据
		deadTankNumber = checkEnemyTankCollide();
		aliveTankNumber = totalTankNumber - deadTankNumber;
		
		
	}

	//界面更新函数，每一次更新界面都会调用此函数
	public void update(Graphics g)
	{
		screenImage = this.createImage(Constant.FRAME_WIDTH,Constant.FRAME_LENGTH);
		Graphics gsi = screenImage.getGraphics();
		Color c = gsi.getColor();
		gsi.setColor(Color.BLACK);
		gsi.fillRect(0,0,Constant.FRAME_WIDTH,Constant.FRAME_LENGTH);
		gsi.setColor(c);
		paint(gsi);
		g.drawImage(screenImage,0,26,Constant.FRAME_WIDTH,Constant.FRAME_LENGTH,null);
		
		Image numberImage = this.createImage(110,310);
		Graphics gni = numberImage.getGraphics();
		int miniX = 0,miniY = 0;
		for(int i = 0;i < aliveTankNumber;i++)
		{
			gni.drawImage(Constant.MINI_TANK_IMAGE,miniX + i % 3 * 40,miniY + i / 3 * 40,null);
		}
		g.drawImage(numberImage,Constant.FRAME_WIDTH + 25,450,null);
	}
	
	//初始化游戏界面所有元素wwwww  aww
	private void initWarElements()
	{
		Constant.GAME_START_SOUND.play();
		//添加河
		for(int i = 180;i <= 420;i += 60)
			rivers.add(new River(0,i));
		rivers.add(new River(540,600));
		rivers.add(new River(720,600));
		rivers.add(new River(720,600));
		rivers.add(new River(720,660));
		
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
	
	//初始化窗口
	private void initWindow()
	{
		//窗口基本信息
		//右侧(200，30)矩形中显示游戏信息
		this.setSize(Constant.FRAME_WIDTH + 200,Constant.FRAME_LENGTH + 30);
		this.setLocation(280,50);
		this.setResizable(false);
		this.setTitle("Kevin's Tank War");
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

			public void keyReleased(KeyEvent e)
			{
				playerOne.keyReleased(e);
				if(playerTwo != null)
					playerTwo.keyReleased(e);
			}
		});
		
		//先生成一个爆炸类，否则游戏时第一个被击中的坦克不会爆炸，不知道为什么
		Blast tempBlast = new Blast(0,0);
		for(int i = 0;i < 8;i++)
			tempBlast.draw(this.getGraphics());
	}

	//判断游戏是否结束，得到结局
	private void checkWin(Graphics g)
	{
		Color c = g.getColor();
		g.setColor(Color.RED);
		if(deadTankNumber >= totalTankNumber && home.isLive() && checkPlayerLife())
		{
			
			Font f = g.getFont();
			g.setFont(new Font("TimeRoman",Font.BOLD,60));
			g.drawString("WIN!!!", 310, 300);
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
	
	//判断等级星是否存在，是否被吃掉
	private void checkStarEat()
	{
		if(star != null)
		{	
			if(playerOne.eatStar(star))
			{
				star.setLive(false);
			}
			else if(playerTwo != null && playerTwo.eatStar(star))
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
	
	//判断敌方子弹的命中情况
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
	
	//判断我方子弹的击中情况，类似于敌方子弹情况判断
	private void checkMyBullet()
	{
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
	
	//移除失效的入场类，添加相应的新坦克
	private void updateNewTank()
	{
		List<Born> done = new ArrayList<Born>();
		for(Born b : born)
		{
			if(!b.isLive())
			{
				done.add(b);
				//随机添加坦克
				if(bornStep % 2 == 1)
				{
					enemyTanks.add(new EnemyFastTank(b.getX(),0,Constant.DOWN,this,40));
				}
				else
				{
					enemyTanks.add(new EnemyNormalTank(b.getX(),0,Constant.DOWN,this,50));
				}
			}
		}
		//如果删除列表里有内容，则执行删除
		if(done.size() != 0)
			born.removeAll(done);
		//判断是否有后续坦克入场，以及后续坦克能否入场
		//最多同时三个入场，判断战场坦克数量，以及坦克剩余数量，并且判断入场位置是否被占用
		if(born.size() < 3 && onBattle < maxOnBattle && aliveTankNumber - onBattle > 0 && bornCollideSomething())
		{
			Constant.ADD_SOUND.play();
			onBattle++;
			born.add(new Born(Constant.BORN_POSITION_X[bornStep % 3]));
		}
	}
	
	private int checkEnemyTankCollide()
	{
		//摧毁坦克计数器
		int deadCount = 0;
		//判断敌方坦克是否撞到某物，撞到则返回上一个位置
		for(Tank t : enemyTanks)
		{
			if(!t.isLive())
			{
				deadCount++;
				continue;
			}
		
			if(t.isCollideHome(home))
			{
				home.setLive(false);
				break;
			}
			else if(t.isCollideTank(playerOne) || (playerTwo != null && t.isCollideTank(playerTwo)))
			{
				t.runBack();
				continue;
			}
			
			boolean flag = false;
			
			for(Born b : born)
			{
				if(t.isCollideBorn(b))
				{
					t.runBack();
					flag = true;
					break;
				}
			}
			if(flag)
				continue;
			
			for(River r : rivers)
			{
				if(t.isCollideRiver(r))
				{
					t.runBack();
					flag = true;
					break;
				}
			}
			if(flag)
				continue;
			
			for(Wall w : walls)
			{
				if(t.isCollideWall(w))
				{
					t.runBack();
					flag = true;
					break;
				}
			}
			if(flag)
				continue;
			
			for(Tank temp : enemyTanks)
			{
				if(!temp.equals(t))
				{
					if(t.isCollideTank(temp))
					{
						t.runBack();
						break;
					}
				}
			}
		}
		return deadCount;
	}
	
	//游戏结束时执行清理
	private void clean()
	{
		blast.clear();
		enemyBullet.clear();
		myBullet.clear();
		star = null;
	}
	
	//主界面键盘监听函数
	protected void clientKeyPressed(KeyEvent e)
	{
		//F1重新开始游戏，F2添加玩家2
		if(e.getKeyCode() == KeyEvent.VK_F1)
		{
			playerTwo = null;
			aliveTankNumber = totalTankNumber;
			deadTankNumber = 0;
			bornStep = 0;
			onBattle = 0;
			enemyTanks.clear();
			blast.clear();
			enemyBullet.clear();
			myBullet.clear();
			walls.clear();
			trees.clear();
			rivers.clear();
			born.clear();
			playerOne = new PlayerOneTank(270,720,Constant.STOP,this,5,Constant.PLAYER_ONE);
			star = new LevelStar(200,120);
			home.setLive(true);
			initWarElements();
		}
		else if(e.getKeyCode() == KeyEvent.VK_F2)
			playerTwo = new PlayerTwoTank(450,720,Constant.STOP,this,5,Constant.PLAYER_TWO);
	}

	//判断入场类的入场位置是否被占用
	private boolean bornCollideSomething()
	{
		bornStep++;
		int step = bornStep % 3;
		//判断是否被其他入场类占用
		for(Born b : born)
			if(b.getX() == Constant.BORN_POSITION_X[step])
				return false;
		
		Born test = new Born(Constant.BORN_POSITION_X[step]);
		
		//判断是否有坦克占用当前位置
		for(Tank t : enemyTanks)
			if(t.isCollideBorn(test))
				return false;
		if(playerOne.isCollideBorn(test))
			return false;
		
		return true;
	}
	
	//判断是否有玩家存活
	private boolean checkPlayerLife()
	{
		//如果只有玩家1
		if(playerTwo == null)
			return playerOne.isLive();
		else
			return playerOne.isLive() || playerTwo.isLive();
	}
		
	//判断玩家坦克是否撞到某物
	private void checkPlayerTankCollide(Tank player)
	{
		if(player == null)
			return;
		if(player.isCollideHome(home))
		{
			home.setLive(false);
		}
		else
		{
			boolean flag = false;
			
			for(Wall w : walls)
			{
				if(!w.isLive())
					continue;
				if(player.isCollideWall(w))
				{
					player.runBack();
					flag = true;
					break;
				}
			}
			
			if(!flag)
			{
				for(Born b : born)
				{
					if(player.isCollideBorn(b))
					{
						player.runBack();
						flag = true;
						break;
					}
				}
			}
			
			if(!flag)
			{
				for(Tank t : enemyTanks)
				{
					if(!t.isLive())
						continue;
					
					if(player.isCollideTank(t))
					{
						player.runBack();
						flag = true;
						break;
					}
				}
			}
			
			if(!flag)
			{
				for(River r : rivers)
				{
					if(player.isCollideRiver(r))
					{
						player.runBack();
						break;
					}
				}
			}
		}
	}

	public static void main(String[] args)
	{
		new TankClient(20);
	}
	
	//界面更新进程
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