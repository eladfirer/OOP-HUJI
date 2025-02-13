package pepse.world;

import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.util.Constants;
import pepse.util.NoiseGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * this class responsible for generating the terrain height and creating ground
 */
public class Terrain {


    private final NoiseGenerator noiseGenerator;
    private float groundHeightAtX0;

    /**
     * Constructor for Terrain.
     *
     * @param windowDimensions window dimensions of program.
     * @param seed             seed for noise generator.
     */
    public Terrain(Vector2 windowDimensions, int seed) {
        groundHeightAtX0 = windowDimensions.y() * 2 / 3;
        noiseGenerator = new NoiseGenerator(seed, 3);
    }


    /**
     * this methods returns the ground height (y) at x position.
     *
     * @param x
     * @return ground height at x position.
     * @see NoiseGenerator
     */
    public float groundHeightAt(float x) {
        double noiseValue = noiseGenerator.noise(x / Constants.NOISE_SCALE, 1.0); // Smooth
        // noise
        return (float) (groundHeightAtX0 + noiseValue * Constants.NOISE_AMPLITUDE);
    }


    /**
     * creates blocks in a specific range [minX,MaxX], representing a terrain
     *
     * @param minX x to start creating terrain
     * @param maxX x to end creating terrain
     * @return blocks to add to game
     */
    public List<Block> createInRange(int minX, int maxX) {
        List<Block> blocks = new ArrayList<Block>();

        minX = (int) Math.floor((double) minX / Constants.BLOCK_SIZE) * Constants
                .BLOCK_SIZE;
        maxX = (int) Math.ceil((double) maxX / Constants.BLOCK_SIZE) * Constants
                .BLOCK_SIZE;


        for (int x = minX; x < maxX; x += Constants.BLOCK_SIZE) {
            float groundHeight = groundHeightAt(x);
            int topBlockY = (int) Math.floor(groundHeight / Constants.BLOCK_SIZE) *
                    Constants.BLOCK_SIZE;

            for (int depth = 0; depth < Constants.TERRAIN_DEPTH; depth++) {
                RectangleRenderable renderable =
                        new RectangleRenderable(ColorSupplier.approximateColor(
                                Constants.BASE_GROUND_COLOR));
                int blockY = topBlockY + depth * Constants.BLOCK_SIZE;
                Block block = new Block(new Vector2(x, blockY), renderable);
                block.setTag(Constants.GROUND);
                block.defineLayer(depth);
                blocks.add(block);
            }
        }
        return blocks;
    }
}
