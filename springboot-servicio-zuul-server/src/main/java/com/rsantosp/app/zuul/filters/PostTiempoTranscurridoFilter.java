package com.rsantosp.app.zuul.filters;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
public class PostTiempoTranscurridoFilter extends ZuulFilter{
	
	private static Logger log = LoggerFactory.getLogger(PostTiempoTranscurridoFilter.class);

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() throws ZuulException {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		
		log.info("Entrando a post filter");
		
		Long tiempoInicio = (Long) request.getAttribute("tiempoInicio");
		Long tiempoFinal = System.currentTimeMillis();
		Long tiempoTancurrido = tiempoFinal - tiempoInicio;
		
		log.info("Tiempo transcurrido en segundos: {} y en milisegundos: {}", tiempoTancurrido.doubleValue()/1000d, tiempoTancurrido);
		
		request.setAttribute("tiempoInicio", tiempoInicio);
		
		return null;
	}

	@Override
	public String filterType() {
		return "post";
	}

	@Override
	public int filterOrder() {
		return 1;
	}

}
