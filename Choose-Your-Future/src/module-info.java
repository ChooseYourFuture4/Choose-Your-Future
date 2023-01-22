module ChooseYourFuture {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.web;
	requires javafx.graphics;
	requires java.sql;
	requires java.desktop;
	requires jdk.crypto.cryptoki;
	requires javafx.base;
	requires jdk.jsobject;
	requires java.xml;
	
	opens application to javafx.graphics, javafx.fxml;
}
