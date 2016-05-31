/* CIST 2372
Nic Smith
SID: 6575
Midterm Project - My Photos */
package myphotos;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

class Photo implements Comparator<Photo>{

    private String DateTaken, PlaceTaken, FilePath;
    private BasicFileAttributes attr;
    private int index;
    private Image imgFile;
    
    Photo(String filePath) {
        Path fPath = Paths.get(filePath);
        try {
            attr = Files.readAttributes(fPath, BasicFileAttributes.class);
        } catch (IOException ex) {
            Logger.getLogger(Photo.class.getName()).log(Level.SEVERE, null, ex);
        }
        DateTaken = attr.creationTime().toString();
        PlaceTaken = "Unknown";
        FilePath = filePath;
        imgFile = new Image("file:\\" + filePath);
    }
    
    // accessors
    int getIndex(){
        return index;
    }
    String getDateTaken(){
        return DateTaken;
    }
    String getPlaceTaken(){
        return PlaceTaken;
    }
    String getFilePath(){
        return FilePath;
    }
    void setIndex(int in){
        index = in;
    }
    void setDateTaken(String dt){
        DateTaken = dt;
    }
    void setPlaceTaken(String pt){
        PlaceTaken = pt;
    }
    void setFilePath(String fn){
        FilePath = fn;
    }
    static Comparator<Photo> COMPARE_BY_TITLE = new Comparator<Photo>() {
        @Override
        public int compare(Photo o1, Photo o2) {
            return o1.getFilePath().compareTo(o2.getFilePath());
        }
    };
    static Comparator<Photo> COMPARE_BY_DATETAKEN = new Comparator<Photo>() {
        @Override
        public int compare(Photo o1, Photo o2) {
            return o1.getDateTaken().compareTo(o2.getDateTaken());
        }
    };
    static Comparator<Photo> COMPARE_BY_PLACETAKEN = new Comparator<Photo>() {
        @Override
        public int compare(Photo o1, Photo o2) {
            return o1.getPlaceTaken().compareTo(o2.getPlaceTaken());
        }
    };

    @Override
    public int compare(Photo t, Photo t1) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    Image getImg() {
        return imgFile;
    }
}
