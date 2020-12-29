package com.example.common.jpa.addSql;//package com.example.common.jpa.addSql;
//
//import com.google.common.collect.Lists;
//import com.picc.riskctrl.base.BaseService;
//import com.picc.riskctrl.common.po.UserInfo;
//import com.picc.riskctrl.common.proxy.yhzx.config.YHZXProperties;
//import com.picc.riskctrl.common.proxy.yhzx.vo.CompanyInfo;
//import com.picc.riskctrl.common.proxy.yhzx.vo.GetPowerInfoRequestVo;
//import com.picc.riskctrl.common.proxy.yhzx.vo.GetPowerInfoResponseVo;
//import com.picc.riskctrl.common.schema.PrpDcompanyFk;
//import org.apache.commons.lang3.StringUtils;
//import org.hibernate.query.criteria.internal.CriteriaBuilderImpl;
//import org.hibernate.query.criteria.internal.predicate.BooleanStaticAssertionPredicate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.jpa.domain.Specification;
//import org.springframework.stereotype.Service;
//
//import javax.persistence.criteria.Path;
//import javax.persistence.criteria.Predicate;
//import javax.persistence.criteria.Root;
//import javax.persistence.criteria.Subquery;
//import java.util.List;
//
///**
// * 用户中心菜单权限获取
// *
// * @author wangwenjie
// * @date 2020-05-20
// */
//@Service
//public class SaaAPIService extends BaseService {
//
//
//    /**
//     * 根据功能代码获取用户权限
//     *
//     * @author wangwenjie
//     * @param taskCode
//     * @return com.picc.riskctrl.common.proxy.yhzx.vo.GetPowerInfoResponseVo
//     */
//    public GetPowerInfoResponseVo getPowerInfo(String taskCode) {
//        UserInfo userInfo = getUserInfo();
//        GetPowerInfoRequestVo requestVo = new GetPowerInfoRequestVo();
//        requestVo.setSvrCode(yhzxProperties.getSvrCode());
//        requestVo.setSvrScope(yhzxProperties.getSvrScope());
//        requestVo.setUserScope(yhzxProperties.getSvrScope());
//        requestVo.setUserCode(userInfo.getUserCode());
//        requestVo.setTaskCode(taskCode);
//        return yhzxService.getPowerInfo(requestVo);
//    }
//
//    /**
//     * 对外提供获取用户权限
//     *  使用Specification.and(addPower())
//     *
//     * @author wangwenjie
//     * @param taskCode 功能代码
//     * @return org.springframework.data.jpa.domain.Specification
//     */
//    public Specification addPower(String taskCode) {
//        //当前用户所在机构
//        GetPowerInfoResponseVo powerInfo = getPowerInfo(taskCode);
//        List<CompanyInfo> permitCompany = powerInfo.getPermitCompany();
//
//        if (permitCompany != null) {
//            if (permitCompany.isEmpty()) {
//                //返回恒不成立条件
//                Specification subQuery = (root, criteriaQuery, criteriaBuilder) -> new BooleanStaticAssertionPredicate((CriteriaBuilderImpl) criteriaBuilder,false);
//                return subQuery;
//            } else {
//                List<String> upperPathList = Lists.newArrayList();
//                List<String> comCodeList = Lists.newArrayList();
//                for (CompanyInfo companyInfo : permitCompany) {
//                    String permitComCode = companyInfo.getComCode();
//                    String upperPath = companyInfo.getUpperPath();
//
//                    if ("1".equals(companyInfo.getIncludeSubCom())) {
//                        upperPathList.add(upperPath);
//                    } else if (StringUtils.isNotBlank(permitComCode)) {
//                        comCodeList.add(permitComCode);
//                    }
//                }
//                Specification subQuery = (Specification<PrpDcompanyFk>) (root, criteriaQuery, criteriaBuilder) -> {
//                    Path<Object> comCode = root.get("comCode");
//                    Subquery<PrpDcompanyFk> subquery = criteriaQuery.subquery(PrpDcompanyFk.class);
//                    Root<PrpDcompanyFk> sub = subquery.from(PrpDcompanyFk.class);
//                    subquery.select(sub);
//                    List<Predicate> predicateList = Lists.newArrayList();
//                    for (String upperPath : upperPathList) {
//                        predicateList.add(criteriaBuilder.like(sub.get("upperPath"), upperPath + '%'));
//                    }
//                    for (String code : comCodeList) {
//                        predicateList.add(criteriaBuilder.equal(sub.get("comCode"), code));
//                    }
//
//                    subquery.where(criteriaBuilder.or(predicateList.toArray(new Predicate[0])),
//                            criteriaBuilder.equal(sub.get("comCode"), comCode));
//                    return criteriaBuilder.exists(subquery);
//                };
//                return subQuery;
//            }
//        }
//        return null;
//    }
//}
