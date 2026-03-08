package Side.Projects.URL_Shortener;

public class UrlMapping {
    private String originalUrl;
    private String shortCode;

    public UrlMapping(String originalUrl, String shortCode){
        this.originalUrl = originalUrl;
        this.shortCode = shortCode;
    }

    public String getUrl(){
        return originalUrl;
    }

    public String getShortCode(){
        return shortCode;
    }


}
