package com.gp.gpscript.keymgr.util.test;

public class SimpleTestResult
    implements TestResult
{
    private boolean success;
    private String  message;

    public SimpleTestResult(
        boolean success,
        String  message)
    {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccessful()
    {
        return success;
    }

    public String toString()
    {
        return message;
    }
}
