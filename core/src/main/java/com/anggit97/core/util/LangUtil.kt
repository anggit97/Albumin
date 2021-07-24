package com.anggit97.core.util

import java.util.*


/**
 * Created by Anggit Prayogo on 14,July,2021
 * GitHub : https://github.com/anggit97
 */
object LangUtil{

    fun getLanguageName(): String = Locale.getDefault().displayName

    fun getLanguageCountryCode(): String = Locale.getDefault().country
}
