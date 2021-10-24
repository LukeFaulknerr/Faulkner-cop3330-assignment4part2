package ucf.assignments;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.*;


public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        BorderPane root = new BorderPane();
        Scene listView = new Scene(root, 1000, 1000, Color.WHITE);

        MenuBar menu = new MenuBar();
        menu.prefWidthProperty().bind(primaryStage.widthProperty());
        root.setTop(menu);

        Menu listMenu = new Menu("List");
        MenuItem newList = new MenuItem("New List");
        MenuItem deleteList = new MenuItem("Delete List");
        MenuItem editTitle = new MenuItem("Edit Title");

        listMenu.getItems().addAll(newList, deleteList, editTitle);

        Menu itemMenu = new Menu("Items");
        MenuItem newItem = new MenuItem("New Item");
        MenuItem deleteItem = new MenuItem("Delete Item");
        MenuItem editDesc = new MenuItem("Edit Description");
        MenuItem editDate = new MenuItem("Edit Date");
        MenuItem editComplete = new MenuItem("Mark Complete");

        itemMenu.getItems().addAll(newItem, deleteItem, new SeparatorMenuItem(), editDesc, editDate, editComplete);

        menu.getMenus().addAll(listMenu, itemMenu);

        primaryStage.setTitle("To Do Lists");
        primaryStage.setScene(listView);
        primaryStage.show();




    }
}
