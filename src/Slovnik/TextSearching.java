package Slovnik;

import javafx.scene.control.Alert;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

/**
 * Created by HARD on 02.11.2016.
 */
public class TextSearching {

    private String textToFind;
    public static ArrayList<String> indexList = new ArrayList<String>();


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
            ct.findWord();
        }
    }

    private static boolean checkIndexBefore(String text, int index){
        if(index != 0){
            if(text.charAt(index - 1) == ' '){
                return true;
            }
            return false;
        }
        return true;

    }

    private static boolean checkIndexAfter(String text, int index){
        if(index != text.length()){
            if("., !?".indexOf(text.charAt(index)) != -1){
                return true;
            }
            return false;
        }
        return true;
    }

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
