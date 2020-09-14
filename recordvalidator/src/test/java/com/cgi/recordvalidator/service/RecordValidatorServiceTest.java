package com.cgi.recordvalidator.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.cgi.recordvalidator.exception.RecordValidatorException;

public class RecordValidatorServiceTest {
	StringBuilder validationResultMock;
	@Test
	public void validateRecordsTest() throws RecordValidatorException {
		StringBuilder result = RecordValidatorService.validateRecords("records.csv");
		assertNotNull(result);
	}

	@Test(expected = RecordValidatorException.class)
	public void validateRecordsExceptionTest() throws RecordValidatorException {
		RecordValidatorService.validateRecords("1records.csv");
	}

	@Test
	public void validateTransactionReferencesTestPositive(){
		validationResultMock=new StringBuilder();
		Set<String> allTransactionReferencesMock = new HashSet<String>();
		allTransactionReferencesMock.add("12345");
		allTransactionReferencesMock.add("12346");
		allTransactionReferencesMock.add("12347");
		StringBuilder actualOutput = RecordValidatorService.validateAllTransactionReferences(allTransactionReferencesMock,"12345",validationResultMock );
		String expectedOutput="Failed Record         Reference Num: 12345    Description: The transaction references is not unique";
		assertNotNull(actualOutput);
		assertEquals(expectedOutput.trim(), actualOutput.toString().trim());
	}
	
	@Test
	public void validateTransactionReferencesTestNegative(){
		validationResultMock=new StringBuilder();
		Set<String> allTransactionReferencesMock = new HashSet<String>();
		allTransactionReferencesMock.add("12345");
		allTransactionReferencesMock.add("12346");
		allTransactionReferencesMock.add("12347");
		StringBuilder actualOutput = RecordValidatorService.validateAllTransactionReferences(allTransactionReferencesMock,"12344",validationResultMock );
		assertEquals("", actualOutput.toString());
	}
	
	@Test
	public void validateEndBalancesForSingleTransactionPositive(){
		validationResultMock=new StringBuilder();
		String[] singleRecordRowDataMock= {"1234","NL91RABO0315273637", "Clothes from Jan Bakker", "21.6","-41.83","-20.23"};
		StringBuilder actualOutput = RecordValidatorService.validateEndBalancesForSingleTransaction(singleRecordRowDataMock,validationResultMock );
		String expectedOutput="Failed Record         Reference Num: 1234    Description: The end balance is not valid";
		assertNotNull(actualOutput);
		assertEquals(expectedOutput.trim(), actualOutput.toString().trim());
	}
	
	@Test
	public void validateEndBalancesForSingleTransactionNegative(){
		validationResultMock=new StringBuilder();
		String[] singleRecordRowDataMock= {"1234","NL91RABO0315273637", "Clothes from Jan Bakker", "30","20","50"};
		StringBuilder actualOutput = RecordValidatorService.validateEndBalancesForSingleTransaction(singleRecordRowDataMock,validationResultMock );
		assertEquals("", actualOutput.toString());
	}
}
