package tank;

import java.awt.Graphics;
import java.util.Random;

/**
 * 敌方普通坦克
 * @author KevinWen
 *
 */
public class EnemyNormalTank extends Tank
{
	private static Random rand = new Random();
	private int changeDirectionStep = rand.nextInt(10) + 5;
	
	/**
	 * 构造函数
	 * @param initX 入场位置横坐标
	 * @param initY 入场位置纵坐标
	 * @param initDirection 初始方向
	 * @param initTc TankClient类
	 * @param fireInterval 开火间隔
	 */
	public EnemyNormalTank(int initX, int initY, int initDirection,TankClient initTc,int fireInterval)
	{
		super(initX, initY, initDirection,initTc,fireInterval);
	}
	
	public void draw(Graphics g)
	{
		if(isLive())
		{
			g.drawImage(Constant.NORMAL_TANK_IMAGES[level][direction],x,y,null);
			move();
			Bullet b = fire(Constant.ENEMY_BULLET);
			if(b != null)
				tc.enemyBullet.add(b);
			fireStep++;
		}
	}
	
	/**
	 * 移动函数，参见EnemyFastTank类
	 */
	public void move()
	{
		oldX = x;
		oldY = y;
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
		checkOutSpace();
	}
}
