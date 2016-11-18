package com.medeiros.ordnael.javaangular.configuration;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class AppFilter implements Filter {

	@Override
	public void init(FilterConfig config) throws ServletException {
		System.out.println("Aplicação Iniciada");
	}
	
	@Override
	public void destroy() {
		System.out.println("Aplicação Finalizada");
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		//System.out.println("Aplicação doFilter antes");
		
		
		chain.doFilter(req, res);
		
		//System.out.println("Aplicação doFilter depois");
	}

}
