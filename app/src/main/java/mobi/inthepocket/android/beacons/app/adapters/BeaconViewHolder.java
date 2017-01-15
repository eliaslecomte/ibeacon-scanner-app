package mobi.inthepocket.android.beacons.app.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import mobi.inthepocket.android.beacons.app.R;
import mobi.inthepocket.android.beacons.ibeaconscanner.Beacon;

/**
 * Created by elias on 26/10/2016.
 */

public class BeaconViewHolder extends RecyclerView.ViewHolder
{
    @BindView(R.id.textview_UUID)
    TextView textViewBeaconUUID;
    @BindView(R.id.textview_major)
    TextView textViewMajor;
    @BindView(R.id.textview_minor)
    TextView textViewMinor;
    @BindView(R.id.textview_rssi)
    TextView textViewRssi;
    @BindView(R.id.progressbar_rssi)
    ProgressBar progressBarRssi;

    public BeaconViewHolder(View itemView)
    {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }

    public void bindData(final Beacon beacon, int rssi)
    {
        this.textViewBeaconUUID.setText(beacon.getUUID().toString());
        this.textViewMajor.setText(String.valueOf(beacon.getMajor()));
        this.textViewMinor.setText(String.valueOf(beacon.getMinor()));
        this.textViewRssi.setText(String.valueOf(rssi));
        this.progressBarRssi.setProgress(rssi*-1+127);
    }
}
