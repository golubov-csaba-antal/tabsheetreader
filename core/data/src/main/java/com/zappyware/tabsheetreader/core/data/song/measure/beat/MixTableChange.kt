package com.zappyware.tabsheetreader.core.data.song.measure.beat

data class MixTableChange(
    val instrument: MixTableItem? = null,
    val rse: RSEInstrument = RSEInstrument(),
    val volume: MixTableItem? = null,
    val balance: MixTableItem? = null,
    val chorus: MixTableItem? = null,
    val reverb: MixTableItem? = null,
    val phaser: MixTableItem? = null,
    val tremolo: MixTableItem? = null,
    val tempoName: String = "",
    val tempo: MixTableItem? = null,
    val hideTempo: Boolean = true,
    val wah: WahEffect? = null,
    val useRSE: Boolean = false
) {
    val isJustWah: Boolean
        get() = instrument == null &&
                volume == null &&
                balance == null &&
                chorus == null &&
                reverb == null &&
                phaser == null &&
                tremolo == null &&
                tempo == null &&
                wah != null
}
