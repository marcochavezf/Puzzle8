/**
 * 
 */
package puzzle.game;

import java.util.LinkedList;
import java.util.Queue;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.entity.primitive.Line;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

import android.util.Log;
import puzzle.bfs.BfsMan;
import puzzle.bfs.BfsMis;
import puzzle.hill_climbing.Pasos;
import puzzle.hill_climbing.Arbolito;

/**
 * @author Marco
 * 
 */
public class Puzzle8Scene extends Scene {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private SelectionButton buttonBreadth;
	private SelectionButton buttonHillClimbing;
	private SelectionButton buttonBFS;
	private SelectionButton buttonFunction1;
	private SelectionButton buttonFunction2;
	private Board solutionBoard;
	private Board initialBoard;
	private Board goalBoard;

	// ===========================================================
	// Constructors
	// ===========================================================

	public Puzzle8Scene(int CAMERA_WIDTH, Engine engine) {

		Scene scene = this;

		/**************************
		 * Creating Lines
		 *************************/
		int widthSpace = 16;
		scene.attachChild(new Line(widthSpace, 272, CAMERA_WIDTH - widthSpace,
				272, 4));
		scene.attachChild(new Line(widthSpace, 512, CAMERA_WIDTH - widthSpace,
				512, 4));

		/**************************
		 * Creating Titles
		 *************************/
		scene.attachChild(new Sprite(80, 16, TextureManager
				.getTitleInitialState()));
		scene.attachChild(new Sprite(304, 16, TextureManager
				.getTitleGoalState()));
		scene.attachChild(new Sprite(192, 288, TextureManager
				.getTitleSearching()));
		scene.attachChild(new Sprite(192, 400, TextureManager
				.getTitleFunctions()));
		scene.attachChild(new Sprite(304, 528, TextureManager
				.getTitleSolution()));

		/**************************
		 * Creating Selection Buttons
		 *************************/
		this.buttonBreadth = new SelectionButton(64, 336,
				TextureManager.getButtonBreadth());
		this.buttonHillClimbing = new SelectionButton(192, 336,
				TextureManager.getButtonHillClimbing());
		this.buttonBFS = new SelectionButton(320, 336,
				TextureManager.getButtonBFS());
		this.buttonFunction1 = new SelectionButton(128, 448,
				TextureManager.getButtonFunction1());
		this.buttonFunction2 = new SelectionButton(256, 448,
				TextureManager.getButtonFunction2());
		this.buttonBreadth.switchOn();
		scene.attachChild(this.buttonBreadth);
		scene.attachChild(this.buttonHillClimbing);
		scene.attachChild(this.buttonBFS);
		scene.attachChild(this.buttonFunction1);
		scene.attachChild(this.buttonFunction2);
		scene.registerTouchArea(this.buttonBreadth);
		scene.registerTouchArea(this.buttonHillClimbing);
		scene.registerTouchArea(this.buttonBFS);
		scene.registerTouchArea(this.buttonFunction1);
		scene.registerTouchArea(this.buttonFunction2);
		this.buttonBreadth.setPuzzleScene(this);
		this.buttonHillClimbing.setPuzzleScene(this);
		this.buttonBFS.setPuzzleScene(this);
		this.buttonFunction1.setPuzzleScene(this);
		this.buttonFunction2.setPuzzleScene(this);

		/**************************
		 * Creating Action Buttons
		 *************************/
		Sprite startButton = new Sprite(80, 560,TextureManager.getButtonStart()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				
				Puzzle8Scene.this.solvePuzzle();
				
				return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
						pTouchAreaLocalY);
			}

		};
		scene.attachChild(startButton);
		scene.registerTouchArea(startButton);
		Sprite resetButton = new Sprite(80, 672,
				TextureManager.getButtonReset()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				Puzzle8Scene.this.resetPuzzle();
				return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
						pTouchAreaLocalY);
			}
		};
		scene.attachChild(resetButton);
		scene.registerTouchArea(resetButton);

		/**************************
		 * Creating Boards
		 *************************/
		int initialBoardMatrix[][] = { { 3, 6, 7 }, { 4, 0, 8 }, { 5, 2, 1 } };
		int pXinitialBoard = 32;
		int pYinitialBoard = 64;
		initialBoard = new Board(initialBoardMatrix, scene, pXinitialBoard,
				pYinitialBoard, engine);

		int goalBoardMatrix[][] = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 } };
		int pXgoalBoard = 256;
		int pYgoalBoard = 64;
		goalBoard = new Board(goalBoardMatrix, scene, pXgoalBoard, pYgoalBoard,
				engine);

		int solutionBoardMatrix[][] = initialBoard.getDeepMatrixClone();
		int pXSolutionBoard = 256;
		int pYSolutionBoard = 576;
		solutionBoard = new Board(solutionBoardMatrix, scene, pXSolutionBoard,
				pYSolutionBoard, engine);

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
	 * @param button
	 */
	public void onButtonPressed(SelectionButton button) {

		if (button.equals(buttonBreadth)) {
			this.buttonHillClimbing.switchOff();
			this.buttonBFS.switchOff();
			this.buttonFunction1.switchOff();
			this.buttonFunction2.switchOff();
		} else if (button.equals(buttonHillClimbing)) {
			this.buttonBreadth.switchOff();
			this.buttonBFS.switchOff();
		} else if (button.equals(buttonBFS)) {
			this.buttonHillClimbing.switchOff();
			this.buttonBreadth.switchOff();
		}

		if (buttonBreadth.isPressed()) {
			this.buttonFunction1.switchOff();
			this.buttonFunction2.switchOff();
		} else {
			if (button.equals(buttonFunction1)) {
				this.buttonFunction2.switchOff();
			} else if (button.equals(buttonFunction2)) {
				this.buttonFunction1.switchOff();
			}
		}
	}

	// ===========================================================
	// Private Methods
	// ===========================================================

	private void solvePuzzle() {

		Queue<Integer> numbersToMove = new LinkedList<Integer>();
		Queue<Pasos> steps = null;
		int[][] initialMatrix = initialBoard.getDeepMatrixClone();
		int[][] goalMatrix = goalBoard.getDeepMatrixClone();

		if (this.buttonBreadth.isPressed()) {
			// Make Breadth Search
		} else if (this.buttonHillClimbing.isPressed()) {

			try {
				Arbolito dududu = new Arbolito(initialMatrix, goalMatrix);

				if (this.buttonFunction1.isPressed()) {
					// Hill Climbing with the first heuristic function
					steps = dududu.resolver(dududu.getInicio());

				} else if (this.buttonFunction2.isPressed()) {
					// Hill Climbing with the second heuristic function
					steps = dududu.resolver2(dududu.getInicio());
					
				} else {
					return;
				}

			} catch (Exception e) {
				System.out
						.println("Movimiento Erróneo: Escoge otra matriz final");
				return;
			}
			;

		} else if (this.buttonBFS.isPressed()) {

			if (this.buttonFunction1.isPressed()) {
				// BFS with the first heuristic function
				BfsMan bfsMan = new BfsMan(initialMatrix, goalMatrix);
				bfsMan.buscarSolucion();
				numbersToMove = bfsMan.getTemp();

			} else if (this.buttonFunction2.isPressed()) {
				// BFS with the second heuristic function
				BfsMis bfsMis = new BfsMis(initialMatrix, goalMatrix);
				bfsMis.buscarSolucion();
				numbersToMove = bfsMis.getTemp();
			} else {
				return;
			}
		}

		this.solutionBoard.reset(initialMatrix);
		if (steps != null) {
			this.solutionBoard.animateSolutionWithSteps(steps);
			steps = null;
		} else {
			this.solutionBoard.animateSolution(numbersToMove);
		}

	}

	/**
	 * 
	 */
	private void resetPuzzle() {
		this.solutionBoard.reset(initialBoard.getDeepMatrixClone());
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
