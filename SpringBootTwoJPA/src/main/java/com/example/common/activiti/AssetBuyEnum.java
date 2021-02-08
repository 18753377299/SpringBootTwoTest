package com.example.common.activiti;

public enum AssetBuyEnum {
    /**提交审核，一级审核，二级审核
     * 一级打回,*二级打回，二级自动审核通过,一级审核通过，二级审核通过,
     *部门助理受理,部门助理采买,部门助理关联
     * */
    SUBMITUNDERWRITE,FIRSTUNDERWRITE,SECONDUNDERWRITE,
    FIRSTROLLBACK,SECONDROLLBACK,SECONDAUTOCOMMIT,FIRSTCOMMIT,SECONDCOMMIT,
    BMZLACCEPT,BMZLBUY,BMZLLINK
}
