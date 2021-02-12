package com.hibernate.test;

import java.util.Date;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.entity.User;

public class Hibernate_Test_Read {
 
    public static void main(String[] args) {
    	
    	SessionFactory sessionFactoryObj = null;
    	Session sessionObj = null;
    	
        try {
        	
        	// Creating Configuration Instance & Passing Hibernate Configuration File
            Configuration configObj = new Configuration();
            configObj.configure("hibernate.cfg.xml");
            
            configObj.addAnnotatedClass(com.entity.User.class);
     
            // Since Hibernate Version 4.x, ServiceRegistry Is Being Used
            ServiceRegistry serviceRegistryObj = new StandardServiceRegistryBuilder().applySettings(configObj.getProperties()).build(); 
     
            // Creating Hibernate SessionFactory Instance
            sessionFactoryObj = configObj.buildSessionFactory(serviceRegistryObj);
            
        	sessionObj = sessionFactoryObj.openSession();
            sessionObj.beginTransaction();
            
            /**
             * Criteria - some where condition / based on condition
             * Create Builder
             * Create a query creteria
             * TypeQuery
             * Executes the query
             * fetch the result
             */
 
            CriteriaBuilder builder = sessionObj.getCriteriaBuilder();
            CriteriaQuery<User> criteria = builder.createQuery(User.class);
           
            Root<User> rootEntry = criteria.from(User.class);
            CriteriaQuery<User> all = criteria.select(rootEntry);
            TypedQuery<User> allQuery = sessionObj.createQuery(all);
            List<User> data =  allQuery.getResultList();
           
            System.out.println("size : " + data.size());
            System.out.println("Users: " + data.toString());
            
            //List<User> data2 = sessionObj.createQuery(criteria).getResultList();
            
            // Committing The Transactions To The Database
            //sessionObj.getTransaction().commit();
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
