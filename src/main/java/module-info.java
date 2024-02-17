module com.example.comp2013cw {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.desktop;
    requires jlayer;
    requires javafx.swing;


    opens Snakey to javafx.fxml;
    exports Snakey;
    exports Snakey.View;
    opens Snakey.View to javafx.fxml;
}