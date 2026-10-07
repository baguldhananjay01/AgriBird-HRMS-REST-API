package com.agribird_hrms.controller;


import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;

import javax.sql.DataSource;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class DatabaseHealthController {

    private final DataSource dataSource;

    public DatabaseHealthController(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    
    @GetMapping("/api/health/db")
    public String checkDatabaseConnection(){

        try(Connection connection=dataSource.getConnection()){

            if(connection.isValid(2)){
                return "Database connection is successful.";
            }
            return "Database connection is failed.";

        }catch(Exception e){
            return "Database connection is failed."+ e.getMessage();
        }
        
    }
    
}
