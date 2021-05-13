package com.example.springboottwoes.func.componment;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;

public interface Pipeline {
    // ResultItems保存了抽取结果，它是一个Map结构，
    // 在page.putField(key,value)中保存的数据，
    //可以通过ResultItems.get(key)获取
    public void process(ResultItems resultItems, Task task);

}
