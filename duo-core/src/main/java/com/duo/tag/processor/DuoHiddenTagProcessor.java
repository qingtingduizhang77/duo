package com.duo.tag.processor;

import lombok.extern.slf4j.Slf4j;
import org.thymeleaf.IEngineConfiguration;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IModelFactory;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractAttributeTagProcessor;
import org.thymeleaf.processor.element.AbstractElementTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.standard.expression.IStandardExpression;
import org.thymeleaf.standard.expression.IStandardExpressionParser;
import org.thymeleaf.standard.expression.StandardExpressions;
import org.thymeleaf.templatemode.TemplateMode;
import org.unbescape.html.HtmlEscape;

/**
 * @author [ Yonsin ] on [2019/9/2]
 * @Description： [ 功能描述 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */

@Slf4j
public class DuoHiddenTagProcessor extends AbstractAttributeTagProcessor {
    
 
   // <DUO:hidden pagetype="grid"/>
    private static final String ATTR_NAME  = "hidden";//标签名 hidden 这个玩意就是 自定义标签的 ： hidden

    private static final int PRECEDENCE = 10000;//优先级

    /*
        templateMode: 模板模式，这里使用HTML模板。
        dialectPrefix: 标签前缀。即xxx:text中的xxx。
        elementName：匹配标签元素名。举例来说如果是div，则我们的自定义标签只能用在div标签中。为null能够匹配所有的标签。
        prefixElementName: 标签名是否要求前缀。
        attributeName: 自定义标签属性名。这里为text。
        prefixAttributeName：属性名是否要求前缀，如果为true，Thymeeleaf会要求使用text属性时必须加上前缀，即xxx:text。
        precedence：标签处理的优先级，此处使用和Thymeleaf标准方言相同的优先级。
        removeAttribute：标签处理后是否移除自定义属性。
        */
    public DuoHiddenTagProcessor(String dialectPrefix) {
        super(
                TemplateMode.HTML, // This processor will apply only to HTML mode
                dialectPrefix,     // Prefix to be applied to name for matching
                null,              // No tag name: match any tag name
                false,             // No prefix to be applied to tag name
                ATTR_NAME,         // Name of the attribute that will be matched
                true,              // Apply dialect prefix to attribute name
                PRECEDENCE,        // Precedence (inside dialect's precedence)
                true);             // Remove the matched attribute afterwards
        log.info("===========================================");
        }

    @Override
    protected void doProcess(ITemplateContext context, IProcessableElementTag tag, AttributeName attributeName,
                             String attributeValue, IElementTagStructureHandler structureHandler) {
        final IEngineConfiguration configuration = context.getConfiguration();
        final IStandardExpressionParser parser = StandardExpressions.getExpressionParser(configuration);
        final IStandardExpression expression = parser.parseExpression(context, attributeValue);
        /*
         * Execute the expression just parsed
         * 使用得到的表达式，处理上下文内容，得到具体传入的参数值
         */
        final String eventCode = (String) expression.execute(context);
        final String funid = (String) tag.getAttributeValue("funid");
        final String style = (String) tag.getAttributeValue("style");
        log.info("===============eventCode============="+eventCode);
        log.info("===============funid============="+funid);
        log.info("===============style============="+style);

       // structureHandler.setBody(title + links.toString(), false);
//            structureHandler.removeElement();
//            structureHandler.removeTags();

        structureHandler.setAttribute("style", "display:none;");

    }
}
