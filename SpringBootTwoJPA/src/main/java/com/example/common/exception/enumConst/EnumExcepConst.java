package com.example.common.exception.enumConst;


public enum EnumExcepConst {

    NullConst("发生空指针异常","00"),

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
