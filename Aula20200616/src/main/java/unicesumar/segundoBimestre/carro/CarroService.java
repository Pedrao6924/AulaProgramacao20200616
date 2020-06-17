package unicesumar.segundoBimestre.carro;


import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CarroService {
	
	@Autowired
	private CarroRepository repo;
	

	public void deleteById(String id) {
		repo.deleteById(id);
	}

	public Carro getById(String id) {
		//return repo.findById(id).get(); 
		return repo.findById(id).orElseThrow(NotFoundException::new);
	}
	
	public List<Carro> getAll() {
		return repo.findAll();
	}
	
	public String save(Carro carro) {
		return this.repo.save(carro).getId();
	}
	
}
