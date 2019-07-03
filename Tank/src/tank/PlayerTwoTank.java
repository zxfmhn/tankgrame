package tank;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

/**
 * 玩家2类
 * P为开火，四个方向键为移动键，其余参见PlayerOneTank类
 * @author KevinWen
 *
 */
public class PlayerTwoTank extends Tank
{
	private boolean up = false;
	private boolean down = false;
	private boolean left = false;
	private boolean right = false;
	private int id = 0;
	public PlayerTwoTank(int initX, int initY, int initDirection,TankClient tc,int fireInterval,int initId)
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
		
		if(direction != Constant.STOP)
			oldDirection = direction;
		checkOutSpace();
	}
	
	public void keyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();
		switch(key)
		{
		case KeyEvent.VK_P:
			Bullet b = fire(Constant.MY_BULLET);
			if(b != null)
			{
				Constant.FIRE_SOUND.play();
				tc.myBullet.add(b);
			}
			break;
		case KeyEvent.VK_UP:
			up = true;
			break;
		case KeyEvent.VK_DOWN:
			down = true;
			break;
		case KeyEvent.VK_LEFT:
			left = true;
			break;
		case KeyEvent.VK_RIGHT:
			right = true;
			break;
		default:
			break;
		}
		changeDirection();
	}
	
	public void keyReleased(KeyEvent e)
	{
		int key = e.getKeyCode();
		switch(key)
		{
		case KeyEvent.VK_UP:
			up = false;
			break;
		case KeyEvent.VK_DOWN:
			down = false;
			break;
		case KeyEvent.VK_LEFT:
			left = false;
			break;
		case KeyEvent.VK_RIGHT:
			right = false;
			break;
		default:
			break;
		}
		changeDirection();
	}
	
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