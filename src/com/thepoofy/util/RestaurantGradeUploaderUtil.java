package com.thepoofy.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.map.ObjectMapper;

import au.com.bytecode.opencsv.CSVReader;

import com.thepoofy.gwumpy.model.NycInspectionGrade;

public class RestaurantGradeUploaderUtil {

	private static final String GRADES_FILE = "C:\\allGrades.txt";
//	private static final String GRADES_FILE = "C:\\testGrades.txt";
	
	
//	private static final String ADDRESS = "http://127.0.0.1:8888/RestaurantGradeUpload";
	private static final String ADDRESS = "http://gwumpymobile.appspot.com/RestaurantGradeUpload";
	private static int uploadCounter = 0;
	
	private static final int MINIMUM = 17000;
	private static final int MAXIMUM = 24000;
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String [] args)
	{
		System.out.println("Starting uploader now...");
		final Map<String, NycInspectionGrade> gradeMap;
		try
		{
			gradeMap = loadGradesCsv();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return;
		}
		
		Set<Map.Entry<String, NycInspectionGrade>> entries = gradeMap.entrySet();
		
		System.out.println("Found "+entries.size());
		
		try
		{
			for(Map.Entry<String, NycInspectionGrade> entry: entries)
			{
				List<NycInspectionGrade> grades = new ArrayList<NycInspectionGrade>();
				grades.add(entry.getValue());
				
				if(uploadCounter < MINIMUM)
				{
					uploadCounter++;
				}
				else if(uploadCounter >= MAXIMUM)
				{
					return;
				}
				else
				{
					upload(grades);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace(System.err);
		}
	}
	
	/**
	 * 
	 * @param grades
	 * @throws Exception
	 * @throws InterruptedException
	 */
	private static void upload(List<NycInspectionGrade> grades)throws Exception, InterruptedException
	{
		List<KeyValuePair> params = new ArrayList<KeyValuePair>();
		
		ObjectMapper mapper = new ObjectMapper();
		
		params.add(new KeyValuePair("inspection", mapper.writeValueAsString(grades)));
		
		String response = URLUtil.doStandalonePost(ADDRESS, params);
		
		if(response == null)
		{
			throw new Exception("Failed to upload.");
		}
		
		System.out.println("Finished: "+uploadCounter++);
		
//		Thread.sleep(500);
	}
	
	
	/**
	 * 
	 * 
	 * @param gradeMap
	 * @param grade
	 * @return
	 */
	private static boolean isBetter(Map<String, NycInspectionGrade> gradeMap, NycInspectionGrade grade)
	{
		if(grade.getCurrentGrade() == null || grade.getCurrentGrade().isEmpty())
		{
			return false;
		}
		
		NycInspectionGrade prevGrade = gradeMap.get(grade.getCamis());
		if(prevGrade == null)
		{
			
			return true;
		}
		
		if(prevGrade.getInspectionDate().before(grade.getInspectionDate()))
		{
			
			return true;
		}
		
		return false;
	}
	
	/**
	 * 
	 * @param inspectionRecord
	 * @return
	 * @throws IOException
	 */
	private static NycInspectionGrade fromCsv(String inspectionRecord) throws IOException
	{
		CSVReader reader = new CSVReader(new StringReader(inspectionRecord));
		
		NycInspectionGrade grade = new NycInspectionGrade();
		
		String [] nextLine = null;
		
		List<String[]>records = reader.readAll();
		
		if(records != null && !records.isEmpty())
		{
			nextLine = records.get(0);
		}
		
		if(nextLine != null && nextLine.length > 10)
		{
			int i=0;
			grade.setCamis(nextLine[i++]);
			grade.setDba(nextLine[i++]);
			grade.setBoro(nextLine[i++]);
			grade.setBuilding(nextLine[i++]);
			grade.setStreet(nextLine[i++]);
			grade.setZipCode(nextLine[i++]);
			grade.setPhone(nextLine[i++]);
			grade.setCuisineCode(nextLine[i++]);
			grade.setInspectionDateString(nextLine[i++]);
			grade.setAction(nextLine[i++]);
			grade.setViolationCode(nextLine[i++]);
			grade.setScoreString(nextLine[i++]);
			grade.setCurrentGrade(nextLine[i++]);
			grade.setGradeDateString(nextLine[i++]);
			grade.setRecordDateString(nextLine[i++]);
		}
		
		return grade;
		
	}
	
	
	public static Map<String, NycInspectionGrade> loadGradesCsv() throws Exception
	{
		Map<String, NycInspectionGrade> gradeMap = new HashMap<String, NycInspectionGrade>();
		
		BufferedReader reader = new BufferedReader(new FileReader(GRADES_FILE));
		if(reader.ready())
		{
			reader.readLine();
		}
		
		
		while(reader.ready())
		{
			String csvRecord = reader.readLine();
			
			NycInspectionGrade grade = fromCsv(csvRecord);
			
			if(isBetter(gradeMap, grade))
			{
				gradeMap.put(grade.getCamis(), grade);
			}
		}
		
		return gradeMap;
	}
}
