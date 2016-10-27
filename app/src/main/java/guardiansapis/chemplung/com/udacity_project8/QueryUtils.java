package guardiansapis.chemplung.com.udacity_project8;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by NUSNAFIF on 10/25/2016.
 */

public class QueryUtils {

    private static String webTitle;
    private static String sectionName;
    private static String pubDate;
    private static String authorName;
    private static String webUrl;

    /**
     * Tag for the log messages
     */
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Query the USGS dataset and return a list of {@link News} objects.
     */
    public static List<News> fetchNewsData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link News}s
        List<News> news = extractFeatureFromJson(jsonResponse);

        // Return the list of {@link news}s
        return news;
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the News JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return a list of {@link News} objects that has been built up from
     * parsing a JSON response.
     */
    public static ArrayList<News> extractFeatureFromJson(String jsonResponse) {
        Log.i(LOG_TAG,"Info : Called Extract data from Json");
        // Create an empty ArrayList that we can start adding newses to
        ArrayList<News> newList = new ArrayList<>();

        /*
        Try to parse the GUARDIAN_API_URL. If there's a problem with the way the JSON
        is formatted, a JSONException exception object will be thrown.
        Catch the exception so the app doesn't crash, and handle exception.
        */
        try {
            // TODO: Parse the response given by the jsonResponse string

            JSONObject newsJsonResponse = new JSONObject(jsonResponse);
            if (newsJsonResponse.has("response")) {
                JSONObject response = newsJsonResponse.getJSONObject("response");
                if (response.has("results")) {
                    JSONArray resultsArray = response.getJSONArray("results");

                    for (int i = 0; i < resultsArray.length(); i++) {
                        JSONObject currentNews = resultsArray.getJSONObject(i);
                        if (currentNews.has("webTitle")) {
                            webTitle = currentNews.getString("webTitle");
                        }
                        if (currentNews.has("webUrl")) {
                            webUrl = currentNews.getString("webUrl");
                        }
                        if (currentNews.has("webPublicationDate")) {
                            pubDate = currentNews.getString("webPublicationDate");
                        }
                        if (currentNews.has("sectionName")) {
                            sectionName = currentNews.getString("sectionName");
                        }
                        if (currentNews.has("tags")) {
                            JSONArray tagsArray = currentNews.getJSONArray("tags");
                            if (tagsArray.length() > 0) {
                                JSONObject tagsDetails = tagsArray.getJSONObject(0);
                                if (tagsDetails.has("webTitle")) {
                                    authorName = tagsDetails.getString("webTitle");
                                }
                            }
                        }
                        newList.add(new News(webTitle, pubDate, sectionName, authorName, webUrl));
                    }
                }
            }
        } catch (JSONException e) {
            //handle exception
            Log.e("QueryUtils", "Problem parsing the News JSON results", e);
        }
        // Return the list of newses
        return newList;
    }
}
