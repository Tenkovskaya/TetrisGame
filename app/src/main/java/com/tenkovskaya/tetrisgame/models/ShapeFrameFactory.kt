package com.tenkovskaya.tetrisgame.models


enum class ShapeFrameFactory(val frameCount: Int, val startPosition: Int) {

    Tetromino1(1, 1) {
        override fun frame(frameNumber: Int): NewFrame = NewFrame(2)
            .frame("11")
            .frame("11")
    },
    Tetromino2(2, 1) {
        override fun frame(frameNumber: Int): NewFrame = when (frameNumber) {
            0 -> NewFrame(3)
                .frame("110")
                .frame("011")
            1 -> NewFrame(2)
                .frame("01")
                .frame("11")
                .frame("10")
            else -> throw IllegalArgumentException("$frameNumber is an invalid frame number")
        }
    },
    Tetromino3(2, 1) {
        override fun frame(frameNumber: Int): NewFrame = when (frameNumber) {
            0 -> NewFrame(3)
                .frame("011")
                .frame("110")
            1 -> NewFrame(2)
                .frame("10")
                .frame("11")
                .frame("01")
            else -> throw IllegalArgumentException("$frameNumber is an invalid frame number")
        }
    },
    Tetromino4(2, 2) {
        override fun frame(frameNumber: Int): NewFrame = when (frameNumber) {
            0 -> NewFrame(4)
                .frame("1111")
            1 -> NewFrame(1)
                .frame("1")
                .frame("1")
                .frame("1")
                .frame("1")
            else -> throw IllegalArgumentException("$frameNumber is an invalid frame number")
        }
    },
    Tetromino5(4, 1) {
        override fun frame(frameNumber: Int): NewFrame = when (frameNumber) {
            0 -> NewFrame(3)
                .frame("010")
                .frame("111")
            1 -> NewFrame(2)
                .frame("10")
                .frame("11")
                .frame("10")
            2 -> NewFrame(3)
                .frame("111")
                .frame("010")
            3 -> NewFrame(2)
                .frame("01")
                .frame("11")
                .frame("01")
            else -> throw IllegalArgumentException("$frameNumber is an invalid frame number")
        }
    },
    Tetromino6(4, 1) {
        override fun frame(frameNumber: Int): NewFrame = when (frameNumber) {
            0 -> NewFrame(3)
                .frame("100")
                .frame("111")
            1 -> NewFrame(2)
                .frame("11")
                .frame("10")
                .frame("10")
            2 -> NewFrame(3)
                .frame("111")
                .frame("001")
            3 -> NewFrame(2)
                .frame("01")
                .frame("01")
                .frame("11")
            else -> throw IllegalArgumentException("$frameNumber is an invalid frame number")
        }
    },
    Tetromino7(4, 1) {
        override fun frame(frameNumber: Int): NewFrame = when (frameNumber) {
            0 -> NewFrame(3)
                .frame("001")
                .frame("111")
            1 -> NewFrame(2)
                .frame("10")
                .frame("10")
                .frame("11")
            2 -> NewFrame(3)
                .frame("111")
                .frame("100")
            3 -> NewFrame(2)
                .frame("11")
                .frame("01")
                .frame("01")
            else -> throw IllegalArgumentException("$frameNumber is an invalid frame number")
        }
    };

    abstract fun frame(frameNumber: Int): NewFrame
}
