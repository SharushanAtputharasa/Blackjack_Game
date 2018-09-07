/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.game.Blackjack;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author sharu
 */
public class BlackjackController implements Initializable {

    @FXML
    private AnchorPane ApBlackJack;
    @FXML
    private ImageView card1;
    @FXML
    private ImageView card2;
    @FXML
    private ImageView card3;
    @FXML
    private ImageView card4;
    @FXML
    private ImageView card5;
    @FXML
    private ImageView card6;
    @FXML
    private ImageView card7;
    @FXML
    private ImageView card8;
    @FXML
    private ImageView card9;
    @FXML
    private ImageView cardC1;
    @FXML
    private ImageView cardC2;
    @FXML
    private ImageView cardC3;
    @FXML
    private ImageView cardC4;
    @FXML
    private ImageView cardC5;
    @FXML
    private Button Confirm;
    @FXML
    private Button btnReset;
    @FXML
    private ImageView Coin3;
    @FXML
    private ImageView Coin2;
    @FXML
    private ImageView Coin1;
    @FXML
    private ImageView Coin;
    @FXML
    private TextField txtChipAmount;
    @FXML
    private Label lblCoin;
    @FXML
    private Label lblCoinRed;
    @FXML
    private Label lblCoinGreen;
    @FXML
    private Label lblCoinBlack;
    @FXML
    private Label lblPointsComputer;
    @FXML
    private Label lblPointsGamer;
    @FXML
    private Button btnhit;
    @FXML
    private Button btnstand;
    @FXML
    private Label lblResult;
    @FXML
    private Label lblWin;
    @FXML
    private Label txtChips;
    @FXML
    private Button btnInsurance;
    @FXML
    private Label lblInsurance;
    @FXML
    private Button btnDouble;
    @FXML
    private TextField txtInsuranceAmount;
    @FXML
    private ImageView ImageViewInsurance;
    @FXML
    private ImageView ImageViewDouble;

    private ArrayList<ImageView> cardViewsComputerZus = new ArrayList<>();
    private ArrayList<ImageView> cardViews;
    private ArrayList<Card> cardsGamer = new ArrayList<>();
    private ArrayList<Card> cardsComputer = new ArrayList<>();

    private int index;
    private int indexComp = 0;
    private int chipsAmount;
    private int chips;

    private Blackjack blackjack;

    private Player player = Player.getInstance();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        /*ImagesViews werden dem ArrayList zugeteilt.*/
        cardViewsComputerZus.add(cardC1);
        cardViewsComputerZus.add(cardC2);
        cardViewsComputerZus.add(cardC3);
        cardViewsComputerZus.add(cardC4);
        cardViewsComputerZus.add(cardC5);

        blackjack = new Blackjack();
        cardViews = new ArrayList<>();

        cardViews.add(card1);
        cardViews.add(card2);
        cardViews.add(card3);
        cardViews.add(card4);
        cardViews.add(card5);
        cardViews.add(card6);
        cardViews.add(card7);
        cardViews.add(card8);
        cardViews.add(card9);

        chipsAmount = 10;
        chips = player.getChips();
        updateChips();
    }

    @FXML
    private void ClickedonConfirm(ActionEvent event) {

        String regex = "[0-9]+";

        if (txtChipAmount.getText().matches(regex) && Integer.valueOf(txtChipAmount.getText()) % 2 == 0 && Integer.valueOf(txtChipAmount.getText()) >= 10) {
            if (Integer.valueOf(txtChipAmount.getText()) > 0 && Integer.valueOf(txtChipAmount.getText()) <= Integer.valueOf(txtChips.getText())) {

                chipsAmount = Integer.valueOf(txtChipAmount.getText());

                chips -= chipsAmount;
                updateChips();

                index = 0;

                /*Die gezogene random Karte wird in dem Array vom Spieler abgespeichert.*/
                Card card = blackjack.foldCard();
                cardsGamer.add(card);
                /*Die gezogene random Karte wird in dem Array vom Computer abgespeichert.*/
                Card card3 = blackjack.foldCard();
                cardsComputer.add(card3);

                /*Die gezogene random Karte wird als erste Karte vom Spieler aufgedeckt.*/
                cardViews.get(index).setImage(new Image("Images/Cards/" + card.getName() + card.getType() + ".png"));
                index++;

                /*Diese Methode deckt die zweite Karte von dem Spieler auf*/
                showCard();

                /*Die gezogene random Karte wird als erste Karte vom Computer aufgedeckt.*/
                cardViews.get(index).setImage(new Image("Images/Cards/" + card3.getName() + card3.getType() + ".png"));
                index++;

                /*Die zweite Karte vom Computer bleibt gedeckt.*/
                cardC1.setImage(new Image("Images/Cards/blue_back.png"));

                /*Die Werte der Karten vom Spieler und Dealer werden in einem Label ausgegeben.*/
                lblPointsGamer.setText(Integer.toString(Blackjack.getValue(cardsGamer)));
                lblPointsComputer.setText(Integer.toString(Blackjack.getValue(cardsComputer)));

                /*Wenn der Spieler den Wert 21 direkt hat, wird die Methode btnstand ausgeführt.*/
                if (Blackjack.getValue(cardsGamer) == 21) {
                    btnstand();

                    double d = chipsAmount * 1.5;
                    lblWin.setText("You won: " + (int) d + " Chips.");

                    chips += d;
                    updateChips();

                } else {
                    btnhit.setDisable(false);
                    btnstand.setDisable(false);
                }

                /*Alle Buttons werden transparend und nicht mehr funktinostüchtig.*/
                Confirm.setDisable(true);
                txtChipAmount.setDisable(true);
                Coin.setDisable(true);
                Coin1.setDisable(true);
                Coin2.setDisable(true);
                Coin3.setDisable(true);
                lblCoin.setDisable(true);
                lblCoinRed.setDisable(true);
                lblCoinGreen.setDisable(true);
                lblCoinBlack.setDisable(true);
                btnReset.setDisable(true);
            }

            /*Wenn die Werte der Karten von Spieler zwischen 8 und 12 ist, wird der Button Double hervorgerufen.*/
            if (Blackjack.getValue(cardsGamer) > 8 && Blackjack.getValue(cardsGamer) < 12) {
                btnDouble.setVisible(true);
                ImageViewDouble.setVisible(true);
            }

            /*Wenn es die erste Karte vom Computer ist und es einen Wert von 11 hat, wird der Button Insurance hervorgerufen.*/
            if (cardsComputer.size() == 1 && Blackjack.getValue(cardsComputer) == 11) {
                btnInsurance.setVisible(true);
                ImageViewInsurance.setVisible(true);
                lblInsurance.setVisible(true);
                txtInsuranceAmount.setVisible(true);
            }

            /*Hier werden die Fehler abgefangen.*/
        } else {
            try {
                int intChipAmount = Integer.valueOf(txtChipAmount.getText());
                if (intChipAmount < 10) {
                    JOptionPane.showMessageDialog(null, "The minimum bet is 10 chips!", "Buy More Chips", JOptionPane.OK_CANCEL_OPTION);
                } else if (intChipAmount % 2 != 0) {
                    JOptionPane.showMessageDialog(null, "Your number must divide by 2!", "Buy More Chips", JOptionPane.OK_CANCEL_OPTION);
                } else if (intChipAmount > chips) {
                    JOptionPane.showMessageDialog(null, "You don't have enough chips!", "Buy More Chips", JOptionPane.OK_CANCEL_OPTION);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Only Numbers!");
            }
        }
    }

    @FXML
    private void showCard() {

        /*Es wird dem gezogenen Karte ein Variabel gesetzt, damit es einfacher ist mit dem weiter zu arbeiten.*/
        Card card = blackjack.foldCard();
        /*Hier werden die Bilder auf der Spieloberfläche angezeigt*/
        cardViews.get(index).setImage(new Image("Images/Cards/" + card.getName() + card.getType() + ".png"));

        /*Die erstellten Karten werden dem Spieler im Array zugteilt.*/
        cardsGamer.add(card);

        /*Punkte von Gamer werden zusammen gezählt.*/
        lblPointsGamer.setText(Integer.toString(Blackjack.getValue(cardsGamer)));
        /*Die nächste ImageView wird ausgewählt.*/
        index++;

        /*Falls die Werte der gezogenen Karte von Spieler 21 überschreiten, ist der Spieler gebusted.*/
        if (Blackjack.getValue(cardsGamer) > 21) {
            lblResult.setText("You are busted!");
            /*Dem gezogenen random Karte wird eine Variabel gesetzt.*/
            card = blackjack.foldCard();
            cardViewsComputerZus.get(indexComp).setImage(new Image("Images/Cards/" + card.getName() + card.getType() + ".png"));
            cardsComputer.add(card);
            lblPointsComputer.setText(Integer.toString(Blackjack.getValue(cardsComputer)));
            indexComp++;
            /*Die nicht benötigten Buttons werden transparent und nicht mehr funktionierbar.*/
            btnhit.setDisable(true);
            btnstand.setDisable(true);
        } else if (Blackjack.getValue(cardsGamer) == 21) {
            lblResult.setText("Blackjack!");
            /*Das gewonnene Betrag wird in einem Label angezeigt.*/

            double d = chipsAmount * 1.5;
            lblWin.setText("You won: " + (int) d + " Chips.");
            chips += d;

            updateChips();

            /*Die nicht benötigten Buttons werden transparent und nicht mehr funktionierbar.*/
            btnhit.setDisable(true);
            btnstand.setDisable(true);
        }
    }

    @FXML
    private void btnstand() {

        while (Blackjack.getValue(cardsComputer) < 18 && Blackjack.getValue(cardsComputer) < Blackjack.getValue(cardsGamer) && Blackjack.getValue(cardsGamer) != 21) {
            /*Dem Computer wird eine Random Karte gezogen*/
            Card card = blackjack.foldCard();
            cardViewsComputerZus.get(indexComp).setImage(new Image("Images/Cards/" + card.getName() + card.getType() + ".png"));
            cardsComputer.add(card);
            lblPointsComputer.setText(Integer.toString(Blackjack.getValue(cardsComputer)));
            indexComp++;
        }

        btnhit.setDisable(true);
        btnstand.setDisable(true);

        /*Es wird überprüft ob man gewonnen hat oder nicht.*/
        String[] result = Blackjack.compareCard(cardsComputer, cardsGamer);

        /*Im lblResult wird das dazugehörige Text angezeigt.*/
        lblResult.setText(result[1]);
        if (result[1].equals("You have won") || result[1].equals("Dealer is busted!")) {
            int d = Integer.parseInt(txtChipAmount.getText());
            double r = d * 2;
            lblWin.setText("You won: " + (int) r + " Chips.");
            chips += chipsAmount * 2;
            updateChips();

        } else if (result[1].equals("You have Blackjack!")) {
            int e = Integer.parseInt(txtChipAmount.getText());
            double f = e * 1.5;
            lblWin.setText("You won: " + (int) f + " Chips.");
            chips += chipsAmount * 1.5;
            updateChips();
        }
    }

    @FXML
    private void ClickedonReset(ActionEvent event) {
        chipsAmount = 10;
        updateChips();
    }

    @FXML
    private void AddHunderd(MouseEvent event) {
        chipsAmount += 100;
        updateChips();
    }

    @FXML
    private void AddFifty(MouseEvent event) {
        chipsAmount += 50;
        updateChips();
    }

    @FXML
    private void AddTen(MouseEvent event) {
        chipsAmount += 10;
        updateChips();
    }

    @FXML
    private void AddTwo(MouseEvent event) {
        chipsAmount += 2;
        updateChips();
    }

    @FXML
    private void makeInsurance(ActionEvent event) throws SQLException {

        boolean valid = false;
        try {
            int insuranceAmount = Integer.valueOf(txtInsuranceAmount.getText());

            /*Fehler abfangen für den Einsatz im Versicherung.*/
            if (insuranceAmount % 2 != 0) {
                JOptionPane.showMessageDialog(null, "Your number must divide by 2!", "Buy More Chips", JOptionPane.OK_CANCEL_OPTION);
            } else if (insuranceAmount > chips) {
                JOptionPane.showMessageDialog(null, "You don't have enough chips!", "Buy More Chips", JOptionPane.OK_CANCEL_OPTION);
            } else if (insuranceAmount < 10) {
                JOptionPane.showMessageDialog(null, "The minimum bet is 10 chips!", "Buy More Chips", JOptionPane.OK_CANCEL_OPTION);
            } else {
                valid = true;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Only Numbers!");
        }

        if (valid) {
            /*Wenn der Dealer nur eine Karte in der Hand hat und die Augensumme 11 ist, hat der Dealer kein Blackjack.*/
            if (cardsComputer.size() == 1 && Blackjack.getValue(cardsComputer) == 11) {

                chips -= Integer.valueOf(txtInsuranceAmount.getText());
                updateChips();

                lblResult.setText("It isn't a Blackjack!");

                Card card = blackjack.foldCard();
                cardViewsComputerZus.get(indexComp).setImage(new Image("Images/Cards/" + card.getName() + card.getType() + ".png"));
                cardsComputer.add(card);
                lblPointsComputer.setText(Blackjack.getValue(cardsComputer) + "");
                indexComp++;

                /*Wenn der Dealer zwei Karten in der Hand hat und die Augensumme 21 ist, hat der Dealer ein Blackjack.*/
                if (Blackjack.getValue(cardsComputer) == 21 && cardsComputer.size() == 2) {

                    chips += Integer.valueOf(txtInsuranceAmount.getText()) * 2;
                    updateChips();

                    lblResult.setText("It is a Blackjack!");

                }
            }

        }
    }

    @FXML
    private void makeDouble(ActionEvent event) throws SQLException {
        if (Blackjack.getValue(cardsGamer) > 8 && Blackjack.getValue(cardsGamer) < 12) {
            chipsAmount *= 2;
            chips -= chipsAmount;
            updateChips();
            showCard();

            btnDouble.setDisable(true);
            btnhit.setDisable(true);

        }
    }

    private void updateChips() {
        player.setChips(chips);
        txtChips.setText(Integer.toString(player.getChips()));
        txtChipAmount.setText(Integer.toString(chipsAmount));
    }

    @FXML
    private void newStart(MouseEvent event) throws IOException {

        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/fxml/Blackjack.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }
}
