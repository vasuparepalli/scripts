package com.pav.paralleltest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.LogManager;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class Glcomparision {

	String baseProjectPath = System.getProperty("user.dir");
	ExtentHtmlReporter htmlReporter;
	ExtentReports extent;
	ExtentTest logger;
	String logfilepath;
	String baseProjPath = System.getProperty("user.dir");
	String orgsList = System.getProperty("orgName"); // "BCBSNC,PREMERA";
	String environment = System.getProperty("environment");// "batche2e";
	String buildnumber = System.getProperty("buildno");// "2022";
	PropertyUtils props = new PropertyUtils(baseProjectPath.concat("/src/main/resources/config.property"));
	String orgs[] = null;
	Logger LOG = LoggerFactory.getLogger(App.class);
	FileUtils fUtils = new FileUtils();
	InitializationUtil inUtil = new InitializationUtil();

	@BeforeTest
	public void setUpReport() {
		htmlReporter = new ExtentHtmlReporter(props.getProperty("destPath").concat("/test-output/report.html"));
		extent = new ExtentReports();
		extent.setSystemInfo("Host Name", "Test");
		extent.setSystemInfo("Environment", "Test");
		extent.setSystemInfo("User Name", "Test");
		htmlReporter.loadXMLConfig(new File(System.getProperty("user.dir") + "/extent-config.xml"));
		extent.attachReporter(htmlReporter);
		if (orgsList.contains(",")) {
			orgs = orgsList.split(",");
		} else {
			orgs = new String[] { orgsList };
		}
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss");
		Date date = new Date();
		System.out.println(formatter.format(date));
		String logfilename = "log" + (formatter.format(date)) + ".log";
		logfilepath = props.getProperty("destPath").concat("/test-output/").concat(logfilename);

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

	@AfterTest
	public void endReport() {
		extent.flush();
		extent.close();
	}

	@Test
	public void xmlTest() throws IOException {
		Logger LOG = LoggerFactory.getLogger(Glcomparision.class);
		for (String orgName : orgs) {
			String[] planCodes = inUtil.getPlanCodesForOrg(orgName, props);
			for (String planCode : planCodes) {
				String[] payTypes = inUtil.getPayTypes(orgName, planCode, props);
				for (String payType : payTypes) {
					PropertyUtils utils = null;
					if (payType.equals("GL")) {
						
						utils = new PropertyUtils(baseProjectPath.concat("/src/main/resources/ElemPosition.property"));
					} else if (payType.equals("CASH")) {
						utils = new PropertyUtils(baseProjectPath.concat("/src/main/resources/cash.property"));
					}
					String actDirName = props.getProperty("destPath").concat("/").concat(buildnumber).concat("/")
							.concat(orgName).concat("/").concat(planCode).concat("/").concat(payType);
					String expDirName = props.getProperty("expPath").concat("/").concat(orgName).concat("/")
							.concat(planCode).concat("/").concat(payType);
					ArrayList<String> expFileNames = fUtils.getTxtFilesFromDir(expDirName);
					ArrayList<String> actFileNames = fUtils.getTxtFilesFromDir(actDirName);
					if (expFileNames.size() != 1 || actFileNames.size() != 1) {
						logger = extent.createTest(expDirName);
						System.out.println("ERROR !! - Either expected or Actual have either more than 1 or 0 files.");
						logger.log(Status.FAIL, "Either expected or Actual have either more than 1 or 0 files.");
						LOG.info("Either expected or Actual have either more than 1 or 0 files.");
						LOG.info("Status " + logger.getStatus());

					} else {
						try {
							logger = extent.createTest(planCode + "-" + payType);
							LOG.info("****Execution for the plan code : " + payType);
							String expectedOnlyfilename = expFileNames.get(0);
							String actualOnlyfilename = actFileNames.get(0);
							String expectedfilename = expDirName + "/" + expectedOnlyfilename;
							String actualfilename = actDirName + "/" + actualOnlyfilename;
							LOG.info("Expected File -- " + expectedfilename);
							LOG.info("Actual File -- " + actualfilename);

							/*
							 * String actualfilename =
							 * System.getProperty("user.dir") +
							 * "/src/main/resources/FEP_Bridge_Premera_GL_430_Expected.xml";
							 * String expectedfilename =
							 * System.getProperty("user.dir") +
							 * "/src/main/resources/FEP_Bridge_Premera_GL_430_Actual.xml";
							 */
							BufferedReader br;
							br = new BufferedReader(new FileReader(new File(actualfilename)));
							String line;
							StringBuilder sbActual;
							sbActual = new StringBuilder();
							while ((line = br.readLine()) != null) {
								sbActual.append(line.trim());
							}
							br = new BufferedReader(new FileReader(new File(expectedfilename)));
							StringBuilder sbExpected;
							sbExpected = new StringBuilder();
							while ((line = br.readLine()) != null) {
								sbExpected.append(line.trim());
							}
							System.out.println("xml Data:" + sbExpected);
							br.close();
							JAXBContext jaxbContext;
							try {
								jaxbContext = JAXBContext.newInstance(GeneralLedgerTransaction.class);
								Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
								GeneralLedgerTransaction objActual;
								objActual = (GeneralLedgerTransaction) jaxbUnmarshaller
										.unmarshal(new StringReader(sbActual.toString()));
								GeneralLedgerTransaction objExpected;
								objExpected = (GeneralLedgerTransaction) jaxbUnmarshaller
										.unmarshal(new StringReader(sbExpected.toString()));
								logger = extent.createTest("Test");
								util utilObj = new util(logger, LOG);
								String[] elementsToValidate = new String[] { "EntityCode", "CostCenter",
										"CostCenterPool", "Site", "Risk", "Product", "Market", "Operations", "Project",
										"CjaCode", "Future", "CjaCode", "Future", "Source", "CycleDate", "GLAmount",
										"GLDescription" };
								for (String element : elementsToValidate) {
									utilObj.assertValues(objExpected, objActual, element);
								}
								utilObj.assertCustomValues(objExpected, objActual, "CustomElements");
							} catch (JAXBException e) {
								e.printStackTrace();
							}
						} catch (Exception ex) {

						}
					}
				}
			}
		}
	}
}