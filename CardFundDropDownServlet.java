package au.com.cfs.winged.core.servlets;

import au.com.cfs.winged.core.models.pojo.CardFundFamily;
import au.com.cfs.winged.core.services.CardFundService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.propertytypes.ServiceDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component(service = Servlet.class, immediate = true, name = "Fund & Performance Dropdown Servlet")
@SlingServletResourceTypes(resourceTypes = "bin/list/fundPerformance", methods = HttpConstants.METHOD_GET)
@ServiceDescription("Fund & Performance Dropdown Servlet")
public class CardFundDropDownServlet extends SlingSafeMethodsServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(CardFundDropDownServlet.class);

    private static final long serialVersionUID = -4458186320139222562L;

    @Reference
    private transient CardFundService cardFundService;

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        try {
            // Retrieve card fund families from the service
            CardFundFamily[] cardFundFamilies = cardFundService.getCardFundFamilies();

            // Convert the array to a list for easier manipulation
            List<CardFundFamily> cardFundFamilyList = Arrays.asList(cardFundFamilies);

            // Here you can perform any additional processing or filtering of the card fund families as needed

            // For demonstration, let's log all attributes of each card fund family
            for (CardFundFamily cardFundFamily : cardFundFamilyList) {
                LOGGER.info("Marketing Name: {}", cardFundFamily.getMarketingName());
                LOGGER.info("APIR: {}", cardFundFamily.getApir());
                LOGGER.info("Effective Date: {}", cardFundFamily.getEffectiveDate());
                LOGGER.info("One Year: {}", cardFundFamily.getOneYear());
                LOGGER.info("Two Years: {}", cardFundFamily.getTwoYears());
                LOGGER.info("Three Years: {}", cardFundFamily.getThreeYears());
                LOGGER.info("Four Years: {}", cardFundFamily.getFourYears());
                LOGGER.info("Five Years: {}", cardFundFamily.getFiveYears());
                LOGGER.info("Seven Years: {}", cardFundFamily.getSevenYears());
                LOGGER.info("Ten Years: {}", cardFundFamily.getTenYears());
                LOGGER.info("Since Inception: {}", cardFundFamily.getSinceInception());
                LOGGER.info("Inception Date: {}", cardFundFamily.getInceptionDate());
                // Add more attributes as needed
            }

            // You can send the response back to the client if needed
            // For example, if you want to return JSON data, you can use response.getWriter().write(jsonData);
            // Or you can forward the request to a JSP or HTL script to render the data
            // For now, let's just set a success status code
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            LOGGER.error("Error occurred while retrieving card fund families", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
