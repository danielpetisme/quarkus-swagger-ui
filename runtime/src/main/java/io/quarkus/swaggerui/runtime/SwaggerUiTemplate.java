package io.quarkus.swaggerui.runtime;

import org.jboss.logging.Logger;

import io.quarkus.arc.runtime.BeanContainer;
import io.quarkus.runtime.annotations.Template;
import io.undertow.servlet.ServletExtension;

@Template
public class SwaggerUiTemplate {

    private static final Logger log = Logger.getLogger(SwaggerUiTemplate.class.getName());

    public ServletExtension createSwaggerUiExtension(String path, BeanContainer container) {
        log.debug("Creating Swagger UI Servlet Extension");
        SwaggerUiServletExtension extension = container.instance(SwaggerUiServletExtension.class);
        extension.setPath(path);
        return extension;
    }
}
