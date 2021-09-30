package com.revature.services;

import com.revature.beans.Enemy;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EnemyService {
	Mono<Enemy> getOneEnemy(String name);
	
	Flux<Enemy> getAllEnemies();
	
	Mono<Enemy> addHealth(String name, Integer value);
	
	Mono<Enemy> removeHealth(String name, Integer value);
	
	Mono<Enemy> restoreHealth(String name);
	
	Mono<Enemy> setMaxHealth(String name, Integer value);

	Mono<Enemy> setSpeed(String name, Integer value);
	
	Mono<Enemy> setInitiative(String name, Integer value);
	
	Mono<Enemy> createName(Enemy submitted);
	
	Mono<Enemy> createNameTypeHealth(Enemy submitted);
	
	Mono<Enemy> createNameTypeSpeedHealth(Enemy submitted);
	
	Mono<Enemy> routeCreate(Enemy submitted);
	
	Mono<Void> deleteEnemy(String name);
	
	Mono<Enemy> duplicateEnemy(Enemy newName, String oldEnemyName);

}
