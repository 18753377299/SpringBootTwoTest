package com.example.common.valid.conf;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*校验模式:  在上面的例子中，我们使用BindingResult验证不通过的结果集合，
 * 但是通常按顺序验证到第一个字段不符合验证要求时，就可以直接拒绝请求了。
 * 这就涉及到两种校验模式的配置：

普通模式（默认是这个模式）: 会校验完所有的属性，然后返回所有的验证失败信息
快速失败模式: 只要有一个验证失败，则返回
如果想要配置第二种模式，需要添加如下配置类*/

@Configuration
public class ValidatorConf {
	
    @Bean
    public Validator validator() {
        ValidatorFactory validatorFactory = Validation.byProvider( HibernateValidator.class )
                .configure()
                .failFast( true )
                .buildValidatorFactory();
        Validator validator = validatorFactory.getValidator();

        return validator;
    }
}
