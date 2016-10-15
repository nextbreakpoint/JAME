/* This file was generated by SableCC (http://www.sablecc.org/). */

package net.sf.jame.contextfree.cfdg.node;

import net.sf.jame.contextfree.cfdg.analysis.*;

@SuppressWarnings("nls")
public final class ASizeSizeToken2 extends PSizeToken2
{
    private TSize _size_;

    public ASizeSizeToken2()
    {
        // Constructor
    }

    public ASizeSizeToken2(
        @SuppressWarnings("hiding") TSize _size_)
    {
        // Constructor
        setSize(_size_);

    }

    @Override
    public Object clone()
    {
        return new ASizeSizeToken2(
            cloneNode(this._size_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseASizeSizeToken2(this);
    }

    public TSize getSize()
    {
        return this._size_;
    }

    public void setSize(TSize node)
    {
        if(this._size_ != null)
        {
            this._size_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._size_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._size_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._size_ == child)
        {
            this._size_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._size_ == oldChild)
        {
            setSize((TSize) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}