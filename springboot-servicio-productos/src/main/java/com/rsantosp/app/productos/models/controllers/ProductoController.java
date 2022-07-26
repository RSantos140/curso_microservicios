package com.rsantosp.app.productos.models.controllers;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.rsantosp.app.productos.models.entity.Producto;
import com.rsantosp.app.productos.models.service.IProductoService;

@RestController
public class ProductoController {
	
	@Autowired
	private IProductoService productoService;
	
	@Autowired
	private Environment env; // Obtenemos el objeto spring environment para recuperar parametros del properties
	
//	@Value("${server.port}")
//	private Integer port; // Recuperamos el parametro del properties de manera directa
	
	@GetMapping("/listar")
	public List<Producto> listar(){
		return productoService.findAll().stream().map(p -> {
			p.setPort(Integer.parseInt(env.getProperty("local.server.port")));
			//p.setPort(port);
			return p;
		}).collect(Collectors.toList());
	}
	
	@GetMapping("/ver/{id}")
	public Producto detalle(@PathVariable Long id) throws InterruptedException {
		
		// provocamos un error
		if (id.equals(10L)) {
			throw new IllegalStateException("Producto no encontrado");
		}
		
		// provocamos un timeout
		if (id.equals(1L)) {
			TimeUnit.SECONDS.sleep(5L);
		}
		
		Producto producto = productoService.findById(id);
		producto.setPort(Integer.parseInt(env.getProperty("local.server.port")));
		//producto.setPort(port);
		
		return producto;
	}

}
