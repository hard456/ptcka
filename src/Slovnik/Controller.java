package Slovnik;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
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
    ArrayList<String> dictionary = new ArrayList();

    public void open() {
        Stage stage = (Stage) gp.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a File");
        FileChooser.ExtensionFilter extensionFilter=new FileChooser.ExtensionFilter("Textový soubor", "*.txt");
        fileChooser.getExtensionFilters().add(extensionFilter);
        File file=fileChooser.showOpenDialog(stage);
        if(file != null) {
            dictionary.clear();
            statusText.setText("Vybraný soubor: " + file.getName().toString());
            try{
                Scanner sc = new Scanner(file);
                while(sc.hasNext()){
                    word = sc.next().toLowerCase();
                    word = word.replaceAll("[,.;!?()]","");
                    dictionary.add(word);
                }
                Set<String> hs = new HashSet(dictionary);
                dictionary.clear();
                dictionary.addAll(hs);
                Collections.sort(dictionary, String.CASE_INSENSITIVE_ORDER);
                listView.setItems(FXCollections.observableList(dictionary));
                sc.close();
            } catch (Exception e){}
        }
        else statusText.setText("Nebyl vybrán žádný soubor");
    }

    public void export() {
        try {
            String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
            FileWriter writer = new FileWriter("slovnik-" + timeStamp + ".txt");
            for (String str : dictionary) {
                writer.write(str + System.lineSeparator());
            }
            writer.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Slovník byl vyexportován");
            alert.setTitle("Export slovníku");
            alert.setGraphic(null);
            alert.setHeaderText(null);
            alert.showAndWait();
        } catch (Exception e){}
    }

    public void importFile() {
        Stage stage = (Stage) gp.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a File");
        FileChooser.ExtensionFilter extensionFilter=new FileChooser.ExtensionFilter("Textový soubor", "*.txt");
        fileChooser.getExtensionFilters().add(extensionFilter);
        File file=fileChooser.showOpenDialog(stage);
        if(file != null) {
            dictionary.clear();
            statusText.setText("Vybraný soubor: " + file.getName().toString());
            try{
                Scanner sc = new Scanner(file);
                while(sc.hasNext()){
                    dictionary.add(sc.next());
                }
                sc.close();
                listView.setItems(FXCollections.observableList(dictionary));
            } catch (Exception e){}
        }
        else statusText.setText("Nebyl vybrán žádný soubor");
    }
}
