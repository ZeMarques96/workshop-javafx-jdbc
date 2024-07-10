module javaf.workshopjavafxjdbc {
    requires javafx.controls;
    requires javafx.fxml;


    opens javaf.workshopjavafxjdbc to javafx.fxml;
    exports javaf.workshopjavafxjdbc;
}