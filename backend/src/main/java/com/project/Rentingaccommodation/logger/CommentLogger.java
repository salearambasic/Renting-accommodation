package com.project.Rentingaccommodation.logger;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.project.Rentingaccommodation.controller.CommentController;

public class CommentLogger {

	private final static Logger logger = Logger.getLogger(CommentController.class.getName());

	public static void log(Level level, String message) throws SecurityException, IOException {
		//logger.addHandler(new ConsoleHandler());
		FileHandler fileHandler = new FileHandler(System.getProperty("user.dir") + "\\CommentLogger.txt",true);
		fileHandler.setFormatter(new SimpleFormatter());
		logger.addHandler(fileHandler);
		logger.log(level, message);
	}
}
