package mobi.inthepocket.android.beacons.app.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import mobi.inthepocket.android.beacons.app.R;
import mobi.inthepocket.android.beacons.ibeaconscanner.Beacon;

/**
 * Created by elias on 26/10/2016.
 */

public class BeaconAdapter extends RecyclerView.Adapter<BeaconViewHolder>
{
    private final LayoutInflater inflater;
    private final List<Pair<Beacon, Integer>> data;

    public BeaconAdapter(@NonNull final Context context)
    {
        this.inflater = LayoutInflater.from(context);

        this.data = new ArrayList<>();
    }

    public void updateBeacon(final Beacon beacon, int rssi)
    {
        // exists already?
        int index = 0;

        for (final Pair<Beacon, Integer> item : this.data)
        {
            if (item.first.equals(beacon))
            {
                this.data.set(index, new Pair<> (beacon, rssi));
                this.notifyItemChanged(index);
                return;
            }

            index++;
        }

        this.data.add(new Pair<> (beacon, rssi));
        this.notifyItemInserted(index);
    }

    public void removeBeacon(final Beacon beacon)
    {
        int index = 0;

        for (final Pair<Beacon, Integer> item : this.data)
        {
            if (item.first.equals(beacon))
            {
                this.data.remove(index);
                this.notifyItemRemoved(index);
                return;
            }

            index++;
        }
    }

    /**
     * Removes all beacons from the adapter.
     */
    public void clear()
    {
        final int count = this.getItemCount();

        this.data.clear();

        this.notifyItemRangeRemoved(0, count);
    }

    @Override
    public BeaconViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        final View view = this.inflater.inflate(R.layout.listitem_beacon, parent, false);
        return new BeaconViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BeaconViewHolder holder, int position)
    {
        Pair<Beacon, Integer> data = this.getData(position);
        holder.bindData(data.first, data.second);
    }

    @Override
    public int getItemCount()
    {
        return data.size();
    }

    private Pair<Beacon, Integer> getData(final int position)
    {
        return this.data.get(position);
    }
}
