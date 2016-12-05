package slovnik;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
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
    public Button importText;
    @FXML
    public TextArea newText;
    @FXML
    public ListView listView;
    @FXML
    public Text dictionaryText;
    @FXML
    private GridPane gp;
    @FXML
    private TextField key;
    @FXML
    private Text keyText;
    @FXML
    private ListView listOfIndexes;

    private static FileChooser fileChooser;
    private static Scanner sc;
    String word;
    public String text = "";
    ArrayList<String> dictionary = new ArrayList();

    public void open() {
        Stage stage = (Stage) gp.getScene().getWindow();
        fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a File");
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Textový soubor", "*.txt");
        fileChooser.getExtensionFilters().add(extensionFilter);
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            dictionary.clear();
            //statusText.setText("Vybraný soubor: " + file.getName().toString());
            try {
                sc = new Scanner(file, "UTF-8");
                while (sc.hasNext()) {
                    word = sc.next().toLowerCase();
                    word = word.replaceAll("[\\p{Punct}\\p{Digit}“”’‘`'´·¯—]", "");
                    if(!word.equals("")){
                        dictionary.add(word);
                    }
                }
                Set<String> hs = new HashSet(dictionary);
                dictionary.clear();
                dictionary.addAll(hs);
                Collections.sort(dictionary, String.CASE_INSENSITIVE_ORDER);
                listView.setItems(FXCollections.observableList(dictionary));
                dictionaryText.setText("Používaný slovník (" + dictionary.size() + " slov)");
                sc.close();
            } catch (Exception e) {
            }
        }
        //else statusText.setText("Nebyl vybrán žádný soubor");
    }

    public void export() {
        Stage stage = (Stage) gp.getScene().getWindow();
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
        fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Textový soubor", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setInitialFileName("slovnik-" + timeStamp + ".txt");

        File file = fileChooser.showSaveDialog(stage);
        try {
            FileWriter writer = new FileWriter(file);
            for (String str : dictionary) {
                writer.write(str + System.lineSeparator());
            }
            writer.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Slovník byl vyexportován");
            alert.setTitle("Export slovníku");
            alert.setGraphic(null);
            alert.setHeaderText(null);
            alert.showAndWait();
        } catch (Exception e) {
        }
    }

    public void importFile() {
        Stage stage = (Stage) gp.getScene().getWindow();
        fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a File");
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Textový soubor", "*.txt");
        fileChooser.getExtensionFilters().add(extensionFilter);
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            dictionary.clear();
            //statusText.setText("Vybraný soubor: " + file.getName().toString());
            try {
                sc = new Scanner(file, "UTF-8");
                while (sc.hasNext()) {
                    dictionary.add(sc.next());
                }
                sc.close();
                listView.setItems(FXCollections.observableList(dictionary));
                dictionaryText.setText("Používaný slovník (" + dictionary.size() + " slov)");
            } catch (Exception e) {
            }
        }
        //else statusText.setText("Nebyl vybrán žádný soubor");
    }

    public void importText() {
        int count = 0;
        newText.setMaxWidth(520);
        newText.setWrapText(true);
        Stage stage = (Stage) gp.getScene().getWindow();
        fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a File");
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Textový soubor", "*.txt");
        fileChooser.getExtensionFilters().add(extensionFilter);
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            try {
                sc = new Scanner(file, "UTF-8");
                while (sc.hasNext()) {
                    if (count != 0) {
                        text += " ";
                    }
                    count++;
                    text += " ";
                    text += sc.next();
                }
                newText.setText(text);
                sc.close();
            } catch (Exception e) {
            }
        }
    }

    public void exportCSV() throws FileNotFoundException {
        String csvText = "";
        Stage stage = (Stage) gp.getScene().getWindow();
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
        fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV soubor", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setInitialFileName("slovnik-" + timeStamp + ".csv");

        File file = fileChooser.showSaveDialog(stage);
        try {
            FileWriter writer = new FileWriter(file);
            for (String temp : dictionary) {
                csvText = csvText + temp + ";";
            }
            writer.write(csvText);
            writer.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Slovník byl vyexportován");
            alert.setTitle("Export slovníku");
            alert.setGraphic(null);
            alert.setHeaderText(null);
            alert.showAndWait();
        } catch (Exception e) {
        }
    }

    public void importCSV() {
        Stage stage = (Stage) gp.getScene().getWindow();
        fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a File");
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("CSV soubor", "*.csv");
        fileChooser.getExtensionFilters().add(extensionFilter);
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            dictionary.clear();
            //statusText.setText("Vybraný soubor: " + file.getName().toString());
            try {
                sc = new Scanner(file, "UTF-8");
                while (sc.hasNext()) {
                    String temp = sc.next();
                    String[] items = temp.split(";");
                    for (int i = 0; i < items.length; i++) {
                        dictionary.add(items[i]);
                    }
                }
                listView.setItems(FXCollections.observableList(dictionary));
                dictionaryText.setText("Používaný slovník (" + dictionary.size() + " slov)");
                sc.close();
            } catch (Exception e) {
            }
        }
    }

    public void importMessageBox() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Import slovníku");
        alert.setHeaderText("Výběr typu souboru");
        ButtonType csvButton = new ButtonType("CSV");
        ButtonType txtButton = new ButtonType("TXT");
        ButtonType exitButton = new ButtonType("Zrušit", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(csvButton, txtButton, exitButton);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == csvButton) importCSV();
        else if (result.get() == txtButton) importFile();
        else alert.close();
    }

    public void exportMessageBox() throws FileNotFoundException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Export slovníku");
        alert.setHeaderText("Výběr typu souboru");
        ButtonType csvButton = new ButtonType("CSV");
        ButtonType txtButton = new ButtonType("TXT");
        ButtonType exitButton = new ButtonType("Zrušit", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(csvButton, txtButton, exitButton);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == csvButton) exportCSV();
        else if (result.get() == txtButton) export();
        else alert.close();
    }

    public void findKey() {
        text = newText.getText();
        TextSearching.findInText(text.toLowerCase(), key.getText().toLowerCase());
    }

    public void findWord(){
        text = newText.getText();
        getLevenshteinValues(key.getText());
    }

    public void getLevenshteinValues(String key){
        if(!isInDictionary(key)) {
            String word = "";
            int distance = 0;

            LinkedList<Distance> list = new LinkedList<Distance>();


            for (String s : dictionary) {
                distance = LevenshteinDistance.distance(key, s);
                list.add(new Distance(s, distance));
            }

            Collections.sort(list, new MyDistanceComp());

            for (int i = 0; ((i < 10) || (i < list.size())); i++) {
                Distance d = list.get(i);
                System.out.println(d);
            }
        }
    }

    public boolean isInDictionary(String word){
        for (String s:dictionary) {
            if(s.equals(word)){
                return true;
            }
        }
        return false;
    }

    public void setSearchedIndexes(String key, ArrayList<String> list) {
        listOfIndexes.setItems(FXCollections.observableList(list));
        keyText.setText(key + " (" + list.size() + ")");
    }

}
