/* This file was generated by SableCC (http://www.sablecc.org/). */

package net.sf.jame.contextfree.cfdg.node;

import net.sf.jame.contextfree.cfdg.analysis.*;

@SuppressWarnings("nls")
public final class AGeometryCommandParameter extends PCommandParameter
{
    private PPathAdjustment _pathAdjustment_;

    public AGeometryCommandParameter()
    {
        // Constructor
    }

    public AGeometryCommandParameter(
        @SuppressWarnings("hiding") PPathAdjustment _pathAdjustment_)
    {
        // Constructor
        setPathAdjustment(_pathAdjustment_);

    }

    @Override
    public Object clone()
    {
        return new AGeometryCommandParameter(
            cloneNode(this._pathAdjustment_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAGeometryCommandParameter(this);
    }

    public PPathAdjustment getPathAdjustment()
    {
        return this._pathAdjustment_;
    }

    public void setPathAdjustment(PPathAdjustment node)
    {
        if(this._pathAdjustment_ != null)
        {
            this._pathAdjustment_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._pathAdjustment_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._pathAdjustment_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._pathAdjustment_ == child)
        {
            this._pathAdjustment_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._pathAdjustment_ == oldChild)
        {
            setPathAdjustment((PPathAdjustment) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
