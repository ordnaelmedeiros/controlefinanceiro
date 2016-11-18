package com.medeiros.ordnael.javaangular.core;

import java.util.HashMap;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtils {

	private static Properties getPropriedades() {
		
		Properties prop = new Properties();
		
		prop.put("hibernate.connection.driver_class", "org.postgresql.Driver");
		prop.put("hibernate.connection.username", "postgres");
		prop.put("hibernate.connection.password", "ids0207");
		prop.put("hibernate.connection.url", "jdbc:postgresql://localhost:5432/controlefinanceiro");
		prop.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
		prop.put("hibernate.hbm2ddl.auto", "update");
		prop.put("hibernate.show_sql", "true");
		prop.put("hibernate.format_sql", "true");
		//prop.put("hibernate.default_schema", "public");
		
		return prop;
		
	}
	
	private static EntityManagerFactory entityManagerFactory = null;
	private static HashMap<String, EntityManagerFactory> listEntityManagerFactory = new HashMap<String, EntityManagerFactory>();
	
	static {
		try {
			entityManagerFactory = Persistence.createEntityManagerFactory("principal",getPropriedades());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void refreshEntityManagerFactory() {
		entityManagerFactory = Persistence.createEntityManagerFactory("principal",getPropriedades());
	}
	
	public static EntityManager createEntityManager() {
		return entityManagerFactory.createEntityManager();
	}
	
	public static EntityManager createEntityManager(String esquema) {
		if (listEntityManagerFactory.get(esquema)!=null) {
			return listEntityManagerFactory.get(esquema).createEntityManager();
		} else {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("principal",getPropriedades());
			listEntityManagerFactory.put(esquema, emf);
			return emf.createEntityManager();
		}
	}
	
}