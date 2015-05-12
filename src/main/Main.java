package main;

import model.IModel;
import model.Model;
import view.PrimaryFrame;
import controller.IPrimaryFrameController;
import controller.PrimaryFrameController;

public final class Main {
	
	private Main() {}

	public static void main(final String[] args) {
		final TestModel t = new TestModel();
		final IModel model = t.getModel();
		final PrimaryFrame view = new PrimaryFrame();
		final IPrimaryFrameController controller = new PrimaryFrameController(model, view);
		
		controller.cmdLoad(null);
		view.setVisible(true);
		controller.buildLoginPanel();
	}

}
