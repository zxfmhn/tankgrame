package tank;

import java.awt.Graphics;

import some_interfaces.Draw;

/**
 * 爆炸类，坦克爆炸是产生
 * @author KevinWen
 *
 */
public class Blast implements Draw
{
	//x，y为爆炸产生位置的坐标
	private int x;
	private int y;
	//一共有8张图，step表示当前放到第几张了
	private int step = 0;
	//标志对象是否存活
	private boolean live = true;

	/**
	 * 构造函数，爆炸的中心为爆炸前坦克的中心
	 * @param initX 坦克被击中前的横坐标
	 * @param initY 坦克被击中前的纵坐标
	 */
	public Blast(int initX,int initY)
	{
		x = initX + Constant.MY_TANK_WIDTH / 2 - Constant.BLAST_WIDTH / 2;
		y = initY + Constant.MY_TANK_LENGTH / 2 - Constant.BLAST_LENGTH / 2;
	}
	
	/**
	 * 画出坦克
	 * @param g 指定画在哪张图像上
	 */
	public void draw(Graphics g)
	{
		if(step == Constant.BLAST_IMAGES.length)
			live = false;
		if(live)
		{
			g.drawImage(Constant.BLAST_IMAGES[step++],x,y,null);
		}
	}
}