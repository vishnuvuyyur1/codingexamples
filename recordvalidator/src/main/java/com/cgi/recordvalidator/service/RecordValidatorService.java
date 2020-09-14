package com.cgi.recordvalidator.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.cgi.recordvalidator.Application;
import com.cgi.recordvalidator.exception.RecordValidatorException;

/**
 * The class that performs the business logic 1. Read the transactions data from
 * the file 2. Validates whether all the transaction references are unique 3.
 * Validates the end balance of each transaction 4. Sends back the result for
 * display on console
 */
public class RecordValidatorService {

	/**
	 * @param fileName
	 *            : Takes the name of the file located in classpath resources folder
	 * @return StringBuilder object that is used to display result on console
	 * @throws RecordValidatorException
	 */
	public static StringBuilder validateRecords(String fileName) throws RecordValidatorException {
		// Stores the result of validation for display purpose
		StringBuilder validationResult = new StringBuilder();
		Path csvFilePath;
		try {
			csvFilePath = Paths.get(Application.class.getClassLoader().getResource(fileName).toURI());
			// Reading the whole data from CSV file into java object
			Stream<String> entireRecordsTableStream = Files.lines(csvFilePath);
			// Once the data is available in java object, refining the data by skipping the
			// headers
			// Separating individual rows by new line and storing in one object
			String entireRecordsTableString = entireRecordsTableStream.skip(1).collect(Collectors.joining("\n"));
			// Extracting the individual rows to an array
			// [194261,NL91RABO0315273637,Clothes from Jan Bakker,21.6,-41.83,-20.23
			// 112806,NL27SNSB0917829871,Clothes for Willem Dekker,91.23,+15.57,106.8
			// 183049,NL69ABNA0433647324,Clothes for Jan King,86.66,+44.5,131.16]
			String recordsTableRows[] = entireRecordsTableString.split("\\r?\\n");
			Set<String> allTransactionReferences = new HashSet<String>();
			// looping through the array of rows
			for (int i = 0; i < recordsTableRows.length; i++) {
				// Extracting data form single row
				// [194261,NL91RABO0315273637,Clothes from Jan Bakker,21.6,-41.83,-20.23]
				String[] singleRecordRowData = recordsTableRows[i].split(",");

				validateAllTransactionReferences(allTransactionReferences, singleRecordRowData[0].trim(),
						validationResult);
				validateEndBalancesForSingleTransaction(singleRecordRowData, validationResult);

			}
		} catch (Exception e) {
			System.out.println(e);
			throw new RecordValidatorException("something wrong reading the file" + fileName);
		}

		return validationResult;
	}

	/**
	 * @param allTransactionReferences : Takes all transaction references
	 * @param transactionReference : A single transaction reference to validate against all transaction references
	 * @param validationResult :  After validating uniqueness of transaction reference stores the result 
	 * @return
	 */
	static StringBuilder validateAllTransactionReferences(Set<String> allTransactionReferences,
			String transactionReference, StringBuilder validationResult) {

		if (allTransactionReferences.contains(transactionReference)) { // check duplication
			validationResult.append("Failed Record    " + "     Reference Num: " + transactionReference
					+ "    Description: The transaction references is not unique");
			validationResult.append(System.getProperty("line.separator"));
		} else {
			allTransactionReferences.add(transactionReference);// add as forwarded
		}
		return validationResult;
	}

	/**
	 * @param singleRecordRowData [194261,NL91RABO0315273637,Clothes from Jan Bakker,21.6,-41.83,-20.23]
	 * @param validationResult    After validating End balance of a single transaction stores the result 
	 * @return
	 */
	static StringBuilder validateEndBalancesForSingleTransaction(String[] singleRecordRowData,
			StringBuilder validationResult) {

		if (Double.parseDouble(singleRecordRowData[3]) + Double.parseDouble(singleRecordRowData[4]) == Double
				.parseDouble(singleRecordRowData[5])) {

		} else {
			validationResult.append("Failed Record    " + "     Reference Num: " + singleRecordRowData[0]
					+ "    Description: The end balance is not valid");
			validationResult.append(System.getProperty("line.separator"));
		}
		return validationResult;

	}
}
