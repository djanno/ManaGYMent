package main;

import model.IModel;
import model.Model;
import view.PrimaryFrame;
import controller.IPrimaryFrameController;
import controller.PrimaryFrameController;

public final class Main {

    private Main() {
    }

    public static void main(String[] args) {
        final IModel model = new Model();
        final PrimaryFrame view = new PrimaryFrame();
        final IPrimaryFrameController controller = new PrimaryFrameController(model, view);

        controller.cmdLoad(null);
        view.setVisible(true);
        controller.buildLoginPanel();
    }

}
