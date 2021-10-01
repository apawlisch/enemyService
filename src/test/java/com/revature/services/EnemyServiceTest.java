package com.revature.services;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.revature.beans.Enemy;
import com.revature.beans.Type;
import com.revature.data.EnemyDao;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class EnemyServiceTest {
	
	@InjectMocks
	private EnemyServiceImpl service;
	
	@Mock
	private EnemyDao enDao;
	
	private static Enemy enemy;
	private static Enemy enemy2;
	
	@BeforeAll
	static void setUp() {
		enemy = new Enemy();
		enemy2 = new Enemy();
	}
	
	@BeforeEach
	void setUpTests() {
		MockitoAnnotations.openMocks(this);
		
		// reset for tests
		enemy.setName("testEn");
		enemy.setType(Type.UNDEAD);
		enemy.setCurrentHitPoints(25);
		enemy.setMaxHitPoints(30);
		enemy.setSpeed(25);
		enemy.setInitiative(null);
		
		enemy2.setName("testEn2");
		enemy2.setType(null);
		enemy2.setCurrentHitPoints(null);
		enemy2.setMaxHitPoints(null);
		enemy2.setSpeed(null);
		enemy2.setInitiative(null);
	}
	
	// getOneEnemy
	@Test
	void testGetOneEnemy() {
		Mockito.when(enDao.findByName("testEn")).thenReturn(Mono.just(enemy));
		
		StepVerifier.create(service.getOneEnemy("testEn"))
			.expectNext(enemy)
			.verifyComplete();
	}
	
	// getAllEnemies
	@Test
	void testGetAllEnemies() {
		
		Mockito.when(enDao.findAll()).thenReturn(Flux.just(enemy, enemy2));
		
		StepVerifier.create(service.getAllEnemies())
			.expectNext(enemy)
			.expectNext(enemy2)
			.verifyComplete();
	}
	
	// addHealth regular
	@Test
	void testAddHealthRegular() {
		// add 3 health to get 28 health
		enemy2.setCurrentHitPoints(28);
		
		Mockito.when(enDao.findByName("testEn")).thenReturn(Mono.just(enemy));
		Mockito.when(enDao.save(Mockito.any())).thenReturn(Mono.just(enemy));
		
		StepVerifier.create(service.addHealth("testEn", 3))
			.expectNextMatches(newEn -> {
				return enemy2.getCurrentHitPoints().equals(newEn.getCurrentHitPoints());
			})
			.verifyComplete();
	}
	
	// addHealth but it hits the max
	@Test
	void testAddHealthHitsMax() {
		// try to add 6 to health, should return max amount though
		enemy2.setCurrentHitPoints(enemy.getMaxHitPoints());
		
		Mockito.when(enDao.findByName("testEn")).thenReturn(Mono.just(enemy));
		Mockito.when(enDao.save(Mockito.any())).thenReturn(Mono.just(enemy2));
		
		StepVerifier.create(service.addHealth("testEn", 6))
			.expectNextMatches(newEn -> {
				return enemy2.getCurrentHitPoints().equals(newEn.getCurrentHitPoints());
			})
			.verifyComplete();
	}
	
	// removeHealth
	@Test
	void testRemoveHealth() {
		// remove 5 health to hit 20 HP
		enemy2.setCurrentHitPoints(20);
		
		Mockito.when(enDao.findByName("testEn")).thenReturn(Mono.just(enemy));
		Mockito.when(enDao.save(Mockito.any())).thenReturn(Mono.just(enemy));
		
		StepVerifier.create(service.removeHealth("testEn", 5))
			.expectNextMatches(newEn -> {
				return enemy2.getCurrentHitPoints().equals(newEn.getCurrentHitPoints());
			})
			.verifyComplete();
	}
	
	// restoreHealth
	@Test
	void testRestoreHealth() {
		enemy2.setCurrentHitPoints(enemy.getMaxHitPoints());
		
		Mockito.when(enDao.findByName("testEn")).thenReturn(Mono.just(enemy));
		Mockito.when(enDao.save(Mockito.any())).thenReturn(Mono.just(enemy));
		
		StepVerifier.create(service.restoreHealth("testEn"))
			.expectNextMatches(newEn -> {
				return enemy2.getCurrentHitPoints().equals(newEn.getCurrentHitPoints());
			})
			.verifyComplete();
	}
	
	// setMaxHealth
	@Test
	void testSetMaxHealth() {
		enemy2.setMaxHitPoints(40);
		
		Mockito.when(enDao.findByName("testEn")).thenReturn(Mono.just(enemy));
		Mockito.when(enDao.save(Mockito.any())).thenReturn(Mono.just(enemy));
		
		StepVerifier.create(service.setMaxHealth("testEn", 40))
			.expectNextMatches(newEn -> {
				return enemy2.getMaxHitPoints().equals(newEn.getMaxHitPoints());
			})
			.verifyComplete();
	}
	
	// setSpeed
	@Test
	void testSetSpeed() {
		enemy2.setSpeed(10);
		
		Mockito.when(enDao.findByName("testEn")).thenReturn(Mono.just(enemy));
		Mockito.when(enDao.save(Mockito.any())).thenReturn(Mono.just(enemy));
		
		StepVerifier.create(service.setSpeed("testEn", 10))
			.expectNextMatches(newEn -> {
				return enemy2.getSpeed().equals(newEn.getSpeed());
			})
			.verifyComplete();
	}
	
	// setInitiative
	@Test
	void testSetInitiative() {
		enemy2.setInitiative(10);
		
		Mockito.when(enDao.findByName("testEn")).thenReturn(Mono.just(enemy));
		Mockito.when(enDao.save(Mockito.any())).thenReturn(Mono.just(enemy));
		
		StepVerifier.create(service.setInitiative("testEn", 10))
			.expectNextMatches(newEn -> {
				return enemy2.getInitiative().equals(newEn.getInitiative());
			})
			.verifyComplete();
	}
	
	// createName
	@Test
	void testCreateName() {
		enemy2.setName("createName");
		
		Mockito.when(enDao.save(Mockito.any())).thenReturn(Mono.just(enemy2));
		
		StepVerifier.create(service.createName(enemy2))
			.expectNextMatches(newEn -> {
				return enemy2.getName().equals(newEn.getName())
						&& newEn.getSpeed() == null
						&& newEn.getInitiative() == null
						&& newEn.getMaxHitPoints() == null
						&& newEn.getType() == null;
			})
			.verifyComplete();
	}
	
	// createNameTypeHealth
	@Test
	void testCreateNameTypeHealth() {
		enemy2.setName("createName");
		enemy2.setType(Type.UNDEAD);
		enemy2.setMaxHitPoints(12);
		
		Mockito.when(enDao.save(Mockito.any())).thenReturn(Mono.just(enemy2));
		
		StepVerifier.create(service.createNameTypeHealth(enemy2))
			.expectNextMatches(newEn -> {
				return enemy2.getName().equals(newEn.getName())
						&& newEn.getSpeed() == null
						&& newEn.getInitiative() == null
						&& enemy2.getMaxHitPoints().equals(newEn.getMaxHitPoints())
						&& enemy2.getType().equals(newEn.getType());
			})
			.verifyComplete();
	}
	
	// createNameTypeSpeedHealth
	@Test
	void testCreateNameTypeSpeedHealth() {
		enemy2.setName("createName");
		enemy2.setType(Type.UNDEAD);
		enemy2.setMaxHitPoints(12);
		enemy2.setSpeed(10);
		
		Mockito.when(enDao.save(Mockito.any())).thenReturn(Mono.just(enemy2));
		
		StepVerifier.create(service.createNameTypeSpeedHealth(enemy2))
			.expectNextMatches(newEn -> {
				return enemy2.getName().equals(newEn.getName())
						&& enemy2.getSpeed().equals(newEn.getSpeed())
						&& newEn.getInitiative() == null
						&& enemy2.getMaxHitPoints().equals(newEn.getMaxHitPoints())
						&& enemy2.getType().equals(newEn.getType());
			})
			.verifyComplete();
	}
	
	// routeCreate - Name
	@Test
	void testRouteCreateName() {
		enemy2.setName("createName");
		
		Mockito.when(enDao.save(Mockito.any())).thenReturn(Mono.just(enemy2));
		
		StepVerifier.create(service.routeCreate(enemy2))
			.expectNextMatches(newEn -> {
				return enemy2.getName().equals(newEn.getName())
						&& newEn.getSpeed() == null
						&& newEn.getInitiative() == null
						&& newEn.getMaxHitPoints() == null
						&& newEn.getType() == null;
			})
			.verifyComplete();
	}
	
	// routeCreate - NameTypeHealth
	@Test
	void testRouteCreateNameTypeHealth() {
		enemy2.setName("createName");
		enemy2.setType(Type.UNDEAD);
		enemy2.setMaxHitPoints(12);
		
		Mockito.when(enDao.save(Mockito.any())).thenReturn(Mono.just(enemy2));
		
		StepVerifier.create(service.routeCreate(enemy2))
			.expectNextMatches(newEn -> {
				return enemy2.getName().equals(newEn.getName())
						&& newEn.getSpeed() == null
						&& newEn.getInitiative() == null
						&& enemy2.getMaxHitPoints().equals(newEn.getMaxHitPoints())
						&& enemy2.getType().equals(newEn.getType());
			})
			.verifyComplete();
	}
	
	// routeCreate - NameTypeSpeedHealth
	@Test
	void testRouteCreateNameTypeSpeedHealth() {
		enemy2.setName("createName");
		enemy2.setType(Type.UNDEAD);
		enemy2.setMaxHitPoints(12);
		enemy2.setSpeed(10);
		
		Mockito.when(enDao.save(Mockito.any())).thenReturn(Mono.just(enemy2));
		
		StepVerifier.create(service.routeCreate(enemy2))
			.expectNextMatches(newEn -> {
				return enemy2.getName().equals(newEn.getName())
						&& enemy2.getSpeed().equals(newEn.getSpeed())
						&& newEn.getInitiative() == null
						&& enemy2.getMaxHitPoints().equals(newEn.getMaxHitPoints())
						&& enemy2.getType().equals(newEn.getType());
			})
			.verifyComplete();
	}
	
	// deleteEnemy
	
	// duplicateEnemy
	@Test
	void testDuplicateEnemy() {
		enemy2.setName("duplicate");
		
		Enemy testEn = new Enemy();
		testEn.setName(enemy2.getName());
		testEn.setType(enemy.getType());
		testEn.setSpeed(enemy.getSpeed());
		testEn.setMaxHitPoints(enemy.getMaxHitPoints());
		
		Mockito.when(enDao.findByName("testEn")).thenReturn(Mono.just(enemy));
		Mockito.when(enDao.save(enemy2)).thenReturn(Mono.just(enemy2));
		
		StepVerifier.create(service.duplicateEnemy(enemy2, "testEn"))
		.expectNextMatches(newEn -> {
			return testEn.getName().equals(newEn.getName())
					&& testEn.getSpeed().equals(newEn.getSpeed())
					&& testEn.getInitiative() == null
					&& testEn.getMaxHitPoints().equals(newEn.getMaxHitPoints())
					&& testEn.getType().equals(newEn.getType());
		})
		.verifyComplete();
	}

}
