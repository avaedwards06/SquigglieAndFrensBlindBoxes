package src.main.java;

import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Collection {
    private UserData userData;
    private GridPane unopenedGrid = new GridPane();
    private GridPane prizesGrid = new GridPane();
    private final String smallButtonStyle = "-fx-background-size: 100% 100%; " +
                    "-fx-background-repeat: no-repeat; " +
                    "-fx-background-position: center; " +
                    "-fx-background-color: transparent; " + 
                    "-fx-font-size: 14px; " +
                    "-fx-padding: 5px 10px;" +
                    "-fx-text-fill: white; " +
                    "-fx-font-family: 'Kindergarten';";
    public void setUserData(UserData userData){
        this.userData = userData;
    }
    
    public void show() {
       Stage collectionStage = new Stage();
       var fontResource = getClass().getResource("/fonts/Kindergarten.ttf");
       Font.loadFont(fontResource.toExternalForm(), 20);

       TabPane tabPane = new TabPane();
       tabPane.setStyle("-fx-background-color: transparent;" +
                        "-fx-tab-header-background: #232454;" +
                        "-fx-font-family: 'Kindergarten';" +
                        "-fx-tab-min-height: 40px;" +
                        "-fx-font-size: 18px");
       
       ScrollPane unopenedScrollPane = createStyledScrollPane(unopenedGrid);
       Tab unopenedBoxTab = new Tab("Unopened Boxes", unopenedScrollPane);
       ScrollPane prizesScrollPane = createStyledScrollPane(prizesGrid);
       Tab prizesTab = new Tab("Collected Frens", prizesScrollPane);

       unopenedBoxTab.setStyle("-fx-background-color: #3498db; -fx-text-base-color: white;");
        prizesTab.setStyle("-fx-background-color: #2980b9; -fx-text-base-color: white;");

       tabPane.getTabs().addAll(unopenedBoxTab, prizesTab);
       tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
       
       userData.getUnopenedBoxes().addListener((ListChangeListener<BlindBox>) c -> refreshUnopenedGrid());
       userData.getPrizes().addListener((ListChangeListener<Prize>) c -> refreshPrizesGrid());

       refreshUnopenedGrid();
       refreshPrizesGrid();

       StackPane backgroundPane = new StackPane(tabPane);
       backgroundPane.setStyle("-fx-background-image: url('/images/wooden_background.jpg'); -fx-background-size: cover;");

       Scene scene = new Scene(backgroundPane, 650, 500);
       collectionStage.setTitle("My Collection");
       collectionStage.setResizable(false);
       collectionStage.setScene(scene);
       collectionStage.show();
    }

    private ScrollPane createStyledScrollPane(GridPane grid){
        setupGridStyle(grid);
        ScrollPane scrollPane = new ScrollPane(grid);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-control-inner-background: transparent;");
        return scrollPane;
    }

    private void refreshUnopenedGrid(){
        unopenedGrid.getChildren().clear();
        setupGridStyle(unopenedGrid);
        int col = 0;
        int row = 0;
        for (BlindBox box : userData.getUnopenedBoxes()) {
            VBox card = createBaseCard(box.getName(), box.getCoverImagePath());

            Button open = new Button("Open");
            open.setStyle(smallButtonStyle + "-fx-background-image: url('/images/green_button_background.png');");
            Button sell = new Button ("Sell");
            sell.setStyle(smallButtonStyle + "-fx-background-image: url('/images/red_button_background.png');");

            open.setOnAction(e -> {
                Prize result = box.open();
                userData.getPrizes().add(result);
                userData.getUnopenedBoxes().remove(box);

                showRevealPopup(result);
            });
            sell.setOnAction(e -> {
                userData.setBalance(userData.getBalance() + (box.getPrice() * 0.8));
                userData.getUnopenedBoxes().remove(box);
            });

            HBox buttonHolder = new HBox(10, open, sell);
            buttonHolder.setAlignment(Pos.CENTER);
            card.getChildren().add(buttonHolder);

            unopenedGrid.add(card, col++, row);
            if (col > 3) {
                col = 0; row++;
            }
        }
    }
    private void refreshPrizesGrid(){
        prizesGrid.getChildren().clear();
        setupGridStyle(prizesGrid);

        int col = 0;
        int row = 0;
        for (Prize prize : userData.getPrizes()) {
            VBox card = createBaseCard(prize.getName(), prize.getImagePath());

            Button rarityButton = new Button("Rarity");
            rarityButton.setStyle(smallButtonStyle + "-fx-background-image: url('/images/button_background.png');");
            Button sellButton = new Button("Sell");
            sellButton.setStyle(smallButtonStyle + "-fx-background-image: url('/images/yellow_button_background.png');");

            rarityButton.setOnAction(e -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(prize.getName() + " Info");
                alert.setContentText("Rarity: " + prize.getRarityLabel());
                alert.show();
            });

            sellButton.setOnAction(e -> {
                userData.setBalance(userData.getBalance() + prize.getSellPrice());
                userData.getPrizes().remove(prize);
            });

            HBox buttonHolder = new HBox(10, rarityButton, sellButton);
            buttonHolder.setAlignment(Pos.CENTER);
            card.getChildren().add(buttonHolder);

            prizesGrid.add(card, col++, row);
            if (col > 3) { col = 0; row++; }
        }
    }
    private VBox createBaseCard(String name, String imgPath){
        VBox card = new VBox(10);
        card.setAlignment(Pos.CENTER);
        card.setPrefSize(180,220);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-font-family: 'Kindergarten';");
        card.setEffect(new DropShadow(10, Color.color(0, 0, 0, 0.3)));

        try {
            Image img = new Image(getClass().getResourceAsStream(imgPath));
            ImageView imVi = new ImageView(img);
            imVi.setFitHeight(100);
            imVi.setPreserveRatio(true);
            card.getChildren().add(imVi);
        } catch (Exception e) {
            card.getChildren().add(new Label("[Error: Image Missing]"));
        }
        Label nameLabel = new Label(name);
        nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-text-fill: #333333");
        card.getChildren().add(nameLabel);

        return card;
    }
    private void setupGridStyle(GridPane grid){
        grid.setHgap(20);
        grid.setVgap(20);
        grid.setPadding(new Insets(20));
        grid.setAlignment(Pos.TOP_LEFT);
    }
    private void showRevealPopup(Prize prize) {
        Stage popupStage = new Stage();
        popupStage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
        popupStage.initStyle(StageStyle.UNDECORATED);

        VBox revealVBox = new VBox(20);
        revealVBox.setAlignment(Pos.CENTER);
        revealVBox.setStyle("-fx-background-color: white; -fx-padding: 30; -fx-border-color: #ffd700; -fx-border-width: 5;");

        Label recieved = new Label("YOU GOT:");
        recieved.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        ImageView prizeView = new ImageView();
        try {
            Image img = new Image(getClass().getResourceAsStream(prize.getImagePath()));
            prizeView.setImage(img);
            prizeView.setFitHeight(200);
            prizeView.setPreserveRatio(true);
        } catch (Exception e) {
            System.out.println("Couldn't load image: " + prize.getImagePath());
        }
        prizeView.setEffect(new DropShadow(20, Color.color(0, 0, 0, 0.2)));
        

        Label nameLabel = new Label(prize.getName());
        nameLabel.setStyle("-fx-font-size: 24px; -fx-font-family: 'Kindergarten';");

        Label rarityLabel = new Label(prize.getRarityLabel());
        if (prize.getRarityLabel().equals("Legendary")) rarityLabel.setStyle("-fx-text-fill: gold; -fx-font-weight: bold;");
        else rarityLabel.setStyle("-fx-text-fill: gray;");

        Button closeButton = new Button("Close");
        String popupButtonStyle = "-fx-background-size: 100% 100%; " +
                  "-fx-background-repeat: no-repeat; " +
                  "-fx-background-position: center; " +
                  "-fx-background-color: transparent; " + 
                  "-fx-font-size: 20px; " +
                  "-fx-padding: 15px 30px;" +
                  "-fx-text-fill: white; " + 
                  "-fx-font-family: 'Kindergarten';";
        closeButton.setStyle(popupButtonStyle + "-fx-background-image: url('/images/button_background.png');");
        closeButton.setOnAction(e -> popupStage.close());

        revealVBox.getChildren().addAll(recieved, prizeView, nameLabel, rarityLabel, closeButton);

        Scene popUpScene = new Scene(revealVBox);
        popupStage.setScene(popUpScene);
        popupStage.show();
    }   
}
