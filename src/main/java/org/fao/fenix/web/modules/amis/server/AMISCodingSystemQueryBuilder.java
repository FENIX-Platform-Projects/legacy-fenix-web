package org.fao.fenix.web.modules.amis.server;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.fao.fenix.web.modules.amis.common.constants.AMISCurrentDatasetView;
import org.fao.fenix.web.modules.amis.common.vo.AMISQueryVO;
import org.fao.fenix.web.modules.amis.common.vo.ClientCbsDatasetResult;

public class AMISCodingSystemQueryBuilder {
	
	private final static Logger LOGGER = Logger.getLogger(AMISCodingSystemQueryBuilder.class);
	
    public static String getTableNameOfCodingSystem(AMISQueryVO qvo) {
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT tablename ");
		sql.append("FROM customdataset " );
		sql.append("WHERE ");
		sql.append("code = '"+qvo.getTypeOfOutput()+"' ");
			
		LOGGER.info("getTableNameOfCodingSystem sql: " + sql);
		return sql.toString();
	}
    
    public static String getYears(AMISQueryVO qvo, String tablename) {
		StringBuffer sql = new StringBuffer();
		
		sql.append(" SELECT DISTINCT(year) as code, CAST(EXTRACT(year from year) AS varchar) as label ");
		sql.append(" FROM "+tablename+" " );
		sql.append(" WHERE  " );
		sql.append(" EXTRACT(year from year) >= '2000' ");
		if(qvo.getSelectedDataset()!=null)
			sql.append(" AND database = '"+qvo.getSelectedDataset()+"'");
		sql.append(" ORDER BY CAST(EXTRACT(year from year) AS varchar) "+qvo.getSortingOrder());
		
		LOGGER.info("sql: " + sql);
		return sql.toString();
	}

    public static String getSplitYears(AMISQueryVO qvo, String tablename) {
        StringBuffer sql = new StringBuffer();

        sql.append(" SELECT DISTINCT(year_label) as code, year_label as label ");
        sql.append(" FROM "+tablename+" " );
        sql.append(" WHERE  " );
        sql.append(" EXTRACT(year from year) >= '2000' ");
        if(qvo.getSelectedDataset()!=null)
            sql.append(" AND database = '"+qvo.getSelectedDataset()+"'");
//        sql.append(" ORDER BY CAST((MID(year_label,1,4)) AS varchar) "+qvo.getSortingOrder());
        //substring (year_label from 1 for 4)
        sql.append(" ORDER BY year_label "+qvo.getSortingOrder());
        System.out.println("getSplitYears "+sql);

        LOGGER.info("sql: " + sql);
        return sql.toString();
    }
    
//    public static String getYearsLabel(AMISQueryVO qvo, String tablename) {
//		StringBuffer sql = new StringBuffer();
//		 //SELECT DISTINCT(year_label), substr(year_label, 0, 5)  FROM amis_all_datasources WHERE substr(year_label, 0, 5) >= '2000'  AND database = 'NATIONAL' ORDER BY substr(year_label, 0, 5) DESC
//		sql.append(" SELECT DISTINCT(year) as code, year_label as label ");
//		sql.append(" FROM "+tablename+" " );
//		sql.append(" WHERE  " );
//		sql.append(" EXTRACT(year from year) >= '2000' ");
//		if(qvo.getSelectedDataset()!=null)
//			sql.append(" AND database = '"+qvo.getSelectedDataset()+"'");
//		//sql.append(" ORDER BY CAST(EXTRACT(year from year) AS varchar) "+qvo.getSortingOrder());
//		sql.append(" ORDER BY year "+qvo.getSortingOrder());
//
//		LOGGER.info("sql: " + sql);
//		return sql.toString();
//	}
    //Start Year Label Changes
    public static String getYearsLabel(AMISQueryVO qvo, String tablename) {
        StringBuffer sql = new StringBuffer();
        //SELECT DISTINCT(year_label), substr(year_label, 0, 5)  FROM amis_all_datasources WHERE substr(year_label, 0, 5) >= '2000'  AND database = 'NATIONAL' ORDER BY substr(year_label, 0, 5) DESC
       // sql.append(" SELECT DISTINCT(year_label), substr(year_label, 0, 5) ");
        sql.append(" SELECT DISTINCT(year_label), year_label, substr(year_label, 0, 5) ");
        sql.append(" FROM "+tablename+" " );
        sql.append(" WHERE  " );
        sql.append(" substr(year_label, 0, 5) >= '2000' ");
        if(qvo.getSelectedDataset()!=null)
            sql.append(" AND database = '"+qvo.getSelectedDataset()+"'");
        //sql.append(" ORDER BY CAST(EXTRACT(year from year) AS varchar) "+qvo.getSortingOrder());
        sql.append(" ORDER BY substr(year_label, 0, 5) "+qvo.getSortingOrder());

        LOGGER.info("sql: " + sql);
        return sql.toString();
    }
    //End Year Label Changes
   /** public static String getDefaultCBSYears(AMISQueryVO qvo, String tablename) {
		StringBuffer sql = new StringBuffer();
		
		sql.append(" SELECT DISTINCT(year) as code, CAST(EXTRACT(year from year) AS varchar) as label ");
		sql.append(" FROM "+tablename+" " );
		sql.append(" WHERE  " );
		sql.append(" EXTRACT(year from year) >= '2000' ");
		sql.append(" AND database = 'CBS'");
		sql.append(" ORDER BY CAST(EXTRACT(year from year) AS varchar) "+qvo.getSortingOrder());
		
		LOGGER.info("sql: " + sql);
		return sql.toString();
	}**/
   //Start Year Label Changes
   public static String getDefaultCBSYearsLabel(AMISQueryVO qvo, String tablename) {
       StringBuffer sql = new StringBuffer();
       //SELECT DISTINCT(year_label), substr(year_label, 0, 5)  FROM amis_all_datasources WHERE substr(year_label, 0, 5) >= '2000'  AND database = 'NATIONAL' ORDER BY substr(year_label, 0, 5) DESC
       //sql.append(" SELECT DISTINCT(year_label), substr(year_label, 0, 5) ");
       sql.append(" SELECT DISTINCT(year_label), year_label, substr(year_label, 0, 5) ");
       sql.append(" FROM "+tablename+" " );
       sql.append(" WHERE  " );
       sql.append(" substr(year_label, 0, 5) >= '2000' ");
       if(qvo.getSelectedDataset()!=null)
           sql.append(" AND database = 'CBS'");
       //sql.append(" ORDER BY CAST(EXTRACT(year from year) AS varchar) "+qvo.getSortingOrder());
       sql.append(" ORDER BY substr(year_label, 0, 5) "+qvo.getSortingOrder());

       LOGGER.info("sql: " + sql);
       return sql.toString();
   }
    //End Year Label Changes

//    public static String getDefaultCBSYearsLabel(AMISQueryVO qvo, String tablename) {
//		StringBuffer sql = new StringBuffer();
//
//		sql.append(" SELECT DISTINCT(year) as code, year_label as label ");
//		sql.append(" FROM "+tablename+" " );
//		sql.append(" WHERE  " );
//		sql.append(" EXTRACT(year from year) >= '2000' ");
//		sql.append(" AND database = 'CBS'");
//		//sql.append(" ORDER BY CAST(EXTRACT(year from year) AS varchar) "+qvo.getSortingOrder());
//		sql.append(" ORDER BY year "+qvo.getSortingOrder());
//
//		LOGGER.info("sql: " + sql);
//		return sql.toString();
//	}
    
    public static String getRangeOfYears(AMISQueryVO qvo, String tablename) {
		StringBuffer sql = new StringBuffer();
		
		sql.append(" SELECT DISTINCT(year) as code, CAST(EXTRACT(year from year) AS varchar) as label, month ");
		sql.append(" FROM "+tablename+" " );
		sql.append(" WHERE  " );
		sql.append(" Database = '"+qvo.getSelectedDataset()+"' AND Region_Code= '"+qvo.getCountryCode()+"' AND Product_Code = '"+qvo.getCommodityCode()+"'  " );
		//sql.append(" EXTRACT(year from year) >= '1990' ");
		sql.append(" ORDER BY CAST(EXTRACT(year from year) AS varchar) "+qvo.getSortingOrder());
	//	SELECT DISTINCT(year) as code, CAST(EXTRACT(year from year) AS varchar) as label, month from  where Database = 'CBS' AND Region_Code= '264' AND Product_Code = '4' ORDER BY CAST(EXTRACT(year from year) AS varchar) ASC;
		//SELECT DISTINCT(year) as code, CAST(EXTRACT(year from year) AS varchar) as label, month  FROM AMIS_DATA_ARGENTINA_DATASOURCE_b2a3b135  WHERE   Database = 'AMIS Data Argentina DataSource' AND Region_Code= '12' AND Product_Code = '1'   ORDER BY CAST(EXTRACT(year from year) AS varchar) ASC; 
	//	SELECT region_name, product_name, year, month FROM AMIS_DATA_ARGENTINA_DATASOURCE_b2a3b135  WHERE   Database = 'AMIS Data Argentina DataSource' AND Region_Code= '12' AND Product_Code = '1';
		LOGGER.info("sql: " + sql);
		return sql.toString();
	}
    
    public static String getRangeOfYearsWithoutMonth(AMISQueryVO qvo, String tablename) {
		StringBuffer sql = new StringBuffer();
		
		sql.append(" SELECT DISTINCT(year) as code, CAST(EXTRACT(year from year) AS varchar) as label ");
		sql.append(" FROM "+tablename+" " );
		sql.append(" WHERE  " );
		sql.append(" Database = '"+qvo.getSelectedDataset()+"' AND Region_Code= '"+qvo.getCountryCode()+"' AND Product_Code = '"+qvo.getCommodityCode()+"'  " );
		//sql.append(" EXTRACT(year from year) >= '1990' ");
		sql.append(" ORDER BY CAST(EXTRACT(year from year) AS varchar) "+qvo.getSortingOrder());
	//	SELECT DISTINCT(year) as code, CAST(EXTRACT(year from year) AS varchar) as label FROM AMIS_DATA_ARGENTINA_DATASOURCE_b2a3b135  WHERE   Database = 'AMIS Data Argentina DataSource' AND Region_Code= '12' AND Product_Code = '1';
		LOGGER.info("getRangeOfYearsWithoutMonth sql: " + sql);
		return sql.toString();
	}
    
    public static String getTimeSeriesForMonth(AMISQueryVO qvo, String oneElementName, String tablename) {
		StringBuffer sql = new StringBuffer();
		
		sql.append(" SELECT month, year as code, CAST(EXTRACT(year from year) AS varchar) as label ");
		sql.append(" FROM "+tablename+" " );
		sql.append(" WHERE  " );
		//sql.append(" Database = '"+qvo.getSelectedDataset()+"' AND Region_Code= '"+qvo.getCountryCode()+"' AND Product_Code = '"+qvo.getCommodityCode()+"' AND Element_Code = '"+qvo.getElementCode()+"'  " );
		sql.append(" Database = '"+qvo.getSelectedDataset()+"' AND Region_Code= '"+qvo.getCountryCode()+"' AND Product_Code = '"+qvo.getCommodityCode()+"'  AND Element_Name = '"+oneElementName+"'  AND Value_Type = '"+qvo.getValueType()+"' ");
		//sql.append(" EXTRACT(year from year) >= '1990' ");
		sql.append(" ORDER BY CAST(EXTRACT(year from year) AS varchar) "+qvo.getSortingOrder());
	//	SELECT month, year as code, CAST(EXTRACT(year from year) AS varchar) as label from AMIS_DATA_f6167527 where Database = 'CBS' AND Region_Code= '12' AND Product_Code = '1' AND Element_Name= 'Production' AND Value_Type = 'TOTAL' ORDER BY CAST(EXTRACT(year from year) AS varchar) ASC;
		LOGGER.info("getTimeSeriesForMonth sql: " + sql);
		return sql.toString();
	}
    
  public static String getDataFromCountryDataset(AMISQueryVO qvo, String tablename) {
		StringBuffer sql = new StringBuffer();
		
		sql.append(" SELECT *");
		sql.append(" FROM "+tablename+" " );
		sql.append(" WHERE  " );
		sql.append(" Database = '"+qvo.getSelectedDataset() + "'");
		LOGGER.info("getDataFromCountryDataset sql: " + sql);
		return sql.toString();
	}
  
  public static String getOpeningClosingStocksForYear(AMISQueryVO qvo, String closingStocksElementCode, String tablename) {
		StringBuffer sql = new StringBuffer();
		
		sql.append(" SELECT year as code, CAST(EXTRACT(year from year) AS varchar), element_code, value, month");
		sql.append(" FROM "+tablename+" " );
		sql.append(" WHERE  " );
		//qvo.getElementCode() contains the code for opening stocks, while the code for closingStocks is in closingStocksElementCode
		sql.append(" Database = '"+qvo.getSelectedDataset() + "' AND region_code = '"+qvo.getCountryCode()+"' AND product_code = '" +qvo.getCommodityCode()+"' AND element_code IN('"+qvo.getElementCode()+"','"+closingStocksElementCode+"')");
		LOGGER.info("getOpeningClosingStocksForYear sql: " + sql);
		return sql.toString();
		//SELECT year as code, CAST(EXTRACT(year from year) AS varchar), element_name, value from AMIS_DATA_ea4d0ff4 where database = 'CBS' AND region_code = '202' AND product_code = '1' AND element_code = '16';
	}
    
    public static String getMarketingYearCountryInformation(AMISQueryVO qvo, String tablename, String country_code, String commodity_code) {
		StringBuffer sql = new StringBuffer();
		
		sql.append(" SELECT product_name, start_month");
		sql.append(" FROM "+tablename+" " );
		sql.append(" WHERE  " );
		sql.append(" region_code ='"+country_code+"' AND product_code='"+commodity_code+"'");
		//select product_name, start_month from AMIS_MARKETING_YEAR_37ca0c98 where region_code ='Argentina' AND product_code='1';
		LOGGER.info("getMarketingYearCountryInformation sql: " + sql);
		return sql.toString();
	}
    
    public static String getCropsNumCountryInformation(AMISQueryVO qvo, String tablename, String country_code, String commodity_code) {
		StringBuffer sql = new StringBuffer();
		
		sql.append(" SELECT product_name, crops_num");
		sql.append(" FROM "+tablename+" " );
		sql.append(" WHERE  " );
		sql.append(" region_code ='"+country_code+"' AND product_code='"+commodity_code+"'");
		//select product_name, crops_num from AMIS_COMMODITIES_CROP_c362107a where region_code = 'Argentina' AND product_code='1';
		LOGGER.info("getCropsNumCountryInformation sql: " + sql);
		return sql.toString();
	}
    
    public static String fillCountryDataset(AMISQueryVO qvo, String tablename, int rowIndex) {
		StringBuffer sql = new StringBuffer();
		List<Object[]> cbsData = qvo.getCbsResult();
		int dim = cbsData.get(rowIndex).length;
		sql.append("INSERT INTO "+ tablename);
		//sql.append(" (database, region_code, region_name, product_code, product_name, element_code, element_name, units, year, month, value, flag, value_type, last_update) ");
		sql.append(" VALUES ('");
//		Format formatter = new SimpleDateFormat("yyyy-mm-dd");
//		String s = formatter.format(d);

		for(int i = 0; i< dim; i++)
		{
			sql.append(cbsData.get(rowIndex)[i]);
			if(i<dim -1)
			{
				sql.append("', '");
			}
			else
			{
				//After the last value
				//sql.append("', '"+s+"')");
				sql.append("',CURRENT_DATE)");
			}
		}
		LOGGER.info("fillCountryDataset rowIndex = "+ rowIndex+ " sql: " + sql);
		return sql.toString();
	}
    
    public static String insertElementInCountryDataset(AMISQueryVO qvo, String tablename) {
		StringBuffer sql = new StringBuffer();		
		String year = qvo.getYear().substring(0,4)+ "-01-01";
		sql.append("INSERT INTO "+ tablename);
		sql.append(" (database, region_code, region_name, product_code, product_name, element_code, element_name, units, year, month, value, flag, value_type, last_update) ");
		String value = qvo.getValue();
		if((value==null)||(value.equals("")))
		{
			value = "0.0";
//			sql.append(" (database, region_code, region_name, product_code, product_name, element_code, element_name, units, year, month, flag, value_type, last_update) ");
//			sql.append("VALUES ('"+qvo.getDatabase() +"', '"+qvo.getCountryCode() +"','"+qvo.getCountryName() +"','"+qvo.getCommodityCode() +"','"+qvo.getCommodityName() +"','"+qvo.getElementCode() +"','"+qvo.getElementName() +"','"+qvo.getUnit() +"','"+year +"','"+qvo.getMonth() +"','"+qvo.getFlag() +"','"+qvo.getValueType() +"',CURRENT_DATE)");
		}
//		else
//		{
//			sql.append(" (database, region_code, region_name, product_code, product_name, element_code, element_name, units, year, month, value, flag, value_type, last_update) ");
//			sql.append("VALUES ('"+qvo.getDatabase() +"', '"+qvo.getCountryCode() +"','"+qvo.getCountryName() +"','"+qvo.getCommodityCode() +"','"+qvo.getCommodityName() +"','"+qvo.getElementCode() +"','"+qvo.getElementName() +"','"+qvo.getUnit() +"','"+year +"','"+qvo.getMonth() +"','"+value +"','"+qvo.getFlag() +"','"+qvo.getValueType() +"',CURRENT_DATE)");
//		}
		sql.append("VALUES ('"+qvo.getDatabase() +"', '"+qvo.getCountryCode() +"','"+qvo.getCountryName() +"','"+qvo.getCommodityCode() +"','"+qvo.getCommodityName() +"','"+qvo.getElementCode() +"','"+qvo.getElementName() +"','"+qvo.getUnit() +"','"+year +"','"+qvo.getMonth() +"','"+value +"','"+qvo.getFlag() +"','"+qvo.getValueType() +"',CURRENT_DATE)");
		LOGGER.info("insertElementInCountryDataset sql = "+ sql);
		return sql.toString();
	}
    
    public static String overwriteElement(AMISQueryVO qvo, ClientCbsDatasetResult clientCbsDatasetResultObject, String tablename) {
		StringBuffer sql = new StringBuffer();
		String year = qvo.getYear().substring(0,4)+ "-01-01";
		sql.append("UPDATE "+ tablename + " ");
		String value = qvo.getValue();
		if((value==null)||(value.equals("")))
		{
		//	LOGGER.info("overwriteElement if((value==null)||(value.equals()): ");
			value = "0.0";
		}
		sql.append("SET value='"+ value + "', flag='"+qvo.getFlag()+"' ");
		sql.append("WHERE ");
		sql.append("database = '"+qvo.getDatabase() + "' AND ");
		sql.append("region_code = '"+qvo.getCountryCode()+ "' AND ");
		sql.append("region_name = '"+qvo.getCountryName()+ "' AND ");
		sql.append("product_code = '"+qvo.getCommodityCode()+ "' AND ");
		sql.append("product_name = '"+qvo.getCommodityName()+ "' AND ");
		sql.append("element_code = '"+qvo.getElementCode()+ "' AND ");
		sql.append("element_name = '"+qvo.getElementName()+ "' AND ");
		sql.append("units = '"+qvo.getUnit()+ "' AND ");
		sql.append("year = '"+year+ "' AND ");
		if((qvo.getMonth()!=null)&&(!qvo.getMonth().equals("")))
		{
			sql.append("month = '"+qvo.getMonth()+ "' AND ");
		}
		value = clientCbsDatasetResultObject.getValue();
		if((value==null)||(value.equals("")))
		{
			//LOGGER.info("overwriteElement if((value==null)||(value.equals()): ");
			value = "0.0";
		}
		else
		{
			//LOGGER.info("overwriteElement else *"+ value+ "*");
		}
		sql.append("value = '"+value+ "' AND ");
		sql.append("flag = '"+clientCbsDatasetResultObject.getAnnotation()+ "' AND ");
		sql.append("value_type = '"+qvo.getValueType()+ "' ");
		LOGGER.info("c" + sql);
		return sql.toString();
	}
   
    public static String overwriteForecastElements(AMISQueryVO qvo, ClientCbsDatasetResult clientCbsDatasetResultObject, String tablename) {
		StringBuffer sql = new StringBuffer();
		String year = qvo.getYear().substring(0,4)+ "-01-01";
		sql.append("UPDATE "+ tablename + " ");
		String value = qvo.getValue();
		if((value==null)||(value.equals("")))
		{
		//	LOGGER.info("overwriteElement if((value==null)||(value.equals()): ");
			value = "0.0";
		}
		sql.append("SET value='"+ value + "', flag='"+qvo.getFlag()+"' ");
		sql.append("WHERE ");
		sql.append("database = '"+qvo.getDatabase() + "' AND ");
		sql.append("region_code = '"+qvo.getCountryCode()+ "' AND ");
		sql.append("region_name = '"+qvo.getCountryName()+ "' AND ");
		sql.append("product_code = '"+qvo.getCommodityCode()+ "' AND ");
		sql.append("product_name = '"+qvo.getCommodityName()+ "' AND ");
		sql.append("element_code = '"+qvo.getElementCode()+ "' AND ");
		sql.append("element_name = '"+qvo.getElementName()+ "' AND ");
		sql.append("units = '"+qvo.getUnit()+ "' AND ");
		sql.append("year = '"+year+ "' AND ");
//		if((qvo.getMonth()!=null)&&(!qvo.getMonth().equals("")))
//		{
//			sql.append("month = '"+qvo.getMonth()+ "' AND ");
//		}
		value = clientCbsDatasetResultObject.getValue();
		if((value==null)||(value.equals("")))
		{
			//LOGGER.info("overwriteElement if((value==null)||(value.equals()): ");
			value = "0.0";
		}
		else
		{
			//LOGGER.info("overwriteElement else *"+ value+ "*");
		}
		//sql.append("value = '"+value+ "' AND ");
		//sql.append("flag = '"+clientCbsDatasetResultObject.getAnnotation()+ "' AND ");
		sql.append("value_type = '"+qvo.getValueType()+ "' ");
		LOGGER.info("overwriteForecastElements sql: " + sql);
		return sql.toString();
	}
    
    public static String getTimeSeries(AMISQueryVO qvo, String tablename, String yearKey, String month, int commodityCode, boolean isPopulation) {
		StringBuffer sql = new StringBuffer();
		//commodityCode is used only if di element is Population(isPopulation= true)
		sql.append(" SELECT Element_Name, Element_Code, value, flag, month, year as code, CAST(EXTRACT(year from year) AS varchar) as label ");
		sql.append(" FROM "+tablename+" " );
		sql.append(" WHERE  " );
		//sql.append(" Database = '"+qvo.getSelectedDataset()+"' AND Region_Code= '"+qvo.getCountryCode()+"' AND Product_Code = '"+qvo.getCommodityCode()+"' AND Element_Code = '"+qvo.getElementCode()+"'  " );
		if((yearKey != null)&&(month != null))
		{
			if(isPopulation)
			{
				sql.append(" Database = '"+qvo.getSelectedDataset()+"' AND Region_Code= '"+qvo.getCountryCode()+"' AND Product_Code = '"+commodityCode+"'  AND Year = '"+yearKey+"-01-01'  AND Month = '"+month+"'  AND Value_Type = '"+qvo.getValueType()+"' ");
			}
			else
			{
				sql.append(" Database = '"+qvo.getSelectedDataset()+"' AND Region_Code= '"+qvo.getCountryCode()+"' AND Product_Code = '"+qvo.getCommodityCode()+"'  AND Year = '"+yearKey+"-01-01'  AND Month = '"+month+"'  AND Value_Type = '"+qvo.getValueType()+"' ");
			}
		}
		else
		{
			if(isPopulation)
			{
				sql.append(" Database = '"+qvo.getSelectedDataset()+"' AND Region_Code= '"+qvo.getCountryCode()+"' AND Product_Code = '"+commodityCode+"'  AND Value_Type = '"+qvo.getValueType()+"' ");
			}
			else
			{
				sql.append(" Database = '"+qvo.getSelectedDataset()+"' AND Region_Code= '"+qvo.getCountryCode()+"' AND Product_Code = '"+qvo.getCommodityCode()+"'  AND Value_Type = '"+qvo.getValueType()+"' ");
			}
		}
		//sql.append(" EXTRACT(year from year) >= '1990' ");
		sql.append(" ORDER BY CAST(EXTRACT(year from year) AS varchar) "+qvo.getSortingOrder());
	//	SELECT Element_Code, value, flag, month, year as code, CAST(EXTRACT(year from year) AS varchar) as label from AMIS_DATA_6d2e069c where Database = 'PSD' AND Region_Code= '12' AND Product_Code = '1' AND Element_Code= '2'  ORDER BY CAST(EXTRACT(year from year) AS varchar) ASC;
		//SELECT Element_Name, Element_Code, value, flag, month, year as code, CAST(EXTRACT(year from year) AS varchar) as label from AMIS_DATA_4297ed15 where Database = 'CBS' AND Region_Code= '12' AND Product_Code = '1' AND Year ='2000-01-01' ORDER BY CAST(EXTRACT(year from year) AS varchar) ASC;
		LOGGER.info("getTimeSeries sql: " + sql);
		return sql.toString();
	}
    
    public static String getSubElementsTimeSeries(AMISQueryVO qvo, String tablename, String yearKey, String month) {
		StringBuffer sql = new StringBuffer();
		
		sql.append(" SELECT Region_Name, Product_Name, Element_Name, Element_Code, Units, value, flag, month, Value_Type, year as code, CAST(EXTRACT(year from year) AS varchar) as label ");
		sql.append(" FROM "+tablename+" " );
		sql.append(" WHERE  " );
		//sql.append(" Database = '"+qvo.getSelectedDataset()+"' AND Region_Code= '"+qvo.getCountryCode()+"' AND Product_Code = '"+qvo.getCommodityCode()+"' AND Element_Code = '"+qvo.getElementCode()+"'  " );
		if((yearKey != null)&&(month != null))
		{
			sql.append(" Database = '"+qvo.getSelectedDataset()+"' AND Region_Code= '"+qvo.getCountryCode()+"' AND Product_Code = '"+qvo.getCommodityCode()+"'  AND Year = '"+yearKey+"-01-01 "+"'  AND Month = '"+month+"'  AND Value_Type != '"+qvo.getValueType()+"' ");
		}
		else
		{
			sql.append(" Database = '"+qvo.getSelectedDataset()+"' AND Region_Code= '"+qvo.getCountryCode()+"' AND Product_Code = '"+qvo.getCommodityCode()+"'  AND Value_Type != '"+qvo.getValueType()+"' ");
		}
		//sql.append(" EXTRACT(year from year) >= '1990' ");
		sql.append(" ORDER BY CAST(EXTRACT(year from year) AS varchar) "+qvo.getSortingOrder());
	//	SELECT Element_Code, value, flag, month, year as code, CAST(EXTRACT(year from year) AS varchar) as label from AMIS_DATA_6d2e069c where Database = 'PSD' AND Region_Code= '12' AND Product_Code = '1' AND Element_Code= '2'  ORDER BY CAST(EXTRACT(year from year) AS varchar) ASC;
		//SELECT Element_Name, Element_Code, value, flag, month, year as code, CAST(EXTRACT(year from year) AS varchar) as label from AMIS_DATA_6d2e069c where Database = 'PSD' AND Region_Code= '12' AND Product_Code = '1' ORDER BY CAST(EXTRACT(year from year) AS varchar) ASC;
		LOGGER.info("sql getSubElementsTimeSeries: " + sql);
		return sql.toString();
	}
    
 public static String getTableNameOfCodingSystem(String codingSystemCode) {
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT tablename ");
		sql.append("FROM customdataset " );
		sql.append("WHERE ");
		sql.append("code = '"+codingSystemCode+"' ");
			
		LOGGER.info("getTableNameOfCodingSystem sql: " + sql);
		return sql.toString();
	}
  

   public static String getG20CountriesQuery(AMISQueryVO qvo, String tableName) {
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT region_code, region_name ");
		sql.append("FROM "+tableName+" " );
		if(qvo.getSelectedDataset()!=null) {
			sql.append("WHERE ");
			if(qvo.getSelectedDataset().equals(AMISCurrentDatasetView.AMIS.toString()) || qvo.getSelectedDataset().equals(AMISCurrentDatasetView.NATIONAL.toString()))
				sql.append("coun_code_cbs is not null ");
			else
				sql.append("coun_code_"+qvo.getSelectedDataset().toLowerCase()+" is not null ");
			
			if(!qvo.getSelectedDataset().equals(AMISCurrentDatasetView.FAOSTAT.toString()) && qvo.getCodeList()!=null && !qvo.getCodeList().isEmpty()){

				sql.append(" AND region_code IN ( ");
				int i = 0;
				 for(String code: qvo.getCodeList()) {
		        		sql.append("'"+code+"'");
		        		
		        		if (i < qvo.getCodeList().size() - 1)
							sql.append(", ");
		        		
		        		i++;
				 }	
				sql.append(") ");
			}


		}
		sql.append("ORDER BY region_name");
		System.out.println("getG20CountriesQuery sql: " + sql);
		LOGGER.info("getG20CountriesQuery sql: " + sql);
		return sql.toString();
	}

    public static String getG20NationalCountriesQuery(AMISQueryVO qvo, String tableName) {

        StringBuffer sql = new StringBuffer();
        sql.append("SELECT region_code, region_name ");
        sql.append("FROM "+tableName+" " );
        sql.append("WHERE ");
        sql.append("region_name IN ( ");
        sql.append("SELECT (regexp_split_to_array(code, 'AMIS_COUNTRY_DATA_'))[2] as region_name from customdataset WHERE code like 'AMIS_COUNTRY_DATA_%' ");
        sql.append(" ) ");

        sql.append("ORDER BY region_name");

        LOGGER.info("getG20NationalCountriesQuery sql: " + sql);
        System.out.println("getG20NationalCountriesQuery sql: " + sql);
        return sql.toString();
    }


   public static String getG20TotalCountriesQuery(AMISQueryVO qvo, String tableName) {
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT DISTINCT(region_code), region_name ");
		sql.append("FROM "+tableName+" " );
		sql.append("WHERE ");
		sql.append("region_code is not null ");
		sql.append("ORDER BY region_name");
			
		//SELECT DISTINCT(region_code), region_name FROM AMIS_DATA_e4a05ad6 ORDER BY region_name;
       System.out.println("getG20TotalCountriesQuery sql: " + sql);
		LOGGER.info("getG20TotalCountriesQuery sql: " + sql);
		return sql.toString();
	}
   
   public static String getG20CountriesWithCropsQuery(AMISQueryVO qvo, String tableNameCountry, String tableNameCrops) {
	   	StringBuffer sqlInner = new StringBuffer();
		sqlInner.append("SELECT region_code ");
		sqlInner.append("FROM "+tableNameCountry+" " );
		sqlInner.append("WHERE ");
		if(qvo.getCountryName()!=null)
		{
			sqlInner.append("region_name = '"+qvo.getCountryName()+ "'");
		}
		else
		{
		sqlInner.append("coun_code_"+qvo.getSelectedDataset().toLowerCase()+" is not null ");
		}
	//	sqlInner.append("coun_code_"+qvo.getSelectedDataset().toLowerCase()+" is not null ");
		
		//sqlInner.append("region_name IN (");
//		for()
//			sqlInner.append("
		//+");
		//IN ('Argentina', 'Viet NAm')
		//sqlInner.append("ORDER BY region_code");
	   
	   
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT DISTINCT region_code, region_name ");
		sql.append("FROM "+tableNameCrops+" " );
		sql.append("WHERE ");
		sql.append("region_code IN (");
		sql.append(sqlInner);
		sql.append(")");
		sql.append("ORDER BY region_name");

       System.out.println("getG20CountriesWithCropsQuery sql: " + sql);
		LOGGER.info("getG20CountriesWithCropsQuery sql: " + sql);
		return sql.toString();
	}
   
   public static String getProductsWithCropsQuery(AMISQueryVO qvo, String tableNameProduct, String tableNameCrops) {
	   	StringBuffer sqlInner = new StringBuffer();
		sqlInner.append("SELECT product_code ");
		sqlInner.append("FROM "+tableNameProduct+" " );
		sqlInner.append("WHERE ");
		sqlInner.append("product_code is not null ");
		//sqlInner.append("ORDER BY product_code");
	   
	   
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT DISTINCT product_code, product_name ");
		sql.append("FROM "+tableNameCrops+" " );
		sql.append("WHERE Region_Code = '"+qvo.getCountryCode()+ "'");
		sql.append(" AND product_code IN (");
		sql.append(sqlInner);
		sql.append(")");
		sql.append("ORDER BY product_name");
			
		LOGGER.info("getProductsWithCropsQuery sql: " + sql);
		return sql.toString();
	}
   
   public static String getCropsNumQuery(AMISQueryVO qvo, String tableNameCrops) {  
	   
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT product_name, crops_num ");
		sql.append("FROM "+tableNameCrops+" " );
		sql.append("WHERE Region_Code = '"+qvo.getCountryCode()+ "'");
		sql.append(" AND product_code = '"+qvo.getCommodityCode()+ "'");
			
		LOGGER.info("getCropsNumQuery sql: " + sql);
		return sql.toString();
	}
   
   public static String getMarketingYearQuery(AMISQueryVO qvo, String tableNameMarketingYear) {  
	   
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT start_month, start_month ");
		sql.append("FROM "+tableNameMarketingYear+" " );
		sql.append("WHERE Region_Code = '"+qvo.getCountryCode()+ "'");
		sql.append(" AND product_code = '"+qvo.getCommodityCode()+ "'");
			
		LOGGER.info("getMarketingYearQuery sql: " + sql);
		return sql.toString();
	}
   
   public static String getDatabaseQuery(AMISQueryVO qvo, String tableName) {
	   
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT DISTINCT Database, Database ");
		sql.append("FROM "+tableName+" " );
		sql.append("ORDER BY Database");
			
		LOGGER.info("getDatabaseQuery sql: " + sql);
		return sql.toString();
	}
   
   public static String getElementsWithMeasurementUnits(AMISQueryVO qvo, String tableName) {
	   
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT DISTINCT element_name, units ");
		sql.append("FROM "+tableName+" " );		
		
		sql.append(" ORDER BY units ");

		LOGGER.info("sql: " + sql);
		return sql.toString();
	}
   
   public static String getElementsWithCodes(AMISQueryVO qvo, String tableName) {
	   
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT DISTINCT element_name, element_code");
		sql.append("FROM "+tableName+" " );		
		
		sql.append(" ORDER BY units ");

		LOGGER.info("sql: " + sql);
		return sql.toString();
	}
   
//   public static String getFlags(AMISQueryVO qvo, String tableName) {
//	   
//		StringBuffer sql = new StringBuffer();
//		sql.append("SELECT DISTINCT flag, flag ");
//		sql.append("FROM "+tableName+" " );		
//		sql.append("WHERE ");
//		sql.append("flag is not null ");
//		sql.append(" ORDER BY flag ");
//
//		LOGGER.info("sql: " + sql);
//		return sql.toString();
//	}
   
   public static String getFlags(AMISQueryVO qvo, String tableName) {
	   
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT Flag_Code, Flag_Description ");
		sql.append("FROM "+tableName+" " );		
		sql.append("WHERE ");
		sql.append("Flag_Description is not null ");
		sql.append(" ORDER BY Flag_Code ");

		LOGGER.info("sql: " + sql);
		return sql.toString();
	}
   
	 public static String getProductsQuery(AMISQueryVO qvo, String tableName) {
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT product_code, product_name ");
		sql.append("FROM "+tableName+" " );
//		if(qvo.getSelectedDataset()!=null) {
//			sql.append("WHERE ");
//			if(qvo.getSelectedDataset().equals(AMISCurrentDatasetView.AMIS.toString()) || qvo.getSelectedDataset().equals(AMISCurrentDatasetView.NATIONAL.toString()))
//				sql.append("prod_code_cbs is not null ");
//			else
//				sql.append("prod_code_"+qvo.getSelectedDataset().toLowerCase()+" is not null ");
//		}

        sql.append("WHERE product_code is not null ");
		sql.append("ORDER BY product_order");
			
		LOGGER.info("getProductsQuery sql: " + sql);
	
		return sql.toString();
	}

    public static String getProductsQueryCompare(AMISQueryVO qvo, String tableName) {

        StringBuffer sql = new StringBuffer();
        sql.append("SELECT product_code, product_name ");
        sql.append("FROM "+tableName+" " );
//        if(qvo.getSelectedDataset()!=null) {
//            sql.append("WHERE ");
//            if(qvo.getSelectedDataset().equals(AMISCurrentDatasetView.AMIS.toString()) || qvo.getSelectedDataset().equals(AMISCurrentDatasetView.NATIONAL.toString()))
//                sql.append("prod_code_cbs is not null AND prod_code_cbs <> '9' ");
//            else
//                sql.append("prod_code_"+qvo.getSelectedDataset().toLowerCase()+" is not null AND prod_code_"+qvo.getSelectedDataset().toLowerCase()+" <> '9' ");
//        }
//        else
//        {
//            sql.append("WHERE product_code <> '9' ");
//        }
        sql.append("WHERE product_code is not null AND product_code <> '9' ");
        sql.append(" ORDER BY product_order");

        LOGGER.info("getProductsQueryCompare sql: " + sql);

        return sql.toString();
    }

	 public static String getProductsWithoutPopulationQuery(AMISQueryVO qvo, String tableName) {
			
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT product_code, product_name ");
			sql.append("FROM "+tableName+" " );
	        sql.append("WHERE ");
            sql.append("product_code is not null ");
			sql.append(" AND product_code NOT IN ('9','3','2', '10') "); // 9 = Population
			sql.append("ORDER BY product_order");
				
			LOGGER.info("getProductsWithoutPopulationQuery sql: " + sql);
		
			return sql.toString();
		}	 
	 
	 public static String getTotalProductsQuery(AMISQueryVO qvo, String tableName) {
			
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT product_code, product_name ");
			sql.append("FROM "+tableName+" " );
			sql.append("WHERE ");
			sql.append("product_code is not null ");
			
			sql.append("ORDER BY product_order");
				
			LOGGER.info("getTotalProductsQuery sql: " + sql);
		
			return sql.toString();
		}
	 
		 public static String getElementsByProductQueryOld(AMISQueryVO qvo, String table1, String table2) {

		 /*
		  * returns only in-common elements for the selected products
		  */

		 /**
		  * 
		  * select n1.element_code, n1.element_name || ' (' || n1.units || ')' as element_name
FROM
(select e.element_code, e.element_name, e.units from AMIS_PRODUCT_ELEMENTS_TABLE_8817d23e pe, AMIS_ELEMENTS_fb373035 e where product_code in ('1') and  pe.element_code=e.element_code) n1
INNER JOIN 
(select e.element_code, e.element_name, e.units from AMIS_PRODUCT_ELEMENTS_TABLE_8817d23e pe, AMIS_ELEMENTS_fb373035 e where product_code in ('2') and  pe.element_code=e.element_code) n2
ON n1.element_code = n2.element_code
INNER JOIN 
(select e.element_code, e.element_name, e.units from AMIS_PRODUCT_ELEMENTS_TABLE_8817d23e pe, AMIS_ELEMENTS_fb373035 e where product_code in ('4') and  pe.element_code=e.element_code) n3
ON n3.element_code = n2.element_code
ORDER BY element_name;
		  */

		 List<String> queries = new ArrayList<String>();

		 int counter = 0;
		 for(String code: qvo.getItems().keySet()){
			 queries.add("(SELECT e.element_code, e.element_name,  e.units, e.databases FROM "+table1 + ", "+table2+" WHERE product_code = '"+code+"' AND pe.element_code=e.element_code) q"+counter);
		     counter++;
		 }

		 StringBuffer sql = new StringBuffer();
		 sql.append("SELECT q0.element_code, q0.element_name || ' (' || q0.units || ')' as element_name ");
		 sql.append("FROM " );
		 sql.append(queries.get(0));

		 if(queries.size() > 1) {
			 for(int i = 1; i < queries.size(); i++){
				 sql.append(" INNER JOIN ");
				 sql.append(queries.get(i));
				 sql.append(" ON q"+(i-1)+".element_code = q"+i+".element_code ");	
			 }
		 }
		 sql.append(" WHERE q0.databases is not null ");
		 sql.append(" ORDER BY element_name" );


		 LOGGER.info("getElementsByProductQueryOld sql: " + sql);

		 return sql.toString();
	 }
		 
		 public static String getElementsByProductQuery(AMISQueryVO qvo, String table1, String table2) {

			 /*
			  * returns only in-common elements for the selected products
			  */

			 /**
			  * 
			  * select n1.element_code, n1.element_name || ' (' || n1.units || ')' as element_name
	FROM
	(select e.element_code, e.element_name, e.units from AMIS_PRODUCT_ELEMENTS_TABLE_8817d23e pe, AMIS_ELEMENTS_fb373035 e where product_code in ('1') and  pe.element_code=e.element_code) n1
	INNER JOIN 
	(select e.element_code, e.element_name, e.units from AMIS_PRODUCT_ELEMENTS_TABLE_8817d23e pe, AMIS_ELEMENTS_fb373035 e where product_code in ('2') and  pe.element_code=e.element_code) n2
	ON n1.element_code = n2.element_code
	INNER JOIN 
	(select e.element_code, e.element_name, e.units from AMIS_PRODUCT_ELEMENTS_TABLE_8817d23e pe, AMIS_ELEMENTS_fb373035 e where product_code in ('4') and  pe.element_code=e.element_code) n3
	ON n3.element_code = n2.element_code
	ORDER BY element_name;
			  */

			 List<String> queries = new ArrayList<String>();

			 int counter = 0;
			 for(String code: qvo.getItems().keySet()){
//				 queries.add("(SELECT e.element_code, e.element_name,  e.units, e.databases, e.is_aggregated, e.element_order FROM "+table1 + ", "+table2+" WHERE product_code = '"+code+"' AND pe.element_code=e.element_code) q"+counter);
				//To remove Governament Stocks
				 queries.add("(SELECT e.element_code, e.element_name,  e.units, e.databases, e.is_aggregated, e.element_order FROM "+table1 + ", "+table2+" WHERE product_code = '"+code+"' AND pe.element_code=e.element_code AND e.element_code not in ('17')) q"+counter);
			     counter++;
			 }

			 StringBuffer sql = new StringBuffer();
			 sql.append("SELECT q0.element_code, q0.element_name, ' (' || q0.units || ')' as units_databases, ");
			 sql.append("CASE WHEN q0.is_aggregated = 'TRUE' THEN 'TRUE' ELSE 'FALSE' END AS is_aggregated ");										
			 sql.append("FROM " );
			 sql.append(queries.get(0));

			 if(queries.size() > 1) {
				 for(int i = 1; i < queries.size(); i++){
					 sql.append(" INNER JOIN ");
					 sql.append(queries.get(i));
					 sql.append(" ON q"+(i-1)+".element_code = q"+i+".element_code ");	
				 }
			 }
			 sql.append(" WHERE q0.databases is not null ");
			 sql.append(" ORDER BY CAST(q0.element_order AS integer)" );


			 LOGGER.info("getElementsByProductQuery sql: " + sql);

			 return sql.toString();
		 }

    public static String getElementsByProductCompareQuery(AMISQueryVO qvo, String table1, String table2) {

			 /*
			  * returns only in-common elements for the selected products
			  */

        /**
         *
         * select n1.element_code, n1.element_name || ' (' || n1.units || ')' as element_name
         FROM
         (select e.element_code, e.element_name, e.units from AMIS_PRODUCT_ELEMENTS_TABLE_8817d23e pe, AMIS_ELEMENTS_fb373035 e where product_code in ('1') and  pe.element_code=e.element_code) n1
         INNER JOIN
         (select e.element_code, e.element_name, e.units from AMIS_PRODUCT_ELEMENTS_TABLE_8817d23e pe, AMIS_ELEMENTS_fb373035 e where product_code in ('2') and  pe.element_code=e.element_code) n2
         ON n1.element_code = n2.element_code
         INNER JOIN
         (select e.element_code, e.element_name, e.units from AMIS_PRODUCT_ELEMENTS_TABLE_8817d23e pe, AMIS_ELEMENTS_fb373035 e where product_code in ('4') and  pe.element_code=e.element_code) n3
         ON n3.element_code = n2.element_code
         ORDER BY element_name;
         */

        List<String> queries = new ArrayList<String>();

        int counter = 0;
        for(String code: qvo.getItems().keySet()){
//				 queries.add("(SELECT e.element_code, e.element_name,  e.units, e.databases, e.is_aggregated, e.element_order FROM "+table1 + ", "+table2+" WHERE product_code = '"+code+"' AND pe.element_code=e.element_code) q"+counter);
            //To remove Governament Stocks
            queries.add("(SELECT e.element_code, e.element_name,  e.units, e.databases, e.is_aggregated, e.element_order FROM "+table1 + ", "+table2+" WHERE product_code = '"+code+"' AND pe.element_code=e.element_code AND e.element_code not in ('17', '1')) q"+counter);
            counter++;
        }

        StringBuffer sql = new StringBuffer();
        sql.append("SELECT q0.element_code, q0.element_name, ' (' || q0.units || ')' as units_databases, ");
        sql.append("CASE WHEN q0.is_aggregated = 'TRUE' THEN 'TRUE' ELSE 'FALSE' END AS is_aggregated ");
        sql.append("FROM " );
        sql.append(queries.get(0));

        if(queries.size() > 1) {
            for(int i = 1; i < queries.size(); i++){
                sql.append(" INNER JOIN ");
                sql.append(queries.get(i));
                sql.append(" ON q"+(i-1)+".element_code = q"+i+".element_code ");
            }
        }
        sql.append(" WHERE q0.databases is not null ");
        sql.append(" ORDER BY CAST(q0.element_order AS integer)" );


        LOGGER.info("getElementsByProductQuery sql: " + sql);

        return sql.toString();
    }
	 
	 
	 public static String getProductsByElementQuery(AMISQueryVO qvo, String table1, String table2) {

		 StringBuffer sql = new StringBuffer();
		 sql.append(" SELECT DISTINCT(p.product_code), p.product_name, p.product_order FROM "+table1 + ", "+table2+" WHERE element_code IN (");
		 int i = 0;
		 for(String code: qvo.getElements().keySet()) {
        		sql.append("'"+code+"'");
        		
        		if (i < qvo.getElements().size() - 1)
					sql.append(", ");
        		
        		i++;
		 }
		 sql.append(") AND pe.product_code=p.product_code " );
		 sql.append(" ORDER BY p.product_order" );

		 LOGGER.info("getProductsByElementQuery sql: " + sql);

		 return sql.toString();
	 }

    public static String getProductsByElementQueryCompare(AMISQueryVO qvo, String table1, String table2) {

        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT DISTINCT(p.product_code), p.product_name, p.product_order FROM "+table1 + ", "+table2+" WHERE element_code IN (");
        int i = 0;
        for(String code: qvo.getElements().keySet()) {
            sql.append("'"+code+"'");

            if (i < qvo.getElements().size() - 1)
                sql.append(", ");

            i++;
        }
        sql.append(") AND p.product_code <> '9' AND pe.product_code=p.product_code " );
        sql.append(" ORDER BY p.product_order" );

        LOGGER.info("getProductsByElementQuery sql: " + sql);

        return sql.toString();
    }
	 
	  public static String getElementsWithDatabasesQueryOld(AMISQueryVO qvo, String tableName) {
			
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT element_code,  element_name || ' (' || units || ')' ");
			sql.append("FROM "+tableName+" " );
			if(qvo.getSelectedDataset()!=null) {
				sql.append("WHERE ");
				//sql.append("elem_code_"+qvo.getSelectedDataset().toLowerCase()+" is not null ");
				sql.append(" element_code not in ('28', '29', '30', '31', '32', '33', '34') ");
						//+qvo.getSelectedDataset().toLowerCase()+" is not null ");
				sql.append(" AND databases is not null ");
			} else {
				sql.append(" WHERE databases is not null ");
			}
			sql.append("ORDER BY element_name");
				
			LOGGER.info("getElementsWithDatabasesQueryOld sql: " + sql);
		
			return sql.toString();
		}
	  
	  
	  public static String getElementsWithDatabasesQuery(AMISQueryVO qvo, String tableName) {
			
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT element_code,  element_name, ' (' || units || ')' as units_databases, ");
			sql.append("CASE WHEN is_aggregated = 'TRUE' THEN 'TRUE' ELSE 'FALSE' END AS is_aggregated ");			
			sql.append("FROM "+tableName+" " );
			if(qvo.getSelectedDataset()!=null) {
				LOGGER.info("getElementsWithDatabasesQuery if(qvo.getSelectedDataset()!=null) ");
				sql.append("WHERE ");
				//To eliminate Governament Stock from the Compare View
				sql.append(" element_code not in ('28', '29', '30', '31', '32', '33', '34', '17') ");
				//sql.append("elem_code_"+qvo.getSelectedDataset().toLowerCase()+" is not null ");
				sql.append(" AND databases is not null ");
			} else {
				LOGGER.info("getElementsWithDatabasesQuery else (qvo.getSelectedDataset()!=null) ");
				//LOGGER.info("getElementsWithDatabasesQuery sql: " + sql);
				sql.append(" WHERE databases is not null and ");
				//To eliminate Governament Stock from the Compare View
				sql.append(" element_code not in ('17') ");
			}
			sql.append("ORDER BY CAST(element_order AS integer)");				
			LOGGER.info("getElementsWithDatabasesQuery sql: " + sql);		
			return sql.toString();
		}

    public static String getElementsWithDatabasesCompareQuery(AMISQueryVO qvo, String tableName) {

        StringBuffer sql = new StringBuffer();
        sql.append("SELECT element_code,  element_name, ' (' || units || ')' as units_databases, ");
        sql.append("CASE WHEN is_aggregated = 'TRUE' THEN 'TRUE' ELSE 'FALSE' END AS is_aggregated ");
        sql.append("FROM "+tableName+" " );
        if(qvo.getSelectedDataset()!=null) {
            LOGGER.info("getElementsWithDatabasesQuery if(qvo.getSelectedDataset()!=null) ");
            sql.append("WHERE ");
            //To eliminate Governament Stock from the Compare View
            sql.append(" element_code not in ('28', '29', '30', '31', '32', '33', '34', '17', '1') ");
            //sql.append("elem_code_"+qvo.getSelectedDataset().toLowerCase()+" is not null ");
            sql.append(" AND databases is not null ");
        } else {
            LOGGER.info("getElementsWithDatabasesQuery else (qvo.getSelectedDataset()!=null) ");
            //LOGGER.info("getElementsWithDatabasesQuery sql: " + sql);
            sql.append(" WHERE databases is not null and ");
            //To eliminate Governament Stock from the Compare View
            sql.append(" element_code not in ('17', '1') ");
        }
        sql.append("ORDER BY CAST(element_order AS integer)");
        LOGGER.info("getElementsWithDatabasesQuery sql: " + sql);
        return sql.toString();
    }


    public static String getElementsQuery(AMISQueryVO qvo, String tableName) {
			
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT element_code,  element_name || ' (' || units || ')' ");
			sql.append("FROM "+tableName+" " );
			if(qvo.getSelectedDataset()!=null) {
				sql.append("WHERE ");
				sql.append(" element_code not in ('21','28', '29', '30', '31', '32', '33', '34', '37') ");
				//if(qvo.getSelectedDataset().equals(AMISCurrentDatasetView.AMIS.toString()) || qvo.getSelectedDataset().equals(AMISCurrentDatasetView.NATIONAL.toString()))
				//	sql.append("elem_code_cbs is not null ");
				//else
				//	sql.append("elem_code_"+qvo.getSelectedDataset().toLowerCase()+" is not null ");
			}
			sql.append("ORDER BY element_name");
			//SELECT element_code,  element_name || ' (' || units || ')' FROM AMIS_ELEMENTS_5d2b20f2 WHERE elem_code_CBS  is not null;
			LOGGER.info("getElementsQuery sql: " + sql);
			return sql.toString();
		}

 public static String getSingleElement(AMISQueryVO qvo, String tableName) {
		
		 String year = qvo.getYear().substring(0,4)+ "-01-01";
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT * ");
			sql.append("FROM "+tableName+" " );
			sql.append("WHERE ");
			sql.append("database = '"+qvo.getDatabase() + "' AND ");
			sql.append("region_code = '"+qvo.getCountryCode()+ "' AND ");
			sql.append("region_name = '"+qvo.getCountryName()+ "' AND ");
			sql.append("product_code = '"+qvo.getCommodityCode()+ "' AND ");
			sql.append("product_name = '"+qvo.getCommodityName()+ "' AND ");
			sql.append("element_code = '"+qvo.getElementCode()+ "' AND ");
			if((qvo.getElementName()!=null)&&(!qvo.getElementName().equals("")))
			{
				sql.append("element_name = '"+qvo.getElementName()+ "' AND ");
			}
			sql.append("units = '"+qvo.getUnit()+ "' AND ");
			sql.append("year = '"+year+ "' AND ");
			if((qvo.getMonth()!=null)&&(!qvo.getMonth().equals("")))
			{
				sql.append("month = '"+qvo.getMonth()+ "' AND ");
			}			
			sql.append("value_type = '"+qvo.getValueType()+ "' ");
		//	LOGGER.info("getSingleElement sql: " + sql);
		//select element_code,  element_name from AMIS_DATA_e4a05ad6 where Database = 'CBS' AND element_code is not null ORDER BY element_name;
			//select element_code,  element_name from AMIS_ELEMENTS_137d75de ORDER BY element_name;
			return sql.toString();
		}

	 public static String getElementsWidthoutUnitsQuery(AMISQueryVO qvo, String tableName) {
			
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT element_code,  element_name ");
			sql.append("FROM "+tableName+" " );
			if((qvo.getSelectedDataset()!=null)&&(qvo.getSelectedDataset()!="")) {
				sql.append("WHERE ");
				sql.append(" element_code not in ('28', '29', '30', '31', '32', '33', '34') ");
				//sql.append("elem_code_"+qvo.getSelectedDataset().toLowerCase()+" is not null ");
			}
			sql.append("ORDER BY element_name");
				
			LOGGER.info("getElementsWidthoutUnitsQuery sql: " + sql);
		//select element_code,  element_name from AMIS_DATA_e4a05ad6 where Database = 'CBS' AND element_code is not null ORDER BY element_name;
			//select element_code,  element_name from AMIS_ELEMENTS_137d75de ORDER BY element_name;
			return sql.toString();
		}
//   
//   public static String getItemsQuery(AMISQueryVO qvo, String tableName) {
//		
//		StringBuffer sql = new StringBuffer();
//		
//		//sql.append("SELECT "+qvo.getSelectedDataset().toLowerCase()+"code, itemname"+qvo.getSelectedDataset().toLowerCase()+AMISConstants.defaultLanguage.toLowerCase()+" ");
//		sql.append("SELECT "+qvo.getSelectedDataset().toLowerCase()+"code, ProductName"+qvo.getSelectedDataset().toLowerCase()+AMISConstants.defaultLanguage.toLowerCase()+" ");
//		sql.append("FROM "+tableName+" " );
//		sql.append("WHERE ");
//		sql.append(qvo.getSelectedDataset().toLowerCase()+"code is not null ");
//		sql.append("ORDER BY ProductName"+qvo.getSelectedDataset().toLowerCase()+AMISConstants.defaultLanguage.toLowerCase());
//			
//		LOGGER.info("getItemQuery sql: " + sql);
//		return sql.toString();
//	}

   
   public static String getCodingSystemQuery(AMISQueryVO qvo, String codingSystemCode) {
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT c.code, c.label ");
		sql.append("FROM codingsystem AS cs, code as c, codingsystem_code AS csc " );
		sql.append("WHERE ");
		sql.append("cs.resourceid = csc.codingsystem_resourceid ");
		sql.append("AND ");
		sql.append("csc.codes_id = c.id ");
		sql.append("AND ");
		sql.append("c.langcode = '").append(qvo.getLanguage()).append("' ");
		sql.append("AND ");
		sql.append("cs.code = '"+codingSystemCode+"' ");
		sql.append("ORDER BY c.label");
			
		LOGGER.info("codingSystemCode sql: " + sql);
		return sql.toString();
	}
	
 public static String getYearsQuery(AMISQueryVO qvo) {
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT DISTINCT(year) as code, year as label ");
		sql.append("FROM "+qvo.getDatasetTableName() + " ");
		sql.append("ORDER BY year DESC");
			
		LOGGER.info("getCodingSystemYearsQuery sql: " + sql);
		return sql.toString();
	}
 
 //The month is null only if the commodity doesn't belong to the MARKETING YEAR TABLE
 public static String getElement(AMISQueryVO qvo) {
//	 database     | text                   | 
//	 region_code  | text                   | 
//	 region_name  | text                   | 
//	 product_code | text                   | 
//	 product_name | text                   | 
//	 element_code | text                   | 
//	 element_name | text                   | 
//	 units        | character varying(255) | 
//	 year         | date                   | 
//	 month        | text                   | 
//	 value        | double precision       | 
//	 flag         | text                   | 
//	 value_type   | text  
	 //String year = qvo.getYear().substring(0,4)+ "-01-01";
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT units, month, value, flag ");
		sql.append("FROM "+qvo.getTablename()+" " );
		sql.append("WHERE ");
		sql.append("database = '"+qvo.getDatabase() + "' AND ");
		sql.append("region_code = '"+qvo.getCountryCode()+ "' AND ");
		sql.append("region_name = '"+qvo.getCountryName()+ "' AND ");
		sql.append("product_code = '"+qvo.getCommodityCode()+ "' AND ");
		sql.append("product_name = '"+qvo.getCommodityName()+ "' AND ");
		sql.append("element_code = '"+qvo.getElementCode()+ "' AND ");
		if((qvo.getElementName()!=null)&&(!qvo.getElementName().equals("")))
		{
			sql.append("element_name = '"+qvo.getElementName()+ "' AND ");
		}
		//sql.append("units = '"+qvo.getUnit()+ "' AND ");
		sql.append("year = '"+qvo.getYear()+ "' AND ");
//		if((qvo.getMonth()!=null)&&(!qvo.getMonth().equals("")))
//		{
//			sql.append("month = '"+qvo.getMonth()+ "' AND ");
//		}			
		sql.append("value_type = '"+qvo.getValueType()+ "' ");
	//	LOGGER.info("getSingleElement sql: " + sql);
	//select element_code,  element_name from AMIS_DATA_e4a05ad6 where Database = 'CBS' AND element_code is not null ORDER BY element_name;
		//select element_code,  element_name from AMIS_ELEMENTS_137d75de ORDER BY element_name;
		return sql.toString();
	}	

 
//// SELECT amis.* FROM AMIS_DATA_MONTH_75ba84df amis
// //INNER JOIN (SELECT database, region_code, product_code, element_code, year, MAX(CAST(month_position AS integer)) AS MaxMonthPosition
// //from AMIS_DATA_MONTH_75ba84df GROUP BY database, region_code, product_code, element_code, year )
// //grouppedAmis ON amis.database = grouppedAmis.database AND amis.region_code = grouppedAmis.region_code AND
// //amis.product_code = grouppedAmis.product_code AND amis.element_code = grouppedAmis.element_code AND
// //amis.year = grouppedAmis.year AND CAST(amis.month_position AS integer) = grouppedAmis.MaxMonthPosition AND
// //amis.database IN ('NATIONAL') AND amis.region_code IN ('46') AND amis.product_code IN ('5') AND amis.element_code IN ('4') AND
// //amis.year IN ('2012-01-01') AND amis.value_type='TOTAL';
// //This function returns the best element, the element with the highest value of 'month position';
// public static String getElementWithMaxMonthPosition(AMISQueryVO qvo) {
////	 database     | text                   |
////	 region_code  | text                   |
////	 region_name  | text                   |
////	 product_code | text                   |
////	 product_name | text                   |
////	 element_code | text                   |
////	 element_name | text                   |
////	 units        | character varying(255) |
////	 year         | date                   |
////	 month        | text                   |
////	 value        | double precision       |
////	 flag         | text                   |
////	 value_type   | text
//	 //String year = qvo.getYear().substring(0,4)+ "-01-01";
//		StringBuffer sql = new StringBuffer();
//
//		//sql.append("SELECT amis.* ");
//
//
//		sql.append("SELECT amis.database, amis.database AS modified_by, amis.region_code, amis.region_name, amis.product_code, amis.product_name, amis.element_code, amis.element_name, amis.units, amis.year_label, amis.month, amis.value, amis.flag, amis.value_type, amis.month_position ");
//		sql.append("FROM "+qvo.getTablename()+" amis INNER JOIN ");
//		sql.append("(SELECT database, region_code, product_code, element_code, year, MAX(CAST(month_position AS integer)) AS MaxMonthPosition ");
//		sql.append("from "+qvo.getTablename()+" GROUP BY database, region_code, product_code, element_code, year ) ");
//		sql.append("grouppedAmis ON amis.database = grouppedAmis.database AND amis.region_code = grouppedAmis.region_code AND ");
//		sql.append("amis.product_code = grouppedAmis.product_code AND amis.element_code = grouppedAmis.element_code AND ");
//		sql.append("amis.year = grouppedAmis.year AND CAST(amis.month_position AS integer) = grouppedAmis.MaxMonthPosition AND ");
//		sql.append("amis.database IN ("+qvo.getDatabase() + ") AND amis.region_code IN ("+qvo.getCountryCode()+ ") AND amis.product_code IN ("+qvo.getCommodityCode()+ ") AND ");
//		sql.append("amis.element_code IN ("+qvo.getElementCode()+ ") AND amis.year IN ("+qvo.getYear()+ ") AND amis.value_type='TOTAL';");
//
//		LOGGER.info("getSingleElement sql: " + sql);
//		return sql.toString();
//	}

    //Start Year Label Changes
    //SELECT amis.database, amis.database AS modified_by, amis.region_code, amis.region_name, amis.product_code,
    //amis.product_name, amis.element_code, amis.element_name, amis.units, amis.year_label, amis.month, amis.value,
    // amis.flag, amis.value_type, amis.month_position FROM amis_all_datasources amis INNER JOIN
    // (SELECT database, region_code, product_code, element_code, year_label, MAX(CAST(month_position AS integer))
    // AS MaxMonthPosition from amis_all_datasources GROUP BY database, region_code, product_code, element_code, year_label )
    // grouppedAmis ON amis.database = grouppedAmis.database AND amis.region_code = grouppedAmis.region_code AND
    // amis.product_code = grouppedAmis.product_code AND amis.element_code = grouppedAmis.element_code AND
    // amis.year_label = grouppedAmis.year_label AND CAST(amis.month_position AS integer) = grouppedAmis.MaxMonthPosition
    // AND amis.database IN ('CBS', 'NATIONAL') AND amis.region_code IN ('37') AND amis.product_code IN ('5') AND
    // amis.element_code IN ('5') AND amis.year_label IN ('1999/2000','2000/01', '2001/02', '2002/03', '2003/04', '2004/05', '2005/06',
    // '2006/07', '2007/08', '2008/09', '2009/10', '2010/11', '2011/12', '2012/13', '2013/14') AND amis.value_type='TOTAL';
    //This function returns the best element, the element with the highest value of 'month position';
    public static String getElementWithMaxMonthPosition(AMISQueryVO qvo) {
//	 database     | text                   |
//	 region_code  | text                   |
//	 region_name  | text                   |
//	 product_code | text                   |
//	 product_name | text                   |
//	 element_code | text                   |
//	 element_name | text                   |
//	 units        | character varying(255) |
//	 year         | date                   |
//	 month        | text                   |
//	 value        | double precision       |
//	 flag         | text                   |
//	 value_type   | text
        //String year = qvo.getYear().substring(0,4)+ "-01-01";
        StringBuffer sql = new StringBuffer();
       // System.out.println("getSingleElement qvo year: " + qvo.getYear());
        //sql.append("SELECT amis.* ");

//      qvo.getYear()=  '2001-01-01', '2000-01-01', '2012-01-01', '2011-01-01', '2010-01-01', '2013-01-01', '2004-01-01', '2005-01-01', '2002-01-01', '2003-01-01', '2008-01-01', '2009-01-01', '2006-01-01', '2007-01-01'
        //qvo.getYear()
        //Removing spaces
        qvo.setYear(qvo.getYear().replaceAll("\\s",""));
        //System.out.println("getSingleElement year: " +  qvo.getYear());
        String delimiter = ",";
        String yearArray[] = qvo.getYear().split(delimiter);
        // '2004-01-01'->'2004/05'
        String year_label = "";
        int dim = yearArray.length;
        for(int i=0; i< dim; i++)
        {
            String year = yearArray[i];
            //System.out.println("getSingleElement year: " +  year);
            year = year.substring(1,5);
            //System.out.println("getSingleElement year: sub " +  year);
            Integer next_year = (Integer.parseInt(year))+1;
            //System.out.println("getSingleElement year: next_year " +  next_year);
            String next_year_s = ""+next_year;
            //System.out.println("getSingleElement year: next_year_s " +  next_year_s);
            year_label += "'"+year+"/"+next_year_s.substring(2)+"'";
            if(i<(dim-1))
            {
                year_label += ",";
            }
        }

       // System.out.println("getSingleElement year label: " +  year_label);
        sql.append("SELECT amis.database, amis.database AS modified_by, amis.region_code, amis.region_name, amis.product_code, amis.product_name, amis.element_code, amis.element_name, amis.units, amis.year_label, amis.month, amis.value, amis.flag, amis.value_type, amis.month_position ");
        sql.append("FROM "+qvo.getTablename()+" amis INNER JOIN ");
        sql.append("(SELECT database, region_code, product_code, element_code, year_label, MAX(CAST(month_position AS integer)) AS MaxMonthPosition ");
        sql.append("from "+qvo.getTablename()+" GROUP BY database, region_code, product_code, element_code, year_label ) ");
        sql.append("grouppedAmis ON amis.database = grouppedAmis.database AND amis.region_code = grouppedAmis.region_code AND ");
        sql.append("amis.product_code = grouppedAmis.product_code AND amis.element_code = grouppedAmis.element_code AND ");
        sql.append("amis.year_label = grouppedAmis.year_label AND CAST(amis.month_position AS integer) = grouppedAmis.MaxMonthPosition AND ");
        sql.append("amis.database IN ("+qvo.getDatabase() + ") AND amis.region_code IN ("+qvo.getCountryCode()+ ") AND amis.product_code IN ("+qvo.getCommodityCode()+ ") AND ");
        sql.append("amis.element_code IN ("+qvo.getElementCode()+ ") AND amis.year_label IN ("+year_label+ ") AND amis.value_type='TOTAL';");

        LOGGER.info("getSingleElement sql: " + sql);
        //System.out.println("getSingleElement sql: " + sql);
        return sql.toString();
    }
    //End Year Label Changes
}