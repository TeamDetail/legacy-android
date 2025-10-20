package com.legacy.legacy_android.feature.network.user

data class UserData(
    val userId: Long,
    val nickname: String,
    val imageUrl: String,
    val description: String,
    val credit: Int,
    val level: Int,
    val title: Title,
    val record: Record
)

data class Record(
    val adventure: Adventure,
    val experience: Experience,
)

data class Experience(
    val rank: Int,
    val adventureAchieve: Int,
    val experienceAchieve: Int,
    val hiddenAchieve: Int,
    val exp: Int,
    val createdAt: String,
    val titleCount: Int,
    val cardCount: Int,
    val shiningCardCount: Int
)

data class Adventure(
    val rank: Int,
    val allBlocks: Int,
    val ruinsBlocks: Int,
    val solvedQuizzes: Int,
    val wrongQuizzes: Int,
    val clearCourse: Int,
    val makeCourse: Int,
    val commentCount: Int
)


data class Title(
    val name: String,
    val content: String,
    val styleId: Int,
    val grade: Int
)

data class DescriptionRequest(
    val description: String
)
data class TitleRequest(
    val styleId: Int
)

data class ImageRequest(
    val profileImageUrl: String
)