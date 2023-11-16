package dev.elliotjarnit.elliotengine.Objects;

import dev.elliotjarnit.elliotengine.Utils.Vector3;

public abstract class EEntity extends EObject {
    private double speed;
    private double health;
    private double  maxHealth;

    public EEntity() {
        super();
        this.speed = 1;
        this.health = -1;
        this.maxHealth = -1;
    }
    public EEntity(Vector3 origin) {
        super(origin);
        this.speed = 1;
        this.health = -1;
        this.maxHealth = -1;
    }
    public EEntity(Vector3 origin, double health) {
        super(origin);
        this.speed = 1;
        this.health = health;
        this.maxHealth = health;
    }
    public EEntity(Vector3 origin, double health, double speed) {
        super(origin);
        this.speed = speed;
        this.health = health;
        this.maxHealth = health;
    }

    public void damage() {
        this.health -= 1;
    }

    public void damage(double damage) {
        this.health -= damage;
    }

    public void heal() {
        this.health += 1;
    }

    public void heal(double heal) {
        this.health += heal;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public void setMaxHealth(double maxHealth) {
        this.maxHealth = maxHealth;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getHealth() {
        return this.health;
    }

    public double getMaxHealth() {
        return this.maxHealth;
    }

    public double getSpeed() {
        return this.speed;
    }

    public boolean isDead() {
        return this.health <= 0;
    }
}
