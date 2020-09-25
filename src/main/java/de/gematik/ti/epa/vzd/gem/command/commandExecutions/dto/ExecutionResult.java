package de.gematik.ti.epa.vzd.gem.command.commandExecutions.dto;

public class ExecutionResult {

    private final String message;
    private final Boolean result;

    public ExecutionResult(String message, Boolean result) {
        this.message = message;
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public Boolean getResult() {
        return result;
    }

}
