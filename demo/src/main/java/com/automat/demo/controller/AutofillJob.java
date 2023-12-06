package com.automat.demo.controller;

import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.automat.demo.service.TableService;
//import com.automat.demo.service.TableService;
@RestController
@Component
public class AutofillJob implements Job {

    @Autowired
    private TableService tableService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Value("${custom.tableName}")
    private String tableName;

    @Value("${custom.columnName}") // column on which the percentage distribution calculation is applied.
    private String columnName;
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException{

        tableService.distribute();
        
    }


    @RequestMapping("/percent")
    public@ResponseBody Double Percentage()
    {
        
        Double total_percentage_by_source = 0.0;
        String selectQuery = "SELECT DISTINCT `Level-2` FROM " + tableName ;
        List<String> countries= jdbcTemplate.queryForList(selectQuery, String.class);
        for (String land : countries) {
            if ( land.equals("") || land.equals(" ") )
            {  
                total_percentage_by_source += 0;
            }
            else
            {
                total_percentage_by_source += tableService.getPercentage(tableName, columnName, land, "natural gas");
            }        
        }
        return total_percentage_by_source;
    }


    //  @RequestMapping("/test")
    // public@ResponseBody List<Map<String, Object>> listtest()
    // {
    //     return tableService.getList(tableName);
    // }
    

}
