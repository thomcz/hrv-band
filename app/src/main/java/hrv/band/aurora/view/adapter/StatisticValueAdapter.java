package hrv.band.aurora.view.adapter;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import hrv.band.aurora.Control.HRVParameters;
import hrv.band.aurora.R;

/**
 * Created by Thomas on 28.06.2016.
 */
public class StatisticValueAdapter extends AbstractValueAdapter {
    private Context context;
    private int layout;
    private List<String> values;
    private List<HRVParameters> parameters;
    private HRVValue type;
    private final String timeFormat = "hh:mm aa";

    public StatisticValueAdapter(Context context, int textViewResourceId,
                                 HRVValue type, List<HRVParameters> parameters) {
        this.layout = textViewResourceId;
        this.context = context;
        this.type = type;
        this.parameters = parameters;
        values = getValues(this.parameters, type);
    }

    private List<String> getValues(List<HRVParameters> parameters, HRVValue type) {
        List<String> values = new ArrayList<>();
        for (int i = 0; i < parameters.size(); i++) {
            values.add(trimValue(getHRVValue(type, parameters.get(i))));
        }
        return values;
    }



    @Override
    public Object getItem(int i) {
        return parameters.get(i);
    }

    @Override
    public int getCount() {
        return parameters.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(layout, parent, false);

        TextView value = (TextView) rowView.findViewById(R.id.stats_value);
        TextView time = (TextView) rowView.findViewById(R.id.stats_time);
        //TextView unit = (TextView) rowView.findViewById(R.id.stats_type);

        DateFormat df = new DateFormat();
        time.setText(df.format(timeFormat, parameters.get(position).getTime()));

        value.setText(values.get(position));
        //unit.setText(type.getUnit());

        return rowView;
    }

    public void setDataset(List<HRVParameters> parameters) {
        this.parameters = parameters;
        values = getValues(this.parameters, type);
        notifyDataSetChanged();
    }
}