package ru.kozorez;

public class CommandResult {
    private final boolean isSuccess;
    private final String output;

    public CommandResult(boolean isSuccess, String output) {
        this.isSuccess = isSuccess;
        this.output = output;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public String getOutput() {
        return output;
    }
}