package src.main.java;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Store {
    private UserData userData;
    private final String storeButtonStyle = "-fx-background-size: contain; " +
                      "-fx-background-repeat: no-repeat; " +
                      "-fx-background-position: center; " +
                      "-fx-background-color: transparent; " + 
                      "-fx-font-size: 18px; " +
                      "-fx-padding: 10px 20px;" +
                      "-fx-text-fill: white; " +
                      "-fx-font-family: 'Kindergarten';";

    public void setUserData(UserData userData){
        this.userData = userData;
    }
    public void show(){
        Stage storeStage = new Stage();

        var fontResource = getClass().getResource("/fonts/Kindergarten.ttf");
        Font.loadFont(fontResource.toExternalForm(), 20);

        GridPane storeGridPane = new GridPane();
        storeGridPane.setHgap(25);
        storeGridPane.setVgap(25);
        storeGridPane.setPadding(new Insets(25));
        storeGridPane.setAlignment(Pos.TOP_CENTER);
        storeGridPane.setStyle("-fx-background-color: transparent");

        storeGridPane.add(CreateShopCard(createClassicBox()), 0, 0);
        storeGridPane.add(CreateShopCard(CreateSpaceBox()), 1, 0);

        ScrollPane scrollPane = new ScrollPane(storeGridPane);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-control-inner-background: transparent;");
        
        StackPane storeStackPane = new StackPane(scrollPane);
        storeStackPane.setStyle("-fx-background-image: url('/images/wooden_background.jpg'); -fx-background-size: cover;");
        Image appIcon = new Image(getClass().getResourceAsStream("/images/app_icon.png"));
        storeStage.getIcons().add(appIcon);
        storeStage.setTitle("Squigglie Shop");
        Scene storeScene = new Scene(storeStackPane, 500, 500);
        storeStage.setScene(storeScene);
        storeStage.show();
    }

    private VBox CreateShopCard(BlindBox box){
        VBox card = new VBox(10);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(25));
        card.setStyle("-fx-background-color: white; -fx-background-radius: 15; -fx-font-family: 'Kindergarten';");
        card.setEffect(new DropShadow(15, Color.color(0, 0, 0, 0.4)));
        card.setPrefWidth(280);

        Label boxTitle = new Label(box.getName());
        boxTitle.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-font-family: 'Kindergarten'; -fx-text-fill: #100978");

        StackPane holdImages = new StackPane();
        holdImages.setPrefHeight(160);
        holdImages.setAlignment(Pos.CENTER);
        try {
            Image img = new Image(getClass().getResourceAsStream(box.getCoverImagePath()));
            ImageView imVi = new ImageView(img);
            imVi.setFitHeight(150);
            imVi.setFitWidth(150);
            imVi.setPreserveRatio(true);
            holdImages.getChildren().add(imVi);
        } catch (Exception e) {
            holdImages.getChildren().add(new Label("Image Missing"));
        }

        card.getChildren().add(holdImages);
        Label priceLabel = new Label("Price: $" + String.format("%.2f", box.getPrice()));
        priceLabel.setStyle("-fx-font-size: 25px; -fx-text-fill: #2e7d32;");

        Button buy = new Button("Buy");
        buy.setStyle(storeButtonStyle + "-fx-background-image: url('/images/purple_button_background.png');");
        buy.setPrefWidth(150);
        buy.setOnAction(e -> {
            if(userData.getBalance() >= box.getPrice()) {
                userData.setBalance(userData.getBalance() - box.getPrice());
                userData.getUnopenedBoxes().add(box);
                buy.setText("Bought!");

                new Thread(() -> {
                    try { Thread.sleep(1000); } catch (InterruptedException ex) {}
                    javafx.application.Platform.runLater(() -> buy.setText("Buy"));
                }).start();
            }
            else {
                buy.setText("Too Poor!");
            }
        });
        buy.setOnMouseEntered(e -> buy.setEffect(new javafx.scene.effect.ColorAdjust(0, 0, -0.3, 0)));
        buy.setOnMouseExited(e -> buy.setEffect(null));

        card.getChildren().addAll(boxTitle, priceLabel, buy);
        return card;
    }
    private BlindBox createClassicBox(){
        BlindBox classicBox = new BlindBox("Classic Series", 50.0, "/images/classic_box.png"); // ADD IMAGE LATER
        //String name, String rarityValue, double price, int weight
        classicBox.addItem("Squigglie", "Common", 15, 22);
        classicBox.addItem("George", "Common", 30, 17);
        classicBox.addItem("Tinarina", "Uncommon", 38, 14);
        classicBox.addItem("Dot", "Uncommon", 50, 12);
        classicBox.addItem("Novabelle", "Rare", 60, 10);
        classicBox.addItem("B", "Rare", 65, 10);
        classicBox.addItem("raT", "Super Rare", 100, 8);
        classicBox.addItem("Glow Squigglie", "Epic", 210, 6);
        classicBox.addItem("God Squigglie", "Legendary", 500, 1);
        return classicBox;
    }

    private BlindBox CreateSpaceBox(){
        BlindBox spaceBox = new BlindBox("Space Series", 60.0, "/images/space_box.png"); // ADD IMAGE LATER
        //String name, String rarityValue, double price, int weight
        spaceBox.addItem("WormHole Squigglie", "Common", 22, 22);
        spaceBox.addItem("Home Novabelle", "Common", 30, 17);
        spaceBox.addItem("Starry Tinarina", "Uncommon", 44, 14);
        spaceBox.addItem("Spacy-B", "Uncommon", 62, 12);
        spaceBox.addItem("Astro George", "Rare", 80, 10);
        spaceBox.addItem("raT in SpAce", "Rare", 105, 10);
        spaceBox.addItem("Moon Dot", "Super Rare", 210, 8);
        spaceBox.addItem("Sun Squigglie", "Epic", 410, 6);
        spaceBox.addItem("Space Queen Novabelle", "Legendary", 1000, 1);
        return spaceBox;
    }
}
