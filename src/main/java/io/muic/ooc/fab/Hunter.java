package io.muic.ooc.fab;

public class Hunter extends Carnivore {

    @Override
    protected double getBreedingProbability() {
        return 0.001;
    }

    @Override
    protected int getMaxLitterSize() {
        return 2;
    }

    @Override
    protected int getBreedingAge() {
        return 40;
    }

    @Override
    protected int getMaxAge() {
        return 5000;
    }

    @Override
    protected boolean isEatable(Animal animal) {
        return animal instanceof Tiger || animal instanceof Fox || animal instanceof Rabbit;
    }

    @Override
    protected void setDead() {
    }
}