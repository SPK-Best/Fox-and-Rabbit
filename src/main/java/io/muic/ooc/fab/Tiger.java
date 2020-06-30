package io.muic.ooc.fab;

public class Tiger extends Carnivore {

    @Override
    protected int getMaxAge() {
        return 200;
    }

    @Override
    protected double getBreedingProbability() {
        return 0.04;
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
    protected boolean isEatable(Animal animal) {
        return animal instanceof Fox || animal instanceof Rabbit;
    }
}