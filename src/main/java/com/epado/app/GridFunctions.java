package com.epado.app;

import java.util.*;

public class GridFunctions{
    private static ArrayList<LinkedHashMap<GridObject,String>> aList = new ArrayList<LinkedHashMap<GridObject,String>>();
    private static GridValidators gv = new GridValidators();
    private GridUtil gu = new GridUtil();
    private Scanner sc = new Scanner(System.in);
    private int maxRows;
    private int maxCols;
    
    public void initializeGrid(){
        System.out.println();
        aList = gu.populateCollection();
        for(LinkedHashMap<GridObject,String> hmap : aList){
        System.out.print("| ");
            for(Map.Entry me : hmap.entrySet()){
                Object obj = me.getKey();
                GridObject go = (GridObject)obj;
                String key = gv.replaceSpecialChartoDelimiter(go.getKey());
                String val = gv.replaceSpecialChartoDelimiter((String)me.getValue());
                System.out.print(key + "," + val);
                System.out.print(" | ");
            }
        System.out.println();
        }
    }
    
    public void editData(){
        getMaxRowCol();
        int rowToEdit = 0, colToEdit = 0;
        System.out.print("\nEnter row to edit: ");
        rowToEdit = gv.checkIntInput();
        System.out.print("Enter column to edit: ");
        colToEdit = gv.checkIntInput();
        
        if((colToEdit <= maxCols && rowToEdit <= maxRows) && (colToEdit > 0 && rowToEdit > 0)){
            int counter = 1;
            GridObject oldkey = null;
            String oldval = "";
            int option = 0;
            
            System.out.println("\nChoose what to edit: ");
            System.out.println("[1] Key");
            System.out.println("[2] Value");
            
            do{
                System.out.print("Option: ");
                option = gv.checkIntInput();
                if(option <= 0 || option > 2)
                    System.out.println("Input the right option!");
            }while(option <= 0 || option > 2);
            
            String newVal = "";
            LinkedHashMap<GridObject,String> hashEntry = aList.get(rowToEdit - 1);
            System.out.print("Enter new value: ");
            if(option == 1){
                GridObject go = gv.checkIfExist(hashEntry);
                LinkedHashMap<GridObject,String> newHashEntry = 
                    new LinkedHashMap<GridObject,String>();
                for(Map.Entry me : hashEntry.entrySet()){
                    if(counter == colToEdit){
                        replaceKeysinObject(go);
                        String val = 
                            gv.replaceDelimeterInputToSpecialChar((String)me.getValue());
                        newHashEntry.put(go, val);
                    }
                    else{
                        GridObject key = (GridObject)me.getKey();
                        replaceKeysinObject(key);
                        String val = 
                            gv.replaceDelimeterInputToSpecialChar((String)me.getValue());
                        newHashEntry.put(key, val);
                    }
                    counter++;
                }
                aList.set(rowToEdit-1,newHashEntry);
            }
            else if(option == 2){
                newVal = sc.next();
                for(Map.Entry me : hashEntry.entrySet()){
                    if(counter == colToEdit)
                        oldkey = (GridObject)me.getKey();
                        counter++;
                }
                newVal = gv.replaceDelimeterInputToSpecialChar(newVal);
                hashEntry.put(oldkey, newVal);
                aList.set(rowToEdit-1, hashEntry);
            }
            gu.writeToFile(getContent());
        } else {
            System.out.println("Row/Col not found!");
            editData();
        }
    }
    
    public void searchString(){
        System.out.print("\nEnter search parameter: ");
        String searchParam = sc.next();
        searchParam = searchParam.replaceAll("\\s+", "");
        String newSearchParam = gv.replaceDelimeterInputToSpecialChar(searchParam);
        
        String output = "";
        int totalOccurrences = 0;
        int colCounter = 1;
        for(HashMap<GridObject,String> hm : aList){
            for(Map.Entry me : hm.entrySet()){
                GridObject key = (GridObject)me.getKey();
                String val = (String)me.getValue();
                int keyOccurrences = 0;
                int valOccurrences = 0;
                if(val.contains(newSearchParam) || key.contains(newSearchParam)){
                    if(val.contains(newSearchParam))
                        valOccurrences = checkOccurrances(val, newSearchParam);
                    if(key.contains(newSearchParam))
                        keyOccurrences = checkOccurrances(key, newSearchParam);
                    output += "["+ (aList.indexOf(hm) + 1) + "][" +colCounter + "] " 
                            + "with Key Occurances: " + keyOccurrences 
                            +  " Value Occurances: " + valOccurrences + "\n";
                }
                totalOccurrences = totalOccurrences + (valOccurrences + keyOccurrences);
                colCounter++;
            }
            colCounter = 1;
        }
        System.out.print("Search parameter \"" + searchParam + 
            "\" occured (" + totalOccurrences + ") time(s) at \n" + output); 
    }
  
    public void sortRow(){
        System.out.print("\nEnter row to sort: ");
        int rowToSort = gv.checkIntInput();
        if(rowToSort <= aList.size()){   
            System.out.println("Sort by: ");
            System.out.println("[1] Ascending");
            System.out.println("[2] Descending");
            System.out.print("Choose your option: ");
            String choice = sc.next();
            aList.set(rowToSort - 1, rowSorting(rowToSort, choice));
            gu.writeToFile(getContent());
        } else {
            System.out.println("Row not found!");
            sortRow();
        }
    }
    
    public void addNewRow(){
        LinkedHashMap<GridObject,String> tempHMap = new LinkedHashMap<GridObject,String>();
        LinkedHashMap<GridObject,String> anotherMap = new LinkedHashMap<GridObject,String>();
        getMaxRowCol();
        String val; 
        GridObject go;
        
        System.out.println("Adding new row [" + (maxRows + 1) + "]:");
        
        for(int i = 0; i < maxCols; i++){
            System.out.print("Add KEY for cell ["+ (maxRows + 1) + "][" + (i + 1) + "]: ");
            go =  gv.checkIfExist(tempHMap);
            replaceKeysinObject(go);
            System.out.print("Add VALUE for cell ["+ (maxRows + 1) + "][" + (i + 1) + "]: ");
            val =  sc.next();
            val = gv.replaceDelimeterInputToSpecialChar(val);
            tempHMap.put(go,val);
        }
        aList.add(tempHMap);
        gu.writeToFile(getContent());
    }
    
    private LinkedHashMap<GridObject, String> rowSorting(int rowToSort, String order){
        LinkedHashMap<GridObject,String> hMap = aList.get(rowToSort - 1);
        List<String> elementsToBeSorted = new ArrayList<String>();
        String newVal = "";
        String newKey = "";
        char temp = 130;
        String tempReplacement = String.valueOf(temp);
        for(Map.Entry me : hMap.entrySet()){
            Object obj = me.getKey();
            GridObject go = (GridObject)obj;
            replaceKeysinObject(go);
            newKey = (String)go.getKey();
            newVal = gv.replaceDelimeterInputToSpecialChar((String)me.getValue());
            if(newKey.contains("|") || newVal.contains("|")){
                newKey = newKey.replaceAll("\\|", tempReplacement);
                newVal = newVal.replaceAll("\\|", tempReplacement);  
            }
            String tempString = newKey + "|" + newVal;
            elementsToBeSorted.add(tempString);
        }
        
        if(order.matches("[12]")){
            if(order.equals("1")){
                hMap.clear();
                Collections.sort(elementsToBeSorted);
                for(String elements : elementsToBeSorted){
                    String splitKeysAndValues[] = elements.split("\\|");
                    splitKeysAndValues[0] = splitKeysAndValues[0].replaceAll(tempReplacement, "|");
                    splitKeysAndValues[1] = splitKeysAndValues[1].replaceAll(tempReplacement, "|");
                    hMap.put(new GridObject(splitKeysAndValues[0]),splitKeysAndValues[1]);
                }
            }
            else if(order.equals("2")){
                hMap.clear();
                Collections.sort(elementsToBeSorted, Collections.reverseOrder());
                for(String elements : elementsToBeSorted){
                    String splitKeysAndValues[] = elements.split("\\|");
                    splitKeysAndValues[0] = splitKeysAndValues[0].replaceAll(tempReplacement, "|");
                    splitKeysAndValues[1] = splitKeysAndValues[1].replaceAll(tempReplacement, "|");
                    hMap.put(new GridObject(splitKeysAndValues[0]),splitKeysAndValues[1]);
                }
            }
        } else {
            System.out.println("Option not found!");
            sortRow();
        }
        return hMap;
    }
    
    private int checkOccurrances(Object obj, String stringToFind){
        String tempString = "";
        
        if(obj instanceof GridObject){
            GridObject go = (GridObject)obj;
            tempString = go.getKey();
        }
        else if(obj instanceof String){
            tempString = obj.toString();
        }
        
        int count = 0;
        if(stringToFind.length() == 1){
            count = tempString.length() - tempString.replaceAll(stringToFind, "").length();
        } else {
            int lastIndex = 0;
            while ((lastIndex = tempString.indexOf(stringToFind, lastIndex)) != -1) {
                count++;
                lastIndex += stringToFind.length() - 1;
            }
        }
        return count;
    }
    
    private void getMaxRowCol(){
        maxRows = aList.size();
        Map<GridObject, String> tempMap = aList.get(maxRows - 1);
        maxCols = tempMap.size();
    }

    private String getContent(){
        String updated = "";
        int ctr = 1;
        
        for(LinkedHashMap<GridObject,String> hmap : aList){
            for(Map.Entry me : hmap.entrySet()){
                Object obj = me.getKey();
                GridObject go = (GridObject)obj;
                String key = go.getKey();
                updated += key + "," + (String)me.getValue();
                if(hmap.size() > ctr){
                    updated += " ";
                    ctr++;
                }
            }
            ctr = 1;
            updated += "\n";
        }
        return updated;
    }
    
    private void replaceKeysinObject(GridObject go){
        String key = go.getKey();
        go.setKey(gv.replaceDelimeterInputToSpecialChar(key));
    }
}
