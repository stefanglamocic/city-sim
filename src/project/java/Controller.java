package project.java;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import project.java.datamodel.Images;
import project.java.datamodel.Position;
import project.java.datamodel.Roads;
import project.java.datamodel.StylesCSS;

import java.util.LinkedList;

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
    public static StackPane[][] stackPanes = new StackPane[30][30];


    @FXML
    public void initialize(){
        populateGridPane();
        generateRoads();
        generateRailroads();
        generateCrossroads();
        generateTrainStations();

//        Image image = new Image("assets/cars/truck2.png");
//
//        ImageView imgView = new ImageView(image);
//        imgView.setFitHeight(30);
//        imgView.setPreserveRatio(true);
//        imgView.setSmooth(true);
//        imgView.setRotate(90);
//
//        fpTop.getChildren().add(new ImageView(image2));
//        fpBottom.getChildren().add(new ImageView(image2));
        //imgView.setVisible(false);

//        stackPanes[13][1].getChildren().add(imgView);
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

    private void generateRoads(){
        String color = StylesCSS.BLUE;
        for(int i = 0; i < 30; i++){
            setStackPaneColor(13, i, color);
            Roads.add(Roads.upToDown, new Position(13, i));
            setStackPaneColor(14, i, color);
            Roads.add(Roads.downToUp, new Position(14, i));
        }

        for(int i = 0; i < 9; i++){
            setStackPaneColor(i,20, color);
            Roads.add(Roads.downToLeft, new Position(i, 20));
            setStackPaneColor(29 - i,20, color);
            Roads.add(Roads.rightToDown, new Position(29 - i, 20));
        }

        for(int i = 0; i < 8; i++){
            setStackPaneColor(i,21, color);
            Roads.add(Roads.leftToDown, new Position(i, 21));
            setStackPaneColor(29 - i,21, color);
            Roads.add(Roads.downToRight, new Position(29 - i, 21));
        }

        for(int i = 22; i < 30; i++){
            setStackPaneColor(7, i, color);
            Roads.add(Roads.leftToDown, new Position(7, i));
            setStackPaneColor(29 - 7, i, color);
            Roads.add(Roads.downToRight, new Position(22, i));
        }

        for(int i = 21; i < 30; i++){
            setStackPaneColor(8, i, color);
            Roads.add(Roads.downToLeft, new Position(8, i));
            setStackPaneColor(29 - 8, i, color);
            Roads.add(Roads.rightToDown, new Position(21, i));
        }
    }

    private void generateCrossroads(){
        String color = StylesCSS.BLACK;
        setStackPaneColor(13, 6, color);
        setStackPaneColor(14, 6, color);
        setStackPaneColor(26, 20, color);
        setStackPaneColor(26, 21, color);
        setStackPaneColor(2, 20, color);
        setStackPaneColor(2, 21, color);
    }

    private void generateRailroads(){
        String color = StylesCSS.GRAY;
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

    private void setStationImage(int quadrant, int i, int j){
        ImageView view = new ImageView();
        view.setFitWidth(27);
        view.setPreserveRatio(true);
        view.setSmooth(true);
        switch (quadrant) {
            case 1: {
                view.setImage(Images.imgStation1);
                StackPane.setAlignment(view, Pos.BOTTOM_RIGHT);
            }
            break;
            case 2:
            {
                view.setImage(Images.imgStation2);
                StackPane.setAlignment(view, Pos.BOTTOM_LEFT);
            }
                break;
            case 3:
            {
                view.setImage(Images.imgStation3);
                StackPane.setAlignment(view, Pos.TOP_RIGHT);
            }
                break;
            case 4:
            {
                view.setImage(Images.imgStation4);
                StackPane.setAlignment(view, Pos.TOP_LEFT);
            }
                break;
            default:
                break;
        }
        stackPanes[i][j].getChildren().add(view);
    }

    private void generateTrainStations(){
        Insets insets = new Insets(0,0,5,5);

        //Train station A:
        Label a = new Label("A");
        setStackPaneColor(1, 28, StylesCSS.GRAY_THIRD);
        stackPanes[1][28].getChildren().add(a);
        setStationImage(3, 1, 28);
        StackPane.setAlignment(a, Pos.BOTTOM_LEFT);
        StackPane.setMargin(a, insets);
        setStackPaneColor(2, 27, StylesCSS.GRAY_SECOND);
        setStationImage(2, 2, 27);
        setStackPaneColor(1, 27, StylesCSS.GRAY_FIRST);
        setStationImage(1, 1, 27);
        setStackPaneColor(2, 28, StylesCSS.GRAY_FOURTH);
        setStationImage(4, 2, 28);

        //Train station B:
        Label b = new Label("B");
        setStackPaneColor(6, 6, StylesCSS.GRAY_THIRD);
        stackPanes[6][6].getChildren().add(b);
        StackPane.setAlignment(b, Pos.BOTTOM_LEFT);
        StackPane.setMargin(b, insets);
        setStationImage(3, 6, 6);
        setStackPaneColor(7, 5, StylesCSS.GRAY_SECOND);
        setStationImage(2, 7, 5);
        setStackPaneColor(6, 5, StylesCSS.GRAY_FIRST);
        setStationImage(1, 6, 5);
        setStackPaneColor(7, 6, StylesCSS.GRAY_FOURTH);
        setStationImage(4, 7, 6);

        //Train station C:
        Label c = new Label("C");
        setStackPaneColor(19, 13, StylesCSS.GRAY_THIRD);
        stackPanes[19][13].getChildren().add(c);
        StackPane.setAlignment(c, Pos.BOTTOM_LEFT);
        StackPane.setMargin(c, insets);
        setStationImage(3, 19, 13);
        setStackPaneColor(20, 12, StylesCSS.GRAY_SECOND);
        setStationImage(2, 20, 12);
        setStackPaneColor(19, 12, StylesCSS.GRAY_FIRST);
        setStationImage(1, 19, 12);
        setStackPaneColor(20, 13, StylesCSS.GRAY_FOURTH);
        setStationImage(4, 20, 13);

        //Train station D:
        Label d = new Label("D");
        setStackPaneColor(26, 2, StylesCSS.GRAY_THIRD);
        stackPanes[26][2].getChildren().add(d);
        StackPane.setAlignment(d, Pos.BOTTOM_LEFT);
        StackPane.setMargin(d, insets);
        setStationImage(3, 26, 2);
        setStackPaneColor(27, 1, StylesCSS.GRAY_SECOND);
        setStationImage(2, 27, 1);
        setStackPaneColor(26, 1, StylesCSS.GRAY_FIRST);
        setStationImage(1, 26, 1);
        setStackPaneColor(27, 2, StylesCSS.GRAY_FOURTH);
        setStationImage(4, 27, 2);

        //Train station E:
        Label e = new Label("E");
        setStackPaneColor(25, 26, StylesCSS.GRAY_THIRD);
        stackPanes[25][26].getChildren().add(e);
        StackPane.setAlignment(e, Pos.BOTTOM_LEFT);
        StackPane.setMargin(e, insets);
        setStationImage(3, 25, 26);
        setStackPaneColor(26, 25, StylesCSS.GRAY_SECOND);
        setStationImage(2, 26, 25);
        setStackPaneColor(25, 25, StylesCSS.GRAY_FIRST);
        setStationImage(1, 25, 25);
        setStackPaneColor(26, 26, StylesCSS.GRAY_FOURTH);
        setStationImage(4, 26, 26);
    }
}
