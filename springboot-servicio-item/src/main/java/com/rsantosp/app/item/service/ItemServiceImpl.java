package com.rsantosp.app.item.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.rsantosp.app.item.models.Item;
import com.rsantosp.app.item.models.Producto;

@Service("serviceRestTemplate")
public class ItemServiceImpl implements ItemService{
	
	@Autowired
	private RestTemplate clienteRest;

	@Override
	public List<Item> findAll() {
		List<Producto> productos = Arrays.asList(clienteRest.getForObject("http://localhost:8001/productos", Producto[].class));
		return productos.stream().map(p -> new Item(p, 1)).collect(Collectors.toList());
	}

	@Override
	public Item findById(Long id, Integer cantidad) {
		// Creamos un mapa de variables para enviar los parametros en la llamada.
		Map<String, String> pathVariables = new HashMap<String, String>();
		pathVariables.put("id", id.toString());
		
		Producto producto = clienteRest.getForObject("http://localhost:8001/productos/{id}", Producto.class, pathVariables);
		return new Item(producto, cantidad);
	}

}
