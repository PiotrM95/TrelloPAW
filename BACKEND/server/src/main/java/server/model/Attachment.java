package server.model;

import server.dbQuerys;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;

public class Attachment {
    private File file;

    public Attachment() throws FileNotFoundException, SQLException {
        setFile();
        dbQuerys.insertFile();
    }

    private void setFile() {
        JFileChooser fc = new JFileChooser();
        if(fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File plik = fc.getSelectedFile();
            this.file = plik;
        }
    }

    public File getFile() {
        return file;
    }
}
