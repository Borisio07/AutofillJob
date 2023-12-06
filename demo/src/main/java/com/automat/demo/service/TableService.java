package com.automat.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class TableService {
   @Autowired
   private JdbcTemplate jdbcTemplate;
    @Value("${custom.tableName}")
    private String tableName;

    @Value("${custom.columnName}") // column on which the percentage distribution calculation is applied.
    private String columnName;

    /**
     * returns the sum of energy consumption of a specific source for a country.
     * @param tableName Table name in database.
     * @param columnName consumption column
     * @param country Country in the Level-2 column.
     * @param source Energy source in the source column of the table.
     * @return return double
     */

    private double getSumConsumption(String tableName, String columnName,String country, String source )
    {
        String selectQuery = "SELECT SUM(" + columnName + ") FROM " + tableName + " WHERE source = ? AND `Level-2` = ? " ;
        List<Double> resultList = jdbcTemplate.queryForList(selectQuery, Double.class, source, country);
        // resultList != null && !resultList.isEmpty() 
        if (resultList.get(0) != null) {
            return resultList.get(0); 
        } else {
            return 0.0; 
        }
    }



    /**
     * returns the percentage of consumption of an energy source in a subsidiary (for a country)
     * @param tableName Table name in database.
     * @param columnName consumption column
     * @param country Country in the Level-2 column.
     * @param source Energy source in the source column of the table.
     * @return double
     */

    public double getPercentage(String tableName, String columnName, String country, String source)
    {
        Double total_consumption_by_source = 0.0;
        
        String selectQuery = "SELECT DISTINCT `Level-2` FROM " + tableName ;
        List<String> countries= jdbcTemplate.queryForList(selectQuery, String.class);
        for (String land : countries) {
            if ( land.equals("") || land.equals(" ") )
            {
                
                total_consumption_by_source += 0;
            }
            else
            {
                total_consumption_by_source += getSumConsumption(tableName, columnName, land, source); 
            }
                    
        }
        if (total_consumption_by_source!= 0) {
            return getSumConsumption(tableName, columnName, country, source)/ total_consumption_by_source;
        }
        else return 0.0;     
    }

    /**
     * Method of inserting new rows in database with updated data.
     * @param tableName Table name in database.
     * @param columnName consumption column
     * @param country Country in the Level-2 column.
     * @param source Energy source in the source column of the table.
     * 
     */
    public void insertRow(String tableName, String columnName, String country, String source)
    {
        Double percent = getPercentage(tableName, columnName, country, source);
        System.out.println("Percent of " + source +" in " + country +":" + percent);
        String insertQuery = "INSERT INTO " + tableName +
        "(`id`, `tableId`, `indicatorShortTitle`, `indicatorVersion`, `row`, `surveyId`, `lastSynced`, `statementTitle`, `timeRefFrom`, `timeRefTo`, `runIndex`, `Level-1`, `Level-2`, `Level-3`, `Level-4`, `source`, `consumption`, `consumption[unit]`) " +
        "SELECT ?, `tableId`, `indicatorShortTitle`, `indicatorVersion`, `row`, `surveyId`, `lastSynced`, `statementTitle`, `timeRefFrom`, `timeRefTo`, `runIndex`, `Level-1`, ?, `Level-3`, `Level-4`, ?, `consumption` * ?, `consumption[unit]` " +
        "FROM " + tableName +" WHERE `Level-2` = ' ' AND `source` = ? ";
        jdbcTemplate.update(insertQuery,  UUID.randomUUID().toString(), country, source, percent, source);      
    }
    
     /**
     * Method of updating and distributing new lines for each country and for each energy source.
     * 
     */
    public void distribute()
    {
        String selectDistinctLevel2Query = "SELECT DISTINCT `Level-2` FROM " + tableName ;
        List<String> countries= jdbcTemplate.queryForList(selectDistinctLevel2Query, String.class);
        String selectDistinctSourceQuery = "SELECT DISTINCT source FROM " + tableName;
        List<String> sources= jdbcTemplate.queryForList(selectDistinctSourceQuery, String.class);
        
        for ( String source : sources) {
             for(String country : countries){ 
                insertRow(tableName, columnName, country, source);
            }
        }
        String deleteQuery = "DELETE FROM " + tableName + " WHERE  `Level-2` = ' ' ";
        jdbcTemplate.update(deleteQuery);
    }

}