package api;

import javax.net.ssl.HttpsURLConnection;
import java.net.URL;


/**
 * Class APICall - Overrides HTTPS API method calling.
 * @author Martin Rios - Junior Developer
 * @version 6.1
 */
// TODO: Set Open-Closed Principle on APICall
// TODO: Set Composition-Over-Inheritance
public class SecureAPICall extends APICall {
    @Override
    protected HttpsURLConnection getURLConnection(URL url) throws APICallException {
        try {
            return (HttpsURLConnection) url.openConnection();
        } catch (Exception error){
            throw new APICallException(error.getMessage());
        }
    }
}
