package com.zappyware.tabsheetreader.core.data.song.measure.beat

data class NoteEffect(
    val accentuatedNote: Boolean = false,
    val bend: BendEffect? = null,
    val ghostNote: Boolean = false,
    val grace: GraceEffect? = null,
    val hammer: Boolean = false,
    val harmonic: HarmonicEffect? = null,
    val heavyAccentuatedNote: Boolean = false,
    val leftHandFinger: Fingering = Fingering.Open,
    val letRing: Boolean = false,
    val palmMute: Boolean = false,
    val rightHandFinger: Fingering = Fingering.Open,
    val slides: List<SlideType> = emptyList(),
    val staccato: Boolean = false,
    val tremoloPicking: TremoloPickingEffect? = null,
    val trill: TrillEffect? = null,
    val vibrato: Boolean = false,
) {
    companion object {
        val DEFAULT = NoteEffect()
    }

    val isBend: Boolean
        get() = bend != null && bend.points.isNotEmpty()

    val isHarmonic: Boolean
        get() = harmonic != null

    val isGrace: Boolean
        get() = grace != null

    val isTrill: Boolean
        get() = trill != null

    val isTremoloPicking: Boolean
        get() = tremoloPicking != null

    val isFingering: Boolean
        get() = leftHandFinger != Fingering.Open || rightHandFinger != Fingering.Open

    val isDefault: Boolean
        get() {
            val default = DEFAULT
            return leftHandFinger == default.leftHandFinger &&
                    rightHandFinger == default.rightHandFinger &&
                    bend == default.bend &&
                    harmonic == default.harmonic &&
                    grace == default.grace &&
                    trill == default.trill &&
                    tremoloPicking == default.tremoloPicking &&
                    vibrato == default.vibrato &&
                    slides == default.slides &&
                    hammer == default.hammer &&
                    palmMute == default.palmMute &&
                    staccato == default.staccato &&
                    letRing == default.letRing
        }
}