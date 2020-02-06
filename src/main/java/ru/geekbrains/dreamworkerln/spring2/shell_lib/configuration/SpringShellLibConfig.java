package ru.geekbrains.dreamworkerln.spring2.shell_lib.configuration;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.jline.reader.History;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.Parser;
import org.jline.terminal.Terminal;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.context.annotation.Lazy;
import org.springframework.shell.jline.JLineShellAutoConfiguration;
import ru.geekbrains.dreamworkerln.spring2.shell_lib.shell.*;

@Configuration
public class SpringShellLibConfig {

    @Bean
    public ShellHelper shellHelper(@Lazy Terminal terminal,
                                   @Lazy ApplicationEventPublisher applicationEventPublisher) {

        return new ShellHelper(terminal, applicationEventPublisher);
    }

    @Bean
    public InputReader inputReader(
        @Lazy Terminal terminal,
        @Lazy Parser parser,
        JLineShellAutoConfiguration.CompleterAdapter completer,
        @Lazy History history,
        ShellHelper shellHelper) {
        
        LineReaderBuilder lineReaderBuilder = LineReaderBuilder.builder()
            .terminal(terminal)
            .completer(completer)
            .history(history)
            .highlighter(
                (LineReader reader, String buffer) -> new AttributedString(
                    buffer, AttributedStyle.BOLD.foreground(PromptColor.WHITE.toJlineAttributedStyle())
                )
            ).parser(parser);

        LineReader lineReader = lineReaderBuilder.build();
        lineReader.unsetOpt(LineReader.Option.INSERT_TAB);
        return new InputReader(lineReader, shellHelper);
    }

    @Bean
    public ProgressBar progressBar(ShellHelper shellHelper) {
        return new ProgressBar(shellHelper);
    }

    @Bean
    public ProgressCounter progressCounter(@Lazy Terminal terminal) {
        return new ProgressCounter(terminal);
    }

}
