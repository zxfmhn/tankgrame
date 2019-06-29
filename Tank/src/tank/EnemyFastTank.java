package tank;

import java.awt.Graphics;
import java.util.Random;

/**
 * 敌方高机动坦克类
 * @author KevinWen
 *
 */
public class EnemyFastTank extends Tank
{
	//随机函数，用于产生随机方向
	private static Random rand = new Random();
	//用于控制方向的转换
	private int changeDirectionStep = rand.nextInt(10) + 5;

	/**
	 * 构造函数，该坦克的速度为10
	 * @param initX 入场横坐标
	 * @param initY 入场纵坐标
	 * @param initDirection 初始方向
	 * @param initTc TankClient类
	 * @param fireInterval 开火间隔
	 */
	public EnemyFastTank(int initX, int initY, int initDirection,TankClient initTc,int fireInterval)
	{
		super(initX, initY, initDirection,initTc,fireInterval);
		speedX = speedY = 10;
	}
	
	public void draw(Graphics g)
	{
		if(isLive())
		{
			g.drawImage(Constant.FAST_TANK_IMAGES[level][direction],x,y,null);
			move();
			Bullet b = fire(Constant.ENEMY_BULLET);
			if(b != null)
				tc.enemyBullet.add(b);
			fireStep++;
		}
	}
	
	public void move()
	{
		//记录当前坐标
		oldX = x;
		oldY = y;
		//随机产生方向
		if(changeDirectionStep == 0)
		{
			changeDirectionStep = rand.nextInt(12) + 3;
			oldDirection = direction;
			direction = rand.nextInt(4);
		}
		else
			changeDirectionStep--;
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
		case Constant.STOP:
		default:
			break;
		}
		//检查是否超出边界
		checkOutSpace();
	}
}
