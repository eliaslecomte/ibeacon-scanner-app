package mobi.inthepocket.android.beacons.app;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.LinearLayout;

import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import mobi.inthepocket.android.beacons.app.adapters.BeaconAdapter;
import mobi.inthepocket.android.beacons.app.rxjava.RxObserver;
import mobi.inthepocket.android.beacons.app.views.AbstractStateView;
import mobi.inthepocket.android.beacons.app.views.ErrorView;
import mobi.inthepocket.android.beacons.app.views.ScanningView;
import mobi.inthepocket.android.beacons.ibeaconscanner.Beacon;
import mobi.inthepocket.android.beacons.ibeaconscanner.Error;
import mobi.inthepocket.android.beacons.ibeaconscanner.IBeaconScanner;

public class MainActivity extends AppCompatActivity implements IBeaconScanner.Callback, ErrorView.RetryClickListener
{
    private static final int REQUEST_BLUETOOTH_ON = 123;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.layout_state)
    LinearLayout layoutState;

    private BeaconAdapter beaconAdapter;
    private AbstractStateView stateView;

    @Override
    protected void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        IBeaconScanner.getInstance().setCallback(this);

        this.beaconAdapter = new BeaconAdapter(this);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        this.recyclerView.setAdapter(this.beaconAdapter);
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        this.startScanning();
    }

    @Override
    protected void onPause()
    {
        super.onPause();

        this.beaconAdapter.clear();

        IBeaconScanner.getInstance()
                .stop();
    }



    //region Callback

    @Override
    public void didEnterBeacon(final Beacon beacon, int rssi)
    {
        this.beaconAdapter.updateBeacon(beacon, rssi);
        this.updateView(this.beaconAdapter.getItemCount(), null);
    }

    @Override
    public void didExitBeacon(final Beacon beacon)
    {
        this.beaconAdapter.removeBeacon(beacon);
        this.updateView(this.beaconAdapter.getItemCount(), null);
    }

    @Override
    public void monitoringDidFail(final Error error)
    {
        this.updateView(0, error);
    }

    //endregion

    //region View

    private void startScanning()
    {
        this.updateView(0, null);

        RxPermissions.getInstance(this)
                .request(Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe(new RxObserver<Boolean>()
                {
                    @Override
                    public void onNext(final Boolean granted)
                    {
                        if (granted)
                        {
                            // beacons enabled!
                            IBeaconScanner.getInstance()
                                    .startMonitoring(Beacon.newBuilder()
                                            .setUUID(UUID.randomUUID())
                                            .setMajor(1)
                                            .setMinor(1)
                                            .build());
                        }
                        else
                        {
                            // todo
                        }
                    }
                });
    }

    private void updateView(final int itemCount, @Nullable Error error)
    {
        AbstractStateView view;

        if (error != null)
        {
            view = new ErrorView(this);
            ((ErrorView) view).setRetryClickListener(this);
            ((ErrorView) view).setError(error);
            this.addStateView(view);
        }
        else
        {
            if (itemCount > 0)
            {
                this.removeStateView(this.stateView);
            }
            else
            {
                view = new ScanningView(this);
                this.addStateView(view);
            }
        }
    }

    private void addStateView(final AbstractStateView view)
    {
        if (this.stateView != null)
        {
            this.removeStateView(this.stateView);
        }

        if (this.layoutState != null && view != null)
        {
            this.layoutState.addView(view);

            this.stateView = view;
        }
    }

    private void removeStateView(final AbstractStateView view)
    {
        if (this.layoutState != null)
        {
            this.layoutState.removeView(view);
        }
    }

    //endregion

    //region RetryClickListener

    @Override
    public void onRetryClicked()
    {
        this.startScanning();
    }

    @Override
    public void onBluetoothOnClicked()
    {
        if (!BluetoothAdapter.getDefaultAdapter().isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_BLUETOOTH_ON);
        }
    }

    //endregion
}
