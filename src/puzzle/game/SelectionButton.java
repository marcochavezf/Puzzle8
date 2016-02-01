package puzzle.game;

import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

public class SelectionButton extends AnimatedSprite {

	// ===========================================================
	// Constants
	// ===========================================================
	
	// ===========================================================
	// Fields
	// ===========================================================

	private Boolean isPressed;
	private Puzzle8Scene puzzleScene;
	
	// ===========================================================
	// Constructors
	// ===========================================================
	
	public SelectionButton(float pX, float pY, TiledTextureRegion textureRegion) {
		super(pX, pY, textureRegion);
		isPressed = false;
	}
	
	// ===========================================================
	// Getters & Setters
	// ===========================================================

	/**
	 * @param puzzleScene the puzzleScene to set
	 */
	public void setPuzzleScene(Puzzle8Scene puzzleScene) {
		this.puzzleScene = puzzleScene;
	}

	/**
	 * @return the isPressed
	 */
	public Boolean isPressed() {
		return isPressed;
	}
	
	
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
			float pTouchAreaLocalX, float pTouchAreaLocalY) {
		
		if (!isPressed) {
			isPressed = true;
			this.setCurrentTileIndex(1);
			this.puzzleScene.onButtonPressed(this);
		}
		
		return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
				pTouchAreaLocalY);
	}
	
	// ===========================================================
	// Public Methods
	// ===========================================================
	
	/**
	 * 
	 */
	public void switchOff() {
		isPressed = false;
		this.setCurrentTileIndex(0);
	}
	
	/**
	 * 
	 */
	public void switchOn() {
		isPressed = true;
		this.setCurrentTileIndex(1);
	}
	
	
	// ===========================================================
	// Private Methods
	// ===========================================================
	
	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================

	
}