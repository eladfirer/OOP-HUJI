/**
 * PlayerFactory. this class is responsible for creating the players.
 */
public class PlayerFactory {
    public PlayerFactory(){};

    /**
     * creates a player.
     * @param type a string that represents the player type [human, whatever, genius, clever]
     * @return returns the player.
     */
    public static Player buildPlayer(String type) {
        type = type.toLowerCase();
        return switch (type) {
            case "human" -> new HumanPlayer();
            case "whatever" -> new WhateverPlayer();
            case "clever" -> new CleverPlayer();
            case "genius" -> new GeniusPlayer();
            default -> null;
        };
    }
}
