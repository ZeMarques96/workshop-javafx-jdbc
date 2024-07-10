module javaf.workshopjavafxjdbc {
    requires javafx.controls;
    requires javafx.fxml;


    opens javaf.workshop to javafx.fxml;
    exports javaf.workshop;
    opens javaf.workshop.gui to javafx.fxml;
    exports javaf.workshop.gui;
    opens javaf.workshop.gui.util to javafx.fxml;
    exports javaf.workshop.gui.util;
}