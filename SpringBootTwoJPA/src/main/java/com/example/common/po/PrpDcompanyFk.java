package com.example.common.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@EntityListeners(AuditingEntityListener.class)
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "prpdcompany")
//@IdClass(PrpdcompanyFkPK.class)
public class PrpDcompanyFk implements Serializable {
    private static final long serialVersionUID = 1L;

    private String comCode;

    private String comCName;
    private String comEName;
    private String comShortName;
    private String addressCName;
    private String addressEName;
    private String postCode;
    private String phoneNumber;
    private String faxNumber;
    private String upperComCode;
    private String insurerName;
    private String comType;
    private String comFlag;
    private String centerFlag;
    private Integer comLevel;
    private String branchType;
    private String upperPath;
    private String comCodeCIRC;
    private String licenseNo;
    private String email;
    private String remark1;
    private String comKind;
    private String manager;
    private String accountant;
    private String remark;
    private String newComCode;
    private Date validDate;
    private Date invalidDate;
    private String validStatus;
    private String acntUnit;
    private String articleCode;
    private String updateFlag;
    private Date updateDate;
    private String operatorComCode;
    private String flag;
    private String specialFlag;
    private String hrLevelCode;
    private String workTime;
    private String longitude;
    private String latitude;
    private String countyTerritory;
	private String insurerCode;
	private String oldUpperPath;
	private String serialNo;
	private String isCenterCity;
	private Date insertTimeForHis;
	private Date operateTimeForHis;
	private String disable;
	private String isEcargo;

    @Id
    @Column(name = "comcode")
    public String getComCode() {
        return comCode;
    }

    public void setComCode(String comCode) {
        this.comCode = comCode;
    }

    @Transient
//    @Id
//    @Column(name = "insurercode")
    public String getInsurerCode() {
        return insurerCode;
    }
    public void setInsurerCode(String insurerCode) {
        this.insurerCode = insurerCode;
    }

    @Basic
    @Column(name = "comcname")
    public String getComCName() {
        return comCName;
    }

    public void setComCName(String comCName) {
        this.comCName = comCName;
    }
    @Basic
    @Column(name = "comename")
    public String getComEName() {
        return comEName;
    }

    public void setComEName(String comEName) {
        this.comEName = comEName;
    }
    @Basic
    @Column(name = "comshortname")
    public String getComShortName() {
        return comShortName;
    }

    public void setComShortName(String comShortName) {
        this.comShortName = comShortName;
    }
    @Basic
    @Column(name = "addresscname")
    public String getAddressCName() {
        return addressCName;
    }

    public void setAddressCName(String addressCName) {
        this.addressCName = addressCName;
    }
    @Basic
    @Column(name = "addressename")
    public String getAddressEName() {
        return addressEName;
    }

    public void setAddressEName(String addressEName) {
        this.addressEName = addressEName;
    }
    @Basic
    @Column(name = "postcode")
    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }
    @Basic
    @Column(name = "phonenumber")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    @Basic
    @Column(name = "faxnumber")
    public String getFaxNumber() {
        return faxNumber;
    }

    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }
    @Basic
    @Column(name = "uppercomcode")
    public String getUpperComCode() {
        return upperComCode;
    }

    public void setUpperComCode(String upperComCode) {
        this.upperComCode = upperComCode;
    }
    @Basic
    @Column(name = "insurername")
    public String getInsurerName() {
        return insurerName;
    }

    public void setInsurerName(String insurerName) {
        this.insurerName = insurerName;
    }
    @Basic
    @Column(name = "comtype")
    public String getComType() {
        return comType;
    }

    public void setComType(String comType) {
        this.comType = comType;
    }
    @Basic
    @Column(name = "comflag")
    public String getComFlag() {
        return comFlag;
    }

    public void setComFlag(String comFlag) {
        this.comFlag = comFlag;
    }
    @Basic
    @Column(name = "centerflag")
    public String getCenterFlag() {
        return centerFlag;
    }

    public void setCenterFlag(String centerFlag) {
        this.centerFlag = centerFlag;
    }
    @Basic
    @Column(name = "comlevel")
    public Integer getComLevel() {
        return comLevel;
    }

    public void setComLevel(Integer comLevel) {
        this.comLevel = comLevel;
    }
    @Basic
    @Column(name = "branchtype")
    public String getBranchType() {
        return branchType;
    }

    public void setBranchType(String branchType) {
        this.branchType = branchType;
    }
    @Basic
    @Column(name = "upperpath")
    public String getUpperPath() {
        return upperPath;
    }

    public void setUpperPath(String upperPath) {
        this.upperPath = upperPath;
    }
    @Basic
    @Column(name = "comcodecirc")
    public String getComCodeCIRC() {
        return comCodeCIRC;
    }

    public void setComCodeCIRC(String comCodeCIRC) {
        this.comCodeCIRC = comCodeCIRC;
    }
    @Basic
    @Column(name = "licenseno")
    public String getLicenseNo() {
        return licenseNo;
    }

    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo;
    }
    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    @Basic
    @Column(name = "remark1")
    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1;
    }
    @Basic
    @Column(name = "comkind")
    public String getComKind() {
        return comKind;
    }

    public void setComKind(String comKind) {
        this.comKind = comKind;
    }
    @Basic
    @Column(name = "manager")
    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }
    @Basic
    @Column(name = "accountant")
    public String getAccountant() {
        return accountant;
    }

    public void setAccountant(String accountant) {
        this.accountant = accountant;
    }
    @Basic
    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    @Basic
    @Column(name = "newcomcode")
    public String getNewComCode() {
        return newComCode;
    }

    public void setNewComCode(String newComCode) {
        this.newComCode = newComCode;
    }
    @Basic
    @Column(name = "validdate")
    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date getValidDate() {
        return validDate;
    }

    public void setValidDate(Date validDate) {
        this.validDate = validDate;
    }
    @Basic
    @Column(name = "invaliddate")
    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date getInvalidDate() {
        return invalidDate;
    }

    public void setInvalidDate(Date invalidDate) {
        this.invalidDate = invalidDate;
    }
    @Basic
    @Column(name = "validstatus")
    public String getValidStatus() {
        return validStatus;
    }

    public void setValidStatus(String validStatus) {
        this.validStatus = validStatus;
    }
    @Basic
    @Column(name = "acntunit")
    public String getAcntUnit() {
        return acntUnit;
    }

    public void setAcntUnit(String acntUnit) {
        this.acntUnit = acntUnit;
    }
    @Basic
    @Column(name = "articlecode")
    public String getArticleCode() {
        return articleCode;
    }

    public void setArticleCode(String articleCode) {
        this.articleCode = articleCode;
    }
    @Basic
    @Column(name = "updateflag")
    public String getUpdateFlag() {
        return updateFlag;
    }

    public void setUpdateFlag(String updateFlag) {
        this.updateFlag = updateFlag;
    }
    @Basic
    @Column(name = "updatedate")
    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
    @Basic
    @Column(name = "operatorcomcode")
    public String getOperatorComCode() {
        return operatorComCode;
    }

    public void setOperatorComCode(String operatorComCode) {
        this.operatorComCode = operatorComCode;
    }
    @Basic
    @Column(name = "flag")
    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
    @Basic
    @Column(name = "specialflag")
    public String getSpecialFlag() {
        return specialFlag;
    }

    public void setSpecialFlag(String specialFlag) {
        this.specialFlag = specialFlag;
    }
    @Basic
    @Column(name = "hrlevelcode")
    public String getHrLevelCode() {
        return hrLevelCode;
    }

    public void setHrLevelCode(String hrLevelCode) {
        this.hrLevelCode = hrLevelCode;
    }
    @Basic
    @Column(name = "worktime")
    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }
    @Basic
    @Column(name = "longitude")
    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
    @Basic
    @Column(name = "latitude")
    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
    @Basic
    @Column(name = "countyterritory")
    public String getCountyTerritory() {
        return countyTerritory;
    }

    public void setCountyTerritory(String countyTerritory) {
        this.countyTerritory = countyTerritory;
    }

    @Basic
    @Column(name = "oldupperpath")
    public String getOldUpperPath() {
        return oldUpperPath;
    }

    public void setOldUpperPath(String oldUpperPath) {
        this.oldUpperPath = oldUpperPath;
    }
    @Basic
    @Column(name = "serialno")
    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }
    @Basic
    @Column(name = "iscentercity")
    public String getIsCenterCity() {
        return isCenterCity;
    }

    public void setIsCenterCity(String isCenterCity) {
        this.isCenterCity = isCenterCity;
    }

    @Basic
    @Column(name = "disable")
    public String getDisable() {
        return disable;
    }

    public void setDisable(String disable) {
        this.disable = disable;
    }
    @Basic
    @Column(name = "isecargo")
    public String getIsEcargo() {
        return isEcargo;
    }

    public void setIsEcargo(String isEcargo) {
        this.isEcargo = isEcargo;
    }
    @Basic
    @CreatedDate
    @Column(name = "INSERTTIMEFORHIS", updatable = false)
    public Date getInsertTimeForHis() {
        return insertTimeForHis;
    }

    public void setInsertTimeForHis(Date insertTimeForHis) {
        this.insertTimeForHis = insertTimeForHis;
    }
    @Basic
    @Column(name = "operatetimeforhis")
    @LastModifiedDate
    public Date getOperateTimeForHis() {
        return operateTimeForHis;
    }

    public void setOperateTimeForHis(Date operateTimeForHis) {
        this.operateTimeForHis = operateTimeForHis;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrpDcompanyFk that = (PrpDcompanyFk) o;
        return Objects.equals(comCode, that.comCode) &&
                Objects.equals(comCName, that.comCName) &&
                Objects.equals(comEName, that.comEName) &&
                Objects.equals(comShortName, that.comShortName) &&
                Objects.equals(addressCName, that.addressCName) &&
                Objects.equals(addressEName, that.addressEName) &&
                Objects.equals(postCode, that.postCode) &&
                Objects.equals(phoneNumber, that.phoneNumber) &&
                Objects.equals(faxNumber, that.faxNumber) &&
                Objects.equals(upperComCode, that.upperComCode) &&
                Objects.equals(insurerName, that.insurerName) &&
                Objects.equals(comType, that.comType) &&
                Objects.equals(comFlag, that.comFlag) &&
                Objects.equals(centerFlag, that.centerFlag) &&
                Objects.equals(comLevel, that.comLevel) &&
                Objects.equals(branchType, that.branchType) &&
                Objects.equals(upperPath, that.upperPath) &&
                Objects.equals(comCodeCIRC, that.comCodeCIRC) &&
                Objects.equals(licenseNo, that.licenseNo) &&
                Objects.equals(email, that.email) &&
                Objects.equals(remark1, that.remark1) &&
                Objects.equals(comKind, that.comKind) &&
                Objects.equals(manager, that.manager) &&
                Objects.equals(accountant, that.accountant) &&
                Objects.equals(remark, that.remark) &&
                Objects.equals(newComCode, that.newComCode) &&
                Objects.equals(validDate, that.validDate) &&
                Objects.equals(invalidDate, that.invalidDate) &&
                Objects.equals(validStatus, that.validStatus) &&
                Objects.equals(acntUnit, that.acntUnit) &&
                Objects.equals(articleCode, that.articleCode) &&
                Objects.equals(updateFlag, that.updateFlag) &&
                Objects.equals(updateDate, that.updateDate) &&
                Objects.equals(operatorComCode, that.operatorComCode) &&
                Objects.equals(flag, that.flag) &&
                Objects.equals(specialFlag, that.specialFlag) &&
                Objects.equals(hrLevelCode, that.hrLevelCode) &&
                Objects.equals(workTime, that.workTime) &&
                Objects.equals(longitude, that.longitude) &&
                Objects.equals(latitude, that.latitude) &&
                Objects.equals(countyTerritory, that.countyTerritory) &&
                Objects.equals(insurerCode, that.insurerCode) &&
                Objects.equals(oldUpperPath, that.oldUpperPath) &&
                Objects.equals(serialNo, that.serialNo) &&
                Objects.equals(isCenterCity, that.isCenterCity) &&
                Objects.equals(insertTimeForHis, that.insertTimeForHis) &&
                Objects.equals(operateTimeForHis, that.operateTimeForHis) &&
                Objects.equals(disable, that.disable) &&
                Objects.equals(isEcargo, that.isEcargo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(comCode, comCName, comEName, comShortName, addressCName, addressEName, postCode, phoneNumber, faxNumber, upperComCode, insurerName, comType, comFlag, centerFlag, comLevel, branchType, upperPath, comCodeCIRC, licenseNo, email, remark1, comKind, manager, accountant, remark, newComCode, validDate, invalidDate, validStatus, acntUnit, articleCode, updateFlag, updateDate, operatorComCode, flag, specialFlag, hrLevelCode, workTime, longitude, latitude, countyTerritory, insurerCode, oldUpperPath, serialNo, isCenterCity, insertTimeForHis, operateTimeForHis, disable, isEcargo);
    }
}
