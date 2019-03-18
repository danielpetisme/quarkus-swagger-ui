package io.quarkus.swaggerui.runtime;

import static io.undertow.Handlers.resource;

import javax.servlet.ServletContext;

import org.jboss.logging.Logger;

import io.undertow.Handlers;
import io.undertow.server.HandlerWrapper;
import io.undertow.server.HttpHandler;
import io.undertow.server.handlers.resource.ClassPathResourceManager;
import io.undertow.server.handlers.resource.ResourceHandler;
import io.undertow.server.handlers.resource.ResourceManager;
import io.undertow.servlet.ServletExtension;
import io.undertow.servlet.api.DeploymentInfo;

public class SwaggerUiServletExtension implements ServletExtension {

    public static final String SWAGGER_UI_RESOURCES_DIR = "swagger-ui";

    private static final Logger log = Logger.getLogger(SwaggerUiServletExtension.class.getName());

    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    /**
     * This registers the SwaggerUiServletExtension
     *
     * @param deploymentInfo - the deployment to augment
     * @param servletContext - the ServletContext for the deployment
     */
    @Override
    public void handleDeployment(DeploymentInfo deploymentInfo, ServletContext servletContext) {
        log.debug("Attaching Swagger UI resource handler to path: " + path);
        ResourceManager resourceManager = new ClassPathResourceManager(servletContext.getClassLoader(),
                SWAGGER_UI_RESOURCES_DIR);
        deploymentInfo.addOuterHandlerChainWrapper(
                (handler) -> Handlers.path(handler).addPrefixPath(path, resource(resourceManager)));
    }

}
