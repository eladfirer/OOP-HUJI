package pepse.world.trees;

import pepse.util.Constants;

import java.util.*;
import java.util.function.Function;

/**
 * this class is responsible for creating the flora in game
 */
public class Flora {

    private final Function<Float, Float> callbackGetHeight;
    private final int seed;
    private Random random;

    /**
     * flora constructor
     * @param callbackGetHeight callback to get height (y) of ground in a specific x location
     */
    public Flora (Function<Float,Float> callbackGetHeight){
        this.callbackGetHeight = callbackGetHeight;
        seed = new Random().nextInt();
    }

    /**
     * creates in range of minX and maxX trees to put in game
     * @param minX x to start creating trees
     * @param maxX x to stop creating trees
     * @return array list of trees to put in game
     * @see Tree
     */
    public ArrayList<Tree> createInRange(int minX, int maxX) {

        minX = (int) Math.floor((double) minX / Constants.BLOCK_SIZE) * Constants
                .BLOCK_SIZE;
        maxX = (int) Math.ceil((double) maxX / Constants.BLOCK_SIZE) * Constants
                .BLOCK_SIZE;

        ArrayList<Tree> trees = new ArrayList<>();
        for (int x = minX; x < maxX; x += Constants.BLOCK_SIZE){
            float groundHeight = callbackGetHeight.apply((float)x);
            random = new Random(Objects.hash(x, seed));

            // randomness for creating tree
            if(random.nextDouble()  < 0.1){
                Tree newTree = Tree.createTree(x, groundHeight,random);
                trees.add(newTree);
            }
        }
        return trees;
    }
}
