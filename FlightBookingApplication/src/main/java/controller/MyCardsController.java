package controller;

import data.CreditCardDao;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.util.StringConverter;
import models.Account;
import models.CreditCard;

import java.io.IOException;
import java.net.URL;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyCardsController implements Initializable {

    @FXML
    private Button actionButton;

    @FXML
    private StackPane cardStack;

    @FXML
    private Button deleteButton;

    @FXML
    private PasswordField inputCVV;

    @FXML
    private TextField inputCardHolder;

    @FXML
    private TextField inputCardNumber;

    @FXML
    private TextField inputExpiryDate;

    @FXML
    private Button nextButton;

    @FXML
    private Button previousButton;

    @FXML
    private Label lblMessage;

    ObservableList<CreditCard> cardList;
    private final SimpleIntegerProperty lastIndex = new SimpleIntegerProperty(-1);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        CreditCardDao creditCardDao = new CreditCardDao();
        cardList = creditCardDao.getCardList();

        previousButton.setVisible(lastIndex.intValue() != -1);
        nextButton.setVisible(lastIndex.intValue() != cardList.size() - 1);
        actionButton.setOnAction(event -> addCard());


        lastIndex.addListener((observable, oldValue, newValue) -> {
            deleteButton.setDisable(newValue.intValue() == -1);

            previousButton.setVisible(newValue.intValue() != -1);
            nextButton.setVisible(newValue.intValue() != cardList.size() - 1);
            setData();

            if (newValue.intValue() != -1) {
                actionButton.setText("UPDATE");
                actionButton.setOnAction(event -> updateCard());
            }
            else {
                actionButton.setText("ADD");
                actionButton.setOnAction(event -> addCard());
            }

        });

        //This snip assures that the card number will be 16 digits and display it in formatted way
        {
            UnaryOperator<TextFormatter.Change> filter = change -> {
                Pattern pattern = Pattern.compile("-", Pattern.UNICODE_CHARACTER_CLASS);
                Matcher matcher = pattern.matcher(change.getControlNewText());
                String originalNumber = matcher.replaceAll("");

                if (originalNumber.length() > 16 || !originalNumber.matches("\\d*")) {
                    return null;
                }
                else {
                    return change;
                }
            };
            StringConverter<String> converter = new StringConverter<>() {
                @Override
                public String toString(String input) {
                    if (input == null) {
                        return inputCardNumber.getText();
                    }

                    Pattern pattern = Pattern.compile("-", Pattern.UNICODE_CHARACTER_CLASS);
                    Matcher matcher = pattern.matcher(input);
                    input = matcher.replaceAll("");

                    if (input.length() / 4 >= 3) {
                        return String.format("%s-%s-%s-%s", input.substring(0, 4), input.substring(4, 8), input.substring(8, 12), input.substring(12));
                    }
                    else if (input.length() / 4 >= 2) {
                        return String.format("%s-%s-%s", input.substring(0, 4), input.substring(4, 8), input.substring(8));
                    }
                    else if (input.length() / 4 >= 1 ) {
                        return String.format("%s-%s", input.substring(0, 4), input.substring(4));
                    }
                    else if (input.length() / 4 < 1) {
                        return input;
                    }
                    else {
                        return null;
                    }
                }

                @Override
                public String fromString(String output) {
                    Pattern pattern = Pattern.compile("-", Pattern.UNICODE_CHARACTER_CLASS);
                    Matcher matcher = pattern.matcher(output);
                    output = matcher.replaceAll("");

                    return output;
                }
            };

            TextFormatter<String> formatter = new TextFormatter<>(converter, null, filter);
            inputCardNumber.setTextFormatter(formatter);

            inputCardNumber.textProperty().addListener((observable, oldValue, newValue) -> {
                Pattern pattern = Pattern.compile("-", Pattern.UNICODE_CHARACTER_CLASS);

                Matcher matcher = pattern.matcher(newValue);
                newValue = matcher.replaceAll("");

                matcher = pattern.matcher(oldValue);
                oldValue = matcher.replaceAll("");

                if (newValue.length() > oldValue.length() && newValue.length() / 4 >= 3) {
                    inputCardNumber.setText(String.format("%s-%s-%s-%s", newValue.substring(0, 4), newValue.substring(4, 8), newValue.substring(8, 12), newValue.substring(12)));
                }
                else if (newValue.length() > oldValue.length() && newValue.length() / 4 >= 2) {
                    inputCardNumber.setText(String.format("%s-%s-%s", newValue.substring(0, 4), newValue.substring(4, 8), newValue.substring(8)));
                }
                else if (newValue.length() > oldValue.length() && newValue.length() / 4 >= 1) {
                    inputCardNumber.setText(String.format("%s-%s", newValue.substring(0, 4), newValue.substring(4)));
                }
            });
        }

        //This snip assures that the expiration date will be formatted as MM/YYYY
        {
            inputExpiryDate.setTextFormatter(new TextFormatter<>(change -> {
                Pattern pattern = Pattern.compile("/", Pattern.UNICODE_CHARACTER_CLASS);

                Matcher matcher = pattern.matcher(change.getControlNewText());
                String newValue = matcher.replaceAll("");

                if (change.isDeleted() || newValue.matches("\\d?\\d?\\d?\\d?\\d?\\d?")) {
                    return change;
                }

                return null;
            }));

            inputExpiryDate.textProperty().addListener((observable, oldValue, newValue) -> {
                Pattern pattern = Pattern.compile("/", Pattern.UNICODE_CHARACTER_CLASS);

                Matcher matcher = pattern.matcher(newValue);
                newValue = matcher.replaceAll("");

                matcher = pattern.matcher(oldValue);
                oldValue = matcher.replaceAll("");

                if (newValue.length() > oldValue.length() && newValue.length() >= 2) {
                    inputExpiryDate.setText(newValue.substring(0, 2) + "/" + newValue.substring(2));
                }

            });
        }
    }

    private void setData() {
        if (lastIndex.get() > -1) {
            CreditCard card = cardList.get(lastIndex.get());

            inputCardHolder.setText(card.getCardHolder());
            inputCardNumber.setText(card.getCardNumber());
            inputExpiryDate.setText(card.getExpirationDate().format(DateTimeFormatter.ofPattern("MM/yyyy")));
            inputCVV.setText(card.getCVV());
        }
        else {
            inputCardHolder.clear();
            inputCardNumber.clear();
            inputExpiryDate.clear();
            inputCVV.clear();
        }
    }

    private void updateCard() {
        if (inputCardNumber.getText().isBlank() || inputCardHolder.getText().isBlank() || inputCVV.getText().isBlank() || inputExpiryDate.getText().isBlank()) {
            lblMessage.setText("All fields are required");
        }
        else if (inputCardNumber.getTextFormatter().getValue().toString().length() != 16) {
            lblMessage.setText("Card number should be 16 digits");
        }
        else if (!inputExpiryDate.getText().matches("\\d\\d/\\d\\d\\d\\d")) {
            lblMessage.setText("Expiration date should be in the format MM/YYYY");
        }
        else {
            lblMessage.setText("");
            CreditCard card = cardList.get(lastIndex.get());
            card.setCardHolder(inputCardHolder.getText());
            card.setCardNumber(inputCardNumber.getTextFormatter().getValue().toString());

            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/yyyy");
            YearMonth expiryDate = YearMonth.parse(inputExpiryDate.getText(), dateFormat);
            card.setExpirationDate(expiryDate.atDay(expiryDate.getMonth().length(expiryDate.isLeapYear()) - 1));
            card.setCVV(inputCVV.getText());

            CreditCardDao creditCardDao = new CreditCardDao();
            creditCardDao.update(card.getId(), card);
        }
    }

    private void addCard() {
        if (inputCardNumber.getText().isBlank() || inputCardHolder.getText().isBlank() || inputCVV.getText().isBlank() || inputExpiryDate.getText().isBlank()) {
            lblMessage.setText("All fields are required");
        }
        else if (inputCardNumber.getTextFormatter().getValue().toString().length() != 16) {
            lblMessage.setText("Card Number");
        }
        else if (!inputExpiryDate.getText().matches("\\d\\d/\\d\\d\\d\\d")) {
            lblMessage.setText("Expiration date should be in the format MM/YYYY");
        }
        else {
            lblMessage.setText("");
            CreditCard card = new CreditCard();
            card.setAccount(Account.getCurrentUser().getId());
            card.setCardHolder(inputCardHolder.getText());
            card.setCardNumber(inputCardNumber.getTextFormatter().getValue().toString());
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/yyyy");
            YearMonth expiryDate = YearMonth.parse(inputExpiryDate.getText(), dateFormat);
            card.setExpirationDate(expiryDate.atDay(expiryDate.getMonth().length(expiryDate.isLeapYear()) - 1));
            card.setCVV(inputCVV.getText());

            CreditCardDao creditCardDao = new CreditCardDao();
            creditCardDao.create(card);
            cardList.add(card);

            goToCard(cardList.size() - 1);
        }
    }

    @FXML
    void deleteCard() {
        CreditCardDao creditCardDao = new CreditCardDao();
        creditCardDao.delete(cardList.get(lastIndex.get()).getId());
        cardList.remove(lastIndex.get());
        goToCard(-1);
    }

    @FXML
    void nextCard(ActionEvent event) {
        lblMessage.setText("");
        lastIndex.set(lastIndex.get() + 1);
        try {
            FXMLLoader cardLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/view/SearchPage/CreditCard.fxml")));
            ToggleButton creditCard = cardLoader.load();

            CreditCardController cardController = cardLoader.getController();
            cardController.setData(cardList.get(lastIndex.get()));

            cardStack.getChildren().add(creditCard);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void previousCard(ActionEvent event) {
        lblMessage.setText("");
        lastIndex.set(lastIndex.get() - 1);
        cardStack.getChildren().remove(cardStack.getChildren().size() - 1);
    }

    private void goToCard(int index) {
        if (index > lastIndex.get()) {
            while (lastIndex.get() < index) {
                nextCard(new ActionEvent());
            }
        }
        else {
            while (lastIndex.get() > index) {
                previousCard(new ActionEvent());
            }
        }
    }
}
