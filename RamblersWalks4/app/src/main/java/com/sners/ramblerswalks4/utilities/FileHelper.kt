package com.sners.myfavouriterobot.utilities

import android.content.Context
import java.io.InputStream

class FileHelper {

    companion object {
        //  How to read from resources - for json etc
        //  Benefits of this one is you cannot misspell the name
        fun getTextFromResources(context: Context, resourceId: Int) : String {
            return context.resources.openRawResource(resourceId).use {
                it.bufferedReader().use {
                    it.readText()
                }}
        }

        //  To add assets - File -> New -> Folder -> Assets folder
        //  Benefit of this one is file name can be changed on the fly
        fun getTextFromAssets(context: Context, assetName: String) : String {
            return context.assets.open(assetName).use {
                it.bufferedReader().use {
                    it.readText()
                }}
        }
    }
}