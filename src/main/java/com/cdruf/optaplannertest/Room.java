package com.cdruf.optaplannertest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Room {
    private String name;

    @Override
    public String toString() {
        return name;
    }
}
