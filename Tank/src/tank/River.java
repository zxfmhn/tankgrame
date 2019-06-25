package tank;

import java.awt.Graphics;
import java.awt.Rectangle;

import some_interfaces.Draw;
import some_interfaces.GetRectangle;

public class River implements Draw,GetRectangle{
	private int x = 0;
	private int y = 0;

	public River(int initX, int initY) {
		// TODO Auto-generated constructor stub
		x = initX;
		y = initY;
	}

	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Rectangle getRect() {
		// TODO Auto-generated method stub
		return null;
	}

}
