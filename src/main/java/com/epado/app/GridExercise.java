package com.epado.app;

import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class GridExercise{
    private static ArrayList<LinkedHashMap<GridObject,String>> alist = new ArrayList<LinkedHashMap<GridObject,String>>();
    private static Scanner sc = new Scanner(System.in);
    private static GridFunctions gf;
    private static GridUtil gu;
    
    private static int maxRows;
    private static int maxCols;
    
    public static void main(String[] args){
        GridExercise ge = new GridExercise();
        gu = new GridUtil();
        gu.setFile(args[0]);
        if(gu.readFile()){
            gf = new GridFunctions();
            gf.initializeGrid();
            ge.showOptions();
        }
    }
        
    private void showOptions(){
        System.out.print("\nOPTIONS:\n" + 
                        "[1] Print\n" +
                        "[2] Edit \n" +
                        "[3] Search \n" + 
                        "[4] Add New Row\n" +
                        "[5] Sort Row\n" +
                        "[6] Exit\n" +
                        "Choose Your Option: ");
        String option = sc.next();
        
        if(option.matches("[123456]")){
            if(option.equals("1")){
                gf.initializeGrid();
                showOptions();
            }
            else if(option.equals("2")){
                gf.editData();
                showOptions();
            }
            else if(option.equals("3")){
                gf.searchString();
                showOptions();  
            }
            else if(option.equals("4")){
                gf.addNewRow();
                showOptions();
            }
            else if(option.equals("5")){
                gf.sortRow();
                showOptions();
            }
            else if(option.equals("6")){
                return;
            }
        }
        else{
            System.out.println("Option not available");
            showOptions();
        }
    }
}
