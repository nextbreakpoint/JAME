/* This file was generated by SableCC (http://www.sablecc.org/). */

package net.sf.jame.contextfree.cfdg.node;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import net.sf.jame.contextfree.cfdg.analysis.Analysis;

@SuppressWarnings("nls")
public final class ABasicPathCommand extends PPathCommand
{
    private TCommand _command_;
    private TLSbkt _lSbkt_;
    private final LinkedList<PCommandParameter> _commandParameter_ = new LinkedList<PCommandParameter>();
    private TRSbkt _rSbkt_;

    public ABasicPathCommand()
    {
        // Constructor
    }

    public ABasicPathCommand(
        @SuppressWarnings("hiding") TCommand _command_,
        @SuppressWarnings("hiding") TLSbkt _lSbkt_,
        @SuppressWarnings("hiding") List<PCommandParameter> _commandParameter_,
        @SuppressWarnings("hiding") TRSbkt _rSbkt_)
    {
        // Constructor
        setCommand(_command_);

        setLSbkt(_lSbkt_);

        setCommandParameter(_commandParameter_);

        setRSbkt(_rSbkt_);

    }

    @Override
    public Object clone()
    {
        return new ABasicPathCommand(
            cloneNode(this._command_),
            cloneNode(this._lSbkt_),
            cloneList(this._commandParameter_),
            cloneNode(this._rSbkt_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseABasicPathCommand(this);
    }

    public TCommand getCommand()
    {
        return this._command_;
    }

    public void setCommand(TCommand node)
    {
        if(this._command_ != null)
        {
            this._command_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._command_ = node;
    }

    public TLSbkt getLSbkt()
    {
        return this._lSbkt_;
    }

    public void setLSbkt(TLSbkt node)
    {
        if(this._lSbkt_ != null)
        {
            this._lSbkt_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._lSbkt_ = node;
    }

    public LinkedList<PCommandParameter> getCommandParameter()
    {
        return this._commandParameter_;
    }

    public void setCommandParameter(List<PCommandParameter> list)
    {
        this._commandParameter_.clear();
        this._commandParameter_.addAll(list);
        for(PCommandParameter e : list)
        {
            if(e.parent() != null)
            {
                e.parent().removeChild(e);
            }

            e.parent(this);
        }
    }

    public TRSbkt getRSbkt()
    {
        return this._rSbkt_;
    }

    public void setRSbkt(TRSbkt node)
    {
        if(this._rSbkt_ != null)
        {
            this._rSbkt_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._rSbkt_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._command_)
            + toString(this._lSbkt_)
            + toString(this._commandParameter_)
            + toString(this._rSbkt_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._command_ == child)
        {
            this._command_ = null;
            return;
        }

        if(this._lSbkt_ == child)
        {
            this._lSbkt_ = null;
            return;
        }

        if(this._commandParameter_.remove(child))
        {
            return;
        }

        if(this._rSbkt_ == child)
        {
            this._rSbkt_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._command_ == oldChild)
        {
            setCommand((TCommand) newChild);
            return;
        }

        if(this._lSbkt_ == oldChild)
        {
            setLSbkt((TLSbkt) newChild);
            return;
        }

        for(ListIterator<PCommandParameter> i = this._commandParameter_.listIterator(); i.hasNext();)
        {
            if(i.next() == oldChild)
            {
                if(newChild != null)
                {
                    i.set((PCommandParameter) newChild);
                    newChild.parent(this);
                    oldChild.parent(null);
                    return;
                }

                i.remove();
                oldChild.parent(null);
                return;
            }
        }

        if(this._rSbkt_ == oldChild)
        {
            setRSbkt((TRSbkt) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
