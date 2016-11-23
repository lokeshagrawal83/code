package org.java.demo.db;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

 
public final class ContextListener implements ServletContextListener {
 
    public void contextInitialized(ServletContextEvent sce) {        
        System.out.println("Context Initialized.");
        ConnectionManager.configureConnPool();		
    }
 
    public void contextDestroyed(ServletContextEvent sce) {
    	System.out.println("Context Destroyed.");
    	ConnectionManager.shutdownConnPool();
    }
}