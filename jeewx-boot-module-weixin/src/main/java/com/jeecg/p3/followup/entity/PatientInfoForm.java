package com.jeecg.p3.followup.entity;

/**
 * 患者信息
 */
public class PatientInfoForm {


    private String openId;

    private String name;


    private String birthday;

    private Integer sex;

    private String phone;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "PatientInfoForm{" +
                "openId='" + openId + '\'' +
                ", name='" + name + '\'' +
                ", birthday='" + birthday + '\'' +
                ", sex=" + sex +
                ", phone='" + phone + '\'' +
                '}';
    }
}
