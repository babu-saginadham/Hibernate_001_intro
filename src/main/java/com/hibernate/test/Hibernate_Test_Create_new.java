package com.hibernate.test;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.entity.User;

public class Hibernate_Test_Create_new {
 
    public static void main(String[] args) {
    	
    	SessionFactory sessionFactoryObj = null;
    	Session sessionObj = null;
    	
        try {
        	
        	// Creating Configuration Instance & Passing Hibernate Configuration File
            Configuration configObj = new Configuration();
            configObj.configure("hibernate.cfg.xml");
            
            //<mapping class="com.entity.User" />
            configObj.addAnnotatedClass(com.entity.User.class);
     
            // Since Hibernate Version 4.x, ServiceRegistry Is Being Used
            ServiceRegistry serviceRegistryObj = new StandardServiceRegistryBuilder()
            		.applySettings(configObj.getProperties()).build(); 
            
            // Creating Hibernate SessionFactory Instance
            sessionFactoryObj = configObj.buildSessionFactory(serviceRegistryObj);
            
            
        	sessionObj = sessionFactoryObj.openSession();
            sessionObj.beginTransaction();
 
            User user = new User();
            user.setUser_id(1012);
            user.setUser_name("Babu");
            user.setUser_email("babu@gmail.com");
            user.setCreated_date(new Date());

            sessionObj.save(user);
            
            System.out.println("\n.......Records Saved Successfully To The Database.......\n");
 
            // Committing The Transactions To The Database
            sessionObj.getTransaction().commit();
        } catch(Exception sqlException) {
        	System.out.println("Exception :" + sqlException);
        	
            if(null != sessionObj && null != sessionObj.getTransaction()) {
                System.out.println("\n.......Transaction Is Being Rolled Back.......");
                sessionObj.getTransaction().rollback();
            }
            sqlException.printStackTrace();
        } finally {
            if(sessionObj != null) {
                sessionObj.close();
            }
        }
    }
    
}
