package com.rsantosp.app.productos.models.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
	
	@GetMapping("/productos")
	public List<Producto> listar(){
		return productoService.findAll().stream().map(p -> {
			p.setPort(Integer.parseInt(env.getProperty("local.server.port")));
			//p.setPort(port);
			return p;
		}).collect(Collectors.toList());
	}
	
	@GetMapping("/productos/{id}")
	public Producto detalle(@PathVariable Long id) {
		Producto producto = productoService.findById(id);
		producto.setPort(Integer.parseInt(env.getProperty("local.server.port")));
		//producto.setPort(port);
		
		// simulamos que tarda 2 segundos en responder
//		try {
//			Thread.sleep(2000L);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
		return producto;
	}

}
