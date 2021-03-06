package org.fao.fenix.web.modules.table.client.view.utils;


import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;


public class FieldParser {

    private static final DateTimeFormat formatterMinus = DateTimeFormat.getFormat("yyyy-MM-dd");
	private static final DateTimeFormat formatterSlash = DateTimeFormat.getFormat("MM/dd/yyyy");
	private static final DateTimeFormat formatter = DateTimeFormat.getFormat("EEE MMM dd hh:mm:ss zzz yyyy");
	private static final DateTimeFormat formatterYear = DateTimeFormat.getFormat("yyyy");
	private static final DateTimeFormat formatterYearMonth = DateTimeFormat.getFormat("yyyy-MM");
	private static final DateTimeFormat longDateFormatter = DateTimeFormat.getFormat("E, dd MMM yyyy HH:mm:ss");
	private static final DateTimeFormat lastModifiedDate = DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss.S");
	private static final DateTimeFormat formatterMinusReverse = DateTimeFormat.getFormat("dd-MM-yyyy");

 
	public static Date parseStandardWorkstationFormat(String dateString) {
		return formatterMinus.parse(dateString);
	}
	
	public static Date parseDate(String dateString, String periodType) {
		
		if (dateString != null && !dateString.isEmpty()) {
			Date date = null;

			if (periodType.equals("yearly")) {
				date = formatterYear.parse(dateString);
			} else if (periodType.equals("monthly")) {
				date = formatterYearMonth.parse(dateString);
			} else {
				if (dateString.indexOf("-") == 4) {
					date = formatterMinus.parse(dateString);
				} else if (dateString.indexOf("/") > 0) {
					date = formatterSlash.parse(dateString);
				} else if (dateString.indexOf(",") > 0){
					date = longDateFormatter.parse(dateString);
				}	else{
					date = formatter.parse(dateString);
				}

			}

			return date;
		} else {
			return null;
		}
	}
	
  public static Date parseLastModifiedDate(String dateString) {
		
		if (dateString != null && !dateString.isEmpty()) {
			return lastModifiedDate.parse(dateString);
		}
		 else {
			return null;
		}
	}

	public static String formatDate(Date dateString, String periodType) {
		String date = null;

		if (periodType.equals("yearly")) {
			date = formatterYear.format(dateString);
		} else if (periodType.equals("monthly")) {
			date = formatterYearMonth.format(dateString);
		}
		else if ( periodType.equals("formatterMinusReverse")) {
			date = formatterMinusReverse.format(dateString);
		} 
		else {
			date = formatterMinus.format(dateString);
		}

		return date;
	}
	
	public static String getDatePattern(String periodType) {
		String pattern = null;

		if (periodType.equals("yearly")) {
			pattern = "yyyy";
		} else if (periodType.equals("monthly")) {
			pattern = "yyyy-MM";
		} else {
			pattern = "yyyy-MM-dd";
		}

		return pattern;
	}
	
	
	public static Date parseDate(String dateString) {
		if (dateString != null && !dateString.isEmpty()) {
			Date date = null;
			
				if("null".equals(dateString)) {
					return null;
				}
				else if ( dateString.indexOf("-") == 4 && dateString.length() == 7)
					date = formatterYearMonth.parse(dateString);
				else if ( dateString.indexOf("-") == 4 && dateString.length() == 10)
					date = formatterMinus.parse(dateString);
				else if (dateString.indexOf("-") == 4)
					date = formatterMinus.parse(dateString);
				else if (dateString.indexOf("-") == 2 && dateString.length() == 10)
					date = formatterMinusReverse.parse(dateString);
				else if (dateString.indexOf("/") > 0)
					date = formatterSlash.parse(dateString);
				// e.g. Wed Jan 01 00:00:00 CST 2003
				else if (dateString.length() == 4)
					date = formatterYear.parse(dateString);
				else if (dateString.indexOf(",") > 0)
					date = longDateFormatter.parse(dateString);
				else
					date = formatter.parse(dateString);
			
			return date;
		} else {
			return null;
		}
	}


}