/* This file was generated by SableCC (http://www.sablecc.org/). */

package net.sf.jame.contextfree.cfdg.node;

import net.sf.jame.contextfree.cfdg.analysis.Analysis;

@SuppressWarnings("nls")
public final class TWhiteSpace extends Token
{
    public TWhiteSpace(String text)
    {
        setText(text);
    }

    public TWhiteSpace(String text, int line, int pos)
    {
        setText(text);
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TWhiteSpace(getText(), getLine(), getPos());
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTWhiteSpace(this);
    }
}
