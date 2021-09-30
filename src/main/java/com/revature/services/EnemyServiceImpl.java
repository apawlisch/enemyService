package com.revature.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.data.EnemyDao;

@Service
public class EnemyServiceImpl implements EnemyService {
	EnemyDao enDao;
	
	@Autowired
	public EnemyServiceImpl(EnemyDao enDao) {
		super();
		this.enDao = enDao;
	}
	
	// view one enemy
	
	// view all enemies
	
	// create enemy
	
	// delete enemy
	
	// add health
	
	// remove health
	
	// restore health to max
	
	// set max health
	
	// set speed
	
	// set initiative
	
	// duplicate enemy?

}
