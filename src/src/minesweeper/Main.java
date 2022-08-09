package minesweeper;
public class Main implements Runnable{
	
	Board board = new Board();
	
	public static void main(String[] args) {
		new Thread(new Main()).start();
		
	}

	@Override
	public void run() {
		while(true) {
			board.repaint();
		}
		
	}

}
