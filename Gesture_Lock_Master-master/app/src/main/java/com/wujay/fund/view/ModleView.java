package com.wujay.fund.view;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

public class ModleView extends View {

	public ModleView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public ModleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public ModleView(Context context) {
		super(context);
	}

	private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	private Paint mPaint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
	private void init() {
		mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(1);
        mPaint.setAntiAlias(true);
        
        for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				sCells[i][j] = new Cell(i, j);
			}
		}
	}
	
	private int mSquareWidth;
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int wSize = MeasureSpec.getSize(widthMeasureSpec);
		mSquareWidth = wSize/3;
		
		mPaint2.setColor(Color.WHITE);
        mPaint2.setStyle(Paint.Style.FILL);
        mPaint2.setStrokeWidth(mSquareWidth/3);
        mPaint2.setAntiAlias(true);
		setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
	}
	@Override
	protected void onDraw(Canvas canvas) {
		
		Cell cell = null;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				cell = sCells[i][j];
				int x = cell.getRow()*mSquareWidth+mSquareWidth/2;
				int y = cell.getColumn()*mSquareWidth+mSquareWidth/2;
				if (cell.isSelected()) {
					canvas.drawCircle(x, y, mSquareWidth/3, mPaint2);
				}else {
					canvas.drawCircle(x, y, mSquareWidth/3, mPaint);
				}
			}
		}
	}
	public void setSelectedCell(List<LockPatternView.Cell> list){
		for (LockPatternView.Cell cell : list) {
			sCells[cell.getColumn()][cell.getRow()].setIsSelected(true);
		}
		invalidate();
	}
	
	public Cell[][] sCells = new Cell[3][3];
	/**
	 * Represents a cell in the 3 X 3 matrix of the unlock pattern view.
	 */
	public static class Cell {
		int row;
		int column;

		// keep # objects limited to 9

		/**
		 * @param row
		 *            The row of the cell.
		 * @param column
		 *            The column of the cell.
		 */
		private Cell(int row, int column) {
			checkRange(row, column);
			this.row = row;
			this.column = column;
		}

		public int getRow() {
			return row;
		}

		public int getColumn() {
			return column;
		}

		private boolean mIsSelected = false;
		public boolean isSelected(){
			return mIsSelected;
		}
		
		public void setIsSelected(boolean a){
			mIsSelected = a;
		}
//		/**
//		 * @param row
//		 *            The row of the cell.
//		 * @param column
//		 *            The column of the cell.
//		 */
//		public synchronized Cell of(int row, int column) {
//			checkRange(row, column);
//			return sCells[row][column];
//		}

		private void checkRange(int row, int column) {
			if (row < 0 || row > 2) {
				throw new IllegalArgumentException("row must be in range 0-2");
			}
			if (column < 0 || column > 2) {
				throw new IllegalArgumentException(
						"column must be in range 0-2");
			}
		}

		public String toString() {
			return "(row=" + row + ",clmn=" + column + ")";
		}
	}
	
}
