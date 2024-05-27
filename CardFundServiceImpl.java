package au.com.cfs.winged.core.impl;

import au.com.cfs.winged.core.config.FundPerformanceConfig;
import au.com.cfs.winged.core.models.common.CardFundAPIConstants;
import au.com.cfs.winged.core.models.common.ResourceOwnerTokenService;
import au.com.cfs.winged.core.models.pojo.CardFundFamily;
import au.com.cfs.winged.core.services.CardFundService;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Component(service = CardFundService.class, immediate = true)
public class CardFundServiceImpl implements CardFundService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CardFundServiceImpl.class);

    @Reference
    private ResourceResolverFactory resolverFactory;

    @Reference
    private ResourceOwnerTokenService resourceOwnerTokenService;

    @Reference
    private FundPerformanceConfig fundPerformanceConfig;

    @Override
    public CardFundFamily[] getCardFundFamilies() {
        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put("companyCode", fundPerformanceConfig.getCompanyCode());
        queryParameters.put("mainGroup", fundPerformanceConfig.getMainGroup());
        queryParameters.put("productId", fundPerformanceConfig.getProductId());

        addQueryParameters(queryParameters, "category", fundPerformanceConfig.getCategory());
        addQueryParameters(queryParameters, "asset", fundPerformanceConfig.getAsset());
        addQueryParameters(queryParameters, "risk", fundPerformanceConfig.getRisk());
        addQueryParameters(queryParameters, "minTimeFrame", fundPerformanceConfig.getMintimeframe());

        return getCardFundDetails(queryParameters);
    }

    private void addQueryParameters(Map<String, String> queryParameters, String key, String[] values) {
        if (values != null) {
            for (String value : values) {
                queryParameters.put(key, value);
            }
        }
    }

    private CardFundFamily[] getCardFundDetails(Map<String, String> queryParameter) {
        CardFundFamily[] cardFundFamily = new CardFundFamily[0];
        String url = constructCardFundApiUrl(queryParameter);
        LOGGER.info("Constructed URL: {}", url); // Log the constructed URL
        try {
            HttpRequest request = resourceOwnerTokenService.getHttpRequest().uri(new URI(url)).GET().build();
            HttpResponse<String> response = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofMillis(fundPerformanceConfig.getTimeout())).build()
                    .send(request, HttpResponse.BodyHandlers.ofString());
            String responseJSONStr = response.body();
            LOGGER.info("API Response: {}", responseJSONStr); // Log the API response
            if (response.statusCode() == 200) {
                return parseCardFunds(responseJSONStr);
            } else {
                LOGGER.error("API request failed with response code: {}", response.statusCode());
            }
        } catch (URISyntaxException | InterruptedException | IOException e) {
            LOGGER.error("getCardFundDetails(): Exception occurred", e);
        }
        return cardFundFamily;
    }

    private String constructCardFundApiUrl(Map<String, String> queryParameters) {
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(fundPerformanceConfig.getBaseApiUrl());

        boolean isFirstParam = true;
        for (Map.Entry<String, String> entry : queryParameters.entrySet()) {
            if (!isFirstParam) {
                urlBuilder.append("&");
            } else {
                urlBuilder.append("?");
                isFirstParam = false;
            }
            urlBuilder.append(entry.getKey()).append("=").append(entry.getValue());
        }

        return urlBuilder.toString();
    }

    private CardFundFamily[] parseCardFunds(String responseJSONStr) {
        Gson gson = new Gson();
        try {
            return gson.fromJson(responseJSONStr, CardFundFamily[].class);
        } catch (JsonSyntaxException e) {
            LOGGER.error("parseCardFunds(): JSON syntax exception occurred", e);
        }
        return new CardFundFamily[0];
    }
}
