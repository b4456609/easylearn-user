/**
 *
 */
package ntou.bernie.easylearn;

import com.fasterxml.jackson.databind.DeserializationFeature;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import ntou.bernie.easylearn.db.MorphiaService;
import ntou.bernie.easylearn.health.DatabaseHealthCheck;
import ntou.bernie.easylearn.user.core.User;
import ntou.bernie.easylearn.user.db.UserDAOImp;
import ntou.bernie.easylearn.user.resource.UserResource;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;

/**
 * @author bernie
 */
public class EasylearnUserAPP extends Application<EasylearnUserAPPConfiguration> {
    private static final Logger LOGGER = LoggerFactory.getLogger(EasylearnUserAPP.class);

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        new EasylearnUserAPP().run(args);

    }

    @Override
    public void run(EasylearnUserAPPConfiguration configuration, Environment environment) throws Exception {
        LOGGER.info("Application name: {}", configuration.getAppName());
        environment.getObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        final FilterRegistration.Dynamic cors =
                environment.servlets().addFilter("CORS", CrossOriginFilter.class);

        // Configure CORS parameters
        cors.setInitParameter("allowedOrigins", "*");
        cors.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin");
        cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");

        // Add URL mapping
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");


        // mongodb driver
        MorphiaService morphia = new MorphiaService(configuration.getDatabaseConfiguration());

        UserResource userResource = new UserResource(new UserDAOImp(User.class, morphia.getDatastore()));
        environment.jersey().register(userResource);

         // database health check
        environment.healthChecks().register("database",
                new DatabaseHealthCheck(configuration.getDatabaseConfiguration()));

    }

}
