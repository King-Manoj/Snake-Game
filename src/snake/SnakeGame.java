package snake;

import java.util.*;

public class SnakeGame {
	private final char[][] board;
	
	private final Queue<Node> snakeBody = new LinkedList<>();
	
	private final Queue<Node> foodQueue = new LinkedList<>();
	
	private final Random random = new Random();
	
	private int currentRow = 0;
	
	private int currentCol = 0;
	
	private char currentHead = '>';
		
	public SnakeGame(int rows, int cols) {
		// TODO Auto-generated constructor stub
		board = new char[rows][cols];
		snakeBody.add(new Node(0,0));
		
		board[0][0] = currentHead;
		generateFoodPostion();
		placeFood();
	}

	public void start() {
		Scanner s = new Scanner(System.in);
		printBoard();
		
		while(true) {
			System.out.println("Enter move U D L R");
			char move = s.next().toUpperCase().charAt(0);
			
			if(makeMove(move)) {
				printBoard();
			}
			else {
				System.out.println("Game Over!!!");
				break;
			}
		}
		s.close();
	}
	
	private boolean makeMove(char direction) {
		int newRow = currentRow;
		int newCol = currentCol;
		char newHead;
		
		switch(direction) {
		case 'U' : newRow--; newHead = '^';break;
		case 'D' : newRow++; newHead = '⌄';break;
		case 'L' : newCol--; newHead = '<';break;
		case 'R' : newCol++; newHead = '>';break;
		default:
			System.out.println("Invalid Move! Use U D L R");
			return true;		
		}
		
		if(!isValidMove(newRow,newCol)) {
			return false;
		}
		
		if(board[newRow][newCol] == 'X') {
			placeFood();
		}
		else {
			Node tail = snakeBody.poll();
			board[tail.row][tail.col] = 'S';
		}
		
		board[currentRow][currentCol] = '.';
		
		snakeBody.add(new Node(newRow,newCol));
		currentRow = newRow;
		currentCol = newCol;
		currentHead = newHead;
		board[newRow][newCol] = currentHead;
		
		return true;
	}
	
	private boolean isValidMove(int row, int col) {
		int n = board.length;
		int m = board[0].length;
		return row>=0 && row<n && col>=0 && col<m && board[row][col]!='.';
	}
	
	private void generateFoodPostion() {
		foodQueue.add(new Node(1,0));
		foodQueue.add(new Node(2,2));
		foodQueue.add(new Node(3,4));
		foodQueue.add(new Node(5,2));
		foodQueue.add(new Node(4,5));
		
	}
	private void placeFood() {
		Node food;
		
		if(!foodQueue.isEmpty()) {
			food = foodQueue.poll();
		}
		else {
			do {
				food = new Node(random.nextInt(board.length),random.nextInt(board[0].length));
			}while(board[food.row][food.col] !='S'); 
		}
		board[food.row][food.col] = 'X';
	}
	
	private void printBoard() {
		for(char[] row : board) {
			for(char cell : row) {
				if(cell !='\0')
				System.out.print(cell +" " );
				else
					System.out.print('S' +" " );
			}
			System.out.println();
		}
		System.out.println();
	}
	
}
