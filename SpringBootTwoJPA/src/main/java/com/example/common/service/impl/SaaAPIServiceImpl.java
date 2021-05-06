package com.example.common.service.impl;

import com.example.common.po.PrpDcompanyFk;
import com.example.common.service.facade.SaaAPIService;
import com.google.common.collect.Lists;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.hibernate.query.criteria.internal.CriteriaBuilderImpl;
import org.hibernate.query.criteria.internal.predicate.BooleanStaticAssertionPredicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.util.List;

/**
 * 用户中心菜单权限获取
 */
@Service(value="saaAPIService")
public class SaaAPIServiceImpl implements SaaAPIService {


    /**
     * 根据功能代码获取用户权限
     * @param taskCode
     * @return com.picc.riskctrl.common.proxy.yhzx.vo.GetPowerInfoResponseVo
     */
    /*public GetPowerInfoResponseVo getPowerInfo(String taskCode) {
        UserInfo userInfo = getUserInfo();
        GetPowerInfoRequestVo requestVo = new GetPowerInfoRequestVo();
        requestVo.setSvrCode(yhzxProperties.getSvrCode());
        requestVo.setSvrScope(yhzxProperties.getSvrScope());
        requestVo.setUserScope(yhzxProperties.getSvrScope());
        requestVo.setUserCode(userInfo.getUserCode());
        requestVo.setTaskCode(taskCode);
        return yhzxService.getPowerInfo(requestVo);
    }*/

    /**
     * 对外提供获取用户权限
     *  使用Specification.and(addPower())
     * @param taskCode 功能代码
     * @return org.springframework.data.jpa.domain.Specification
     */
    @ApiOperation(value = "外侧定义的root、criteriaQuery等都是RiskReportSaleMain对象，里面重新定义的则是子对象",
            notes = "使用的jpa，所以需要注解")
    public Specification addPower(String taskCode) {
        if(StringUtils.isBlank(taskCode)){
             //返回恒不成立条件
            Specification subQuery = (root, criteriaQuery, criteriaBuilder) ->
                    new BooleanStaticAssertionPredicate((CriteriaBuilderImpl) criteriaBuilder,false);
            return subQuery;
        }
        Specification subQuery = (Specification<PrpDcompanyFk>) (root, criteriaQuery, criteriaBuilder) -> {
            Path<Object> comCode = root.get("comCode");
            Subquery<PrpDcompanyFk> subquery = criteriaQuery.subquery(PrpDcompanyFk.class);
            Root<PrpDcompanyFk> sub = subquery.from(PrpDcompanyFk.class);
            subquery.select(sub);
            List<Predicate> predicateList = Lists.newArrayList();
           /* for (String upperPath : upperPathList) {
                predicateList.add(criteriaBuilder.like(sub.get("upperPath"), upperPath + '%'));
            }
            for (String code : comCodeList) {
                predicateList.add(criteriaBuilder.equal(sub.get("comCode"), code));
            }*/
            predicateList.add(criteriaBuilder.like(sub.get("upperPath"), taskCode + '%'));

            /*subquery.where(criteriaBuilder.or(predicateList.toArray(new Predicate[0])),
                    criteriaBuilder.equal(sub.get("comCode"), comCode));*/

            predicateList.add(criteriaBuilder.equal(sub.get("comCode"), comCode));
            subquery.where(criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()])));
            return criteriaBuilder.exists(subquery);
        };
        return subQuery;
    }
}
