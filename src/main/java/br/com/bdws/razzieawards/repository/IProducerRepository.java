package br.com.bdws.razzieawards.repository;

import br.com.bdws.razzieawards.entity.Producer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProducerRepository extends JpaRepository<Producer, Long> {

}