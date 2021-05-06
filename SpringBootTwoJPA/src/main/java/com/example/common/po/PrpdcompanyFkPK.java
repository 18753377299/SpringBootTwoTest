package com.example.common.po;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class PrpdcompanyFkPK implements Serializable {
    private String comCode;
//    private String insurerCode;

    @Column(name = "comcode")
    @Id
    public String getComCode() {
        return comCode;
    }

    public void setComCode(String comCode) {
        this.comCode = comCode;
    }

//    @Column(name = "insurercode")
//    @Id
//    public String getInsurerCode() {
//        return insurerCode;
//    }
//
//    public void setInsurerCode(String insurerCode) {
//        this.insurerCode = insurerCode;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrpdcompanyFkPK that = (PrpdcompanyFkPK) o;
        return Objects.equals(comCode, that.comCode);
//                &&
//                Objects.equals(insurerCode, that.insurerCode
//                );
    }

    @Override
    public int hashCode() {
//        return Objects.hash(comCode, insurerCode);
        return Objects.hash(comCode);
    }
}
