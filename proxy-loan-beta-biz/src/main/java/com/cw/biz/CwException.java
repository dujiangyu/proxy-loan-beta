package com.cw.biz;

/**
 * Created by dujy on 2017-05-21.
 */
public class CwException  extends RuntimeException {
    public CwException() {
    }
    public CwException(String message)
    {
        super(message);
    }

    public CwException(String message,Throwable throwable)
    {
        super(message,throwable);
    }

    public CwException(Throwable throwable)
    {
        super(throwable);
    }

    public static void throwIt(String message)
    {
        throw new CwException(message);
    }

    public static void throwIt(String message,Throwable e)
    {
        throw new CwException(message,e);
    }

    public static void throwIt(Throwable throwable)
    {
        throw new CwException(throwable);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
