/* This file was generated by SableCC (http://www.sablecc.org/). */

package net.sf.jame.contextfree.cfdg.node;

import net.sf.jame.contextfree.cfdg.analysis.Analysis;

@SuppressWarnings("nls")
public final class AArg0Function extends PFunction
{
    private TFunctionArg0 _functionArg0_;
    private TLRbkt _lRbkt_;
    private TRRbkt _rRbkt_;

    public AArg0Function()
    {
        // Constructor
    }

    public AArg0Function(
        @SuppressWarnings("hiding") TFunctionArg0 _functionArg0_,
        @SuppressWarnings("hiding") TLRbkt _lRbkt_,
        @SuppressWarnings("hiding") TRRbkt _rRbkt_)
    {
        // Constructor
        setFunctionArg0(_functionArg0_);

        setLRbkt(_lRbkt_);

        setRRbkt(_rRbkt_);

    }

    @Override
    public Object clone()
    {
        return new AArg0Function(
            cloneNode(this._functionArg0_),
            cloneNode(this._lRbkt_),
            cloneNode(this._rRbkt_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAArg0Function(this);
    }

    public TFunctionArg0 getFunctionArg0()
    {
        return this._functionArg0_;
    }

    public void setFunctionArg0(TFunctionArg0 node)
    {
        if(this._functionArg0_ != null)
        {
            this._functionArg0_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._functionArg0_ = node;
    }

    public TLRbkt getLRbkt()
    {
        return this._lRbkt_;
    }

    public void setLRbkt(TLRbkt node)
    {
        if(this._lRbkt_ != null)
        {
            this._lRbkt_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._lRbkt_ = node;
    }

    public TRRbkt getRRbkt()
    {
        return this._rRbkt_;
    }

    public void setRRbkt(TRRbkt node)
    {
        if(this._rRbkt_ != null)
        {
            this._rRbkt_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._rRbkt_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._functionArg0_)
            + toString(this._lRbkt_)
            + toString(this._rRbkt_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._functionArg0_ == child)
        {
            this._functionArg0_ = null;
            return;
        }

        if(this._lRbkt_ == child)
        {
            this._lRbkt_ = null;
            return;
        }

        if(this._rRbkt_ == child)
        {
            this._rRbkt_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._functionArg0_ == oldChild)
        {
            setFunctionArg0((TFunctionArg0) newChild);
            return;
        }

        if(this._lRbkt_ == oldChild)
        {
            setLRbkt((TLRbkt) newChild);
            return;
        }

        if(this._rRbkt_ == oldChild)
        {
            setRRbkt((TRRbkt) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
