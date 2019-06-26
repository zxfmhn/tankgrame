package tank;

import java.awt.Graphics;
import java.awt.Rectangle;
import some_interfaces.Draw;
import some_interfaces.GetRectangle;

public class Home implements Draw,GetRectangle
{
	private int x = 0;
	private int y = 0;
	private boolean live = true;

	public Home(int initX, int initY) {
		// TODO Auto-generated constructor stub
		x = initX;
		y = initY;
	}

	@Override
	public Rectangle getRect() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		
	}

	public boolean isLive() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setLive(boolean b) {
		// TODO Auto-generated method stub
		
	}

}
