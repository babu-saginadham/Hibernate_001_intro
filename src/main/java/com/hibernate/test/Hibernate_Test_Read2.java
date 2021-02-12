package com.hibernate.test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.entity.User;

public class Hibernate_Test_Read2 {

	static User user;
    static Session sessionObj;
    static SessionFactory sessionFactoryObj;
 
    public static void main(String[] args) {
        System.out.println("Hibernate Read\n");
        try {
            sessionObj = buildSessionFactory().openSession();
            sessionObj.beginTransaction();
            
            User user_read = sessionObj.get(User.class, 101);
            System.out.println("user details: " + user_read);
            
           
        } catch(Exception sqlException) {
        	System.out.println("Exception :" + sqlException);
        	
            if(null != sessionObj && null != sessionObj.getTransaction()) {
                System.out.println("\n Rolled Back");
                sessionObj.getTransaction().rollback();
            }
            sqlException.printStackTrace();
        } finally {
            if(sessionObj != null) {
                sessionObj.close();
            }
        }
    }
    
    
    
    private static SessionFactory buildSessionFactory() {
        // Creating Configuration Instance & Passing Hibernate Configuration File
        Configuration configObj = new Configuration();
        configObj.configure("hibernate.cfg.xml");
        
        configObj.addAnnotatedClass(com.entity.User.class);
 
        // Since Hibernate Version 4.x, ServiceRegistry Is Being Used
        ServiceRegistry serviceRegistryObj = new StandardServiceRegistryBuilder().applySettings(configObj.getProperties()).build(); 
 
        // Creating Hibernate SessionFactory Instance
        sessionFactoryObj = configObj.buildSessionFactory(serviceRegistryObj);
        return sessionFactoryObj;
    }
    
    
    public static void display() {

        
    }
}
