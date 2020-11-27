package service;

import pojo.Pet;

/**
 * 宠物工厂
 */
public interface PetFactory {
    /**
     * 孵化新宠物
     */
    Pet breedNewPet(String[] newParam);
}
