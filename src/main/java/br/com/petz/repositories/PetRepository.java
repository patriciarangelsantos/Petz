package br.com.petz.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.petz.domain.Pet;

@Repository
public interface PetRepository extends JpaRepository<Pet, Integer> {
	
	List<Pet> findByCliente_Id(Integer idCliente);
	

}
