package com.carefirst.fep.PAV.regression;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class NOPXmlChkCompare {
	
	@Test
	public void f() throws Exception 
	{
		String actFolderPath="E:/Automation/pavpremera/PavRegression/NOPXMLValidation/Actual/CHK";
		String expXmlFilePath="E:/Automation/pavpremera/PavRegression/NOPXMLValidation/Expected/CHK/FEP_Bridge_Premera_PAY_ProvChk.xml";
		//String actXmlFilePath="E:/Automation/pavpremera/PavRegression/NOPXMLValidation/Actual/CHK/FEP_Bridge_Premera_PAY_ProvChk_430_20190731_053619.xml";
		
		//Get Latest file from folder path
		File myval=getLatestFilefromDir(actFolderPath);
		//System.out.println("myval" + myval );
		String actXmlFilePath=myval.getAbsolutePath();
		System.out.println("actXmlFilePath" + actXmlFilePath );
		
		//Validate Elements listed
		List<String> FinanceCheckRecordArrayElements = Arrays.asList(
				  "PayeeIDNumber","PayeeName","CheckAmount","VoucherCode","StateCode","FEPSPLIT1HO","FEPSPLIT2SO","FEPSPLIT3BO","FEPSPLIT4HMO","FEPSPLIT5SS",
				  "FEPSPLIT6BF","PayeeAddress1","PayeeAddress2","PayeeAddress3","PayeeCity","PayeeState","PayeeZip","PayeeAlternateID","MBRNumber",
				  "PayeeType","PaymentType","TaxId","PlanCode");
		
		List<String> NOPRemitDetailsElements = Arrays.asList("OutputFormat","Application","HPXPrintInd","MedicalOrDental");
		List<String> SPRElements = Arrays.asList("MailingAddFirstName","ChkMailingAddLine1","ChkMailingAddCity","ChkMailingAddState","ChkMailingAddZip","CheckAmountSummary");
		List<String> PaymentElements = Arrays.asList("ProviderNumber","ProviderTaxID","ProviderNPINumber","PayeeNumber");
		List<String> RemarkElements = Arrays.asList("RemarkCode","RemarkDescription");
		List<String> ClaimLineArrayElements = Arrays.asList("ClaimNumber");
				
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	      
		File expxmlFile = new File(expXmlFilePath);
        Document expDoc = docBuilder.parse(expxmlFile);
        
        File actxmlFile = new File(actXmlFilePath);
        Document actDoc = docBuilder.parse(actxmlFile);
        
         //Validate FinanceCheckRecordArray Elements
		NodeList ExpFCRParentNodeslist = expDoc.getElementsByTagName("FinanceCheckRecordArray");
		NodeList ActFCRParentNodeslist = actDoc.getElementsByTagName("FinanceCheckRecordArray");
		
		NodeList ExpNOPParentNodeslist = expDoc.getElementsByTagName("NOP_RemitDetails");
		NodeList ActNOPParentNodeslist = actDoc.getElementsByTagName("NOP_RemitDetails");
		
		NodeList ExpSPRParentNodeslist = expDoc.getElementsByTagName("SummaryPageArray");
		NodeList ActSPRParentNodeslist = actDoc.getElementsByTagName("SummaryPageArray");
		
		NodeList ExpPaymentParentNodeslist = expDoc.getElementsByTagName("PaymentDetails");
		NodeList ActPaymentParentNodeslist = actDoc.getElementsByTagName("PaymentDetails");
		
		NodeList ExpRemarksParentNodeslist = expDoc.getElementsByTagName("Remarks");
		NodeList ActRemarksParentNodeslist = actDoc.getElementsByTagName("Remarks");
		
		NodeList ExpClaimArrayParentNodeslist = expDoc.getElementsByTagName("ClaimArray");
		NodeList ActClaimArrayParentNodeslist = actDoc.getElementsByTagName("ClaimArray");
		
		int expnumofFCRparentnodes=ExpFCRParentNodeslist.getLength();
		int actnumofFCRparentnodes=ActFCRParentNodeslist.getLength();
		System.out.println("expnumofparentnodes[" + expnumofFCRparentnodes + "] " + "actnumofparentnodes[" + actnumofFCRparentnodes + "] are equal.");
		if(expnumofFCRparentnodes!=actnumofFCRparentnodes)
			System.out.println("expnumofparentnodes[" + expnumofFCRparentnodes + "] " + "actnumofparentnodes[" + actnumofFCRparentnodes + "] are not equal."); 
		
		String ExpFCRchildelementname=null;
		String ActFCRchildelementname=null;
		String ExpFCRchildelementvalue=null;
		String ActFCRchildelementvalue=null;
		
		String ExpNOPchildelementname=null;
		String ExpNOPchildelementvalue=null;
		String ActNOPchildelementvalue=null;
		
		String ExpSPRchildelementname=null;
		String ExpSPRchildelementvalue=null;
		String ActSPRchildelementvalue=null;
		
		String ExpPaymentchildelementname=null;
		String ExpPaymentchildelementvalue=null;
		String ActPaymentchildelementvalue=null;
		
		//Loop through all Expected FinanceCheckRecordArray Nodes
		for(int ExpChildItem=0;ExpChildItem<expnumofFCRparentnodes;ExpChildItem++)
	  	{
			boolean ExpPayeeIDNumberPrint=false;
			
			//Expected FinanceCheckRecordArray Nodes
		  	Node ExpFCRParentNode = ExpFCRParentNodeslist.item(ExpChildItem);
		  	NodeList ExpFCRChildlist = ExpFCRParentNode.getChildNodes();
		 
		  //Expected NOP_RemitDetails Nodes
		  	Node ExpNOPParentNode = ExpNOPParentNodeslist.item(ExpChildItem);
		  	NodeList ExpNOPChildlist = ExpNOPParentNode.getChildNodes();
		  	
		  //Expected SummaryPageArray Nodes
		  	Node ExpSPRParentNode = ExpSPRParentNodeslist.item(ExpChildItem);
		  	NodeList ExpSPRChildlist = ExpSPRParentNode.getChildNodes();
		  	
		  //Expected PaymentDetails Nodes
		  	Node ExpPaymentParentNode = ExpPaymentParentNodeslist.item(ExpChildItem);
		  	NodeList ExpPaymentChildlist = ExpPaymentParentNode.getChildNodes();
		  	
		  //Expected Remarks Nodes
		  	Node ExpRemarksParentNode = ExpRemarksParentNodeslist.item(ExpChildItem);
		  	NodeList ExpRemarksChildlist = ExpRemarksParentNode.getChildNodes();
			  	
		  //Expected ClaimLineArray Nodes
		  	Node ExpClaimArrayParentNode = ExpClaimArrayParentNodeslist.item(ExpChildItem);
		  	NodeList ExpClaimArrayChildlist = ExpClaimArrayParentNode.getChildNodes();
			
		  	//Get Expected Payee ID
		  	String ExpPayeeIDNumber=null;
		  	for (int itemnum = 0; itemnum != ExpFCRChildlist.getLength(); ++itemnum)
		    {
		  		Node Expchild = ExpFCRChildlist.item(itemnum);
		  		ExpFCRchildelementname=Expchild.getNodeName();
		  		if(ExpFCRchildelementname.equals("PayeeIDNumber"))
			    {
		  			ExpPayeeIDNumber=Expchild.getTextContent();
		  			break;
			    }
		    }
		  	
		 	//For each expected Payee ID,Loop through all Actual Nodes for Payee ID, if exists validate all elements else report not Exists
		  	boolean PayeeIDNumberExists=false;
		  	for(int ActChildItem=0;ActChildItem<actnumofFCRparentnodes;ActChildItem++)
		  	{
		  		//Actual FinanceCheckRecordArray Nodes
		  		Node ActFCRParentNode = ActFCRParentNodeslist.item(ActChildItem);
			  	NodeList ActFCRChildlist = ActFCRParentNode.getChildNodes();
			  	
			  	//Actual NOP_RemitDetails Nodes
		  		Node ActNOPParentNode = ActNOPParentNodeslist.item(ActChildItem);
			  	NodeList ActNOPChildlist = ActNOPParentNode.getChildNodes();
			  	
			  //Actual SummaryPageArray Nodes
		  		Node ActSPRParentNode = ActSPRParentNodeslist.item(ActChildItem);
			  	NodeList ActSPRChildlist = ActSPRParentNode.getChildNodes();
			  	
			  //Actual PaymentDetails Nodes
		  		Node ActPaymentParentNode = ActPaymentParentNodeslist.item(ActChildItem);
			  	NodeList ActPaymentChildlist = ActPaymentParentNode.getChildNodes();
			  	
			  //Actual Remarks Nodes
		  		Node ActRemarksParentNode = ActRemarksParentNodeslist.item(ActChildItem);
			  	NodeList ActRemarksChildlist = ActRemarksParentNode.getChildNodes();
			  	
							  	
			  //Actual ClaimLineArray Nodes
		  		Node ActClaimArrayParentNode = ActClaimArrayParentNodeslist.item(ActChildItem);
			  	NodeList ActClaimArrayChildlist = ActClaimArrayParentNode.getChildNodes();
				
			  	//Get Actual PayeeIDNumber
			  	String ActPayeeIDNumber=null;
				for (int itemnum = 0; itemnum != ActFCRChildlist.getLength(); ++itemnum)
			    {
					Node Actchild = ActFCRChildlist.item(itemnum);
					ActFCRchildelementname=Actchild.getNodeName();
			  		if(ActFCRchildelementname.equals("PayeeIDNumber"))
				    {
			  			ActPayeeIDNumber=Actchild.getTextContent();
			  			break;
				    }
			  	}
			  	
				//Compare if Actual PayeeID number Equals to Expected PayeeID number
				if(ActPayeeIDNumber.equals(ExpPayeeIDNumber))
				{
					PayeeIDNumberExists=true;
					//Compare all FCR Child values, loop though all Expected childs and compare with Actual
					boolean FCRPrint=false;
					for (int itemnum = 0; itemnum != ExpFCRChildlist.getLength(); ++itemnum)
				    {
				  		Node ExpFCRchild = ExpFCRChildlist.item(itemnum);
				  		Node ActFCRchild = ActFCRChildlist.item(itemnum);
				  		ExpFCRchildelementname=ExpFCRchild.getNodeName();
				  		if(FinanceCheckRecordArrayElements.contains(ExpFCRchildelementname))
					    {
					    	ExpFCRchildelementvalue=ExpFCRchild.getTextContent();
					    	ActFCRchildelementvalue=ActFCRchild.getTextContent();
					    	
					    	if(!ExpFCRchildelementvalue.equals(ActFCRchildelementvalue))
					    	{
					    		if(!ExpPayeeIDNumberPrint)
					    			System.out.println("For PayeeIDNumber:" + ExpPayeeIDNumber);
					    		
					    		if(!FCRPrint)
					    			System.out.println("\t" + "Under FinanceCheckRecordArray/");
					    		
					    		ExpPayeeIDNumberPrint=true;
					    		FCRPrint=true;
					    		System.out.println("\t" + "\t" + ExpFCRchildelementname + "::Expected Value[" + ExpFCRchildelementvalue + "] " + "Actual Value[" + ActFCRchildelementvalue + "] are not equal.");
					    	}
					    }
				    }
					
					//Compare all NOP Child values, loop though all Expected childs and compare with Actual
					boolean NOPPrint=false;
					for (int itemnum = 0; itemnum != ExpNOPChildlist.getLength(); ++itemnum)
				    {
				  		Node ExpNOPchild = ExpNOPChildlist.item(itemnum);
				  		Node ActNOPchild = ActNOPChildlist.item(itemnum);
				  		ExpNOPchildelementname=ExpNOPchild.getNodeName();
				  		if(NOPRemitDetailsElements.contains(ExpNOPchildelementname))
					    {
					    	ExpNOPchildelementvalue=ExpNOPchild.getTextContent();
					    	ActNOPchildelementvalue=ActNOPchild.getTextContent();
					    	
					    	if(!ExpNOPchildelementvalue.equals(ActNOPchildelementvalue))
					    	{
					    		if(!ExpPayeeIDNumberPrint)
					    			System.out.println("For ExpPayeeIDNumber:" + ExpPayeeIDNumber);
					    		
					    		if(!NOPPrint)
					    			System.out.println("\t" + "Under NOP_RemitDetails/");
					    		
					    		ExpPayeeIDNumberPrint=true;
					    		NOPPrint=true;
					    		System.out.println("\t" + "\t" + ExpNOPchildelementname + "::Expected Value[" + ExpNOPchildelementvalue + "] " + "Actual Value[" + ActNOPchildelementvalue + "] are not equal.");
					    	}
					    }
				    }
					
					//Compare all SummaryPageArray Child values, loop though all Expected childs and compare with Actual
					boolean SummaryPageArrayPrint=false;
					for (int itemnum = 0; itemnum != ExpSPRChildlist.getLength(); ++itemnum)
				    {
				  		Node ExpSPRchild = ExpSPRChildlist.item(itemnum);
				  		Node ActSPRchild = ActSPRChildlist.item(itemnum);
				  		ExpSPRchildelementname=ExpSPRchild.getNodeName();
				  		if(SPRElements.contains(ExpSPRchildelementname))
					    {
					    	ExpSPRchildelementvalue=ExpSPRchild.getTextContent();
					    	ActSPRchildelementvalue=ActSPRchild.getTextContent();
					    	
					    	if(!ExpSPRchildelementvalue.equals(ActSPRchildelementvalue))
					    	{
					    		if(!ExpPayeeIDNumberPrint)
					    			System.out.println("For ExpPayeeIDNumber:" + ExpPayeeIDNumber);
					    		
					    		if(!SummaryPageArrayPrint)
					    			System.out.println("\t" + "Under CheckSummaryDetails/SummaryPageArray");
					    		
					    		ExpPayeeIDNumberPrint=true;
					    		SummaryPageArrayPrint=true;
					    		System.out.println("\t" + "\t" + ExpSPRchildelementname + "::Expected Value[" + ExpSPRchildelementvalue + "] " + "Actual Value[" + ActSPRchildelementvalue + "] are not equal.");
					    	}
					    }
				    }
					
					//Compare all PaymentDetails Child values, loop though all Expected childs and compare with Actual
					boolean PaymentDetailsPrint=false;
					for (int itemnum = 0; itemnum != ExpPaymentChildlist.getLength(); ++itemnum)
				    {
				  		Node ExpPaymentchild = ExpPaymentChildlist.item(itemnum);
				  		Node ActPaymentchild = ActPaymentChildlist.item(itemnum);
				  		ExpPaymentchildelementname=ExpPaymentchild.getNodeName();
				  		if(PaymentElements.contains(ExpPaymentchildelementname))
					    {
					    	ExpPaymentchildelementvalue=ExpPaymentchild.getTextContent();
					    	ActPaymentchildelementvalue=ActPaymentchild.getTextContent();
					    	
					    	if(!ExpPaymentchildelementvalue.equals(ActPaymentchildelementvalue))
					    	{
					    		if(!ExpPayeeIDNumberPrint)
					    			System.out.println("For ExpPayeeIDNumber:" + ExpPayeeIDNumber);
					    		
					    		if(!PaymentDetailsPrint)
					    			System.out.println("\t" + "Under PaymentDetails/");
					    		
					    		ExpPayeeIDNumberPrint=true;
					    		PaymentDetailsPrint=true;
					    		System.out.println("\t" + "\t" + ExpPaymentchildelementname + "::Expected Value[" + ExpPaymentchildelementvalue + "] " + "Actual Value[" + ActPaymentchildelementvalue + "] are not equal.");
					    	}
					    }
				    }
					
					//Compare all Remarks Child values, loop though all Expected childs and compare with Actual
					boolean RemarksPrint=false;
					for (int itemnum = 0; itemnum != ExpRemarksChildlist.getLength(); ++itemnum)
				    {
				  		Node ExpRemarkschild = ExpRemarksChildlist.item(itemnum);
				  		Node ActRemarkschild = ActRemarksChildlist.item(itemnum);
				  		
				  		//Loop through all RemarkArray Nodes
				  		NodeList ExpRemarkArrayChildlist = ExpRemarkschild.getChildNodes();
				  		NodeList ActRemarkArrayChildlist = ActRemarkschild.getChildNodes();
				  		for (int Chlditemnum = 0; Chlditemnum != ExpRemarkArrayChildlist.getLength(); ++Chlditemnum)
					    {
				  			Node ExpRemarkArraychild = ExpRemarkArrayChildlist.item(Chlditemnum);
				  			Node ActRemarkArraychild = ActRemarkArrayChildlist.item(Chlditemnum);
				  			String ExpRemarkArraychildelementname=ExpRemarkArraychild.getNodeName();
					  		if(RemarkElements.contains(ExpRemarkArraychildelementname))
						    {
					  			String ExpRemarkArraychildelementvalue = ExpRemarkArraychild.getTextContent();
						    	String ActRemarkArraychildelementvalue = ActRemarkArraychild.getTextContent();
						    	if(!ExpRemarkArraychildelementvalue.equals(ActRemarkArraychildelementvalue))
						    	{
						    		
						    		if(!RemarksPrint)
						    			System.out.println("\t" + "Under /Remarks/RemarkArray/");
						    		
						    		RemarksPrint=true;
						    		System.out.println("\t" + "\t" + ExpRemarkArraychildelementname + "::Expected Value[" + ExpRemarkArraychildelementvalue + "] " + "Actual Value[" + ActRemarkArraychildelementvalue + "] are not equal.");
								  	System.out.println("\n");
						    	}
						    }
				  		}
				    }
					
					//Compare all ClaimLineArray Child values, loop though all Expected childs and compare with Actual
					boolean ClaimLineArrayPrint=false;
					for (int itemnum = 0; itemnum != ExpClaimArrayChildlist.getLength(); ++itemnum)
				    {
				  		Node ExpClaimArraychild = ExpClaimArrayChildlist.item(itemnum);
				  		Node ActClaimArraychild = ActClaimArrayChildlist.item(itemnum);
				  		
				  		//Loop through all ClaimArray Nodes
				  		NodeList ExpClaimLineArrayChildlist = ExpClaimArraychild.getChildNodes();
				  		NodeList ActClaimLineArrayChildlist = ActClaimArraychild.getChildNodes();
				  		for (int Chlditemnum = 0; Chlditemnum != ExpClaimLineArrayChildlist.getLength(); ++Chlditemnum)
					    {
				  			Node ExpClaimLineArraychild = ExpClaimLineArrayChildlist.item(Chlditemnum);
				  			Node ActClaimLineArraychild = ActClaimLineArrayChildlist.item(Chlditemnum);
				  			String ExpClaimLineArraychildelementname=ExpClaimLineArraychild.getNodeName();
				  			//System.out.println("ExpClaimLineArraychildelementname:"+ExpClaimLineArraychildelementname);
					  		if(ClaimLineArrayElements.contains(ExpClaimLineArraychildelementname))
						    {
					  			String ExpClaimLineArraychildelementvalue = ExpClaimLineArraychild.getTextContent();
						    	String ActClaimLineArraychildelementvalue = ActClaimLineArraychild.getTextContent();
						    	if(!ExpClaimLineArraychildelementvalue.equals(ActClaimLineArraychildelementvalue))
						    	{
						    		
						    		if(!ClaimLineArrayPrint)
						    			System.out.println("\t" + "Under /ClaimArray/ClaimLineArray/");
						    		
						    		ClaimLineArrayPrint=true;
						    		System.out.println("\t" + "\t" + ExpClaimLineArraychildelementname + "::Expected Value[" + ExpClaimLineArraychildelementvalue + "] " + "Actual Value[" + ActClaimLineArraychildelementvalue + "] are not equal.");
								  	System.out.println("\n");
						    	}
						    }
				  		}
				    }
				}
				
				if(PayeeIDNumberExists)
				{
					break;
				}
		
		  	}//For each expected PayeeID Loop Ends
			
		  	if(!PayeeIDNumberExists)
			{
				System.out.println("ExpPayeeIDNumber" + ExpPayeeIDNumber  + " Not found.");
			}
		}//Parents FinanceCheckRecordArray loop ends	
	}
	
	private static File getLatestFilefromDir(String dirPath)
	{
	    File dir = new File(dirPath);
	    File[] files = dir.listFiles();
	    if (files == null || files.length == 0) {
	        return null;
	    }

	    File lastModifiedFile = files[0];
	    for (int i = 1; i < files.length; i++) {
	       if (lastModifiedFile.lastModified() < files[i].lastModified()) {
	           lastModifiedFile = files[i];
	       }
	    }
	    
	    return lastModifiedFile;
	}
}