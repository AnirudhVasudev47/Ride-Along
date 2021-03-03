package com.cse8.rideAlong;

public class Offers {

    public String title;
    public String desc;
    public String target;

    public Offers() {
    }

    public Offers(String title, String desc, String target) {
        this.title = title;
        this.desc = desc;
        this.target = target;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
