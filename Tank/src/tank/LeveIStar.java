package tank;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import some_interfaces.Draw;
import some_interfaces.GetRectangle;

/**
 * 等级星类，让坦克升级
 * @author KevinWen
 *
 */
public class LeveIStar implements Draw,GetRectangle
{
	private int x = 0;
	private int y = 0;
	private int oldX = 0;
	private int oldY = 0;
	private boolean live = false;
	//用于产生随机位置
	private static Random rand = new Random();
	//产生间隔
	private int appearInterval;
	//停留时间s
	private int appearTime;
	//计时器
	private int appearStep = 0;
	
	/**
	 * 构造函数
	 * @param initAppearInterval 产生间隔
	 * @param initAppearTime 停留时间
	 */
	public LeveIStar(int initAppearInterval,int initAppearTime)
	{
		appearInterval = initAppearInterval;
		appearTime = initAppearTime;
	}
	
	public void draw(Graphics g)
	{
		if(appearStep == appearInterval)
		{
			x = rand.nextInt(Constant.FRAME_WIDTH - Constant.STAR_WIDTH);
			y = rand.nextInt(Constant.FRAME_LENGTH - Constant.STAR_LENGTH);
			oldX = x;
			oldY = y;
			appearStep = 0;
			live = true;
		}
		
		if(appearStep < appearTime && live)
		{
			g.drawImage(Constant.STAR_IMAGE,oldX,oldY,null);
		}
		else
			live = false;
		appearStep++;
	}
	
	/**
	 * 获得等级星矩形
	 * @return 等级星矩形
	 */
	public Rectangle getRect()
	{
		return new Rectangle(x,y,Constant.STAR_WIDTH,Constant.STAR_LENGTH);
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
