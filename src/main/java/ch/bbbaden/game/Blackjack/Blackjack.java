/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.game.Blackjack;


import java.util.ArrayList;
import java.util.Random;
import javafx.event.ActionEvent;

/**
 *
 * @author sharu
 */
public class Blackjack {

    /*Von der Klasse in die Controller zugreifen*/
    BlackjackController bjc = new BlackjackController();

    /*ArrayList damit die Karten, die bei der Methode createDeck erstellt werden, abgespeichert werden.*/
    private ArrayList<Card> cards = new ArrayList<>();

    /*Die verschiedenen Kartentypen und Kartennummern werden in einem ArrayList gespeichert.*/
    private String[] cardTypes = {"H", "D", "S", "C"};
    private String[] cardNames = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
    private boolean isFirstRound = true;

    public Blackjack() {
        createDeck();
    }

    /*Hier wird eine endliche Kartendeck kreiert.*/
    public void createDeck() {
        int value;
        for (int i = 0; i < cardTypes.length; i++) {
            for (int j = 0; j < cardNames.length; j++) {
                if (j < 10) {
                    /*Alles was unter 10 ist wird ein Wert gegeben. Das Wert wird mit der Position von der ArrayList + 1 gerechnet. */
                    value = j + 1;
                } else {
                    /*Sonst wird den Karten das Wert 10 gegeben. */
                    value = 10;
                }
                /*Die Karten werden im ArrayList cards abgespeichert*/
                cards.add(new Card(value, cardNames[j], cardTypes[i]));
            }
        }

    }

    /*Hier werden die random Karten gezogen.*/
    public Card foldCard() {
        Random rnd = new Random();

        /*Gibt einen zufälligen Index des cards Array aus. */
        int rnd1 = (int) (rnd.nextInt(cards.size() - 1));

        /*Hier werden die erstellten Karten von dem Array gelöscht, damit es keien doppelte Karten gibt.*/
        Card card = cards.get(rnd1);
        cards.remove(rnd1);

        return card;
    }

    /*Static weil wir vom BlackjackContoller aus auf diese Methode zugreifen müssen, jedoch werden keine Attribute der Blackjack Klasse benötigt. */
    public static String[] compareCard(ArrayList<Card> pointsComputer, ArrayList<Card> pointsGamer) {
        /*Hier haben wir ein Array, in welcher das Faktor für den Gewinn/Verlust benutzt wird une ein Text dazu.
        Weil wir hier wieder vom vom BlackjackContoller aus auf diese Methode zugreifen müssen, musste ich dies in einem Array speichern.*/
        String[] output = new String[2];
        if (getValue(pointsComputer) == 21) {
            output[0] = "0";
            output[1] = "Dealer has Blackjack!";
        } else if (getValue(pointsGamer) == 21) {
            output[0] = "1.5";
            output[1] = "You have Blackjack!";
        } else if (getValue(pointsComputer) == getValue(pointsGamer)) {
            output[0] = "1";
            output[1] = "It's a draw!";
        } else if (getValue(pointsComputer) > getValue(pointsGamer) && getValue(pointsComputer) < 21) {
            output[0] = "0";
            output[1] = "Dealer has won!";
        } else if (getValue(pointsComputer) > 21) {
            output[0] = "2";
            output[1] = "Dealer is busted!";
        } else if (getValue(pointsGamer) > 21) {
            output[0] = "0";
            output[1] = "You are busted!";
        } else {
            output[0] = "2";
            output[1] = "You have won";
        }
        return output;
    }


    /*Static weil wir vom BlackjackContoller aus auf diese Methode zugreifen müssen, jedoch werden keine Attribute der Blackjack Klasse benötigt. */
 /*Ein Parameter wird erstellt.*/
    public static int getValue(ArrayList<Card> cards) {
        /*Hier werden die Werte vom Spieler brechnet.*/
        int sum = 0;
        int aces = 0;

        /*Bei jedem Durchgang wird eine neue Karte aus der ArrayList geladen.*/
        for (Card card : cards) {
            /*Hier habe ich dem Aces den Gesamtwert 11 zugeteilt, wenn die erste Karte ein Aces ist.*/
            if (card.getValue() == 1) {
                sum += 11;
                /*Aces wird eins hochgezählt, damit ich weiss wie viel Aces auf der Hand sind.*/
                aces++;
            } else {
                sum += card.getValue();
            }
        }
        /*Falls die Augensumme grösser als 21 ist und man mehr als 0 Aces in der Hand hat, wird die Augensumme -10 gerechnet*/
        while (sum > 21 && aces > 0) {
            sum -= 10;
            aces--;
        }
        return sum;
    }
}
