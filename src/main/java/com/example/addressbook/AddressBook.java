package com.example.addressbook;

import javafx.application.Application;
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
        tfFirstName.textProperty().addListener(_ ->
                contact.setFirstName(tfFirstName.getText()));

        // Last name
        Label lbLastName = new Label("Last Name");
        gridPane.add(lbLastName, 4, 0, 1, 1);
        TextField tfLastName = new TextField();
        gridPane.add(tfLastName, 5, 0, 3, 1);
        tfLastName.textProperty().addListener(_ ->
                contact.setLastName(tfLastName.getText()));

        // Spouse name
        Label lbSpouseName = new Label("Spouse");
        gridPane.add(lbSpouseName, 0, 1, 1, 1);
        TextField tfSpouseName = new TextField();
        gridPane.add(tfSpouseName, 1, 1, 3, 1);
        tfSpouseName.textProperty().addListener(_ ->
                contact.setSpouse(tfSpouseName.getText()));

        // Gender
        Label lbGender = new Label("Gender");
        gridPane.add(lbGender, 4, 1, 1, 1);
        RadioButton rbMale = new RadioButton("Male");
        rbMale.setOnAction(_ ->
                contact.setGender(rbMale.isSelected()));
        gridPane.add(rbMale, 5, 1, 1, 1);
        RadioButton rbFemale = new RadioButton("Female");
        gridPane.add(rbFemale, 6, 1, 1, 1);
        rbFemale.setOnAction(_ ->
                contact.setGender(rbMale.isSelected()));

        ToggleGroup group = new ToggleGroup();
        rbMale.setToggleGroup(group);
        rbFemale.setToggleGroup(group);
        rbFemale.setSelected(true);

        // Phone types
        String[] phoneTypes = {"Mobile Phone", "Work Phone", "Home Phone"};
        ComboBox cbPhoneType1 = new ComboBox(FXCollections.observableArrayList(phoneTypes));
        cbPhoneType1.getSelectionModel().selectFirst();
        gridPane.add(cbPhoneType1, 0, 2, 2, 1);
        TextField tfPhoneType1 = new TextField();
        gridPane.add(tfPhoneType1, 2, 2, 2, 1);
        cbPhoneType1.setOnAction(_ ->
                contact.setPhoneType1(cbPhoneType1.getSelectionModel().getSelectedIndex()));
        tfPhoneType1.textProperty().addListener(_ ->
                contact.setPhone1(tfPhoneType1.getText()));

        ComboBox cbPhoneType2 = new ComboBox(FXCollections.observableArrayList(phoneTypes));
        cbPhoneType2.getSelectionModel().selectFirst();
        gridPane.add(cbPhoneType2, 4, 2, 2, 1);
        TextField tfPhoneType2 = new TextField();
        gridPane.add(tfPhoneType2, 6, 2, 2, 1);
        cbPhoneType2.setOnAction(_ ->
                contact.setPhoneType2(cbPhoneType2.getSelectionModel().getSelectedIndex()));
        tfPhoneType2.textProperty().addListener(_ ->
                contact.setPhone2(tfPhoneType2.getText()));

        // Address
        Label lbAddressType = new Label("Address Type");
        gridPane.add(lbAddressType, 0, 3, 2, 1);
        String[] addressTypes = {"Home", "Work"};
        ComboBox cbAddressType = new ComboBox(FXCollections.observableArrayList(addressTypes));
        cbAddressType.getSelectionModel().selectFirst();
        gridPane.add(cbAddressType, 2, 3, 2, 1);

        Label lbAddress = new Label("Address");
        gridPane.add(lbAddress, 0, 4, 1, 1);
        TextField tfAddress = new TextField();
        gridPane.add(tfAddress, 1, 4, 3, 1);
        tfAddress.textProperty().addListener(_ ->
        {
            if (cbAddressType.getSelectionModel().getSelectedIndex() == 0)
            {
                contact.getHomeAddress().setAddress(tfAddress.getText());
            }
            else
            {
                contact.getWorkAddress().setAddress(tfAddress.getText());
            }
        });

        Label lbCity = new Label("City");
        gridPane.add(lbCity, 4, 4, 1, 1);
        TextField tfCity = new TextField();
        gridPane.add(tfCity, 5, 4, 3, 1);
        tfCity.textProperty().addListener(_ ->
        {
            if (cbAddressType.getSelectionModel().getSelectedIndex() == 0)
            {
                contact.getHomeAddress().setCity(tfCity.getText());
            }
            else
            {
                contact.getWorkAddress().setCity(tfCity.getText());
            }
        });

        Label lbState = new Label("State");
        gridPane.add(lbState, 0, 5, 1, 1);
        TextField tfState = new TextField();
        gridPane.add(tfState, 1, 5, 3, 1);
        tfState.textProperty().addListener(_ ->
        {
            if (cbAddressType.getSelectionModel().getSelectedIndex() == 0)
            {
                contact.getHomeAddress().setState(tfState.getText());
            }
            else
            {
                contact.getWorkAddress().setState(tfState.getText());
            }
        });

        Label lbZip = new Label("ZIP Code");
        gridPane.add(lbZip, 4, 5, 1, 1);
        TextField tfZip = new TextField();
        gridPane.add(tfZip, 5, 5, 3, 1);
        tfZip.textProperty().addListener(_ ->
        {
            if (cbAddressType.getSelectionModel().getSelectedIndex() == 0)
            {
                contact.getHomeAddress().setZip(tfZip.getText());
            }
            else
            {
                contact.getWorkAddress().setZip(tfZip.getText());
            }
        });

        cbAddressType.setOnAction(_ ->
        {
            if (cbAddressType.getSelectionModel().getSelectedIndex() == 0)
            {
                tfAddress.setText(contact.getHomeAddress().getAddress());
                tfCity.setText(contact.getHomeAddress().getCity());
                tfState.setText(contact.getHomeAddress().getState());
                tfZip.setText(contact.getHomeAddress().getZip());
            }
            else
            {
                tfAddress.setText(contact.getWorkAddress().getAddress());
                tfCity.setText(contact.getWorkAddress().getCity());
                tfState.setText(contact.getWorkAddress().getState());
                tfZip.setText(contact.getWorkAddress().getZip());
            }

            contact.setAddressSelected(cbAddressType.getSelectionModel().getSelectedIndex());
        });

        // Memo
        Label lbMemo = new Label("Memo");
        gridPane.add(lbMemo, 0, 6, 1, 1);
        TextArea taMemo = new TextArea();
        gridPane.add(taMemo, 0, 7, 8, 1);
        taMemo.textProperty().addListener(_ ->
                contact.setMemo(taMemo.getText()));

        // Add constraints to the grid pane for resizing
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
        btUpdatePhoto.setOnAction(_ ->
                uploadContactPhoto(stage, imageView));

        Button btSave = new Button("Save");
        btSave.setMinWidth(imageView.getFitWidth());
        btSave.setOnAction(_ ->
                saveContact(stage));

        Button btCancel = new Button("Cancel");
        btCancel.setMinWidth(imageView.getFitWidth());
        btCancel.setOnAction(_ ->
        {
            if (!recentlySavedFile.toString().isEmpty())
            {
                try (FileInputStream input = new FileInputStream(recentlySavedFile.toString());
                     ObjectInputStream objectInput = new ObjectInputStream(input))
                {
                    contact = (Contact) objectInput.readObject();
                    updateFields(tfFirstName, tfLastName, tfSpouseName, rbMale, rbFemale, cbPhoneType1, tfPhoneType1,
                            cbPhoneType2, tfPhoneType2, cbAddressType, tfAddress, tfCity, tfState, tfZip, taMemo);
                }
                catch (IOException | ClassNotFoundException ex)
                {
                    System.out.println(ex);
                    showDialog("Issue Reading File", "There was an issue reading the selected file. Try again.");
                }
            }
            else
            {
                contact = new Contact();
                updateFields(tfFirstName, tfLastName, tfSpouseName, rbMale, rbFemale, cbPhoneType1, tfPhoneType1,
                        cbPhoneType2, tfPhoneType2, cbAddressType, tfAddress, tfCity, tfState, tfZip, taMemo);
            }

            try
            {
                imageView.setImage(new Image(new FileInputStream(contact.getContactPhoto())));
            }
            catch (FileNotFoundException ex)
            {
                System.out.println(ex);
                showDialog("Image Not Found", "Could not find contact photo.");
            }
        });

        vBox.getChildren().addAll(imageView, btUpdatePhoto, btSave, btCancel);

        // Set up the menu bar
        MenuBar menu = new MenuBar();
        Menu menuFile = new Menu("File");
        MenuItem menuItemNew = new MenuItem("New");
        menuItemNew.setOnAction(_ ->
        {
            recentlySavedFile = new StringBuilder();
            contact = new Contact();
            updateFields(tfFirstName, tfLastName, tfSpouseName, rbMale, rbFemale, cbPhoneType1, tfPhoneType1, cbPhoneType2,
                    tfPhoneType2, cbAddressType, tfAddress, tfCity, tfState, tfZip, taMemo);
            try
            {
                imageView.setImage(new Image(new FileInputStream(contact.getContactPhoto())));
            }
            catch (FileNotFoundException ex)
            {
                System.out.println(ex);
                showDialog("Image Not Found", "Could not find contact photo.");
            }
        });
        MenuItem menuItemLoad = new MenuItem("Load");
        menuItemLoad.setOnAction(_ ->
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
                    updateFields(tfFirstName, tfLastName, tfSpouseName, rbMale, rbFemale, cbPhoneType1, tfPhoneType1,
                            cbPhoneType2, tfPhoneType2, cbAddressType, tfAddress, tfCity, tfState, tfZip, taMemo);

                    try
                    {
                        imageView.setImage(new Image(new FileInputStream(contact.getContactPhoto())));
                    }
                    catch (FileNotFoundException ex)
                    {
                        System.out.println(ex);
                        showDialog("Image Not Found", "Could not find contact photo.");
                    }
                }
                catch(ClassNotFoundException | InvalidClassException ex)
                {
                    System.out.println(ex);
                    showDialog("Out Of Date File", "File you're trying to load is out of date.");
                }
                catch (IOException ex)
                {
                    System.out.println(ex);
                    showDialog("Issue Reading File", "There was an issue reading the selected file. Try again.");
                }
            }
        });
        menuFile.getItems().addAll(menuItemNew, menuItemLoad);
        Menu menuEdit = new Menu("Edit");
        Menu menuHelp = new Menu("Help");
        MenuItem menuItemAbout = new MenuItem("About");
        menuItemAbout.setOnAction(_ ->
        {
            showDialog("About", """
                    This address book will show one contact at a time.
                    
                    Each contact will have a home and work address, which only one can be displayed at a time.
                    
                    In addition, you can save the contact to a .dat file any time, and upon pressing cancel, if a \
                    file was previously loaded, it will reload that file. Otherwise, all text fields will be turned blank.""");
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

    private void updateFields(TextField tfFirstName, TextField tfLastName, TextField tfSpouseName,
                              RadioButton rbMale, RadioButton rbFemale, ComboBox cbPhoneType1,
                              TextField tfPhoneType1, ComboBox cbPhoneType2, TextField tfPhoneType2,
                              ComboBox cbAddressType, TextField tfAddress, TextField tfCity, TextField tfState,
                              TextField tfZip, TextArea taMemo)
    {
        tfFirstName.setText(contact.getFirstName());
        tfLastName.setText(contact.getLastName());
        tfSpouseName.setText(contact.getSpouse());
        rbMale.setSelected(contact.getGender());
        rbFemale.setSelected(!contact.getGender());
        tfPhoneType1.setText(contact.getPhone1());
        tfPhoneType2.setText(contact.getPhone2());
        cbPhoneType1.getSelectionModel().select(contact.getPhoneType1());
        cbPhoneType2.getSelectionModel().select(contact.getPhoneType2());
        cbAddressType.getSelectionModel().select(contact.getAddressSelected());
        taMemo.setText(contact.getMemo());
        if (cbAddressType.getSelectionModel().getSelectedIndex() == 0)
        {
            tfAddress.setText(contact.getHomeAddress().getAddress());
            tfCity.setText(contact.getHomeAddress().getCity());
            tfState.setText(contact.getHomeAddress().getState());
            tfZip.setText(contact.getHomeAddress().getZip());
        }
        else
        {
            tfAddress.setText(contact.getWorkAddress().getAddress());
            tfCity.setText(contact.getWorkAddress().getCity());
            tfState.setText(contact.getWorkAddress().getState());
            tfZip.setText(contact.getWorkAddress().getZip());
        }
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
        fileChooser.setInitialDirectory(new File(".")); // DO THIS……
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
                showDialog("Issue Saving File", "There was an issue saving the selected file. Try again.");
            }
        }
    }

    private static void showDialog(String title, String content)
    {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle(title);
        ButtonType btType = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        dialog.setContentText(content);
        dialog.getDialogPane().getButtonTypes().add(btType);
        dialog.getDialogPane().setMaxSize(300, 300);
        dialog.showAndWait();
    }
}

class Contact implements Serializable
{
    private String firstName;
    private String lastName;
    private String spouse;
    private boolean gender;
    private String memo;

    private int addressSelected;
    private Address workAddress;
    private Address homeAddress;

    private int phoneType1;
    private int phoneType2;
    private String phone1;
    private String phone2;

    private String contactPhoto;

    public Contact()
    {
        firstName = "";
        lastName = "";
        spouse = "";
        gender = false;
        memo = "";
        addressSelected = 0;
        workAddress = new Address();
        homeAddress = new Address();
        phoneType1 = 0;
        phoneType2 = 0;
        phone1 = "";
        phone2 = "";
        contactPhoto = "default_img.jpg";
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSpouse() {
        return spouse;
    }

    public void setSpouse(String spouse) {
        this.spouse = spouse;
    }

    public boolean getGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public Address getWorkAddress() {
        return workAddress;
    }

    public Address getHomeAddress() {
        return homeAddress;
    }

    public int getPhoneType1() {
        return phoneType1;
    }

    public void setPhoneType1(int phoneType1) {
        this.phoneType1 = phoneType1;
    }

    public int getPhoneType2() {
        return phoneType2;
    }

    public void setPhoneType2(int phoneType2) {
        this.phoneType2 = phoneType2;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getContactPhoto() {
        return contactPhoto;
    }

    public void setContactPhoto(String contactPhoto) {
        this.contactPhoto = contactPhoto;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public int getAddressSelected() {
        return addressSelected;
    }

    public void setAddressSelected(int addressSelected) {
        this.addressSelected = addressSelected;
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