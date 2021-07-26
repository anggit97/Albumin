package com.anggit97.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Post(
    val body: String,
    val id: Int,
    val title: String,
    val userId: Int,
    var user: User
) : Parcelable {

    fun getSharedElementUsernameId() = user.username.plus("_").plus(id.toString())

    fun getSharedElementCompanyId() = user.company.plus("_").plus(id.toString())

    fun getSharedElementAvatarId() = user.getAvatarUrl().plus("_").plus(id.toString())

    fun getSharedElementTitleId() = title.take(10).plus("_").plus(id.toString())

    fun getSharedElementBodyId() = body.take(10).plus("_").plus(id.toString())
}