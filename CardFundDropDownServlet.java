package au.com.cfs.winged.core.servlets;

import au.com.cfs.winged.core.models.pojo.CardFundFamily;
import au.com.cfs.winged.core.services.CardFundService;
import com.google.gson.Gson;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
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

@Component(service = {Servlet.class})
@SlingServletPaths(value = "/bin/jsonDataDropdown")
@ServiceDescription("Card Fund Drop Down Servlet")
public class CardFundDropDownServlet extends SlingSafeMethodsServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(CardFundDropDownServlet.class);

    @Reference
    private transient CardFundService cardFundService;

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws ServletException, IOException {
        try {
            CardFundFamily[] cardFundFamilies = cardFundService.getCardFundFamilies();
            
            if (cardFundFamilies != null) {
                List<CardFundFamily> cardFundFamilyList = Arrays.asList(cardFundFamilies);
                
                // Log each attribute of each CardFundFamily
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

                // Convert list to JSON and write to response
                Gson gson = new Gson();
                String jsonResponse = gson.toJson(cardFundFamilyList);
                response.setContentType("application/json");
                response.getWriter().write(jsonResponse);
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                LOGGER.info("No CardFundFamilies found.");
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } catch (Exception e) {
            LOGGER.error("Error occurred while retrieving card fund families", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
