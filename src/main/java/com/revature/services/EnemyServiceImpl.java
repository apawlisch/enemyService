package com.revature.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.beans.Enemy;
import com.revature.data.EnemyDao;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class EnemyServiceImpl implements EnemyService {
	EnemyDao enDao;
	
	@Autowired
	public EnemyServiceImpl(EnemyDao enDao) {
		super();
		this.enDao = enDao;
	}
	
	// view one enemy
	@Override
	public Mono<Enemy> getOneEnemy(String name) {
		return enDao.findByName(name);
	}
	
	// view all enemies
	@Override
	public Flux<Enemy> getAllEnemies() {
		return enDao.findAll();
	}
	
	// add health
	@Override
	public Mono<Enemy> addHealth(String name, Integer value) {
		return enDao.findByName(name).flatMap( enemy -> {
			Integer amount = value + enemy.getCurrentHitPoints();
			if(amount > enemy.getMaxHitPoints()) {
				enemy.setCurrentHitPoints(enemy.getCurrentHitPoints());
			} else {
				enemy.setCurrentHitPoints(amount);
			}
			return enDao.save(enemy);
		});
	}
	
	// remove health
	@Override
	public Mono<Enemy> removeHealth(String name, Integer value) {
		return enDao.findByName(name).flatMap( enemy -> {
			enemy.setCurrentHitPoints( enemy.getCurrentHitPoints() - value);
			return enDao.save(enemy);
		});
	}
	
	// restore health to max
	@Override
	public Mono<Enemy> restoreHealth(String name) {
		return enDao.findByName(name).flatMap(enemy -> {
			enemy.setCurrentHitPoints(enemy.getMaxHitPoints());
			return enDao.save(enemy);
		});
	}
	
	// set max health
	@Override
	public Mono<Enemy> setMaxHealth(String name, Integer value) {
		return enDao.findByName(name).flatMap(enemy -> {
			enemy.setMaxHitPoints(value);
			return enDao.save(enemy);
		});
	}
	
	// set speed
	@Override
	public Mono<Enemy> setSpeed(String name, Integer value) {
		return enDao.findByName(name).flatMap(enemy -> {
			enemy.setSpeed(value);
			return enDao.save(enemy);
		});
	}
	
	// set initiative
	@Override
	public Mono<Enemy> setInitiative(String name, Integer value) {
		return enDao.findByName(name).flatMap(enemy -> {
			enemy.setInitiative(value);
			return enDao.save(enemy);
		});
	}
	
	// create with name
	@Override
	public Mono<Enemy> createName(Enemy submitted) {
		Enemy enemy = new Enemy();
		enemy.setName(submitted.getName());
		enemy.setType(null);
		enemy.setMaxHitPoints(null);
		enemy.setCurrentHitPoints(null);
		enemy.setSpeed(null);
		enemy.setInitiative(null);
		
		return enDao.save(enemy);
	}
	
	// create with name, type, and health
	@Override
	public Mono<Enemy> createNameTypeHealth(Enemy submitted) {
		Enemy enemy = new Enemy();
		enemy.setName(submitted.getName());
		enemy.setType(submitted.getType());
		enemy.setMaxHitPoints(submitted.getMaxHitPoints());
		enemy.setCurrentHitPoints(submitted.getMaxHitPoints());
		enemy.setSpeed(null);
		enemy.setInitiative(null);
		
		return enDao.save(enemy);
	}
	
	// create with name, type, health, and speed
	@Override
	public Mono<Enemy> createNameTypeSpeedHealth(Enemy submitted) {
		Enemy enemy = new Enemy();
		enemy.setName(submitted.getName());
		enemy.setType(submitted.getType());
		enemy.setMaxHitPoints(submitted.getMaxHitPoints());
		enemy.setCurrentHitPoints(submitted.getMaxHitPoints());
		enemy.setSpeed(submitted.getSpeed());
		enemy.setInitiative(null);
		
		return enDao.save(enemy);
		
	}
	
	@Override
	public Mono<Enemy> routeCreate(Enemy submitted) {
		// if speed != null, createNameTypeSpeedHealth
		// then if health type != null, createNameTypeHealth
		// else, createName
		
		if (submitted.getSpeed() != null ) {
			// speed is full, createNameTypeSpeedHealth
			return createNameTypeSpeedHealth(submitted);
		} else {
			// speed is null
			
			if (submitted.getType() != null && submitted.getMaxHitPoints() != null) {
				// type and HP are full, createNameTypeHealth
				return createNameTypeHealth(submitted);
				
			} else {
				// all except name is null
				return createName(submitted);
			}
		}
		
	}
	
	// delete enemy
	@Override
	public Mono<Void> deleteEnemy(String name) {
		return enDao.delete(new Enemy(name));
	}
	
	// duplicate enemy?
	// get name of enemy you want to duplicate + name of enemy you want to make
	@Override
	public Mono<Enemy> duplicateEnemy(Enemy newName, String oldEnemyName) {
		return enDao.findByName(oldEnemyName).flatMap(oldEnemy -> {
			Enemy enemy = new Enemy();
			enemy.setName(newName.getName());
			enemy.setType(oldEnemy.getType());
			enemy.setMaxHitPoints(oldEnemy.getMaxHitPoints());
			enemy.setCurrentHitPoints(oldEnemy.getMaxHitPoints());
			enemy.setSpeed(oldEnemy.getSpeed());
			enemy.setInitiative(null);
			
			return enDao.save(enemy);
		});
	}

}
