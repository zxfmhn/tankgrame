package tank;

import java.awt.Graphics;
import java.awt.Rectangle;

import some_interfaces.Draw;
import some_interfaces.GetRectangle;

/**
 * 基地类
 * @author KevinWen
 *
 */
public class Home implements Draw,GetRectangle
{
	private int x = 0;
	private int y = 0;
	private boolean live = true;
	
	/**
	 * 构造函数
	 * @param initX 基地位置横坐标
	 * @param initY 基地位置纵坐标
	 */
	public Home(int initX,int initY)
	{
		x = initX;
		y = initY;
	}
	
	public void draw(Graphics g)
	{
		if(live)
			g.drawImage(Constant.HOME_IMAGE,x,y,null);
	}
	
	/**
	 * 得到基地矩形
	 * @return 基地矩形
	 */
	public Rectangle getRect()
	{
		return new Rectangle(x,y,Constant.HOME_WIDTH,Constant.HOME_LENGTH);
	}
	
	public boolean isLive()
	{
		return live;
	}
	
	public void setLive(boolean live)
	{
		this.live = live;
	}
}

