package org.vs;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Properties;

public class AlwaysActive {

	private static int x = 0;
	private static int y = 0;
    private  static final Logger logger=Logger.getLogger(AlwaysActive.class.getName());
	private static final InputStream reader=AlwaysActive.class.getClassLoader().getResourceAsStream("log4j.properties");;
    private static final String userHomeDir=System.getProperty("user.home");
	private static final Properties props=new Properties();
	static{
	InputStream reader= null;
	try {
		reader=AlwaysActive.class.getClassLoader().getResourceAsStream("log4j.properties");;
		props.load(reader);
	    Path path=	Paths.get(userHomeDir, props.getProperty("log"));
		String fileName=props.getProperty("fileName");
		fileName=fileName+"_"+Instant.now().truncatedTo(ChronoUnit.SECONDS).toString().replaceAll("[Z]", "")+".log";
       	props.setProperty("log", path.toUri().getPath());
		props.setProperty("fileName", fileName);
		PropertyConfigurator.configure(props);
		reader.close();

	} catch (Exception e) {
		logger.info("static block catch exception");
		logger.error(e.getMessage());
		System.exit(-1);
	}

}
	public static void main(String[] args)  {

		while (System.currentTimeMillis() > 0L) {
			logger.info("inside main while");
			spot();
			move1();
			spot();
			move2();
		}
	}

	private static void spot() {
		logger.info("inside spot()");
		Point currentPoint = MouseInfo.getPointerInfo().getLocation();
		x = (int) currentPoint.getX();
		y = (int) currentPoint.getY();
	}

	private static void move1() {
		try {
			logger.info("inside move1()");
			Robot robot = new Robot();
			robot.mouseMove(x + 1, y + 1);
			Thread.sleep(1000);
		} catch (AWTException | InterruptedException e) {
			logger.info("inside move1() exception");
			logger.error(e.getMessage());
			Thread.currentThread().interrupt();
		}
	}

	private static void move2() {
		try {
			logger.info("inside move2()");
			Robot robot = new Robot();
			robot.mouseMove(x - 2, y - 2);
			Thread.sleep(1000);
		} catch (AWTException | InterruptedException e) {
			logger.info("inside move2() exception");
			logger.error(e.getMessage());
			Thread.currentThread().interrupt();
		}
	}

}
