package com.duo.core.filter;

/**
 * @author [ Yonsin ] on [2019/5/15]
 * @Description： [ Websitemesh 的过滤器定义 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */


//import org.sitemesh.builder.SiteMeshFilterBuilder;
//import org.sitemesh.config.ConfigurableSiteMeshFilter;
import org.springframework.beans.factory.annotation.Value;

public class WebSiteMeshFilter {//extends ConfigurableSiteMeshFilter

    /** 需要装饰的访问路径 */
    @Value("${spring.sitemesh.contentPath}")
    private String contentPath;

    /** 装饰器页面路径 */
    @Value("${spring.sitemesh.decoratorPath}")
    private String decoratorPath;

    /** 不需要装饰的访问路径,多个之间用英文逗号分隔 */
    @Value("${spring.sitemesh.excludedPaths}")
    private String excludedPaths;

//    @Override
//    protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {
//        // 加载配置文件
//        builder.addDecoratorPath(contentPath, decoratorPath);
//        if (excludedPaths == null) {
//            return;
//        }
//        String[] paths = excludedPaths.split(",");
//        for (String path : paths) {
//            builder.addExcludedPath(path);
//        }
//    }

}