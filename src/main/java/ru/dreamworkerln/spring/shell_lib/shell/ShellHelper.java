package ru.dreamworkerln.spring.shell_lib.shell;

import org.jline.terminal.Terminal;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.util.StringUtils;

public class ShellHelper {

    @Value("${shell.out.info.colour:CYAN}")
    public String infoColor;

    @Value("${shell.out.success.colour:GREEN}")
    public String successColor;

    @Value("${shell.out.warning.colour:YELLOW}")
    public String warningColor;

    @Value("${shell.out.error.colour:RED}")
    public String errorColor;

    private Terminal terminal;

    private ApplicationEventPublisher applicationEventPublisher;

    public ShellHelper(Terminal terminal,
                       ApplicationEventPublisher applicationEventPublisher) {
        this.terminal = terminal;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    /**
     * Construct colored message in the given color.
     *
     * @param message message to return
     * @param color   color to print
     * @return colored message
     */
    public String getColored(String message, PromptColor color) {

        if (StringUtils.isEmpty(message)) {
            return "";
        }

        return (new AttributedStringBuilder()).append(message, AttributedStyle.DEFAULT.foreground(color.toJlineAttributedStyle())).toAnsi();
    }

    public String getInfoMessage(String message) {
        return getColored(message, PromptColor.valueOf(infoColor));
    }

    public String getSuccessMessage(String message) {
        return getColored(message, PromptColor.valueOf(successColor));
    }

    public String getWarningMessage(String message) {
        return getColored(message, PromptColor.valueOf(warningColor));
    }

    public String getErrorMessage(String message) {
        return getColored(message, PromptColor.valueOf(errorColor));
    }

    //--- Print methods -------------------------------------------------------

    /**
     * Print message to the console in the default color.
     *
     * @param message message to print
     */
    public void print(String message) {
        print(message, null);
    }

    /**
     * Print message to the console in the success color.
     *
     * @param message message to print
     */
    public void printSuccess(String message) {
        print(message, PromptColor.valueOf(successColor));
    }

    /**
     * Print message to the console in the info color.
     *
     * @param message message to print
     */
    public void printInfo(String message) {
        print(message, PromptColor.valueOf(infoColor));
    }

    /**
     * Print message to the console in the warning color.
     *
     * @param message message to print
     */
    public void printWarning(String message) {
        print(message, PromptColor.valueOf(warningColor));
    }

    /**
     * Print message to the console in the error color.
     *
     * @param message message to print
     */
    public void printError(String message) {
        print(message, PromptColor.valueOf(errorColor));
    }

    /**
     * Generic Print to the console method.
     *
     * @param message message to print
     * @param color   (optional) prompt color
     */
    public void print(String message, PromptColor color) {
        String toPrint = message;
        if (color != null) {
            toPrint = getColored(message, color);
        }
        terminal.writer().println(toPrint);
        terminal.flush();
    }

    //--- set / get methods ---------------------------------------------------

    public Terminal getTerminal() {
        return terminal;
    }

    public void setTerminal(Terminal terminal) {
        this.terminal = terminal;
    }


    public void setShellPromptText(final String text) {
        ShellPromptChangedEvent event = new ShellPromptChangedEvent(this, text);
        applicationEventPublisher.publishEvent(event);
    }
}
