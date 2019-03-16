package io.quarkus.swaggerui.runtime;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.logging.Logger;

@WebServlet
public class SwaggerUiServlet extends HttpServlet {

    private static final Logger log = Logger.getLogger(SwaggerUiServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("Request to serve Swagger UI");

        String oai = "I'm a tea pot";
        resp.setStatus(418);
        resp.setCharacterEncoding("UTF-8");
        resp.getOutputStream().print(oai);
    }

}
