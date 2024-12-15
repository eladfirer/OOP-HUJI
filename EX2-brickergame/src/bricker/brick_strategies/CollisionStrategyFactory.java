package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import danogl.util.Counter;

import java.util.Random;

public abstract class CollisionStrategyFactory {
    private static Random random = new Random();

    public static CollisionStrategy createStrategy(BrickerGameManager brickerGameManager,
                                                   Counter counter) {
        int chance;
        if (counter.value() == 2) {
            // Generate a random number between 0 and 8
            chance = random.nextInt(9);
        }
        else {
            // Generate a random number between 0 and 9
            chance = random.nextInt(10);
        }

        if (chance < 5) {
            // 50% probability: Regular collision
            return new BasicCollisionStrategy(brickerGameManager);
        }
        else if (chance == 5) {
            // 10% probability: Add additional balls
            return new PuckCollisionStrategy(brickerGameManager);
        }
        else if (chance == 6) {
            // 10% probability: Add extra paddle
            return new ExtraPaddleCollisionStrategy(brickerGameManager);
        }
        else if (chance == 7) {
            // 10% probability: Activate turbo mode
            return new TurboModeCollisionStrategy(brickerGameManager);
        }
        else if (chance == 8) {
            // 10% probability: Return lost life
            return new ExtraLiveCollisionStrategy(brickerGameManager);
        }
        else {
            // 10% probability: Double effect (combine two strategies)
            return new DoubleBehaviorsCollisionStrategy(brickerGameManager, counter);
        }
    }
}

