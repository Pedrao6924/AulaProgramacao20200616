package unicesumar.segundoBimestre;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.catalina.authenticator.SavedRequest;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import unicesumar.segundoBimestre.carro.CarroController;
import unicesumar.segundoBimestre.carro.CarroService;
import unicesumar.segundoBimestre.carro.*;


@WebMvcTest
@AutoConfigureMockMvc
public class TesteComApiDeCarro {

	class TestesComApiDeLivro {
		@Autowired
		private MockMvc mockMvc;
		
		@Autowired
		private CarroController controller;
		
		@MockBean
		private CarroService service;
		
		@Autowired
		private ObjectMapper objectMapper;
		
		@Test
		void testandoGetByIdComDadoInexistente() throws Exception {
			when(service.getById("2")).thenThrow(NotFoundException.class);

			mockMvc.perform(get("/api/livros/2")).andExpect(status().isNotFound());
		}
		
		@Test
		void testandoGetByIdComDadoExistente() throws Exception {
			Carro existente = new Carro("1", "TesteModelo", "TesteMarca");		
			when(service.getById("1")).thenReturn(existente);
			
			mockMvc.perform(get("/api/carros/1"))
			.andExpect(jsonPath("$.id").value("1"))
			.andExpect(jsonPath("$.modelo").value("TesteModelo"))
			.andExpect(jsonPath("$.marca").value("TesteMarca"))
			.andExpect(status().isOk());
		}
		
		@Test
		void testandoGetAll() throws Exception {
			Carro a = new Carro("1", "A", "Z");		
			Carro b = new Carro("2", "B", "Y");		
			Carro c = new Carro("3", "C", "X");		
			when(service.getAll()).thenReturn(Arrays.asList(a,b,c));
			
			mockMvc.perform(get("/api/carros"))
			.andExpect(jsonPath("$").isArray())
			.andExpect(jsonPath("$", hasSize(3)))
			.andExpect(jsonPath("$.[0].id").value("1"))
			.andExpect(jsonPath("$.[1].id").value("2"))
			.andExpect(jsonPath("$.[2].id").value("3"))
			.andExpect(jsonPath("$.[0].modelo").value("A"))
			.andExpect(jsonPath("$.[1].modelo").value("B"))
			.andExpect(jsonPath("$.[2].modelo").value("C"))
			.andExpect(jsonPath("$.[0].marca").value("X"))
			.andExpect(jsonPath("$.[1].marca").value("Y"))
			.andExpect(jsonPath("$.[2].marca").value("Z"))
			.andExpect(status().isOk());
		}
		
		@Test
		void testandoPost() throws Exception {
			when(service.save(ArgumentMatchers.any(Carro.class))).thenReturn("99");
			
			Map<String, String> carro  = new HashMap<String, String>() {{
			    put("id", "99");
			    put("modelo", "Java Core 2020");
			    put("marca", "111");
			}};
			
			String carroComoJson = objectMapper.writeValueAsString(carro);
			
//			String livroComoJson = "{" 
//			+ "\"id\": \"99\"," 
//			+ "\"titulo\": \"Java Core 2020\"," 
//			+ "\"numeroDePaginas\": 111" 
//			+ "}";
			
			mockMvc.perform(post("/api/carros")
					.contentType(MediaType.APPLICATION_JSON)
					.content(carroComoJson))
			.andExpect(status().isCreated())
			.andExpect(content().string("99"));
		}
		
		@Test
		void testandoDelete() throws Exception
		{
			Carro a = new Carro("1", "A", "Z");	
			service.save(a);
			when(service.getById("1")).thenReturn(a);

			mockMvc.perform(delete("/api/carros/{id}","1")
					.contentType(MediaType.APPLICATION_JSON))
		            .andExpect(status().isOk());	
			
		}
		
		@Test
		void testandoPut() throws Exception
		{
			when(service.save(ArgumentMatchers.any(Carro.class))).thenReturn("99");
			
			Map<String, String> carro  = new HashMap<String, String>() {{
			    put("id", "99");
			    put("modelo", "abc");
			    put("marca", "111");
			}};
			
			String carroComoJson = objectMapper.writeValueAsString(carro);
			
			mockMvc.perform(post("/api/carros")
					.contentType(MediaType.APPLICATION_JSON)
					.content(carroComoJson))
					.andExpect(status().isCreated())
					.andExpect(content().string("99"));
		
			
			Map<String, String> carroAlt  = new HashMap<String, String>() {{
			    put("id", "99");
			    put("modelo", "xyz");
			    put("marca", "999");
			}};
			
			
			String carroAltComoJson = objectMapper.writeValueAsString(carroAlt);

				mockMvc.perform(put("/api/carros/{id}", "99")
						.contentType(MediaType.APPLICATION_JSON)
						.content(carroAltComoJson))
						.andExpect(status().isOk());
				
				
				mockMvc.perform(get("/api/carros/1"))
						.andExpect(jsonPath("$.id").value("99"))
						.andExpect(jsonPath("$.modelo").value("xyz"))
						.andExpect(jsonPath("$.marca").value("999"))
						.andExpect(status().isOk());
		}
		
		
		

	}








}
