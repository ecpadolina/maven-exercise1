package com.epado.app;

import java.util.*;

public class GridValidators{
    private Scanner sc = new Scanner(System.in);
    
    private int checkInput(){
        int input = 0;
        boolean endLoop = false;
        while(!endLoop){
            try{
                input = sc.nextInt();
                endLoop = true;
            } catch(InputMismatchException e){
                System.out.println("Input is wrong!");
                sc.nextLine();
                System.out.print("Input again:");
            }
        }
        return input;
    }

    public GridObject checkIfExist(LinkedHashMap<GridObject,String> lhm){
        GridObject go;
        go = new GridObject(sc.next());
        do{
            if(lhm.containsKey(go)){
                System.out.println("Key already exists");
                sc.nextLine();
                System.out.print("Input again: ");
            }
        }while(lhm.containsKey(go));
        return go;
    }
    
    public int checkIntInput(){
        int option;
        option = checkInput();
        return option;
    }   
    
    public String replaceDelimeterInputToSpecialChar(String a){
        char replaceforComma = 128;
        char replaceforSpace = 129;
        char[] b = a.toCharArray();
        if(a.contains(",") || a.contains(" ")){
            for(int i = 0; i < b.length; i++){
                if(b[i] == ',')
                    b[i] = replaceforComma;
                if(b[i] == ' ')
                    b[i] = replaceforSpace;
            }
        }
        String newString = new String(b);
        return newString;
    }
    
    public String replaceSpecialChartoDelimiter(String a){
        char replaceforComma = 128;
        char replaceforSpace = 129;
        String specCharforComma = String.valueOf(replaceforComma);
        String specCharforSpace = String.valueOf(replaceforSpace);
        char[] b = a.toCharArray();
        if(a.contains(specCharforComma) || a.contains(specCharforSpace)){
            for(int i = 0; i < b.length; i++){
                if(b[i] == replaceforComma)
                    b[i] = ',';
                if(b[i] == replaceforSpace)
                    b[i] = ' ';
            }
        }
        String newString = new String(b);
        return newString;
    }
}
