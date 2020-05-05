package net.hibiznet.test;

import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class JframeTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new JframeTest();
	}
	
	JFrame mainwindow;
	BufferStrategy buffer;
	BufferedImage img_back, img_cloud, img_jiki, img_teki, img_title;
	boolean gamestate = false;
	boolean key_space = false;
	
	int[] cx = {480, 860};
	int[] cy = {0, 80};
	
	// ��������  ����.
	public JframeTest() {
		this.mainwindow = new JFrame("�V�� ����");
		this.mainwindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.mainwindow.setVisible(true);
		// ��������â ũ�� ����.
		Insets in = this.mainwindow.getInsets();
		this.mainwindow.setSize(480 + in.left + in.right, 360 + in.top + in.bottom);
		// ���콺&Ű����
		this.mainwindow.addMouseListener(new GameMouseAdapter());
		
		try {
			//System.out.println(">>> path : " + url.getPath() + " <<<");
			//File imageFile = new File("assets/images/hair_style01.jpg");
			// �̹��� �ε�
			this.img_back = 
					ImageIO.read(JframeTest.class.getClass().getResource("/images/s_back.png"));
			this.img_cloud = 
					ImageIO.read(JframeTest.class.getClass().getResource("/images/s_cloud.png"));
			this.img_jiki = 
					ImageIO.read(JframeTest.class.getClass().getResource("/images/s_jiki.png"));
			this.img_teki = 
					ImageIO.read(JframeTest.class.getClass().getResource("/images/s_teki.png"));
			this.img_title = 
					ImageIO.read(JframeTest.class.getClass().getResource("/images/s_title.png"));			
			
		} catch(Exception e) {
			e.getStackTrace();
		}
		// ���� �ۼ�.
		this.mainwindow.setIgnoreRepaint(true);
		this.mainwindow.createBufferStrategy(2);
		this.buffer = this.mainwindow.getBufferStrategy();
		
		// Ÿ�̸� �ۼ�
		Timer t = new Timer();
		t.schedule(new GameTask(), 0, 50);
	}
	
	void drawGameMain(Graphics g) {
		
	}
	
	void drawGameTitle(Graphics g) {
		g.drawImage(this.img_title,
			240 - this.img_title.getWidth() /2,
			180 - this.img_title.getHeight() /2,
			this.mainwindow);
	}
	
	class GameTask extends TimerTask {
		
		public void run() {
			//System.out.println("ȣ���");
			if (buffer.contentsLost() == false) {
				Graphics g = buffer.getDrawGraphics();
				Insets in = mainwindow.getInsets();
				g.translate(in.left, in.top);
				g.drawImage(img_back, 0, 0, mainwindow);
				// �����׸�
				for(int i=0; i<cx.length; i++) {
					cx[i] -= 2;
					if(cx[i]<-409) cx[i] = 480;
					g.drawImage(img_cloud, cx[i], cy[i], mainwindow);
				}
				// ���� ����.
				if(gamestate == false) drawGameTitle(g);
				else drawGameMain(g);
				// ���� ��������.
				g.dispose();
				// ���� ��ȯ.
				buffer.show();
			} else {
				// 10�� �����ϰ� getDrawGraphics�� ����.
				while(buffer.contentsRestored() == false) {
					Graphics g = buffer.getDrawGraphics();
					g.dispose();
					try {
						Thread.sleep(10);
					} catch(InterruptedException e) {}
				}
			}
			
		}
	}
	
	class GameMouseAdapter extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			//super.mouseClicked(e);
			//System.out.println("Ŭ����!");
			if(gamestate == false) {
				gamestate = true;
			}
			
		}
		
	}
	
	class GameKeyAdapter extends KeyAdapter {
		
		@Override
		public void keyPressed(KeyEvent arg0) {
			if(arg0.getKeyCode() == KeyEvent.VK_SPACE) {
				key_space = false;
			}
		}
		@Override
		public void keyReleased(KeyEvent arg0) {
			if(arg0.getKeyCode() == KeyEvent.VK_SPACE) {
				key_space = false;
			}
		}
	}
	
}
