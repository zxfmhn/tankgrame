package tank;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Image;
import java.awt.Toolkit;

/**
 * 常数类，用于存放改项目用到的所有常数
 * @author KevinWen
 *
 */
public class Constant
{
	//移动方向
	public static final int UP = 0;
	public static final int DOWN = 1;
	public static final int LEFT = 2;
	public static final int RIGHT = 3;
	public static final int STOP = 4;
	//墙的等级
	public static final int IRON_WALL = 1;
	public static final int NORMAL_WALL = 0;
	//坦克等级，同时也是子弹等级
	public static final int TANK_LEVEL_1 = 0;
	public static final int TANK_LEVEL_2 = 1;
	//子弹类型，地方子弹还是我方子弹
	public static final int ENEMY_BULLET = 0;
	public static final int MY_BULLET = 1;
	
	//界面长宽
	public static final int FRAME_WIDTH = 780;
	public static final int FRAME_LENGTH = 780;
	//坦克图片的长宽
	public static final int TANK_WIDTH = 60;
	public static final int TANK_LENGTH = 60;
	//我方坦克的长宽
	public static final int MY_TANK_WIDTH = 60;
	public static final int MY_TANK_LENGTH = 60;
	//敌方普通坦克的长宽
	public static final int NORMAL_TANK_WIDTH = 60;
	public static final int NORMAL_TANK_LENGTH = 60;
	//敌方高机动坦克的长宽
	public static final int FAST_TANK_WIDTH = 60;
	public static final int FAST_TANK_LENGTH = 60;
	//等级星的长宽
	public static final int STAR_WIDTH = 40;
	public static final int STAR_LENGTH = 40;
	//树的长宽
	public static final int TREE_WIDTH = 60;
	public static final int TREE_LENGTH = 60;
	//墙的长宽
	public static final int WALL_WIDTH = 30;
	public static final int WALL_LENGTH = 30;
	//河的长宽
	public static final int RIVER_WIDTH = 60;
	public static final int RIVER_LENGTH = 60;
	//子弹的长宽
	public static final int BULLET_WIDTH = 15;
	public static final int BULEET_LENGTH = 15;
	//基地的长宽
	public static final int HOME_WIDTH = 60;
	public static final int HOME_LENGTH = 60;
	//爆炸类的长宽
	public static final int BLAST_WIDTH = 136;
	public static final int BLAST_LENGTH = 107;
	
	//坦克的出生位置横坐标
	public static final int[] BORN_POSITION_X = new int[]{0,360,720};
	//玩家标示
	public static final int PLAYER_ONE = 0;
	public static final int PLAYER_TWO = 1;
	
	public static final AudioClip GAME_START_SOUND = Applet.newAudioClip(Home.class.getClassLoader().getResource("Music/start.wav"));
	public static final AudioClip BLAST_SOUND = Applet.newAudioClip(Home.class.getClassLoader().getResource("Music/blast.wav"));
	public static final AudioClip FIRE_SOUND = Applet.newAudioClip(Home.class.getClassLoader().getResource("Music/fire.wav"));
	public static final AudioClip ADD_SOUND = Applet.newAudioClip(Home.class.getClassLoader().getResource("Music/add.wav"));
	
	
	
	//以下为所有素材的图片常量
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	public static Image HOME_IMAGE = null;
	public static Image STAR_IMAGE = null;
	public static Image RIVER_IMAGE = null;
	public static Image[] BULLET_IMAGES = null;
	public static Image TREE_IMAGE = null;
	public static Image[][][] MY_TANK_IMAGES = null;
	public static Image[][] FAST_TANK_IMAGES = null;
	public static Image[][] NORMAL_TANK_IMAGES = null;
	public static Image[] WALL_IMAGES = null;
	public static Image[] BLAST_IMAGES = null;
	public static Image[] BORN_IMAGES = null;
	public static Image MINI_TANK_IMAGE = null;
	
	static
	{
		HOME_IMAGE = tk.getImage(Home.class.getClassLoader().getResource("TankWarImages/home.gif"));
		STAR_IMAGE = tk.getImage(LeveIStar.class.getClassLoader().getResource("TankWarImages/levelStar.gif"));
		RIVER_IMAGE = tk.getImage(River.class.getClassLoader().getResource("TankWarImages/river.gif"));
		TREE_IMAGE = tk.getImage(Tree.class.getClassLoader().getResource("TankWarImages/tree.gif"));
		MINI_TANK_IMAGE = tk.getImage(Home.class.getClassLoader().getResource("TankWarImages/miniTank.gif"));
		
		BULLET_IMAGES = 
			new Image[]
			{
				tk.getImage(Bullet.class.getClassLoader().getResource("TankWarImages/enemyBullet.gif")),
				tk.getImage(Bullet.class.getClassLoader().getResource("TankWarImages/myBullet.gif"))
			};
		
		//我方坦克的图片，三维数组，第一维为玩家标示，第二维为等级，第三维为方向
		MY_TANK_IMAGES = 
				new Image[][][]
				{
					{
						{
							tk.getImage(PlayerOneTank.class.getClassLoader().getResource("TankWarImages/playerOneTankLevelOneUp.gif")),
							tk.getImage(PlayerOneTank.class.getClassLoader().getResource("TankWarImages/playerOneTankLevelOneDown.gif")),
							tk.getImage(PlayerOneTank.class.getClassLoader().getResource("TankWarImages/playerOneTankLevelOneLeft.gif")),
							tk.getImage(PlayerOneTank.class.getClassLoader().getResource("TankWarImages/playerOneTankLevelOneRight.gif")),
						},
						{
							tk.getImage(PlayerOneTank.class.getClassLoader().getResource("TankWarImages/playerOneTankLevelTwoUp.gif")),
							tk.getImage(PlayerOneTank.class.getClassLoader().getResource("TankWarImages/playerOneTankLevelTwoDown.gif")),
							tk.getImage(PlayerOneTank.class.getClassLoader().getResource("TankWarImages/playerOneTankLevelTwoLeft.gif")),
							tk.getImage(PlayerOneTank.class.getClassLoader().getResource("TankWarImages/playerOneTankLevelTwoRight.gif")),
						}
					},
					{
						{
							tk.getImage(PlayerTwoTank.class.getClassLoader().getResource("TankWarImages/playerTwoTankLevelOneUp.gif")),
							tk.getImage(PlayerTwoTank.class.getClassLoader().getResource("TankWarImages/playerTwoTankLevelOneDown.gif")),
							tk.getImage(PlayerTwoTank.class.getClassLoader().getResource("TankWarImages/playerTwoTankLevelOneLeft.gif")),
							tk.getImage(PlayerTwoTank.class.getClassLoader().getResource("TankWarImages/playerTwoTankLevelOneRight.gif")),
						},
						{
							tk.getImage(PlayerTwoTank.class.getClassLoader().getResource("TankWarImages/playerTwoTankLevelTwoUp.gif")),
							tk.getImage(PlayerTwoTank.class.getClassLoader().getResource("TankWarImages/playerTwoTankLevelTwoDown.gif")),
							tk.getImage(PlayerTwoTank.class.getClassLoader().getResource("TankWarImages/playerTwoTankLevelTwoLeft.gif")),
							tk.getImage(PlayerTwoTank.class.getClassLoader().getResource("TankWarImages/playerTwoTankLevelTwoRight.gif")),
						}
					}
				};
			
		//敌方高机动坦克,第一维为坦克等级，第二维为方向
		FAST_TANK_IMAGES = 
				new Image[][]
				{
					{
						tk.getImage(EnemyFastTank.class.getClassLoader().getResource("TankWarImages/fastTankLevelOneUp.gif")),
						tk.getImage(EnemyFastTank.class.getClassLoader().getResource("TankWarImages/fastTankLevelOneDown.gif")),
						tk.getImage(EnemyFastTank.class.getClassLoader().getResource("TankWarImages/fastTankLevelOneLeft.gif")),
						tk.getImage(EnemyFastTank.class.getClassLoader().getResource("TankWarImages/fastTankLevelOneRight.gif")),
					},
					{
						tk.getImage(EnemyFastTank.class.getClassLoader().getResource("TankWarImages/fastTankLevelTwoUp.gif")),
						tk.getImage(EnemyFastTank.class.getClassLoader().getResource("TankWarImages/fastTankLevelTwoDown.gif")),
						tk.getImage(EnemyFastTank.class.getClassLoader().getResource("TankWarImages/fastTankLevelTwoLeft.gif")),
						tk.getImage(EnemyFastTank.class.getClassLoader().getResource("TankWarImages/fastTankLevelTwoRight.gif")),
					}
				};
		
		//敌方高机动坦克
		NORMAL_TANK_IMAGES = 
				new Image[][]
				{
					{
						tk.getImage(EnemyNormalTank.class.getClassLoader().getResource("TankWarImages/normalTankLevelOneUp.gif")),
						tk.getImage(EnemyNormalTank.class.getClassLoader().getResource("TankWarImages/normalTankLevelOneDown.gif")),
						tk.getImage(EnemyNormalTank.class.getClassLoader().getResource("TankWarImages/normalTankLevelOneLeft.gif")),
						tk.getImage(EnemyNormalTank.class.getClassLoader().getResource("TankWarImages/normalTankLevelOneRight.gif")),
					},
					{
						tk.getImage(EnemyNormalTank.class.getClassLoader().getResource("TankWarImages/normalTankLevelTwoUp.gif")),
						tk.getImage(EnemyNormalTank.class.getClassLoader().getResource("TankWarImages/normalTankLevelTwoDown.gif")),
						tk.getImage(EnemyNormalTank.class.getClassLoader().getResource("TankWarImages/normalTankLevelTwoLeft.gif")),
						tk.getImage(EnemyNormalTank.class.getClassLoader().getResource("TankWarImages/normalTankLevelTwoRight.gif")),
					}
				};
		WALL_IMAGES = 
				new Image[]
				{
					tk.getImage(Wall.class.getClassLoader().getResource("TankWarImages/wall.gif")),
					tk.getImage(Wall.class.getClassLoader().getResource("TankWarImages/ironWall.gif")),
				};
		BLAST_IMAGES = 
				new Image[]
				{
					tk.getImage(Blast.class.getClassLoader().getResource("TankWarImages/blast1.gif")),
					tk.getImage(Blast.class.getClassLoader().getResource("TankWarImages/blast2.gif")),
					tk.getImage(Blast.class.getClassLoader().getResource("TankWarImages/blast3.gif")),
					tk.getImage(Blast.class.getClassLoader().getResource("TankWarImages/blast4.gif")),
					tk.getImage(Blast.class.getClassLoader().getResource("TankWarImages/blast5.gif")),
					tk.getImage(Blast.class.getClassLoader().getResource("TankWarImages/blast6.gif")),
					tk.getImage(Blast.class.getClassLoader().getResource("TankWarImages/blast7.gif")),
					tk.getImage(Blast.class.getClassLoader().getResource("TankWarImages/blast8.gif")),
				};
		BORN_IMAGES = 
			new Image[]
			{
				tk.getImage(Born.class.getClassLoader().getResource("TankWarImages/born1.gif")),
				tk.getImage(Born.class.getClassLoader().getResource("TankWarImages/born2.gif")),
				tk.getImage(Born.class.getClassLoader().getResource("TankWarImages/born3.gif")),
				tk.getImage(Born.class.getClassLoader().getResource("TankWarImages/born4.gif"))
			};
	}
}
