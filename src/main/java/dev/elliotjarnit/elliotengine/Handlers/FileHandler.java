package dev.elliotjarnit.elliotengine.Handlers;

import javax.swing.*;
import java.io.*;

public class FileHandler {
    public static String promptFileDialog() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new java.io.File("."));
        fileChooser.setDialogTitle("Select a file");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile().getAbsolutePath();
        }
        return null;
    }
    public static String promptFileDialog(String fileType) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new java.io.File("."));
        fileChooser.setDialogTitle("Select a file");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
            public boolean accept(java.io.File f) {
                return f.getName().toLowerCase().endsWith(fileType) || f.isDirectory();
            }
            public String getDescription() {
                return fileType;
            }
        });
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile().getAbsolutePath();
        }
        return null;
    }

    public static String[] loadFileFromResources(String fileName) {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream(fileName);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
        String[] lines = bufferedReader.lines().toArray(String[]::new);
        return lines;
    }

    public static String[] loadFile(String absolutePath) throws FileNotFoundException {
        FileReader fileReader = new FileReader(absolutePath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String[] lines = bufferedReader.lines().toArray(String[]::new);
        try {
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lines;
    }
}
