package io.quarkus.swaggerui.deployment;

import javax.inject.Inject;

import org.jboss.logging.Logger;

import io.quarkus.arc.deployment.BeanContainerBuildItem;
import io.quarkus.deployment.annotations.BuildProducer;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.annotations.ExecutionTime;
import io.quarkus.deployment.annotations.Record;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import io.quarkus.deployment.builditem.LaunchModeBuildItem;
import io.quarkus.runtime.annotations.ConfigItem;
import io.quarkus.runtime.annotations.ConfigRoot;
import io.quarkus.swaggerui.runtime.SwaggerUiTemplate;
import io.quarkus.undertow.deployment.ServletExtensionBuildItem;

public class SwaggerUiProcessor {

    private static final Logger log = Logger.getLogger(SwaggerUiProcessor.class.getName());

    /**
     * The configuration for swagger-ui.
     */
    private SwaggerUiConfig swaggerUiConfig;

    @Inject
    private LaunchModeBuildItem launch;

    /**
     * Register this extension as a swagger-ui feature
     *
     * @return
     */
    @BuildStep
    FeatureBuildItem feature() {
        log.info("Feature: swagger-ui");
        return new FeatureBuildItem("swagger-ui");
    }

    /**
     * Register the Swagger UI servlet extention
     *
     * @param template - Swagger UI runtime template
     * @param container - the BeanContainer for creating CDI beans
     * @return servlet extension build item
     */
    @BuildStep
    @Record(ExecutionTime.STATIC_INIT)
    public void registerSwaggerUiServletExtension(SwaggerUiTemplate template,
            BuildProducer<ServletExtensionBuildItem> servletExtension, BeanContainerBuildItem container) {
        if (launch.getLaunchMode().isDevOrTest()) {
            log.info("Registering Swagger UI Servlet Extension");
            servletExtension.produce(new ServletExtensionBuildItem(
                    template.createSwaggerUiExtension(swaggerUiConfig.path, container.getValue())));
        }
    }

    @ConfigRoot
    static final class SwaggerUiConfig {
        /**
         * The path of the swagger-ui servlet.
         */
        @ConfigItem(defaultValue = "/swagger-ui")
        String path;
    }
}
