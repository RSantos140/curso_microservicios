package com.rsantosp.app.gateway.filters.factory;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
//import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class EjemploGatewayFilterFactory extends AbstractGatewayFilterFactory<EjemploGatewayFilterFactory.Configuracion>{

	private final Logger log = LoggerFactory.getLogger(EjemploGatewayFilterFactory.class);
	
	public EjemploGatewayFilterFactory() {
		super(Configuracion.class);
	}

	@Override
	public GatewayFilter apply(Configuracion config) {
		// Ejemplo de filtro ordenado
//		return new OrderedGatewayFilter((exchange, chain) -> {
//			log.info("ejecutando pre gateway filter factory {}", config.mensaje);
//			
//			return chain.filter(exchange).then(Mono.fromRunnable(() -> {
//				log.info("ejecutando post gateway filter factory {}", config.mensaje);
//				
//				Optional.ofNullable(config.cookieValor).ifPresent(cookie -> 
//					exchange.getResponse().addCookie(ResponseCookie.from(config.cookieNombre, cookie).build())
//				);
//			}));
//		}, 2);
		
		// Ejemplo de filtro sin orden
		return (exchange, chain) -> {
			log.info("ejecutando pre gateway filter factory {}", config.mensaje);
			
			return chain.filter(exchange).then(Mono.fromRunnable(() -> {
				log.info("ejecutando post gateway filter factory {}", config.mensaje);
				
				Optional.ofNullable(config.cookieValor).ifPresent(cookie -> 
					exchange.getResponse().addCookie(ResponseCookie.from(config.cookieNombre, cookie).build())
				);
			}));
		};
	}
	
	@Override
	public List<String> shortcutFieldOrder() {
		return Arrays.asList("mensaje", "cookieValor", "cookieNombre");
	}
	
	@Override
	public String name() {
		return "EjemploCookie";
	}

	public static class Configuracion {
		private String mensaje;
		private String cookieValor;
		private String cookieNombre;
		
		public String getMensaje() {
			return mensaje;
		}
		public void setMensaje(String mensaje) {
			this.mensaje = mensaje;
		}
		public String getCookieValor() {
			return cookieValor;
		}
		public void setCookieValor(String cookieValor) {
			this.cookieValor = cookieValor;
		}
		public String getCookieNombre() {
			return cookieNombre;
		}
		public void setCookieNombre(String cookieNombre) {
			this.cookieNombre = cookieNombre;
		}
	}
}
