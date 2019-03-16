package io.quarkus.swaggerui.deployment;

import io.quarkus.arc.deployment.AdditionalBeanBuildItem;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import io.quarkus.runtime.annotations.ConfigItem;
import io.quarkus.runtime.annotations.ConfigRoot;
import io.quarkus.swaggerui.runtime.SwaggerUiServlet;
import io.quarkus.undertow.deployment.ServletBuildItem;
import org.jboss.logging.Logger;

import java.util.Arrays;
import java.util.List;

public class SwaggerUiProcessor {

    private static final Logger log = Logger.getLogger(SwaggerUiProcessor.class.getName());

    /**
     * The configuration for swagger-ui.
     */
    SwaggerUiConfig swaggerUiConfig;

    @BuildStep
    FeatureBuildItem feature() {
        log.info("Feature: swagger-ui");
        return new FeatureBuildItem("swagger-ui");
    }

    @BuildStep
    ServletBuildItem servlet() {
        log.info("Servlet: " + swaggerUiConfig.path);
        return ServletBuildItem
                .builder("swaggerui", SwaggerUiServlet.class.getName())
                .addMapping(swaggerUiConfig.path)
                .build();
    }

    @BuildStep
    List<AdditionalBeanBuildItem> beans() {
        return Arrays.asList(new AdditionalBeanBuildItem(SwaggerUiServlet.class));
    }

    @ConfigRoot(name = "swaggerui")
    static final class SwaggerUiConfig {
        /**
         * The path of the swagger-ui servlet.
         */
        @ConfigItem(defaultValue = "/swagger-ui")
        String path;
    }
}
