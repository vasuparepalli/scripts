package com.pav.paralleltest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.PropertyConfigurator;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class FlatFileComparision {
	String baseProjectPath = System.getProperty("user.dir");
	ExtentHtmlReporter htmlReporter;
	ExtentReports extent;
	ExtentTest logger;
	String logfilepath;
	String baseProjPath = System.getProperty("user.dir");
	String orgsList = System.getProperty("SelectedOrgs");
	String environment = System.getProperty("environment");
	String buildnumber = System.getProperty("buildno");
	HashMap<Integer, String> expectedFileMapping = new HashMap<Integer, String>();
	HashMap<Integer, String> actualFileMapping = new HashMap<Integer, String>();
	List<HashMap<String, String>> mappedRecords = null;
	ArrayList<String> keysList = new ArrayList<String>();
	PropertyUtils utils = new PropertyUtils(baseProjectPath.concat("/src/main/resources/ElemPosition.property"));

	@BeforeTest
	public void setUpReport() {
		htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/test-output/report.html");
		extent = new ExtentReports();
		extent.setSystemInfo("Host Name", "Test");
		extent.setSystemInfo("Environment", "Test");
		extent.setSystemInfo("User Name", "Test");
		htmlReporter.loadXMLConfig(new File(System.getProperty("user.dir") + "/extent-config.xml"));
		extent.attachReporter(htmlReporter);
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss");
		Date date = new Date();
		System.out.println(formatter.format(date));
		String logfilename = "log" + (formatter.format(date)) + ".log";
		logfilepath = System.getProperty("user.dir") + "/test-output/" + logfilename;
		File file = new File(logfilepath);
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			Properties properties = new Properties();
			InputStream configStream = new FileInputStream(baseProjPath.concat("/src/main/resources/log4j.properties"));
			properties.load(configStream);
			configStream.close();
			properties.setProperty("log4j.appender.R.File", logfilepath);
			LogManager.resetConfiguration();
			PropertyConfigurator.configure(properties);
		} catch (Exception exception) {
			System.out.println("Error in finding the log file::" + exception.getMessage());
		}
	}

	@Test
	public void readFile() throws Exception {

		String actualfilename = "H:\\PavparallelProcessing\\Expected\\GeneralLedger\\Expected\\FSRPNCA.GLIN.FEP.PLAN580.2020-02-24-1741.TXT";
		String expectedfilename = "H:\\PavparallelProcessing\\Expected\\GeneralLedger\\Actual\\FSRPNCA.GLIN.FEP.PLAN580.2020-02-24-1741.TXT";
		expectedFileMapping = fileToStrobj(expectedfilename);
		System.out.println("Expected File Records: " + expectedFileMapping);
		actualFileMapping = fileToStrobj(actualfilename);
		System.out.println("Actual File Records: " + actualFileMapping);

		List<HashMap<String, String>> expectedMappedRecords = buildImpl(expectedFileMapping);
		List<HashMap<String, String>> actualMappedRecords = buildImpl(actualFileMapping);
		compareRecords(expectedMappedRecords, actualMappedRecords);
		extent.flush();
	}

	public void compareRecords(List<HashMap<String, String>> exp, List<HashMap<String, String>> act) {
		logger = extent.createTest("Flatfile comparison..");
		for (int i = 0; i < exp.size(); i++) {
			for (int j = 0; j < exp.get(i).size(); j++) {
				if (exp.get(j).get(keysList.get(j)).equals(act.get(j).get(keysList.get(j)))) {
					System.out.println("Expected Record value: Record Position: " + i + " Key is: " + keysList.get(j)
							+ " Value is: " + exp.get(j).get(keysList.get(j)));
					logger.log(Status.PASS, "Expected Record value: Record Position: " + i + " Key is: "
							+ keysList.get(j) + " Value is: " + exp.get(j).get(keysList.get(j)));
					System.out.println("Actual Record value: Record Position: " + i + " Key is: " + keysList.get(j)
							+ " Value is: " + act.get(j).get(keysList.get(j)));
				} else {
					System.out.println("Records doesn't match");
					logger.log(Status.FAIL, "Failed");
				}
			}
		}
	}

	public HashMap<Integer, String> fileToStrobj(String fileName) throws Exception {
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		BufferedReader br;
		br = new BufferedReader(new FileReader(new File(fileName)));
		String line;
		int counter = 0;
		while ((line = br.readLine()) != null) {

			map.put(counter, line.trim());
			counter++;
		}
		br.close();
		return map;
	}

	public List<HashMap<String, String>> buildImpl(HashMap<Integer, String> recordMap) {
		HashMap<String, String> keyValues = buildKeyStructure(recordMap);
		mappedRecords = new ArrayList<HashMap<String, String>>();
		for (int i = 0; i < recordMap.size(); i++) {
			HashMap<String, String> finalMapping = new HashMap<String, String>();
			for (int j = 0; j < keyValues.size(); j++) {
				String value = splitRecord(recordMap.get(i), keyValues.get(keysList.get(j)));
				finalMapping.put(keysList.get(j), value);
			}
			mappedRecords.add(finalMapping);
		}
		return mappedRecords;
	}

	public HashMap<String, String> buildKeyStructure(HashMap<Integer, String> map) {
		HashMap<String, String> keyValueMap = new HashMap<String, String>();
		Set<Object> keys = utils.getAllKeys();
		for (Object obj : keys) {
			String key = (String) obj;
			System.out.println("Key is: " + key + "Value is: " + utils.getProperty(key));
			keysList.add(key);
			keyValueMap.put(key, utils.getProperty(key));
		}
		return keyValueMap;
	}

	public String splitRecord(String record, String value) {
		int startPosition = Integer.valueOf(value.split(",")[0]);
		int endPosition = Integer.valueOf(value.split(",")[1]);
		return record.substring(startPosition, endPosition + 1);
	}

}
