package ru.dreamworkerln.spring.shell_lib.shell;

import org.springframework.context.ApplicationEvent;

public class ShellPromptChangedEvent extends ApplicationEvent {
    private String text;

    public ShellPromptChangedEvent(Object source, String text) {
        super(source);
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
