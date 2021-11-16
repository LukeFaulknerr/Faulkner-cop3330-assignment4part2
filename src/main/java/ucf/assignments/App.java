/*
 *  UCF COP3330 Fall 2021 Assignment 4 Solution
 *  Copyright 2021 Luke Faulkner
 */

package ucf.assignments;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.*;
import java.util.ArrayList;


public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // General Variables/ Objects
        ObservableList<TDList> listDisplay = FXCollections.observableArrayList();

        // Main Scene
        BorderPane root = new BorderPane();
        Scene listView = new Scene(root, 1000, 1000, Color.WHITE);

        // Create menu bar with controls
        MenuBar menu = new MenuBar();
        menu.prefWidthProperty().bind(primaryStage.widthProperty());
        root.setTop(menu);

        // Menu with list options
        Menu listMenu = new Menu("List");
        MenuItem newList = new MenuItem("New List");
        MenuItem clearList = new MenuItem("Clear List");
        MenuItem editTitle = new MenuItem("Edit Title");
        listMenu.getItems().addAll(newList, clearList, editTitle);

        // Menu for editing items
        Menu itemMenu = new Menu("Items");
        MenuItem newItem = new MenuItem("New Item");
        MenuItem deleteItem = new MenuItem("Remove Item");
        MenuItem editDesc = new MenuItem("Edit Description");
        MenuItem editDate = new MenuItem("Edit Date");
        MenuItem editComplete = new MenuItem("Mark Complete");
        itemMenu.getItems().addAll(newItem, deleteItem, new SeparatorMenuItem(), editDesc, editDate, editComplete);

        // Menu for displaying
        Menu displayMenu = new Menu("Display Options");
        MenuItem displayLists = new MenuItem("Display Lists");
        MenuItem displayAll = new MenuItem("Display All Items");
        MenuItem displayComplete = new MenuItem("Display Complete");
        MenuItem displayIncomplete = new MenuItem("Display Incomplete");
        displayMenu.getItems().addAll(displayLists, new SeparatorMenuItem(), displayAll, displayComplete, displayIncomplete);

        // Menu for saving/loading
        Menu saveLoadMenu = new Menu("File");
        MenuItem saveFile = new MenuItem("Save List");
        MenuItem loadFile = new MenuItem("Load List");
        saveLoadMenu.getItems().addAll(saveFile, loadFile);

        // Menu for Help
        Menu helpMenu = new Menu("Help");
        MenuItem helpPage = new MenuItem("Help Page");
        helpMenu.getItems().addAll(helpPage);

        // Add Menus to top bar
        menu.getMenus().addAll(listMenu, itemMenu, displayMenu, saveLoadMenu, helpMenu);

        // Add a new item to a list
        newItem.setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent event) {
                // Create fields to enter information with
                // List Name Label
                Label listName = new Label("List Name: ");

                // List options
                ComboBox availableLists = new ComboBox(listDisplay);
                availableLists.setCellFactory(param -> new ListCell<TDList>() {
                    @Override
                    protected void updateItem(TDList item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty || item == null || item.getTitle() == null) {
                            setText(null);
                        } else {
                            setText(item.getTitle());
                        }
                    }
                });

                // Description Label and input
                Label descriptionLabel = new Label("Description: ");
                TextField descriptionTextField = new TextField();

                // Due date Label and input
                Label dueDateLabel = new Label("Due Date (YYYY-MM-DD): ");
                TextField dueDateTextField = new TextField();

                // Button to submit
                Button submitNewItem = new Button("Submit");

                // Add button features
                submitNewItem.setOnAction(event12 -> {
                    // Add desired item to desired list
                    TDItemEdit.addTDItem(new TDItem(descriptionTextField.getText(), dueDateTextField.getText(), false), ((TDList) availableLists.getValue()));
                });

                // Create scene and display
                VBox newItemRoot = new VBox(menu);
                Scene makeNewItem = new Scene(newItemRoot, 1000, 1000, Color.WHITE);
                newItemRoot.getChildren().addAll(listName, availableLists, descriptionLabel, descriptionTextField, dueDateLabel, dueDateTextField, submitNewItem);
                primaryStage.setScene(makeNewItem);
            }
        });

        // Remove an item from a list
        deleteItem.setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent event) {
                // Create fields to enter information with
                // List Name Label
                Label listName = new Label("List Name: ");

                // List options
                ComboBox availableLists = new ComboBox(listDisplay);
                availableLists.setCellFactory(param -> new ListCell<TDList>() {
                    @Override
                    protected void updateItem(TDList item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty || item == null || item.getTitle() == null) {
                            setText(null);
                        } else {
                            setText(item.getTitle());
                        }
                    }
                });

                // Button to submit list choice
                Button submitListButton = new Button("Submit");

                // Create new page once submitted
                submitListButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        // Item name box and label
                        Label itemName = new Label("Item Name: ");

                        // Item Options
                        ObservableList<TDItem> itemDisplay = FXCollections.observableArrayList();
                        for (int i = 0; i < ((TDList) availableLists.getValue()).getTDItems().size(); i++) {
                            itemDisplay.add(((TDList) availableLists.getValue()).getTDItems().get(i));
                        }
                        ComboBox availableItems = new ComboBox(itemDisplay);
                        availableItems.setCellFactory(param -> new ListCell<TDItem>() {
                            @Override
                            protected void updateItem(TDItem item, boolean empty) {
                                super.updateItem(item, empty);

                                if (empty || item == null || item.getDesc() == null || item.getDate() == null) {
                                    setText(null);
                                } else {
                                    setText(item.getDesc() + "\t\t\t\t\t" + item.getDate() + "\t\t\t\t\t" + item.getCompleteAsString());
                                }
                            }
                        });

                        // Button to submit item choice
                        Button submitItemButton = new Button("Submit");

                        // Submits choice for action
                        submitItemButton.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                // Delete desired item
                                TDItemEdit.removeTDItem(((TDList) availableLists.getValue()), ((TDItem) availableItems.getValue()));
                            }
                        });

                        // Item delete window display
                        VBox itemSelectRoot = new VBox(menu);
                        Scene itemSelectScene = new Scene(itemSelectRoot, 1000, 1000, Color.WHITE);
                        itemSelectRoot.getChildren().addAll(itemName, availableItems, submitItemButton);
                        primaryStage.setScene(itemSelectScene);

                    }
                });

                // List selection window display
                VBox listSelectRoot = new VBox(menu);
                Scene listSelectScene = new Scene(listSelectRoot, 1000, 1000, Color.WHITE);
                listSelectRoot.getChildren().addAll(listName, availableLists, submitListButton);
                primaryStage.setScene(listSelectScene);
            }
        });

        // Clear a List
        clearList.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // List select
                // List Name Label
                Label listName = new Label("List Name: ");

                // List options
                ComboBox availableLists = new ComboBox(listDisplay);
                availableLists.setCellFactory(param -> new ListCell<TDList>() {
                    @Override
                    protected void updateItem(TDList item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty || item == null || item.getTitle() == null) {
                            setText(null);
                        } else {
                            setText(item.getTitle());
                        }
                    }
                });

                // Button to Submit Desired List
                Button submitClearListButton = new Button("Submit");
                submitClearListButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        TDListEdit.clearTDList(((TDList)availableLists.getValue()));
                    }
                });

                // Clear list window display
                VBox clearListRoot = new VBox(menu);
                Scene clearListScene = new Scene(clearListRoot, 1000, 1000, Color.WHITE);
                clearListRoot.getChildren().addAll(listName, availableLists, submitClearListButton);
                primaryStage.setScene(clearListScene);
            }
        });

        // Edit description of an item in a list
        editDesc.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Create fields to enter information with
                // List Name Label
                Label listName = new Label("List Name: ");

                // List options
                ComboBox availableLists = new ComboBox(listDisplay);
                availableLists.setCellFactory(param -> new ListCell<TDList>() {
                    @Override
                    protected void updateItem(TDList item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty || item == null || item.getTitle() == null) {
                            setText(null);
                        } else {
                            setText(item.getTitle());
                        }
                    }
                });

                // Button to submit list choice
                Button submitListButton = new Button("Submit");

                // Create new page once submitted
                submitListButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        // Item name box and label
                        Label itemName = new Label("Item Name: ");

                        // Item Options
                        ObservableList<TDItem> itemDisplay = FXCollections.observableArrayList();
                        for (int i = 0; i < ((TDList)availableLists.getValue()).getTDItems().size(); i++){
                            itemDisplay.add(((TDList)availableLists.getValue()).getTDItems().get(i));
                        }
                        ComboBox availableItems = new ComboBox(itemDisplay);
                        availableItems.setCellFactory(param -> new ListCell<TDItem>() {
                            @Override
                            protected void updateItem(TDItem item, boolean empty) {
                                super.updateItem(item, empty);

                                if (empty || item == null || item.getDesc() == null || item.getDate() == null) {
                                    setText(null);
                                } else {
                                    setText(item.getDesc() + "\t\t\t\t\t" + item.getDate() + "\t\t\t\t\t" + item.getCompleteAsString());
                                }
                            }
                        });

                        // Description Change Box
                        Label editDescLabel = new Label("New Description");
                        TextField editDescTextField = new TextField();

                        // Button to submit item choice
                        Button submitItemButton = new Button("Submit");

                        // Submits choice for action
                        submitItemButton.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                // Edit Description desired item
                                TDItemEdit.editDescription(((TDItem)availableItems.getValue()), editDescTextField.getText());
                            }
                        });

                        // Item delete window display
                        VBox itemSelectRoot = new VBox(menu);
                        Scene itemSelectScene = new Scene(itemSelectRoot, 1000, 1000, Color.WHITE);
                        itemSelectRoot.getChildren().addAll(itemName, availableItems, editDescLabel, editDescTextField, submitItemButton);
                        primaryStage.setScene(itemSelectScene);

                    }
                });

                // List selection window display
                VBox listSelectRoot = new VBox(menu);
                Scene listSelectScene = new Scene(listSelectRoot, 1000, 1000, Color.WHITE);
                listSelectRoot.getChildren().addAll(listName, availableLists, submitListButton);
                primaryStage.setScene(listSelectScene);
            }
        });

        // Edit due date of the item
        editDate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Create fields to enter information with
                // List Name Label
                Label listName = new Label("List Name: ");

                // List options
                ComboBox availableLists = new ComboBox(listDisplay);
                availableLists.setCellFactory(param -> new ListCell<TDList>() {
                    @Override
                    protected void updateItem(TDList item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty || item == null || item.getTitle() == null) {
                            setText(null);
                        } else {
                            setText(item.getTitle());
                        }
                    }
                });

                // Button to submit list choice
                Button submitListButton = new Button("Submit");

                // Create new page once submitted
                submitListButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        // Item name box and label
                        Label itemName = new Label("Item Name: ");

                        // Item Options
                        ObservableList<TDItem> itemDisplay = FXCollections.observableArrayList();
                        for (int i = 0; i < ((TDList)availableLists.getValue()).getTDItems().size(); i++){
                            itemDisplay.add(((TDList)availableLists.getValue()).getTDItems().get(i));
                        }
                        ComboBox availableItems = new ComboBox(itemDisplay);
                        availableItems.setCellFactory(param -> new ListCell<TDItem>() {
                            @Override
                            protected void updateItem(TDItem item, boolean empty) {
                                super.updateItem(item, empty);

                                if (empty || item == null || item.getDesc() == null || item.getDate() == null) {
                                    setText(null);
                                } else {
                                    setText(item.getDesc() + "\t\t\t\t\t" + item.getDate() + "\t\t\t\t\t" + item.getCompleteAsString());
                                }
                            }
                        });

                        // Description Change Box
                        Label editDateLabel = new Label("New Due Date (YYYY-MM-DD): ");
                        TextField editDateTextField = new TextField();

                        // Button to submit item choice
                        Button submitItemButton = new Button("Submit");

                        // Submits choice for action
                        submitItemButton.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                // Edit Date of desired item
                                ((TDItem)(availableItems.getValue())).setDate(editDateTextField.getText(0, 4), editDateTextField.getText(5, 7), editDateTextField.getText(8, 10));
                            }
                        });

                        // Item delete window display
                        VBox itemSelectRoot = new VBox(menu);
                        Scene itemSelectScene = new Scene(itemSelectRoot, 1000, 1000, Color.WHITE);
                        itemSelectRoot.getChildren().addAll(itemName, availableItems, editDateLabel, editDateTextField, submitItemButton);
                        primaryStage.setScene(itemSelectScene);

                    }
                });

                // List selection window display
                VBox listSelectRoot = new VBox(menu);
                Scene listSelectScene = new Scene(listSelectRoot, 1000, 1000, Color.WHITE);
                listSelectRoot.getChildren().addAll(listName, availableLists, submitListButton);
                primaryStage.setScene(listSelectScene);
            }
        });

        // Mark an item complete / incomplete
        editComplete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Create fields to enter information with
                // List Name Label
                Label listName = new Label("List Name: ");

                // List options
                ComboBox availableLists = new ComboBox(listDisplay);
                availableLists.setCellFactory(param -> new ListCell<TDList>() {
                    @Override
                    protected void updateItem(TDList item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty || item == null || item.getTitle() == null) {
                            setText(null);
                        } else {
                            setText(item.getTitle());
                        }
                    }
                });

                // Button to submit list choice
                Button submitListButton = new Button("Submit");

                // Create new page once submitted
                submitListButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        // Item name box and label
                        Label itemName = new Label("Item Name: ");

                        // Item Options
                        ObservableList<TDItem> itemDisplay = FXCollections.observableArrayList();
                        for (int i = 0; i < ((TDList)availableLists.getValue()).getTDItems().size(); i++){
                            itemDisplay.add(((TDList)availableLists.getValue()).getTDItems().get(i));
                        }
                        ComboBox availableItems = new ComboBox(itemDisplay);
                        availableItems.setCellFactory(param -> new ListCell<TDItem>() {
                            @Override
                            protected void updateItem(TDItem item, boolean empty) {
                                super.updateItem(item, empty);

                                if (empty || item == null || item.getDesc() == null || item.getDate() == null) {
                                    setText(null);
                                } else {
                                    setText(item.getDesc() + "\t\t\t\t\t" + item.getDate() + "\t\t\t\t\t" + item.getCompleteAsString());
                                }
                            }
                        });

                        // Check box for whether someone wants to mark complete
                        Label completeCheckBoxLabel = new Label("Check if you want an item to be complete, leave blank if you want to mark an item incomplete");
                        CheckBox completeCheckBox = new CheckBox();

                        // Button to submit item choice
                        Button submitItemButton = new Button("Submit");

                        // Submits choice for action
                        submitItemButton.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                // If box is checked, mark complete
                                if (completeCheckBox.isSelected()) {
                                    ((TDItem)availableItems.getValue()).setComplete(true);
                                }
                                else
                                    ((TDItem)availableItems.getValue()).setComplete(false);
                            }
                        });

                        // Item delete window display
                        VBox itemSelectRoot = new VBox(menu);
                        Scene itemSelectScene = new Scene(itemSelectRoot, 1000, 1000, Color.WHITE);
                        itemSelectRoot.getChildren().addAll(itemName, availableItems, completeCheckBoxLabel, completeCheckBox, submitItemButton);
                        primaryStage.setScene(itemSelectScene);

                    }
                });

                // List selection window display
                VBox listSelectRoot = new VBox(menu);
                Scene listSelectScene = new Scene(listSelectRoot, 1000, 1000, Color.WHITE);
                listSelectRoot.getChildren().addAll(listName, availableLists, submitListButton);
                primaryStage.setScene(listSelectScene);
            }
        });

        // New List Creator
        newList.setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent event) {
                // Create new window to add new List
                VBox newListRoot = new VBox(menu);
                Scene makeNewList = new Scene(newListRoot, 1000, 1000, Color.WHITE);

                // Create Fields to enter information with
                Label listName = new Label("List Name: ");
                TextField listNameField = new TextField();
                Button createList = new Button("Create List");

                // When button is clicked, add list
                createList.setOnAction(event1 -> TDListEdit.addTDList(listDisplay, listNameField.getText()));
                newListRoot.getChildren().addAll(listName, listNameField, createList);
                primaryStage.setScene(makeNewList);
            }
        });

        // Display all Lists
        displayLists.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ListView<TDList> lists = new ListView<TDList>(listDisplay);

                lists.setCellFactory(param -> new ListCell<TDList>() {
                    @Override
                    protected void updateItem(TDList item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty || item == null || item.getTitle() == null) {
                            setText(null);
                        } else {
                            setText(item.getTitle());
                        }
                    }
                });
                VBox displayAllRoot = new VBox(menu);
                displayAllRoot.getChildren().addAll(lists);
                Scene displayAll = new Scene(displayAllRoot, 1000, 1000, Color.WHITE);
                primaryStage.setScene(displayAll);
                primaryStage.show();
            }
        });

        // Display all items of a list
        displayAll.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                VBox selectList = new VBox(menu);
                Scene selectListScene = new Scene(selectList, 1000, 1000, Color.WHITE);

                // List options
                ComboBox availableLists = new ComboBox(listDisplay);
                availableLists.setCellFactory(param -> new ListCell<TDList>() {
                    @Override
                    protected void updateItem(TDList item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty || item == null || item.getTitle() == null) {
                            setText(null);
                        } else {
                            setText(item.getTitle());
                        }
                    }
                });

                // Submit
                Button submitButton = new Button("Submit");

                // Operation for when button is selected
                submitButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        ObservableList<TDItem> itemDisplay = FXCollections.observableArrayList();
                        for (int i = 0; i < ((TDList)availableLists.getValue()).getTDItems().size(); i++){
                            itemDisplay.add(((TDList)availableLists.getValue()).getTDItems().get(i));
                        }
                        ListView<TDItem> lists = new ListView<TDItem>(itemDisplay);

                        lists.setCellFactory(param -> new ListCell<TDItem>() {
                            @Override
                            protected void updateItem(TDItem item, boolean empty) {
                                super.updateItem(item, empty);

                                if (empty || item == null || item.getDesc() == null || item.getDate() == null) {
                                    setText(null);
                                } else {
                                    setText(item.getDesc() + "\t\t\t\t\t" + item.getDate() + "\t\t\t\t\t" + item.getCompleteAsString());
                                }
                            }
                        });
                        VBox displayAllRoot = new VBox(menu);
                        displayAllRoot.getChildren().addAll(lists);
                        Scene displayAll = new Scene(displayAllRoot, 1000, 1000, Color.WHITE);
                        primaryStage.setScene(displayAll);
                        primaryStage.show();
                    }
                });

                // Display
                selectList.getChildren().addAll(availableLists, submitButton);
                primaryStage.setScene(selectListScene);
            }
        });

        // Display all incomplete items in a list
        displayIncomplete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                VBox selectList = new VBox(menu);
                Scene selectListScene = new Scene(selectList, 1000, 1000, Color.WHITE);

                // List options
                ComboBox availableLists = new ComboBox(listDisplay);
                availableLists.setCellFactory(param -> new ListCell<TDList>() {
                    @Override
                    protected void updateItem(TDList item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty || item == null || item.getTitle() == null) {
                            setText(null);
                        } else {
                            setText(item.getTitle());
                        }
                    }
                });

                // Submit
                Button submitButton = new Button("Submit");

                // Operation for when button is selected
                submitButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        ObservableList<TDItem> itemDisplay = FXCollections.observableArrayList();
                        for (int i = 0; i < ((TDList)availableLists.getValue()).getTDItems().size(); i++){
                            if (!(((TDList)availableLists.getValue()).getTDItems().get(i).getComplete())){
                                itemDisplay.add(((TDList)availableLists.getValue()).getTDItems().get(i));
                            }
                        }
                        ListView<TDItem> lists = new ListView<TDItem>(itemDisplay);

                        lists.setCellFactory(param -> new ListCell<TDItem>() {
                            @Override
                            protected void updateItem(TDItem item, boolean empty) {
                                super.updateItem(item, empty);

                                if (empty || item == null || item.getDesc() == null || item.getDate() == null) {
                                    setText(null);
                                } else {
                                    setText(item.getDesc() + "\t\t\t\t\t" + item.getDate() + "\t\t\t\t\t" + item.getCompleteAsString());
                                }
                            }
                        });
                        VBox displayAllRoot = new VBox(menu);
                        displayAllRoot.getChildren().addAll(lists);
                        Scene displayAll = new Scene(displayAllRoot, 1000, 1000, Color.WHITE);
                        primaryStage.setScene(displayAll);
                        primaryStage.show();
                    }
                });

                // Display
                selectList.getChildren().addAll(availableLists, submitButton);
                primaryStage.setScene(selectListScene);
            }
        });

        // Display all complete items in a list
        displayComplete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                VBox selectList = new VBox(menu);
                Scene selectListScene = new Scene(selectList, 1000, 1000, Color.WHITE);

                // List options
                ComboBox availableLists = new ComboBox(listDisplay);
                availableLists.setCellFactory(param -> new ListCell<TDList>() {
                    @Override
                    protected void updateItem(TDList item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty || item == null || item.getTitle() == null) {
                            setText(null);
                        } else {
                            setText(item.getTitle());
                        }
                    }
                });

                // Submit
                Button submitButton = new Button("Submit");

                // Operation for when button is selected
                submitButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        ObservableList<TDItem> itemDisplay = FXCollections.observableArrayList();
                        for (int i = 0; i < ((TDList)availableLists.getValue()).getTDItems().size(); i++){
                            if ((((TDList)availableLists.getValue()).getTDItems().get(i).getComplete())){
                                itemDisplay.add(((TDList)availableLists.getValue()).getTDItems().get(i));
                            }
                        }
                        ListView<TDItem> lists = new ListView<TDItem>(itemDisplay);

                        lists.setCellFactory(param -> new ListCell<TDItem>() {
                            @Override
                            protected void updateItem(TDItem item, boolean empty) {
                                super.updateItem(item, empty);

                                if (empty || item == null || item.getDesc() == null || item.getDate() == null) {
                                    setText(null);
                                } else {
                                    setText(item.getDesc() + "\t\t\t\t\t" + item.getDate() + "\t\t\t\t\t" + item.getCompleteAsString());
                                }
                            }
                        });
                        VBox displayAllRoot = new VBox(menu);
                        displayAllRoot.getChildren().addAll(lists);
                        Scene displayAll = new Scene(displayAllRoot, 1000, 1000, Color.WHITE);
                        primaryStage.setScene(displayAll);
                        primaryStage.show();
                    }
                });

                // Display
                selectList.getChildren().addAll(availableLists, submitButton);
                primaryStage.setScene(selectListScene);
            }
        });

        // Save List to external storage
        saveFile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Set window
                VBox saveFileRoot = new VBox(menu);
                Scene saveFileScene = new Scene(saveFileRoot, 1000, 1000, Color.WHITE);

                // List label
                Label listName = new Label("List Name: ");

                // List options
                ComboBox availableLists = new ComboBox(listDisplay);
                availableLists.setCellFactory(param -> new ListCell<TDList>() {
                    @Override
                    protected void updateItem(TDList item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty || item == null || item.getTitle() == null) {
                            setText(null);
                        } else {
                            setText(item.getTitle());
                        }
                    }
                });

                // Choose File Directory
                Label fileDirectoryLabel = new Label("Please enter the full file directory you wish to save your list to.");
                Button selectDirectory = new Button("Select Directory");
                selectDirectory.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        // Find a directory to save the object to
                        DirectoryChooser directoryChooser = new DirectoryChooser();
                        directoryChooser.setInitialDirectory(new File("."));
                        File file = directoryChooser.showDialog(saveFileScene.getWindow());
                        try {
                            // Create a CSV file to save to
                            // Variables for iteration
                            TDList saveItem = (TDList)availableLists.getValue();
                            String CSVSeperator = (",");

                            // Objects to iterate through the object data
                            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(saveItem.getTitle() + ".csv"), "UTF-8"));
                            StringBuffer readObject = new StringBuffer();

                            // Read in the title of the list
                            readObject.append(saveItem.getTitle().trim().length() <=0 ? "" : saveItem.getTitle());
                            readObject.append(CSVSeperator);

                            // Read in the data of each item in the list
                            for (TDItem t : saveItem.getTDItems()){
                                readObject.append(t.getDesc().trim().length() <= 0 ? "" : t.getDesc());
                                readObject.append(CSVSeperator);
                                readObject.append(t.getDate().trim().length() <= 0 ? "" : t.getDate());
                                readObject.append(CSVSeperator);
                                readObject.append(t.getComplete());
                                readObject.append(CSVSeperator);

                            }
                            // Write the data to the file and close it
                            bw.write(readObject.toString());
                            bw.flush();
                            bw.close();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                // Set scene with correct data
                saveFileRoot.getChildren().addAll(listName, availableLists, fileDirectoryLabel, selectDirectory);
                primaryStage.setScene(saveFileScene);
            }
        });

        // Load List from memory
        loadFile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Set window
                VBox loadFileRoot = new VBox(menu);
                Scene loadFileScene = new Scene(loadFileRoot, 1000, 1000, Color.WHITE);

                // Button that prompts a file select
                Button selectFile = new Button("Select File");

                // When button is clicked
                selectFile.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        // Find a file to load
                        FileChooser fileChooser = new FileChooser();
                        fileChooser.setInitialDirectory(new File("."));
                        File file = fileChooser.showOpenDialog(loadFileRoot.getScene().getWindow());

                        // Parse the file
                        try {
                            // Object to read in the file
                            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

                            // String that holds the entire .csv file
                            String row = br.readLine();

                            // Array that holds all values from the .csv file
                            String[] data = row.split(",");

                            // TDList object that will be added - first index is always the title of the list
                            TDList loadedList = new TDList(new ArrayList<TDItem>(), data[0]);

                            // Parse through the list and create each list item as necessary
                            for (int i = 1; i < data.length; i = i + 3){
                                loadedList.getTDItems().add(new TDItem(data[i], data[i + 1], TDListEdit.getCompleteAsBoolean(data[i + 2])));
                            }

                            // Add the TDList to stack of lists
                            listDisplay.addAll(loadedList);

                            // Close file reader
                            br.close();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

                // Display window
                loadFileRoot.getChildren().addAll(selectFile);
                primaryStage.setScene(loadFileScene);

            }
        });

        // Help
        helpPage.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                VBox helpRoot = new VBox(menu);
                Scene helpScene = new Scene(helpRoot, 1000, 1000, Color.WHITE);

                helpRoot.setSpacing(10);

                // Texts to display
                Text OverView = new Text("This program allows you to create and edit To-Do Lists");
                Text createListHelp = new Text('\u2022' + "In order to create a new list, click the List tab in the menu and click the 'New List' option. From there, enter the title of your list and click the submit button.");
                Text clearList = new Text('\u2022' + "In order to clear a list, click the list tab in the menu and select 'Clear List'. From there, choose the list you wish to clear and click the submit button");
                Text editTitle = new Text('\u2022' + "In order to edit the title of a list, click the list tab in the menu and select 'Edit Title.' From there, select the list you wish to change the title of and enter the new title into the text box and click submit.");
                Text newItem = new Text('\u2022' + "In order to add a new item to a list, click the item tab in the menu and select 'New Item.' From there, select the list you wish to add a new item to from the drop-box and enter the description and due date of the item, and click submit.");
                Text deleteItem = new Text('\u2022' + "In order to delete an item from a list, click the item tab and select 'Delete Item.' From there, select the list you wish to delete an item from, and click submit. After, click the item you wish to remove and click submit.");
                Text editDesc = new Text('\u2022' + "In order to edit the description of an item, click the item tab and select 'Edit Description.' From there, select the list you wish to edit an item description in, and click submit. Then, select the item you wish to change the description of, enter the new description, and click submit.");
                Text editDate = new Text('\u2022' + "In order to edit the due date of an item, click the item tab and select 'Edit Date.' From there, select the list you wish to edit an item due date in, and click submit. Then, select the item you wish to change the due date of, enter the new due date, and click submit.");
                Text editComplete = new Text('\u2022' + "In order to edit the completion of an item, click the item tab and select 'Mark Complete.' From there, select the list you wish to mark the completion of an item in, and click submit. Then, select the item you wish to change the completion of. Then, check the box if you wish to mark the item as complete, and leave it blank if you wish to mark it as incomplete. Then, click submit.");
                Text displayLists = new Text('\u2022' + "In order to display all lists, click the 'Display Options' tab and select 'Display Lists.'");
                Text displayAllItems = new Text('\u2022' + "In order to display all items in a list, click the 'Display Options' tab and select 'Display All Items.' From there, select the list you wish to display all items from and click submit.");
                Text displayIncomplete = new Text('\u2022' + "In order to display all items in a list, click the 'Display Options' tab and select 'Display Incomplete Items.' From there, select the list you wish to display all incomplete items from and click submit.");
                Text displayComplete = new Text('\u2022' + "In order to display all items in a list, click the 'Display Options' tab and select 'Display Complete Items.' From there, select the list you wish to display all Complete items from and click submit.");
                Text fileSave = new Text('\u2022' + "In order to save a list to external storage click the 'File' tab and select 'Save List.' From there, select the list you wish to save, and then click the 'Select Directory' button. Select the directory you wish to save your list in from the file pop-up window, and your list will be saved.");
                Text fileLoad = new Text('\u2022' + "In order to load a list from external storage, click the 'File' tab and select 'Load List.' From there, click the 'Select file' button and select the file you wish to load (must be a .csv file to work). Your list will be added.");

                helpRoot.getChildren().addAll(OverView, createListHelp, clearList, editTitle, newItem, deleteItem, editDesc, editDate, editComplete, displayLists, displayAllItems, displayIncomplete, displayComplete, fileSave, fileLoad);
                primaryStage.setScene(helpScene);
            }
        });

        primaryStage.setTitle("To Do Lists");
        primaryStage.setScene(listView);
        primaryStage.show();

    }
}
