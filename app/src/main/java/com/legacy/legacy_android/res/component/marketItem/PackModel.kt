package com.legacy.legacy_android.res.component.marketItem

import androidx.compose.ui.graphics.Color
import com.legacy.legacy_android.ui.theme.Blue_Netural
import com.legacy.legacy_android.ui.theme.Purple_Netural
import com.legacy.legacy_android.ui.theme.Red_Normal

data class PackModel(val id: Int, val name: String, val description: String, val credit: Int, val border: Color)

public object Packs {
    val purplePackList = listOf(
        PackModel(id = 0, name = "삼국시대 팩", description = "고구려 & 신라 & 백제 특성 \n카드가 포함된 카드팩", credit = 300000, border = Purple_Netural),
        PackModel(id = 1, name = "고려시대 팩", description = "고려의 기상이 담긴 카드들을 \n만날 수 있는 카드팩", credit = 300000, border = Purple_Netural),
        PackModel(id = 2, name = "조선시대 팩", description = "조선시대의 아름다움과 \n대한제국의 발전을 느낄 수 \n있는 카드팩", credit = 300000, border = Purple_Netural),
        PackModel(id = 3, name = "대한민국 팩", description = "자랑스러운 우리 나라,\n 대한민국의 문화가 담긴 카드팩", credit = 300000, border = Purple_Netural),
    )
    val bluePackList = listOf(
        PackModel(id = 0, name = "역사&학문 팩", description = "역사를 잊는 민족에게 \n미래는 없다.", credit = 300000, border = Blue_Netural),
        PackModel(id = 1, name = "예술&기술 팩", description = "고도로 발달한 기술은 \n마법과 구별할 수 없다.", credit = 300000, border = Blue_Netural),
        PackModel(id = 2, name = "신앙&체제 팩", description = "교회가 국가를 대신할 수 \n없듯이, 국가는 교회를 대신할 수 없다.", credit = 300000, border = Blue_Netural),
        PackModel(id = 3, name = "놀이&의식주 팩", description = "현재는 선물이다. \n그러니 걱정 말고 맘껏 즐거라.", credit = 300000, border = Blue_Netural),
    )
    val redPackList = listOf(
        PackModel(id = 0, name = "경상도 팩", description = "경상북도 & 경상남도의 \n카드가 포함된 카드팩", credit = 300000, border = Red_Normal),
        PackModel(id = 1, name = "경기도 팩", description = "경기도의 카드가 \n포함된 카드팩", credit = 300000, border = Red_Normal),
        PackModel(id = 2, name = "충청도 팩", description = "충청북도 & 충청남도의 \n카드가 포함된 카드팩", credit = 300000, border = Red_Normal),
        PackModel(id = 3, name = "전라도 팩", description = "전라북도 & 전라남도의 \n카드가 포함된 카드팩", credit = 300000, border = Red_Normal),
        PackModel(id = 4, name = "제주도 팩", description = "제주도의 카드가 \n포함된 카드팩", credit = 300000, border = Red_Normal),
        PackModel(id = 5, name = "강원도 팩", description = "강원도의 카드가 \n포함된 카드팩", credit = 300000, border = Red_Normal),
    )
}
