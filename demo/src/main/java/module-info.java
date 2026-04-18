module com.proyecto {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens com.proyecto to javafx.fxml;
    exports com.proyecto;
}
