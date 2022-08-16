package ru.vk.client_chat.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import ru.vk.client_chat.Network;
import ru.vk.client_chat.StartClient;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class ChatController {

    @FXML
    private ListView<String> usersList;

    @FXML
    private Label usernameTitle;

    @FXML
    private TextArea chatHistory;

    @FXML
    private TextField inputField;

    @FXML
    private Button sendButton;
    private Network network;
    private String selectedRecipient;
    private StartClient startClient;

    @FXML
    public void initialize() {
        usersList.setItems(FXCollections.observableArrayList("Super_Sonic", "Bender", "Super_Mario", "Гендальф_Серый"
                , "Брюс_Уэйн", "Martin_Superstar"));

        sendButton.setOnAction(event -> sendMessage());
        inputField.setOnAction(event -> sendMessage());

        usersList.setCellFactory(lv -> {
            MultipleSelectionModel<String> selectionModel = usersList.getSelectionModel();
            ListCell<String> cell = new ListCell<>();
            cell.textProperty().bind(cell.itemProperty());
            cell.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
                usersList.requestFocus();
                if (!cell.isEmpty()) {
                    int index = cell.getIndex();
                    if (selectionModel.getSelectedIndices().contains(index)) {
                        selectionModel.clearSelection(index);
                        selectedRecipient = null;
                    } else {
                        selectionModel.select(index);
                        selectedRecipient = cell.getItem();
                    }
                    event.consume();
                }
            });
            return cell;
        });
    }

    private void sendMessage() {
        String msg = inputField.getText().trim();
        //Message message = new Message(inputField.getText().trim());
        inputField.clear();

        if (msg.isEmpty()) {
            return;
        }

//        appendMessage(msg);
//        network.sendMessage(msg);

        if (selectedRecipient != null) {
            network.sendPrivateMessage(selectedRecipient, msg);
        } else {
            network.sendMessage(msg);
        }
    }

    public void printData(String message) {
        chatHistory.appendText(message);
    }

    public void appendMessage(String sender, String message) {
        chatHistory.appendText("[" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMM dd, HH:mm")) + "] " + sender + ": " + message + "\n");
    }

    public void appendServerMessage(String message) {
        chatHistory.appendText(String.format("Внимание! %s", message));
        chatHistory.appendText(System.lineSeparator());
        chatHistory.appendText(System.lineSeparator());
    }

    public void setNetwork(Network network) {
        this.network = network;
    }

    public void setUsernameTitle(String usernameTitleStr) {
        this.usernameTitle.setText(usernameTitleStr);
    }

    public void setStartClient(StartClient startClient) {
        this.startClient = startClient;
    }

    public StartClient getStartClient() {
        return startClient;
    }

    public void updateUserList(String[] users) {
        Platform.runLater(() -> {
            ObservableList<String> items = usersList.getItems();
            items.clear();
            items.addAll(Arrays.asList(users));
        });
    }
}
