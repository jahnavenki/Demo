package au.com.cfs.winged.core.servlets;

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

@Component(service = { Servlet.class }, immediate = true, name = "Fund & Performance Dropdown Servlet")
@SlingServletResourceTypes(resourceTypes="bin/list/fundPerformance", methods= HttpConstants.METHOD_GET)
@ServiceDescription("Fund & Performance Dropdown Servlet")
public class CardFundDropDownServlet extends SlingSafeMethodsServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(CardFundDropDownServlet.class);

    private static final long serialVersionUID = -4458186320139222562L;
    private static final String DATASOURCE = "datasource";
    private static final String APIR = "apir";
    private static final String  MARKETING_NAME= "marketingName";
    private static final String  EFFECTIVE_DATE= "effectiveDate";
    private static final String  ONE_YEARS= "oneYear";
    private static final String  TWO_YEARS= "twoYears";
    private static final String  THREE_YEARS= "threeYears";
    private static final String  FIVE_YEARS= "fiveYears";
    private static final String  SEVEN_YEARS= "sevenYears";
    private static final String  TEN_YEAR= "tenYears";
    private static final String  SINCE_INCEPTION= "sinceInception";

    @Reference
   private transient CardFundService CardFundService;

    @Override
    protected void doGet(@org.jetbrains.annotations.NotNull SlingHttpServletRequest request, @org.jetbrains.annotations.NotNull SlingHttpServletResponse response) throws ServletException, IOException {
        super.doGet(request, response);
    }
}
