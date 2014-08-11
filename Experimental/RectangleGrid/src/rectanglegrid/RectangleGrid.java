/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rectanglegrid;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 *
 * @author Huis
 */
public class RectangleGrid extends Application {
    private Rectangle lastOne;
    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();

        int grid_x = 7; //number of rows
        int grid_y = 7; //number of columns
        
        // this binding will find out which parameter is smaller: height or width
        NumberBinding rectsAreaSize = Bindings.min(root.heightProperty(), root.widthProperty());
        for (int x = 0; x < grid_x; x++) {
            for (int y = 0; y < grid_y; y++) {
                Rectangle rectangle = new Rectangle();
                rectangle.setStroke(Color.WHITE);

                rectangle.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent t) {
                        if (lastOne != null) {
                            lastOne.setFill(Color.BLACK);
                        }
                        // remembering clicks
                        lastOne = (Rectangle) t.getSource();
                        // updating fill
                        lastOne.setFill(Color.RED);
                    }
                });

                // here we position rects (this depends on pane size as well)
                rectangle.xProperty().bind(rectsAreaSize.multiply(x).divide(grid_x));
                rectangle.yProperty().bind(rectsAreaSize.multiply(y).divide(grid_y));

                // here we bind rectangle size to pane size 
                rectangle.heightProperty().bind(rectsAreaSize.divide(grid_x));
                rectangle.widthProperty().bind(rectangle.heightProperty());
                rectangle.setCursor(Cursor.HAND);
                root.getChildren().add(rectangle);
            }
        }
        
        Scene scene = new Scene(root, 500, 500);
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
