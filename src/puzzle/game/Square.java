package puzzle.game;

import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

public class Square extends Sprite {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private final int NUMBER;
	private Board board;

	// ===========================================================
	// Constructors
	// ===========================================================

	public Square(float pX, float pY, TextureRegion pTextureRegion,
			final int NUMBER) {
		super(pX, pY, pTextureRegion);
		this.NUMBER = NUMBER;
	}

	// ===========================================================
	// Getters & Setters
	// ===========================================================

	/**
	 * @return the nUMBER
	 */
	public int getNumber() {
		return NUMBER;
	}

	/**
	 * @param board
	 *            the board to set
	 */
	public void setBoard(Board board) {
		this.board = board;
	}

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
			float pTouchAreaLocalX, float pTouchAreaLocalY) {
		
		if (board.animationIsFinished()) {
			board.checkSquareAndMove(NUMBER);
		}

		return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
				pTouchAreaLocalY);
	}

	// ===========================================================
	// Public Methods
	// ===========================================================

	// ===========================================================
	// Private Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================

}
