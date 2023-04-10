package api;

import java.nio.charset.Charset;

/**
 * Interface StringConverter - APICall's new string conversion interface.
 * @author Martin Rios - Technical Leader
 * @version 7.0
 */
public interface StringConverter {
    String toString(String strCharset);
    String toString(Charset charset);
}
