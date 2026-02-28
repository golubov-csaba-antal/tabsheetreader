package com.zappyware.tabsheetreader.composable.common

import com.zappyware.tabsheetreader.R
import com.zappyware.tabsheetreader.core.data.InstrumentCategory
import com.zappyware.tabsheetreader.core.data.Instruments
import com.zappyware.tabsheetreader.core.data.Track

val Track.iconResource: Int get() {
    if (isPercussionTrack) return R.drawable.drum
    if (is12StringedGuitarTrack) return R.drawable.strings

    val instrument = Instruments.fromId(rse.instrument.instrument) ?: return R.drawable.unknown
    return when(instrument.category) {
        InstrumentCategory.Piano -> R.drawable.piano
        InstrumentCategory.ChromaticPercussion -> R.drawable.chromatic_percussion
        InstrumentCategory.Organ -> R.drawable.organ
        InstrumentCategory.Guitar -> R.drawable.eguitar
        InstrumentCategory.Bass -> R.drawable.bass
        InstrumentCategory.Strings -> R.drawable.strings
        InstrumentCategory.Ensemble -> R.drawable.strings
        InstrumentCategory.Brass -> R.drawable.brass
        InstrumentCategory.Reed -> R.drawable.reed
        InstrumentCategory.Pipe -> R.drawable.pipe
        InstrumentCategory.SynthLead -> R.drawable.synth
        InstrumentCategory.SynthPad -> R.drawable.synth
        InstrumentCategory.SynthEffects -> R.drawable.synth
        InstrumentCategory.Ethnic -> R.drawable.ethnic
        InstrumentCategory.Percussive -> R.drawable.drum
        InstrumentCategory.SoundEffects -> R.drawable.soundeffect
    }
}