package com.automat.demo.controller;
//import org.quartz.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

import java.util.Objects;

@Configuration
public class QuartzConfig {
   
    @Value("${job.repeat.interval}")
    private long repeatInterval;

    @Value("${job.repeat.count}")
    private int repeatCount;

    @Bean
    public JobDetailFactoryBean jobDetailFactoryBean(){
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(AutofillJob.class); // specifies the class of the job to be executed.
        factoryBean.setDurability(true); // ensures that the job survives system restarts.
        return factoryBean;
    }
    @Bean
    public SimpleTriggerFactoryBean triggerFactoryBean(){
        SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean(); // activates and schedules the job.
        factoryBean.setJobDetail(Objects.requireNonNull(jobDetailFactoryBean().getObject())); // indicates which job is triggered by this trigger.
        factoryBean.setRepeatInterval(repeatInterval); // sets the time interval (in milliseconds) between each job execution
        factoryBean.setRepeatCount(repeatCount); //indicates that the trigger should repeat indefinitely.
        
        return factoryBean;
    }
}
