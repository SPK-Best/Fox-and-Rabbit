package io.muic.ooc.fab;

public class Fox extends Carnivore {

    @Override
    protected int getMaxAge() {
        return 150;
    }

    @Override
    protected double getBreedingProbability() {
        return 0.08;
    }

    @Override
    protected int getMaxLitterSize() {
        return 2;
    }

    @Override
    protected int getBreedingAge() {
        return 15;
    }

    @Override
    protected boolean isEatable(Animal animal) {
        return animal instanceof Rabbit;
    }
}