package Ex2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.shape.Line;

import java.sql.*;
import static java.lang.System.out;
import java.util.ArrayList;

public class Ex2Controller {

    @FXML private PieChart pieChart;
    @FXML private BarChart barChart;

    private ArrayList<Dept> arrayList = new ArrayList<>();
    private ArrayList<Line> lineList = new ArrayList<>();

    public void initialize(){
        try {
            //Get connection and dept from table
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded");

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/JavaEx", "root", "");
            System.out.println("Database connected");

            PreparedStatement ps = connection.prepareStatement("select dept, count(*) from Student where dept is not null group by dept;");
            ResultSet rSet = ps.executeQuery();

            while (rSet.next()){
                arrayList.add(new Dept(rSet.getString(1), Integer.parseInt(rSet.getString(2))));
            }

            //Set values for pie chart
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
            for (int i=0; i<arrayList.size(); i++){
                pieChartData.add(new PieChart.Data(arrayList.get(i).getDept(), arrayList.get(i).getDeptCount()));
            }
            pieChart.setData(pieChartData);

            //Set Values for bar chart
            XYChart.Series series = new XYChart.Series();
            for (int i=0; i<arrayList.size(); i++){
                series.getData().add(new XYChart.Data(arrayList.get(i).getDept(), arrayList.get(i).getDeptCount()));
            }
            barChart.getData().add(series);
        }

        catch (SQLException | ClassNotFoundException ex){
            ex.printStackTrace();
        }
    }
    private class Dept{

        private String dept;
        private int deptCount;

        public Dept(String dept, int deptCount){
            this.dept = dept;
            this.deptCount = deptCount;
        }
        public String getDept(){
            return dept;
        }
        public int getDeptCount(){
            return deptCount;
        }
    }
}
