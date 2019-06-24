package tank;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
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
		
	}
	private void checkEnemyBullet() {
		// TODO Auto-generated method stub
		
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
		Constant.GAME_START_SOUND.play();
		//添加游戏界面中的河
		for(int i = 180; i<=420;i +=60)
			rivers.add(new River(0,i));
		
	}

	private void initWindow() {
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
