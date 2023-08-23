module GestorePassaporti {
	requires javafx.controls;
	requires javafx.fxml;
<<<<<<< HEAD
	requires javafx.base;
	requires java.sql;
=======
	requires javafx.graphics;
	requires javafx.base;
>>>>>>> 298b7e919858fc878faf6fc1ad00d39971922ee1
	
	opens application to javafx.graphics, javafx.fxml;
}
