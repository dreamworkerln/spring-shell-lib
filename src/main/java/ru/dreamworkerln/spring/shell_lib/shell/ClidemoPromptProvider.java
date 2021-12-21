package ru.dreamworkerln.spring.shell_lib.shell;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.context.event.EventListener;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;

@Component
public class ClidemoPromptProvider implements PromptProvider {

    private String promptText = "shell:$ ";

    @Override
    public AttributedString getPrompt() {
        return new AttributedString(promptText,
            AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW | AttributedStyle.BRIGHT));
    }

    @EventListener
    public void handle(ShellPromptChangedEvent event) {
        this.promptText = event.getText();
    }
}
