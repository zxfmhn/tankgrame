package tank;

import java.awt.Rectangle;

import some_interfaces.Draw;
import some_interfaces.GetRectangle;
import some_interfaces.Move;

/**
 * 坦克类，基类
 * @author KevinWen
 *
 */
public abstract class Tank implements Draw,Move,GetRectangle
{
	protected int x = 0; //位置横坐标
	protected int y = 0; //位置纵坐标
	protected int oldX = 0; //移动前位置的横坐标
	protected int oldY = 0; //移动前位置的纵坐标
	protected int speedX = 6; //移动速度
	protected int speedY = 6; 
	protected int direction = Constant.STOP; //移动方向
	protected int oldDirection = Constant.UP; //移动前的方向
	protected int level = Constant.TANK_LEVEL_1; //坦克等级
	protected int live = 1; //判断是否存活 
	protected int fireInterval = 20; //开火间隔 
	protected int fireStep = 0; //开火计时器 
	protected TankClient tc = null;
	//开火位置
	private int fireX;
	private int fireY;
	
	/**
	 * 构造函数
	 * @param initX 入场位置横坐标
	 * @param initY 入场位置纵坐标
	 * @param initDirection 入场时方向
	 * @param initTc TankClient类，用于添加子弹
	 * @param initFireInterval 开火间隔
	 */
	public Tank(int initX,int initY,int initDirection,TankClient initTc,int initFireInterval)
	{
		x = initX;
		y = initY;
		direction = initDirection;
		tc = initTc;
		fireInterval = initFireInterval;
	}
	
	/**
	 * 开火函数，开火间隔到才能开火
	 * 敌方坦克自动开火，我方坦克按键控制开火
	 * @return 子弹类
	 */
	public Bullet fire(int id)
	{
		//判断开火间隔是否满足
		if(fireStep == fireInterval)
		{
			//判断开火方向
			int finalDirection = direction == Constant.STOP ? oldDirection : direction;
			//重置开火计时器
			fireStep = 0;
			//得到开火位置
			decideBulletPosition(finalDirection);
			return new Bullet(fireX,fireY,finalDirection,level,id);
		}
		else
			return null;
	}
		
	/**
	 * 坦克升级函数，碰到等级星时触发
	 * @param s 等级星
	 * @return 吃掉等级星则返回true，否则返回false
	 */
	public boolean eatStar(LeveIStar s)
	{
		//坦克是否存活，等级星是否存活，坦克是否碰到等级星
		if(live >= 0 && s.isLive() && this.getRect().intersects(s.getRect()))
		{
			live = 2;
			level = Constant.TANK_LEVEL_2;
			return true;
		}
		return false;
	}
	
	/**
	 * 回到上一个位置
	 */
	public void runBack()
	{
		x = oldX;
		y = oldY;
	}
	
	/**
	 * 受到攻击
	 */
	public void beHitted()
	{
		level--;
		live--;
	}
	
	/**
	 * 设置坦克速度
	 * @param speedX
	 * @param speedY
	 */
	public void setSpeed(int speedX,int speedY)
	{
		this.speedX = speedX;
		this.speedY = speedY;
	}
	
	/**
	 * 判断坦克是否超出边界，超出则将坦克放置到界面边缘
	 */
	protected void checkOutSpace()
	{
		if(x < 0)
			x = 0;
		else if(x > Constant.FRAME_WIDTH - Constant.TANK_WIDTH)
			x = Constant.FRAME_WIDTH - Constant.TANK_WIDTH;
		
		if(y < 0)
			y = 0;
		else if(y > Constant.FRAME_LENGTH - Constant.TANK_LENGTH)
			y = Constant.FRAME_LENGTH - Constant.TANK_LENGTH;
	}
	
	//决定开火位置
	private void decideBulletPosition(int finalDirection)
	{
		switch(finalDirection)
		{
		case  Constant.UP:
			fireX = x + Constant.TANK_WIDTH / 2 - Constant.BULLET_WIDTH / 2;
			fireY = y - 9;
			break;
		case Constant.DOWN:
			fireX = x + Constant.TANK_WIDTH / 2 - Constant.BULLET_WIDTH / 2;
			fireY = y + Constant.TANK_LENGTH;
			break;
		case Constant.LEFT:
			fireX = x - 9;
			fireY = y + Constant.TANK_LENGTH / 2 - Constant.BULEET_LENGTH / 2;
			break;
		case Constant.RIGHT:
			fireX = x + Constant.TANK_WIDTH;
			fireY = y + Constant.TANK_LENGTH / 2 - Constant.BULEET_LENGTH / 2;
			break;
		default:
			break;
		}
	}
	
	public Rectangle getRect()
	{
		return new Rectangle(x,y,Constant.TANK_WIDTH,Constant.TANK_LENGTH);
	}
	
	/**
	 * 判断是否碰到墙
	 * @param w 墙类
	 * @return 碰到返回true，否则返回false
	 */
	public boolean isCollideWall(Wall w)
	{
		return w.isLive() && isLive() && this.getRect().intersects(w.getRect());
	}
	
	/**
	 * 判断是否碰到河
	 * @param r 河类
	 * @return 碰到返回true，否则返回false
	 */
	public boolean isCollideRiver(River r)
	{
		return isLive() && this.getRect().intersects(r.getRect());
	}
	
	/**
	 * 判断是否碰到基地
	 * @param h 基地类
	 * @return 碰到返回true，否则返回false
	 */
	public boolean isCollideHome(Home h)
	{
		return isLive() && this.getRect().intersects(h.getRect());
	}
	
	/**
	 * 判断是否碰到其它坦克
	 * @param t 坦克类
	 * @return 碰到返回true，否则返回false
	 */
	public boolean isCollideTank(Tank t)
	{
		return t.isLive() && isLive() && this.getRect().intersects(t.getRect());
	}
	
	/**
	 * 判断是否碰到入场类
	 * @param b 入场类
	 * @return 碰到返回true，否则返回false
	 */
	public boolean isCollideBorn(Born b)
	{
		return isLive() && this.getRect().intersects(b.getRect());
	}
	
	public boolean isLive()
	{
		return live <= 0?false:true;
	}
	
	public int getDirection()
	{
		return direction;
	}

	public void setDirection(int direction)
	{
		this.direction = direction;
	}

	public int getLevel()
	{
		return level;
	}

	public void setLevel(int level)
	{
		this.level = level;
		if(level == Constant.TANK_LEVEL_1)
			live = 1;
		else if(level == Constant.TANK_LEVEL_2)
			live = 2;
	}
	
	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}

	public boolean isCollideTank(PlayerOneTank playerOne) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isCollideTank(PlayerTwoTank playerTwo) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean eatStar1(LeveIStar star) {
		// TODO Auto-generated method stub
		return false;
	}
}
