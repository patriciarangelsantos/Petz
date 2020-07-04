package br.com.petz.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.rds.model.ResourceNotFoundException;

import br.com.petz.domain.Cliente;
import br.com.petz.domain.Pet;
import br.com.petz.repositories.ClienteRepository;
import br.com.petz.repositories.PetRepository;

@RestController
public class PetResource {
	
	@Autowired
	private PetRepository petRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	
	@PostMapping(value = "/clientes/{clienteId}/pets")
	@ResponseStatus(code = HttpStatus.CREATED)
	public Pet save(@PathVariable Integer clienteId,@RequestBody Pet  pet) {
		return clienteRepository.findById(clienteId).map(cliente -> {
			pet.setCliente(cliente);
			return petRepository.save(pet);
			
		}).orElseThrow(() -> new ResourceNotFoundException("Cliente [clienteId="+clienteId+"] não encontrado"));

	 }
	
	@GetMapping(value = "/clientes/{clienteId}/pets") 
	 public List<Pet> all (@PathVariable Integer clienteId){ 
              return petRepository.findByCliente_Id(clienteId);
	 } 
	
	@DeleteMapping(value = "/clientes/{clienteId}/pets/{petId}")
	public ResponseEntity<?> deletePet(@PathVariable Integer clienteId,@PathVariable Integer petId){

		if (!clienteRepository.existsById(clienteId)) {
			throw new ResourceNotFoundException("Cliente [clienteId="+clienteId+"] não encontrado");
		}
		
		return petRepository.findById(petId).map(pet ->{
			petRepository.delete(pet);
			   return ResponseEntity.ok().build();
		       }).orElseThrow(() -> new ResourceNotFoundException("Pet [petId="+petId+"] não encontrado"));

	}
	
	@PutMapping(value = "/clientes/{clienteId}/pets/{petId}")
	public ResponseEntity<Pet> updatePet(@PathVariable Integer clienteId,@PathVariable Integer petId,@RequestBody Pet novoPet){

		Cliente cliente = clienteRepository.findById(clienteId).orElseThrow(() -> new ResourceNotFoundException("Cliente [clienteId=\"+clienteId+\"] não encontrado"));
		
		return petRepository.findById(petId).map(pet ->{
			pet.setCliente(cliente);
			pet.setCor(novoPet.getCor());
			pet.setDtNascimento(novoPet.getDtNascimento());
			pet.setNomePet(novoPet.getNomePet());
			pet.setRaca(novoPet.getRaca());
			pet.setSexo(novoPet.getSexo());
			petRepository.save(pet);
			   return ResponseEntity.ok(pet);
		       }).orElseThrow(() -> new ResourceNotFoundException("Pet [petId="+petId+"] não encontrado"));

		
	}
}
