package minesweeper;

import javax.swing.*;
import javax.swing.Timer;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class Board extends JFrame{
	
	int spacing = 2;
	
	public int x = 0;
	public int y = 0;
	
	public char[][] mine_setting = new char[15][15];
	boolean[][] revealed = new boolean[15][15];
	boolean[][] flagged = new boolean[15][15];
	
	ImageIcon icon = new ImageIcon("img/title_bar_icon.png");
    Image img = icon.getImage();
    ImageIcon num0 = new ImageIcon("img/zero.png");
    Image zero = num0.getImage();
    ImageIcon num1 = new ImageIcon("img/one.png");
    Image one = num1.getImage();
    ImageIcon num2 = new ImageIcon("img/two.png");
    Image two = num2.getImage();
    ImageIcon num3 = new ImageIcon("img/three.png");
    Image three = num3.getImage();
    ImageIcon num4 = new ImageIcon("img/four.png");
    Image four = num4.getImage();
    ImageIcon num5 = new ImageIcon("img/five.png");
    Image five = num5.getImage();
    ImageIcon num6 = new ImageIcon("img/six.png");
    Image six = num6.getImage();
    ImageIcon num7 = new ImageIcon("img/seven.png");
    Image seven = num7.getImage();
    ImageIcon num8 = new ImageIcon("img/eigth.png");
    Image eight = num8.getImage();
    ImageIcon num9 = new ImageIcon("img/nine.png");
    Image nine = num9.getImage();
    ImageIcon mine = new ImageIcon("img/bomb.png");
    Image bomb = mine.getImage();
    ImageIcon flagg = new ImageIcon("img/flag.png");
    Image flag = flagg.getImage();
    
    ImageIcon btn_img = new ImageIcon("img/btn_icon.png");
    Image btn_icon = btn_img.getImage();
    Image resizeing = btn_icon.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
    ImageIcon resized = new ImageIcon(resizeing);
    
    JButton btn = new JButton(resized);
    
	JLabel count = new JLabel();
	JLabel flags = new JLabel();
	
	int flag_count = 25;
	
	Maps maps = new Maps();
	
	int elapsedTime = 0;
	int sec = 0;
	int min = 0;
	int hou = 0;

	String sec_str = String.format("%02d", sec);
	String min_str = String.format("%02d", min);
	String hou_str = String.format("%02d", hou);
	Timer timer = new Timer(1000, new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			elapsedTime = elapsedTime + 1000;
			hou = (elapsedTime / 3600000);
			min = (elapsedTime / 60000) % 60;
			sec = (elapsedTime / 1000) % 60;
			sec_str = String.format("%02d", sec);
			min_str = String.format("%02d", min);
			hou_str = String.format("%02d", hou);
			count.setText(hou_str + ":" + min_str + ":" + sec_str);
		}
		
	});
	
	public Board() {
		
		this.setTitle("Minesweeper"); 
		this.setSize(468, 521); 
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 프로그램 종료시 컴퓨터 메모리에서 제거
		this.setVisible(true); 
		this.setResizable(false);
		this.setLocationRelativeTo(null); // 프로그램 실행시 화면 가운데 표시
		
		Field field = new Field();
		
		this.setContentPane(field);
		
	    this.setIconImage(img);
	  
		maps.Setting();
		for(int i = 0; i < 15; i++) {
			for(int j = 0; j < 15; j++) {
				revealed[i][j] = false;
				mine_setting[i][j] = maps.bombMap[i][j];
				System.out.print(mine_setting[i][j]);
				
			}
			System.out.println();
		}
		
		Point point = new Point();
		Click click = new Click();
		
		field.addMouseMotionListener(point);
		field.addMouseListener(click);
	}
	
	public class Field extends JPanel implements ActionListener{
		
		public void paintComponent(Graphics g){
			
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, 460, 490);
			
			btn.setBounds(211, 0, 30, 30);
			btn.setBorderPainted(false);
			btn.setBackground(Color.WHITE);
			btn.addActionListener(this);
			this.add(btn);
			
			g.drawImage(flag, 2, 0, 30, 30, null);
			
			if(flag_count <= 0) {
				flags.setText("x " + "0");
			}else {
				flags.setText("x " + Integer.toString(flag_count));
			}
			
			flags.setBounds(34, 0, 50, 30);
			flags.setFont(new Font("Verdana", Font.BOLD, 14));
			this.add(flags); 
			
			count.setText(hou_str + ":" + min_str + ":" + sec_str);
			count.setBounds(376, 0, 80, 30);
			count.setFont(new Font("Verdana", Font.BOLD, 14));
			this.add(count); 
			timer.start();
						
			for(int i = 0; i < 15; i++) {
				for(int j = 0; j < 15; j++) {
					
					g.setColor(Color.GRAY);
					
//					if(mine_setting[i][j] == '*') {
//						g.setColor(Color.DARK_GRAY);
//					}else {
//						g.setColor(Color.GRAY);
//					}
					
					if(x >=  i * 30 && x < i * 30 + 30
							&& y >= j * 30 + 30 && y < j * 30 + 60) {
						g.setColor(Color.LIGHT_GRAY);
					}
					
					g.fillRect(spacing + i * 30, spacing + j * 30 + 30, 30 - 1 * spacing, 30 - 1 * spacing);
				
					for(int m = 0; m < 15; m++) {
						for(int n = 0; n < 15; n++) {
							if(revealed[m][n] == true && flagged[m][n] == false) {
								if(mine_setting[m][n] == '*') {
									openmine();
									g.clearRect(spacing + m * 30, spacing + n * 30 + 30, 30 - 1 * spacing, 30 - 1 * spacing);
									g.drawImage(bomb, m * 30 + 2, n * 30 + 32, 28, 28, null);
									timer.stop();
									
								}else if(mine_setting[m][n] == '0') {
									g.clearRect(spacing + m * 30, spacing + n * 30 + 30, 30 - 1 * spacing, 30 - 1 * spacing);
									g.drawImage(zero, m * 30 - 1, n * 30 + 29, 34, 34, null);
								}else if(mine_setting[m][n] == '1') {
									g.clearRect(spacing + m * 30, spacing + n * 30 + 30, 30 - 1 * spacing, 30 - 1 * spacing);
									g.drawImage(one, m * 30 - 1, n * 30 + 29, 34, 34, null);
								}else if(mine_setting[m][n] == '2') {
									g.clearRect(spacing + m * 30, spacing + n * 30 + 30, 30 - 1 * spacing, 30 - 1 * spacing);
									g.drawImage(two, m * 30 - 1, n * 30 + 29, 34, 34, null);
								}else if(mine_setting[m][n] == '3') {
									g.clearRect(spacing + m * 30, spacing + n * 30 + 30, 30 - 1 * spacing, 30 - 1 * spacing);
									g.drawImage(three, m * 30 - 1, n * 30 + 29, 34, 34, null);
								}else if(mine_setting[m][n] == '4') {
									g.clearRect(spacing + m * 30, spacing + n * 30 + 30, 30 - 1 * spacing, 30 - 1 * spacing);
									g.drawImage(four, m * 30 - 1, n * 30 + 29, 34, 34, null);
								}else if(mine_setting[m][n] == '5') {
									g.clearRect(spacing + m * 30, spacing + n * 30 + 30, 30 - 1 * spacing, 30 - 1 * spacing);
									g.drawImage(five, m * 30 - 1, n * 30 + 29, 34, 34, null);
								}else if(mine_setting[m][n] == '6') {
									g.clearRect(spacing + m * 30, spacing + n * 30 + 30, 30 - 1 * spacing, 30 - 1 * spacing);
									g.drawImage(six, m * 30 - 1, n * 30 + 29, 34, 34, null);
								}else if(mine_setting[m][n] == '7') {
									g.clearRect(spacing + m * 30, spacing + n * 30 + 30, 30 - 1 * spacing, 30 - 1 * spacing);
									g.drawImage(seven, m * 30 - 1, n * 30 + 29, 34, 34, null);
								}else if(mine_setting[m][n] == '8') {
									g.clearRect(spacing + m * 30, spacing + n * 30 + 30, 30 - 1 * spacing, 30 - 1 * spacing);
									g.drawImage(eight, m * 30 - 1, n * 30 + 29, 34, 34, null);
								}else if(mine_setting[m][n] == '9') {
									g.clearRect(spacing + m * 30, spacing + n * 30 + 30, 30 - 1 * spacing, 30 - 1 * spacing);
									g.drawImage(nine, m * 30 - 1, n * 30 + 29, 34, 34, null);
								}
							}else if(revealed[m][n] == false && flagged[m][n] == true) {
								if(flag_count >= 0) {
									g.drawImage(flag, m * 30 + 2, n * 30 + 32, 28, 28, null);
								}else if(flag_count == -1){
									//JOptionPane.showMessageDialog(null, "");
								}
							}
						}
					}				
				}
			}
		}
		
		public void openmine() {
			
			for(int i = 0; i < 15; i++) {
				for(int j = 0; j < 15; j++) {
					if(mine_setting[i][j] == '*') {
						if(flagged[i][j] == true) {
							flagged[i][j] = false;
						}
						revealed[i][j] = true;
					}
				}
			}
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource() == btn) {
				restart();
			}
		}
		
		void restart() {
			timer.restart();
			flag_count = 25;
			elapsedTime = 0;
			sec = 0;
			min = 0;
			hou = 0;
			sec_str = String.format("%02d", sec);
			min_str = String.format("%02d", min);
			hou_str = String.format("%02d", hou);
			count.setText(hou_str + ":" + min_str + ":" + sec_str);
			maps.Setting();
			for(int i = 0; i < 15; i++) {
				for(int j = 0; j < 15;  j++) {
					revealed[i][j] = false;
					flagged[i][j] = false;
					mine_setting[i][j] = maps.bombMap[i][j];
				}
			}
		}	
		
	}
	
	public class Click implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			if(SwingUtilities.isRightMouseButton(e)) {
				if(boxx() != -1 && boxy() != -1) {
					if(revealed[boxx()][boxy()] == false) {
						if(flagged[boxx()][boxy()] == false) {
							flagged[boxx()][boxy()] = true;
							if(flag_count >= 0) {
								flag_count--;
							}
						}else {
							flagged[boxx()][boxy()] = false;
							flag_count++;
						}
					}
				}	
			}else {
				if(boxx() != -1 && boxy() != -1) {
					if(flagged[boxx()][boxy()] == false) {
						revealed[boxx()][boxy()] = true;
					}
				}
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public class Point implements MouseMotionListener{

		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
			x = e.getX();
			y = e.getY();
			//System.out.println("x: " + x + " y: " + y);
		}
		
	}
	
	public int boxx() {
		for(int i = 0; i < 15; i++) {
			for(int j = 0; j < 15; j++) {
				if(x >=  i * 30 && x < i * 30 + 30
						&& y >= j * 30 + 30 && y < j * 30 + 60) {
					return i;
				}
			}
		}
		return -1;
	}
	
	public int boxy() {
		for(int i = 0; i < 15; i++) {
			for(int j = 0; j < 15; j++) {
				if(x >=  i * 30 && x < i * 30 + 30
						&& y >= j * 30 + 30 && y < j * 30 + 60) {
					return j;
				}
			}
		}
		return -1;
	}
	
}


