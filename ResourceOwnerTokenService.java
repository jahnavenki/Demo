package au.com.cfs.winged.core.models.common;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class ResourceOwnerTokenService {

    private final HttpClient httpClient;

    public ResourceOwnerTokenService() {
        this.httpClient = HttpClient.newBuilder()
                                    .version(HttpClient.Version.HTTP_2)
                                    .build();
    }

    public HttpRequest.Builder getHttpRequestBuilder() {
        return HttpRequest.newBuilder();
    }

    public HttpResponse<String> sendRequest(HttpRequest request) throws IOException, InterruptedException {
        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
