
package ui.Model;

import java.util.Date;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import logic.Data_services;

public class Bay extends Parent {
    public static final int SIZE = 100;
    private double x;
    private double y;
    private ImageView imageview = new ImageView();
    private Point2D dragAnchor;
    private boolean state;
    private String id;
    private Date datetime;
    private double rotation = 0;
    private boolean selected = false;

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public Bay(double x, double y, Point2D dragAnchor, boolean state, String id, Double rotation) {
        this.x = x;
        this.y = y;
        this.dragAnchor = dragAnchor;
        this.state = state;
        this.id = id;
        this.rotation = rotation;
    }
    
    public String getBayId() {
        return this.id;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public void setImage(Image image) {
        this.imageview.setImage(image);
    }

    public boolean isState() {
        return state;
    }

    public boolean isSelected() {
        return selected;
    }
    

    public Bay(double xin, double yin, boolean state, Image image, String id, Date datetime, Double rotation) {
        this.x = xin;
        this.y = yin;
        this.state = state;
        this.id = id;
        this.datetime = datetime;
        this.rotation = rotation;
        
        imageview.setImage(image);
        imageview.setFitHeight(100);
        imageview.setFitWidth(50);
        imageview.preserveRatioProperty();
        imageview.setRotate(rotation);
        imageview.setCursor(Cursor.HAND);
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
                
                imageview.setCursor(Cursor.MOVE);
                if (t.isControlDown()) {
                    selected = true;
                    imageview.setEffect(new Glow(0.3));
                } else {
                    imageview.setEffect(null);
                    selected = false;
                }
            }
        });
        setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                if (getTranslateX() < (10) && getTranslateX() > (- 10) && getTranslateY() < (10) && getTranslateY() > (- 10)) {
                    setTranslateX(0);
                    setTranslateY(0);
                }
                Data_services data = new Data_services();
                data.savePositionForUI(id,getTranslateX(),getTranslateY());
                if (!t.isControlDown()) {
                    imageview.setEffect(null);
                    selected = false;
                }
                imageview.setCursor(Cursor.HAND);
                
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

    boolean compareImage(Image expiredImage) {
        return expiredImage.equals(this.imageview.getImage());
    }
    
    
}
