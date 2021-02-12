package com.hibernate.test;

import java.util.Date;

import javax.transaction.Transaction;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import com.entity.User;

public class Hibernate_Test_Create {
 
    public static void main(String[] args) {
    	
    	SessionFactory sessionFactoryObj = null;
    	Session sessionObj = null;
    	
        try {
        	
        	// Creating Configuration Instance & Passing Hibernate Configuration File
            StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                    .configure() // configures settings from hibernate.cfg.xml
                    .build();
                
            // Creating Hibernate SessionFactory Instance
            sessionFactoryObj = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            
        	sessionObj = sessionFactoryObj.openSession();
        	
        	//Operation
            org.hibernate.Transaction txn = sessionObj.beginTransaction();
 
            User user = new User();
            user.setUser_id(1015);
            user.setUser_name("ABC");
            user.setUser_email("anc@gmail.com");
            user.setCreated_date(new Date());
           
            sessionObj.save(user);
            
            System.out.println("\n.......Records Saved Successfully To The Database.......\n");
 
            // Committing The Transactions To The Database
            txn.commit();
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
