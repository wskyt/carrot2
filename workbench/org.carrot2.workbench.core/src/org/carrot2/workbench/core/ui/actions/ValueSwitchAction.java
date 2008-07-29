package org.carrot2.workbench.core.ui.actions;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;

/**
 * A value switch action that implements {@link IPropertyChangeListener}
 * and actively reacts to changes on a given property, selecting the action
 * if the property's value equals the given value.
 */
public class ValueSwitchAction extends Action implements IPropertyChangeListener, IWorkbenchAction
{
    /**
     * The value of this action in the preference store.
     */
    public final String value;

    /**
     * The key of this action in the preference store.
     */
    public final String key;

    /**
     * The host of this property.
     */
    private IPropertyHost propertyHost;

    /*
     * 
     */
    public ValueSwitchAction(String key, String value, String label, int style, IPropertyHost host)
    {
        super(label, style);

        this.key = key;
        this.value = value;
        this.propertyHost = host;

        host.addPropertyChangeListener(this);
        
        updateState();
    }

    /*
     * 
     */
    @Override
    public void run()
    {
        propertyHost.setProperty(key, value);
    }

    /*
     * 
     */
    public void propertyChange(PropertyChangeEvent event)
    {
        if (ObjectUtils.equals(key, event.getProperty()))
        {
            updateState();
        }
    }

    /*
     * 
     */
    private void updateState()
    {
        final String current = propertyHost.getProperty(key);
        setChecked(StringUtils.equals(current, value));
    }

    /*
     * 
     */
    public void dispose()
    {
        propertyHost.removePropertyChangeListener(this);
    }
}