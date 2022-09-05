package com.jaehyeon.intinbletestapp.util.device

/**
 * Created by Jaehyeon on 2022/08/29.
 */
enum class SendMessageType(val message: String) {
    Mod("MOD?"),
    Time("MODTS:%s"),
    Start("MOD:START"),
    Stop("MOD:STOP"),
    Pause("MOD:PAUSE"),
    Battery("BAT?"),
    ReqSpiData("REQ:MOD_SPI_DATA")
}