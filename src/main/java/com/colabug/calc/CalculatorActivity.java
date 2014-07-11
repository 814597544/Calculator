package com.colabug.calc;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.colabug.calc.events.StoreValueEvent;
import com.colabug.calc.fragments.CalculatorStateFragment;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class CalculatorActivity extends FragmentActivity
{
    private String displayValue;

    @Override
    public void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );

        setContentView( R.layout.main );

        addCalculatorStateFragment();
    }

    private void addCalculatorStateFragment()
    {
        getSupportFragmentManager().beginTransaction()
                                   .add( CalculatorStateFragment.newInstance(), "state" )
                                   .commit();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        registerWithBus();
    }

    /**
     * Display should be updated.
     *
     * @param event - holds data to change view value
     */
    @Subscribe
    public void onSetDisplayValue( StoreValueEvent event )
    {
        this.displayValue = event.getValue();
    }

    public String getDisplayValue()
    {
        return displayValue;
    }

    @Override
    public void onPause()
    {
        super.onPause();
        unRegisterFromBus();
    }

    private void registerWithBus()
    {
        getBus().register( this );
    }

    private void unRegisterFromBus()
    {
        getBus().unregister( this );
    }

    protected Bus getBus()
    {
        return CalculatorApplication.getInstance().getBus();
    }
}
