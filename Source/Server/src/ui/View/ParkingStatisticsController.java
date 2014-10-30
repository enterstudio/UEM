
package ui.View;

import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;

public class ParkingStatisticsController implements Initializable {

    @FXML
    private BarChart<String, Integer> barChart;
    @FXML
    private CategoryAxis xAxis;
    
    private ObservableList<String> bayIds = FXCollections.observableArrayList();
    private ObservableList<Integer> bayValues = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void setBaysIds(HashMap in) {
        Iterator it = in.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry)it.next();
            bayIds.add(pairs.getKey().toString());
            bayValues.add(Integer.parseInt(pairs.getValue().toString()));
        }
        xAxis.setCategories(bayIds);
        
        XYChart.Series<String, Integer> series = new XYChart.Series<>();

        for (int i = 0; i < in.size(); i++) {
            series.getData().add(new XYChart.Data<>(bayIds.get(i), bayValues.get(i)));
        }

        barChart.getData().add(series);
    }
    
}
