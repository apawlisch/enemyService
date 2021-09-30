package com.revature.data;

import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;

import com.revature.beans.Enemy;

import reactor.core.publisher.Mono;

@Repository
public interface EnemyDao extends ReactiveCassandraRepository<Enemy, String>{
	Mono<Enemy> findByName(String name);

}
