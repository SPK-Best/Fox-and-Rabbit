package io.muic.ooc.fab;

import java.util.Iterator;
import java.util.List;

public class Tiger extends Animal {

    private static final int FOX_FOOD_VALUE = 10;

    // Individual characteristics (instance fields).
    // The fox's food level, which is increased by eating rabbits.
    private int foodLevel;

    /**
     * Create a fox. A fox can be created as a new born (age zero and not
     * hungry) or with a random age and food level.
     *
     * @param randomAge If true, the fox will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    @Override
    public void initialize(boolean randomAge, Field field, Location location) {
        super.initialize(randomAge, field, location);
        foodLevel = RANDOM.nextInt(getRabbitFoodValue() + FOX_FOOD_VALUE);
    }

    @Override
    protected Location moveToNewLocation() {
        Location newLocation = findFood();
        if (newLocation == null) {
            // No food found - try to move to a free location.
            newLocation = field.freeAdjacentLocation(getLocation());
        }
        return newLocation;
    }

    /**
     * This is what the fox does most of the time: it hunts for rabbits. In the
     * process, it might breed, die of hunger, or die of old age.
     *
     * @param newAnimals A list to return newly born foxes.
     */
    @Override
    public void act(List<Animal> newAnimals) {
        incrementAge();
        super.act(newAnimals);
    }

    /**
     * Make this fox more hungry. This could result in the fox's death.
     */
    private void incrementHunger() {
        foodLevel--;
        if (foodLevel <= 0) {
            setDead();
        }
    }

    /**
     * Look for rabbits and foxes  adjacent to the current location. Only the first live
     * rabbit and foxes are eaten.
     *
     * @return Where food was found, or null if it wasn't.
     */
    private Location findFood() {
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while (it.hasNext()) {
            Location where = it.next();
            Object animal = field.getObjectAt(where);
            if (animal instanceof Rabbit) {
                Rabbit rabbit = (Rabbit) animal;
                if (rabbit.isAlive()) {
                    rabbit.setDead();
                    foodLevel = getRabbitFoodValue();
                    return where;
                }
            }
            if (animal instanceof Fox){
                Fox fox = (Fox) animal;
                if (fox.isAlive()) {
                    fox.setDead();
                    foodLevel = FOX_FOOD_VALUE;
                    return where;
                }
            }
        }
        return null;
    }

    @Override
    protected int getMaxAge() {
        return 200;
    }

    @Override
    protected double getBreedingProbability() {
        return 0.015;
    }

    @Override
    protected int getMaxLitterSize() {
        return 2;
    }

    @Override
    protected int getBreedingAge() {
        return 30;
    }

    @Override
    protected int getRabbitFoodValue() {
        return 9;
    }
}