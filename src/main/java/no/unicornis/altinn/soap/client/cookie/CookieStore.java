package no.unicornis.altinn.soap.client.cookie;

/**
 * Created by taldev on 14/10/16.
 *
 * Originally written by tba @ brreg
 */
public class CookieStore {
    @SuppressWarnings("CanBeFinal")
    private static ThreadLocal<Object> requestCookie = new ThreadLocal<>();

    public static void setCookie(Object cookie) {
        requestCookie.set(cookie);
    }

    public static Object getCookie() {
        return requestCookie.get();
    }
}

