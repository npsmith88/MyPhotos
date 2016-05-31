/* CIST 2372
 Nic Smith
 SID: 6575
 Midterm Project - My Photos */
package myphotos;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
import javafx.application.Application;
import javafx.collections.*;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.image.*;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Modality;

public class MyPhotos extends Application {

    ArrayList<Photo> photos = new ArrayList<>();
    ImageView photoView = new ImageView();
    int currentImg = 0;
    Label imageTitle;
    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) {
        // Create a border pane 
        BorderPane pane = new BorderPane();

        String imgDirectory = "src\\photos\\";
        final File folder = new File(imgDirectory);
        final File[] fileList = folder.listFiles();
        for (File file : fileList) {
            String filePath = file.getAbsolutePath();
            if (filePath.contains("png") ||
                    filePath.contains(".jpg") ||
                    filePath.contains(".jpeg") ||
                    filePath.contains(".gif") ||
                    filePath.contains(".bmp") ||
                    filePath.contains(".tmp")) {
                photos.add(new Photo(file.getAbsolutePath()));
            }
        }
        photoView.setImage(photos.get(currentImg).getImg());
        photoView.setPreserveRatio(true);
        photoView.setFitHeight(380);
        photoView.setFitWidth(380);
        HBox hBoxCenter = new HBox();
        hBoxCenter.setAlignment(Pos.CENTER);
        hBoxCenter.getChildren().add(photoView);

        // Create a scene and place it in the stage
        Scene scene = new Scene(pane);
        primaryStage.setTitle("MyPhotos"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage

        HBox hBox1 = new HBox(1);
        hBox1.setPadding(new Insets(1, 1, 1, 1));
        hBox1.setAlignment(Pos.BOTTOM_LEFT);

        imageTitle = new Label(photos.get(currentImg).getFilePath());
        hBox1.getChildren().add(imageTitle);

        HBox hBox2 = new HBox(4);
        hBox2.setPadding(new Insets(5, 5, 5, 5));
        hBox2.setAlignment(Pos.CENTER);

        Button RemoveButton = new Button("Remove Image");
        Button AddButton = new Button("Add Image");
        Button ShowMetaDataButton = new Button("Show MetaData");
        Button SortByTitleButton = new Button("Sort by Title");
        Button SortByDateButton = new Button("Sort by Date");
        Button SortByPlaceButton = new Button("Sort by Place");

        // create setOnAction for each button when pressed
        RemoveButton.setOnAction(e -> {
            if (!photos.isEmpty()) {
                System.out.println("Removed Img " + photos.get(currentImg).getFilePath());
                photos.remove(currentImg);
                if (!photos.isEmpty()) {
                    nextImg();
                } else {
                    imgRedraw();
                }
            }
        });
        AddButton.setOnAction(e -> {
            FileChooser fileChooser1 = new FileChooser();
            File selectFile = fileChooser1.showOpenDialog(primaryStage);
            String P = selectFile.getAbsolutePath().toLowerCase();
            if (selectFile != null){
                if (P.contains(".png") ||
                        P.contains(".jpg") ||
                        P.contains(".jpeg") ||
                        P.contains(".gif") ||
                        P.contains(".bmp") ||
                        P.contains(".tmp")) {
                    photos.add(new Photo(selectFile.getAbsolutePath()));
                    System.out.println("");
                }
            }
            imgRedraw();
        });
        SortByTitleButton.setOnAction(e -> {
            Collections.sort(photos, Photo.COMPARE_BY_TITLE);
            photos.get(currentImg);
        });
        SortByDateButton.setOnAction(e -> {
            Collections.sort(photos, Photo.COMPARE_BY_DATETAKEN);
            photos.get(currentImg);
        });
        SortByPlaceButton.setOnAction(e -> {
            Collections.sort(photos, Photo.COMPARE_BY_PLACETAKEN);
            photos.get(currentImg);
        });
        ShowMetaDataButton.setOnAction(e -> {
            if (!photos.isEmpty()) {
                Label dateLabel, placeLabel, filePathLabel;
                FlowPane pane2;
                Scene scene2;
                Stage newStage;
                dateLabel = new Label(photos.get(currentImg).getDateTaken());
                placeLabel = new Label(photos.get(currentImg).getPlaceTaken());
                filePathLabel = new Label(photos.get(currentImg).getFilePath());
                pane2 = new FlowPane();
                //add everything to pane
                pane2.getChildren().addAll(dateLabel, placeLabel, filePathLabel);
                //make scene2 from pane2
                scene2 = new Scene(pane2, 200, 100);
                //make a stage for scene2
                newStage = new Stage();
                newStage.setScene(scene2);
                //tell stage it is meant to pop-up (Modal)
                newStage.initModality(Modality.APPLICATION_MODAL);
                newStage.setTitle("MetaData");
                newStage.show();
            }
        });

        hBox2.getChildren().addAll(RemoveButton, AddButton, SortByTitleButton,
                SortByDateButton, SortByPlaceButton, ShowMetaDataButton);

        VBox vBoxL = new VBox(5);
        vBoxL.setPadding(new Insets(5, 5, 5, 5));
        vBoxL.setAlignment(Pos.CENTER);
        Button PrevButton = new Button("<<");
        PrevButton.setOnAction(e -> {
            prevImg();
        });
        vBoxL.getChildren().add(PrevButton);

        VBox vBoxR = new VBox(5);
        vBoxR.setPadding(new Insets(5, 5, 5, 5));
        vBoxR.setAlignment(Pos.CENTER);
        Button NextButton = new Button(">>");
        NextButton.setOnAction(e -> {
            nextImg();
            System.out.println("" + currentImg);
        });
        vBoxR.getChildren().add(NextButton);

        // Place nodes in the pane
        pane.setTop(hBox1);
        pane.setLeft(vBoxL);
        pane.setCenter(hBoxCenter);
        pane.setRight(vBoxR);
        pane.setBottom(hBox2);

    }

    // Set display information
    private void nextImg() {
        if (!photos.isEmpty()) {
            if (currentImg + 1 < photos.size()) {
                currentImg++;
            } else {
                currentImg = 0;
            }
            imgRedraw();
        }
    }
    private void prevImg() {
        if (!photos.isEmpty()) {
            if (currentImg - 1 > -1) {
                currentImg--;
            } else {
                currentImg = photos.size() - 1;
            }
            imgRedraw();
        }
    }
    private void imgRedraw() {
        if (!photos.isEmpty()) {
            imageTitle.setText(photos.get(currentImg).getFilePath());
            photoView.setImage(photos.get(currentImg).getImg());
        } else {
            imageTitle.setText("");
            photoView.setImage(null);
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}
