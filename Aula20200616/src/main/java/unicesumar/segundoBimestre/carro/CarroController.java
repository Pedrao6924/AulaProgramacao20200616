package unicesumar.segundoBimestre.carro;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/carros")
public class CarroController {
	@Autowired
	private CarroService service;
	
		
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping
	public List<Carro> getAll() {
		return service.getAll();
	}
	
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping("/{id}")
	public Carro getById(@PathVariable("id") String id) {
		Carro recuperado = service.getById(id);
		//recuperado.setTitulo(recuperado.getTitulo().toUpperCase());
		return recuperado;
	}
	
	@ResponseStatus(code = HttpStatus.CREATED)
	@PostMapping
	public String post(@RequestBody Carro novo) {
		return service.save(novo);
	}

	@ResponseStatus(code = HttpStatus.OK)
	@DeleteMapping("/{id}")
	public void deleteById(@PathVariable("id") String id) {
		service.deleteById(id);
	}
	
	@ResponseStatus(code = HttpStatus.OK)
	@PutMapping("/{id}")
	public void put(@PathVariable("id") String id, @RequestBody Carro modificado) {
		if (!id.equals(modificado.getId())) {
			throw new RuntimeException("Id do recurso não confere com Id do body!");
		}
		service.save(modificado);
	}

}


