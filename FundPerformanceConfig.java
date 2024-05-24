package au.com.cfs.winged.core.config;

public interface FundPerformanceConfig {
    public String getBaseApiUrl();
    public String[] getCompanyCode();
    public String[] getMainGroup();
    public String[] getProductId();
    public String[] getCategory();
    public String[] getAsset();
    public String[] getMintimeframe();
    public int getTimeout();

}
