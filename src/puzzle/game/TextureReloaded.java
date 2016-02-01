package puzzle.game;

import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.BaseTextureRegion;

public class TextureReloaded extends Texture{

	// ===========================================================
	// Constants
	// ===========================================================
	
	// ===========================================================
	// Fields
	// ===========================================================

	private int offsetX;
	private int offsetY;
	
	private int width;	
	private int offsetYPivot;
	
	// ===========================================================
	// Constructors
	// ===========================================================
	
	public TextureReloaded(int pWidth, int pHeight,
			TextureOptions bilinearPremultiplyalpha) {
		super(pWidth, pHeight, bilinearPremultiplyalpha);
		
		this.width = pWidth;
		this.offsetYPivot = 0;
		this.offsetY = 0;
		this.offsetX = 0;
	}
	
	// ===========================================================
	// Getter & Setter
	// ===========================================================

	/**
	 * 
	 * @param textureRegion
	 */
	public void shiftOfsset(BaseTextureRegion textureRegion) {
		float textureWidth = textureRegion.getWidth();
		float textureHeight = textureRegion.getHeight();
		
		if(this.offsetY + textureHeight > this.offsetYPivot) {
			this.offsetYPivot = (int) (this.offsetY + textureHeight);
		}
		
		if(this.offsetX + textureWidth > this.width) {
			this.offsetX = 0;
			this.offsetY = offsetYPivot;
		}else {
			this.offsetX += textureWidth;
		}
	}
	
	/**
	 * 
	 * @param height
	 */
	public void shiftFontOfsset(float height) {
		this.offsetX =0;
		this.offsetYPivot = (int) (this.offsetY + height);
		this.offsetY = offsetYPivot;
	}
	
	/**
	 * @return the offsetX
	 */
	public int getOffsetX() {
		return offsetX;
	}

	/**
	 * @return the offsetY
	 */
	public int getOffsetY() {
		return offsetY;
	}
	
	/**
	 * 
	 * @param textureWidth
	 * @param textureHeight
	 */
	public void checkOfsset(int textureWidth, int textureHeight) {
		
		if(this.offsetY + textureHeight > this.offsetYPivot) {
			this.offsetYPivot = (int) (this.offsetY + textureHeight);
		}
		
		if(this.offsetX + textureWidth > this.width) {
			this.offsetX = 0;
			this.offsetY = offsetYPivot;
		}
	}
	
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================
	
	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================

}
