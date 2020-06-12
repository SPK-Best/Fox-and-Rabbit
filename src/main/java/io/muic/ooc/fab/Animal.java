package io.muic.ooc.fab;

import java.util.List;
import java.util.Random;

/**
* Use DRY (Don't repeat yourself) Method
* - Group the similar things to be together
* */

public abstract class Animal {

    // A shared random number generator to control breeding.
    protected static final Random RANDOM = new Random();
    // Whether the animal is alive or not.
    private boolean alive = true;
    // The fox's position.
    private Location location;
    // The field occupied.
    protected Field field;
    // The rabbit's age.
    private int age = 0;


    public Animal(boolean randomAge, Field field, Location location) {
        this.field = field;
        setLocation(location);
        if (randomAge) {
            age = RANDOM.nextInt(getMaxAge());
        }
    }

    protected abstract Location moveToNewLocation();

    public void act(List<Animal> newAnimals){
        incrementAge();
        if (isAlive()) {
            giveBirth(newAnimals);
            // Try to move into a free location.
            Location newLocation = moveToNewLocation();
            if (newLocation != null) {
                setLocation(newLocation);
            } else {
                // Overcrowding.
                setDead();
            }
        }
    }

    protected abstract Animal breedOne(boolean randomAge, Field field, Location location);

    /**
     * Check whether or not this rabbit is to give birth at this step. New
     * births will be made into free adjacent locations.
     *
     * @param newAnimals A list to return newly born rabbits.
     */
    private void giveBirth(List<Animal> newAnimals) {
        // New animals are born into adjacent locations.
        // Get a list of adjacent free locations.
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for (int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Animal young = breedOne(false, field, loc);
            newAnimals.add(young);
        }
    }

    /**
     * Check whether the animal is alive or not.
     *
     * @return True if the animal is still alive.
     */
    public boolean isAlive() {
        return alive;
    }

    /**
     * Indicate that the rabbit is no longer alive. It is removed from the
     * field.
     */
    protected void setDead() {
        alive = false;
        if (location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }

    /**
     * Return the animal's location.
     *
     * @return The animal's location.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Place the animal at the new location in the given field.
     *
     * @param newLocation The animal's new location.
     */
    protected void setLocation(Location newLocation) {
        if (location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }

    /**
    * Liskov Substitution - Code of Animal does not depend on the subtype
     * (Do not need to worry about the different age of animals)
    * */
    protected abstract int getMaxAge();

    /**
     * Increase the age. This could result in the fox's death.
     */
    protected void incrementAge() {
        age++;
        if (age > getMaxAge()) {
            setDead();
        }
    }

    protected abstract double getBreedingProbability();

    protected abstract int getMaxLitterSize();

    protected abstract int getBreedingAge();

    /**
     * Generate a number representing the number of births, if it can breed.
     *
     * @return The number of births (may be zero).
     */
    protected int breed() {
        int births = 0;
        if (canBreed() && RANDOM.nextDouble() <= getBreedingProbability()) {
            births = RANDOM.nextInt(getMaxLitterSize()) + 1;
        }
        return births;
    }

    /**
     * A animal can breed if it has reached the breeding age.
     *
     * @return true if the animal can breed, false otherwise.
     */
    protected boolean canBreed() {
        return age >= getBreedingAge();
    }
}