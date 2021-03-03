package com.cse8.rideAlong;

public class Rewards {

    public String storeName;
    public String storeBanner;
    public String storeLogo;
    public String storeDesc;

    public String getStoreDesc() {
        return storeDesc;
    }

    public void setStoreDesc(String storeDesc) {
        this.storeDesc = storeDesc;
    }

    public Rewards(String storeName, String storeBanner, String storeLogo, String storeDesc) {
        this.storeName = storeName;
        this.storeBanner = storeBanner;
        this.storeLogo = storeLogo;
        this.storeDesc = storeDesc;
    }

    public String getStoreLogo() {
        return storeLogo;
    }

    public void setStoreLogo(String storeLogo) {
        this.storeLogo = storeLogo;
    }

    public Rewards() {
    }


    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreBanner() {
        return storeBanner;
    }

    public void setStoreBanner(String storeBanner) {
        this.storeBanner = storeBanner;
    }
}
