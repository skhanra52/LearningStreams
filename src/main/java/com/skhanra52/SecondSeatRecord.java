package com.skhanra52;

import java.util.Random;

public record SecondSeatRecord(char rowMaker, int seatNumber, boolean isReserved) {
    public SecondSeatRecord(char rowMaker, int seatNumber) {
        this(rowMaker, seatNumber, new Random().nextBoolean());
    }
}
