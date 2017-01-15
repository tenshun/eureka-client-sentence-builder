package builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

/**
 * 15.01.2017.
 */

@RestController
public class SentenceController {


    @Autowired
    private DiscoveryClient client;

    @RequestMapping("/sentence")
    public @ResponseBody String getSentence() {
        return
                getWord("SUBJECT") + " "
                        + getWord("VERB") + " "
                        + getWord("ARTICLE") + " "
                        + getWord("ADJECTIVE") + " "
                        + getWord("NOUN") + "."
                ;
    }

    public String getWord(String service) {
        List<ServiceInstance> list = client.getInstances(service);
        if (list != null && list.size() > 0 ) {
            URI uri = list.get(0).getUri();
            if (uri !=null ) {
                return (new RestTemplate()).getForObject(uri,String.class);
            }
        }
        return null;
    }
}
