/* This file was generated by SableCC (http://www.sablecc.org/). */

package net.sf.jame.contextfree.cfdg.node;

import net.sf.jame.contextfree.cfdg.analysis.*;

@SuppressWarnings("nls")
public final class TFunctionArg0 extends Token
{
    public TFunctionArg0()
    {
        super.setText("rnd");
    }

    public TFunctionArg0(int line, int pos)
    {
        super.setText("rnd");
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TFunctionArg0(getLine(), getPos());
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTFunctionArg0(this);
    }

    @Override
    public void setText(@SuppressWarnings("unused") String text)
    {
        throw new RuntimeException("Cannot change TFunctionArg0 text.");
    }
}
