/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.game.Blackjack;

/**
 *
 * @author sharu
 */
public class Card {

    private final int value;
    private final String name;
    private final String type;

    public Card(int value, String name, String type) {
        this.value = value;
        this.name = name;
        this.type = type;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

}