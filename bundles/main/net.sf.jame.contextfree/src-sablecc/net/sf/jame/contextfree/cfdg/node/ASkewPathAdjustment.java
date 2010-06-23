/* This file was generated by SableCC (http://www.sablecc.org/). */

package net.sf.jame.contextfree.cfdg.node;

import net.sf.jame.contextfree.cfdg.analysis.*;

@SuppressWarnings("nls")
public final class ASkewPathAdjustment extends PPathAdjustment
{
    private TSkewToken _skewToken_;
    private PFirstExpression _firstExpression_;
    private PSecondExpression _secondExpression_;

    public ASkewPathAdjustment()
    {
        // Constructor
    }

    public ASkewPathAdjustment(
        @SuppressWarnings("hiding") TSkewToken _skewToken_,
        @SuppressWarnings("hiding") PFirstExpression _firstExpression_,
        @SuppressWarnings("hiding") PSecondExpression _secondExpression_)
    {
        // Constructor
        setSkewToken(_skewToken_);

        setFirstExpression(_firstExpression_);

        setSecondExpression(_secondExpression_);

    }

    @Override
    public Object clone()
    {
        return new ASkewPathAdjustment(
            cloneNode(this._skewToken_),
            cloneNode(this._firstExpression_),
            cloneNode(this._secondExpression_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseASkewPathAdjustment(this);
    }

    public TSkewToken getSkewToken()
    {
        return this._skewToken_;
    }

    public void setSkewToken(TSkewToken node)
    {
        if(this._skewToken_ != null)
        {
            this._skewToken_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._skewToken_ = node;
    }

    public PFirstExpression getFirstExpression()
    {
        return this._firstExpression_;
    }

    public void setFirstExpression(PFirstExpression node)
    {
        if(this._firstExpression_ != null)
        {
            this._firstExpression_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._firstExpression_ = node;
    }

    public PSecondExpression getSecondExpression()
    {
        return this._secondExpression_;
    }

    public void setSecondExpression(PSecondExpression node)
    {
        if(this._secondExpression_ != null)
        {
            this._secondExpression_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._secondExpression_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._skewToken_)
            + toString(this._firstExpression_)
            + toString(this._secondExpression_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._skewToken_ == child)
        {
            this._skewToken_ = null;
            return;
        }

        if(this._firstExpression_ == child)
        {
            this._firstExpression_ = null;
            return;
        }

        if(this._secondExpression_ == child)
        {
            this._secondExpression_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._skewToken_ == oldChild)
        {
            setSkewToken((TSkewToken) newChild);
            return;
        }

        if(this._firstExpression_ == oldChild)
        {
            setFirstExpression((PFirstExpression) newChild);
            return;
        }

        if(this._secondExpression_ == oldChild)
        {
            setSecondExpression((PSecondExpression) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
