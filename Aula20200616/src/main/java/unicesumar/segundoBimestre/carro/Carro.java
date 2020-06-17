package unicesumar.segundoBimestre.carro;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Carro {
	@Id
	private String id;
	
	private String modelo;
	
	private String marca;

	public Carro(String id, String modelo, String marca) {		
		this.id = id;
		this.modelo = modelo;
		this.marca = marca;
	}

	public Carro() {
		this.id = UUID.randomUUID().toString();
	}
	
	public String getId() {
		return id;
	}
	
	public String getModelo() {
		return modelo;
	}

	public String getMarca() {
		return marca;
	}
	
	public void setmodelo(String modelo) {
		this.modelo = modelo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Carro other = (Carro) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	


	
	

}
