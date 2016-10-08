package Slovnik;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.*;

public class Controller {
    @FXML
    public Text statusText;
    @FXML
    public Button openNew;
    @FXML
    public Button openExisting;
    @FXML
    public ListView listView;
    @FXML
    private GridPane gp;

    String word;
    ArrayList words = new ArrayList();

    public void open() {
        Stage stage = (Stage) gp.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a File");
        FileChooser.ExtensionFilter extensionFilter=new FileChooser.ExtensionFilter("Textový soubor", "*.txt");
        fileChooser.getExtensionFilters().add(extensionFilter);
        File file=fileChooser.showOpenDialog(stage);
        if(file != null) {
            statusText.setText("Vybraný soubor: " + file.getName().toString());
            try{
                Scanner sc = new Scanner(file);
                while(sc.hasNext()){
                    word = sc.next().toLowerCase();
                    word = word.replaceAll("[,.;!?()]","");
                    words.add(word);
                }
                Set<String> hs = new HashSet(words);
                words.clear();
                words.addAll(hs);
                Collections.sort(words, String.CASE_INSENSITIVE_ORDER);
                listView.setItems(FXCollections.observableList(words));
                sc.close();
            } catch (Exception e){}
        }
        else statusText.setText("Nebyl vybrán žádný soubor");
        System.out.println("a");
    }

}
