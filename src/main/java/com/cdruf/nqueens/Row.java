package com.cdruf.nqueens;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Row {
    private int index;

    @Override
    public String toString() {
        return "r_" + index;
    }
}
