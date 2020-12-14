package com.duo.tag;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.dialect.IExecutionAttributeDialect;
import org.thymeleaf.dialect.IExpressionObjectDialect;
import org.thymeleaf.dialect.IProcessorDialect;
import org.thymeleaf.expression.IExpressionObjectFactory;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.processor.element.AbstractElementTagProcessor;
import org.thymeleaf.standard.StandardDialect;
import org.thymeleaf.standard.processor.StandardXmlNsTagProcessor;
import org.thymeleaf.templatemode.TemplateMode;

import java.lang.reflect.Constructor;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author [ Yonsin ] on [2019/5/13]
 * @Description： [ 功能描述 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */
@Slf4j
public class DuoDialect extends AbstractProcessorDialect implements IProcessorDialect {

    private String classnames; //从配置文件取
    private static final String DIALECT_NAME = "Duo Dialect";//定义方言名称

    public DuoDialect(String classnames) {
        // 我们将设置此方言与“方言处理器”优先级相同
        // 标准方言, 以便处理器执行交错。
        super(DIALECT_NAME, "duo", StandardDialect.PROCESSOR_PRECEDENCE);
        this.classnames=classnames;
    }

    @Override
    public Set<IProcessor> getProcessors(String dialectPrefix) {
        Set<IProcessor> processors = new HashSet<IProcessor>();
        log.info("========：====================================================================");
        //添加我们定义的标签
        try {
            if(classnames!=null&&!classnames.equals(""))
                for( String tagclass:classnames.split(",")) {
                    log.info("添加Tag：=============" + tagclass);
                    Class clazz = Class.forName(tagclass);
                    Constructor ct = clazz.getConstructor(new Class[]{String.class});
                    ct.setAccessible(true);
                    AbstractElementTagProcessor tag = (AbstractElementTagProcessor) ct.newInstance(dialectPrefix);
                    processors.add(tag);
                }
        }catch (Exception e){
            log.error("添加自定义标签出错！",e);
        }
       processors.add(new StandardXmlNsTagProcessor(TemplateMode.HTML, dialectPrefix));
        return processors;
    }

}
