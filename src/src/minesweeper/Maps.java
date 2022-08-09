package minesweeper;
import java.util.Random;

public class Maps {
	
	public char[][] bombMap = new char[15][15];
	
	static int[] x= {1, -1, 0, 0, 1, 1, -1, -1};
	
	static int[] y= {0, 0, 1, -1, 1, -1, 1, -1};
	
	public void Setting() {
		
		Random mine = new Random();
		
		int[][] arr = new int[15][15];
		
		while(true) { // 폭탄이 15개보다 적게 만들어질 경우 대비 무한반복
			
			int mine_count = 0;
			for(int i = 0; i < 15; i++) {
				for(int j = 0; j < 15; j++) {
					arr[i][j] = mine.nextInt(2); //해당 인덱스를 랜덤하게 폭탄 or 폭탄 아닌 곳으로(0~1) 지정
					
					if(mine_count > 24) {
						arr[i][j] = 0; //폭탄이 15개까지 만들어지면 나머지는 전부 폭탄아닌 곳으로 생성
					}
					
					if(arr[i][j] == 1) { //해당 인덱스가 폭탄이면 폭탄갯수 카운트
						mine_count++;
						
					}
				}
			}
			if(mine_count == 25) { // 폭탄이 15개 만들어졌으면 무한반복 종료, 아니면 다시 [0][0]부터 시작
				//System.out.println(mine_count);
				break;
			}
		}
		
		//생성된 15개의 폭탄을 랜덤하게 섞음
		int index = 0;
		int r, t;
		
		for(int i = 0; i < 15; i++) {
			for(int j = 0; j < 15; j++) {
				r = mine.nextInt(15); 
				t = arr[index][j]; 
				arr[index][j] = arr[r][j];
				arr[r][j] = t;
			}
		}
		
		for(int i = 0; i < 15; i++) {
			for(int j = 0; j < 15; j++) {
				if(arr[i][j] == 1) {
					bombMap[i][j] = '*';
				}else {
					bombMap[i][j] = '.';
				}
			}
		}
		
		for(int p = 0; p < 2; p++){
            for(int i = 0; i < 15; i++){
                for(int j = 0; j < 15; j++){
                    if(p == 0){ // 첫번재 반복문에서 지뢰가 아닌 값들을 '0'으로 표시해줌
                        if(bombMap[i][j] == '.'){
                            bombMap[i][j] = '0';
                        }
                    }else{
                        if(bombMap[i][j] == '*'){ // 해당 값이 지뢰인 경우 주변에 지뢰를 제외한 값들 ('0'이 들어간 값)에 1씩 더해줌
                            for(int k = 0; k < 8; k++){
                                int a = i + x[k];
						        int b = j + y[k];
                                if(a >= 0 && b >= 0 && a < 15 && b < 15) {
                                    if(bombMap[a][b] != '*') {
                                        bombMap[a][b] += 1;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
	} //Setting
}
