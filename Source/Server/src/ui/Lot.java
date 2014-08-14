
package ui;

import javafx.scene.layout.Pane;

public class Lot extends Pane {
    Lot(int LENGTH, int WIDTH) {
        setStyle("-fx-background-color: #cccccc;");
        setPrefSize(LENGTH, WIDTH);
        setMaxSize(LENGTH + 100, WIDTH + 100);
        autosize();
    }
}
