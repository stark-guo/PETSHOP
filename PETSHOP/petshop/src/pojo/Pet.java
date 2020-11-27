package pojo;

import java.io.Serializable;
import java.util.Date;

public class Pet implements Serializable {
    private int id;//宠物编号
    private String name;//宠物名称
    private String typeName;//宠物类型
    private int health;//宠物健康值
    private int love;//宠物与主人的亲密值
    private Date birthday;//宠物出生日期
    private int ownerId;//主人编号
    private int storeId;//商店编号

    public Pet() {
    }

    public Pet(int id, String name, String typeName, int health, int love, Date birthday, int ownerId, int storeId) {
        this.id = id;
        this.name = name;
        this.typeName = typeName;
        this.health = health;
        this.love = love;
        this.birthday = birthday;
        this.ownerId = ownerId;
        this.storeId = storeId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getLove() {
        return love;
    }

    public void setLove(int love) {
        this.love = love;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    @Override
    public String toString() {
        return "Pet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", typeName='" + typeName + '\'' +
                ", health=" + health +
                ", love=" + love +
                ", birthday='" + birthday + '\'' +
                ", ownerId=" + ownerId +
                ", storeId=" + storeId +
                '}';
    }
}
