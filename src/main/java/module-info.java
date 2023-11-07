module com.oopmp {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;


    opens com.oopmp to javafx.fxml;
    exports com.oopmp;
}