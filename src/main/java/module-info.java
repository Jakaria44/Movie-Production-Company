module com.example.movielogin {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
//    requires annotations;
//    requires java.lang.module.FindException ;


    opens com.example.movielogin to javafx.fxml;
    exports com.example.movielogin;
    exports util;
    opens util to javafx.fxml;
    exports Server;
    opens Server to java.base;

}