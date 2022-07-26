package com.rsantosp.app.item.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rsantosp.app.item.models.Item;
import com.rsantosp.app.item.models.Producto;
import com.rsantosp.app.item.service.ItemService;

@RestController
public class ItemController {
	
	private final Logger log = LoggerFactory.getLogger(ItemController.class);
	
	@Autowired
	private CircuitBreakerFactory cbFactory;	// implementacion de resilience4j

	@Autowired
	@Qualifier("serviceFeign")
	//@Qualifier("serviceRestTemplate")
	private ItemService itemService;
	
	@GetMapping("/listar")
	public List<Item> listar(
			@RequestParam(name="nombre", required = false) String nombre, 
			@RequestHeader(name="token-request", required = false) String token
	){
		System.out.println(nombre);
		System.out.println(token);
		return itemService.findAll();
	}
	
	@GetMapping("/ver/{id}/{cantidad}")
	public Item detalle(@PathVariable Long id, @PathVariable Integer cantidad) {
		return cbFactory.create("items")
				.run(() -> itemService.findById(id, cantidad), e -> metodoAlternativo(id, cantidad, e));
	}
	
	public Item metodoAlternativo(Long id, Integer cantidad, Throwable e) {
		Item item = new Item();
		Producto producto = new Producto();
		
		log.info(e.getMessage());
		
		item.setCantidad(cantidad);
		producto.setId(id);
		producto.setNombre("Producto creado en circuit braker");
		producto.setPrecio(69.3d);
		item.setProducto(producto);
		
		return item;
	}
}
