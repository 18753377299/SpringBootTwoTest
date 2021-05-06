package com.example.test.jpachildTest;

import com.example.common.jpa.condition.Restrictions;
import com.example.common.jpa.vo.Criteria;
import com.example.common.po.Course;
import com.example.common.po.Klass;
import com.example.common.service.facade.SaaAPIService;
import com.example.dao.KlassRepository;
import com.example.dao.RiskReportSaleMainRepository;
import com.example.pojo.RiskReportSaleMain;
import com.example.test.DemoTestSuper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.util.List;

@Api("jpa的child测试")
public class JpaChildTest extends DemoTestSuper {

    @Autowired
    private KlassRepository klassRepository;
    @Autowired
    RiskReportSaleMainRepository mainRepository;

    @Autowired
    SaaAPIService saaAPIService;

    @Test
    @ApiOperation(value="外侧定义的root、criteriaQuery等都是RiskReportSaleMain对象，里面重新定义的则是子对象",
            notes="可以成功")
    public void  testChildExist(){
        String taskCode = "41270200";
        Criteria<RiskReportSaleMain>  criteriaMain =new Criteria<>();
        criteriaMain.add(Restrictions.eq("archivesNo","1234"));

        Specification specification = saaAPIService.addPower(taskCode);
        if(null !=specification){
            criteriaMain.and(specification);
        }
        /*else {
            specification = criteriaMain;
        }*/
        List<RiskReportSaleMain> klassList = this.mainRepository.findAll(criteriaMain);

        for (RiskReportSaleMain main: klassList){
            System.out.println(main);
        }
    }


    @Test
    @ApiOperation(value="尚未成功")
    public void  testChild(){
//        List<Klass> klassList =  klassRepository.findAll();

        String  courseName ="41270200";
        List<Klass> klassList = this.klassRepository.findAll((Specification<Klass>) (root, query, builder) -> {

            // 查询的结果的类型
            Subquery<Klass> courseSubquery = query.subquery(Klass.class);
            // 从那里查询  select * form ... 中的form...
            Root<Course> courseRoot = query.from(Course.class);
            // 连接两个表
            Join<Course, Klass> klassJoin = root.join("course");
            // select 查询结果，where即查询条件
            courseSubquery.select(klassJoin);
//            courseSubquery.select(klassJoin)
//                    .where(builder.equal(courseRoot.get("name"),  courseName));

            return builder.in(root).value(courseSubquery);
//            return builder.in(root).value(courseSubquery);
//            return builder.in(courseSubquery);
        });
        for (Klass klass: klassList){
            System.out.println(klass);
        }
    }
}
