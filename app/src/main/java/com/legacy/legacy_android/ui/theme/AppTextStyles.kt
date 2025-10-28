package com.legacy.legacy_android.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp

object AppTextStyles {
    // Title
    object Title1 {
        val bold = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 36.sp,
            letterSpacing = (-0.03).em,
            color = com.legacy.legacy_android.ui.theme.Label
        )
        val medium = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.Bold,
            fontSize = 36.sp,
            letterSpacing = (-0.03).em,
            color = com.legacy.legacy_android.ui.theme.Label
        )
        val regular = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.Normal,
            fontSize = 36.sp,
            letterSpacing = (-0.03).em,
            color = com.legacy.legacy_android.ui.theme.Label
        )
    }

    object Title2 {
        val bold = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 28.sp,
            letterSpacing = (-0.03).em,
            color = com.legacy.legacy_android.ui.theme.Label
        )
        val medium = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            letterSpacing = (-0.03).em,
            color = com.legacy.legacy_android.ui.theme.Label
        )
        val regular = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.Normal,
            fontSize = 28.sp,
            letterSpacing = (-0.03).em,
            color = com.legacy.legacy_android.ui.theme.Label
        )
    }

    object Title3 {
        val bold = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 24.sp,
            
            letterSpacing = (-0.03).em,
            color = com.legacy.legacy_android.ui.theme.Label
        )
        val medium = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            
            letterSpacing = (-0.03).em,
            color = com.legacy.legacy_android.ui.theme.Label
        )
        val regular = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.Normal,
            fontSize = 24.sp,
            
            letterSpacing = (-0.03).em,
            color = com.legacy.legacy_android.ui.theme.Label
        )
    }
    // Heading
    object Heading1 {
        val bold = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 22.sp,
            
            letterSpacing = (-0.03).em,
            color = com.legacy.legacy_android.ui.theme.Label
        )
        val medium = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.Medium,
            fontSize = 22.sp,
            
            letterSpacing = (-0.03).em,
            color = com.legacy.legacy_android.ui.theme.Label
        )
        val regular = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.Normal,
            fontSize = 22.sp,
            
            letterSpacing = (-0.03).em,
            color = com.legacy.legacy_android.ui.theme.Label
        )
    }

    object Heading2 {
        val bold = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 20.sp,
            
            letterSpacing = (-0.03).em,
            color = com.legacy.legacy_android.ui.theme.Label
        )
        val medium = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp,
            
            letterSpacing = (-0.03).em,
            color = com.legacy.legacy_android.ui.theme.Label
        )
        val regular = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.Normal,
            fontSize = 20.sp,
            
            letterSpacing = (-0.03).em,
            color = com.legacy.legacy_android.ui.theme.Label
        )
    }
    object Headline {
        val bold = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 18.sp,
            color = com.legacy.legacy_android.ui.theme.Label
        )
        val medium = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp,
            color = com.legacy.legacy_android.ui.theme.Label
        )
        val regular = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp,
            color = com.legacy.legacy_android.ui.theme.Label
        )
    }
    object Body1 {
        val bold = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            
            letterSpacing = (0.01).em,
            color = com.legacy.legacy_android.ui.theme.Label
        )
        val medium = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,

            letterSpacing = (0.01).em,
            color = com.legacy.legacy_android.ui.theme.Label
        )
        val regular = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            
            letterSpacing = (0.01).em,
            color = com.legacy.legacy_android.ui.theme.Label
        )
    }

    object Body2 {
        val bold = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            
            letterSpacing = (0.01).em,
            color = com.legacy.legacy_android.ui.theme.Label
        )
        val medium = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.Medium,
            fontSize = 15.sp,
            
            letterSpacing = (0.01).em,
            color = com.legacy.legacy_android.ui.theme.Label
        )
        val regular = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.Normal,
            fontSize = 15.sp,
            
            letterSpacing = (0.01).em,
            color = com.legacy.legacy_android.ui.theme.Label
        )
    }
    object Label {
        val ExtraBold = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 14.sp,
            
            letterSpacing = (0.02).em,
            color = com.legacy.legacy_android.ui.theme.Label
        )
        val Bold = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            
            letterSpacing = (0.02).em,
            color = com.legacy.legacy_android.ui.theme.Label
        )
        val Medium = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            
            letterSpacing = (0.02).em,
            color = com.legacy.legacy_android.ui.theme.Label
        )
        val regular = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            
            letterSpacing = (0.02).em,
            color = com.legacy.legacy_android.ui.theme.Label
        )
    }

    object Caption1 {
        val ExtraBold = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 13.sp,
            
            letterSpacing = (0.02).em,
            color = com.legacy.legacy_android.ui.theme.Label
        )
        val Bold = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp,
            
            letterSpacing = (0.02).em,
            color = com.legacy.legacy_android.ui.theme.Label
        )
        val Medium = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.Medium,
            fontSize = 13.sp,
            letterSpacing = (0.02).em,
            color = com.legacy.legacy_android.ui.theme.Label
        )
        val regular = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.Normal,
            fontSize = 13.sp,
            
            letterSpacing = (0.02).em,
            color = com.legacy.legacy_android.ui.theme.Label
        )
    }
    object Caption2 {
        val Bold = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 12.sp,
            
            letterSpacing = (0.02).em,
            color = com.legacy.legacy_android.ui.theme.Label
        )
        val Medium = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            
            letterSpacing = (0.02).em,
            color = com.legacy.legacy_android.ui.theme.Label
        )
        val regular = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            
            letterSpacing = (0.02).em,
            color = com.legacy.legacy_android.ui.theme.Label
        )
    }

}