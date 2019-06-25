package tank;

import java.awt.Graphics;
import java.awt.Rectangle;

import some_interfaces.Draw;
import some_interfaces.GetRectangle;

public class Wall implements Draw,GetRectangle
{
	private int x = 0;
	private int y = 0;
	private int level = Constant.NORMAL_WALL;
	private boolean live = true;
	
	/**
	 * 构造函数
	 * @param initX 位置横坐标
	 * @param initY 位置纵坐标
	 * @param initLevel 墙的等级
	 */
	public Wall(int initX,int initY,int initLevel)
	{
		
	}
	
	public int getLevel()
	{
		return level;
	}

	public void setLevel(int level)
	{
		this.level = level;
	}
	
	public void draw(Graphics g)
	{
		if(live)
			g.drawImage(Constant.WALL_IMAGES[level],x,y,null);
	}
	
	public Rectangle getRect()
	{
		return new Rectangle(x,y,Constant.WALL_WIDTH,Constant.WALL_LENGTH);
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
