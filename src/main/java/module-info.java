module ucf.assignments {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens ucf.assignments to javafx.fxml;
    exports ucf.assignments;
}