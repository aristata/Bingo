package com.aristatait.Frame;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;

public class MainFrame extends JFrame {

	private final int SIZE = 5; //빙고판의 크기
	private int bingoCnt = 0; //완성된 라인의 수
	
	private JButton[] btnArr = null;
	private boolean[][] bArr = new boolean[SIZE][SIZE]; //빙고판 체크여부 확인을 위한 배열
	
	public MainFrame() {
		setTitle("빙고 게임");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridLayout(SIZE, SIZE));
		
		MyEventHandler handler = new MyEventHandler();
		addWindowListener(handler);
		
		btnArr = new JButton[SIZE * SIZE];
		
		for (int i = 0; i < SIZE * SIZE; i++) {
			btnArr[i] = new JButton(i + 1 + "");
			add(btnArr[i]);
			btnArr[i].addActionListener(handler);
		}
		
		setBounds(500, 200, 300, 300);
		setVisible(true);
	}
	
	//배열 bArr을 출력한다.
	private void print(){
		for (int i = 0; i < bArr.length; i++) {
			for (int j = 0; j < bArr.length; j++) {
				if(bArr[i][j]){
					System.out.print("O");
				}else{
					System.out.print("X");
				}
			}
			System.out.println("");
		}
		
	}
	
	//빙고가 완성되었는지를 확인한다.
	private boolean checkBingo(){
		bingoCnt = 0;
		int garoCnt = 0; //가로줄의 O 개수
		int seroCnt = 0; //세로줄의 O 개수
		int crossCnt1 = 0; //대각선의 O 개수
		int crossCnt2 = 0; //역대각선의 O 개수
		
		for (int i = 0; i < bArr.length; i++) {
			for (int j = 0; j < bArr.length; j++) {
				if(bArr[i][j]){
					garoCnt++;
				}
				if(bArr[j][i]){
					seroCnt++;
				}
				if(bArr[i][j] && i == j){
					crossCnt1++;
				}
				if(bArr[i][j] && i + j == SIZE-1){
					crossCnt2++;
				}
				
			}
			if(garoCnt == SIZE){
				bingoCnt++;
			}
			if(seroCnt == SIZE){
				bingoCnt++;
			}
			garoCnt = 0;
			seroCnt = 0;
		}
		if(crossCnt1 == SIZE){
			bingoCnt++;
		}
		if(crossCnt2 == SIZE){
			bingoCnt++;
		}
		System.out.println("빙고 : " + bingoCnt);
		System.out.println("====================");
		
		return bingoCnt >= SIZE;
	}
	
	//inner class
	private class MyEventHandler extends WindowAdapter implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton btn = (JButton) e.getSource();
			int x = 0; //이차원 배열의 x축 값
			int y = 0; //이차원 배열의 y축 값
			for (int i = 0; i < btnArr.length; i++) {
				if(btnArr[i] == btn){
					x = i / 5; //위치값을 5로 나눈 몫은 x좌표
					y = i % 5; //위치값을 5로 나눈 나머지는 y좌표
					if(bArr[x][y]){
						System.out.println("이미 누른 버튼 입니다.");
						break;
					}else{
						bArr[x][y] = true;
						break;
					}
				}
			}
			if(bArr[x][y]){
				btn.setBackground(Color.YELLOW);
			}
			print();
			if(checkBingo()){
				System.out.println("빙고 성공!!");
			}
			
		}
		
		public void windowClosing(WindowEvent e){
			e.getWindow().setVisible(false);
			e.getWindow().dispose();
			System.exit(0);
		}
		
	}
	
	
}
