package controllers;

import bdd.Database;
import views.MainView;

public class MainController {
	private MainView _mainView;
	public Database _bdd;
	
	public MainController() {		
		_mainView = new MainView();
		
		_bdd.getInstance();
		
		_mainView.setVisible(true);
	}
}
