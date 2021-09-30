package com.revature.beans;

import java.io.Serializable;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("enemy")
public class Enemy implements Serializable {
	@PrimaryKey
	private String name;
	private Type type;
	private Integer currentHitPoints;
	private Integer maxHitPoints;
	private Integer speed;
	private Integer initiative;
	
	public Enemy() {
		super();
	}

	public Enemy(String name, Type type, Integer currentHitPoints, Integer maxHitPoints, Integer speed,
			Integer initiative) {
		this();
		this.name = name;
		this.type = type;
		this.currentHitPoints = currentHitPoints;
		this.maxHitPoints = maxHitPoints;
		this.speed = speed;
		this.initiative = initiative;
	}

	public Enemy(String name) {
		this();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Integer getCurrentHitPoints() {
		return currentHitPoints;
	}

	public void setCurrentHitPoints(Integer currentHitPoints) {
		this.currentHitPoints = currentHitPoints;
	}

	public Integer getMaxHitPoints() {
		return maxHitPoints;
	}

	public void setMaxHitPoints(Integer maxHitPoints) {
		this.maxHitPoints = maxHitPoints;
	}

	public Integer getSpeed() {
		return speed;
	}

	public void setSpeed(Integer speed) {
		this.speed = speed;
	}

	public Integer getInitiative() {
		return initiative;
	}

	public void setInitiative(Integer initiative) {
		this.initiative = initiative;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((currentHitPoints == null) ? 0 : currentHitPoints.hashCode());
		result = prime * result + ((initiative == null) ? 0 : initiative.hashCode());
		result = prime * result + ((maxHitPoints == null) ? 0 : maxHitPoints.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((speed == null) ? 0 : speed.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Enemy other = (Enemy) obj;
		if (currentHitPoints == null) {
			if (other.currentHitPoints != null)
				return false;
		} else if (!currentHitPoints.equals(other.currentHitPoints))
			return false;
		if (initiative == null) {
			if (other.initiative != null)
				return false;
		} else if (!initiative.equals(other.initiative))
			return false;
		if (maxHitPoints == null) {
			if (other.maxHitPoints != null)
				return false;
		} else if (!maxHitPoints.equals(other.maxHitPoints))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (speed == null) {
			if (other.speed != null)
				return false;
		} else if (!speed.equals(other.speed))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Enemy [name=" + name + ", type=" + type + ", currentHitPoints=" + currentHitPoints + ", maxHitPoints="
				+ maxHitPoints + ", speed=" + speed + ", initiative=" + initiative + "]";
	}
	
	

	
}
