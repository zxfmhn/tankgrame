package tank;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Comparator;

import some_interfaces.Draw;
import some_interfaces.GetRectangle;

/**
 * 入场类，坦克出现时有星光闪烁；坦克只能在界面最顶部三个位置随机出现
 * @author KevinWen
 *
 */
public class Born implements Comparator<Born>,Draw,GetRectangle
{
	//闪烁步骤，一共4步
	private int step = 0;
	//闪烁类的横坐标，纵坐标默认为零
	private int x;
	//判断对象是否存活
	private boolean live = true;
	
	/**
	 * 构造函数，传入入场位置的横坐标
	 * @param initX
	 */
	public Born(int initX)
	{
		x = initX;
	}
	
	public void draw(Graphics g)
	{
		if(step == Constant.BORN_IMAGES.length)
		{
			live = false;
		}
		if(live)
		{
			g.drawImage(Constant.BORN_IMAGES[step++],x,0,null);
		}
	}
	
	public int getX()
	{
		return x;
	}

	public boolean isLive()
	{
		return live;
	}

	public void setLive(boolean live)
	{
		this.live = live;
	}
	
	/**
	 * 获得矩形信息
	 * @return 返回入场对象的矩形
	 */
	public Rectangle getRect()
	{
		return new Rectangle(x,0,Constant.MY_TANK_WIDTH,Constant.MY_TANK_LENGTH);
	}
	
	/**
	 * 用于ArrayList的比较
	 * @return 0为相等
	 */
	public int compare(Born left, Born right)
	{
		return left.hashCode() - right.hashCode();
	}
}
