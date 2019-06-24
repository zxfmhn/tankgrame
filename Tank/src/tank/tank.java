package tank;

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
	}

	private void updateNewTank() {
		// TODO Auto-generated method stub
		
	}
	private void chechWin(Graphics g) {
		// TODO Auto-generated method stub
		
	}
	private void initWarElements() {
		// TODO Auto-generated method stub
		
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
