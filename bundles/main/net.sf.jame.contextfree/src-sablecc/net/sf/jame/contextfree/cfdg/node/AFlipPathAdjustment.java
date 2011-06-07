/* This file was generated by SableCC (http://www.sablecc.org/). */

package net.sf.jame.contextfree.cfdg.node;

import net.sf.jame.contextfree.cfdg.analysis.*;

@SuppressWarnings("nls")
public final class AFlipPathAdjustment extends PPathAdjustment
{
    private TFlipToken _flipToken_;
    private PExpression _expression_;

    public AFlipPathAdjustment()
    {
        // Constructor
    }

    public AFlipPathAdjustment(
        @SuppressWarnings("hiding") TFlipToken _flipToken_,
        @SuppressWarnings("hiding") PExpression _expression_)
    {
        // Constructor
        setFlipToken(_flipToken_);

        setExpression(_expression_);

    }

    @Override
    public Object clone()
    {
        return new AFlipPathAdjustment(
            cloneNode(this._flipToken_),
            cloneNode(this._expression_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAFlipPathAdjustment(this);
    }

    public TFlipToken getFlipToken()
    {
        return this._flipToken_;
    }

    public void setFlipToken(TFlipToken node)
    {
        if(this._flipToken_ != null)
        {
            this._flipToken_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._flipToken_ = node;
    }

    public PExpression getExpression()
    {
        return this._expression_;
    }

    public void setExpression(PExpression node)
    {
        if(this._expression_ != null)
        {
            this._expression_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._expression_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._flipToken_)
            + toString(this._expression_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._flipToken_ == child)
        {
            this._flipToken_ = null;
            return;
        }

        if(this._expression_ == child)
        {
            this._expression_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._flipToken_ == oldChild)
        {
            setFlipToken((TFlipToken) newChild);
            return;
        }

        if(this._expression_ == oldChild)
        {
            setExpression((PExpression) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
