package src.main.java;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Wallet {
    private UserData userData;
    public void setUserData(UserData userData){
        this.userData = userData;
    }
    public void show(){
        Stage walletStage = new Stage();
        Label balanceLabel = new Label();
        Label headerLabel = new Label("WALLET BALANCE: ");

        balanceLabel.textProperty().bind(userData.balanceProperty().asString("$%.2f"));

        // font stuff
        var fontResource = getClass().getResource("/fonts/Kindergarten.ttf");
        Font.loadFont(fontResource.toExternalForm(), 70);
        var fontResource2 = getClass().getResource("/fonts/WorstPaintJobEver.ttf");
        Font.loadFont(fontResource2.toExternalForm(), 80);
        headerLabel.setStyle("-fx-font-size: 20px;" + "-fx-font-family: 'WorstPaintJobEver';" + "-fx-text-fill: #02045c");

        // Promo Code stuff
        TextField promoInput = new TextField();
        promoInput.setPromptText("Enter Promo Code Here");
        promoInput.setStyle("-fx-font-size: 15px;");
        promoInput.setMaxWidth(150);
        promoInput.setFocusTraversable(false);

        Button promoButton = new Button("Claim");
        promoButton.setStyle("-fx-background-image: url('/images/button_background.png'); " +
                      "-fx-background-size: contain; " +
                      "-fx-background-repeat: no-repeat; " +
                      "-fx-background-position: center; " +
                      "-fx-background-color: transparent; " + 
                      "-fx-font-size: 20px; " +
                      "-fx-padding: 20px 40px;" +
                      "-fx-text-fill: white;");

        promoButton.setOnAction(e -> {
            String code = promoInput.getText().trim();

            if (code.equals("FREEMONEYGLITCH")) {
                userData.setBalance(userData.getBalance() + 100.0);
                promoInput.clear();
                promoInput.setPromptText("Valid! + $100");
            } else {
                promoInput.setPromptText("Invalid Code!");
                promoInput.clear();
            }
        });
        VBox walletVbox = new VBox(15, headerLabel, balanceLabel, promoInput, promoButton);
        walletVbox.setAlignment(Pos.CENTER);
        walletVbox.setMaxSize(220,220);
        walletVbox.setEffect(new DropShadow(20, Color.color(0, 0, 0, 0.4)));
        walletVbox.setStyle("-fx-background-color: #ffffff; " +
                            "-fx-background-radius: 20; " +
                            "-fx-padding: 20;"
        );
        StackPane walletBGPane = new StackPane();
        walletBGPane.getChildren().add(walletVbox);
        walletBGPane.setStyle("-fx-background-image: url('/images/wooden_background.jpg');" +
                              "-fx-background-size: cover; " +
                              "-fx-background-position: center;" + 
                              "-fx-font-family: 'Kindergarten';" + 
                              "-fx-font-size: 50px;"
        );

        Scene walletScene = new Scene(walletBGPane, 300, 300);
        Image appIcon = new Image(getClass().getResourceAsStream("/images/app_icon.png"));
        walletStage.getIcons().add(appIcon);
        walletStage.setResizable(false);
        walletStage.setScene(walletScene);
        walletStage.show();
    }
}