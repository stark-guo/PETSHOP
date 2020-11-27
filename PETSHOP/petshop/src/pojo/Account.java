package pojo;

import java.io.Serializable;
import java.util.Date;

public class Account implements Serializable,Comparable {
    private int id; //账目编号
    private int dealType; //交易类型
    private int petId; //宠物编号
    private int sellerId; //卖家编号
    private int buyerId; //买家编号
    private double price; //交易价格
    private Date dealTime; //交易时间

    public Account() {
    }

    public Account(int id, int dealType, int petId, int sellerId, int buyerId, double price, Date dealTime) {
        this.id = id;
        this.dealType = dealType;
        this.petId = petId;
        this.sellerId = sellerId;
        this.buyerId = buyerId;
        this.price = price;
        this.dealTime = dealTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDealType() {
        return dealType;
    }

    public void setDealType(int dealType) {
        this.dealType = dealType;
    }

    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public int getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(int buyerId) {
        this.buyerId = buyerId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getDealTime() {
        return dealTime;
    }

    public void setDealTime(Date dealTime) {
        this.dealTime = dealTime;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", dealType=" + dealType +
                ", petId=" + petId +
                ", sellerId=" + sellerId +
                ", buyerId=" +  buyerId+
                ", price=" + price +
                ", dealTime='" + dealTime + '\'' +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
