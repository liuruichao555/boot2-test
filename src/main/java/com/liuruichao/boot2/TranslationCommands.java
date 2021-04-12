package com.liuruichao.boot2;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

/**
 * TranslationCommands
 *
 * @author ruichao.liu
 * Created on 2021-04-12 15:13
 */
@ShellComponent
public class TranslationCommands {
    @ShellMethod("Translate text from one language to another.")
    public String translate() {
        return "hehe";
    }
}
