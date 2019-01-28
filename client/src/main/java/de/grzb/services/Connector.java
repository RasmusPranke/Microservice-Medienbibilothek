package de.grzb.services;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.netflix.discovery.DiscoveryClient;

@Service
public class Connector {

    private final DiscoveryClient _discoveryClient;

    public Connector(DiscoveryClient discoveryClient) {
        _discoveryClient = discoveryClient;
    }

    public String getHostname(String applicationName) {
        return _discoveryClient.getApplication(applicationName).getInstances().get(0).getHostName();
    }

    public URI getUrlFor(String applicationName, String location) {
        String hostname = getHostname(applicationName);
        URI uri;
        try {
            uri = new URI("", hostname, location, "");
        }
        catch(URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
        return uri;
    }

    public <K> K getResult(String applicationName, String location, Class<K> type) {
        RestTemplate template = new RestTemplate();
        try {
            return template.getForEntity(getUrlFor(applicationName, location), type).getBody();
        }
        catch(RestClientException e) {
            e.printStackTrace();
            return null;
        }
    }
}
