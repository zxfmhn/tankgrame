package tank;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

/**
 * 我方坦克类
 * @author KevinWen
 *
 */
public class PlayerOneTank extends Tank
{
	//用于判断方向
	private boolean up = false;
	private boolean down = false;
	private boolean left = false;
	private boolean right = false;
	//区分玩家
	private int id = 0;
	/**
	 * 构造函数
	 * @param initX 初始位置横坐标
	 * @param initY 初始位置纵坐标
	 * @param initDirection 初始方向
	 * @param tc TankClient
	 * @param fireInterval 开火间隔
	 * @param initId 玩家标示
	 */
	public PlayerOneTank(int initX, int initY, int initDirection,TankClient tc,int fireInterval,int initId)
	{
		super(initX, initY, initDirection,tc,fireInterval);
		id = initId;
	}

	public void draw(Graphics g)
	{
		if(isLive())
		{
			g.drawImage(Constant.MY_TANK_IMAGES[id][level][oldDirection],x,y,null);
			move();
			if(fireStep < fireInterval)
				fireStep++;
		}
	}
	
	public void move()
	{
		oldX = x;
		oldY = y;
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
		//如果最终方向为STOP，则图片保持现状
		if(direction != Constant.STOP)
			oldDirection = direction;
		checkOutSpace();
	}
	
	/**
	 * 用于控制坦克的移动方向，按下键盘触发此函数
	 * 空格键开火，W,S,A,D,分别为上，下，左，右
	 * @param e 键盘按键
	 */
	public void keyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();
		switch(key)
		{
		case KeyEvent.VK_SPACE:
			Bullet b = fire(Constant.MY_BULLET);
			if(b != null)
			{
				Constant.FIRE_SOUND.play();
				tc.myBullet.add(b);
			}
			break;
		case KeyEvent.VK_W:
			up = true;
			break;
		case KeyEvent.VK_S:
			down = true;
			break;
		case KeyEvent.VK_A:
			left = true;
			break;
		case KeyEvent.VK_D:
			right = true;
			break;
		default:
			break;
		}
		//决定移动方向
		changeDirection();
	}
	
	/**
	 * 用于控制坦克移动，松开键盘触发此函数
	 * 详情参见KeyPressed函数
	 * @param e 键盘事件
	 */
	public void keyReleased(KeyEvent e)
	{
		int key = e.getKeyCode();
		switch(key)
		{
		case KeyEvent.VK_W:
			up = false;
			break;
		case KeyEvent.VK_S:
			down = false;
			break;
		case KeyEvent.VK_A:
			left = false;
			break;
		case KeyEvent.VK_D:
			right = false;
			break;
		default:
			break;
		}
		changeDirection();
	}
	
	//判断移动方向
	private void changeDirection()
	{
		if(up && !down && !left && !right)
			direction = Constant.UP;
		else if(!up && down && !left && !right)
			direction = Constant.DOWN;
		else if(!up && !down && left && !right)
			direction = Constant.LEFT;
		else if(!up && !down && !left && right)
			direction = Constant.RIGHT;
		else
			direction = Constant.STOP;
	}
}