package com.fappslab.bookshelf.main.presentation.model

import androidx.annotation.StringRes
import com.fappslab.bookshelf.R

enum class ChipType(@StringRes val textRes: Int) {
    CODE(textRes = R.string.code),
    BUSINESS(textRes = R.string.business),
    HISTORY(textRes = R.string.history),
    AI(textRes = R.string.ai),
    ROMANCE(textRes = R.string.romance),
    TECHNOLOGY(textRes = R.string.technology),
    ADVENTURE(textRes = R.string.adventure),
    BLOCKCHAIN(textRes = R.string.blockchain),
    MYSTERY(textRes = R.string.mystery),
    ANDROID(textRes = R.string.android),
    BIOGRAPHY(textRes = R.string.biography),
    HORROR(textRes = R.string.horror),
    IOS(textRes = R.string.ios);

    companion object {
        val list: List<ChipType>
            get() = ChipType.values()
                .toList()
                .shuffled()
    }
}
