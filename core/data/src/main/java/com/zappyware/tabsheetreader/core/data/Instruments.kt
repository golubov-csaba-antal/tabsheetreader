package com.zappyware.tabsheetreader.core.data

enum class InstrumentCategory {
    Piano,
    ChromaticPercussion,
    Organ,
    Guitar,
    Bass,
    Strings,
    Ensemble,
    Brass,
    Reed,
    Pipe,
    SynthLead,
    SynthPad,
    SynthEffects,
    Ethnic,
    Percussive,
    SoundEffects
}

enum class Instruments(
    val category: InstrumentCategory,
    val instrumentId: Int,
    val instrumentName: String
) {
    // Piano
    AcousticGrandPiano(InstrumentCategory.Piano, 0, "Acoustic Grand Piano"),
    AcousticBrightPiano(InstrumentCategory.Piano, 1, "Bright Acoustic Piano"),
    ElectricGrandPiano(InstrumentCategory.Piano, 2, "Electric Grand Piano"),
    HonkyTonkPiano(InstrumentCategory.Piano, 3, "Honky-tonk Piano"),
    ElectricPiano1(InstrumentCategory.Piano, 4, "Electric Piano 1"),
    ElectricPiano2(InstrumentCategory.Piano, 5, "Electric Piano 2"),
    Harpsichord(InstrumentCategory.Piano, 6, "Harpsichord"),
    Clavinet(InstrumentCategory.Piano, 7, "Clavinet"),

    // Chromatic Percussion
    Celesta(InstrumentCategory.ChromaticPercussion, 8, "Celesta"),
    Glockenspiel(InstrumentCategory.ChromaticPercussion, 9, "Glockenspiel"),
    MusicBox(InstrumentCategory.ChromaticPercussion, 10, "Music Box"),
    Vibraphone(InstrumentCategory.ChromaticPercussion, 11, "Vibraphone"),
    Marimba(InstrumentCategory.ChromaticPercussion, 12, "Marimba"),
    Xylophone(InstrumentCategory.ChromaticPercussion, 13, "Xylophone"),
    TubularBells(InstrumentCategory.ChromaticPercussion, 14, "Tubular Bells"),
    Dulcimer(InstrumentCategory.ChromaticPercussion, 15, "Dulcimer"),

    // Organ
    DrawbarOrgan(InstrumentCategory.Organ, 16, "Drawbar Organ"),
    PercussiveOrgan(InstrumentCategory.Organ, 17, "Percussive Organ"),
    RockOrgan(InstrumentCategory.Organ, 18, "Rock Organ"),
    ChurchOrgan(InstrumentCategory.Organ, 19, "Church Organ"),
    ReedOrgan(InstrumentCategory.Organ, 20, "Reed Organ"),
    Accordion(InstrumentCategory.Organ, 21, "Accordion"),
    Harmonica(InstrumentCategory.Organ, 22, "Harmonica"),
    TangoAccordion(InstrumentCategory.Organ, 23, "Tango Accordion"),

    // Guitar
    AcousticGuitarNylon(InstrumentCategory.Guitar, 24, "Acoustic Guitar (nylon)"),
    AcousticGuitarSteel(InstrumentCategory.Guitar, 25, "Acoustic Guitar (steel)"),
    ElectricGuitarJazz(InstrumentCategory.Guitar, 26, "Electric Guitar (jazz)"),
    ElectricGuitarClean(InstrumentCategory.Guitar, 27, "Electric Guitar (clean)"),
    ElectricGuitarMuted(InstrumentCategory.Guitar, 28, "Electric Guitar (muted)"),
    OverdrivenGuitar(InstrumentCategory.Guitar, 29, "Overdriven Guitar"),
    DistortionGuitar(InstrumentCategory.Guitar, 30, "Distortion Guitar"),
    GuitarHarmonics(InstrumentCategory.Guitar, 31, "Guitar Harmonics"),

    // Bass
    AcousticBass(InstrumentCategory.Bass, 32, "Acoustic Bass"),
    ElectricBassFinger(InstrumentCategory.Bass, 33, "Electric Bass (finger)"),
    ElectricBassPick(InstrumentCategory.Bass, 34, "Electric Bass (pick)"),
    FretlessBass(InstrumentCategory.Bass, 35, "Fretless Bass"),
    SlapBass1(InstrumentCategory.Bass, 36, "Slap Bass 1"),
    SlapBass2(InstrumentCategory.Bass, 37, "Slap Bass 2"),
    SynthBass1(InstrumentCategory.Bass, 38, "Synth Bass 1"),
    SynthBass2(InstrumentCategory.Bass, 39, "Synth Bass 2"),

    // Strings
    Violin(InstrumentCategory.Strings, 40, "Violin"),
    Viola(InstrumentCategory.Strings, 41, "Viola"),
    Cello(InstrumentCategory.Strings, 42, "Cello"),
    Contrabass(InstrumentCategory.Strings, 43, "Contrabass"),
    TremoloStrings(InstrumentCategory.Strings, 44, "Tremolo Strings"),
    PizzicatoStrings(InstrumentCategory.Strings, 45, "Pizzicato Strings"),
    OrchestralHarp(InstrumentCategory.Strings, 46, "Orchestral Harp"),
    Timpani(InstrumentCategory.Strings, 47, "Timpani"),

    // Ensemble
    StringEnsemble1(InstrumentCategory.Ensemble, 48, "String Ensemble 1"),
    StringEnsemble2(InstrumentCategory.Ensemble, 49, "String Ensemble 2"),
    SynthStrings1(InstrumentCategory.Ensemble, 50, "SynthStrings 1"),
    SynthStrings2(InstrumentCategory.Ensemble, 51, "SynthStrings 2"),
    ChoirAahs(InstrumentCategory.Ensemble, 52, "Choir Aahs"),
    VoiceOohs(InstrumentCategory.Ensemble, 53, "Voice Oohs"),
    SynthVoice(InstrumentCategory.Ensemble, 54, "Synth Voice"),
    OrchestraHit(InstrumentCategory.Ensemble, 55, "Orchestra Hit"),

    // Brass
    Trumpet(InstrumentCategory.Brass, 56, "Trumpet"),
    Trombone(InstrumentCategory.Brass, 57, "Trombone"),
    Tuba(InstrumentCategory.Brass, 58, "Tuba"),
    MutedTrumpet(InstrumentCategory.Brass, 59, "Muted Trumpet"),
    FrenchHorn(InstrumentCategory.Brass, 60, "French Horn"),
    BrassSection(InstrumentCategory.Brass, 61, "Brass Section"),
    SynthBrass1(InstrumentCategory.Brass, 62, "SynthBrass 1"),
    SynthBrass2(InstrumentCategory.Brass, 63, "SynthBrass 2"),

    // Reed
    SopranoSax(InstrumentCategory.Reed, 64, "Soprano Sax"),
    AltoSax(InstrumentCategory.Reed, 65, "Alto Sax"),
    TenorSax(InstrumentCategory.Reed, 66, "Tenor Sax"),
    BaritoneSax(InstrumentCategory.Reed, 67, "Baritone Sax"),
    Oboe(InstrumentCategory.Reed, 68, "Oboe"),
    EnglishHorn(InstrumentCategory.Reed, 69, "English Horn"),
    Bassoon(InstrumentCategory.Reed, 70, "Bassoon"),
    Clarinet(InstrumentCategory.Reed, 71, "Clarinet"),

    // Pipe
    Piccolo(InstrumentCategory.Pipe, 72, "Piccolo"),
    Flute(InstrumentCategory.Pipe, 73, "Flute"),
    Recorder(InstrumentCategory.Pipe, 74, "Recorder"),
    PanFlute(InstrumentCategory.Pipe, 75, "Pan Flute"),
    BlownBottle(InstrumentCategory.Pipe, 76, "Blown Bottle"),
    Shakuhachi(InstrumentCategory.Pipe, 77, "Shakuhachi"),
    Whistle(InstrumentCategory.Pipe, 78, "Whistle"),
    Ocarina(InstrumentCategory.Pipe, 79, "Ocarina"),

    // Synth Lead
    Lead1Square(InstrumentCategory.SynthLead, 80, "Lead 1 (square)"),
    Lead2Sawtooth(InstrumentCategory.SynthLead, 81, "Lead 2 (sawtooth)"),
    Lead3Calliope(InstrumentCategory.SynthLead, 82, "Lead 3 (calliope)"),
    Lead4Chiff(InstrumentCategory.SynthLead, 83, "Lead 4 (chiff)"),
    Lead5Charang(InstrumentCategory.SynthLead, 84, "Lead 5 (charang)"),
    Lead6Voice(InstrumentCategory.SynthLead, 85, "Lead 6 (voice)"),
    Lead7Fifths(InstrumentCategory.SynthLead, 86, "Lead 7 (fifths)"),
    Lead8BassLead(InstrumentCategory.SynthLead, 87, "Lead 8 (bass + lead)"),

    // Synth Pad
    Pad1NewAge(InstrumentCategory.SynthPad, 88, "Pad 1 (new age)"),
    Pad2Warm(InstrumentCategory.SynthPad, 89, "Pad 2 (warm)"),
    Pad3Polysynth(InstrumentCategory.SynthPad, 90, "Pad 3 (polysynth)"),
    Pad4Choir(InstrumentCategory.SynthPad, 91, "Pad 4 (choir)"),
    Pad5Bowed(InstrumentCategory.SynthPad, 92, "Pad 5 (bowed)"),
    Pad6Metallic(InstrumentCategory.SynthPad, 93, "Pad 6 (metallic)"),
    Pad7Halo(InstrumentCategory.SynthPad, 94, "Pad 7 (halo)"),
    Pad8Sweep(InstrumentCategory.SynthPad, 95, "Pad 8 (sweep)"),

    // Synth Effects
    FX1Rain(InstrumentCategory.SynthEffects, 96, "FX 1 (rain)"),
    FX2Soundtrack(InstrumentCategory.SynthEffects, 97, "FX 2 (soundtrack)"),
    FX3Crystal(InstrumentCategory.SynthEffects, 98, "FX 3 (crystal)"),
    FX4Atmosphere(InstrumentCategory.SynthEffects, 99, "FX 4 (atmosphere)"),
    FX5Brightness(InstrumentCategory.SynthEffects, 100, "FX 5 (brightness)"),
    FX6Goblins(InstrumentCategory.SynthEffects, 101, "FX 6 (goblins)"),
    FX7Echoes(InstrumentCategory.SynthEffects, 102, "FX 7 (echoes)"),
    FX8SciFi(InstrumentCategory.SynthEffects, 103, "FX 8 (sci-fi)"),

    // Ethnic
    Sitar(InstrumentCategory.Ethnic, 104, "Sitar"),
    Banjo(InstrumentCategory.Ethnic, 105, "Banjo"),
    Shamisen(InstrumentCategory.Ethnic, 106, "Shamisen"),
    Koto(InstrumentCategory.Ethnic, 107, "Koto"),
    Kalimba(InstrumentCategory.Ethnic, 108, "Kalimba"),
    Bagpipe(InstrumentCategory.Ethnic, 109, "Bagpipe"),
    Fiddle(InstrumentCategory.Ethnic, 110, "Fiddle"),
    Shanai(InstrumentCategory.Ethnic, 111, "Shanai"),

    // Percussive
    TinkleBell(InstrumentCategory.Percussive, 112, "Tinkle Bell"),
    Agogo(InstrumentCategory.Percussive, 113, "Agogo"),
    SteelDrums(InstrumentCategory.Percussive, 114, "Steel Drums"),
    Woodblock(InstrumentCategory.Percussive, 115, "Woodblock"),
    TaikoDrum(InstrumentCategory.Percussive, 116, "Taiko Drum"),
    MelodicTom(InstrumentCategory.Percussive, 117, "Melodic Tom"),
    SynthDrum(InstrumentCategory.Percussive, 118, "Synth Drum"),
    ReverseCymbal(InstrumentCategory.Percussive, 119, "Reverse Cymbal"),

    // Sound Effects
    GuitarFretNoise(InstrumentCategory.SoundEffects, 120, "Guitar Fret Noise"),
    BreathNoise(InstrumentCategory.SoundEffects, 121, "Breath Noise"),
    Seashore(InstrumentCategory.SoundEffects, 122, "Seashore"),
    BirdTweet(InstrumentCategory.SoundEffects, 123, "Bird Tweet"),
    TelephoneRing(InstrumentCategory.SoundEffects, 124, "Telephone Ring"),
    Helicopter(InstrumentCategory.SoundEffects, 125, "Helicopter"),
    Applause(InstrumentCategory.SoundEffects, 126, "Applause"),
    Gunshot(InstrumentCategory.SoundEffects, 127, "Gunshot");

    companion object {
        fun fromId(id: Int): Instruments? = entries.find { it.instrumentId == id }
    }
}
