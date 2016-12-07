package Slovnik;

import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Controller pro práci s FXML.
 */

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
    @FXML
    private ListView levenshteinList;
    @FXML
    private Button addToDictionary;

    private String findingWord;
    private static FileChooser fileChooser;
    private static Scanner sc;
    String word;
    public String text = "";
    Set<String> dictionary = new HashSet<>();

    /**
     * Importuje text, z kterého udělá slovník.
     */

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
                    word = word.replaceAll("[\\p{Punct}\\p{Digit}]", "");
                    if (!word.equals("")) {
                        dictionary.add(word);
                    }
                }
                List dictionaryList = new ArrayList<>(dictionary);
                Collections.sort(dictionaryList, String.CASE_INSENSITIVE_ORDER);
                listView.setItems(FXCollections.observableList(dictionaryList));
                dictionaryText.setText("Používaný slovník (" + dictionary.size() + " slov)");
                sc.close();
            } catch (Exception e) {
                System.out.println("Chyba při importu textu");
            }
        }
        //else statusText.setText("Nebyl vybrán žádný soubor");
    }

    /**
     * Exportuje slovník do textového souboru.
     */

    public void export() {
        Stage stage = (Stage) gp.getScene().getWindow();
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
        fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Textový soubor", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setInitialFileName("Slovnik-" + timeStamp + ".txt");

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
            System.out.println("Chyba při exportu textu");
        }
    }

    /**
     * Importuje slovník, který je v textovém souboru do ListView.
     */

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
                List dictionaryList = new ArrayList<>(dictionary);
                listView.setItems(FXCollections.observableList(dictionaryList));
                dictionaryText.setText("Používaný slovník (" + dictionary.size() + " slov)");
            } catch (Exception e) {
                System.out.println("Chyba při importu textu");
            }
        }
        //else statusText.setText("Nebyl vybrán žádný soubor");
    }

    /**
     * Importuje text, který pak slouží pro vyhledávání.
     */

    @FXML
    public void importText() {
        Stage stage = (Stage) gp.getScene().getWindow();
        fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a File");
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Textový soubor", "*.txt");
        fileChooser.getExtensionFilters().add(extensionFilter);
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            try {
                BufferedReader bf = new BufferedReader(new FileReader(file));
                String input;
                while ((input = bf.readLine()) != null) {
                    newText.appendText(input);
                }
                bf.close();
            } catch (Exception e) {
                System.out.println("Chyba při importu textu");
            }
        }
    }

    /**
     * Exportuje slovník do CSV souboru.
     *
     * @throws FileNotFoundException
     */

    public void exportCSV() throws FileNotFoundException {
        String csvText = "";
        Stage stage = (Stage) gp.getScene().getWindow();
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
        fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV soubor", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setInitialFileName("Slovnik-" + timeStamp + ".csv");

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
            System.out.println("Chyba při exportu CSV");
        }
    }

    /**
     * Importuje slovník, který je uložen v souboru typu CSV do ListView.
     */

    public void importCSV() {
        Stage stage = (Stage) gp.getScene().getWindow();
        fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a File");
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("CSV soubor", "*.csv");
        fileChooser.getExtensionFilters().add(extensionFilter);
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            dictionary.clear();
            try {
                sc = new Scanner(file, "UTF-8");
                while (sc.hasNext()) {
                    String temp = sc.next();
                    String[] items = temp.split(";");
                    for (int i = 0; i < items.length; i++) {
                        dictionary.add(items[i]);
                    }
                }
                List dictionaryList = new ArrayList<>(dictionary);
                listView.setItems(FXCollections.observableList(dictionaryList));
                dictionaryText.setText("Používaný slovník (" + dictionary.size() + " slov)");
                sc.close();
            } catch (Exception e) {
                System.out.println("Chyba při importu CSV");
            }
        }
    }

    /**
     * Message box pro výběr typu importu.
     */

    public void importMessageBox() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Import slovníku");
        alert.setHeaderText("Výběr typu souboru");
        ButtonType csvButton = new ButtonType("CSV");
        ButtonType txtButton = new ButtonType("TXT");
        ButtonType exitButton = new ButtonType("Zrušit", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(csvButton, txtButton, exitButton);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == csvButton) {
            importCSV();
        } else if (result.get() == txtButton) {
            importFile();
        } else {
            alert.close();
        }
    }

    /**
     * Message box pro výběr typu exportu.
     *
     * @throws FileNotFoundException
     */

    public void exportMessageBox() throws FileNotFoundException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Export slovníku");
        alert.setHeaderText("Výběr typu souboru");
        ButtonType csvButton = new ButtonType("CSV");
        ButtonType txtButton = new ButtonType("TXT");
        ButtonType exitButton = new ButtonType("Zrušit", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(csvButton, txtButton, exitButton);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == csvButton) {
            exportCSV();
        } else if (result.get() == txtButton) {
            export();
        } else {
            alert.close();
        }
    }

    /**
     * Metoda, která hledá slovo v načteném textu.
     */

    public void findKey() {
        findingWord = key.getText();
        if (!findingWord.equals("") && findingWord.replaceAll("[\\p{Punct}\\p{Digit} ]", "").equals(findingWord)) {
            if (!isInDictionary(findingWord)) {
                addToDictionary.setDisable(false);
            } else {
                addToDictionary.setDisable(true);
            }
            keyText.setText("");
            listOfIndexes.getItems().clear();
            text = newText.getText();
            TextSearching.findInText(text.toLowerCase(), key.getText().toLowerCase());
        }
    }

    /**
     * Metoda prochází pustupně slovník a porovnává hodnoty se zadaným slovem.
     * Dochází zde k vypíšu 10 prvků slovníku s nejnižší nalezenou hodnotou.
     */

    public void getLevenshteinValues() {

        if (!isInDictionary(key.getText()) && key.getText().replaceAll("[\\p{Punct}\\p{Digit}]", "").equals(key.getText())) {
            levenshteinList.getItems().clear();
            int distance = 0;
            MyDistanceComp dis = new MyDistanceComp();
            LinkedList<Distance> list = new LinkedList<Distance>();

            for (String s : dictionary) {
                distance = LevenshteinDistance.distance(key.getText(), s);
                list.add(new Distance(s, distance));
            }

            Collections.sort(list, dis);

            for (int i = 0; ((i < 10) && (i < list.size())); i++) {
                Distance d = list.get(i);
                levenshteinList.getItems().add(d);
            }
            addToDictionary.setDisable(false);

        }
    }

    /**
     * Zkontroluje, zda-li je slovo ve slovníku.
     *
     * @param word slovo hledající ve slovníku.
     * @return když je ve slovníku, tak vrátí true, jinak false.
     */

    public boolean isInDictionary(String word) {
        for (String s : dictionary) {
            if (s.equals(word)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Přidá slovo do slovínku. List view se slovníkem aktualizace.
     */

    public void addWordDictionary() {
        dictionary.add(findingWord);
        List dictionaryList = new ArrayList<>(dictionary);
        Collections.sort(dictionaryList, String.CASE_INSENSITIVE_ORDER);
        listView.setItems(FXCollections.observableList(dictionaryList));
        addToDictionary.setDisable(true);
    }

    /**
     * Zobrazí počáteční a konečné indexy nalezené slova do ListView.
     *
     * @param key  hledaný klíč.
     * @param list indexy hledaného slova.
     */

    public void setSearchedIndexes(String key, List<String> list) {
        listOfIndexes.setItems(FXCollections.observableList(list));
        keyText.setText(key + " (" + list.size() + ")");
        levenshteinList.getItems().clear();
    }

}
