package project.java;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import project.java.datamodel.StylesCSS;

public class Controller {
    @FXML
    private FlowPane fpTop;
    @FXML
    private FlowPane fpBottom;
    @FXML
    private FlowPane fpLeft;
    @FXML
    private FlowPane fpRight;
    @FXML
    private GridPane gridPane;
    private StackPane[][] stackPanes = new StackPane[30][30];

    @FXML
    public void initialize(){
        populateGridPane();
        generateRoads(StylesCSS.BLUE);
        generateRailroads(StylesCSS.GRAY);
        generateCrossroads(StylesCSS.BLACK);


//        Image image = new Image("assets/cars/car2.png");
//        Image image2 = new Image("assets/cars/car1.png");
//        ImageView imgView = new ImageView(image);
//        Image image3 = new Image("assets/trains/train.png");
//        fpTop.getChildren().add(new ImageView(image));
//        fpTop.getChildren().add(new ImageView(image2));
//        fpBottom.getChildren().add(new ImageView(image2));
//        stackPanes[14][1].getChildren().add(imgView);
    }

    private void populateGridPane(){
        for(int i = 0; i < 30; i++){
            for(int j = 0; j < 30; j++){
                stackPanes[i][j] = new StackPane();
                StackPane temp = stackPanes[i][j];
                temp.setPrefSize(80, 80);
                setStackPaneColor(i, j, StylesCSS.WHITE);
                gridPane.add(temp, i, j);
            }
        }
    }

    private void generateRoads(String color){
        for(int i = 0; i < 30; i++){
            setStackPaneColor(13, i, color);
            setStackPaneColor(14, i, color);
        }

        for(int i = 0; i < 9; i++){
            setStackPaneColor(i,20, color);
            setStackPaneColor(i,21, color);
            setStackPaneColor(29 - i,20, color);
            setStackPaneColor(29 - i,21, color);
        }

        for(int i = 22; i < 30; i++){
            setStackPaneColor(7, i, color);
            setStackPaneColor(8, i, color);
            setStackPaneColor(29 - 7, i, color);
            setStackPaneColor(29 - 8, i, color);
        }
    }

    private void generateCrossroads(String color){
        setStackPaneColor(13, 6, color);
        setStackPaneColor(14, 6, color);
        setStackPaneColor(26, 20, color);
        setStackPaneColor(26, 21, color);
        setStackPaneColor(2, 20, color);
        setStackPaneColor(2, 21, color);
    }

    private void generateRailroads(String color){
        for(int i = 0; i < 14; i++){
            setStackPaneColor(2, 29 - i, color);
            setStackPaneColor(6 + i, 6, color);
        }
        for(int i = 0; i < 11; i++)
            setStackPaneColor(5, 6 + i, color);
        for(int i = 0; i < 8; i++)
            setStackPaneColor(19, 6 + i, color);
        for(int i = 0; i < 7; i++)
            setStackPaneColor(20, 12 + i, color);
        for(int i = 0; i < 9; i++)
            setStackPaneColor(26, 18 + i, color);
        for(int i = 0; i < 5; i++)
            setStackPaneColor(25 - i, 18, color);
        for(int i = 0; i < 3; i++) {
            setStackPaneColor(29 - i, 25, color);
            setStackPaneColor(26, 9 + i, color);
        }
        for(int i = 0; i < 4; i++)
            setStackPaneColor(28, 6 + i, color);
        for(int i = 0; i < 2; i++) {
            setStackPaneColor(3 + i, 16, color);
            setStackPaneColor(22, 2 + i, color);
            setStackPaneColor(23, 3 + i, color);
        }
        for(int i = 0; i < 6; i++){
            setStackPaneColor(27 - i, 1, color);
            setStackPaneColor(28 - i, 5, color);
            setStackPaneColor(26 - i, 12, color);
        }
        setStackPaneColor(27, 9, color);
    }

    private void setStackPaneColor(int i, int j, String color){
        stackPanes[i][j].setStyle(color);
    }
}
