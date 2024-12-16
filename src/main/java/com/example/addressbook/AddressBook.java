package com.example.addressbook;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.FileChooser;

import java.io.*;

public class AddressBook extends Application {
    private static Contact contact = new Contact();
    private static StringBuilder recentlySavedFile = new StringBuilder();

    @Override
    public void start(Stage stage) throws IOException {
        BorderPane pane = new BorderPane();

        // Set up the center of the screen
        GridPane gridPane = new GridPane();
        gridPane.setHgap(15);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10));

        // First name
        Label lbFirstName = new Label("First Name");
        lbFirstName.setTextAlignment(TextAlignment.RIGHT);
        gridPane.add(lbFirstName, 0, 0, 1, 1);
        TextField tfFirstName = new TextField();
        gridPane.add(tfFirstName, 1, 0, 3, 1);
        Bindings.bindBidirectional(tfFirstName.textProperty(), contact.firstName());

        // Last name
        Label lbLastName = new Label("Last Name");
        gridPane.add(lbLastName, 4, 0, 1, 1);
        TextField tfLastName = new TextField();
        gridPane.add(tfLastName, 5, 0, 3, 1);

        // Spouse name
        Label lbSpouseName = new Label("Spouse");
        gridPane.add(lbSpouseName, 0, 1, 1, 1);
        TextField tfSpouseName = new TextField();
        gridPane.add(tfSpouseName, 1, 1, 3, 1);

        // Gender
        Label lbGender = new Label("Gender");
        gridPane.add(lbGender, 4, 1, 1, 1);
        RadioButton rbMale = new RadioButton("Male");
        gridPane.add(rbMale, 5, 1, 1, 1);
        RadioButton rbFemale = new RadioButton("Female");
        gridPane.add(rbFemale, 6, 1, 1, 1);

        ToggleGroup group = new ToggleGroup();
        rbMale.setToggleGroup(group);
        rbFemale.setToggleGroup(group);
        rbFemale.setSelected(true);

        // Phone types
        String phoneTypes[] = {"Mobile Phone", "Work Phone", "Home Phone"};
        ComboBox cbPhoneType1 = new ComboBox(FXCollections.observableArrayList(phoneTypes));
        cbPhoneType1.getSelectionModel().selectFirst();
        gridPane.add(cbPhoneType1, 0, 2, 2, 1);
        TextField tfPhoneType1 = new TextField();
        gridPane.add(tfPhoneType1, 2, 2, 2, 1);

        ComboBox cbPhoneType2 = new ComboBox(FXCollections.observableArrayList(phoneTypes));
        cbPhoneType2.getSelectionModel().selectFirst();
        gridPane.add(cbPhoneType2, 4, 2, 2, 1);
        TextField tfPhoneType2 = new TextField();
        gridPane.add(tfPhoneType2, 6, 2, 2, 1);

        // Address
        Label lbAddressType = new Label("Address Type");
        gridPane.add(lbAddressType, 0, 3, 2, 1);
        String addressTypes[] = {"Home", "Work"};
        ComboBox cbAddressType = new ComboBox(FXCollections.observableArrayList(addressTypes));
        cbAddressType.getSelectionModel().selectFirst();
        gridPane.add(cbAddressType, 2, 3, 2, 1);

        Label lbAddress = new Label("Address");
        gridPane.add(lbAddress, 0, 4, 1, 1);
        TextField tfAddress = new TextField();
        gridPane.add(tfAddress, 1, 4, 3, 1);

        Label lbCity = new Label("City");
        gridPane.add(lbCity, 4, 4, 1, 1);
        TextField tfCity = new TextField();
        gridPane.add(tfCity, 5, 4, 3, 1);

        Label lbState = new Label("State");
        gridPane.add(lbState, 0, 5, 1, 1);
        TextField tfState = new TextField();
        gridPane.add(tfState, 1, 5, 3, 1);

        Label lbZip = new Label("ZIP Code");
        gridPane.add(lbZip, 4, 5, 1, 1);
        TextField tfZip = new TextField();
        gridPane.add(tfZip, 5, 5, 3, 1);

        // Memo
        Label lbMemo = new Label("Memo");
        gridPane.add(lbMemo, 0, 6, 1, 1);
        TextArea taMemo = new TextArea();
        gridPane.add(taMemo, 0, 7, 8, 1);

        // Add constraints to the grid pane for resizability
        RowConstraints row = new RowConstraints();
        row.setVgrow(Priority.ALWAYS);
        for (int i = 0; i < 8; i++)
        {
            gridPane.getRowConstraints().add(row);
        }

        ColumnConstraints column = new ColumnConstraints();
        column.setHgrow(Priority.ALWAYS);
        for (int i = 0; i < 8; i++)
        {
            gridPane.getColumnConstraints().add(column);
        }

        // Set up the left side of the screen
        VBox vBox = new VBox();
        vBox.setSpacing(12);
        vBox.setPadding(new Insets(10));
        ImageView imageView = new ImageView(new Image(new FileInputStream("default_img.jpg")));
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);

        Button btUpdatePhoto = new Button("Update Photo");
        btUpdatePhoto.setMinWidth(imageView.getFitWidth());
        btUpdatePhoto.setOnAction(e ->
        {
            uploadContactPhoto(stage, imageView);
        });

        Button btSave = new Button("Save");
        btSave.setMinWidth(imageView.getFitWidth());
        btSave.setOnAction(e ->
        {
            saveContact(stage);
        });

        Button btCancel = new Button("Cancel");
        btCancel.setMinWidth(imageView.getFitWidth());
        btCancel.setOnAction(e ->
        {
            if (recentlySavedFile.toString() != "")
            {
                try (FileInputStream input = new FileInputStream(recentlySavedFile.toString());
                     ObjectInputStream objectInput = new ObjectInputStream(input))
                {
                    contact = (Contact) objectInput.readObject();
                }
                catch (IOException | ClassNotFoundException ex)
                {
                    System.out.println(ex);
                }
            }
            else
            {
                contact = new Contact();
            }

            try
            {
                imageView.setImage(new Image(new FileInputStream(contact.getContactPhoto())));
            }
            catch (FileNotFoundException ex)
            {
                System.out.println(ex);
            }
        });

        vBox.getChildren().addAll(imageView, btUpdatePhoto, btSave, btCancel);

        // Set up the menu bar
        MenuBar menu = new MenuBar();
        Menu menuFile = new Menu("File");
        MenuItem menuItemNew = new MenuItem("New");
        menuItemNew.setOnAction(e ->
        {
            System.out.println(contact.firstName());
            contact = new Contact();
            System.out.println(contact.firstName());
            Bindings.bindBidirectional(tfFirstName.textProperty(), contact.firstName());
            try
            {
                imageView.setImage(new Image(new FileInputStream(contact.getContactPhoto())));
            }
            catch (FileNotFoundException ex)
            {
                System.out.println(ex);
            }
        });
        MenuItem menuItemLoad = new MenuItem("Load");
        menuItemLoad.setOnAction(e ->
        {
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter vceExtension = new FileChooser.ExtensionFilter("DAT", "*.dat");
            fileChooser.setTitle("Save Contact Name");
            fileChooser.setInitialDirectory(new File(".")); // DO THIS……
            fileChooser.getExtensionFilters().addAll(vceExtension);
            fileChooser.setSelectedExtensionFilter(vceExtension);
            File loadFrom = fileChooser.showOpenDialog(stage);

            if (loadFrom != null)
            {
                recentlySavedFile = new StringBuilder(loadFrom.toString());

                try (FileInputStream input = new FileInputStream(recentlySavedFile.toString());
                     ObjectInputStream objectInput = new ObjectInputStream(input))
                {
                    contact = (Contact) objectInput.readObject();
                    imageView.setImage(new Image(new FileInputStream(contact.getContactPhoto())));
                }
                catch (IOException | ClassNotFoundException ex)
                {
                    System.out.println(ex);
                }
            }
        });
        menuFile.getItems().addAll(menuItemNew, menuItemLoad);
        Menu menuEdit = new Menu("Edit");
        Menu menuHelp = new Menu("Help");
        MenuItem menuItemAbout = new MenuItem("About");
        menuItemAbout.setOnAction(e ->
        {
            Dialog<String> dialog = new Dialog<String>();
            dialog.setTitle("About");
            ButtonType btType = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
            dialog.setContentText("This address book will show one contact at a time.\n\n" +
                    "Each contact will have a home and work address, which only one can be displayed at a time.\n\n" +
                    "In addition, you can save the contact to a .dat file any time, and upon pressing cancel, if a " +
                    "file was previously loaded, it will reload that file. Otherwise, all text fields will be turned blank.");
            dialog.getDialogPane().getButtonTypes().add(btType);
            dialog.getDialogPane().setMaxSize(300, 300);
            dialog.showAndWait();
        });
        menuHelp.getItems().addAll(menuItemAbout);
        menu.getMenus().addAll(menuFile, menuEdit, menuHelp);

        // Set the border pane up
        pane.setTop(menu);
        pane.setLeft(vBox);
        pane.setCenter(gridPane);

        Scene scene = new Scene(pane, 700, 400);
        stage.setTitle("Address Book");
        stage.setScene(scene);
        stage.setMinHeight(scene.getHeight());
        stage.setMinWidth(scene.getWidth());
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    private static void uploadContactPhoto(Stage stage, ImageView imageView)
    {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter pngExtension = new FileChooser.ExtensionFilter("PNG", "*.png");
        FileChooser.ExtensionFilter jpgExtension = new FileChooser.ExtensionFilter("JPG", "*.jpg");
        fileChooser.setTitle("Open Contact Photo");
        fileChooser.setInitialDirectory(new File(".")); // DO THIS……
        fileChooser.getExtensionFilters().addAll(pngExtension, jpgExtension);
        fileChooser.setSelectedExtensionFilter(pngExtension);
        File image = fileChooser.showOpenDialog(stage);

        if (image != null)
        {
            contact.setContactPhoto(image.toString());
            System.out.println(contact.getContactPhoto());

            try
            {
                imageView.setImage(new Image(new FileInputStream(contact.getContactPhoto())));
            }
            catch (FileNotFoundException ex)
            {
                System.out.println("Couldn't find contact photo");
            }
        }
    }

    private static void saveContact(Stage stage)
    {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter vceExtension = new FileChooser.ExtensionFilter("DAT", "*.dat");
        fileChooser.setTitle("Save Contact Name");
        fileChooser.setInitialDirectory(new File("."));
        fileChooser.getExtensionFilters().addAll(vceExtension);
        fileChooser.setSelectedExtensionFilter(vceExtension);
        File saveTo = fileChooser.showSaveDialog(stage);

        if (saveTo != null)
        {
            recentlySavedFile = new StringBuilder(saveTo.toString());
            try (FileOutputStream output = new FileOutputStream(recentlySavedFile.toString());
                 ObjectOutputStream objectOutput = new ObjectOutputStream(output))
            {
                objectOutput.writeObject(contact);
            }
            catch (IOException ex)
            {
                System.out.println(ex);
            }
        }
    }
}

class Contact implements Serializable
{
    private Address workAddress;
    private Address homeAddress;

    private String contactPhoto;

    private transient StringProperty firstName;

    public Contact()
    {
        workAddress = new Address();
        homeAddress = new Address();
        contactPhoto = "default_img.jpg";
        firstName = new SimpleStringProperty("");
    }

    private void writeObject(ObjectOutputStream s) throws IOException
    {
        s.defaultWriteObject();
        s.writeUTF(firstName.getValueSafe());
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException
    {
        firstName.set(s.readUTF());
    }

    public String getContactPhoto()
    {
        return contactPhoto;
    }

    public void setContactPhoto(String contactPhoto)
    {
        this.contactPhoto = contactPhoto;
    }

    public StringProperty firstName()
    {
        return firstName;
    }
}

class Address implements Serializable
{
    private String address;
    private String city;
    private String state;
    private String zip;

    public Address()
    {
        address = "";
        city = "";
        state = "";
        zip = "";
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
}