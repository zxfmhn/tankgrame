package tank;

import java.awt.Graphics;

import some_interfaces.Draw;

/**
 * 树类
 * @author KevinWen
 *
 */
public class Tree implements Draw
{
	private int x = 0;
	private int y = 0;
	/**
	 * 构造函数
	 * @param initX 位置横坐标
	 * @param initY 位置纵坐标
	 */
	public Tree(int initX,int initY)
	{
		x = initX;
		y = initY;
	}
	
	public void draw(Graphics g)
	{
		g.drawImage(Constant.TREE_IMAGE,x,y,null);
	}
}
