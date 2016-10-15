/* This file was generated by SableCC (http://www.sablecc.org/). */

package net.sf.jame.contextfree.cfdg.node;

import net.sf.jame.contextfree.cfdg.analysis.*;

@SuppressWarnings("nls")
public final class TRxToken extends Token
{
    public TRxToken()
    {
        super.setText("rx");
    }

    public TRxToken(int line, int pos)
    {
        super.setText("rx");
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TRxToken(getLine(), getPos());
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTRxToken(this);
    }

    @Override
    public void setText(@SuppressWarnings("unused") String text)
    {
        throw new RuntimeException("Cannot change TRxToken text.");
    }
}