package minesweeper;
import java.util.Random;

public class Maps {
	
	public char[][] bombMap = new char[15][15];
	
	static int[] x= {1, -1, 0, 0, 1, 1, -1, -1};
	
	static int[] y= {0, 0, 1, -1, 1, -1, 1, -1};
	
	public void Setting() {
		
		Random mine = new Random();
		
		int[][] arr = new int[15][15];
		
		while(true) { // ��ź�� 15������ ���� ������� ��� ��� ���ѹݺ�
			
			int mine_count = 0;
			for(int i = 0; i < 15; i++) {
				for(int j = 0; j < 15; j++) {
					arr[i][j] = mine.nextInt(2); //�ش� �ε����� �����ϰ� ��ź or ��ź �ƴ� ������(0~1) ����
					
					if(mine_count > 24) {
						arr[i][j] = 0; //��ź�� 15������ ��������� �������� ���� ��ź�ƴ� ������ ����
					}
					
					if(arr[i][j] == 1) { //�ش� �ε����� ��ź�̸� ��ź���� ī��Ʈ
						mine_count++;
						
					}
				}
			}
			if(mine_count == 25) { // ��ź�� 15�� ����������� ���ѹݺ� ����, �ƴϸ� �ٽ� [0][0]���� ����
				//System.out.println(mine_count);
				break;
			}
		}
		
		//������ 15���� ��ź�� �����ϰ� ����
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
                    if(p == 0){ // ù���� �ݺ������� ���ڰ� �ƴ� ������ '0'���� ǥ������
                        if(bombMap[i][j] == '.'){
                            bombMap[i][j] = '0';
                        }
                    }else{
                        if(bombMap[i][j] == '*'){ // �ش� ���� ������ ��� �ֺ��� ���ڸ� ������ ���� ('0'�� �� ��)�� 1�� ������
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
