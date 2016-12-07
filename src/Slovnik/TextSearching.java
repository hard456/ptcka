package Slovnik;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

/**
 * Třída pro vyhledávání v textu.
 */
public class TextSearching {

    private String textToFind;
    public static ArrayList<String> indexList = new ArrayList<String>();

    /**
     * Metoda, která vyhledává slovo v textu.
     * @param text zadaný text, v kterém se vyhledává.
     * @param key slovo, která se vyhledává.
     */
    public static void findInText(String text, String key) {
        indexList.clear();
        if (!text.equals("") && !key.equals("")) {
            int index = text.indexOf(key);
            while (index >= 0) {
                if(checkIndexAfter(text, index + key.length()) && checkIndexBefore(text, index)){
                    indexList.add("{"+index + ", " + (index + key.length()-1)+"}");
                    index = text.indexOf(key, index + 1);
                }
                else{
                    index = text.indexOf(key, index + 1);
                }
            }
        }

        Controller ct = Main.fxml.getController();
        if(indexList.size() > 0) {
            ct.setSearchedIndexes(key, indexList);
            indexesToFile(indexList, key);
        }
        else {
            ct.getLevenshteinValues();
        }
    }

    /**
     * Metoda, která zkoumá index před slovem, které se vyhledalo.
     * @param text zadaný text, v kterém se hledá.
     * @param index hodnota indexu, na který se tato metoda kouká.
     * @return v případě, že se jedná o true, tak je pozice před slovem v pořádku.
     */
    private static boolean checkIndexBefore(String text, int index){
        if(index != 0){
            if(text.charAt(index - 1) == ' '){
                return true;
            }
            return false;
        }
        return true;

    }

    /**
     * Metoda, která se kouká za index slova, které se vyhledalo.
     * @param text zadaný text, v kterém se hledá.
     * @param index hodnota indexu, na který se metoda kouká.
     * @return v případě, že metoda vracá true, tak je index za slovem je v pořádku.
     */

    private static boolean checkIndexAfter(String text, int index){
        if(index != text.length()){
            if("., !?".indexOf(text.charAt(index)) != -1){
                return true;
            }
            return false;
        }
        return true;
    }

    /**
     * Metoda, která zapíše indexy do souboru v případě, že dojde k nalezení slova.
     * @param list seznam indexů.
     * @param key slovo, které se hledalo.
     */
    private static void indexesToFile(ArrayList<String> list, String key){
        File file = new File("output.txt");
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(key + " ("+ list.size() +")" + System.lineSeparator());
            for (int i = 0; i < list.size(); i++) {
                writer.write(list.get(i)+System.lineSeparator());
            }
            writer.close();
        } catch (Exception e) {
        }
    }

}
