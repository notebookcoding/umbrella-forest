package com.simulation.notebook.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * 排序规则
 * @author Administrator
 */
@Getter
@AllArgsConstructor
public enum OrderedEnum {
    /**
     * 上移一位
     */
    UP(-1, "上移"),
    /**
     * 下移一位
     */
    DOWN(1, "下移");

    private final int move;
    private final String name;

    private final static Map<Integer, OrderedEnum> ORDERED_MAP = new HashMap<>();

    static {
        for (OrderedEnum entry : OrderedEnum.values()) {
            ORDERED_MAP.put(entry.getMove(), entry);
        }
    }

    public static OrderedEnum get(Integer move) {
        return ORDERED_MAP.get(move);
    }

}
