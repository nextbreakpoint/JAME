/* This file was generated by SableCC (http://www.sablecc.org/). */

package net.sf.jame.contextfree.cfdg.node;

import net.sf.jame.contextfree.cfdg.analysis.*;

@SuppressWarnings("nls")
public final class ASimpleColorAdjustment extends PColorAdjustment
{
    private PSimpleColorAdjustment _simpleColorAdjustment_;

    public ASimpleColorAdjustment()
    {
        // Constructor
    }

    public ASimpleColorAdjustment(
        @SuppressWarnings("hiding") PSimpleColorAdjustment _simpleColorAdjustment_)
    {
        // Constructor
        setSimpleColorAdjustment(_simpleColorAdjustment_);

    }

    @Override
    public Object clone()
    {
        return new ASimpleColorAdjustment(
            cloneNode(this._simpleColorAdjustment_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseASimpleColorAdjustment(this);
    }

    public PSimpleColorAdjustment getSimpleColorAdjustment()
    {
        return this._simpleColorAdjustment_;
    }

    public void setSimpleColorAdjustment(PSimpleColorAdjustment node)
    {
        if(this._simpleColorAdjustment_ != null)
        {
            this._simpleColorAdjustment_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._simpleColorAdjustment_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._simpleColorAdjustment_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._simpleColorAdjustment_ == child)
        {
            this._simpleColorAdjustment_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._simpleColorAdjustment_ == oldChild)
        {
            setSimpleColorAdjustment((PSimpleColorAdjustment) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
