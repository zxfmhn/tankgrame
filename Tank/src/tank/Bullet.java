package tank;

import java.awt.Graphics;
import java.awt.Rectangle;

import some_interfaces.Draw;
import some_interfaces.GetRectangle;
import some_interfaces.Move;

/**
 * 子弹类
 * @author KevinWen
 *
 */
public class Bullet implements Draw,Move,GetRectangle
{
	//敌我子弹标示
	private int id;
	//子弹发出时的坐标
	private int x = 0;
	private int y = 0;
	//子弹的移动速度
	private int speedX = 20;
	private int speedY = 20;
	//子弹的移动方向
	private int direction = Constant.STOP;
	//子弹等级，根据坦克的等级设定子弹等级
	private int level = Constant.TANK_LEVEL_1;
	//判断子弹是否存活
	private boolean live = true;
	
	/**
	 * 子弹类构造函数
	 * @param fireX 开火位置的横坐标
	 * @param fireY 开火位置的纵坐标
	 * @param initDirection 开火方向
	 * @param initLevel 子弹等级
	 */
	public Bullet(int fireX, int fireY, int initDirection, int initLevel,int initId)
	{
		x = fireX;
		y = fireY;
		direction = initDirection;
		level = initLevel;
		id = initId;
	}
	
	public void draw(Graphics g)
	{
		if(live)
		{
			g.drawImage(Constant.BULLET_IMAGES[id],x,y,null);
			move();
		}
	}
	
	/**
	 * 子弹移动函数
	 */
	public void move()
	{
		switch(direction)
		{
		case Constant.UP:
			y -= speedY;
			break;
		case Constant.DOWN:
			y += speedY;
			break;
		case Constant.LEFT:
			x -= speedX;
			break;
		case Constant.RIGHT:
			x += speedX;
			break;
		default:
			break;
		}
		//判断子弹是否超出屏幕，超出则置live为false
		if(x < 0 || y < 0 || x >= Constant.FRAME_WIDTH || y >= Constant.FRAME_LENGTH)
			live = false;
	}
	
	/**
	 * 判断子弹是否打中坦克
	 * @param t 坦克类
	 * @return 打中返回true，未打中返回false
	 */
	public boolean hitTank(Tank t)
	{
		return this.getRect().intersects(t.getRect()) ? true : false;
	}
	
	/**
	 * 判断子弹是否打中墙
	 * @param w 墙类
	 * @return 打中返回true，未打中返回false
	 */
	public boolean hitWall(Wall w)
	{
		return this.getRect().intersects(w.getRect()) ? true : false;
	}
	
	/**
	 * 判断是否打中家
	 * @param h 家类
	 * @return 打中返回true，未打中返回false
	 */
	public boolean hitHome(Home h)
	{
		return this.getRect().intersects(h.getRect()) ? true : false;
	}
	
	/**
	 * 子弹是否存活
	 * @return 存活返回true，否则返回false
	 */
	public boolean isLive()
	{
		return live;
	}

	/**
	 * 设置子弹存活状态
	 * @param live true为存活，false为过期
	 */
	public void setLive(boolean live)
	{
		this.live = live;
	}
	
	/**
	 * 得到子弹的矩形信息
	 * @return 返回子弹的矩形
	 */
	public Rectangle getRect()
	{
		return new Rectangle(x,y,Constant.BULLET_WIDTH,Constant.BULEET_LENGTH);
	}

	/**
	 * 得到子弹等级
	 * @return 子弹等级
	 */
	public int getLevel()
	{
		return level;
	}
}
