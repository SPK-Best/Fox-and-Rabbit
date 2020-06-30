package io.muic.ooc.fab;

import java.util.Iterator;
import java.util.List;

public abstract class Carnivore extends Animal {

    /**
     * The carnivore's food level, which is increased by eating animals.
     */
    private int foodLevel;

    @Override
    public void initialize(boolean randomAge, Field field, Location location) {
        super.initialize(randomAge, field, location);
        foodLevel = RANDOM.nextInt(getRabbitFoodValue());
    }

    /**
     * Make this carnivore more hungry. This could result in the carnivore's death.
     */
    private void incrementHunger() {
        foodLevel--;
        if (foodLevel <= 0) {
            setDead();
        }
    }

    protected abstract boolean isEatable(Animal animal);


    /**
     * Look for rabbits adjacent to the current location. Only the first live
     * rabbit is eaten.
     *
     * @return Where food was found, or null if it wasn't.
     */
    protected Location findFood() {
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while (it.hasNext()) {
            Location where = it.next();
            Animal animal = (Animal) field.getObjectAt(where);
            if(isEatable(animal)) {
                if (animal.isAlive()) {
                    updateFoodLevel(getFoodValue(animal));
                    animal.setDead();
                    return where;
                }
            }
        }
        return null;
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
     */
    @Override
    public void act(List<Animal> newAnimals) {
        incrementHunger();
        super.act(newAnimals);
    }

    private int getRabbitFoodValue() {
        return 9;
    }

    private int getFoxFoodValue() {
        return 15;
    }

    private int getTigerFoodValue() {
        return 25;
    }

    private int getFoodValue(Animal animal) {
        if(animal instanceof Rabbit) {
            return getRabbitFoodValue();
        }else if(animal instanceof Fox) {
            return getFoxFoodValue();
        }else if(animal instanceof Tiger) {
            return getTigerFoodValue();
        }
        return 0;
    }

    protected void updateFoodLevel(int foodLevel) {
        this.foodLevel = foodLevel;
    }
}
