package puzzle.game;

import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import android.content.Context;

public class TextureManager {

	// ===========================================================
	// Constants
	// ===========================================================

	private static TextureReloaded textureReloaded;
	private static Context context;
	private static TextureRegion regionTexturaIntialState;
	private static TextureRegion regionTexturaGoalState;
	private static TextureRegion regionTexturaTitleFunctions;
	private static TextureRegion regionTexturaTitleSearching;
	private static TextureRegion regionTexturaButtonStart;
	private static TextureRegion regionTexturaButtonReset;
	private static TextureRegion regionTexturaPuzzleNumber;
	private static TiledTextureRegion regionTexturaButtonBreadth;
	private static TiledTextureRegion regionTexturaBFS;
	private static TiledTextureRegion regionTexturaButtonHillClimbing;
	private static TiledTextureRegion regionTexturaButtonFunction1;
	private static TiledTextureRegion regionTexturaButtonFunction2;
	private static TextureRegion textureRegionTitleSolution;

	// ===========================================================
	// Fields
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================

	/**
	 * 
	 * @param context
	 */
	public static void setContext(Context context) {
		TextureManager.context = context;
		if (textureReloaded == null) {
			textureReloaded = new TextureReloaded(1024, 1024,
					TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		}
	}

	/**
	 * 
	 */
	public void freeResources() {
		textureReloaded = null;
	}

	/**
	 * 
	 * @param textureRegion
	 * @param PATH
	 * @param textureWidth
	 * @param textureHeight
	 * @return
	 */
	private static TextureRegion addImageToTexture(TextureRegion textureRegion,
			final String PATH, int textureWidth, int textureHeight) {
		if (textureRegion == null) {
			textureReloaded.checkOfsset(textureWidth, textureHeight);
			TextureRegionFactory.setAssetBasePath("gfx/");
			textureRegion = TextureRegionFactory.createFromAsset(
					textureReloaded, context, PATH,
					textureReloaded.getOffsetX(), textureReloaded.getOffsetY());
			textureReloaded.shiftOfsset(textureRegion);
		}
		return textureRegion;
	}
	
	/**
	 * 
	 * @param regionTileTexture
	 * @param COLUMNS
	 * @param ROWS
	 * @param TEXTURE_WIDTH
	 * @param TEXTURE_HEIGHT
	 * @param PATH
	 * @return
	 */
	private static TiledTextureRegion addTiledImageToTexture(
			TiledTextureRegion regionTileTexture, final int COLUMNS,
			final int ROWS, final int TEXTURE_WIDTH, final int TEXTURE_HEIGHT,
			final String PATH) {
		if (regionTileTexture == null) {
			textureReloaded.checkOfsset(TEXTURE_WIDTH, TEXTURE_HEIGHT);
			TextureRegionFactory.setAssetBasePath("gfx/");
			regionTileTexture = TextureRegionFactory.createTiledFromAsset(
					textureReloaded, context, PATH,
					textureReloaded.getOffsetX(), textureReloaded.getOffsetY(),
					COLUMNS, ROWS);
			textureReloaded.shiftOfsset(regionTileTexture);
		}
		return regionTileTexture;
	}

	// ===========================================================
	// Getters & Setters
	// ===========================================================

	/**
	 * @return the textura
	 */
	public static TextureReloaded getTexture() {
		if (textureReloaded == null) {
			textureReloaded = new TextureReloaded(512, 512,
					TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		}
		return textureReloaded;
	}

	/**
	 * @return the texturaFondo
	 */
	public static TextureRegion getTitleInitialState() {
		if (regionTexturaIntialState == null) {
			TextureRegionFactory.setAssetBasePath("gfx/");
			regionTexturaIntialState = TextureRegionFactory.createFromAsset(
					textureReloaded, context, "initial_state_title.png",
					textureReloaded.getOffsetX(), textureReloaded.getOffsetY());
			textureReloaded.shiftOfsset(regionTexturaIntialState);
		}
		return regionTexturaIntialState;
	}

	/**
	 * @return the texturaFondo
	 */
	public static TextureRegion getTitleGoalState() {
		if (regionTexturaGoalState == null) {
			TextureRegionFactory.setAssetBasePath("gfx/");
			regionTexturaGoalState = TextureRegionFactory.createFromAsset(
					textureReloaded, context, "goal_state_title.png",
					textureReloaded.getOffsetX(), textureReloaded.getOffsetY());
			textureReloaded.shiftOfsset(regionTexturaGoalState);
		}
		return regionTexturaGoalState;
	}

	/**
	 * @return the texturaFondo
	 */
	public static TextureRegion getTitleFunctions() {
		if (regionTexturaTitleFunctions == null) {
			TextureRegionFactory.setAssetBasePath("gfx/");
			regionTexturaTitleFunctions = TextureRegionFactory.createFromAsset(
					textureReloaded, context, "functions_title.png",
					textureReloaded.getOffsetX(), textureReloaded.getOffsetY());
			textureReloaded.shiftOfsset(regionTexturaTitleFunctions);
		}
		return regionTexturaTitleFunctions;
	}

	/**
	 * @return the texturaFondo
	 */
	public static TextureRegion getTitleSearching() {
		if (regionTexturaTitleSearching == null) {
			TextureRegionFactory.setAssetBasePath("gfx/");
			regionTexturaTitleSearching = TextureRegionFactory.createFromAsset(
					textureReloaded, context, "searching_title.png",
					textureReloaded.getOffsetX(), textureReloaded.getOffsetY());
			textureReloaded.shiftOfsset(regionTexturaTitleSearching);
		}
		return regionTexturaTitleSearching;
	}

	/**
	 * @return the texturaFondo
	 */
	public static TextureRegion getTitleSolution() {
		TextureRegion textureRegion = textureRegionTitleSolution;
		final String PATH = "solution_title.png";
		int textureWidth = 0;
		int textureHeight = 0;
		textureRegion = addImageToTexture(textureRegion, PATH, textureWidth,
				textureHeight);
		return textureRegion;
	}

	/**
	 * @return the texturaFondo
	 */
	public static TextureRegion getButtonStart() {
		if (regionTexturaButtonStart == null) {
			TextureRegionFactory.setAssetBasePath("gfx/");
			regionTexturaButtonStart = TextureRegionFactory.createFromAsset(
					textureReloaded, context, "go_button.png",
					textureReloaded.getOffsetX(), textureReloaded.getOffsetY());
			textureReloaded.shiftOfsset(regionTexturaButtonStart);
		}
		return regionTexturaButtonStart;
	}

	/**
	 * @return the texturaFondo
	 */
	public static TextureRegion getButtonReset() {
		if (regionTexturaButtonReset == null) {
			TextureRegionFactory.setAssetBasePath("gfx/");
			regionTexturaButtonReset = TextureRegionFactory.createFromAsset(
					textureReloaded, context, "reset_button.png",
					textureReloaded.getOffsetX(), textureReloaded.getOffsetY());
			textureReloaded.shiftOfsset(regionTexturaButtonReset);
		}
		return regionTexturaButtonReset;
	}

	/**
	 * @return the texturaFondo
	 */
	public static TextureRegion getPuzzleNumbers() {
		if (regionTexturaPuzzleNumber == null) {
			TextureRegionFactory.setAssetBasePath("gfx/");
			regionTexturaPuzzleNumber = TextureRegionFactory.createFromAsset(
					textureReloaded, context, "numbers_puzzle.png",
					textureReloaded.getOffsetX(), textureReloaded.getOffsetY());
			textureReloaded.shiftOfsset(regionTexturaPuzzleNumber);
		}
		return regionTexturaPuzzleNumber;
	}

	/**
	 * @return ButtonBreadth
	 */
	public static TiledTextureRegion getButtonBreadth() {
		TiledTextureRegion regionTileTexture = regionTexturaButtonBreadth;
		final int COLUMNS = 2;
		final int ROWS = 1;
		final int TEXTURE_WIDTH = 200;
		final int TEXTURE_HEIGHT = 50;
		final String PATH = "breadth_title.png";
		regionTileTexture = addTiledImageToTexture(regionTileTexture, COLUMNS,
				ROWS, TEXTURE_WIDTH, TEXTURE_HEIGHT, PATH);
		return regionTileTexture;
	}

	/**
	 * @return ButtonBreadth
	 */
	public static TiledTextureRegion getButtonBFS() {
		TiledTextureRegion regionTileTexture = regionTexturaBFS;
		final int COLUMNS = 2;
		final int ROWS = 1;
		final int TEXTURE_WIDTH = 200;
		final int TEXTURE_HEIGHT = 50;
		final String PATH = "bfs_title.png";
		regionTileTexture = addTiledImageToTexture(regionTileTexture, COLUMNS,
				ROWS, TEXTURE_WIDTH, TEXTURE_HEIGHT, PATH);
		return regionTileTexture;
	}

	/**
	 * @return ButtonBreadth
	 */
	public static TiledTextureRegion getButtonHillClimbing() {
		TiledTextureRegion regionTileTexture = regionTexturaButtonHillClimbing;
		final int COLUMNS = 2;
		final int ROWS = 1;
		final int TEXTURE_WIDTH = 200;
		final int TEXTURE_HEIGHT = 50;
		final String PATH = "hill_climbing.png";
		regionTileTexture = addTiledImageToTexture(regionTileTexture, COLUMNS,
				ROWS, TEXTURE_WIDTH, TEXTURE_HEIGHT, PATH);
		return regionTileTexture;
	}

	/**
	 * @return ButtonBreadth
	 */
	public static TiledTextureRegion getButtonFunction1() {
		TiledTextureRegion regionTileTexture = regionTexturaButtonFunction1;
		final int COLUMNS = 2;
		final int ROWS = 1;
		final int TEXTURE_WIDTH = 200;
		final int TEXTURE_HEIGHT = 50;
		final String PATH = "fun1_title.png";
		regionTileTexture = addTiledImageToTexture(regionTileTexture, COLUMNS,
				ROWS, TEXTURE_WIDTH, TEXTURE_HEIGHT, PATH);
		return regionTileTexture;
	}

	/**
	 * @return ButtonBreadth
	 */
	public static TiledTextureRegion getButtonFunction2() {
		TiledTextureRegion regionTileTexture = regionTexturaButtonFunction2;
		final int COLUMNS = 2;
		final int ROWS = 1;
		final int TEXTURE_WIDTH = 200;
		final int TEXTURE_HEIGHT = 50;
		final String PATH = "fun2_title.png";
		regionTileTexture = addTiledImageToTexture(regionTileTexture, COLUMNS,
				ROWS, TEXTURE_WIDTH, TEXTURE_HEIGHT, PATH);
		return regionTileTexture;
	}
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================

}
