package br.com.vr.autorizador.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.vr.autorizador.domain.CartaoDomain;

/**
 * Repository
 * 
 * @author Julivan Silva
 */
@Repository
public interface AutorizadorRepository extends JpaRepository<CartaoDomain, Long> {

	Optional<CartaoDomain> findByNumeroCartao(Long numeroCartao);
	
}
