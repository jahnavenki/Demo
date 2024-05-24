package au.com.cfs.winged.core.config;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.Designate;

@Component(service = FundPerformanceConfig.class, immediate = true)
@Designate(ocd = FundPerformanceServiceConfig.class)
public class FundPerformanceConfigImpl implements FundPerformanceConfig {
    private String baseApiUrl;
    private String companyCode;
    private String mainGroup;
    private String productId;
    private String[] category;
    private String[] asset;
    private String[] risk; // Added field for risk
    private String[] mintimeframe;
    private int timeout;

    @Override
    public String getBaseApiUrl() {
        return baseApiUrl;
    }

    @Override
    public String getCompanyCode() {
        return companyCode;
    }

    @Override
    public String getMainGroup() {
        return mainGroup;
    }

    @Override
    public String getProductId() {
        return productId;
    }

    @Override
    public String[] getCategory() {
        return category;
    }

    @Override
    public String[] getAsset() {
        return asset;
    }

    @Override
    public String[] getRisk() {
        return risk;
    }

    @Override
    public String[] getMintimeframe() {
        return mintimeframe;
    }

    @Override
    public int getTimeout() {
        return timeout;
    }
}
