package Slovnik;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class Controller {
    @FXML
    public Text statusText;
    @FXML
    public Button openNew;
    @FXML
    public Button openExisting;
    @FXML
    private GridPane gp;

    public void open() {
        Stage stage = (Stage) gp.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a File");
        FileChooser.ExtensionFilter extensionFilter=new FileChooser.ExtensionFilter("Textový soubor", "*.txt");
        fileChooser.getExtensionFilters().add(extensionFilter);
        //for showing the popup window to open file
        File file=fileChooser.showOpenDialog(stage);
        if(file != null) statusText.setText("Vybraný soubor: " + file.getName().toString());
        else statusText.setText("Nebyl vybrán žádný soubor");
    }

}
