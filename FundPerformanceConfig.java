package au.com.cfs.winged.core.config;

public interface FundPerformanceConfig {
    String getBaseApiUrl();
    String getCompanyCode();
    String getMainGroup();
    String getProductId();
    String[] getCategory();
    String[] getAsset();
    String[] getRisk();
    String[] getMintimeframe();
    int getTimeout();
}
