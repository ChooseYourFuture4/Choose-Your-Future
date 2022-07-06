module ChooseYourFuture {
	requires javafx.controls;
	requires java.sql;
	requires javafx.graphics;
	requires javafx.fxml;
	requires javafx.base;
	requires jdk.crypto.cryptoki;
	requires javafx.web;
	
	opens application to javafx.graphics, javafx.fxml;
}
