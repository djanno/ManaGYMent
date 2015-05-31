package main;

import model.IModel;
import model.Model;
import view.PrimaryFrame;
import controller.IPrimaryFrameController;
import controller.PrimaryFrameController;

/**
 * Main dell'applicazione.
 * @author Federico Giannoni
 * @author Simone Letizi
 * @author Davide Borficchia
 *
 */
public final class Main {

    private Main() {
    }

    public static void main(final String[] args) {
        final IModel model = Model.getModel();
        final PrimaryFrame view = PrimaryFrame.getPrimaryFrame();
        final IPrimaryFrameController controller = new PrimaryFrameController(model, view);

        controller.cmdLoad(null);
        view.setVisible(true);
        controller.buildLoginPanel();
    }

}
