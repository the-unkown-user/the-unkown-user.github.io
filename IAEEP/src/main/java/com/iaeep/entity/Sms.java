package com.iaeep.entity;

/**
 * @version 1.0
 * @Classname LoginController
 * @Description TODO
 * @CreateDate 2022/9/24 8:59
 * @Created by DELL
 * @Update DELL
 * @UpdateDate 2022/9/24 8:59
 */
public class Sms {
    private String phoneNumber;
    private String code;
    private Integer outTime;

    public Sms(String phoneNumber){
        this.phoneNumber=phoneNumber;
        this.code=String.valueOf((int)((Math.random()*9+1)*Math.pow(10,5)));
        this.outTime=30;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getOutTime() {
        return outTime;
    }

    public void setOutTime(Integer outTime) {
        this.outTime = outTime;
    }

    @Override
    public String toString() {
        return "Sms{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", code='" + code + '\'' +
                ", outTime=" + outTime +
                '}';
    }
}
