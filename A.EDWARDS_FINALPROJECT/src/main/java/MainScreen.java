package src.main.java;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MainScreen extends Application {
    private UserData userData = new UserData();
    public void start(Stage mainStage) {
       
       VBox mainVBox = new VBox(15);
       mainVBox.setAlignment(Pos.CENTER);
       mainVBox.setMaxSize(300, 450);
       var fontResource = getClass().getResource("/fonts/Kindergarten.ttf");
       Font.loadFont(fontResource.toExternalForm(), 70);
       mainVBox.setStyle(
        "-fx-background-color: #ffffff; " + 
        "-fx-background-radius: 2; " +      
        "-fx-padding: 40;" + 
        "-fx-font-family: 'Kindergarten';"
        );
        mainVBox.setEffect(new DropShadow(20, Color.color(0, 0, 0, 0.4)));
       String buttonStyle = "-fx-background-size: contain; " +
                      "-fx-background-repeat: no-repeat; " +
                      "-fx-background-position: center; " +
                      "-fx-background-color: transparent; " + 
                      "-fx-font-size: 30px; " +
                      "-fx-padding: 20px 40px;" +
                      "-fx-text-fill: white;";  // To let me repeat

       Button wallet = new Button("Wallet");
       wallet.setStyle(buttonStyle + "-fx-background-image: url('/images/red_button_background.png');");
       wallet.setPrefWidth(200);
       Button collection = new Button("Collection");
       collection.setStyle(buttonStyle + "-fx-background-image: url('/images/green_button_background.png');");
       collection.setPrefWidth(200);
       Button shop = new Button("Shop");
       shop.setStyle(buttonStyle  + "-fx-background-image: url('/images/purple_button_background.png');");
       shop.setPrefWidth(200);
       Button quit = new Button("Quit");
       quit.setStyle(buttonStyle + "-fx-background-image: url('/images/yellow_button_background.png');");
       quit.setPrefWidth(200);

       //Hovering
       wallet.setOnMouseEntered(e -> wallet.setEffect(new javafx.scene.effect.ColorAdjust(0, 0, -0.3, 0)));
       wallet.setOnMouseExited(e -> wallet.setEffect(null));
       collection.setOnMouseEntered(e -> collection.setEffect(new javafx.scene.effect.ColorAdjust(0, 0, -0.3, 0)));
       collection.setOnMouseExited(e -> collection.setEffect(null));
       shop.setOnMouseEntered(e -> shop.setEffect(new javafx.scene.effect.ColorAdjust(0, 0, -0.3, 0)));
       shop.setOnMouseExited(e -> shop.setEffect(null));
       quit.setOnMouseEntered(e -> quit.setEffect(new javafx.scene.effect.ColorAdjust(0, 0, -0.3, 0)));
       quit.setOnMouseExited(e -> quit.setEffect(null));

       mainVBox.getChildren().addAll(wallet, collection, shop, quit);

       // Event Handling
       wallet.setOnAction(e -> {
            Wallet walletWindow = new Wallet();
            walletWindow.setUserData(this.userData);
            walletWindow.show();
        });
        collection.setOnAction(e -> {
            Collection collectionWindow = new Collection();
            collectionWindow.setUserData(this.userData); // Pass shared data
            collectionWindow.show();
        });
        shop.setOnAction(e -> {
            Store storeWindow = new Store();
            storeWindow.setUserData(this.userData); // Pass shared data
            storeWindow.show();
        });
       quit.setOnAction(e-> {
            mainStage.close();
        });

       StackPane mainStackPane = new StackPane();
       mainStackPane.getChildren().add(mainVBox);
       mainStackPane.setStyle("-fx-background-image: url('/images/wooden_background.jpg');" + "-fx-background-size: cover;");
       Scene mainScene = new Scene(mainStackPane, 700, 600);
       Image appIcon = new Image(getClass().getResourceAsStream("/images/app_icon.png"));
       mainStage.getIcons().add(appIcon);
       mainStage.setResizable(false);
       mainStage.setScene(mainScene);
       mainStage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}
