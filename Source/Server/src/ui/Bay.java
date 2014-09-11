
package ui;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class Bay extends Parent {
    public static final int SIZE = 100;
    private double x;
    private double y;
    private ImageView imageview = new ImageView();
    private Point2D dragAnchor;
    private boolean state;
    private String id;
    
    public String getBayId() {
        return this.id;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public void setImage(Image image) {
        this.imageview.setImage(image);
    }
    

    public Bay(double xin, double yin, boolean state, Image image, String id) {
        this.x = xin;
        this.y = yin;
        this.state = state;
        this.id = id;
        
        imageview.setImage(image);
        imageview.setFitHeight(100);
        imageview.setFitWidth(50);
        imageview.preserveRatioProperty();
        setFocusTraversable(true);
        getChildren().addAll(imageview);
        setCache(true);
        setTranslateX(x);
        setTranslateY(y);
        setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                toFront();
                x = getTranslateX();
                y = getTranslateY();
                dragAnchor = new Point2D(t.getSceneX(), t.getSceneY());
            }
        });
        setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                if (getTranslateX() < (10) && getTranslateX() > (- 10) && getTranslateY() < (10) && getTranslateY() > (- 10)) {
                    setTranslateX(0);
                    setTranslateY(0);
                }
            }
        });
        setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                double newTranslateX = x + t.getSceneX() - dragAnchor.getX();
                double newTranslateY = y + t.getSceneY() - dragAnchor.getY();
                setTranslateX(newTranslateX);
                setTranslateY(newTranslateY);
            }
        });
    }
    
    
}
