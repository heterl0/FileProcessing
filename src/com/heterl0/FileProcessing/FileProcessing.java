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

/**
 *
 * @author Le Van Hieu CE160866
 */
public class FileProcessing {

    public void checkInputPath(String path) {
        File f = new File(path);
        if (f.isFile()) {
            System.out.println("Path to file");
        } else if (f.isDirectory()) {
            System.out.println("Path to directory");
        } else {
            System.out.println("Path doesn't exist");
        }
    }

    public List<String> getAllFileNameJavaInDirectory(String path) {
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

    public File[] getFileWithSizeGreaterThanInput(String path, int size) {
        File f = new File(path);
        File[] listFile = f.listFiles(new FileFilter() {
            public boolean accept(File pathname) {
                if (Math.ceil(pathname.length() / 1024.0) >= size) {
                    return true;
                } else {
                    return false;
                }
            }
        });
//        File[] listFileGreaterThanInput = new File[listFile.length];
//        int index = 0;
//        for (File fileName : listFile) {
//            //  length() method return size of file with format (Byte) 
//            //  divide 1024 turn into KB, takes ceiling. (There is no file 
//            //  with 0KB!
//            if (Math.ceil(fileName.length() / 1024.0) >= size) {
//                listFileGreaterThanInput[index++] = fileName;
//            }
//        }
//        return listFileGreaterThanInput;
        return listFile;
    }

    public boolean appendContentToFile(String path, String input) {
        File f = new File(path);
        try (FileWriter fw = new FileWriter(f, true)) {
            fw.append(input);
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    public int countCharacter(String path) {
        File f = new File(path);
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line = br.readLine();
            int count = 0;
            while (line != null) {
                count += line.length();
                line = br.readLine();
            }
            return count;
        } catch (IOException ex) {
            return -1;
        }
    }

    public void callMenu() {
        Scanner sc = new Scanner(System.in);
        int choice = 0, size = 0;
        String contentInput = "", path = "";
        boolean isChoiceValid = false, isLoop = true;
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
                    if (choice < 1 && choice > 6) {
                        System.out.println("Option from 1 to 6!");
                    } else {
                        isChoiceValid = true;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Option from 1 to 6!");
                }
                sc.nextLine();
            } while (isChoiceValid == false);
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
            if (isLoop == true) {
                System.out.print("Enter Path: ");
                path = sc.nextLine();
            }
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
                    for (File file : arrFile) {
                        System.out.println((file != null ? file.getName() : ""));
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

    public static void main(String[] args) {
        FileProcessing fp = new FileProcessing();
        fp.callMenu();
    }

}
