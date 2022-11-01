package experimentation;

import java.util.ArrayList;

public class confusionMatrix {
	private ArrayList<String> categories = new ArrayList<>();
	private Integer [][] matrix;
	private int columns = 0;
	private int rows = 0;
	
	public confusionMatrix (ArrayList<String> paramCategories) {
		categories = paramCategories;
		columns = categories.size();
		rows = categories.size() - 1;
		matrix = new Integer[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
            	matrix[i][j] = 0;
            }
        }
}
	
	public void addValue(String actualValue, String predictValue) {
		int actualValueIndex = categories.indexOf(actualValue);
		int predictValueIndex = categories.indexOf(predictValue);
		matrix[actualValueIndex][predictValueIndex]++;
	}
	
	public void print() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
	}
	
	public int getSizeRow() {
		return rows;
	}
	
	public int getSizeCol() {
		return columns;
	}
	
	public int getSumMainDiagonal() {
		int sum = 0;
        for (int i = 0; i < rows; i++) {
            sum += matrix[i][i];
        }
        return sum;
	}
	
	public int getElementsSquareMatrix() {
		int sum = 0;
        for (int i = 0; i < rows; i++) {
        	for (int j = 0; j < rows; j++) {
                sum += matrix[i][j];
        	}
        }
        return sum;
	}
	
	public int getTotalElements() {
		int sum = 0;
        for (int i = 0; i < rows; i++) {
        	for (int j = 0; j < columns; j++) {
                sum += matrix[i][j];
        	}
        }
        return sum;
	}
}
