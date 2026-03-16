package com.iti.skypulse.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.iti.skypulse.R

val Poppins = FontFamily(
    Font(R.font.poppins_light, FontWeight.Light),
    Font(R.font.poppins_regular, FontWeight.Normal),
    Font(R.font.poppins_medium, FontWeight.Medium),
    Font(R.font.poppins_semibold, FontWeight.SemiBold),
    Font(R.font.poppins_bold, FontWeight.Bold),
)

object AppTypography {
    val bold72 = TextStyle(fontFamily = Poppins, fontWeight = FontWeight.Bold, fontSize = 72.sp)
    val bold56 = TextStyle(fontFamily = Poppins, fontWeight = FontWeight.Bold, fontSize = 56.sp)
    val bold45 = TextStyle(fontFamily = Poppins, fontWeight = FontWeight.Bold, fontSize = 45.sp)
    val bold36 = TextStyle(fontFamily = Poppins, fontWeight = FontWeight.Bold, fontSize = 36.sp)
    val bold20 = TextStyle(fontFamily = Poppins, fontWeight = FontWeight.Bold, fontSize = 20.sp)
    val bold14 = TextStyle(fontFamily = Poppins, fontWeight = FontWeight.Bold, fontSize = 14.sp)
    val bold10 = TextStyle(fontFamily = Poppins, fontWeight = FontWeight.Bold, fontSize = 10.sp)

    val semiBold32 = TextStyle(fontFamily = Poppins, fontWeight = FontWeight.SemiBold, fontSize = 32.sp)
    val semiBold28 = TextStyle(fontFamily = Poppins, fontWeight = FontWeight.SemiBold, fontSize = 28.sp)
    val semiBold24 = TextStyle(fontFamily = Poppins, fontWeight = FontWeight.SemiBold, fontSize = 24.sp)
    val semiBold22 = TextStyle(fontFamily = Poppins, fontWeight = FontWeight.SemiBold, fontSize = 22.sp)
    val semiBold18 = TextStyle(fontFamily = Poppins, fontWeight = FontWeight.SemiBold, fontSize = 18.sp)

    val semiBold16 =
        TextStyle(fontFamily = Poppins, fontWeight = FontWeight.SemiBold, fontSize = 18.sp)

    val medium16 = TextStyle(fontFamily = Poppins, fontWeight = FontWeight.Medium, fontSize = 16.sp)
    val medium14 = TextStyle(fontFamily = Poppins, fontWeight = FontWeight.Medium, fontSize = 14.sp)
    val medium12 = TextStyle(fontFamily = Poppins, fontWeight = FontWeight.Medium, fontSize = 12.sp)
    val medium10 = TextStyle(fontFamily = Poppins, fontWeight = FontWeight.Medium, fontSize = 10.sp)

    val regular16 = TextStyle(fontFamily = Poppins, fontWeight = FontWeight.Normal, fontSize = 16.sp)
    val regular14 = TextStyle(fontFamily = Poppins, fontWeight = FontWeight.Normal, fontSize = 14.sp)
    val regular12 = TextStyle(fontFamily = Poppins, fontWeight = FontWeight.Normal, fontSize = 12.sp)
}