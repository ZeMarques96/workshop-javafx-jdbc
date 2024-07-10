module javaf.workshopjavafxjdbc {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens javaf.workshop to javafx.fxml;
    exports javaf.workshop;
    opens javaf.workshop.gui to javafx.fxml;
    exports javaf.workshop.gui;
    opens javaf.workshop.gui.util to javafx.fxml;
    exports javaf.workshop.gui.util;
    opens javaf.workshop.model.entities to javafx.fxml;
    exports javaf.workshop.model.entities;
    opens javaf.workshop.model.service to javafx.fxml;
    exports javaf.workshop.model.service;
}