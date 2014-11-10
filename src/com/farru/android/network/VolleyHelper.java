package com.farru.android.network;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.ProtocolException;
import org.apache.http.client.CircularRedirectException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.RedirectHandler;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerPNames;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.RedirectLocations;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;

import android.content.Context;

import com.farru.android.listener.IRequest;
import com.farru.android.listener.IRequestCompletionListener;
import com.farru.android.volley.DefaultRetryPolicy;
import com.farru.android.volley.RequestQueue;
import com.farru.android.volley.toolbox.HttpClientStack;
import com.farru.android.volley.toolbox.Volley;

/**
 * Created  on 30/1/14.
 * Helper class for volley requests and response processing
 */
public class VolleyHelper implements IRequestCompletionListener {
    private static final int TIMEOUT_CONN = 5* 60* 1000;
    private static final int TIMEOUT_SO = 5* 60* 1000;
    private static final int MCC_TIMEOUT = 5* 60* 1000;
    
    private static final int API_TIMEOUT = 5* 60* 1000;

    private static VolleyHelper mInstance;
    private RequestQueue mQueue;
    private ArrayList<IRequest> mPendingRequests;
    private DefaultRetryPolicy mDefaultRetryPolicy;

    /**
     * Method for creating VolleyHelper Instance.
     * This Instance will have single RequestQueue.
     *
     * @param ctx Context Object
     * @return VolleyHelper instance
     */
    public static VolleyHelper getInstance(Context ctx) {
        if (mInstance == null) {
            mInstance = new VolleyHelper();
            HttpClient client = getNewHttpClient(new BasicHttpParams());
            mInstance.mQueue = Volley.newRequestQueue(ctx, new HttpClientStack(client));
            mInstance.mPendingRequests = new ArrayList<IRequest>();
            mInstance.mDefaultRetryPolicy = new DefaultRetryPolicy(
                    API_TIMEOUT,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        }

        return mInstance;
    }

    /**
     * Adds request in Volley request queue and keep the request for updating listeners in case of rotation
     *
     * @param request Request To be added in Volley queue
     */

    public void addRequestInQueue(com.farru.android.volley.Request request) {
        if (null == request) {
            return;
        }
        /* If retry policy is not set explicitly..e.g setting retry policy for COD in BaseActivity*/
        /*if(request instanceof VolleyGenericRequest && ((VolleyGenericRequest) request).getEventType() == ApiType.API_PAYMENT_COD)
            request.setRetryPolicy(getRetryPolicyForCOD());
        else*/
        request.setRetryPolicy(mDefaultRetryPolicy);

        mQueue.add(request);
        if (request instanceof IRequest) {
            ((IRequest) request).setOnCompletedListener(this);
            mPendingRequests.add((IRequest) request);
        }

    }

    /* For COD only, timeout increased to 90 seconds*/
   /* private DefaultRetryPolicy getRetryPolicyForCOD(){
        DefaultRetryPolicy mRetryPolicyForCOD = new DefaultRetryPolicy(
                Constants.API_TIMEOUT_FOR_COD,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        );

        return mRetryPolicyForCOD;
    }*/

    /**
     * Updates Listeners of all Pending Requests
     *
     * @param newListener new listener to be registered
     */

    public boolean updateListeners(Object newListener) {
        boolean isUpdated = false;

        for (IRequest req : mPendingRequests) {
            isUpdated |= req.updateListeners(newListener);

        }
        return isUpdated;
    }

    /**
     * Callback on request completion
     *
     * @param isSuccess True If request completed successfully
     * @param request   Processed request
     */
    @Override
    public void onRequestProcessed(boolean isSuccess, IRequest request) {

        //       Log.d("VolleyHelper_onRequestProcessed", "" + request.toString());

        mPendingRequests.remove(request);
    }

    /**
     * @param params
     * @return HttpClient
     */
    public static HttpClient getNewHttpClient(HttpParams params) {
        try {

            SchemeRegistry schemeRegistry = new SchemeRegistry();

            schemeRegistry.register(new Scheme("http", PlainSocketFactory
                    .getSocketFactory(), 80));
            schemeRegistry.register(new Scheme("https",
                    new EasySSLSocketFactory(), 443));

            params.setParameter(ConnManagerPNames.MAX_TOTAL_CONNECTIONS, 4);
            params.setParameter(ConnManagerPNames.MAX_CONNECTIONS_PER_ROUTE,
                    new ConnPerRouteBean(4));
            params.setParameter(HttpProtocolParams.USE_EXPECT_CONTINUE, false);
            params.setLongParameter(ConnManagerPNames.TIMEOUT, MCC_TIMEOUT);
            HttpConnectionParams.setConnectionTimeout(params, TIMEOUT_CONN);
            HttpConnectionParams.setSoTimeout(params, TIMEOUT_SO);
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            ClientConnectionManager cm = new ThreadSafeClientConnManager(params,
                    schemeRegistry);

            // HttpsURLConnection.setDefaultHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);


            return new DefaultHttpClient(cm, params){
                @Override
                protected RedirectHandler createRedirectHandler() {
                    final RedirectHandler superHandler= super.createRedirectHandler();
                    RedirectHandler handler=new RedirectHandler() {
                        @Override
                        public boolean isRedirectRequested(HttpResponse httpResponse, HttpContext httpContext) {
                            return superHandler.isRedirectRequested(httpResponse,httpContext);
                        }

                        @Override
                        public URI getLocationURI(HttpResponse response, HttpContext context) throws ProtocolException {
                              final String REDIRECT_LOCATIONS = "http.protocol.redirect-locations";
                        //    URI uri=superHandler.getLocationURI(httpResponse,httpContext);
                      //      URI newUri=URI.create(URLEncoder.encode(httpResponse.get));

                            if (response == null) {
                                throw new IllegalArgumentException("HTTP response may not be null");
                            }
                            //get the location header to find out where to redirect to
                            Header locationHeader = response.getFirstHeader("location");
                            if (locationHeader == null) {
                                // got a redirect response, but no location header
                                throw new ProtocolException(
                                        "Received redirect response " + response.getStatusLine()
                                                + " but no location header");
                            }
                            String location =locationHeader.getValue().replaceAll(" ","%20");


                            URI uri;
                            try {
                                uri = new URI(location);
                            } catch (URISyntaxException ex) {
                                throw new ProtocolException("Invalid redirect URI: " + location, ex);
                            }

                            HttpParams params = response.getParams();
                            // rfc2616 demands the location value be a complete URI
                            // Location       = "Location" ":" absoluteURI
                            if (!uri.isAbsolute()) {
                                if (params.isParameterTrue(ClientPNames.REJECT_RELATIVE_REDIRECT)) {
                                    throw new ProtocolException("Relative redirect location '"
                                            + uri + "' not allowed");
                                }
                                // Adjust location URI
                                HttpHost target = (HttpHost) context.getAttribute(
                                        ExecutionContext.HTTP_TARGET_HOST);
                                if (target == null) {
                                    throw new IllegalStateException("Target host not available " +
                                            "in the HTTP context");
                                }

                                HttpRequest request = (HttpRequest) context.getAttribute(
                                        ExecutionContext.HTTP_REQUEST);

                                try {
                                    URI requestURI = new URI(request.getRequestLine().getUri());
                                    URI absoluteRequestURI = URIUtils.rewriteURI(requestURI, target, true);
                                    uri = URIUtils.resolve(absoluteRequestURI, uri);
                                } catch (URISyntaxException ex) {
                                    throw new ProtocolException(ex.getMessage(), ex);
                                }
                            }

                            if (params.isParameterFalse(ClientPNames.ALLOW_CIRCULAR_REDIRECTS)) {

                                RedirectLocations redirectLocations = (RedirectLocations) context.getAttribute(
                                        REDIRECT_LOCATIONS);

                                if (redirectLocations == null) {
                                    redirectLocations = new RedirectLocations();
                                    context.setAttribute(REDIRECT_LOCATIONS, redirectLocations);
                                }

                                URI redirectURI;
                                if (uri.getFragment() != null) {
                                    try {
                                        HttpHost target = new HttpHost(
                                                uri.getHost(),
                                                uri.getPort(),
                                                uri.getScheme());
                                        redirectURI = URIUtils.rewriteURI(uri, target, true);
                                    } catch (URISyntaxException ex) {
                                        throw new ProtocolException(ex.getMessage(), ex);
                                    }
                                } else {
                                    redirectURI = uri;
                                }

                                if (redirectLocations.contains(redirectURI)) {
                                    throw new CircularRedirectException("Circular redirect to '" +
                                            redirectURI + "'");
                                } else {
                                    redirectLocations.add(redirectURI);
                                }
                            }

                            return uri;
                        }
                    };
                    return handler;
                }

            };
        } catch (Exception e) {
            params.setLongParameter(ConnManagerPNames.TIMEOUT, MCC_TIMEOUT);
            HttpConnectionParams.setConnectionTimeout(params, TIMEOUT_CONN);
            HttpConnectionParams.setSoTimeout(params, TIMEOUT_SO);
            return new DefaultHttpClient(params);
        }
    }
    //TODO have to destroy queue instance
}
