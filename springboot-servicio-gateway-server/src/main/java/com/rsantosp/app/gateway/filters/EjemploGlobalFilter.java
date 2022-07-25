package com.rsantosp.app.gateway.filters;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
//import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class EjemploGlobalFilter implements GlobalFilter, Ordered{

	private final Logger log = LoggerFactory.getLogger(EjemploGlobalFilter.class);
	
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		log.info("ejecutando filtro global pre");
		
		// modificamos la cabecera entes de enviar el request para añadirle un token
		exchange.getRequest().mutate().headers(h -> h.add("token", "123456"));
		
		
		return chain.filter(exchange).then(Mono.fromRunnable(() -> {
			log.info("ejecutando filtro global post");
			
			// obtenemos el token de la cabecera despues de realizar el request
			Optional.ofNullable(exchange.getRequest().getHeaders().getFirst("token")).ifPresent(valor -> {
				exchange.getResponse().getHeaders().add("token", valor);
			});
			
			// añadimos una cookie al response
			exchange.getResponse().getCookies().add("color", ResponseCookie.from("color", "rojo").build());
			
			// indicamos el tipo de contenido del response
			//exchange.getResponse().getHeaders().setContentType(MediaType.TEXT_PLAIN);
		}));
	}

	@Override
	public int getOrder() {
		// si se usa un valor negativo(-1) significa que el orden es de alta prioridad y por lo tanto el response es de solo lectura. Si se intenta modificar dara un error
		return 1;
	}

}
