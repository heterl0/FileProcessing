package com.heterl0.FileProcessing;

import java.io.BufferedReader;
import java.util.Scanner;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Le Van Hieu CE160866
 * class SE1607
 */
public class FileProcessing {
    
    private String path; // path is used to check and process
    
    /**
     * Check if a path have been existed, is a file or is a directory
     * the result will display inside method. Using Class File with two method 
     * isFile() is used to check a file and isDirectory() is used to check a
     * directory.
     * @param path a path name (String) 
     * 
     */
    public void checkInputPath(String path) {
        this.path = path;
        File f = new File(path);
        if (f.isFile()) {
            System.out.println("Path to file");
        } else if (f.isDirectory()) {
            System.out.println("Path to directory");
        } else {
            System.out.println("Path doesn't exist");
        }
    }

    /**
     * Get all file end with extension (.java). Using ArrayList to store all of 
     * java file at the current path user enter.
     * @param path a path name (String)
     * @return return List<String> all name (String) of java file at the current
     * directory.
     */
    public List<String> getAllFileNameJavaInDirectory(String path) {
        this.path = path;
        File f = new File(path);
        String[] listFile = f.list();
        ArrayList<String> listJava = new ArrayList<>();
        for (String fileName : listFile) {
            if (fileName.endsWith(".java")) {
                listJava.add(fileName);
            }
        }
        return listJava;

    }

    /**
     * Get all file with the length greater than the user's input size. 
     * Using FileFilter with a method accept() return true with file is satisfied
     * , false if not. 
     * @param path a path name (String)
     * @param size the smallest length of file user enter.  
     * @return an array of File contains all satisfied files.
     */
    public File[] getFileWithSizeGreaterThanInput(String path, int size) {
        this.path = path;
        File f = new File(path);
        if (!f.exists()) return null;
        // Using Interface FileFilter to filte files
        File[] listFile = f.listFiles(new FileFilter() {
            public boolean accept(File pathname) {
                
                //  length() method return size of file with format (Byte) 
                //  divide 1024 turn into KB, takes ceiling. (There is no file 
                //  with 0KB!
                if (Math.ceil(pathname.length() / 1024.0) > size) {
                    return true;
                } else {
                    return false;
                }
            }
        });
        return listFile;
    }

    /**
     * Processing file, append new content into a file that has been existed, 
     * just write into files with extension such as txt, docs,... document files.
     * Can't append to directory or something does not exist.
     * @param path a path name (String)
     * @param input input content user want to append to specific file.
     * @return return true if the method added successfully, or false if something
     * wrong happen.
     */
    public boolean appendContentToFile(String path, String input) {
        this.path = path;
        File f = new File(path);
        if (!f.exists()) try {
            f.createNewFile();
        } catch (IOException ex) {
            System.out.println("Can't crate new file!");
        }
        
        // Using FileWriter with append *mode*, add new content to the end of file
        try (FileWriter fw = new FileWriter(f, true)) {
            fw.append(input);
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    /**
     * Count the number of character in document files. Same as appendContentToFile
     * operate on file such as txt, docs, html,... Count everything in file Alphabet,
     * non-Alphabet, letters, digits, signals,...  all things are knew as Character.
     * @param path a path name (String)
     * @return the number of character in file. Return -1 if IOException happen.
     */
    public int countCharacter(String path) {
        this.path = path;
        File f = new File(path);
        // Using BufferedReader to read each line
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line = br.readLine();
            int count = 0;
            // Count each line until line turn null
            while (line != null) { 
                count += line.length();
                line = br.readLine();
            }
            return count;
        } catch (IOException ex) {
            // return -1 if exception happen
            return -1;
        }
    }

    /**
     * Main menu, display menu for user use. Menu with 6 functions.
     */
    public void callMenu() {
        Scanner sc = new Scanner(System.in);
        int choice = 0, size = 0;
        String contentInput = "", path = "";
        boolean isChoiceValid = false, isLoop = true;
        
        // loop program unless user choose number 6 to exit
        do {
            System.out.println("====== File Processing ======");
            System.out.println("1. Check Path");
            System.out.println("2. Get file name with type java");
            System.out.println("3. Get file with size  greater than input");
            System.out.println("4. Write more content to file");
            System.out.println("5. Read file and count characters");
            System.out.println("6. Exit");
            do {
                try {
                    System.out.print("Please choice one option: ");
                    choice = sc.nextInt();
                    if (choice < 1 || choice > 6) {
                        System.out.println("Option from 1 to 6!");
                    } else {
                        isChoiceValid = true;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Option from 1 to 6!");
                }
                sc.nextLine();
            } while (isChoiceValid == false);
            
            // display title of each function.
            switch (choice) {
                case 1:
                    System.out.println("- - - - Check Path - - - -");
                    break;
                case 2:
                    System.out.println("- - Get file name with type java - -");
                    break;
                case 3:
                    System.out.println("- - Get file with size greater than input - -");
                    boolean isSizeValid = false;
                    do {
                        try {
                            System.out.print("Enter Size (Integer): ");
                            size = sc.nextInt();
                            if (size <= 0) {
                                System.out.println("Size is greater than 0 (KB)!");
                            } else {
                                isSizeValid = true;
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Size is digit");
                        }
                        sc.nextLine();
                    } while (isSizeValid == false);
                    break;
                case 4:
                    System.out.println("- - - - Write more content to file - - - -");
                    boolean isValid = false;
                    do {
                        System.out.print("Enter Content: ");
                        contentInput = sc.nextLine().trim();
                        if (contentInput.equals("")) {
                            System.out.println("Content can't empty");
                        } else {
                            isValid = true;
                        }
                    } while (isValid == false);
                    break;
                case 5:
                    System.out.println("- - - - Read file and count characters - - - -");
                    break;
                case 6:
                    isLoop = false;
                    System.out.println("Thanks for using software");
                    System.out.println("Goodbye! See you later");
            }
            
            // if user's function is not number 6, user enter path to continue 
            // fucntion.
            if (isLoop == true) {
                System.out.print("Enter Path: ");
                path = sc.nextLine();
            }
            
            // call user's chose method
            switch (choice) {
                case 1:
                    checkInputPath(path);
                    break;
                case 2:
                    List<String> list = getAllFileNameJavaInDirectory(path);
                    System.out.println("Result " + list.size() + " file!");
                    break;
                case 3:
                    File[] arrFile = getFileWithSizeGreaterThanInput(path, size);
                    if (arrFile == null) {
                        System.out.println("The path does not exist!");
                        break;
                    }
                    for (File file : arrFile) {
                        System.out.println(file.getName());
                    }
                    System.out.println("Result " + arrFile.length + " file");
                    break;
                case 4:
                    if (appendContentToFile(path, contentInput)) {
                        System.out.println("Write done");
                    } else {
                        System.out.println("Path is not file");
                    }
                    break;
                case 5:
                    int countWord = countCharacter(path);
                    if (countWord == -1) {
                        System.out.println("Path is not file");
                    } else {
                        System.out.println("Total " + countWord);
                    }
            }
            System.out.println("Enter to continue!");
            sc.nextLine();
        } while (isLoop);
    }

    /**
     * Main function
     * @param args
     */
    public static void main(String[] args) {
        FileProcessing fp = new FileProcessing();
        fp.callMenu();
    }

}
