package com.cgi.recordvalidator;

import com.cgi.recordvalidator.exception.RecordValidatorException;
import com.cgi.recordvalidator.service.RecordValidatorService;

/**
 * @author vishnu
 * Class with Main method that executes the application and shows the result
 * To execute the project right click on Application.java file and select run as Java Application from IDE
 * The Output will be displayed on console
 */
public class Application {
	// **INPUT** :We are retrieving the transaction data form CSV file which is
	// placed in class path resources folder
	public static final String RECORDSINPUTFILENAME = "records.csv";

	public static void main(String[] args) throws RecordValidatorException {
		StringBuilder validationResult = RecordValidatorService.validateRecords(RECORDSINPUTFILENAME);
		// ** OUTPUT **: After validating the records in the CSV file
		// The transaction reference and description of each of the failed records are displayed.
		System.out.println(validationResult);
	}
}
