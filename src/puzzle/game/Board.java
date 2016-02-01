package puzzle.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.handler.timer.ITimerCallback;
import org.anddev.andengine.engine.handler.timer.TimerHandler;
import org.anddev.andengine.entity.modifier.MoveModifier;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.util.modifier.ease.EaseExponentialOut;

import puzzle.hill_climbing.Pasos;

import android.util.Log;

public class Board {

	// ===========================================================
	// Constants
	// ===========================================================

	private final String LEFT_MOVEMENT = "left";
	private final String RIGHT_MOVEMENT = "right";
	private final String UP_MOVEMENT = "up";
	private final String DOWN_MOVEMENT = "down";

	// ===========================================================
	// Fields
	// ===========================================================

	private MoveModifier moveModifier;
	private int matrix[][];
	private int positionsInX[][];
	private int[][] positionsInY;
	private HashMap<Integer, Square> collSquares;
	private final int LENGTH;
	private volatile boolean stopAnimation;
	private Engine engine;
	private boolean threadIsRunning;

	// ===========================================================
	// Constructors
	// ===========================================================

	/**
	 * Constructor
	 * 
	 * @param matrix
	 * @param scene
	 * @param pX
	 * @param pY
	 */
	public Board(int matrix[][], Scene scene, int pX, int pY, Engine engine) {
		this.engine = engine;
		this.matrix = matrix;
		this.LENGTH = matrix.length;
		this.positionsInX = new int[LENGTH][LENGTH];
		this.positionsInY = new int[LENGTH][LENGTH];
		collSquares = new HashMap<Integer, Square>(8);
		this.stopAnimation = false;
		this.threadIsRunning = false;

		int pXtexture = TextureManager.getPuzzleNumbers().getTexturePositionX();
		int pYtexture = TextureManager.getPuzzleNumbers().getTexturePositionY();
		final int POS_X_TX_I = pXtexture;
		final int POS_X_I = pX;
		final int WIDTH = 64;
		final int HEIGHT = 64;

		/*****************************
		 * Obtaining textures
		 *****************************/
		TextureReloaded texture = TextureManager.getTexture();
		ArrayList<TextureRegion> texturesRegionSquares = new ArrayList<TextureRegion>(
				8);
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				texturesRegionSquares.add(TextureRegionFactory
						.extractFromTexture(texture, pXtexture, pYtexture,
								WIDTH, HEIGHT));
				pXtexture += WIDTH;
			}
			pYtexture += HEIGHT;
			pXtexture = POS_X_TX_I;
		}

		/*****************************
		 * Placing Squares
		 *****************************/
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				final int NUMBER = matrix[i][j];
				// Log.d("Debugging", "array i:"+i+" j:"+j+" #:"+NUMBER);
				this.positionsInX[i][j] = pX;
				this.positionsInY[i][j] = pY;
				if (NUMBER == 0) {
					pX += WIDTH;
					continue;
				}
				int index = NUMBER - 1;
				Square square = new Square(pX, pY,
						texturesRegionSquares.get(index), NUMBER);
				square.setBoard(this);
				this.collSquares.put(NUMBER, square);
				scene.attachChild(square);
				scene.registerTouchArea(square);
				pX += WIDTH;
			}
			pY += HEIGHT;
			pX = POS_X_I;
		}

	}

	// ===========================================================
	// Getters & Setters
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	// ===========================================================
	// Public Methods
	// ===========================================================

	/**
	 * 
	 * @return
	 */
	public boolean animationIsFinished() {
		return this.moveModifier == null || this.moveModifier.isFinished();
	}

	/**
	 * 
	 * @param NUMBER
	 */
	public void checkSquareAndMove(final int NUMBER) {

		final int LENGHT = matrix.length;
		for (int i = 0; i < LENGHT; i++) {
			for (int j = 0; j < LENGHT; j++) {
				if (matrix[i][j] == NUMBER) {

					final int SPACE = 0;

					/******************
					 * Left Movement
					 ******************/
					int left = j - 1;
					if (left >= 0 && matrix[i][left] == SPACE) {
						// Log.d("Debugging", "Left Movement");
						this.moveSquare(NUMBER, LEFT_MOVEMENT);
						matrix[i][left] = NUMBER;
						matrix[i][j] = SPACE;
						return;
					}

					/********************
					 * Right Movement
					 *******************/
					int right = j + 1;
					if (right < LENGHT && matrix[i][right] == SPACE) {
						// Log.d("Debugging", "Right Movement");
						this.moveSquare(NUMBER, RIGHT_MOVEMENT);
						matrix[i][right] = NUMBER;
						matrix[i][j] = SPACE;
						return;
					}

					/*********************
					 * Up Movement
					 *********************/
					int up = i - 1;
					if (up >= 0 && matrix[up][j] == SPACE) {
						// Log.d("Debugging", "Up Movement");
						this.moveSquare(NUMBER, UP_MOVEMENT);
						matrix[up][j] = NUMBER;
						matrix[i][j] = SPACE;
						return;
					}

					/**********************
					 * Down Movement
					 **********************/
					int down = i + 1;
					if (down < LENGHT && matrix[down][j] == SPACE) {
						// Log.d("Debugging", "Down Movement");
						this.moveSquare(NUMBER, DOWN_MOVEMENT);
						matrix[down][j] = NUMBER;
						matrix[i][j] = SPACE;
						return;
					}

				}
			}
		}

	}

	/**
	 * 
	 * @param NUMBER
	 * @param MOVEMENT
	 */
	public void moveSquare(final int NUMBER, final String MOVEMENT) {

		if (NUMBER == 0) {
			return;
		}

		Square square = this.collSquares.get(NUMBER);

		// final float TIME = .625f; // in seconds
		final float TIME = .300f; // in seconds
		final float WIDTH = square.getWidth();
		final float HEIGHT = square.getHeight();
		float pX = square.getX();
		float pY = square.getY();
		float pXf = pX;
		float pYf = pY;

		if (MOVEMENT == LEFT_MOVEMENT) {
			pXf -= WIDTH;
		} else if (MOVEMENT == RIGHT_MOVEMENT) {
			pXf += WIDTH;
		} else if (MOVEMENT == UP_MOVEMENT) {
			pYf -= HEIGHT;
		} else if (MOVEMENT == DOWN_MOVEMENT) {
			pYf += HEIGHT;
		} else {
			Log.d("Debugging", "Undefined Movement");
			return;
		}

		this.moveModifier = new MoveModifier(TIME, pX, pXf, pY, pYf,
				EaseExponentialOut.getInstance());
		square.registerEntityModifier(this.moveModifier);
	}

	/**
	 * 
	 * @return
	 */
	public int[][] getDeepMatrixClone() {
		int matrixClone[][] = new int[LENGTH][LENGTH];
		for (int i = 0; i < LENGTH; i++) {
			for (int j = 0; j < LENGTH; j++) {
				matrixClone[i][j] = matrix[i][j];
			}
		}
		return matrixClone;
	}

	/**
	 * 
	 * @param initialMatrix
	 */
	public void reset(int initialMatrix[][]) {
		for (int i = 0; i < LENGTH; i++) {
			for (int j = 0; j < LENGTH; j++) {
				final int NUMBER = initialMatrix[i][j];
				Square square = this.collSquares.get(NUMBER);
				if (square == null) {
					continue;
				}
				final float TIME = .625f; // in seconds
				float pX = square.getX();
				float pY = square.getY();
				float pXf = positionsInX[i][j];
				float pYf = positionsInY[i][j];
				this.moveModifier = new MoveModifier(TIME, pX, pXf, pY, pYf,
						EaseExponentialOut.getInstance());
				square.registerEntityModifier(this.moveModifier);
			}
		}
		this.matrix = initialMatrix;
	}

	/**
	 * 
	 * @param numbersToMove
	 */
	public void animateSolution(final Queue<Integer> numbersToMove) {

		if (threadIsRunning) {
			return;
		}

		new Thread(new Runnable() {
			public void run() {

				Stack<Integer> stack = new Stack<Integer>();

				while (!numbersToMove.isEmpty()) {

					try {
						int NUMBER = numbersToMove.poll();
						// Log.d("Debugging", "Al salir#:"+NUMBER);
						stack.push(NUMBER);
					} catch (NullPointerException e) {
						break;
					}
					;
				}

				while (!stack.isEmpty()) {
					while (Board.this.moveModifier.isFinished()) {

						if (stopAnimation) {
							numbersToMove.clear();
							stack.clear();
							return;
						}

						try {
							int NUMBER = stack.pop();
							// Log.d("Debugging", "Al entrar#:"+NUMBER);
							checkSquareAndMove(NUMBER);
						} catch (NullPointerException e) {
							break;
						}
						;
					}
				}

				threadIsRunning = false;

			}
		}).start();

	}

	/**
	 * 
	 * @param numbersToMove
	 */
	public void animateSolutionWithSteps(final Queue<Pasos> numbersToMove) {

		if (threadIsRunning) {
			return;
		}

		new Thread(new Runnable() {
			public void run() {

				while (!numbersToMove.isEmpty()) {

					while (Board.this.moveModifier.isFinished()) {

						if (stopAnimation) {
							numbersToMove.clear();
							return;
						}

						try {
							Pasos step = numbersToMove.poll();
							int NUMBER = step.getNumero();
							// String MOVEMENT = step.getMovimiento();
							// moveSquare(NUMBER, MOVEMENT);
							checkSquareAndMove(NUMBER);
						} catch (NullPointerException e) {
							break;
						}
						;
					}
				}
				threadIsRunning = false;

			}
		}).start();

	}

	public void stopAmination() {
		this.stopAnimation = true;
	}

	// ===========================================================
	// Private Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================

}