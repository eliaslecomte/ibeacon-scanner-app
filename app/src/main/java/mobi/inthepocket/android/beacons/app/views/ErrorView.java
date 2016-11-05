package mobi.inthepocket.android.beacons.app.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mobi.inthepocket.android.beacons.app.R;
import mobi.inthepocket.android.beacons.ibeaconscanner.Error;

/**
 * Created by elias on 03/11/2016.
 */

public class ErrorView extends AbstractStateView
{
    @BindView(R.id.textview_error)
    TextView textViewError;

    private RetryClickListener retryClickListener;

    public ErrorView(Context context)
    {
        this(context, null);
    }

    public ErrorView(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public ErrorView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);

        LayoutInflater.from(context).inflate(R.layout.view_error, this);

        ButterKnife.bind(this);

        this.setOrientation(LinearLayout.VERTICAL);
        this.setGravity(Gravity.CENTER);
    }

    @OnClick(R.id.button_retry)
    public void onButtonRetryClicked()
    {
        if (this.retryClickListener != null)
        {
            this.retryClickListener.OnRetryClicked();
        }
    }

    public void setError(final Error error)
    {
        final int errorResourceId;

        switch (error)
        {
            case NO_BLUETOOTH_LE:
                errorResourceId = R.string.state_no_bluetooth;
                break;

            case BLUETOOTH_OFF:
                errorResourceId = R.string.state_bluetooth_off;
                break;

            case LOCATION_OFF:
                errorResourceId = R.string.state_location_off;
                break;

            case NO_LOCATION_PERMISSION:
                errorResourceId = R.string.state_no_permission;
                break;

            default:
                errorResourceId = R.string.state_general_error;
                break;
        }

        this.textViewError.setText(this.getContext().getString(errorResourceId));
    }

    //region Properties

    public void setRetryClickListener(final RetryClickListener retryClickListener)
    {
        this.retryClickListener = retryClickListener;
    }

    //endregion

    //region RetryClickListener

    public interface RetryClickListener
    {
        void OnRetryClicked();
    }

    //endregion
}
