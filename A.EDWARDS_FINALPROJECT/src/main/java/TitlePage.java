package src.main.java;
import javafx.util.Duration;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TitlePage extends Application {
    public void start(Stage titleStage){

        VBox titleVbox = new VBox();
        var fontResource = getClass().getResource("/fonts/Kindergarten.ttf");
        Font.loadFont(fontResource.toExternalForm(), 70);
        titleVbox.setStyle("-fx-font-family: 'Kindergarten';" + "-fx-font-weight: bold;");
        Image img = new Image("/images/title_page_background.jpg");
        BackgroundImage titleBackground = new BackgroundImage(img, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(1.0, 1.0, true, true, false, false));
        titleVbox.setBackground(new Background(titleBackground));
        titleVbox.setSpacing(20);

        var fontResource2 = getClass().getResource("/fonts/WorstPaintJobEver.ttf");
        Font.loadFont(fontResource2.toExternalForm(), 80);

        // Title
        FlowPane titleFlowPane = new FlowPane();
        titleFlowPane.setAlignment(Pos.CENTER);
        titleFlowPane.setHgap(2);
        titleFlowPane.setVgap(5);
        titleFlowPane.setMaxWidth(450);

        String title = "SQUIGGLE AND FRENS";
        String titleStyle = "-fx-font-size: 60px; -fx-font-family: 'WorstPaintJobEver'; -fx-fill: #02045c;";

        for (char c : title.toCharArray()) {
            Text letter = new Text(String.valueOf(c));
            letter.setStyle(titleStyle);
            titleFlowPane.getChildren().add(letter);
        }

        titleVbox.getChildren().add(titleFlowPane);

        animateTitle(titleFlowPane);

        //Menu

        String buttonStyle = "-fx-background-image: url('/images/button_background.png'); " +
                      "-fx-background-size: contain; " +
                      "-fx-background-repeat: no-repeat; " +
                      "-fx-background-position: center; " +
                      "-fx-background-color: transparent; " + 
                      "-fx-font-size: 20px; " +
                      "-fx-padding: 20px 40px;" +
                      "-fx-text-fill: white;";  // To let me repeat
        
        Button start = new Button("Start");
        start.setStyle(buttonStyle);
        start.setPrefWidth(200);
        Button quit = new Button("Quit");
        quit.setStyle(buttonStyle);
        quit.setPrefWidth(200);

        //Hovering
        start.setOnMouseEntered(e -> start.setEffect(new javafx.scene.effect.ColorAdjust(0, 0, -0.3, 0)));
        quit.setOnMouseEntered(e -> quit.setEffect(new javafx.scene.effect.ColorAdjust(0, 0, -0.3, 0)));
        start.setOnMouseExited(e -> start.setEffect(null));
        quit.setOnMouseExited(e -> quit.setEffect(null));


        // Event Handling
        start.setOnAction(e -> {
            MainScreen mainScreen = new MainScreen();
            try {
                mainScreen.start(new Stage());
                titleStage.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        quit.setOnAction(e-> {
            titleStage.close();
        });
        titleVbox.getChildren().addAll(start, quit);
        titleVbox.setAlignment(Pos.CENTER);
        titleVbox.setPadding(new Insets(20));
        Scene titleScene = new Scene(titleVbox, 500, 500);
        Image appIcon = new Image(getClass().getResourceAsStream("/images/app_icon.png"));
        titleStage.getIcons().add(appIcon);
        titleStage.setResizable(false);
        titleStage.setScene(titleScene);
        titleStage.show();
    }
    
    private void animateTitle(FlowPane titleContainer){
        for (int i = 0; i < titleContainer.getChildren().size(); i++) {
            Text letter = (Text) titleContainer.getChildren().get(i);

            TranslateTransition bounce = new TranslateTransition(Duration.seconds(0.5), letter);
            bounce.setByY(-20);
            bounce.setCycleCount(TranslateTransition.INDEFINITE);
            bounce.setAutoReverse(true);
            bounce.setInterpolator(Interpolator.EASE_BOTH);

            bounce.setDelay(Duration.seconds(i * 0.1));
            bounce.play();
        }
    }
    public static void main(String[] args) {
        launch();
    }
}