module com.example.labgui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.example.labgui.controller to javafx.fxml;
    opens com.example.labgui to javafx.fxml;

    exports com.example.labgui;
    exports com.example.labgui.controller;

    opens com.example.labgui.domain.domain to javfx.fxml;
    exports com.example.labgui.domain.domain;

    opens com.example.labgui.domain to javfx.fxml;
    exports com.example.labgui.domain;

}