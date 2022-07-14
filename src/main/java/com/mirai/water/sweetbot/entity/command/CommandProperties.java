package com.mirai.water.sweetbot.entity.command;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author by MechellWater
 * @date : 2021-03-17 15:41
 */
@Getter
@Setter
public class CommandProperties {
    /**
     * 指令名称
     */
    public String name;
    /**
     * 指令别称
     */
    public ArrayList<String> alias;

    public CommandProperties(String name, ArrayList<String> alias) {
        this.name = name;
        this.alias = alias;
    }

    public CommandProperties(String name, String... alias) {
        this(name, new ArrayList<>(Arrays.asList(alias)));
    }

}
