package mobi.inthepocket.android.beacons.app.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import mobi.inthepocket.android.beacons.app.R;

/**
 * Created by elias on 03/11/2016.
 */

public class ScanningView extends AbstractStateView
{
    public ScanningView(final Context context)
    {
        this(context, null);
    }

    public ScanningView(final Context context, final AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public ScanningView(final Context context, final AttributeSet attrs, final int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);

        LayoutInflater.from(context).inflate(R.layout.view_scanning, this);

        this.setOrientation(LinearLayout.VERTICAL);
        this.setGravity(Gravity.CENTER);
    }
}
