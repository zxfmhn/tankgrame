package tank;

import java.awt.Graphics;
import java.awt.Rectangle;

import some_interfaces.Draw;
import some_interfaces.GetRectangle;

/**
 * 河类
 * @author KevinWen
 *
 */
public class River implements Draw,GetRectangle
{
	private int x = 0;
	private int y = 0;
	
	/**
	 * 构造函数
	 * @param initX 初始位置横坐标
	 * @param initY 初始位置纵坐标
	 */
	public River(int initX,int initY)
	{
		x = initX;
		y = initY;
	}
	
	public void draw(Graphics g)
	{
		g.drawImage(Constant.RIVER_IMAGE,x,y,null);
	}
	
	public Rectangle getRect()
	{
		return new Rectangle(x,y,Constant.RIVER_WIDTH,Constant.RIVER_LENGTH);
	}
}
