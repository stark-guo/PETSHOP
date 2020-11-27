package service.impl;

import pojo.Pet;
import service.PetFactory;

public class PetFactoryImpl implements PetFactory {

     private Pet pet = new Pet();
    /**
     * 宠物工厂孵化新宠物
     * @param newParam
     * @return
     */
    @Override
    public Pet breedNewPet(String[] newParam) {
        pet.setName(newParam[0]);
        pet.setTypeName(newParam[1]);
        pet.setHealth(Integer.parseInt(newParam[2]));
        pet.setLove(Integer.parseInt(newParam[3]));
        pet.setStoreId(Integer.parseInt(newParam[4]));

        return pet;
    }
}
