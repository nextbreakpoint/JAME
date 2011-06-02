/* This file was generated by SableCC (http://www.sablecc.org/). */

package net.sf.jame.contextfree.cfdg.node;

import net.sf.jame.contextfree.cfdg.analysis.Analysis;

@SuppressWarnings("nls")
public final class AFunctionExtendedExpression extends PExtendedExpression
{
    private PFunction _function_;

    public AFunctionExtendedExpression()
    {
        // Constructor
    }

    public AFunctionExtendedExpression(
        @SuppressWarnings("hiding") PFunction _function_)
    {
        // Constructor
        setFunction(_function_);

    }

    @Override
    public Object clone()
    {
        return new AFunctionExtendedExpression(
            cloneNode(this._function_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAFunctionExtendedExpression(this);
    }

    public PFunction getFunction()
    {
        return this._function_;
    }

    public void setFunction(PFunction node)
    {
        if(this._function_ != null)
        {
            this._function_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._function_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._function_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._function_ == child)
        {
            this._function_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._function_ == oldChild)
        {
            setFunction((PFunction) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
