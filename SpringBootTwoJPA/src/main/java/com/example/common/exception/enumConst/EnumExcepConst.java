package com.example.common.exception.enumConst;

import io.swagger.annotations.Api;

@Api(value="异常信息枚举")
public enum EnumExcepConst {

    NullConst("发生空指针异常","00"),
    ParamValidateConst("参数校验异常:","03"),
    CustomConst("服务器代码发生异常,请联系管理员:","05")

    ;

    EnumExcepConst() {
    }

    EnumExcepConst(String name, String code) {
        this.code = code;
        this.name = name;
    }
    private String name;
    private String code;

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

}
