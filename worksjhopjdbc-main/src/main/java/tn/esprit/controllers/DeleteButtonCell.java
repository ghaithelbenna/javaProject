package tn.esprit.controllers;

import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import tn.esprit.models.Pack;

public class DeleteButtonCell extends TableCell<Pack, Boolean> {

    final Button deleteButton = new Button("Supprimer");

    public DeleteButtonCell() {
        deleteButton.setOnAction(event -> {
            Pack pack = getTableView().getItems().get(getIndex());
            getTableView().getItems().remove(pack);
        });
    }

    @Override
    protected void updateItem(Boolean item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setGraphic(null);
        } else {
            setGraphic(deleteButton);
        }
    }
}
