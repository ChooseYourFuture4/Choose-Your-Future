module ChooseYourFuture {
	requires javafx.controls;
	requires java.sql;
	requires javafx.graphics;
	requires javafx.fxml;
	requires javafx.base;
	requires jdk.crypto.cryptoki;
	requires javafx.web;
	requires jdk.jsobject;
	requires java.xml;
	requires java.desktop;
	
	opens application to javafx.graphics, javafx.fxml;
}
