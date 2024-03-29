module ru.kozorez {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires com.almasb.fxgl.all;

    opens ru.kozorez to javafx.fxml;
    exports ru.kozorez;
}