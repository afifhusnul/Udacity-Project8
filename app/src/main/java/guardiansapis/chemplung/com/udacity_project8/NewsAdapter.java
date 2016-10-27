package guardiansapis.chemplung.com.udacity_project8;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by NUSNAFIF on 10/25/2016.
 */

public class NewsAdapter extends ArrayAdapter<News> {
    /** Tag for the log messages */
    private static final String LOG_TAG = NewsAdapter.class.getSimpleName();

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd HH:mm:ss");
    Calendar calendar = new GregorianCalendar(2013,1,28,13,24,56);

    /**
    * Constructs a new {@link NewsAdapter}.
    *
    * @param context of the app
    * @param newsList is the list of earthquakes, which is the data source of the adapter
    */
    public NewsAdapter(Context context, List<News> newsList) { super(context, 0, newsList);  }

    /**
     * Returns a list item view that displays information about the earthquake at the given position
     * in the list of earthquakes.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_list_item, parent, false);
        }

        News currentNews = getItem(position);

        TextView titleTextView = (TextView) listItemView.findViewById(R.id.news_title);
        titleTextView.setText(currentNews.getTitle());

        TextView dateTextView = (TextView) listItemView.findViewById(R.id.news_date);
        dateTextView.setText("Date : "+ currentNews.getPublishDate());

        TextView sectionTextView = (TextView) listItemView.findViewById(R.id.news_section);
        sectionTextView.setText("Section : "+ currentNews.getSection());

        TextView authorTextView = (TextView) listItemView.findViewById(R.id.news_author);
        authorTextView.setText("Contributor : "+ currentNews.getAuthor());

        return listItemView;
    }
}
