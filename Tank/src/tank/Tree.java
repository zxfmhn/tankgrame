package tank;

import java.awt.Graphics;

public class Tree {

	private int x = 0;
	private int y = 0;

	public Tree(int i, int i2) {
		// TODO Auto-generated constructor stub
	}

	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(Constant.TREE_IMAGE,x,y,null);
	}

}
